/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml.fileWriters;

import cz.muni.fi.pb138.log4jtoxml.BaseElementTest;
import cz.muni.fi.pb138.log4jtoxml.fileWriters.xml.CreatePropertiesElement;
import java.util.Properties;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.w3c.dom.Element;


public class PropertiesElementTest extends BaseElementTest{
    @Test
    public void testCreateElement() {
        Properties properties = new Properties();
        properties.setProperty("property.name", "filename");
        properties.setProperty("property.value", "target/test.log");
        
        Element propertiesEl = CreatePropertiesElement.createPropertiesElement(doc, properties);
        
        assertNotNull(propertiesEl);
        assertTrue(propertiesEl.hasChildNodes());
        assertEquals(2, propertiesEl.getChildNodes().getLength());
        
        for(int i = 0; i<propertiesEl.getChildNodes().getLength(); i++) {
            Element propE = (Element) propertiesEl.getChildNodes().item(i);
            switch (propE.getAttribute("name")) {
                case "name":
                    assertEquals("filename", propE.getAttribute("value"));
                    break;
                case "value":
                    assertEquals("target/test.log", propE.getAttribute("value"));
                    break;
                default:
                    fail("NotFounded right property");
                    break;
            }
        }
    }
    
    @Test(expected = NullPointerException.class)
    public void testCreateElementNoProperties() {
        Element res = CreatePropertiesElement.createPropertiesElement(doc, null);
        fail("Exception should be throw!");
    }
}
