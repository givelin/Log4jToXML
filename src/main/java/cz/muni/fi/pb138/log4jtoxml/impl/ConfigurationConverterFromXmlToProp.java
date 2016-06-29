package cz.muni.fi.pb138.log4jtoxml.impl;

import cz.muni.fi.pb138.log4jtoxml.ConfigurationConverter;
import cz.muni.fi.pb138.log4jtoxml.XMLNormalizator;
import cz.muni.fi.pb138.log4jtoxml.XMLValidator;
import cz.muni.fi.pb138.log4jtoxml.fileReaders.InputLoader;
import cz.muni.fi.pb138.log4jtoxml.fileWriters.PropertiesWriter;
import cz.muni.fi.pb138.log4jtoxml.parser.XMLParser;
import java.io.File;
import java.io.IOException;
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
        XMLNormalizator normalizator = new XMLNormalizator();
        doc = normalizator.removeNodeValues(doc);
        doc = normalizator.toConcise(doc);
        validator = new XMLValidatorImpl();
        Boolean valid = validator.isXMLFileValid(doc);
        if (valid) {
            System.out.println("XML file valid");
        }
        
        
        
        
        
        XMLParser parser = new XMLParser(doc);
        PropertiesWriter writer = new PropertiesWriter(parser.parse());
        writer.writeData(output);
        System.out.println("Properties saved.");
        }
        catch (ParserConfigurationException | IOException | SAXException e) {
            //Logger.getLogger(ConfigurationConverterFromPropToXml.class.getName()).log(Level.SEVERE, null, e);
            System.out.printf("An error occured during conversion. Input file might be invalid - try again, please.");
        }
    }
    
}
