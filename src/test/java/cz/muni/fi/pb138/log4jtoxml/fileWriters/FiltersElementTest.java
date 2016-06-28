/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml.fileWriters;

import cz.muni.fi.pb138.log4jtoxml.BaseElementTest;
import cz.muni.fi.pb138.log4jtoxml.fileWriters.xml.CreateFiltersElement;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.w3c.dom.Element;


public class FiltersElementTest extends BaseElementTest{
    @Test
    public void testCreateElement() {
        
    }
    
    @Test(expected = NullPointerException.class)
    public void testCreateElementNoProperties() {
        Element res = CreateFiltersElement.createFiltersElement(doc, null);
        fail("Exception should be throw!");
    }   
}
