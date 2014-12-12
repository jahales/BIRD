package models.simulator.data;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import models.ISerializer;
import models.Measurement;
import models.Unit;
import models.rocket.Rocket;
import models.rocket.data.XmlRocketSerializer;
import models.rocket.parts.CircularCylinder;
import models.rocket.parts.ConicalFrustum;
import models.rocket.parts.Motor;
import models.rocket.parts.NoseCone;
import models.rocket.parts.Parachute;
import models.rocket.parts.RocketComponent;
import models.rocket.parts.TrapezoidFinSet;
import models.simulator.LaunchRail;
import models.simulator.Simulation;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Jacob
 */
public class BirdSimulationSerializer implements ISerializer<Simulation> {

  @Override
  public void serialize(Simulation o, OutputStream outputStream) throws Exception {
    Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

    Element rootElement = document.createElement("Simulation");
    document.appendChild(rootElement);

    // Write the rocket components
    ISerializer<Rocket> rocketSerializer = new XmlRocketSerializer();
    Rocket rocket = rocketSerializer.deserialize(new FileInputStream(o.getRocketFile()));

    SerializeAtmosphere(rootElement, o.getLaunchRail());
    Serialize(rootElement, o.getAtmosphereFile());

    Element settingsElement = document.createElement("Menu");
    rootElement.appendChild(settingsElement);
    settingsElement.setAttribute("Name", "Settings");
    settingsElement.setAttribute("Type", "Setting");
    AddItem(settingsElement, "OutputFileName", o.getOutputFile());

    for (RocketComponent component : rocket.getInteriorComponents()) {
      Element element = document.createElement("Menu");
      element.setAttribute("Type", "Part");
      rootElement.appendChild(element);

      if (component instanceof Motor) {
        Serialize(element, "Motor", (Motor) component);
      } else if (component instanceof Parachute) {
        Serialize(element, "Parachute", (Parachute) component);
      } else if (component instanceof NoseCone) {
        Serialize(element, component.getName(), (NoseCone) component);
      } else if (component instanceof TrapezoidFinSet) {
        Serialize(element, component.getName(), (TrapezoidFinSet) component);
      } else if (component instanceof ConicalFrustum) {
        Serialize(element, component.getName(), (ConicalFrustum) component);
      } else if (component instanceof CircularCylinder) {
        Serialize(element, component.getName(), (CircularCylinder) component);
      }
    }

    for (RocketComponent component : rocket.getExteriorComponents()) {
      Element element = document.createElement("Menu");
      element.setAttribute("Type", "Part");
      rootElement.appendChild(element);

      if (component instanceof NoseCone) {
        Serialize(element, "Nose", (NoseCone) component);
      } else if (component instanceof TrapezoidFinSet) {
        Serialize(element, "Fins", (TrapezoidFinSet) component);
      } else if (component instanceof ConicalFrustum) {
        Serialize(element, "Tail", (ConicalFrustum) component);
      } else if (component instanceof CircularCylinder) {
        Serialize(element, "Body", (CircularCylinder) component);
      }
    }

    // Transorm the XML document into an XML stream
    Transformer transformer = TransformerFactory.newInstance().newTransformer();
    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
    transformer.transform(new DOMSource(document), new StreamResult(outputStream));
  }

  private void AddItem(Element parent, String name, String value) {
    Element itemElement = parent.getOwnerDocument().createElement("Item");
    parent.appendChild(itemElement);
    itemElement.setAttribute("Name", name);
    itemElement.setAttribute("Value", value);
  }

  private void AddMeasurement(Element parent, String name, Measurement measurement) {
    Element itemElement = parent.getOwnerDocument().createElement("Item");
    parent.appendChild(itemElement);
    itemElement.setAttribute("Name", name);
    itemElement.setAttribute("Value", Double.toString(measurement.getValue()));
    itemElement.setAttribute("Error", Double.toString(measurement.getError()));
  }

  private void SerializeComponent(Element element, String name, String shape, RocketComponent c) {
    element.setAttribute("Name", name);
    AddItem(element, "Shape", shape);
    AddMeasurement(element, "Mass", c.getMass());
    AddMeasurement(element, "Offset", c.getAxialOffset());
    AddMeasurement(element, "Length", c.getAxialLength());
    AddMeasurement(element, "Thickness", c.getThickness());
  }

  private void Serialize(Element element, String name, NoseCone c) {
    SerializeComponent(element, name, "Ogive", c);
    AddMeasurement(element, "Diameter", c.getDiameter());
  }

  private void Serialize(Element motorElement, String name, Motor c) {
    Document document = motorElement.getOwnerDocument();
    Element rootElement = (Element) motorElement.getParentNode();
    Element fuelElement = document.createElement("Menu");
    fuelElement.setAttribute("Type", "Part");
    rootElement.appendChild(fuelElement);

    Element thrustElement = document.createElement("Menu");
    thrustElement.setAttribute("Name", "Thrust");
    thrustElement.setAttribute("Type", "Setting");
    rootElement.appendChild(thrustElement);
    AddItem(thrustElement, "ThrustFile", c.getThrustFile());

    Measurement motorMass = c.getMass();
    Measurement fuelMass = c.getFuelMass();
    motorMass.setValue(c.getMass().getValue() - c.getFuelMass().getValue());

    c.setMass(motorMass);
    Serialize(motorElement, "Motor", (CircularCylinder) c);

    c.setMass(fuelMass);
    Serialize(fuelElement, "Fuel", (CircularCylinder) c);
  }

  private void Serialize(Element parachuteElement, String name, Parachute c) {
    Document document = parachuteElement.getOwnerDocument();
    Element rootElement = (Element) parachuteElement.getParentNode();
    Element settingsElement = document.createElement("Menu");
    settingsElement.setAttribute("Type", "Setting");
    rootElement.appendChild(settingsElement);

    Serialize(parachuteElement, "Parachute", (CircularCylinder) c);

    double r = c.getDiameter().getValue() / 2.0;
    Measurement ap = new Measurement(Math.PI * r * r, c.getDiameter().getError(), Unit.other);
    AddMeasurement(settingsElement, "Cd", c.getDragCoefficient());
    AddMeasurement(settingsElement, "Ap", ap);
    settingsElement.setAttribute("Name", "Parachute");
  }

  private void Serialize(Element element, String name, TrapezoidFinSet c) {
    SerializeComponent(element, name, "Trapezoid", c);
    AddItem(element, "Count", Integer.toString(c.getCount()));
    AddMeasurement(element, "TipChord", c.getTipChord());
    AddMeasurement(element, "MidChord", c.getAxialLength()); // TODO: fix me
    AddMeasurement(element, "Span", c.getAxialLength());
    AddMeasurement(element, "TotalSpan", c.getAxialLength());
    AddMeasurement(element, "BodyDiameter", c.getAxialLength());
  }

  private void Serialize(Element element, String name, ConicalFrustum c) {
    SerializeComponent(element, name, "ConicalFrustum", c);
    AddMeasurement(element, "UpperDiameter", c.getUpperDiameter());
    AddMeasurement(element, "LowerDiameter", c.getLowerDiameter());
  }

  private void Serialize(Element element, String name, CircularCylinder c) {
    SerializeComponent(element, name, "Cylinder", c);
    AddMeasurement(element, "InnerDiameter", new Measurement(0, 0, Unit.meters));
    AddMeasurement(element, "OuterDiameter", c.getDiameter());
  }

  private void Serialize(Element rootElement, String atmosphere) {
    Element e = rootElement.getOwnerDocument().createElement("Menu");
    rootElement.appendChild(e);
    e.setAttribute("Name", "Environment");
    e.setAttribute("Type", "Setting");
    AddMeasurement(e, "Elevation", new Measurement(0, 0, Unit.other));
    AddMeasurement(e, "Temperature", new Measurement(0, 0, Unit.other));
    AddMeasurement(e, "Wx", new Measurement(0, 0, Unit.other));
    AddMeasurement(e, "Wy", new Measurement(0, 0, Unit.other));
    AddMeasurement(e, "Wz", new Measurement(0, 0, Unit.other));
  }

  private void SerializeAtmosphere(Element rootElement, LaunchRail l) {
    Element e = rootElement.getOwnerDocument().createElement("Menu");
    rootElement.appendChild(e);
    e.setAttribute("Name", "LaunchRail");
    e.setAttribute("Type", "Setting");
    AddMeasurement(e, "Length", l.getLength());
    AddMeasurement(e, "Cf", new Measurement(0, 0, Unit.other));
    AddMeasurement(e, "Ux", new Measurement(0, 0, Unit.other));
    AddMeasurement(e, "Uy", new Measurement(0, 0, Unit.other));
    AddMeasurement(e, "Uz", new Measurement(1, 0, Unit.other));
  }

  @Override
  public Simulation deserialize(InputStream inputStream) throws Exception {
    return null;
  }
}
