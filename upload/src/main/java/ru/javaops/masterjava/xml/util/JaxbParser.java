package ru.javaops.masterjava.xml.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.PropertyException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import org.xml.sax.SAXException;


/**
 * Marshalling/Unmarshalling JAXB helper
 * XML Facade
 */
public class JaxbParser {

  private static Map<Class, JaxbParser> singletonMap = new HashMap<>();
  private Class clazz;

  // thread-local pattern: one marshaller/unmarshaller instance per thread
  private ThreadLocal<JaxbMarshaller> marshallerThreadLocal = new ThreadLocal<>();
  private ThreadLocal<JaxbUnmarshaller> unmarshallerThreadLocal = new ThreadLocal<>();
  private ThreadLocal<Schema> schemaThreadLocal = new ThreadLocal<>();


  private JaxbParser(Class clazz) {
    this.clazz = clazz;
  }

  public static synchronized JaxbParser get(Class clazz) {
    JaxbParser jaxb = singletonMap.get(clazz);
    if (jaxb == null) {
      jaxb = new JaxbParser(clazz);
      singletonMap.put(clazz, jaxb);
    }
    return jaxb;
  }

  public JaxbMarshaller getMarshaller() throws JAXBException {
    JaxbMarshaller m = marshallerThreadLocal.get();
    if (m == null) {
      JAXBContext jc = JAXBContext.newInstance(clazz);
      m = new JaxbMarshaller(jc);
      marshallerThreadLocal.set(m);
    }
    return m;
  }

  public JaxbUnmarshaller getUnmarshaller() throws JAXBException {
    JaxbUnmarshaller um = unmarshallerThreadLocal.get();
    if (um == null) {
      JAXBContext jc = JAXBContext.newInstance(clazz);
      um = new JaxbUnmarshaller(jc);
      unmarshallerThreadLocal.set(um);
    }
    return um;
  }

  // Unmarshaller
  public <T> T unmarshal(InputStream is) throws JAXBException {
    return (T) getUnmarshaller().unmarshal(is);
  }

  public <T> T unmarshal(Reader reader) throws JAXBException {
    return (T) getUnmarshaller().unmarshal(reader);
  }

  public <T> T unmarshal(String str) throws JAXBException {
    return (T) getUnmarshaller().unmarshal(str);
  }

  public <T> T unmarshal(XMLStreamReader reader, Class<T> elementClass) throws JAXBException {
    return getUnmarshaller().unmarshal(reader, elementClass);
  }

  // Marshaller
  public void setMarshallerProperty(String prop, Object value) {
    try {
      getMarshaller().setProperty(prop, value);
    } catch (PropertyException e) {
      throw new IllegalArgumentException(e);
    } catch (JAXBException e) {
      e.printStackTrace();
    }
  }

  public String marshal(Object instance) throws JAXBException {
    return getMarshaller().marshal(instance);
  }

  public void marshal(Object instance, Writer writer) throws JAXBException {
    getMarshaller().marshal(instance, writer);
  }

  public void setSchema(Schema schema) throws JAXBException {

    schemaThreadLocal.set(schema);
    getUnmarshaller().setSchema(schema);
    getMarshaller().setSchema(schema);
  }

  public void validate(String str) throws IOException, SAXException {
    validate(new StringReader(str));
  }

  public void validate(Reader reader) throws IOException, SAXException {
    Schema schema = schemaThreadLocal.get();
    if (schema != null) {
      schema.newValidator().validate(new StreamSource(reader));
    }
  }
}
