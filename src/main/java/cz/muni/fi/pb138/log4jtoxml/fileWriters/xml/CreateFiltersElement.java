/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml.fileWriters.xml;

import cz.muni.fi.pb138.log4jtoxml.constants.Log4j2Constants;
import cz.muni.fi.pb138.log4jtoxml.constants.XMLConst;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
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
        Iterator<String> it = propNames.iterator();
        
        while(it.hasNext()) {
            String name = it.next();
            if(!name.startsWith(Log4j2Constants.FILTER) ||
                    name.startsWith(Log4j2Constants.FILTER+"."+Log4j2Constants.THRESHOLD)) {
                it.remove();
            }
        }
        
        while(!propNames.isEmpty()) {
            filtersElement.appendChild(createFilter(document, properties, getFiltersOfOneType(propNames)));
        }
        
        if(filtersElement.hasChildNodes()) {
            return filtersElement;
        }
        return null;
    }
    
    private static Element createFilter(Document document, Properties properties, Set<String> propNames) {
        Element filter = document.createElement(XMLConst.FILTER);
        String firstName = propNames.iterator().next();
        String[] splitName = firstName.split("\\.");
        String filterPrefix = splitName[0]+"."+splitName[1];
        

        filter.setAttribute("type", properties.getProperty(filterPrefix + ".type"));
        if (propNames.contains(filterPrefix + ".level")) {
            filter.setAttribute("level", properties.getProperty(filterPrefix + ".level"));
        }
        if (propNames.contains(filterPrefix + ".marker")) {
            filter.setAttribute("marker", properties.getProperty(filterPrefix + ".marker"));
        }
        if (propNames.contains(filterPrefix + ".onMatch")) {
            filter.setAttribute("onMatch", properties.getProperty(filterPrefix + ".onMatch"));
        }
        if (propNames.contains(filterPrefix + ".onMismatch")) {
            filter.setAttribute("onMismatch", properties.getProperty(filterPrefix + ".onMismatch"));
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
        return filter;
    }
    
    private static Set<String> getFiltersOfOneType(Set<String> names) {
        if(names.isEmpty()) return Collections.EMPTY_SET; //should never happen
        Iterator<String> it = names.iterator();
        String firstName = it.next();        
        String[] splitName = firstName.split("\\.");
        Set<String> out = new HashSet<>();
        
        it = names.iterator(); //reset iterator
        while(it.hasNext()) {
            String n = it.next();
            if(n.startsWith(splitName[0]+"."+splitName[1])) {
                out.add(n);
                it.remove();
            }
        }
        return out;
    }
}
