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
 * Class for child properties xml element.
 * @author Jakub
 */
public class CreatePropertiesElement {
    /**
     * Static method for creating of properties xml child
     * @param document xml
     * @param properties data
     * @return Elemen properties
     */
    public static Element createPropertiesElement(Document document, Properties properties) {
        Element propertiesElement = document.createElement(XMLConst.PROPERTIES);
        Set<String> propNames = properties.stringPropertyNames();
        for(String name : propNames) {
            if (name.startsWith(Log4j2Constants.PROPERTY)) {
                String propName = name.substring(Log4j2Constants.PROPERTY.length());
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
}
