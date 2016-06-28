package cz.muni.fi.pb138.log4jtoxml.fileWriters;

import cz.muni.fi.pb138.log4jtoxml.BaseElementTest;
import cz.muni.fi.pb138.log4jtoxml.fileWriters.xml.CreateThresholdFilterElement;
import java.util.Properties;
import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.w3c.dom.Element;

public class ThresholdFilterElementTest extends BaseElementTest{
    @Test
    public void testCreateElement() {
        Properties prop = new Properties();
        prop.setProperty("filter.threshold.type", "ThresholdFilter");
        prop.setProperty("filter.threshold.level", "debug");
        
        Element thrash = CreateThresholdFilterElement.createThresholdFilterElement(doc, prop);
  
        assertNotNull(thrash);
        assertTrue(thrash.hasAttribute("level"));
        assertEquals("debug", thrash.getAttribute("level"));
    }
    
    @Test(expected = NullPointerException.class)
    public void testCreateElementNoProperties() {
        Element res = CreateThresholdFilterElement.createThresholdFilterElement(doc, null);
        fail("Exception should be throw!");
    }
}
