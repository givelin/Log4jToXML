/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml.fileWriters.xml;

import cz.muni.fi.pb138.log4jtoxml.constants.XMLConst;
import java.util.Properties;
import java.util.Set;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Jakub
 */
public class CreateCustomLevels {
    public static Element createCustomLevels(Document document, Properties properties) {
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
}
