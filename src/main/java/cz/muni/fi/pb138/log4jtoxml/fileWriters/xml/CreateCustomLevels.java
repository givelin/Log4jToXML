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
        Element customLevels = document.createElement(XMLConst.CUSTOM_LEVELS);
        Set<String> propNames = properties.stringPropertyNames();
        for(String name : propNames) {
            if(name.startsWith("customLevel")) {
                String[] split = name.split(".");
                String prefix = split[0]+"."+split[1];
                Element customLevel = document.createElement(XMLConst.CUSTOM_LEVEL);
            customLevel.setAttribute("name", properties.getProperty(prefix+".name"));
            propNames.remove(prefix+".name");
            customLevel.setAttribute("intLevel", properties.getProperty(prefix+".intLevel"));
            propNames.remove(prefix+".intLevel");
            customLevels.appendChild(customLevel);
            }
        }
        if(customLevels.hasChildNodes()) {
            return customLevels;
        }
        return null;
    }
}
