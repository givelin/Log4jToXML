/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml.fileWriters.xml;

import cz.muni.fi.pb138.log4jtoxml.constants.Log4j2Constants;
import cz.muni.fi.pb138.log4jtoxml.constants.XMLConst;
import java.util.Properties;
import java.util.Set;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Class for child filters xml element.
 * @author Jakub
 */
public class CreateFiltersElement {
    /**
     * Static method for filters of appeneder xml child
     * @param document xml
     * @param properties data
     * @return Element filters
     */    
    public static Element createFiltersElement(Document document, Properties properties) {
        Element filtersElement = document.createElement(XMLConst.FILTERS);
        Set<String> propNames = properties.stringPropertyNames();
        for(String name : propNames) {            
            /*
            if(name.startsWith(PropertiesConst.FILTER+"."+PropertiesConst.THRESHOLD)) {
                continue;
            }
            */
            if(!name.startsWith(Log4j2Constants.FILTER)) {
                continue;
            }
            
            String[] split = name.split("\\.");
            String filterType = split[1];
            String filterPrefix = Log4j2Constants.FILTER + "." + filterType;
            
            //find filters
            Element filter = document.createElement(XMLConst.FILTER);

            filter.setAttribute("type", filterType);
            //propNames.remove(filterPrefix + ".type");
            if (propNames.contains(filterPrefix + ".level")) {
                filter.setAttribute("level", properties.getProperty(filterPrefix + ".level"));
                //propNames.remove(filterPrefix + ".level");
            }
            if (propNames.contains(filterPrefix + ".marker")) {
                filter.setAttribute("marker", properties.getProperty(filterPrefix + ".marker"));
                //propNames.remove(filterPrefix + ".marker");
            }
            if (propNames.contains(filterPrefix + ".onMatch")) {
                filter.setAttribute("onMatch", properties.getProperty(filterPrefix + ".onMatch"));
                //propNames.remove(filterPrefix + ".onMatch");
            }
            if (propNames.contains(filterPrefix + ".onMismatch")) {
                filter.setAttribute("onMismatch", properties.getProperty(filterPrefix + ".onMismatch"));
                //propNames.remove(filterPrefix + ".onMismatch");
            }
            boolean containKeyPair = false;
            for (String s : propNames) {
                if (s.startsWith(filterPrefix + ".pair")) {
                    containKeyPair = true;
                    break;
                }
            }
            if (containKeyPair) {
                Element keyPair = document.createElement(properties.getProperty(filterPrefix + ".pair.type"));
                //propNames.remove(filterPrefix + ".pair.type");
                keyPair.setAttribute("key", properties.getProperty(filterPrefix + ".pair.key"));
               // propNames.remove(filterPrefix + ".pair.key");
                keyPair.setAttribute("value", properties.getProperty(filterPrefix + ".pair.value"));
                //propNames.remove(filterPrefix + ".pair.value");
                filter.appendChild(keyPair);
            }
            filtersElement.appendChild(filter);
        }
        if(filtersElement.hasChildNodes()) {
            return filtersElement;
        }
        return null;
    }
}
