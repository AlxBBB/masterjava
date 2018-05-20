package ru.javaops.masterjava.xml.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;

public class StaxStreamProcessor implements AutoCloseable {

  private static final XMLInputFactory FACTORY = XMLInputFactory.newInstance();

  private final XMLStreamReader reader;

  public StaxStreamProcessor(InputStream is) throws XMLStreamException {
    reader = FACTORY.createXMLStreamReader(is);
  }

  public XMLStreamReader getReader() {
    return reader;
  }

  public boolean doUntil(int stopEvent, String value) throws XMLStreamException {
    while (reader.hasNext()) {
      int event = reader.next();
      if (event == stopEvent) {
        if (value.equals(getValue(event))) {
          return true;
        }
      }
    }
    return false;
  }

  public String getValue(int event) throws XMLStreamException {
    return (event == XMLEvent.CHARACTERS) ? reader.getText() : reader.getLocalName();
  }

  public String getElementValue(String element) throws XMLStreamException {
    return doUntil(XMLEvent.START_ELEMENT, element) ? reader.getElementText() : null;
  }

  public String getText() throws XMLStreamException {
    return reader.getElementText();
  }

  public String getIdByText(String element, String text) throws XMLStreamException {
    if (element == null || text == null) {
      return null;
    }
    while (doUntil(XMLEvent.START_ELEMENT, element)) {
      String id = reader.getAttributeValue(null, "id");
      if (text.equals(reader.getElementText())) {
        return id;
      }
    }
    return null;
  }

  @Override
  public void close() {
    if (reader != null) {
      try {
        reader.close();
      } catch (XMLStreamException e) {
        // empty
      }
    }
  }
}
