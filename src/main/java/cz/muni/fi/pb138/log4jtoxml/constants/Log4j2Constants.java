/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml.constants;

/**
 * Class for holding some constatns used in properties files.
 * 
 * @author Team
 */
public class Log4j2Constants {
    
    public static final String PREFIX = "log4j";
    public static final String APPENDER = "appender";
    public static final String CATEGORY = "category";
    public static final String FACTORY = "factory";
    public static final String FILTER = "filter";
    public static final String LOGGER = "logger";
    public static final String ROOT_CATEGORY = "rootCategory";
    public static final String ROOT_LOGGER = "rootLogger";
    public static final String DEBUG = "debug";
    public static final String THRESHOLD = "threshold";
    public static final String LOGGER_FACTORY = "loggerFactory";
    public static final String ADDITIVITY = "additivity";
    public static final String RENDERER = "renderer";
    public static final String THROWABLE_RENDERER = "throwableRenderer";
    public static final String LOGGER_REF = "logger-ref";
    public static final String ROOT_REF = "root-ref";
    public static final String APPENDER_REF_TAG = "appender-ref";
    public static final String RESET = "reset";
    public static final String ROOT = "root";
    
    public static final String PROPERTY = "property";
    
    public static final String CONFIGURATION = "Configuration";
    
        //Configuration attributes
    public static final String PACKAGES ="packages";
    public static final String STATUS="status";
    public static final String SCRIPT="strict";
    public static final String NAME="name";
    public static final String ADVERTISER="advertiser";
    public static final String SCHEMA="schema";
    public static final String[] configAttributes = {PACKAGES,STATUS,SCRIPT,NAME,ADVERTISER,SCHEMA};
    public static final String LAYOUT="layout";
    public static final String FAILOVERS="Failovers";
    //configuration childs
    public static final String CUSTOM_LEVELS = "CustomLevels";
    public static final String CUSTOM_LEVEL = "CustomLevel";
    public static final String PROPERTIES = "Properties";
    public static final String FILTERS = "Filters";
    public static final String THRESHOL_FILTER = "ThresholdFilter";
    public static final String APPENDERS = "Appenders";
    public static final String LOGGERS = "Loggers";

 
    
    public static final String APPENDE_REF = "AppenderRef";

    public static final String RESOURCES_TEST_PREFIX = "\\src\\test\\java\\cz\\muni\\fi\\pb138\\log4jtoxml\\resources\\";
    public static final String RESOURCES_PREFIX = "\\src\\main\\resources\\";
    
    public static String joinConst(String frst, String scnd) {
        return frst + "." + scnd;
    }
    public static String prefixConst(String text, String prefix) {
        return joinConst(prefix, text);
    }
}
