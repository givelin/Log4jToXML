/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml.fileWriters.xml;

import cz.muni.fi.pb138.log4jtoxml.constants.PropertiesConst;
import cz.muni.fi.pb138.log4jtoxml.constants.XMLConst;
import java.util.Properties;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Jakub
 */
public class CreateThresholdFilterElement {
        public static Element createThresholdFilterElement(Document document,Properties properties) {
            String prefix = PropertiesConst.FILTER+"."+PropertiesConst.THRESHOLD;
            for(String name : properties.stringPropertyNames()) {
                if(name.startsWith(PropertiesConst.FILTER+"."+PropertiesConst.THRESHOLD)) {
                    Element thFilter = document.createElement(XMLConst.THRESHOL_FILTER);
                    if(properties.containsKey(prefix+".level")) {
                        thFilter.setAttribute("level", prefix+".level");
                    }
                    if(properties.containsKey(prefix+".onMatch")) {
                        thFilter.setAttribute("onMatch", prefix+".onMatch");
                    }
                    if(properties.containsKey(prefix+".onMismatch")) {
                        thFilter.setAttribute("onMismatch", prefix+".onMismatch");
                    }
                    return thFilter;
                }
            }    
        return null;
    }
}