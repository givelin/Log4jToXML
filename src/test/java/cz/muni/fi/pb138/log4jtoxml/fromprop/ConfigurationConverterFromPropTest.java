/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml.fromprop;

import cz.muni.fi.pb138.log4jtoxml.ConfigurationConverterBaseTest;
import cz.muni.fi.pb138.log4jtoxml.impl.ConfigurationConverterFromPropToXml;
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
public class ConfigurationConverterFromPropTest extends ConfigurationConverterBaseTest{
    
    @Before
    public void setUp() {
        converter = new ConfigurationConverterFromPropToXml();
    }
    
    @Test
    public void baseTest() {
        ClassLoader cl = getClass().getClassLoader();
        File input = new File(cl.getResource("fromProp/input/in01.properties").getPath());
        File output = new File(cl.getResource("fromProp/output/out01.xml").getPath());
        
        converter.convert(input, output);
        
        File original = new File(cl.getResource("fromProp/original/orig01.xml").getPath());
        
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
