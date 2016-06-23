/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml.fileReaders;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is for reading property file
 * @author Jakub
 */
public class PropertieReader {

    /**
     * This static class read property file and return all contains as 
     * java.util.Properties object
     * 
     * @param file input file of type property
     * @return java.util.Properties loaded from file
     */
    public static Properties readPropertieFile(File file) {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(file));
        } catch (IOException ex) {
            Logger.getLogger(PropertieReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return properties;
    }
}
