/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml.fromxml;

import cz.muni.fi.pb138.log4jtoxml.ConfigurationConverterBaseTest;
import cz.muni.fi.pb138.log4jtoxml.impl.ConfigurationConverterFromXmlToProp;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Jakub
 */
public class ConfigurationConverterFromXmlTest  extends ConfigurationConverterBaseTest {
    @Before
    public void setUp() {
        converter = new ConfigurationConverterFromXmlToProp();
    }
    
    @Test
    public void baseTest() {
        ClassLoader cl = getClass().getClassLoader();
        File input = new File(cl.getResource("fromXml/input/in01.xml").getPath());
        File output = new File(cl.getResource("fromXml/output/out01.properties").getPath());
        
        converter.convert(input, output);
        
        File original = new File(cl.getResource("fromXml/original/orig01.properties").getPath());
        
        try {
            compareFiles(original, output); 
        } catch (FileNotFoundException ex) {
            //failed BufferReader
            fail("BufferReader throw exception");
        }
        catch(IOException ex) {
            //readLine failed
            fail("readLine throw exception");
        }
    }
    
    //after test delete files in outpu??
}
