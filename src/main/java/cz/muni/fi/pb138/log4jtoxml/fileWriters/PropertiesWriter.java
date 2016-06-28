/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml.fileWriters;

import cz.muni.fi.pb138.log4jtoxml.constants.Log4j2Constants;
import cz.muni.fi.pb138.log4jtoxml.prop.Log4j2Object;
import cz.muni.fi.pb138.log4jtoxml.prop.Log4j2ObjectList;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *Class for writing properties in file
 * 
 * @author tomf
 */
public class PropertiesWriter {
    //objects that hold information about main configuration elements
    private Log4j2Object config;
    private Log4j2Object customLevels;
    private Log4j2Object properties;
    private Log4j2Object appenders;
    private Log4j2Object loggers;
    private Log4j2Object filters;
    
    private static final Map<String, String> POLICY_TYPES = new HashMap<String, String>(){
        {
            put("TimeBasedTriggeringPolicy", "time");
            put("SizeBasedTriggeringPolicy", "size");
            
        }
    };
    
    private static final Map<String, String> FILTER_TYPES = new HashMap<String, String>(){
        {
            put("ThreadContextMapFilter", "mdc");
            put("ThresholdFilter", "threshold");
            put("ScriptFilter", "script");
            put("MarkerFilter", "marker");
            put("Marker", "marker");
            //filter prefixes below this are made up, couldnt find any example anywhere
            put("DynamicThresholdFilter", "dtc"); 
            put("BurstFilter", "burst");
            
        }
        
    };
    
    /**
 * Constructor. Loads input objects from provided list
 * 
 * 
 * 
 * @param  init list of main configuration objects
 *              Precise order expected as follows:
 *              0: config
 *              1: customLevels
 *              2:properties
 *              3:filters
 *              4:appenders
 *              5:loggers
 */
    /*
    public PropertiesWriter(List<Log4j2Object> init) {
        if (!(init == null) && !(init.isEmpty()) && (init.size() == 6)) {
            config = init.get(0);
            customLevels = init.get(1);
            properties = init.get(2);
            filters = init.get(3);
            appenders = init.get(4);
            loggers = init.get(5);
        } else {
            throw new IllegalArgumentException("recieved empty, null or incomplete data list");
        }
    }
    */
    
    /**
     * Constructor used in conjuction with xmlParser class
     * @param input 
     */
    public PropertiesWriter (Log4j2ObjectList input) {
        if (input != null) {
            if (input.getConfiguration() != null)
                config = input.getConfiguration();
            if (input.getCustomLevels() != null)
                customLevels = input.getCustomLevels();
            if (input.getProperties() != null)
                properties = input.getProperties();
            if (input.getFilters() != null)
                filters = input.getFilters();
            if (input.getAppenders() != null)
                appenders = input.getAppenders();
            if (input.getLoggers() != null) 
                loggers = input.getLoggers();
        } else {
            throw new IllegalArgumentException("recieved empty, null or incomplete Log4j2ObjectList");
        }
    }
    
    
    /**
 * Writes a properties file
 * 
 * @param  output path to the output file
 */
    public void writeData(File output) throws UnsupportedEncodingException, FileNotFoundException, IOException {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), "utf-8"))) {
            if (!isNullOrEmpty(config)) {
                writeDeep(config, writer, "");
                writer.write("\n");
            }
            if (!isNullOrEmpty(customLevels)) {
                writeDeep(customLevels, writer, "");
                writer.write("\n");
            }
            if (!isNullOrEmpty(properties)) {
                writeDeep(properties, writer, "");
                writer.write("\n");
            }
            if (!isNullOrEmpty(filters)) {
                if (filters.getChildren().isEmpty()) {
                    writeFilter(filters, writer, "filter.");
                    writer.write("\n");
                } 
                else {
                    writeFilter(filters, writer, "filter.");
                    writer.write("\n");
                }
            }
            if (!isNullOrEmpty(appenders)) {
                writeAppender(appenders, writer, "appender.");
                writer.write("\n");
            }
            if (!isNullOrEmpty(loggers)) {
                writeLogger(loggers, writer, "", "");
                writer.write("\n");
            }
        }
    }
    
    private void writeFilter (Log4j2Object start, Writer writer, String path) throws IOException {
        String name = start.getName();
        if (name.equals("Filters")) {
            if (!start.getChildren().isEmpty()) { 
                for (Log4j2Object obj: start.getChildren()) {
                    writeFilter(obj, writer, path);
                }
            }
            return;
        }
        
        if (name.contains("Filter") || name.equals("Marker")) { //stupid special cases
            String prefix = FILTER_TYPES.get(name);
            if (prefix == null){ //unknown filter
                prefix = name.toLowerCase();
            }
            path+=prefix+".";
            writer.write(path.toLowerCase() + "type" + " = " + name + "\n");
        }
        
        //write all attributes of current level
        if (!start.getAttributes().isEmpty()) {
            if (start.getName().toLowerCase().equals("keyvaluepair")) { //special case
                String keyPath = path.toLowerCase();
                keyPath = keyPath.substring(0, keyPath.lastIndexOf("keyvaluepair")) + "pair.";
                writer.write(keyPath + "type = KeyValuePair\n"); //suggests there are other types, none that i know of though
                
                for (Entry e : start.getAttributes().entrySet()) {
                    writer.write(keyPath + e.getKey() + " = " + e.getValue() + "\n");
                } 
            }
            else {
                for (Entry e : start.getAttributes().entrySet()) {
                    writer.write(path.toLowerCase() + e.getKey() + " = " + e.getValue() + "\n");
                } 
            }
        }
        
        //call itself for all children in deeper level
        if (!start.getChildren().isEmpty()) { 
            for (Log4j2Object obj: start.getChildren()) {
                String str = "";
                if (obj.getName() != null) {
                    str = obj.getName() + ".";
                }
                writeFilter(obj, writer, path + str);
            }
        }
    }
    
    
    /**
 * Writes a log4j2 object in file and then calls itself on all its children
 * 
 * @param  start Log4j2 object to be processed
 * @param  writer writer used to write the file
 * @param path path of currently proccesed object(prefix) - can be used to provide prefix 
 *             if the log4j2 object structure doesn't match expected properties format
 */
    private void writeDeep (Log4j2Object start, Writer writer, String path) throws IOException {
        //write all attributes of current level
        if (!start.getAttributes().isEmpty()) {
            for (Entry e : start.getAttributes().entrySet()) {
                writer.write(path.toLowerCase() + e.getKey() + " = " + e.getValue() + "\n");
            } 
        }
        
        //call itself for all children in deeper level
        if (!start.getChildren().isEmpty()) { 
            for (Log4j2Object obj: start.getChildren()) {
                String str = "";
                if (obj.getName() != null) {
                    str = obj.getName() + ".";
                }
                writeDeep(obj, writer, path + str);
            }
        }
    }
    
    /**
 * Writes appender log4j2 object in file and then calls itself on all its children
 * 
 * @param  start Log4j2 object to be processed
 * @param  writer writer used to write the file
 * @param path path of currently proccesed object(prefix) - can be used to provide prefix 
 *             if the log4j2 object structure doesn't match expected properties format;
 *             first call should be with "appender."
 */
    private void writeAppender (Log4j2Object start, Writer writer, String path) throws IOException {
        //write all attributes of current level
        if (!start.getAttributes().isEmpty()) {
            for (Entry e : start.getAttributes().entrySet()) {
                writer.write(path.toLowerCase() + e.getKey() + " = " + e.getValue() + "\n");
            } 
        }
        
        //call itself for all children in deeper level
        if (!start.getChildren().isEmpty()) { 
            for (Log4j2Object obj: start.getChildren()) {
                String name = obj.getName();
                String str = "";
                if (name != null) {
                    if (path.equals("appender.")) {
                        //this compensates for the way Log4j2 objects are read from XML
                        obj.addAttribute("type", name);
                    }
                    
                    String help = nameHelper(name);
                    str += help;
                    if (name.toLowerCase().contains(Log4j2Constants.FILTER)) {
                        String prefix = FILTER_TYPES.get(name);
                        if (prefix == null){ //unknown filter
                            prefix = name.toLowerCase();
                        }
                        
                        str += "."+prefix;
                    }
                    
                    if (name.toLowerCase().contains(Log4j2Constants.POLICY)) {
                        String prefix = POLICY_TYPES.get(name);
                        if (prefix == null){ //unknown filter
                            prefix = name.toLowerCase();
                        }
                        
                        str += "."+prefix;
                    }
                    
                                        
                    //inserts type to the output at the appropriate location
                    if (!obj.getName().equals(str) && !str.equals("")) {
                        //int stopper = obj.getName().toLowerCase().indexOf(str);
                        //obj.addAttribute("type", obj.getName().substring(0, stopper)); this was wrong anyway
                        obj.addAttribute("type", obj.getName());
                    }
                    if (!str.equals(""))
                       str += ".";
                }
                writeAppender(obj, writer, path + str);
            }
        }
    }
    
    /**
 * Writes logger log4j2 object in file and then calls itself on all its children
 * 
 * @param  start Log4j2 object to be processed
 * @param  writer writer used to write the file
 * @param  path path of currently proccesed object(prefix) - can be used to provide prefix 
 *              if the log4j2 object structure doesn't match expected properties format;
 * @param appender appref for current logger object
 */
    private void writeLogger (Log4j2Object start, Writer writer, String path, String appender) throws IOException {
        if (!start.getAttributes().isEmpty()) {
            if (start.getName().toLowerCase().equals("keyvaluepair")) { //special case
                String keyPath = path.toLowerCase();
                keyPath = keyPath.substring(0, keyPath.lastIndexOf("keyvaluepair")) + "pair.";
                writer.write(keyPath + "type = KeyValuePair\n"); //suggests there are other types, none that i know of though
                
                for (Entry e : start.getAttributes().entrySet()) {
                    writer.write(keyPath + e.getKey() + " = " + e.getValue() + "\n");
                } 
            }
            else {
                for (Entry e : start.getAttributes().entrySet()) {
                    writer.write(path.toLowerCase() + e.getKey() + " = " + e.getValue() + "\n");
                } 
            }
        }
        
        if (!start.getChildren().isEmpty()) { 
            for (Log4j2Object obj: start.getChildren()) {
                String str = "";
                String appRef = appender;
                if (obj.getName() != null) {
                    //str = obj.getName() + ".";
                    str += nameHelper(obj.getName());
                    if (!obj.getName().equals(str) && !str.equals("")) {
                        int stopper = obj.getName().toLowerCase().indexOf(str);
                        obj.addAttribute("type", obj.getName().substring(0, stopper));
                    }
                    if (obj.getName().toLowerCase().equals(Log4j2Constants.LOGGER)) {
                        str = obj.getName() + "." + getLoggerSuffix(obj);
                        appRef = getLoggerSuffix(obj);
                    }
                    if (obj.getName().equals(Log4j2Constants.APPENDE_REF)) {
                        str = obj.getName() + "." + appRef ;
                    }
                    if (obj.getName().toLowerCase().equals(Log4j2Constants.ROOT)){
                        str = "rootLogger";
                        appRef = getLoggerSuffix(obj);
                    }
                    if (!str.equals(""))
                        str+=".";
                }
                writeLogger(obj, writer, path + str, appRef);
            }
        }
    }
    
    
    /**
 * returns approprite constant to be added to the properties output
 * compensates for the way properties are read from XML file
 * 
 * @param  str string with current log4j2 object prefix
 * @return     returns appropriate constant for given prefix
 */
    private String nameHelper (String str) {
        if (!str.equals(Log4j2Constants.FILTERS) && !str.equals(Log4j2Constants.FAILOVERS)
                && !str.equals(Log4j2Constants.PROPERTIES)) {
            if (str.toLowerCase().contains(Log4j2Constants.FILTER)) {
                return Log4j2Constants.FILTER;
            } else if (str.toLowerCase().contains(Log4j2Constants.LAYOUT)) {
                return Log4j2Constants.LAYOUT;
            }
            else if (str.toLowerCase().contains(Log4j2Constants.POLICY)) {
                return Log4j2Constants.POLICY;
            }
            return str;
        }
        return "";
    }
   
    /**
 * returns type of appenderRef
 * compensates for the way properties are read from XML file
 * 
 * @param  logger  logger object to be processed
 * @return     returns type of appenderRef
 */
    private String getLoggerSuffix (Log4j2Object logger) {
        String appenderRef = null;
        for (Log4j2Object obj : logger.getChildren()) {
            if (obj.getName().equals(Log4j2Constants.APPENDE_REF)) {
                appenderRef = obj.getAttributes().get("ref");
            }
        }
        for (Log4j2Object obj : appenders.getChildren()) {
            if (obj.getAttributes().get("name").equals(appenderRef)) {
                return obj.getAttributes().get("type");
            }
        }
        return "EMPTY";
    }
    
    private String trimString (String str) {
        if (str != null) {
            String[] out = str.split("(?<=\\p{Ll})(?=\\p{Lu})");
            return out[0];
        }
        return null;
    }
    
    /**
 * Returns true if the input object is null or empty; false otherwise
 * 
 * @param  obj Log42j object to be checked
 * @return     Returns true if the input object is null or empty; false otherwise
 */
    private boolean isNullOrEmpty (Log4j2Object obj) {
        if (obj == null) {
            return true;
        } else if (obj.getChildren().isEmpty() && obj.getAttributes().isEmpty()) {
            return true;
        }
        return false;
    }
}
