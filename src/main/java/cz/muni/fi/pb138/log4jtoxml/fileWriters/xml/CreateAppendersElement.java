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
import java.util.Properties;
import java.util.Set;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Class for child appender xml element.
 * @author Jakub
 */
public class CreateAppendersElement {
    /**
     * Static method for creating of appeneder xml child
     * 
     * @param document xml document
     * @param properties data
     * @return Element - appender
     */
    public static Element createAppendersElement(Document document, Properties properties) {
        Element appendersElement = document.createElement(XMLConst.APPENDERS);
        Set<String> propNames = properties.stringPropertyNames();
        for(String name : propNames) {
            if(!name.startsWith(Log4j2Constants.APPENDER)) {
                propNames.remove(name);
            }
        }
        while(!propNames.isEmpty()) {
            appendersElement.appendChild(createAppender(document, properties, getAppendersOfOneType(propNames)));
        }
        if(appendersElement.hasChildNodes()) {
            return appendersElement;
        }
        return null;
    }
    private static Element createAppender(Document document, Properties properties, Set<String> propNames) {
        String firstName = propNames.iterator().next();
        String[] splitName = firstName.split(".");
        String prefix = splitName[0]+"."+splitName[1];
        
        Element appendeer = document.createElement(properties.getProperty(prefix+".type"));
        appendeer.setAttribute("name", properties.getProperty(prefix+".name"));
        if(propNames.contains(prefix+".fileName")) {
            appendeer.setAttribute("fileName", properties.getProperty(prefix+".fileName"));
        }
        appendeer.appendChild(appenderLayout(document, properties, propNames, prefix));
        appendeer.appendChild(appenderFilters(document, properties, propNames, prefix));
        return appendeer;
    }
    private static Element appenderLayout(Document document, Properties properties, Set<String> propNames, String prefix) {
        if(propNames.contains(prefix+".layout.type")) {
            Element layout = document.createElement(properties.getProperty(prefix+".layout.type"));
            layout.setAttribute("pattern", properties.getProperty(prefix+"layout.pattern"));
            return layout;
        }
        return null;
    }
    private static Element appenderFilters(Document document, Properties properties, Set<String> propNames, String prefix) {
        Element filtersElement = document.createElement(XMLConst.FILTERS);
        Set<String> appenderFilters = getAppenderFilterPropNames(properties, prefix);
        while(!appenderFilters.isEmpty()) {
            filtersElement.appendChild(createAppendFilter(document, properties, appenderFilters));
        }
        if(filtersElement.hasChildNodes()) {
            return filtersElement;
        }
        return null;
    }

    private static Element createAppendFilter(Document document, Properties properties, Set<String> filterPropNames) {
        Element filterEl = document.createElement(XMLConst.FILTER);
        String[] splitSubName = filterPropNames.iterator().next().split(".");
        String filterPrefix = splitSubName[0]+"."+splitSubName[1]+"."+splitSubName[2];
        
        filterEl.setAttribute("type", properties.getProperty(filterPrefix+".type"));
        filterPropNames.remove(filterPrefix+".type");
        if(filterPropNames.contains(filterPrefix+".level")) {
            filterEl.setAttribute("level", properties.getProperty(filterPrefix+".level"));
            filterPropNames.remove(filterPrefix+".level");
        }
        if(filterPropNames.contains(filterPrefix+".marker")) {
            filterEl.setAttribute("marker", properties.getProperty(filterPrefix+".marker"));
            filterPropNames.remove(filterPrefix+".marker");
        }
        if(filterPropNames.contains(filterPrefix+".onMatch")) {
            filterEl.setAttribute("onMatch", properties.getProperty(filterPrefix+".onMatch"));
            filterPropNames.remove(filterPrefix+".onMatch");
        }
        if(filterPropNames.contains(filterPrefix+".onMismatch")) {
            filterEl.setAttribute("onMismatch", properties.getProperty(filterPrefix+".onMismatch"));
            filterPropNames.remove(filterPrefix+".onMismatch");
        }
        boolean containKeyPair = false;
        for(String s : filterPropNames) {
            if(s.startsWith(filterPrefix+".pair")) {
                containKeyPair = true;
                break;
            }
        }
        if(containKeyPair) {
            Element keyPair = document.createElement(properties.getProperty(filterPrefix+".pair.type"));
            filterPropNames.remove(filterPrefix+".pair.type");
            keyPair.setAttribute("key", properties.getProperty(filterPrefix+".pair.key"));
            filterPropNames.remove(filterPrefix+".pair.key");
            keyPair.setAttribute("value", properties.getProperty(filterPrefix+".pair.value"));
            filterPropNames.remove(filterPrefix+".pair.value");
            filterEl.appendChild(keyPair);
        }
        return filterEl;
    }
    
    private static Set<String> getAppendersOfOneType(Set<String> names) {
        if(names.isEmpty()) return Collections.EMPTY_SET;
        String firstName = names.iterator().next();
        String[] splitName = firstName.split(".");
        Set<String> out = new HashSet<>();
        for(String n : names) {
            if(n.startsWith(splitName[0]+"."+splitName[1])) {
                out.add(n);
            }
        }
        return out;
    }
    private static Set<String> getAppenderFilterPropNames(Properties properties, String prefix) {
        Set<String> names = new HashSet<>();
        for (String s : properties.stringPropertyNames()) {
            if(s.startsWith(prefix+".filter")) {
                names.add(s);
            }
        }
        return names;
    }
}