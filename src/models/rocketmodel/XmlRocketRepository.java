package models.rocketmodel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import models.Measurement;
import models.rocketmodel.parts.CircularCylinder;
import models.rocketmodel.parts.ConicalFrustum;
import models.rocketmodel.parts.Motor;
import models.rocketmodel.parts.NoseCone;
import models.rocketmodel.parts.NoseShape;
import models.rocketmodel.parts.Parachute;
import models.rocketmodel.parts.RocketComponent;
import models.rocketmodel.parts.TrapezoidFinSet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Jacob
 */
public class XmlRocketRepository implements IRocketRepository {

  @Override
  public void Create(Rocket rocket) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Rocket Retrieve(String id) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void Update(Rocket rocket) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public void Delete(Rocket rocket) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
  
  public Rocket load(InputStream stream) throws Exception {
    Rocket rocket = new Rocket();
    
    Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream);
    doc.normalize();
    
    if (!doc.getDocumentElement().getNodeName().equalsIgnoreCase("Rocket")) {
      throw new Exception("Invalid root node: " + doc.getDocumentElement().getNodeName());
    }
    
    // Load all of the override settings
    NodeList overrideElements = doc.getElementsByTagName("Override");
    
    for (int i = 0; i < overrideElements.getLength(); i++) {
      Element overrideElement = (Element)overrideElements.item(i);
      String overrideName = overrideElement.getLocalName();
      Measurement overrideMeasurement = loadMeasurementElement(overrideElement);
      rocket.getOverrides().put(overrideName, overrideMeasurement);
    }
    
    // Load all of the exterior settings
    NodeList exteriorElements = doc.getElementsByTagName("Exterior");
    
    for (int i = 0; i < exteriorElements.getLength(); i++) {
      Element exteriorElement = (Element)exteriorElements.item(i);
      RocketComponent exteriorComponent = loadComponentElement(exteriorElement);
      rocket.getExteriorComponents().add(exteriorComponent);
    }
    
    // Load all of the interior settings
    NodeList interiorNodes = doc.getElementsByTagName("Interior");
    
    for (int i = 0; i < interiorNodes.getLength(); i++) {
      Element interiorElement = (Element)interiorNodes.item(i);
      RocketComponent interiorComponent = loadComponentElement(interiorElement);
      rocket.getInteriorComponents().add(interiorComponent);
    }
    
    return rocket;
  }
  
  private String getElementValue(Element parentElement, String elementName)
  {
    return getElementValue(parentElement, elementName, "");
  }
  
  private String getElementValue(Element parentElement, String elementName, String defaultValue)
  {
    NodeList childElements = parentElement.getElementsByTagName(elementName);
    
    if (childElements.getLength() == 1)
    {
      return defaultValue;
    }
    
    return childElements.item(0).getNodeValue();
  }
  
  private Measurement loadMeasurementElement(Element element)
  {
    Measurement measurement = new Measurement();
    
    // Attempt to parse the value of the measurement
    measurement.setValue(Double.parseDouble(element.getNodeValue()));
    
    // Attempt to parse the error of the measurement
    String errorString = element.getAttribute("Error");
    
    if (errorString != null)
    {
      measurement.setError(Double.parseDouble(errorString));
    }
    
    return measurement;
  }
  
  private Measurement loadMeasurementElement(Element parentElement, String elementName) 
  {
    NodeList childElements = parentElement.getElementsByTagName(elementName); 
    
    if (childElements.getLength() != 1)
    {
      return new Measurement();
    }
    
    return loadMeasurementElement((Element)childElements.item(0));
  }
  
  private RocketComponent loadComponentElement(Element element) throws Exception
  {   
    // Load the component properties specific to the component type
    String componentType = element.getLocalName();
    RocketComponent component = null;
    
    switch (componentType.toUpperCase())
    {
      case "NOSECONE":
        component = loadNoseCone(element);
        break;
      case "TRAPEZOIDFINSET":
        component = loadTrapezoidFinSet(element);   
        break;
      case "CONICALFRUSTUM":
        component = loadConicalFrustum(element);
        break;
      case "CIRCULARCYLINDER":
        component = loadCircularCylinder(element);
        break;
      case "PARACHUTE":
        component = loadParachute(element);
        break;
      case "MOTOR":
        component = loadMotor(element);
        break;
      default:
        return null; // TODO: make this better
    }
    
    // Load the component properties common to all components
    component.setName(element.getAttribute("Name"));
    component.setMass(loadMeasurementElement(element, "Mass"));
    component.setAxialOffset(loadMeasurementElement(element, "AxialOffset"));
    component.setRadialOffset(loadMeasurementElement(element, "RadialOffset"));
    component.setAxialLength(loadMeasurementElement(element, "AxialLength"));
    component.setThickness(loadMeasurementElement(element, "Thickness"));
    
    return component;
  }
  
  private RocketComponent loadNoseCone(Element element)
  {
    NoseCone noseCone = new NoseCone();
    String noseShapeString = getElementValue(element, "NoseShape");
    String noseParameterString = getElementValue(element, "NoseParameter", "0");    
    noseCone.setNoseShape(NoseShape.parseString(noseShapeString));
    noseCone.setShapeParameter(Double.parseDouble(noseParameterString));
    noseCone.setDiameter(loadMeasurementElement(element, "Diameter"));
    return noseCone;    
  }
  
  private RocketComponent loadTrapezoidFinSet(Element element)
  {
    TrapezoidFinSet trapezoidFinSet = new TrapezoidFinSet();
    return trapezoidFinSet;
  }
  
  private RocketComponent loadConicalFrustum(Element element)
  {
    ConicalFrustum conicalFrustum = new ConicalFrustum();
    return conicalFrustum;
  }
  
  private RocketComponent loadCircularCylinder(Element element)
  {
    CircularCylinder circularCylinder = new CircularCylinder();
    return circularCylinder;
  }
  
  private RocketComponent loadParachute(Element element)
  {
    Parachute parachute = new Parachute();
    return parachute;
  }
  
  private RocketComponent loadMotor(Element element)
  {
    Motor motor = new Motor();
    return motor;
  }
}
