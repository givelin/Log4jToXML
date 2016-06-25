/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml.fileWriters.xml;

import cz.muni.fi.pb138.log4jtoxml.constants.Log4j2Constants;
import cz.muni.fi.pb138.log4jtoxml.constants.XMLConst;
import java.util.Properties;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Class for child threshold filter xml element.
 * @author Jakub
 */
public class CreateThresholdFilterElement {
    /**
     * Static method for creating of threshold filter xml child
     * @param document xml
     * @param properties data
     * @return Element threshodl filter
     */
    public static Element createThresholdFilterElement(Document document,Properties properties) {
            String prefix = Log4j2Constants.FILTER+"."+Log4j2Constants.THRESHOLD;
            for(String name : properties.stringPropertyNames()) {
                if(name.startsWith(Log4j2Constants.FILTER+"."+Log4j2Constants.THRESHOLD)) {
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