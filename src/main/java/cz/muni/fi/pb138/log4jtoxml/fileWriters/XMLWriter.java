/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml.fileWriters;

import cz.muni.fi.pb138.log4jtoxml.constants.XMLConst;
import cz.muni.fi.pb138.log4jtoxml.fileWriters.xml.CreateAppendersElement;
import cz.muni.fi.pb138.log4jtoxml.fileWriters.xml.CreateCustomLevels;
import cz.muni.fi.pb138.log4jtoxml.fileWriters.xml.CreateFiltersElement;
import cz.muni.fi.pb138.log4jtoxml.fileWriters.xml.CreateLoggersElement;
import cz.muni.fi.pb138.log4jtoxml.fileWriters.xml.CreatePropertiesElement;
import cz.muni.fi.pb138.log4jtoxml.fileWriters.xml.CreateThresholdFilterElement;
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
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Class dor writitn in properties data into xml 
 * @author Jakub
 */
public class XMLWriter {
    //class for writing data into file
    private static Document document;
    
    /**
     * Class for creatin XML documet into file
     * @param output File where are data saved.
     * @param properties data input for xml document.
     */
    public static void writeData(File output, Properties properties) {
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
            //add childs to main root
            for (Element element : setConfigurationChild(properties)) {
                if (element != null) {
                    mainRootElement.appendChild(element);
                }
            }
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(output);
            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);
            transformer.transform(source, result);
            System.out.println("File saved!");
        } catch (ParserConfigurationException | TransformerException ex) {
            ex.printStackTrace();
            Logger.getLogger(XMLWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static Set<Element> setConfigurationChild(Properties properties) {
        Set<Element> childs = new HashSet<>();
        childs.add(CreateCustomLevels.createCustomLevels(document, properties));
        childs.add(CreatePropertiesElement.createPropertiesElement(document, properties));
        childs.add(CreateFiltersElement.createFiltersElement(document, properties));
        childs.add(CreateThresholdFilterElement.createThresholdFilterElement(document, properties));
        childs.add(CreateAppendersElement.createAppendersElement(document, properties));
        childs.add(CreateLoggersElement.createLoggersElement(document, properties));
        return childs;
    }

    private static void setConfigurationAttributes(Properties properties, Element configuration) {
        for (String attributeName : XMLConst.configAttributes) {
            if (properties.containsKey(attributeName)) {
                if (properties.getProperty(attributeName) == null) {
                    continue;
                }
                configuration.setAttribute(attributeName, properties.getProperty(attributeName));
            }
        }
    }
}
