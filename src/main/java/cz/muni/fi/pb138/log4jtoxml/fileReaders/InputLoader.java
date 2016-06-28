/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml.fileReaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *Class for loading input from input file - supports both XML and properties
 * 
 * @author tomf
 */
public class InputLoader {
    private Document xmlDoc = null;
    private Properties propertiesDoc = null;
    private BufferedReader in;
    private InputStream is;
    
    /**
 * Constructor. Opens input file as a stream
 * 
 * @param  nameOfFile path to the input file to be read
 */
    public InputLoader(String nameOfFile) throws FileNotFoundException {
        if (nameOfFile == null) {
            throw new IllegalArgumentException("No name of file");
        }
        if (nameOfFile.isEmpty()) {
            throw new IllegalArgumentException("Name is empty");
        }  
        this.is = new FileInputStream(new File(nameOfFile));
        this.in = new BufferedReader(new InputStreamReader(is)); 
    }
    
    /**
 * Constructor. Opens input file as a stream
 * 
 * @param  file file be read
 */
    public InputLoader (File file) {
        if (file == null) {
            throw new IllegalArgumentException("No file");
        }
        try {
            this.is = new FileInputStream(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InputLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.in = new BufferedReader(new InputStreamReader(is));
    }
    
    /**
 * Reads xml file and returns it as a Document object
 * 
 * @return Returns Document object created from input file
 * @throws IOException if any IO errors occur
 * @throws ParserConfigurationException if a DocumentBuilder cannot be created which satisfies the configuration requested
 * @throws SAXException if any parse errors occur
 */
    public Document getDOM() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        xmlDoc = builder.parse(this.is);
        return xmlDoc;
    }
    
    /**
 * Reads properties file and returns it as a Properties object
 * 
 * @return Returns Properties object created from input file
 * @throws IOException if any IO errors occur
 */
    public Properties getProperties() throws IOException {
        propertiesDoc = new Properties();
        propertiesDoc.load(this.in);        
        return propertiesDoc;
    }
}
