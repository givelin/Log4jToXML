package cz.muni.fi.pb138.log4jtoxml;

import cz.muni.fi.pb138.log4jtoxml.impl.ValidatorImpl;
import java.io.File;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

//TODO add xml files
public class ValidatorTest {
    protected Validator validator;
    protected ClassLoader classLoader;
    
    @Before
    public void setUp() {
        validator = new ValidatorImpl();
        classLoader = getClass().getClassLoader();
    }
    
    @Test
    public void validXmlTest() {
        //TODO add xml file
        File xml = new File(classLoader.getResource("validator/xmlValid.xml").getPath());
        Assert.assertTrue(validator.validateConfiguration(xml));
    }
    
    @Test
    public void unvalidXmlTest() {
        //TODO add xml file
        File xml = new File(classLoader.getResource("validator/xmlUnvalid.xml").getPath());
        Assert.assertFalse(validator.validateConfiguration(xml));
    }
    
    
}
