
package cz.muni.fi.pb138.log4jtoxml.fileWriters;

import cz.muni.fi.pb138.log4jtoxml.prop.Log4j2Object;
import cz.muni.fi.pb138.log4jtoxml.prop.Log4j2ObjectList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class PropertieWriterTest {
    
    File testFile;
    PropertiesWriter propWriter;
    
    @Before
    public void inti() {
        testFile = new File("prop.properties");
        assertNotNull(testFile);
    }
    
    
    @Test(expected = IllegalArgumentException.class)
    public void propertiesWriteNoData() {
        propWriter = new PropertiesWriter(null);
    }
    
    @Test
    public void popertiesWrite() throws FileNotFoundException, IOException {
        Log4j2ObjectList testData = makeTestData();
        
        propWriter = new PropertiesWriter(testData);
        propWriter.writeData(testFile);
        try {
            List<String> lines = Files.readAllLines(testFile.toPath(), StandardCharsets.UTF_8);
            assertTrue(lines.contains("name = XMLConfigTest"));
            assertTrue(lines.contains("status = OFF"));
        } catch (IOException ex) {
            Logger.getLogger(PropertieWriterTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Log4j2ObjectList makeTestData () {
       
        Log4j2Object config = new Log4j2Object();
        Log4j2Object customLevels = new Log4j2Object();
        Log4j2Object properties = new Log4j2Object();
        Log4j2Object appenders = new Log4j2Object();
        Log4j2Object loggers = new Log4j2Object();
        Log4j2Object filters = new Log4j2Object();
        config.addAttribute("status", "OFF");
        config.addAttribute("name", "XMLConfigTest");
        Log4j2ObjectList testData = new Log4j2ObjectList(config, customLevels, properties, filters, appenders, loggers);
        return testData;
    }
}
