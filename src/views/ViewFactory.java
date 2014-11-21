package views;

import controllers.ControllerFactory;
import java.io.IOException;
import javafx.fxml.FXMLLoader;

/**
 * Factory class for creating new FXML views
 * @author Jacob
 */
public class ViewFactory {

  /**
   * Creates a new view from the specified FXML file.
   * @param url Location of the FXML file.
   * @param sharedInstances Instances to share (inject) into the view controller(s).
   * @return The loaded object hierarchy.
   * @throws IOException
   */
  public Object create(String url, Object[] sharedInstances) throws IOException {
    ControllerFactory factory = new ControllerFactory();
    factory.addSharedInstances(sharedInstances);
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(ViewFactory.class.getResource(url));
    loader.setControllerFactory(factory);
    return loader.load();
  }
  
  /**
   * Creates a new view from the specified FXML file.
   * @param url Location of the FXML file.
   * @param controller The controller to associate with the view.
   * @return The loaded object hierarchy.
   * @throws IOException
   */
  public Object create(String url, Object controller) throws IOException
  {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(ViewFactory.class.getResource(url));
    loader.setController(controller);
    return loader.load();
  }
}
