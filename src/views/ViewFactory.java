package views;

import controllers.ControllerFactory;
import java.io.IOException;
import javafx.fxml.FXMLLoader;

/**
 *
 * @author Jacob
 */
public class ViewFactory {

  public static Object create(String url, Object[] singletons) throws IOException {
    ControllerFactory factory = new ControllerFactory();
    factory.addSingletons(singletons);
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(ControllerFactory.class.getResource(url));
    loader.setControllerFactory(factory);
    return loader.load();
  }
}
