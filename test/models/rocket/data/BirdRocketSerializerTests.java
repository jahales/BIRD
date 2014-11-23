package models.rocket.data;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import models.Measurement;
import models.Unit;
import models.rocket.Rocket;
import models.rocket.parts.CircularCylinder;
import models.rocket.parts.ConicalFrustum;
import models.rocket.parts.NoseCone;
import models.rocket.parts.Parachute;
import models.rocket.parts.RocketComponent;
import models.rocket.parts.TrapezoidFinSet;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 *
 * @author Jacob
 */
public class BirdRocketSerializerTests {

  private Rocket loadXmlString(String s) throws Exception {
    BirdRocketSerializer serializer = new BirdRocketSerializer();
    return serializer.deserialize(new ByteArrayInputStream(s.getBytes()));
  }

  private String buildRocketXmlString(String override, String exterior, String interior) {
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

  private Boolean saveAndValidate(Rocket r1) throws Exception {
    IRocketSerializer serializer = new BirdRocketSerializer();

    // Serialize the rocket
    ByteArrayOutputStream outputStream1 = new ByteArrayOutputStream();
    serializer.serialize(r1, outputStream1);
    String result1 = outputStream1.toString().replaceAll("\\s+","");

    // Deserialize the rocket to see if it serialized correctly
    ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream1.toByteArray());
    Rocket r2 = serializer.deserialize(inputStream);

    // Reserialize the rocket for comparison
    ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream();
    serializer.serialize(r2, outputStream2);
    String result2 = outputStream2.toString().replaceAll("\\s+","");

    return result1.equalsIgnoreCase(result2);
  }

  @Test
  public void load_fails_onInvalidSchema() {

  }

  @Test
  public void load_reads_emptyElement() throws Exception {
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
    Assert.assertEquals(rocket.getInteriorComponents().size(), 0);
    Assert.assertEquals(rocket.getExteriorComponents().size(), 0);
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
    Assert.assertEquals(mass.getValue(), 20.0);
    Assert.assertEquals(mass.getError(), 10.0);
    Assert.assertEquals(centerOfMass.getValue(), 40.0);
    Assert.assertEquals(centerOfMass.getError(), 30.0);
  }

  @Test
  public void load_reads_genericComponentProperties() throws Exception {
    // Arrange
    //

    String override = "";
    String exterior = "<CircularCylinder Name=\"Body\">\n"
        + "			<Mass Error=\"1\">7</Mass>\n"
        + "			<AxialLength Error=\"2\">8</AxialLength>\n"
        + "			<Diameter Error=\"3\">9</Diameter>	\n"
        + "			<Thickness Error=\"4\">10</Thickness>\n"
        + "			<AxialOffset Error=\"5\">11</AxialOffset>\n"
        + "			<RadialOffset Error=\"6\">12</RadialOffset>\n"
        + "		</CircularCylinder>";
    String interior = "";
    String xml = buildRocketXmlString(override, exterior, interior);

    // Act
    //
    Rocket rocket = loadXmlString(xml);
    RocketComponent c = rocket.getExteriorComponents().get(0);

    // Assert
    //
    Assert.assertEquals(c.getName(), "Body");
    Assert.assertEquals(c.getMass().getValue(), 7.0);
    Assert.assertEquals(c.getMass().getError(), 1.0);
    Assert.assertEquals(c.getAxialLength().getValue(), 8.0);
    Assert.assertEquals(c.getAxialLength().getError(), 2.0);
    Assert.assertEquals(c.getAxialOffset().getValue(), 11.0);
    Assert.assertEquals(c.getAxialOffset().getError(), 5.0);
    Assert.assertEquals(c.getRadialOffset().getValue(), 12.0);
    Assert.assertEquals(c.getRadialOffset().getError(), 6.0);
    Assert.assertEquals(c.getThickness().getValue(), 10.0);
    Assert.assertEquals(c.getThickness().getError(), 4.0);
  }

  @Test
  public void load_reads_circularCylinderElement() throws Exception {
    // Arrange
    //

    String override = "";
    String exterior = "<CircularCylinder Name=\"Body\">\n"
        + "			<Mass Error=\"1\">7</Mass>\n"
        + "			<Length Error=\"2\">8</Length>\n"
        + "			<Diameter Error=\"3\">9</Diameter>	\n"
        + "			<Thickness Error=\"4\">10</Thickness>\n"
        + "			<AxialOffset Error=\"5\">11</AxialOffset>\n"
        + "			<RadialOffset Error=\"6\">12</RadialOffset>\n"
        + "		</CircularCylinder>";
    String interior = "";
    String xml = buildRocketXmlString(override, exterior, interior);

    // Act
    //
    Rocket rocket = loadXmlString(xml);
    CircularCylinder c = (CircularCylinder) rocket.getExteriorComponents().get(0);

    // Assert
    //
    Assert.assertEquals(c.getName(), "Body");
    Assert.assertEquals(c.getMass().getValue(), 7.0);
    Assert.assertEquals(c.getMass().getError(), 1.0);
    Assert.assertEquals(c.getDiameter().getValue(), 9.0);
    Assert.assertEquals(c.getDiameter().getError(), 3.0);
    Assert.assertEquals(c.getThickness().getValue(), 10.0);
    Assert.assertEquals(c.getThickness().getError(), 4.0);
    Assert.assertEquals(c.getMass().getValue(), 7.0);
    Assert.assertEquals(c.getMass().getError(), 1.0);
  }

  @Test
  public void load_reads_conicalFrustumElement() throws Exception {
    // Arrange
    //

    String override = "";
    String exterior = "<ConicalFrustum Name=\"BoatTail\">\n"
        + "			<UpperDiameter Error=\"1\">7</UpperDiameter>\n"
        + "			<LowerDiameter Error=\"2\">8</LowerDiameter>\n"
        + "		</ConicalFrustum>";
    String interior = "";
    String xml = buildRocketXmlString(override, exterior, interior);

    // Act
    //
    Rocket rocket = loadXmlString(xml);
    ConicalFrustum c = (ConicalFrustum) rocket.getExteriorComponents().get(0);

    // Assert
    //
    Assert.assertEquals(c.getUpperDiameter().getValue(), 7.0);
    Assert.assertEquals(c.getUpperDiameter().getError(), 1.0);
    Assert.assertEquals(c.getLowerDiameter().getValue(), 8.0);
    Assert.assertEquals(c.getLowerDiameter().getError(), 2.0);
  }

  @Test
  public void load_reads_motorElement() {

  }

  @Test
  public void load_reads_noseConeElement() throws Exception {
    // Arrange
    //

    String override = "";
    String exterior = "<NoseCone Name=\"Nose\">\n"
        + "     <NoseParameter>11</NoseParameter>"
        + "			<Diameter Error=\"1\">7</Diameter>\n"
        + "			<NoseShape>Ogive</NoseShape>\n"
        + "		</NoseCone>";
    String interior = "";
    String xml = buildRocketXmlString(override, exterior, interior);

    // Act
    //
    Rocket rocket = loadXmlString(xml);
    NoseCone c = (NoseCone) rocket.getExteriorComponents().get(0);

    // Assert
    //
    Assert.assertEquals(c.getShapeParameter(), 11.0);
    Assert.assertEquals(c.getDiameter().getValue(), 7.0);
    Assert.assertEquals(c.getDiameter().getError(), 1.0);
    Assert.assertEquals(c.getNoseShape(), NoseCone.NoseShape.OGIVE);
  }

  @Test
  public void load_reads_parachuteElement() throws Exception {
    // Arrange
    //

    String override = "";
    String exterior = "";
    String interior = "<Parachute Name=\"Parachute\">\n"
        + "     <DeployAtApogee>True</DeployAtApogee>"
        + "			<DragCoefficient Error=\"1\">4</DragCoefficient>\n"
        + "			<DeployedDiameter Error=\"2\">5</DeployedDiameter>\n"
        + "			<DeploymentAltitude Error=\"3\">6</DeploymentAltitude>\n"
        + "		</Parachute>";
    String xml = buildRocketXmlString(override, exterior, interior);

    // Act
    //
    Rocket rocket = loadXmlString(xml);
    Parachute c = (Parachute) rocket.getInteriorComponents().get(0);

    // Assert
    //
    Assert.assertEquals(c.getDeployAtApogee(), (Boolean) true);
    Assert.assertEquals(c.getDragCoefficient().getValue(), 4.0);
    Assert.assertEquals(c.getDragCoefficient().getError(), 1.0);
    Assert.assertEquals(c.getDeployedDiameter().getValue(), 5.0);
    Assert.assertEquals(c.getDeployedDiameter().getError(), 2.0);
    Assert.assertEquals(c.getDeploymentAltitude().getValue(), 6.0);
    Assert.assertEquals(c.getDeploymentAltitude().getError(), 3.0);
  }

  @Test
  public void load_reads_trapezoidFinSetElement() throws Exception {
    // Arrange
    //

    String override = "";
    String interior = "";
    String exterior = "<TrapezoidFinSet Name=\"FinSet\">\n"
        + "     <Count>4</Count>"
        + "			<RootChord Error=\"1\">11</RootChord>\n"
        + "			<TipChord Error=\"2\">22</TipChord>\n"
        + "			<SpanLength Error=\"3\">33</SpanLength>\n"
        + "			<SweepLength Error=\"4\">44</SweepLength>\n"
        + "			<CantAngle Error=\"5\">55</CantAngle>\n"
        + "			<BodyDiameter Error=\"6\">66</BodyDiameter>\n"
        + "		</TrapezoidFinSet>";
    String xml = buildRocketXmlString(override, exterior, interior);

    // Act
    //
    Rocket rocket = loadXmlString(xml);
    TrapezoidFinSet c = (TrapezoidFinSet) rocket.getExteriorComponents().get(0);

    // Assert
    //
    Assert.assertEquals(c.getCount(), 4);
    Assert.assertEquals(c.getRootChord().getValue(), 11.0);
    Assert.assertEquals(c.getRootChord().getError(), 1.0);
    Assert.assertEquals(c.getTipChord().getValue(), 22.0);
    Assert.assertEquals(c.getTipChord().getError(), 2.0);
    Assert.assertEquals(c.getSpanLength().getValue(), 33.0);
    Assert.assertEquals(c.getSpanLength().getError(), 3.0);
    Assert.assertEquals(c.getSweepLength().getValue(), 44.0);
    Assert.assertEquals(c.getSweepLength().getError(), 4.0);
    Assert.assertEquals(c.getCantAngle().getValue(), 55.0);
    Assert.assertEquals(c.getCantAngle().getError(), 5.0);
    Assert.assertEquals(c.getBodyDiameter().getValue(), 66.0);
    Assert.assertEquals(c.getBodyDiameter().getError(), 6.0);
  }

  @Test
  public void load_loads_multipleComponents() throws Exception {
    // Arrange
    //

    String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n"
        + "<Rocket>\n"
        + "  <Override/>\n"
        + "  <Interior/>\n"
        + "  <Exterior>\n"
        + "    <CircularCylinder Name=\"\">\n"
        + "      <Diameter Error=\"0.0\">0.0</Diameter>\n"
        + "      <AxialLength Error=\"0.0\">0.0</AxialLength>\n"
        + "      <AxialOffset Error=\"0.0\">0.0</AxialOffset>\n"
        + "      <Mass Error=\"0.0\">0.0</Mass>\n"
        + "      <RadialOffset Error=\"0.0\">0.0</RadialOffset>\n"
        + "      <Thickness Error=\"0.0\">0.0</Thickness>\n"
        + "    </CircularCylinder>\n"
        + "    <NoseCone Name=\"\">\n"
        + "      <NoseShape>OGIVE</NoseShape>\n"
        + "      <ShapeParameter>0.0</ShapeParameter>\n"
        + "      <Diameter Error=\"0.0\">0.0</Diameter>\n"
        + "      <AxialLength Error=\"0.0\">0.0</AxialLength>\n"
        + "      <AxialOffset Error=\"0.0\">0.0</AxialOffset>\n"
        + "      <Mass Error=\"0.0\">0.0</Mass>\n"
        + "      <RadialOffset Error=\"0.0\">0.0</RadialOffset>\n"
        + "      <Thickness Error=\"0.0\">0.0</Thickness>\n"
        + "    </NoseCone>\n"
        + "  </Exterior>\n"
        + "</Rocket>";

    // Act
    //
    Rocket rocket = loadXmlString(xml);

    // Assert
    //
    Assert.assertEquals(rocket.getExteriorComponents().size(), 2);
  }

  @Test
  public void save_saves_emptyRocket() throws Exception {
    // Arrange
    //

    Rocket r = new Rocket();

    // Act
    //
    Boolean result = saveAndValidate(r);

    // Assert
    //
    Assert.assertEquals(result, (Boolean) true);
  }

  @Test
  public void save_saves_rocketWithOverrides() throws Exception {
    // Arrange
    //

    Rocket r = new Rocket();
    r.getOverrides().put("Mass", new Measurement(10, 1, Unit.kilograms));
    r.getOverrides().put("CenterOfMass", new Measurement(10, 1, Unit.meters));

    // Act
    //
    Boolean result = saveAndValidate(r);

    // Assert
    //
    Assert.assertEquals(result, (Boolean) true);
  }

  @Test
  public void save_saves_rocketWithInteriorParts() throws Exception {
    // Arrange
    //

    Rocket r = new Rocket();
    r.getInteriorComponents().add(new CircularCylinder());
    r.getInteriorComponents().add(new Parachute());

    // Act
    //
    Boolean result = saveAndValidate(r);

    // Assert
    //
    Assert.assertEquals(result, (Boolean) true);
  }

  @Test
  public void save_saves_rocketWithExteriorParts() throws Exception {
    // Arrange
    //

    Rocket r = new Rocket();
    r.getExteriorComponents().add(new CircularCylinder());
    r.getExteriorComponents().add(new TrapezoidFinSet());
    r.getExteriorComponents().add(new NoseCone());

    // Act
    //
    Boolean result = saveAndValidate(r);

    // Assert
    //
    Assert.assertEquals(result, (Boolean) true);
  }
}
