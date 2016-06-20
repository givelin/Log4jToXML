package cz.muni.fi.pb138.log4jtoxml.impl;

import cz.muni.fi.pb138.log4jtoxml.ConfigurationConverter;
import cz.muni.fi.pb138.log4jtoxml.Validator;
import cz.muni.fi.pb138.log4jtoxml.impl.fileReaders.XMLReader;
import cz.muni.fi.pb138.log4jtoxml.impl.fileWriters.PropertieWriter;
import java.io.File;
import java.util.Properties;

/**
 * Implementation of ConfigurationConverter from XML to Prop
 */
public class ConfigurationConverterFromXmlToProp implements ConfigurationConverter {

    protected Validator validator;

    @Override
    public void convert(File input) {
        //TODO
        String name = input.getName();
        String[] splitName = name.split(".");
        String outName ="";
        for(int i =0; i<splitName.length-1;i++) {
            outName+=splitName[i]+".";
        }
        outName+="properties";
        convert(input, new File(input.getParent(), outName));
        
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void convert(File input, File output) {
        //TODO
        if(validator==null) {
            validator = new ValidatorImpl();
        }
        if(validator.isXMLFileValid(input)) {
        //ok skip
        } else {
        //throw unvalid exception...
        }

        Properties properties = XMLReader.readXMLFile(input);
        PropertieWriter.writeData(output, properties);

        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
