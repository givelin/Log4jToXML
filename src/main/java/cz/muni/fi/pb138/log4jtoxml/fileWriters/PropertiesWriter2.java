/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml.fileWriters;

import static cz.muni.fi.pb138.log4jtoxml.constants.XMLConst.APPENDER;
import static cz.muni.fi.pb138.log4jtoxml.constants.XMLConst.APPENDE_REF;
import static cz.muni.fi.pb138.log4jtoxml.constants.XMLConst.FILTER;
import static cz.muni.fi.pb138.log4jtoxml.constants.XMLConst.LOGGER;
import static cz.muni.fi.pb138.log4jtoxml.constants.XMLConst.ROOT;
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
    private Log4j2Object appenders;
    private Log4j2Object loggers;
    private Log4j2Object filters;
    private String outputFilename;
    
    public PropertiesWriter2 (List<Log4j2Object> init) {
        config = init.get(0);
        customLevels = init.get(1);
        properties = init.get(2);
        filters = init.get(3);
        appenders = init.get(4);
        loggers = init.get(5);
    }
    
    public void writeData(String output) throws UnsupportedEncodingException, FileNotFoundException, IOException {
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), "utf-8"))) {
            if (!isNullOrEmpty(config)) {
                writeDeep(config, writer, "");
            }
            if (!isNullOrEmpty(customLevels)) {
                writeDeep(customLevels, writer, "");
            }
            if (!isNullOrEmpty(properties)) {
                writeDeep(properties, writer, "");
            }
            if (!isNullOrEmpty(filters)) {
                if (filters.getChildren().isEmpty()) {
                    writeDeep(filters, writer, "filter.");
                } 
                else {
                    writeDeep(filters, writer, "");
                }
            }
            if (!isNullOrEmpty(appenders)) {
                writeAppender(appenders, writer, "");
            }
            if (!isNullOrEmpty(loggers)) {
                writeLogger(loggers, writer, "", "");
            }
            /*
            writeConfig(writer);
            writeCustomLevels(writer);
            writeProperties(writer);
            writeFilters(writer);
            writeAppender(appenders, writer, "");
            writeLogger(loggers ,writer, "", "");
                    */
        }
    }
    
    
    
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
                    str = obj.getName() + ".";
                    if (obj.getName().equals(APPENDER) || obj.getName().equals(FILTER))
                        str = obj.getName() + "." + trimString(obj.getType()) + ".";
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
                    str = obj.getName() + ".";
                    if (obj.getName().equals(LOGGER)) {
                        str = obj.getName() + "." + getLoggerSuffix(obj) + ".";
                        appRef = getLoggerSuffix(obj);
                    }
                    if (obj.getName().equals(APPENDE_REF)) {
                        str = obj.getName() + "." + appRef + ".";
                    }
                    if (obj.getName().equals(ROOT)){
                        str = "rootLogger.";
                        appRef = getLoggerSuffix(obj);
                    }
                }
                writeLogger(obj, writer, path + str, appRef);
            }
        }
    }
    
    private String getLoggerSuffix (Log4j2Object logger) {
        String appenderRef = null;
        for (Log4j2Object obj : logger.getChildren()) {
            if (obj.getName().equals(APPENDE_REF)) {
                appenderRef = obj.getAttributes().get("ref");
            }
        }
        for (Log4j2Object obj : appenders.getChildren()) {
            if (obj.getAttributes().get("name").equals(appenderRef)) {
                return trimString(obj.getType());
            }
        }
        return null;
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
}
