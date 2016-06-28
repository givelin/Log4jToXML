/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml.fileWriters;

import cz.muni.fi.pb138.log4jtoxml.BaseElementTest;
import cz.muni.fi.pb138.log4jtoxml.fileWriters.xml.CreateCustomLevels;
import java.util.Properties;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.w3c.dom.Element;

/**
 *
 * @author Jakub
 */
public class CustomLevelsTest extends BaseElementTest{
    @Test
    public void testCreateElement() {
        Properties prop = new Properties();
        prop.setProperty("customLevel.my.name", "name");
        prop.setProperty("customLevel.my.intLevel", "trace");
        
        Element customEl = CreateCustomLevels.createCustomLevels(doc, prop);
        
        assertNotNull(customEl);
        assertTrue(customEl.hasChildNodes());
        assertEquals(1, customEl.getChildNodes().getLength());
    }
    
    @Test(expected = NullPointerException.class)
    public void testCreateElementNoProperties() {
        Element ret = CreateCustomLevels.createCustomLevels(doc, null);
        fail("Exception should be throw!");
    }
}
