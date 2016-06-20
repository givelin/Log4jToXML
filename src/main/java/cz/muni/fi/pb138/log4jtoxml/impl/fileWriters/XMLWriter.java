/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml.impl.fileWriters;

import cz.muni.fi.pb138.log4jtoxml.constants.XMLConst;
import java.io.File;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Jakub
 */
public class XMLWriter {
    //class for writing data into file
    
    public static void writeData(File output, Properties properties) {
        Set<String> propNames = properties.stringPropertyNames();
            
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            Element mainRootElement = document.createElement(XMLConst.CONFIGURATION);
            //saying that root element of document is Configuration
            document.appendChild(mainRootElement);
            
            //here set for mainRootElement attributes
            setConfigurationAttributes(properties, mainRootElement);
            //set child for main root
            
            
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private static void setConfigurationAttributes(Properties properties, Element configuration) {
        for(String attributeName : XMLConst.configAttributes){
            if(properties.contains(attributeName)) {
                configuration.setAttribute(attributeName, properties.getProperty(attributeName));
            }
        }
    }
}
