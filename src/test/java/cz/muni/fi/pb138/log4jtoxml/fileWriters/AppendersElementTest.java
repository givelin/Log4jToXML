/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml.fileWriters;

import cz.muni.fi.pb138.log4jtoxml.BaseElementTest;
import cz.muni.fi.pb138.log4jtoxml.fileWriters.xml.CreateAppendersElement;
import java.util.Properties;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.w3c.dom.Element;


public class AppendersElementTest extends BaseElementTest {  
    @Test
    public void testCreateElement() {
        Properties prop = new Properties();
        
        prop.setProperty("appender.file.fileName", "${filename}");
        prop.setProperty("appender.file.name", "File");
        prop.setProperty("appender.file.type", "File");
        prop.setProperty("appender.file.layout.pattern", "%d %p %C{1.} [%t] %m%n");
        prop.setProperty("appender.file.layout.type", "Pattern");

        Element appenedrEl = CreateAppendersElement.createAppendersElement(doc, prop);
        
        assertNotNull(appenedrEl);
        assertTrue(appenedrEl.hasChildNodes());
        assertEquals(1, appenedrEl.getChildNodes().getLength());
        
        Element e = (Element) appenedrEl.getChildNodes().item(0);
        assertEquals("File", e.getNodeName());
        assertEquals("File", e.getAttribute("name"));
        assertEquals("${filename}", e.getAttribute("fileName"));
        
        assertTrue(e.hasChildNodes());
        assertEquals(1, e.getChildNodes().getLength());
        Element l = (Element) e.getChildNodes().item(0);
        assertEquals("Pattern", l.getNodeName());
        assertEquals("%d %p %C{1.} [%t] %m%n", l.getAttribute("pattern"));
    }
    
    @Test(expected = NullPointerException.class)
    public void testCreateElementNoProperties() {
        Element ret = CreateAppendersElement.createAppendersElement(doc, null);
        fail("Exception should be throw!");
    }
}
