
package cz.muni.fi.pb138.log4jtoxml.fileWriters;

import static cz.muni.fi.pb138.log4jtoxml.constants.Log4j2Constants.NAME;
import static cz.muni.fi.pb138.log4jtoxml.constants.Log4j2Constants.STATUS;
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

public class XMLWriterTest {
    File testFile;
    
    @Before
    public void inti() {
        testFile = new File("file.xml");
        assertNotNull(testFile);
    }
    
    @Test
    public void emptyXMLWrite() {
        Properties prop = new Properties();
        XMLWriter.writeData(testFile, prop);
        try {
            List<String> lines = Files.readAllLines(testFile.toPath());
            assertNotNull(lines);
            //it is generetaed like one line xml doc
            assertEquals(1, lines.size());
            String sub = "<Configuration>";
            assertTrue(lines.get(0).contains(sub.subSequence(0, sub.length())));
            assertTrue(lines.get(0).endsWith("</Configuration>"));
        } catch (IOException ex) {
            Logger.getLogger(PropertieWriterTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test(expected = NullPointerException.class)
    public void xmlWirteNoFile() {
        XMLWriter.writeData(null, new Properties());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void xmlWriteWrongFormatFile() {
        File wrong = new File("randmo.txt");
        Properties properties = new Properties();
        XMLWriter.writeData(wrong, properties);
    }
    
    @Test
    public void xmlWrite() {
        Properties prop = new Properties();
        prop.setProperty(STATUS, "OUT");
        prop.setProperty(NAME, "test");
        
        XMLWriter.writeData(testFile, prop);
        try {
            List<String> lines = Files.readAllLines(testFile.toPath());
            assertNotNull(lines);
            //it is generetaed like one line xml doc
            assertEquals(1, lines.size());
            String sub = "Configuration name=\"test\" status=\"OUT\"";
            assertTrue(lines.get(0).contains(sub.subSequence(0, sub.length())));
            assertTrue(lines.get(0).endsWith("</Configuration>"));
        } catch (IOException ex) {
            Logger.getLogger(PropertieWriterTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
