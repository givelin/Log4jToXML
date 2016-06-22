/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml.fileWriters.xml;

import cz.muni.fi.pb138.log4jtoxml.constants.PropertiesConst;
import cz.muni.fi.pb138.log4jtoxml.constants.XMLConst;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Jakub
 */
public class CreateLoggersElement {
    public static Element createLoggersElement(Document document, Properties properties) {
        Element loggersElement = document.createElement(XMLConst.LOGGERS);
        Set<String> propNames = properties.stringPropertyNames();
        for(String name : propNames) {
            if(!name.startsWith(PropertiesConst.LOGGER)) {
                propNames.remove(name);
            }
        }
        
        //same as appender
        while(!propNames.isEmpty()) {
            loggersElement.appendChild(createLoggerElement(document, propNames, properties));
        }

        loggersElement.appendChild(createRootLoggerElement(document, properties));
        if(loggersElement.hasChildNodes()) {
            return loggersElement;
        }
        return null;
    }
    private static Element createLoggerElement(Document document, Set<String> propNames, Properties properties) {
        Element loggerEl = document.createElement(XMLConst.LOGGER);
        String firstName = propNames.iterator().next();
        String[] splitName = firstName.split(".");
        String prefix = splitName[0]+"."+splitName[1];
        //set atributes
        loggerEl.setAttribute("name", properties.getProperty(prefix+".name"));
        propNames.remove(prefix+".name");
        if(propNames.contains(prefix+".level")) {
            loggerEl.setAttribute("level", properties.getProperty(prefix+".level"));
            propNames.remove(prefix+".level");
        }
        if(propNames.contains(prefix+".additivity")) {
            loggerEl.setAttribute("additivity", properties.getProperty(prefix+".additivity"));
            propNames.remove(prefix+".additivity");
        }
        //create child filters and create child appender ref        
        loggerEl.appendChild(createLoggerFilters(document, properties, prefix));
        for(String n : propNames) {
            if(n.startsWith(prefix+".appenderRef")) {
                propNames.remove(n);
            }
        }
        loggerEl.appendChild(createAppenderRef(document, properties, prefix));
        return loggerEl;
    }
    private static Element createRootLoggerElement(Document document, Properties properties) {
        Element rootLogger = document.createElement(XMLConst.ROOT);
        rootLogger.setAttribute("level", properties.getProperty(PropertiesConst.ROOT_LOGGER+".level"));
        //add AppendeerRef
        rootLogger.appendChild(createAppenderRef(document, properties, PropertiesConst.ROOT_LOGGER));
        return rootLogger;
    }
    
    private static Element createAppenderRef(Document document, Properties properties, String prefix) {
        Element appnederRef = document.createElement(XMLConst.APPENDE_REF);
        String name = null;
        for(String n : properties.stringPropertyNames()) {
            if(n.startsWith(prefix+".appenderRef")) {
                name = n;
                break;
            }
        }
        if(name!=null) {
            appnederRef.setAttribute("ref", properties.getProperty(name));
            
            return appnederRef;
        }
        return null;
    }
    
    private static Element createLoggerFilters(Document document, Properties properties, String prefix) {
        Element filtersElement = document.createElement(XMLConst.FILTERS);
        Set<String> loggerFilters = getLoggerFilterPropNames(properties, prefix);
        while(!loggerFilters.isEmpty()) {
            filtersElement.appendChild(createLogFilter(document, properties, loggerFilters));
        }
        if(filtersElement.hasChildNodes()) {
        return filtersElement;
        }
        return null;
    }
    private static Element createLogFilter(Document document, Properties properties, Set<String> filterPropNames) {
        Element filterEl = document.createElement(XMLConst.FILTER);
        String[] splitSubName = filterPropNames.iterator().next().split(".");
        //String filterTag = splitSubName[3];
        String filterPrefix = splitSubName[0]+"."+splitSubName[1]+"."+splitSubName[2]+"."+splitSubName[3];
        
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
    
    private static Set<String> getLoggerFilterPropNames(Properties properties, String prefix) {
        Set<String> names = new HashSet<>();
        for (String s : properties.stringPropertyNames()) {
            if(s.startsWith(prefix+".filter")) {
                names.add(s);
            }
        }
        return names;
    }
}
