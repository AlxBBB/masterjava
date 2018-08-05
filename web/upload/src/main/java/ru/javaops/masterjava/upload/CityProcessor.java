package ru.javaops.masterjava.upload;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import ru.javaops.masterjava.persist.DBIProvider;
import ru.javaops.masterjava.persist.dao.CityDao;
import ru.javaops.masterjava.persist.model.City;
import ru.javaops.masterjava.xml.schema.ObjectFactory;
import ru.javaops.masterjava.xml.util.JaxbParser;
import ru.javaops.masterjava.xml.util.StaxStreamProcessor;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class CityProcessor {
    private static final JaxbParser jaxbParser = new JaxbParser(ObjectFactory.class);
    private static CityDao cityDao = DBIProvider.getDao(CityDao.class);


    public int process(final InputStream is) throws XMLStreamException, JAXBException {
        log.info("Start processing load cities");

        List<City> cities = new ArrayList<>();
        val processor = new StaxStreamProcessor(is);
        val unmarshaller = jaxbParser.createUnmarshaller();

        int count = 0;
        while (processor.doUntil(XMLEvent.START_ELEMENT, "City")) {
            ru.javaops.masterjava.xml.schema.CityType xmlCity = unmarshaller.unmarshal(processor.getReader(), ru.javaops.masterjava.xml.schema.CityType.class);
            final City city = new City(xmlCity.getValue(), xmlCity.getId());
            if (cityDao.insert(city) > 0) {
                count++;
            }
        }


        return count;
    }


}
