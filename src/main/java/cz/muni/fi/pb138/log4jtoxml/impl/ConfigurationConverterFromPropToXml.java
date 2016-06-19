package cz.muni.fi.pb138.log4jtoxml.impl;

import cz.muni.fi.pb138.log4jtoxml.ConfigurationConverter;
import cz.muni.fi.pb138.log4jtoxml.impl.fileReaders.PropertieReader;
import cz.muni.fi.pb138.log4jtoxml.impl.fileWriters.XMLWriter;
import cz.muni.fi.pb138.log4jtoxml.impl.parser.PropertiesParser;
import cz.muni.fi.pb138.log4jtoxml.prop.Log4jObject;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of ConfigurationConverter from Prop to XML
 */
public class ConfigurationConverterFromPropToXml implements ConfigurationConverter {

    @Override
    public void convert(File input) {
        //TODO
        //call convert and set outpu with same name and type of xml
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void convert(File input, File output) {
        //TODO
        //reading file, get all data
        Map<String,String> properties = null;
        try {
            properties = PropertieReader.readPropertieFile(input);
        } catch (IOException ex) {
            Logger.getLogger(ConfigurationConverterFromPropToXml.class.getName()).log(Level.SEVERE, null, ex);
        }
        //data from file sort and prepare
        if (properties==null) throw new NullPointerException(); //change for specific exception
        Set<Log4jObject> data = PropertiesParser.sortAndSave(properties);
        //wirete data to file
        XMLWriter.writeData(output, data);      
        
        //take saved data and write it into output file
        //if input file has data, delete it
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
