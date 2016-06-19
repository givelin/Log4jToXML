package cz.muni.fi.pb138.log4jtoxml.impl;

import cz.muni.fi.pb138.log4jtoxml.ConfigurationConverter;
import java.io.File;

/**
 * Implementation of ConfigurationConverter from XML to Prop
 */
public class ConfigurationConverterFromXmlToProp implements ConfigurationConverter {

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
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
