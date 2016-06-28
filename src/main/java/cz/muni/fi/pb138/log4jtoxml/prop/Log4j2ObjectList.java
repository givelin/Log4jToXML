/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml.prop;

/**
 *
 * @author tomf
 */
public class Log4j2ObjectList {
    private Log4j2Object configuration;
    private Log4j2Object customLevels;
    private Log4j2Object properties;
    private Log4j2Object filters;
    private Log4j2Object appenders;
    private Log4j2Object loggers;

    public Log4j2Object getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Log4j2Object configuration) {
        this.configuration = configuration;
    }

    public Log4j2Object getCustomLevels() {
        return customLevels;
    }

    public void setCustomLevels(Log4j2Object customLevels) {
        this.customLevels = customLevels;
    }

    public Log4j2Object getProperties() {
        return properties;
    }

    public void setProperties(Log4j2Object properties) {
        this.properties = properties;
    }

    public Log4j2Object getFilters() {
        return filters;
    }

    public void setFilters(Log4j2Object filters) {
        this.filters = filters;
    }

    public Log4j2Object getAppenders() {
        return appenders;
    }

    public void setAppenders(Log4j2Object appenders) {
        this.appenders = appenders;
    }

    public Log4j2Object getLoggers() {
        return loggers;
    }

    public void setLoggers(Log4j2Object loggers) {
        this.loggers = loggers;
    }
    
    public Log4j2ObjectList (Log4j2Object config, Log4j2Object customLevel, 
            Log4j2Object properties, Log4j2Object filters, Log4j2Object appenders, Log4j2Object loggers) {
        this.configuration = config;
        this.customLevels = customLevel;
        this.properties = properties;
        this.filters = filters;
        this.appenders = appenders;
        this.loggers = loggers;
    }
    
    public Log4j2ObjectList () {
        
    }
}
