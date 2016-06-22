/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml.fileWriters.xml;

import cz.muni.fi.pb138.log4jtoxml.constants.PropertiesConst;
import cz.muni.fi.pb138.log4jtoxml.constants.XMLConst;
import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Jakub
 */
public class CreateAppendersElement {
    public static Element createAppendersElement(Document document, Properties properties) {
    //TODO
        Element appendersElement = document.createElement(XMLConst.APPENDERS);
        Set<String> propNames = properties.stringPropertyNames();
        for(String name : propNames) {
            if(!name.startsWith(PropertiesConst.APPENDER)) {
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
}
