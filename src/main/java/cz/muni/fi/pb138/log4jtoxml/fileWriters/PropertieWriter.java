/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml.fileWriters;

import cz.muni.fi.pb138.log4jtoxml.prop.Log4jObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for writing properties in file
 * 
 * @author Jakub
 */
public class PropertieWriter {
 
    public static void writeData(File output, Set<Log4jObject> data) {
        Properties properties = new Properties();
        //some kind of sorting??
        for(Log4jObject obj : data) {
            properties.setProperty(obj.getName(), obj.getValue());
        }

        try {
            properties.store(new FileOutputStream(output), null);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PropertieWriter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PropertieWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * This static method takes data as object of java.util.Properties and
     * saves all data in file
     * 
     * @param output File where ara the data saved
     * @param properties Object of java.util.Properties. This object contains all data.
     */
    public static void writeData(File output, Properties properties) {
        try {
            properties.store(new FileOutputStream(output), null);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PropertieWriter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PropertieWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
