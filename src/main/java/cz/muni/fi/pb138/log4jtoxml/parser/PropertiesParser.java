/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml.parser;

import cz.muni.fi.pb138.log4jtoxml.constants.PropertiesConst;
import cz.muni.fi.pb138.log4jtoxml.prop.Additivity;
import cz.muni.fi.pb138.log4jtoxml.prop.Appender;
import cz.muni.fi.pb138.log4jtoxml.prop.Category;
import cz.muni.fi.pb138.log4jtoxml.prop.Factory;
import cz.muni.fi.pb138.log4jtoxml.prop.Filters;
import cz.muni.fi.pb138.log4jtoxml.prop.Log4jObject;
import cz.muni.fi.pb138.log4jtoxml.prop.Loggers;
import cz.muni.fi.pb138.log4jtoxml.prop.Renderer;
import cz.muni.fi.pb138.log4jtoxml.prop.Reset;
import cz.muni.fi.pb138.log4jtoxml.prop.RootCategory;
import cz.muni.fi.pb138.log4jtoxml.prop.RootLogger;
import cz.muni.fi.pb138.log4jtoxml.prop.Threshold;
import cz.muni.fi.pb138.log4jtoxml.prop.ThrowAbleRender;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Jakub
 */
public class PropertiesParser {

    private static Set<Log4jObject> data;
    
    public static Set<Log4jObject> sortAndSave(Map<String,String> inputData) {
        //TODO
        data = new HashSet<>();
        for(String key : inputData.keySet()) {
            String[] splitKey = key.split(".");
            if(splitKey[0].equals(PropertiesConst.PREFIX)) {
                switch(splitKey[1]) {
                    case PropertiesConst.CATEGORY:
                        data.add(new Category(key, inputData.get(key)));
                        break;
                    case PropertiesConst.FACTORY:
                        data.add(new Factory(key, inputData.get(key)));
                        break;
                    case PropertiesConst.ADDITIVITY:
                        data.add(new Additivity(key, inputData.get(key)));
                        break;
                    case PropertiesConst.ROOT_CATEGORY:
                        data.add(new RootCategory(key, inputData.get(key)));
                        break;
                    case PropertiesConst.ROOT_LOGGER:
                        data.add(new RootLogger(key, inputData.get(key)));
                        break;
                    case PropertiesConst.RENDERER:
                        data.add(new Renderer(key,inputData.get(key)));
                        break;
                    case PropertiesConst.THRESHOLD:
                        data.add(new Threshold(key, inputData.get(key)));
                        break;
                    case PropertiesConst.THROWABLE_RENDERER:
                        data.add(new ThrowAbleRender(key, inputData.get(key)));
                        break;
                    case PropertiesConst.RESET:
                        data.add(new Reset(key, inputData.get(key)));
                        break;
                    case PropertiesConst.APPENDER:                       
                        data.add(new Appender(key, inputData.get(key)));
                        break;
                    case PropertiesConst.FILTER:
                        data.add(new Filters(key, inputData.get(key)));
                        break;
                    case PropertiesConst.LOGGER:
                        data.add(new Loggers(key, inputData.get(key)));
                        break;
                    default:
                        data.add(new Log4jObject(key, inputData.get(key)));
                        break;
                }
            } else {
            //throw exception - wrong prefix
            }
        }
        return data;
    }
    
}
