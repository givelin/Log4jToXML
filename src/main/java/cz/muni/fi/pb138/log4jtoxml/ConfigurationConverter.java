package cz.muni.fi.pb138.log4jtoxml;

import java.io.File;

/**
 *
 */
public interface ConfigurationConverter {
    /**
     * Convert file
     * For file of type property convert to to XML. Name and locationa of file 
     * will bi same (only with differtnt extendsion)
     * 
     * @param input source. File can be property or XML
     */
    public void convert(File input);
    /**
     * Convert file
     * 
     * @param input source. File can be property or XML.
     * @param output destination. File can be property or XML.
     */
    public void convert(File input, File output);
}
