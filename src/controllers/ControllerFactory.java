package controllers;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Callback;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import models.rocket.Rocket;

/**
 *
 * @author Jacob
 */
public class ControllerFactory implements Callback<Class<?>, Object> {

  final static Logger logger = Logger.getLogger(ControllerFactory.class.getName());
  Map<Class<?>, Object> typeInstanceMap = new HashMap<>();

  @Override
  public Object call(Class<?> param) {
    return resolveInstance(param);
  }

  public void addSingleton(Object instance) {
    typeInstanceMap.put(instance.getClass(), instance);
  }

  public void addSingletons(Object[] instances) {
    for (Object singleton : instances) {
      addSingleton(singleton);
    }
  }

  private Object resolveInstance(Class<?> param) {
    // Sort the constructors by the maximum number of parameters
    List<Constructor> constructors = new ArrayList<>();
    constructors.addAll(Arrays.asList(param.getConstructors()));
    Collections.sort(constructors, (Constructor o1, Constructor o2) -> {
      return Integer.compare(o1.getParameterCount(), o2.getParameterCount());
    });
    Collections.reverse(constructors);

    // Attempt to resolveInstance at least one constructor
    for (Constructor constructor : constructors) {
      Object instance = resolveConstructor(constructor);

      if (instance != null) {
        return instance;
      }
    }

    return null;
  }

  private Object resolveConstructor(Constructor constructor) {
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
