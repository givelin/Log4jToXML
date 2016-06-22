/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml.fileWriters.xml;

import cz.muni.fi.pb138.log4jtoxml.constants.PropertiesConst;
import cz.muni.fi.pb138.log4jtoxml.constants.XMLConst;
import java.util.Properties;
import java.util.Set;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Jakub
 */
public class CreatePropertiesElement {
    public static Element createPropertiesElement(Document document, Properties properties) {
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
}
