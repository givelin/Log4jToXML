package cz.muni.fi.pb138.log4jtoxml;

import cz.muni.fi.pb138.log4jtoxml.impl.XMLValidatorImpl;
import java.io.File;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

//TODO add xml files
public class ValidatorTest {
    protected XMLValidator validator;
    protected ClassLoader classLoader;
    
    @Before
    public void setUp() {
        validator = new XMLValidatorImpl();
        classLoader = getClass().getClassLoader();
    }
    
    @Test
    public void validXmlTest() {
        //TODO add xml file
        File xml = new File(classLoader.getResource("validator/xmlValid.xml").getPath());
        Assert.assertTrue(validator.isXMLFileValid(xml));
    }
    
    @Test
    public void unvalidXmlTest() {
        //TODO add xml file
        File xml = new File(classLoader.getResource("validator/xmlUnvalid.xml").getPath());
        Assert.assertFalse(validator.isXMLFileValid(xml));
    }
    
    
}
