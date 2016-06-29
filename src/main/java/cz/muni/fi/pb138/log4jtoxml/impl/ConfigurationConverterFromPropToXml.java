package cz.muni.fi.pb138.log4jtoxml.impl;

import cz.muni.fi.pb138.log4jtoxml.ConfigurationConverter;
import cz.muni.fi.pb138.log4jtoxml.XMLValidator;
import cz.muni.fi.pb138.log4jtoxml.fileReaders.InputLoader;
import cz.muni.fi.pb138.log4jtoxml.fileWriters.XMLWriter;
import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of ConfigurationConverter from Prop to XML
 */
public class ConfigurationConverterFromPropToXml implements ConfigurationConverter {

    protected XMLValidator validator;

    @Override
    public void convert(File input) {        
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
            XMLWriter writer = new XMLWriter();
            InputLoader loader = new InputLoader(input);
            try{
                Properties prop = loader.getProperties();
                writer.writeData(output, prop);
                
                validator = new XMLValidatorImpl();
                Boolean valid = validator.isXMLFileValid(output);
                if (valid) {
                    System.out.println("XML file valid");
                }
        
            }
            catch(IOException e)
            {
                Logger.getLogger(ConfigurationConverterFromPropToXml.class.getName()).log(Level.SEVERE, null, e);
                System.out.printf("An error occured during conversion. We are sorry. Nothing can help you now.");
            }
            /*
            if(validator==null) {
                validator = new XMLValidatorImpl();
            }
            if(validator.isXMLFileValid(output)) {
                // somethink like System.out.println("Have a nice day, Mr. Bond");
            } else {
                //throw exception about non valid output
            }
            */
    }
    
}
