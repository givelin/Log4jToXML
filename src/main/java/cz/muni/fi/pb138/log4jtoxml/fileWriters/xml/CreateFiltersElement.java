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
public class CreateFiltersElement {
        
    public static Element createFiltersElement(Document document, Properties properties) {
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
}
