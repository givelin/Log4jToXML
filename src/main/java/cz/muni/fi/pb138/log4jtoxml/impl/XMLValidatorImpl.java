/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml.impl;

import cz.muni.fi.pb138.log4jtoxml.XMLValidator;
import static cz.muni.fi.pb138.log4jtoxml.constants.Log4j2Constants.RESOURCES_PREFIX;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import javax.xml.XMLConstants;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Jakub
 */
public class XMLValidatorImpl implements XMLValidator {

    @Override
    public boolean isXMLFileValid(File file) {
        File sch = this.getSchema();
        if (sch == null) {
            System.err.println("could not load schema");
            return false;
        }
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            
            Schema schema = factory.newSchema(sch);
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(file));
        } catch (IOException e) {
            System.out.println("I/O Exception: " + e.getMessage());
            return false;
        } catch (SAXException e1) {
            System.out.println("SAX Exception: " + e1.getMessage());
            return false;
        }
        return true;
    }
    
    private File getSchema(){
        String resource = "/config.xsd";
        URL res = getClass().getResource(resource);
        File file = null;
        if (res.toString().startsWith("jar:")) {
            try {
                InputStream input = getClass().getResourceAsStream(resource);
                file = File.createTempFile("tempfile", ".tmp");
                OutputStream out = new FileOutputStream(file);
                int read;
                byte[] bytes = new byte[1024];

                while ((read = input.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                file.deleteOnExit();
            } catch (IOException ex) {
                System.err.println(ex.toString());
            }
        } else {
            //this will probably work in your IDE, but not from a JAR
            file = new File(res.getFile());
        }
        return file;
    }
    
    @Override
    public boolean isXMLFileValid(Document doc) {   
        File file = this.getSchema();
        if (file == null) {
            System.err.println("could not load schema");
            return false;
        }
        
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            //File s = new File(url.getFile());
            Schema schema = factory.newSchema(file);
            Validator validator = schema.newValidator();
            DOMSource domSource=new DOMSource(doc);
            validator.validate(domSource);
        } catch (IOException e) {
            System.err.println("I/O Exception: " + e.getMessage());
            return false;
        } catch (SAXException e1) {
            System.err.println("SAX Exception: " + e1.getMessage());
            return false;
        }
        return true;
    }
    
}
