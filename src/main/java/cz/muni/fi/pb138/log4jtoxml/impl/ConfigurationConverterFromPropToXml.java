package cz.muni.fi.pb138.log4jtoxml.impl;

import cz.muni.fi.pb138.log4jtoxml.ConfigurationConverter;
import cz.muni.fi.pb138.log4jtoxml.impl.fileReaders.PropertieReader;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of ConfigurationConverter from Prop to XML
 */
public class ConfigurationConverterFromPropToXml implements ConfigurationConverter {

    @Override
    public void convert(File input) {
        //TODO
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void convert(File input, File output) {
        //TODO
        try {
            Map<String,String> properties = PropertieReader.readPropertieFile(input);
        } catch (IOException ex) {
            Logger.getLogger(ConfigurationConverterFromPropToXml.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
