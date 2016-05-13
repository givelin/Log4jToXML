package cz.muni.fi.pb138.log4jtoxml.fromxml;

import cz.muni.fi.pb138.log4jtoxml.ConfigurationConverterBaseTest;
import cz.muni.fi.pb138.log4jtoxml.impl.ConfigurationConverterFromXmlToProp;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

public class ConfigurationConverterFromXmlTest  extends ConfigurationConverterBaseTest {
    @Before
    public void setUp() {
        converter = new ConfigurationConverterFromXmlToProp();  
        classLoader = getClass().getClassLoader();
    }
    
    @Test
    public void baseTest() {
        File input = new File(classLoader.getResource("fromXml/input/in01.xml").getPath());
        File output = new File(classLoader.getResource("fromXml/output/out01.properties").getPath());
        
        converter.convert(input, output);
        
        File original = new File(classLoader.getResource("fromXml/original/orig01.properties").getPath());
        
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
    
    @Test(expected = Exception.class) //make special exception???
    public void wongOutputFileTest() {
        File input = new File(classLoader.getResource("fromXml/input/in01.xml").getPath());
        File output = new File(classLoader.getResource("fromXml/input/in01.xml").getPath());
        converter.convert(input, output);
        //throw exception
        fail("Converter convert");
    }
    
    @Test(expected = Exception.class) //make special exception
    public void convertunvalidFileTest() {
        // create unvalid file
        File input = new File(classLoader.getResource("fromXml/input/unvalid_in01.xml").getPath());
        convert.convert(input);
        //throw exception
        fail("Unvalid file was converted");
    }
}
