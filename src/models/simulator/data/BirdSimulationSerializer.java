package models.simulator.data;

import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import models.ISerializer;
import models.rocket.Rocket;
import models.rocket.data.XmlRocketSerializer;
import models.rocket.parts.CircularCylinder;
import models.rocket.parts.ConicalFrustum;
import models.rocket.parts.NoseCone;
import models.rocket.parts.RocketComponent;
import models.rocket.parts.TrapezoidFinSet;
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
    Rocket rocket = rocketSerializer.deserialize(null);

    for (RocketComponent component : rocket.getInteriorComponents()) {
      Element element = document.createElement("Menu");
      element.setAttribute("Type", "Part");
      rootElement.appendChild(element);

      if (component instanceof NoseCone) {
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
  }
   
  private void AddItem(Element parent, String name, double value, double error)
  {
    Element itemElement = parent.getOwnerDocument().createElement("Item");
    parent.appendChild(itemElement);
    itemElement.setAttribute("Name", name);
    itemElement.setAttribute("Value", Double.toString(value));
    itemElement.setAttribute("Error", Double.toString(error));
  }
  
  private void AddItem(Element parent, String name, String value, String error)
  {
    Element itemElement = parent.getOwnerDocument().createElement("Item");
    parent.appendChild(itemElement);
    itemElement.setAttribute("Name", name);
    itemElement.setAttribute("Value", value);
    itemElement.setAttribute("Error", error);
  }

  private void Serialize(Element element, String name, NoseCone c) {
    element.setAttribute("Name", name);
    AddItem(element, "Shape", "Ogive", "");
    AddItem(element, "Mass", c.getMass().getValue(), c.getMass().getError());
    AddItem(element, "Offset", c.getAxialOffset().getValue(), c.getAxialOffset().getError());
    AddItem(element, "Length", c.getAxialLength().getValue(), c.getAxialLength().getError());
    AddItem(element, "Diameter", c.getDiameter().getValue(), c.getDiameter().getError());
  }

  private void Serialize(Element element, String name, TrapezoidFinSet c) {
    element.setAttribute("Name", name);
    
   // double s = c.gets
//double midChord = c.getSweepLength() / c.
    
    AddItem(element, "Shape", "Trapezoid", "");
    AddItem(element, "Mass", c.getMass().getValue(), c.getMass().getError());
    AddItem(element, "Offset", c.getAxialOffset().getValue(), c.getAxialOffset().getError());
    AddItem(element, "Length", c.getAxialLength().getValue(), c.getAxialLength().getError());
    
    AddItem(element, "Count", Integer.toString(c.getCount()), "");
    AddItem(element, "Thickness", c.getThickness().getValue(), c.getThickness().getError());
    AddItem(element, "TipChord", c.getTipChord().getValue(), c.getTipChord().getError());
    AddItem(element, "MidChord", c.getAxialLength().getValue(), c.getAxialLength().getError());
    AddItem(element, "Span", c.getAxialLength().getValue(), c.getAxialLength().getError());
    AddItem(element, "TotalSpan", c.getAxialLength().getValue(), c.getAxialLength().getError());
    AddItem(element, "BodyDiameter", c.getAxialLength().getValue(), c.getAxialLength().getError());
    
    /*
    <Item Name="Shape" Value="Trapezoid" Description="" />
		<Item Name="Mass" Value="0.143" Error="0.001" Description="" />
		<Item Name="Offset" Value="1.75" Error="0.001" Description="" />		
		<Item Name="Length" Value="0.2" Error="0.001" Description="" />
		<Item Name="Count" Value="4" Description="" />
		<Item Name="Thickness" Value="0.003" Error="0.001" Description="" />
		<Item Name="TipChord" Value="0.15" Error="0.001" Description="" />
		<Item Name="MidChord" Value="0.142077" Error="0.001" Description="" />
		<Item Name="Span" Value="0.1" Error="0.001" Description="" />
		<Item Name="TotalSpan" Value="0.225" Error="0.001" Description="" />
		<Item Name="BodyDiameter" Value="0.08" Error="0.001" Description="" />
    */
  }

  private void Serialize(Element element, String name, ConicalFrustum component) {
  }

  private void Serialize(Element element, String name, CircularCylinder component) {
  }

  @Override
  public Simulation deserialize(InputStream inputStream) throws Exception {
    return null;
  }

}
