/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml.impl.fileWriters;

import cz.muni.fi.pb138.log4jtoxml.constants.XMLConst;
import java.io.File;
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
    
    public static void writeData(File output, Properties properties) throws TransformerConfigurationException {
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
            Set<Element> rootChilds = setConfigurationChild();
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
    
    private static Set<Element> setConfigurationChild() {
        Set<Element> childs = new HashSet<>();
        childs.add(createCustomLevels());
        childs.add(createPropertiesElement());
        childs.add(createFiltersElement());
        childs.add(createThresholdFilterElement());
        childs.add(createAppendersElement());
        childs.add(createLoggersElement());
        return childs;
    }
    
    private static Element createCustomLevels() {
    //TODO
        return null;
    }
    private static Element createPropertiesElement() {
    //TODO
        return null;
    }
    private static Element createFiltersElement() {
    //TODO
        return null;
    }
    private static Element createThresholdFilterElement() {
    //TODO
        return null;
    }
    private static Element createAppendersElement() {
    //TODO
        return null;
    }
    private static Element createLoggersElement() {
    //TODO
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
