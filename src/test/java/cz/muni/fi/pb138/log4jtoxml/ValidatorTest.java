package cz.muni.fi.pb138.log4jtoxml;

import static cz.muni.fi.pb138.log4jtoxml.constants.Log4j2Constants.RESOURCES_TEST_PREFIX;
import cz.muni.fi.pb138.log4jtoxml.fileReaders.InputLoader;
import cz.muni.fi.pb138.log4jtoxml.impl.XMLValidatorImpl;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import static org.junit.Assert.fail;

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
        String path = System.getProperty("user.dir");
        path += RESOURCES_TEST_PREFIX;
        path += "valid.xml";
        File xml = new File(path);
        
        InputLoader loader = new InputLoader(xml);
        try {
        Document doc = loader.getDOM();
        /*XMLNormalizator normalizator = new XMLNormalizator();
        doc = normalizator.removeNodeValues(doc);
        doc = normalizator.toConcise(doc);*/
        Assert.assertTrue(validator.isXMLFileValid(doc));
        }
        catch(IOException | ParserConfigurationException | SAXException e) {
            fail("exception thrown");
        }
            
        //this returns null no matter what i do
        //File xml = new File(classLoader.getResource("log4j-test1.xml").getPath());
    }
    
    @Test
    public void invalidXmlTest() {
        String path = System.getProperty("user.dir");
        path += RESOURCES_TEST_PREFIX;
        path += "invalid.xml";
        File xml = new File(path);
        
        InputLoader loader = new InputLoader(xml);
        Assert.assertFalse(validator.isXMLFileValid(xml));
        
    }
    
    
}
