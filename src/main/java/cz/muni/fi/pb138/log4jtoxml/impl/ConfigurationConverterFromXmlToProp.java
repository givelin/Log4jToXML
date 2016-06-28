package cz.muni.fi.pb138.log4jtoxml.impl;

import cz.muni.fi.pb138.log4jtoxml.ConfigurationConverter;
import cz.muni.fi.pb138.log4jtoxml.XMLNormalizator;
import cz.muni.fi.pb138.log4jtoxml.XMLValidator;
import cz.muni.fi.pb138.log4jtoxml.fileReaders.InputLoader;
import cz.muni.fi.pb138.log4jtoxml.fileWriters.PropertiesWriter2;
import cz.muni.fi.pb138.log4jtoxml.parser.XMLParser3;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * Implementation of ConfigurationConverter from XML to Prop
 */
public class ConfigurationConverterFromXmlToProp implements ConfigurationConverter {

    protected XMLValidator validator;

    @Override
    public void convert(File input) {
        String name = input.getName();
        String[] splitName = name.split(".");
        String outName ="";
        for(int i =0; i<splitName.length-1;i++) {
            outName+=splitName[i]+".";
        }
        outName+="properties";
        convert(input, new File(input.getParent(), outName));
    }

    @Override
    public void convert(File input, File output) {
        InputLoader loader = new InputLoader(input);
        try {
            
        Document doc = loader.getDOM();
        
        validator = new XMLValidatorImpl();
        Boolean valid = validator.isXMLFileValid(doc);
        System.out.println(valid);
        
        XMLNormalizator normalizator = new XMLNormalizator();
        doc = normalizator.removeNodeValues(doc);
        doc = normalizator.toConcise(doc);
        
        
        
        XMLParser3 parser = new XMLParser3(doc);
        PropertiesWriter2 writer = new PropertiesWriter2(parser.parse());
        writer.writeData(output);
        }
        catch (ParserConfigurationException | IOException | SAXException e) {
            Logger.getLogger(ConfigurationConverterFromPropToXml.class.getName()).log(Level.SEVERE, null, e);
            System.out.printf("An error occured during conversion. We are sorry. Nothing can help you now.");
        }
    }
    
}
