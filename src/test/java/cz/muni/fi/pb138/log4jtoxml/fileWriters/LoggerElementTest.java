/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml.fileWriters;

import cz.muni.fi.pb138.log4jtoxml.BaseElementTest;
import cz.muni.fi.pb138.log4jtoxml.fileWriters.xml.CreateLoggersElement;
import java.util.Properties;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.w3c.dom.Element;


public class LoggerElementTest extends BaseElementTest{
    @Test
    public void testCreateElement() {
        Properties properties = new Properties();
        properties.setProperty("logger.file.additivity", "false");
        properties.setProperty("logger.file.level", "debug");
        properties.setProperty("logger.file.name", "org.apache.logging.log4j.test2");
        properties.setProperty("logger.file.appenderref.file.ref", "File");
        properties.setProperty("rootLogger.level", "trace");
        properties.setProperty("rootLogger.appenderref.list.ref", "List");
        
        Element loggerElement = CreateLoggersElement.createLoggersElement(doc, properties);
        
        assertNotNull(loggerElement);
        assertTrue(loggerElement.hasChildNodes());
        assertEquals(2, loggerElement.getChildNodes().getLength());
        
        for(int i =0;i<loggerElement.getChildNodes().getLength();i++) {
            Element le = (Element) loggerElement.getChildNodes().item(i);
            if(le.getNodeName().equals("Root")) {
                assertEquals("trace", le.getAttribute("level"));
            } else {
                assertEquals("org.apache.logging.log4j.test2", le.getAttribute("name"));
                assertEquals("debug", le.getAttribute("level"));
                assertEquals("false", le.getAttribute("additivity"));
            }
        }
    }
    
    @Test(expected = NullPointerException.class)
    public void testCreateElementNoProperties() {
        Element rec = CreateLoggersElement.createLoggersElement(doc, null);
        fail("Exception should be throw!");
    }
}
