/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml.impl;

import cz.muni.fi.pb138.log4jtoxml.XMLValidator;
import static cz.muni.fi.pb138.log4jtoxml.constants.Log4j2Constants.RESOURCES_PREFIX;
import java.io.File;
import java.io.IOException;
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
        String s = this.getClass().getResource("/config.xsd").toString().substring(5);
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            /*
            String path = System.getProperty("user.dir"); //could not make any "loadResource" work
            path += "\\src\\main\\java\\cz\\muni\\fi\\pb138\\log4jtoxml\\resources\\config.xsd";
            File s = new File(path);
            */
            
            Schema schema = factory.newSchema(new File (s));
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
    
    @Override
    public boolean isXMLFileValid(Document doc) {   
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            String path = System.getProperty("user.dir");
            path += RESOURCES_PREFIX;
            //path += "config.xsd";
            path += "config-short.xsd";
            File s = new File(path);
            
            Schema schema = factory.newSchema(s);
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
