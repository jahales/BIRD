package models.rocketmodel.data;



import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import models.Measurement;
import models.rocketmodel.Rocket;
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
import org.w3c.dom.NodeList;

/**
 *
 * @author Jacob
 */
public class FileSystemRocketRepository implements IRocketRepository {
  
  String folder;
  IRocketSerializer serializer;
  
  public FileSystemRocketRepository(String folder, IRocketSerializer serializer)
  {
    this.folder = folder;
    this.serializer = serializer;
  }

  /**
   *
   * @param rocket
   */
  @Override
  public void Create(Rocket rocket) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  /**
   *
   * @param id
   * @return
   */
  @Override
  public Rocket Retrieve(String id) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  /**
   *
   * @param rocket
   */
  @Override
  public void Update(Rocket rocket) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  /**
   *
   * @param rocket
   */
  @Override
  public void Delete(Rocket rocket) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}
