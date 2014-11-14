package models.rocket;

import models.rocket.data.FileSystemRocketRepository;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Measurement;
import models.rocket.parts.CircularCylinder;
import models.rocket.parts.RocketComponent;
import org.testng.Assert;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Jacob
 */
public class XmlRocketRepositoryTest {

  private Rocket loadXmlString(String s)
  { 
    /*try {
      FileSystemRocketRepository repository = new FileSystemRocketRepository();
      return repository.load(new ByteArrayInputStream(s.getBytes()));
    } catch (Exception ex) {
      return null;
    }*/
    
    return null;
  }
  
  private String buildRocketXmlString(String override, String exterior, String interior)
  {
    StringBuilder sb = new StringBuilder();
    sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<Rocket>");
    
    sb.append("<Override>");
    sb.append(override);
    sb.append("</Override>");
    
    sb.append("<Interior>");
    sb.append(interior);
    sb.append("</Interior>");
    
    sb.append("<Exterior>");
    sb.append(exterior);
    sb.append("</Exterior>");
    
    sb.append("</Rocket>");
    return sb.toString();
  }
  
  @Test
  public void load_fails_onInvalidSchema() {

  }
  
  @Test
  public void load_reads_emptyElement() {
    // Arrange
    //
    
    String override = "";
    String exterior = "";
    String interior = "";
    String xml = buildRocketXmlString(override, exterior, interior);
    
    // Act
    //
    
    Rocket rocket = loadXmlString(xml);
    
    // Assert
    //
    
    Assert.assertEquals(rocket != null, true);
//    Assert.assertEquals(rocket.getComponents().size(), 0);
    Assert.assertEquals(rocket.getOverrides().size(), 0);
  }
  
  @Test
  public void load_readsOverrideElements() throws Exception {
    // Arrange
    //
    
    String override = "<Mass Error=\"10\">20</Mass><CenterOfMass Error=\"30\">40</CenterOfMass>";
    String exterior = "";
    String interior = "";
    String xml = buildRocketXmlString(override, exterior, interior);
    
    // Act
    //
    
    Rocket rocket = loadXmlString(xml);
    Measurement mass = rocket.getOverrides().get("Mass");
    Measurement centerOfMass = rocket.getOverrides().get("CenterOfMass");
    
    // Assert
    //
    
    Assert.assertEquals(mass.getValue(), 20);
    Assert.assertEquals(mass.getError(), 10);
    Assert.assertEquals(centerOfMass.getValue(), 40);
    Assert.assertEquals(centerOfMass.getError(), 30);
  }
  
    @Test
  public void load_reads_genericComponentProperties() {
    // Arrange
    //
    
    String override = "";
    String exterior = "<CircularCylinder Name=\"Body\">\n" +
      "			<Mass Error=\"1\">7</Mass>\n" +
      "			<AxialLength Error=\"2\">8</AxialLength>\n" +
      "			<Diameter Error=\"3\">9</Diameter>	\n" +
      "			<Thickness Error=\"4\">10</Thickness>\n" +
      "			<AxialOffset Error=\"5\">11</AxialOffset>\n" +
      "			<RadialOffset Error=\"6\">12</RadialOffset>\n" +
      "		</CircularCylinder>";
    String interior = "";
    String xml = buildRocketXmlString(override, exterior, interior);
    
    // Act
    //
    
    Rocket rocket = loadXmlString(xml);
    RocketComponent c = rocket.getExteriorComponents().get(0);
    
    // Assert
    //
    
    Assert.assertEquals(c.getName(), "Body");
    Assert.assertEquals(c.getMass().getValue(), 7);
    Assert.assertEquals(c.getMass().getError(), 1);
    Assert.assertEquals(c.getAxialLength().getValue(), 8);
    Assert.assertEquals(c.getAxialLength().getError(), 2);
    Assert.assertEquals(c.getAxialOffset().getValue(), 7);
    Assert.assertEquals(c.getAxialOffset().getError(), 11);
    Assert.assertEquals(c.getRadialOffset().getValue(), 12);
    Assert.assertEquals(c.getRadialOffset().getError(), 6);    
    Assert.assertEquals(c.getThickness().getValue(), 4);
    Assert.assertEquals(c.getThickness().getError(), 10);
  }
  
  @Test
  public void load_reads_circularCylinderElement() {
    // Arrange
    //
    
    String override = "";
    String exterior = "<CircularCylinder Name=\"Body\">\n" +
      "			<Mass Error=\"1\">7</Mass>\n" +
      "			<Length Error=\"2\">8</Length>\n" +
      "			<Diameter Error=\"3\">9</Diameter>	\n" +
      "			<Thickness Error=\"4\">10</Thickness>\n" +
      "			<AxialOffset Error=\"5\">11</AxialOffset>\n" +
      "			<RadialOffset Error=\"6\">12</RadialOffset>\n" +
      "		</CircularCylinder>";
    String interior = "";
    String xml = buildRocketXmlString(override, exterior, interior);
    
    // Act
    //
    
    Rocket rocket = loadXmlString(xml);
    CircularCylinder c = (CircularCylinder)rocket.getExteriorComponents().get(0);
    
    // Assert
    //
    
    Assert.assertEquals(c.getName(), "Body");
    Assert.assertEquals(c.getMass().getValue(), 7);
    Assert.assertEquals(c.getMass().getError(), 1);
    Assert.assertEquals(c.getAxialLength().getValue(), 7);
    Assert.assertEquals(c.getAxialLength().getError(), 1);
    Assert.assertEquals(c.getDiameter().getValue(), 7);
    Assert.assertEquals(c.getDiameter().getError(), 1);
    Assert.assertEquals(c.getThickness().getValue(), 7);
    Assert.assertEquals(c.getThickness().getError(), 1);
    Assert.assertEquals(c.getMass().getValue(), 7);
    Assert.assertEquals(c.getMass().getError(), 1);
  }
  
  @Test
  public void load_reads_conicalFrustumElement() {

  }
  
  @Test
  public void load_reads_motorElement() {

  }
  
  @Test
  public void load_reads_noseConeElement() {

  }
  
  @Test
  public void load_reads_parachuteElement() {

  }
  
  @Test
  public void load_reads_trapezoidFinSetElement() {

  }
}
