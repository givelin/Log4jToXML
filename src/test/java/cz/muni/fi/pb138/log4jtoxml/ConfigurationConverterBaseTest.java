package cz.muni.fi.pb138.log4jtoxml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Assert;


public abstract class ConfigurationConverterBaseTest {
    protected ConfigurationConverter converter;
    protected ClassLoader classLoader;
    
    protected void compareFiles(File origFile, File outputFile) throws FileNotFoundException, IOException {
        BufferedReader originalBuffer = new BufferedReader(new FileReader(origFile));
        BufferedReader outputBuffer = new BufferedReader(new FileReader(outputFile));
        String originalLine = originalBuffer.readLine();
        do {
            String outputLine = outputBuffer.readLine();
            if (outputLine == null) {
                //add logger
                throw new NullPointerException("Output file on end. ");
            }
            System.out.print(outputLine);
            Assert.assertEquals(originalLine, outputLine);
            
            originalLine = originalBuffer.readLine();
        } while (originalLine != null);
    }
}
