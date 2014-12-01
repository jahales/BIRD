package controllers;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Callback;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

/**
 * Factory class for creating new controllers. Constructor injection is used to share class
 * instances among controllers created with a factory instance.
 *
 * @author Jacob
 */
public class ControllerFactory implements Callback<Class<?>, Object> {

  final static Logger logger = Logger.getLogger(ControllerFactory.class.getName());
  Map<Class<?>, Object> typeInstanceMap = new HashMap<>();

  /**
   * Creates a new controller for the specified controller class type.
   *
   * @param param The class type of the controller to create.
   * @return An initialized instance of the specified class type.
   */
  @Override
  public Object call(Class<?> param) {
    return resolveInstance(param);
  }

  public IController load(String url) throws IOException {
    return load(url, null);
  }

  public IController load(String url, Object[] sharedInstances) throws IOException {
    addSharedInstances(sharedInstances);
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(ControllerFactory.class.getResource(url));
    loader.setControllerFactory(this);
    Node view = (Node) loader.load();
    IController controller = (IController) loader.getController();
    controller.setView(view);
    return controller;
  }

  /**
   * Adds a class instance to be shared with all controllers created with this factory instance.
   *
   * @param instance A class instance to be shared.
   */
  public void addSharedInstance(Object instance) {
    for (Class<?> c = instance.getClass(); c != null; c = c.getSuperclass()) {
      typeInstanceMap.put(c, instance);
    }
  }

  /**
   * Adds class instances to be shared with all controllers created with this factory instance.
   *
   * @param instances Class instances to be shared.
   */
  public void addSharedInstances(Object[] instances) {
    if (instances == null) {
      return;
    }

    for (Object singleton : instances) {
      addSharedInstance(singleton);
    }
  }

  /**
   * Creates an instance of the specified class type, resolving constructor dependencies as needed.
   *
   * @param param The class type to resolve.
   * @return A new instance of the specified class type, or null if it could not be resolved.
   */
  private Object resolveInstance(Class<?> param) {
    // Sort the constructors by the maximum number of parameters
    List<Constructor<?>> constructors = new ArrayList<>();
    constructors.addAll(Arrays.asList(param.getConstructors()));
    Collections.sort(constructors, (Constructor<?> o1, Constructor<?> o2) -> {
      return Integer.compare(o1.getParameterCount(), o2.getParameterCount());
    });
    Collections.reverse(constructors);

    // Attempt to resolveInstance for at least one constructor
    for (Constructor<?> constructor : constructors) {
      Object instance = resolveConstructor(constructor);

      if (instance != null) {
        return instance;
      }
    }

    return null;
  }

  /**
   * Attempts to create an object instance by resolving the parameters for one of its constructors.
   *
   * @param constructor The constructor to resolve.
   * @return A new instance created with the constructor, or null if it could not be resolved.
   */
  private Object resolveConstructor(Constructor<?> constructor) {
    // Resolve each of the parameters
    Class<?>[] types = constructor.getParameterTypes();
    List<Object> parameters = new ArrayList<>();

    for (Class<?> type : types) {
      parameters.add(resolveParameter(type));
    }

    // Create the controller with the specified arguments
    try {
      return constructor.newInstance(parameters.toArray());
    } catch (Exception ex) {
      logger.throwing(null, null, ex);
      return null;
    }
  }

  /**
   * Attempts to resolve the specified class type by either using a shared instance or creating a
   * new instance.
   *
   * @param type The class type to resolve.
   * @return Returns a new or shared instance if possible, otherwise null.
   */
  private Object resolveParameter(Class<?> type) {
    Object instance = typeInstanceMap.get(type);

    if (instance == null) {
      try {
        instance = type.newInstance();
      } catch (Exception ex) {
        logger.throwing(null, null, ex);
        instance = null;
      }
    }

    return instance;
  }
}
