
package cz.muni.fi.pb138.log4jtoxml.fileWriters;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class PropertieWriterTest {
    
    File testFile;
    
    @Before
    public void inti() {
        testFile = new File("prop.properties");
        assertNotNull(testFile);
    }
    
    @Test
    public void emptyPropertiesWrite() {
        Properties properties = new Properties();
        assertTrue(properties.isEmpty());
        PropertieWriter.writeData(testFile, properties);
        try {
            List<String> lines = Files.readAllLines(testFile.toPath());
            //contains one line with time and date
            assertEquals(1,lines.size());
        } catch (IOException ex) {
            Logger.getLogger(PropertieWriterTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test(expected = NullPointerException.class)
    public void propertiesWirteNoFile() {
        PropertieWriter.writeData(null, new Properties());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void propertiesWriteWrongFormatFile() {
        File wrong = new File("randmo.txt");
        Properties properties = new Properties();
        PropertieWriter.writeData(wrong, properties);
    }
    
    @Test
    public void popertiesWrite() {
        Properties properties = new Properties();
        properties.setProperty("key", "value");
        properties.setProperty("someOther", "prop");
        PropertieWriter.writeData(testFile, properties);
        try {
            List<String> lines = Files.readAllLines(testFile.toPath());
            assertTrue(lines.contains("key=value"));
            assertTrue(lines.contains("someOther=prop"));
        } catch (IOException ex) {
            Logger.getLogger(PropertieWriterTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
