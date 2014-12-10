/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import controllers.Main;
import controllers.MainViewController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import models.rocket.Rocket;
import models.rocket.data.XmlRocketSerializer;

/**
 *
 * @author Joseph
 */
public class FileHelper {

  final static Logger logger = Logger.getLogger(FileHelper.class.getName());

  public static void open(MainViewModel mainViewModel, Node root) {
    if (mainViewModel.isUnsaved()) {
      //prompt user if he wants to save or not
    }
    File openFile;
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Resource File");
    //Set initial file path
    configInitialDirectory(fileChooser, mainViewModel);
    openFile = fileChooser.showOpenDialog(root.getScene().getWindow());
    
    if (openFile != null) {
      mainViewModel.setPresentWorkingFile(openFile);
      mainViewModel.setPresentWorkingDirectory(openFile.getParentFile());

      try {
        spawnNewInstance(loadRocket(openFile), mainViewModel);
      } catch (Exception ex) {
        //Needs a prompt to let the user know that loading the file errored
        logger.log(Level.SEVERE, "Load rocket failed");
      }
    }
  }

  public static void save(MainViewModel mainViewModel, Node root) {
    File saveFile;
    if (mainViewModel.hasNeverBeenSaved()) {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Open Resource File");
      configInitialDirectory(fileChooser, mainViewModel);
      saveFile = fileChooser.showSaveDialog(root.getScene().getWindow());

      try {
        saveRocket(saveFile, mainViewModel);
        mainViewModel.setNeverBeenSaved(false);
      } catch (Exception ex) {
        //Inform user that the saving did not work.
      }
    } else {
      if (mainViewModel.isUnsaved()) {
        //Only attempt to save if there's actually been a change
        saveFile = mainViewModel.getPresentWorkingFile();
        try {
          saveRocket(saveFile, mainViewModel);
          mainViewModel.setNeverBeenSaved(false);
        } catch (Exception ex) {
          //Inform user that the saving did not work.
        }
      }
    }
  }

  static public void fileSaveAs(MainViewModel mainViewModel, Node root) {
    File saveFile;
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Resource File");
    configInitialDirectory(fileChooser, mainViewModel);
    saveFile = fileChooser.showSaveDialog(root.getScene().getWindow());

    try {
      saveRocket(saveFile, mainViewModel);
      mainViewModel.setNeverBeenSaved(false);
    } catch (Exception ex) {
      //Inform user that the saving did not work.
    }
  }

  private static Rocket loadRocket(File openFile) throws Exception {
    try {
      try {
        InputStream inputStream = new FileInputStream(openFile);
        ISerializer<Rocket> serializer = new XmlRocketSerializer();
        return serializer.deserialize(inputStream);
      } catch (Exception ex) {
        logger.log(Level.WARNING, "Failed to load rocket");
        throw new Exception();
      }
    } catch (NullPointerException npex) {
      logger.log(Level.WARNING, "The selected file is null");
      throw new Exception();
    }
  }

  static private void saveRocket(File saveFile, MainViewModel mainViewModel) throws Exception {
    try {
      //Set the new present working directory to the save file's directory
      mainViewModel.setPresentWorkingDirectory(saveFile.getParentFile());
    } catch (NullPointerException npex) {
      logger.log(Level.WARNING, "File Chooser did not choose a file", npex);
      throw new Exception();
    }

    try {
      OutputStream outStream = new FileOutputStream(saveFile);
      ISerializer<Rocket> serializer = new XmlRocketSerializer();
      serializer.serialize(mainViewModel.getRocket(), outStream);
    } catch (Exception ex) {
      logger.log(Level.WARNING, null, ex);
      throw new Exception();
    }
  }

  static public String openAtmosphereFile(MainViewModel mainViewModel, Node root) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Open Atmosphere File");
    FileHelper.configInitialDirectory(fileChooser, mainViewModel);
    File file = fileChooser.showOpenDialog(root.getScene().getWindow());
    if (file != null) {
      mainViewModel.getSimulation().setAtmosphereFile(file.getAbsolutePath());
      return file.getName();
    }
    return "";
  }

  static private void configInitialDirectory(FileChooser fileChooser, MainViewModel mainViewModel) {
    if (mainViewModel.getPresentWorkingDirectory() == null) {
      //If no present working directory, use default
      fileChooser.setInitialDirectory(
        new File(AppSettings.getInstance().getDefaultRocketPath()));
    } else {
      //If there's a present working directory, open up to that directory
      fileChooser.setInitialDirectory(mainViewModel.getPresentWorkingDirectory());
    }
    //Make sure the initial directory is a directory    
    if (!fileChooser.getInitialDirectory().isDirectory()) {
      logger.warning("Invalid initial directory path");
      fileChooser.setInitialDirectory(null);
    }
  }

  public static void spawnNewInstance(Rocket rocket, MainViewModel mainViewModel) {
    MainViewModel newModel = new MainViewModel();
    newModel.setPresentWorkingDirectory(mainViewModel.getPresentWorkingDirectory());
    newModel.setPresentWorkingFile(mainViewModel.getPresentWorkingFile());
    newModel.setRocket(rocket);
    Main.startNewMainView(newModel);
  }

  public static File createRocketDir(MainViewModel mainViewModel, Node root) throws IOException {
    File rocketFolder;
    if (!mainViewModel.getPresentWorkingFile().isFile()) {
      save(mainViewModel, root);
    }
    int dotIndex = mainViewModel.getPresentWorkingFile().getName().indexOf(".");
    rocketFolder = new File(mainViewModel.getPresentWorkingFile().getParentFile().getPath(),
      mainViewModel.getPresentWorkingFile().getName().substring(0, dotIndex));
    rocketFolder.mkdir();
    return rocketFolder;
  }

  public static File createResultsFolder(File rocketFolder, int finNum, int motorNum) throws IOException {
    File file = new File(rocketFolder, "Variant-Motor" + motorNum + "-FinSet" + finNum);
    file.mkdir();
    return file;
  }
  
  public static File spawnResultsFilePath(File resultsFolder, int finNum, int motorNum) throws IOException {
    File file = new File(resultsFolder, "Variant-Motor" + motorNum + "-FinSet" + finNum + ".csv");
    file.createNewFile();
    return file;
  }
}