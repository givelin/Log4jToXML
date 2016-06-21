/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml.constants;

public class PropertiesConst {
    
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
    
    public static String joinConst(String frst, String scnd) {
        return frst + "." + scnd;
    }
    public static String prefixConst(String text, String prefix) {
        return joinConst(prefix, text);
    }
}
