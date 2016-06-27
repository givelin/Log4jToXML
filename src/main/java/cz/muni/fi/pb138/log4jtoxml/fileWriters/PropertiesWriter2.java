/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml.fileWriters;

import cz.muni.fi.pb138.log4jtoxml.constants.Log4j2Constants;
import cz.muni.fi.pb138.log4jtoxml.prop.Log4j2Object;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;
import java.util.Map.Entry;

/**
 *
 * @author tomf
 */
public class PropertiesWriter2 {
    private Log4j2Object config;
    private Log4j2Object customLevels;
    private Log4j2Object properties;
    //private Log4j2Object thresholdFilter;
    private Log4j2Object appenders;
    private Log4j2Object loggers;
    private Log4j2Object filters;
    //private String outputFilename;
    
    public PropertiesWriter2 (List<Log4j2Object> init) {
        config = init.get(0);
        customLevels = init.get(1);
        properties = init.get(2);
        //thresholdFilter = init.get(3);;
        filters = init.get(4);
        appenders = init.get(5);
        loggers = init.get(6);
    }
    
    public void writeData(String output) throws UnsupportedEncodingException, FileNotFoundException, IOException {
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
            /*
            if (!isNullOrEmpty(thresholdFilter)) {
                writeDeep(thresholdFilter, writer, "filter.threshold.");
                writer.write("\n");
            }
            */
            if (!isNullOrEmpty(filters)) {
                if (filters.getChildren().isEmpty()) {
                    writeDeep(filters, writer, "filter.");
                    writer.write("\n");
                } 
                else {
                    writeDeep(filters, writer, "");
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
    
    
    
    private void writeDeep (Log4j2Object start, Writer writer, String path) throws IOException {
        if (!start.getAttributes().isEmpty()) {
            for (Entry e : start.getAttributes().entrySet()) {
                writer.write(path.toLowerCase() + e.getKey() + " = " + e.getValue() + "\n");
            } 
        }
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
    
    private void writeAppender (Log4j2Object start, Writer writer, String path) throws IOException {
        if (!start.getAttributes().isEmpty()) {
            for (Entry e : start.getAttributes().entrySet()) {
                writer.write(path.toLowerCase() + e.getKey() + " = " + e.getValue() + "\n");
            } 
        }
        if (!start.getChildren().isEmpty()) { 
            for (Log4j2Object obj: start.getChildren()) {
                String str = "";
                if (obj.getName() != null) {
                    if (path.equals("appender.")) {
                        obj.addAttribute("type", obj.getName());
                    }
                    str += nameHelper(obj.getName());
                    if (!obj.getName().equals(str) && !str.equals("")) {
                        int stopper = obj.getName().toLowerCase().indexOf(str);
                        obj.addAttribute("type", obj.getName().substring(0, stopper));
                    }
                    if (!str.equals(""))
                       str += ".";
                }
                writeAppender(obj, writer, path + str);
            }
        }
    }
    
    private void writeLogger (Log4j2Object start, Writer writer, String path, String appender) throws IOException {
        if (!start.getAttributes().isEmpty()) {
            for (Entry e : start.getAttributes().entrySet()) {
                writer.write(path.toLowerCase() + e.getKey() + " = " + e.getValue() + "\n");
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
    
    private String nameHelper (String str) {
        if (!str.equals(Log4j2Constants.FILTERS) && !str.equals(Log4j2Constants.FAILOVERS)
                && !str.equals(Log4j2Constants.PROPERTIES)) {
            if (str.toLowerCase().contains(Log4j2Constants.FILTER)) {               
                return Log4j2Constants.FILTER;
            } else if (str.toLowerCase().contains(Log4j2Constants.LAYOUT)) {
                return Log4j2Constants.LAYOUT;
            }
            return str;
        }
        return "";
    }
    
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
    
    private boolean isNullOrEmpty (Log4j2Object obj) {
        if (obj == null) {
            return true;
        } else if (obj.getChildren().isEmpty() && obj.getAttributes().isEmpty()) {
            return true;
        }
        return false;
    }
    
    /*
    
    private void writeConfig (Writer writer) throws IOException {
        for (Entry e : config.getAttributes().entrySet()) {
            writer.write(e.getKey() + " = " + e.getValue() + "\n");
        }
    }
    
    private void writeCustomLevels(Writer writer) throws IOException {
        if (customLevels != null) {
            String path = "customLevel.";
            if (customLevels.getChildren().size() > 1) {
                int i = 1;
                for (Log4j2Object o : customLevels.getChildren()) {
                    for (Entry e : o.getAttributes().entrySet()) {
                        writer.write(path + "." + i + e.getKey() + " = " + e.getValue() + "\n");
                        i++;
                    }
                }
            } else {
                for (Entry e : customLevels.getAttributes().entrySet()) {
                    writer.write(path + e.getKey() + " = " + e.getValue() + "\n");
                }
            }
        }
    }
    
    private void writeProperties (Writer writer) throws IOException {
        if (properties != null) {
            String path = "property.";
            if (properties.getChildren().size() > 1) {
                int i = 1;
                for (Log4j2Object o : properties.getChildren()) {
                    for (Entry e : o.getAttributes().entrySet()) {
                        writer.write(path + "." + i + e.getKey() + " = " + e.getValue() + "\n");
                        i++;
                    }
                }
            } else {
                for (Entry e : properties.getAttributes().entrySet()) {
                    writer.write(path + e.getKey() + " = " + e.getValue() + "\n");
                }
            }
        }
    }
    
    private void writeFilters (Writer writer) throws IOException {
        if (filters != null) {
           String path = "filter.";
            if (filters.getChildren().size() > 1) {
                int i = 1;
                for (Log4j2Object o : filters.getChildren()) {
                    for (Entry e : o.getAttributes().entrySet()) {
                        writer.write(path + "." + i + e.getKey() + " = " + e.getValue() + "\n");
                        i++;
                    }
                }
            } else {
                for (Entry e : filters.getAttributes().entrySet()) {
                    writer.write(path + e.getKey() + " = " + e.getValue() + "\n");
                }
            } 
        }
    }
    
    */
}
