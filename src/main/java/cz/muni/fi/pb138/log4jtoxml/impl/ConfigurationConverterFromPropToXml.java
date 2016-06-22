package cz.muni.fi.pb138.log4jtoxml.impl;

import cz.muni.fi.pb138.log4jtoxml.ConfigurationConverter;
import cz.muni.fi.pb138.log4jtoxml.Validator;
import cz.muni.fi.pb138.log4jtoxml.fileReaders.PropertieReader;
import cz.muni.fi.pb138.log4jtoxml.fileWriters.XMLWriter;
import java.io.File;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.TransformerConfigurationException;

/**
 * Implementation of ConfigurationConverter from Prop to XML
 */
public class ConfigurationConverterFromPropToXml implements ConfigurationConverter {

    protected Validator validator;

    @Override
    public void convert(File input) {
        //TODO
        //call convert and set outpu with same name and type of xml
        
        String name = input.getName();
        String[] splitName = name.split(".");
        String outName ="";
        for(int i =0; i<splitName.length-1;i++) {
            outName+=splitName[i]+".";
        }
        outName+="xml";
        convert(input, new File(input.getParent(), outName));
    }

    @Override
    public void convert(File input, File output) {
        //TODO
        //reading file, get all data
        Properties properties = PropertieReader.readPropertieFile(input);

        try {
            XMLWriter.writeData(output, properties);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(ConfigurationConverterFromPropToXml.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(validator==null) {
            validator = new ValidatorImpl();
        }
        if(validator.isXMLFileValid(output)) {
            // somethink like System.out.println("Have a nice day, Mr. Bond");
        } else {
            //throw exception about non valid output
        }

        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
