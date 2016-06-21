/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml.constants;

/**
 *
 * @author Jakub
 */
public class XMLConst {
    public static final String CONFIGURATION = "Configuration";
    
        //Configuration attributes
    public static final String PACKAGES ="packages";
    public static final String STATUS="status";
    public static final String SCRIPT="strict";
    public static final String NAME="name";
    public static final String ADVERTISER="advertiser";
    public static final String SCHEMA="schema";
    public static final String[] configAttributes = {PACKAGES,STATUS,SCRIPT,NAME,ADVERTISER,SCHEMA};
    
    //configuration childs
    public static final String CUSTOM_LEVELS = "CustomLevels";
    public static final String CUSTOM_LEVEL = "CustomLevel";
    public static final String PROPERTIES = "Properties";
    public static final String FILTERS = "Filters";
    public static final String THRESHOL_FILTER = "ThresholdFilter";
    public static final String APPENDERS = "Appenders";
    public static final String LOGGERS = "Loggers";

    public static final String PROPERTY = "Property";
    
    public static final String FILTER = "Filter";
}
