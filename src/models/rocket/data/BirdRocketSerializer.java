package models.rocket.data;



import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import models.Measurement;
import models.rocket.Rocket;
import models.rocket.parts.CircularCylinder;
import models.rocket.parts.ConicalFrustum;
import models.rocket.parts.Motor;
import models.rocket.parts.NoseCone;
import models.rocket.parts.NoseShape;
import models.rocket.parts.Parachute;
import models.rocket.parts.RocketComponent;
import models.rocket.parts.TrapezoidFinSet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Serializes and deserializes a rocket for interoperability with other programs using the BIRD
 * file format.
 * @author Jacob
 */
public class BirdRocketSerializer implements IRocketSerializer {

  @Override
  public String getDefaultExtension()
  {
    return ".xml";
  }
  
  /**
   * Serializes a rocket to the specified output stream
   * @param rocket The rocket to serialize
   * @param outputStream The stream to write to
   * @throws Exception
   */
  @Override
    public void serialize(Rocket rocket, OutputStream outputStream) throws Exception {
    Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

    Element rootElement = document.createElement("Rocket");
    document.appendChild(rootElement);

    // Create the override element
    Element overrideElement = document.createElement("Override");
    rootElement.appendChild(overrideElement);

    for (String name : rocket.getOverrides().keySet()) {
      Measurement measurement = rocket.getOverrides().get(name);
      overrideElement.appendChild(createMeasurementElement(document, name, measurement));
    }

    // Create the interior element
    Element interiorElement = document.createElement("Interior");
    rootElement.appendChild(interiorElement);

    for (RocketComponent rocketComponent : rocket.getInteriorComponents()) {
      interiorElement.appendChild(createRocketComponentElement(document, rocketComponent));
    }

    // Create the exterior element
    Element exteriorElement = document.createElement("Exterior");
    rootElement.appendChild(exteriorElement);

    for (RocketComponent rocketComponent : rocket.getExteriorComponents()) {
      exteriorElement.appendChild(createRocketComponentElement(document, rocketComponent));
    }

    // Transorm the XML document into an XML stream
    Transformer transformer = TransformerFactory.newInstance().newTransformer();
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
    transformer.transform(new DOMSource(document), new StreamResult(outputStream));
  }

  private Element createMeasurementElement(Document document, String name, Measurement measurement) {
    Element measurementElement = document.createElement(name);
    measurementElement.setAttribute("Error", Double.toString(measurement.getError()));
    measurementElement.setNodeValue(Double.toString(measurement.getValue()));
    return measurementElement;
  }

  private Element createRocketComponentElement(Document document, RocketComponent component) throws Exception {
    Element element = null;

    // Add the part-specific measurements and quantities
    if (component instanceof CircularCylinder) {
      element = createCircularCylinderElement(document, (CircularCylinder) component);
    } else if (component instanceof ConicalFrustum) {
      element = createConicalFrustumElement(document, (ConicalFrustum) component);
    } else if (component instanceof Motor) {
      element = createMotorElement(document, (Motor) component);
    } else if (component instanceof NoseCone) {
      element = createNoseConeElement(document, (NoseCone) component);
    } else if (component instanceof Parachute) {
      element = createParachuteElement(document, (Parachute) component);
    } else if (component instanceof TrapezoidFinSet) {
      element = createTrapezoidFinSetElement(document, (TrapezoidFinSet) component);
    } else {
      throw new Exception(""); // TODO: replace with better exception
    }

    // Add the generic rocket component measurements to the new component element
    element.setAttribute("Name", component.getName());
    element.appendChild(createMeasurementElement(document, "AxialLength", component.getAxialLength()));
    element.appendChild(createMeasurementElement(document, "AxialOffset", component.getAxialOffset()));
    element.appendChild(createMeasurementElement(document, "Mass", component.getMass()));
    element.appendChild(createMeasurementElement(document, "RadialOffset", component.getRadialOffset()));
    element.appendChild(createMeasurementElement(document, "Thickness", component.getThickness()));

    return element;
  }

  private Element createCircularCylinderElement(Document document, CircularCylinder component) {
    Element element = document.createElement("CircularCylinder");
    element.appendChild(createMeasurementElement(document, "Diameter", component.getDiameter()));
    return element;
  }

  private Element createConicalFrustumElement(Document document, ConicalFrustum component) {
    Element element = document.createElement("ConicalFrustum");
    element.appendChild(createMeasurementElement(document, "UpperDiameter", component.getUpperDiameter()));
    element.appendChild(createMeasurementElement(document, "LowerDiameter", component.getLowerDiameter()));

    return element;
  }

  private Element createMotorElement(Document document, Motor component) {
    Element element = document.createElement("Motor");
    element.appendChild(createMeasurementElement(document, "Diameter", component.getDiameter()));
    return element;
  }

  private Element createNoseConeElement(Document document, NoseCone component) {
    Element element = document.createElement("NoseCone");
    Element noseShapeElement = document.createElement("NoseShape");
    noseShapeElement.setNodeValue(component.getNoseShape().toString());
    Element shapeParameterElement = document.createElement("ShapeParameter");
    shapeParameterElement.setNodeValue(Double.toString(component.getShapeParameter()));
    element.appendChild(noseShapeElement);
    element.appendChild(shapeParameterElement);
    element.appendChild(createMeasurementElement(document, "Diameter", component.getDiameter()));
    return element;
  }

  private Element createParachuteElement(Document document, Parachute component) {
    Element element = document.createElement("Parachute");
    element.appendChild(createMeasurementElement(document, "Diameter", component.getDiameter()));
    element.appendChild(createMeasurementElement(document, "DragCoefficient", component.getDragCoefficient()));
    element.appendChild(createMeasurementElement(document, "DeployedDiameter", component.getDeployedDiameter()));
    element.appendChild(createMeasurementElement(document, "DeploymentAltitude", component.getDeploymentAltitude()));
    return element;
  }

  private Element createTrapezoidFinSetElement(Document document, TrapezoidFinSet component) {
    Element element = document.createElement("TrapezoidFinSet");
    Element finCountElement = document.createElement("FinCount");
    finCountElement.setNodeValue(Integer.toString(component.getCount()));
    element.appendChild(finCountElement);
    element.appendChild(createMeasurementElement(document, "RootChord", component.getRootChord()));
    element.appendChild(createMeasurementElement(document, "TipChord", component.getTipChord()));
    element.appendChild(createMeasurementElement(document, "SpanLength", component.getSpanLength()));
    element.appendChild(createMeasurementElement(document, "SweepLength", component.getSweepLength()));
    element.appendChild(createMeasurementElement(document, "CantAngle", component.getCantAngle()));
    element.appendChild(createMeasurementElement(document, "BodyDiameter", component.getBodyDiameter()));
    return element;
  }

  /**
   * Deserializes a rocket from the specified input stream
   * @param stream The stream to read from
   * @return Returns the deserialized Rocket
   * @throws Exception
   */
    @Override
  public Rocket deserialize(InputStream stream) throws Exception {
    Rocket rocket = new Rocket();

    Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream);
    doc.normalize();

    if (!doc.getDocumentElement().getNodeName().equalsIgnoreCase("Rocket")) {
      throw new Exception("Invalid root node: " + doc.getDocumentElement().getNodeName());
    }

    // Load all of the override settings
    NodeList overrideElements = doc.getElementsByTagName("Override");

    for (int i = 0; i < overrideElements.getLength(); i++) {
      Element overrideElement = (Element) overrideElements.item(i);
      String overrideName = overrideElement.getLocalName();
      Measurement overrideMeasurement = loadMeasurementElement(overrideElement);
      rocket.getOverrides().put(overrideName, overrideMeasurement);
    }

    // Load all of the exterior settings
    NodeList exteriorElements = doc.getElementsByTagName("Exterior");

    for (int i = 0; i < exteriorElements.getLength(); i++) {
      Element exteriorElement = (Element) exteriorElements.item(i);
      RocketComponent exteriorComponent = loadComponentElement(exteriorElement);
      rocket.getExteriorComponents().add(exteriorComponent);
    }

    // Load all of the interior settings
    NodeList interiorNodes = doc.getElementsByTagName("Interior");

    for (int i = 0; i < interiorNodes.getLength(); i++) {
      Element interiorElement = (Element) interiorNodes.item(i);
      RocketComponent interiorComponent = loadComponentElement(interiorElement);
      rocket.getInteriorComponents().add(interiorComponent);
    }

    return rocket;
  }

  private String getElementValue(Element parentElement, String elementName) {
    return getElementValue(parentElement, elementName, "");
  }

  private String getElementValue(Element parentElement, String elementName, String defaultValue) {
    NodeList childElements = parentElement.getElementsByTagName(elementName);

    if (childElements.getLength() == 1) {
      return defaultValue;
    }

    return childElements.item(0).getNodeValue();
  }

  private Measurement loadMeasurementElement(Element element) {
    Measurement measurement = new Measurement();

    // Attempt to parse the value of the measurement
    measurement.setValue(Double.parseDouble(element.getNodeValue()));

    // Attempt to parse the error of the measurement
    String errorString = element.getAttribute("Error");

    if (errorString != null) {
      measurement.setError(Double.parseDouble(errorString));
    }

    return measurement;
  }

  private Measurement loadMeasurementElement(Element parentElement, String elementName) {
    NodeList childElements = parentElement.getElementsByTagName(elementName);

    if (childElements.getLength() != 1) {
      return new Measurement();
    }

    return loadMeasurementElement((Element) childElements.item(0));
  }

  private RocketComponent loadComponentElement(Element element) throws Exception {
    // Load the component properties specific to the component type
    String componentType = element.getLocalName();
    RocketComponent component = null;

    switch (componentType.toUpperCase()) {
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

  private RocketComponent loadNoseCone(Element element) {
    NoseCone noseCone = new NoseCone();
    String noseShapeString = getElementValue(element, "NoseShape");
    String noseParameterString = getElementValue(element, "NoseParameter", "0");
    noseCone.setNoseShape(NoseShape.valueOf(noseShapeString));
    
    noseCone.setShapeParameter(Double.parseDouble(noseParameterString));
    noseCone.setDiameter(loadMeasurementElement(element, "Diameter"));
    return noseCone;
  }

  private RocketComponent loadTrapezoidFinSet(Element element) {
    TrapezoidFinSet trapezoidFinSet = new TrapezoidFinSet();
    String finCountString = getElementValue(element, "Count", "0");
    trapezoidFinSet.setCount(Integer.parseInt(finCountString));
    trapezoidFinSet.setRootChord(loadMeasurementElement(element, "RootChord"));
    trapezoidFinSet.setTipChord(loadMeasurementElement(element, "TipChord"));
    trapezoidFinSet.setSpanLength(loadMeasurementElement(element, "SpanLength"));
    trapezoidFinSet.setSweepLength(loadMeasurementElement(element, "SweepLength"));
    trapezoidFinSet.setCantAngle(loadMeasurementElement(element, "CantAngle"));
    trapezoidFinSet.setBodyDiameter(loadMeasurementElement(element, "BodyDiameter"));
    return trapezoidFinSet;
  }

  private RocketComponent loadConicalFrustum(Element element) {
    ConicalFrustum conicalFrustum = new ConicalFrustum();
    conicalFrustum.setUpperDiameter(loadMeasurementElement(element, "UpperDiameter"));
    conicalFrustum.setLowerDiameter(loadMeasurementElement(element, "LowerDiameter"));
    return conicalFrustum;
  }

  private RocketComponent loadCircularCylinder(Element element) {
    CircularCylinder circularCylinder = new CircularCylinder();
    circularCylinder.setDiameter(loadMeasurementElement(element, "Diameter"));
    return circularCylinder;
  }

  private RocketComponent loadParachute(Element element) {
    Parachute parachute = new Parachute();
    parachute.setDragCoefficient(loadMeasurementElement(element, "DragCoefficient"));
    parachute.setDeployedDiameter(loadMeasurementElement(element, "DeployedDiameter"));
    parachute.setDeploymentAltitude(loadMeasurementElement(element, "DeploymentAltitude"));
    return parachute;
  }

  private RocketComponent loadMotor(Element element) {
    Motor motor = new Motor();
    return motor;
  }
}
