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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author tomf
 */
public class InputLoader {
    private Document xmlDoc = null;
    private Properties propertiesDoc = null;
    private String nameOfFile;
    private BufferedReader in;
    private InputStream is;
    
    public InputLoader(InputStream is) {
        if (is == null) {
            throw new IllegalArgumentException("No input stream");
        }
    	this.is = is;
        this.in = new BufferedReader(new InputStreamReader(is));
    }
    
    public InputLoader(String nameOfFile) throws FileNotFoundException {
        if (nameOfFile == null) {
            throw new IllegalArgumentException("No name of file");
        }
        if (nameOfFile.isEmpty()) {
            throw new IllegalArgumentException("Name is empty");
        }  
        this.nameOfFile = nameOfFile;
        this.is = new FileInputStream(new File(nameOfFile));
        this.in = new BufferedReader(new InputStreamReader(is)); 
    }
    
    public Document getDOM() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        xmlDoc = builder.parse(this.is);
        return xmlDoc;
    }
    
    public Properties getProperties() throws IOException {
        propertiesDoc = new Properties();
        propertiesDoc.load(this.in);
        return propertiesDoc;
    }
}
