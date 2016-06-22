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
public class CreateLoggersElement {
    public static Element createLoggersElement(Document document, Properties properties) {
    //TODO
        Element loggersElement = document.createElement(XMLConst.LOGGERS);
        Set<String> propNames = properties.stringPropertyNames();
        for(String name : propNames) {
            if(!name.startsWith(PropertiesConst.LOGGER)) {
                propNames.remove(name);
            }
        }
        
        //same as appender
        while(!propNames.isEmpty()) {
            loggersElement.appendChild(createLoggerElement(document, propNames, properties));
        }

        loggersElement.appendChild(createRootLoggerElement(document, properties));
        if(loggersElement.hasChildNodes()) {
            return loggersElement;
        }
        return null;
    }
    private static Element createLoggerElement(Document document, Set<String> propNames, Properties properties) {
    //TODO
        Element loggerEl = document.createElement(XMLConst.LOGGER);
        String firstName = propNames.iterator().next();
        String[] splitName = firstName.split(".");
        String prefix = splitName[0]+"."+splitName[1];
        //set atributes
        loggerEl.setAttribute("name", properties.getProperty(prefix+".name"));
        propNames.remove(prefix+".name");
        if(propNames.contains(prefix+".level")) {
            loggerEl.setAttribute("level", properties.getProperty(prefix+".level"));
            propNames.remove(prefix+".level");
        }
        if(propNames.contains(prefix+".additivity")) {
            loggerEl.setAttribute("additivity", properties.getProperty(prefix+".additivity"));
            propNames.remove(prefix+".additivity");
        }
        //create child filters and create child appender ref        
/*        <xs:complexType name="LoggerType">
        <xs:sequence>
            <xs:choice minOccurs="0" maxOccurs="1">
                <xs:element name="Filters" type="FiltersType"/>
                <xs:element name="Filter" type="FilterType"/>
            </xs:choice>
            <xs:element name="AppenderRef" type="AppenderRefType"/>
        </xs:sequence>
*/
        
        return loggerEl;
    }
    private static Element createRootLoggerElement(Document document, Properties properties) {
    //TODO
        Element rootLogger = document.createElement(XMLConst.ROOT);
        rootLogger.setAttribute("level", properties.getProperty(PropertiesConst.ROOT_LOGGER+".level"));
        //add AppendeerRef
        
    /*<xs:complexType name="RootType">
        <xs:sequence>
            <xs:element name="AppenderRef" type="AppenderRefType" minOccurs="1" maxOccurs="unbounded"/>
        </xs:sequence>
</xs:complexType>*/
        return rootLogger;
    }
}
