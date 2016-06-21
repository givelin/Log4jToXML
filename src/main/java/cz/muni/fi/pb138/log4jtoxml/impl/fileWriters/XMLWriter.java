/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml.impl.fileWriters;

import cz.muni.fi.pb138.log4jtoxml.constants.PropertiesConst;
import cz.muni.fi.pb138.log4jtoxml.constants.XMLConst;
import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Jakub
 */
public class XMLWriter {
    //class for writing data into file
    private static Document document;
    
    public static void writeData(File output, Properties properties) throws TransformerConfigurationException {
        Set<String> propNames = properties.stringPropertyNames();
            
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        
        try {
            builder = factory.newDocumentBuilder();
            document = builder.newDocument();
            Element mainRootElement = document.createElement(XMLConst.CONFIGURATION);
            //saying that root element of document is Configuration
            document.appendChild(mainRootElement);
            
            //here set for mainRootElement attributes
            setConfigurationAttributes(properties, mainRootElement);
            //set child for main root
            Set<Element> rootChilds = setConfigurationChild(properties);
            //add childs to main root
            for(Element element : rootChilds) {
                if(element!=null) {
                    mainRootElement.appendChild(element);
                }
            }

            //Document to XML file
            /* 
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
            DOMSource source = new DOMSource(doc);
            StreamResult console = new StreamResult(System.out);
            transformer.transform(source, console);
            System.out.println("\nXML DOM Created Successfully..");
            */
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLWriter.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
    private static Set<Element> setConfigurationChild(Properties properties) {
        Set<Element> childs = new HashSet<>();
        childs.add(createCustomLevels(properties));
        childs.add(createPropertiesElement(properties));
        childs.add(createFiltersElement(properties));
        childs.add(createThresholdFilterElement(properties));
        childs.add(createAppendersElement(properties));
        childs.add(createLoggersElement(properties));
        return childs;
    }
    
    private static Element createCustomLevels(Properties properties) {
    //TODO
        Element customLevels = document.createElement(XMLConst.CUSTOM_LEVELS);
        Set<String> propNames = properties.stringPropertyNames();
        for(String name : propNames) {
            //find customLevel
            Element customLevel = document.createElement(XMLConst.CUSTOM_LEVEL);
            customLevel.setAttribute("name", name);//add
            customLevel.setAttribute("intLevel", name);//add
            customLevels.appendChild(customLevel);
        }
        if(customLevels.hasChildNodes()) {
            return customLevels;
        }
        return null;
    }
    
    private static Element createPropertiesElement(Properties properties) {
        Element propertiesElement = document.createElement(XMLConst.PROPERTIES);
        Set<String> propNames = properties.stringPropertyNames();
        for(String name : propNames) {
            if (name.startsWith(PropertiesConst.PROPERTY)) {
                String propName = name.substring(PropertiesConst.PROPERTY.length());
                Element propElement = document.createElement(XMLConst.PROPERTY);
                propElement.setAttribute("name", propName);
                propElement.appendChild(document.createTextNode(properties.getProperty(name)));
                propertiesElement.appendChild(propElement);
            }
        }
        if(propertiesElement.hasChildNodes()) {
            return propertiesElement;
        }
        return null;
    }
    
    private static Element createFiltersElement(Properties properties) {
    //TODO
        Element filtersElement = document.createElement(XMLConst.FILTERS);
        Set<String> propNames = properties.stringPropertyNames();
        for(String name : propNames) {
            //find filters
            Element filter = document.createElement(XMLConst.FILTER);
            //add atributes
            filtersElement.appendChild(filter);
        }
        if(filtersElement.hasChildNodes()) {
            return filtersElement;
        }
        return null;
    }
    private static Element createThresholdFilterElement(Properties properties) {
    //TODO
        return null;
    }
    private static Element createAppendersElement(Properties properties) {
    //TODO
        Element appendersElement = document.createElement(XMLConst.APPENDERS);
        Set<String> propNames = properties.stringPropertyNames();
        for(String name : propNames) {
            if(!name.startsWith(PropertiesConst.APPENDER)) {
                propNames.remove(name);
            }
        }
        while(!propNames.isEmpty()) {
            appendersElement.appendChild(createAppender(properties, getAppendersOfOneType(propNames)));
        }
        if(appendersElement.hasChildNodes()) {
            return appendersElement;
        }
        return null;
    }
    private static Element createAppender(Properties properties, Set<String> propNames) {
        String firstName = propNames.iterator().next();
        String[] splitName = firstName.split(".");
        String prefix = splitName[0]+"."+splitName[1];
        
        Element appendeer = document.createElement(properties.getProperty(prefix+".type"));
        appendeer.setAttribute("name", properties.getProperty(prefix+".name"));
        if(propNames.contains(prefix+".fileName")) {
            appendeer.setAttribute("fileName", properties.getProperty(prefix+".fileName"));
        }
        appendeer.appendChild(appenderLayout());
        appendeer.appendChild(appenderFilters());
        //maybe more...
        return appendeer;
    }
    private static Element appenderLayout() {
    //TODO
        return null;
    }
    private static Element appenderFilters() {
    //TODO
        return null;
    }
    private static Set<String> getAppendersOfOneType(Set<String> names) {
        if(names.isEmpty()) return Collections.EMPTY_SET;
        String firstName = names.iterator().next();
        String[] splitName = firstName.split(".");
        Set<String> out = new HashSet<>();
        for(String n : names) {
            if(n.startsWith(splitName[0]+"."+splitName[1])) {
                out.add(n);
                names.remove(n);
            }
        }
        return out;
    }

    private static Element createLoggersElement(Properties properties) {
    //TODO
        Element loggersElement = document.createElement(XMLConst.LOGGERS);
        Set<String> propNames = properties.stringPropertyNames();
        for(String name : propNames) {
            if(!name.startsWith(PropertiesConst.LOGGER)) {
                propNames.remove(name);
            }
        }
        //same as appender
        if(loggersElement.hasChildNodes()) {
            return loggersElement;
        }
        return null;
    }
    
    private static void setConfigurationAttributes(Properties properties, Element configuration) {
        for(String attributeName : XMLConst.configAttributes){
            if(properties.contains(attributeName)) {
                configuration.setAttribute(attributeName, properties.getProperty(attributeName));
            }
        }
    }
}
