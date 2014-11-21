package views;

import controllers.ControllerFactory;
import java.io.IOException;
import javafx.fxml.FXMLLoader;

/**
 *
 * @author Jacob
 */
public class ViewFactory {

  public Object create(String url, Object[] singletons) throws IOException {
    ControllerFactory factory = new ControllerFactory();
    factory.addSingletons(singletons);
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(ViewFactory.class.getResource(url));
    loader.setControllerFactory(factory);
    return loader.load();
  }
  
  public Object create(String url, Object controller) throws IOException
  {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(ViewFactory.class.getResource(url));
    loader.setController(controller);
    return loader.load();
  }
}
