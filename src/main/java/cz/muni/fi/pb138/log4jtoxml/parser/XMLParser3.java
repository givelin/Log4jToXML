/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml.parser;

import static cz.muni.fi.pb138.log4jtoxml.constants.Log4j2Constants.APPENDERS;
import static cz.muni.fi.pb138.log4jtoxml.constants.Log4j2Constants.CUSTOM_LEVELS;
import static cz.muni.fi.pb138.log4jtoxml.constants.Log4j2Constants.FILTER;
import static cz.muni.fi.pb138.log4jtoxml.constants.Log4j2Constants.FILTERS;
import static cz.muni.fi.pb138.log4jtoxml.constants.Log4j2Constants.LOGGERS;
import static cz.muni.fi.pb138.log4jtoxml.constants.Log4j2Constants.PROPERTIES;
import static cz.muni.fi.pb138.log4jtoxml.constants.XMLConst.THRESHOL_FILTER;
import cz.muni.fi.pb138.log4jtoxml.prop.Log4j2Object;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.ValidationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author tomf
 */
public class XMLParser3 {
    private Document doc;
    private Log4j2Object config;
    private Log4j2Object customLevels;
    private Log4j2Object properties;
    private Log4j2Object appenders;
    private Log4j2Object loggers;
    private Log4j2Object filters;
    //private Log4j2Object thresholdFilter;
    
    
    public XMLParser3 (Document doc) {
        this.doc = doc;
    }
    
    public List<Log4j2Object> parse () {
        Element root = doc.getDocumentElement();
        parseConfig(root);
        NodeList children = root.getChildNodes();
        for (int i=0; i<children.getLength(); i++) {
            if (children.item(i).getNodeType() == Node.ELEMENT_NODE) {
                //System.out.println(children.item(i).getNodeName());
                String str = children.item(i).getNodeName();
                if (str.equals(CUSTOM_LEVELS)) {
                    customLevels = parseNode(children.item(i));
                } else if (str.equals(PROPERTIES)) {
                    properties = parseNode(children.item(i));
                } else if (str.toLowerCase().contains(FILTER)) {
                    filters = parseNode(children.item(i));
                } else if (str.equals(APPENDERS)) {
                    appenders = parseNode(children.item(i));
                } else if (str.equals(LOGGERS)) {
                    loggers = parseNode(children.item(i));
                } else {
                    System.err.println(children.item(i).getNodeName());
                    throw new IllegalArgumentException();
                }
                /*
                switch (children.item(i).getNodeName()) {
                    case CUSTOM_LEVELS:
                        customLevels = parseNode(children.item(i));
                        break;
                    case PROPERTIES:
                        properties = processProperties((Element)children.item(i));
                        break;
                    case FILTERS:
                        filters = parseNode(children.item(i));
                        break;
                    case FILTER:
                        filters = parseNode(children.item(i));
                        break;
                    case APPENDERS:
                        appenders = parseNode(children.item(i));
                        break;
                    case LOGGERS:
                        loggers = parseNode(children.item(i));
                        break;
                    case THRESHOL_FILTER:
                        thresholdFilter = parseNode(children.item(i));
                        break;
                    default:{
                        System.err.println(children.item(i).getNodeName());
                        throw new IllegalArgumentException();
                    }
                }
                */
            }
        }
        ArrayList<Log4j2Object> result = new ArrayList<>();
        result.add(config);
        result.add(customLevels);
        result.add(properties);
        //result.add(thresholdFilter);
        result.add(filters);
        result.add(appenders);
        result.add(loggers);
        return result;
    }
    
    private void parseConfig (Node node) {
        config = new Log4j2Object();
        if (node.hasAttributes()) {
            for (int i=0; i<node.getAttributes().getLength(); i++) {
                Node att = node.getAttributes().item(i);
                config.addAttribute(att.getNodeName(), att.getNodeValue());
            }
        }
    }
    
    
    private Log4j2Object processProperties(Element properties) {
        Log4j2Object o = new Log4j2Object();
        o.setName(properties.getNodeName());
        
        NodeList property = properties.getElementsByTagName("Property");
        for (int i = 0; i < property.getLength(); i++) {
            Log4j2Object child = new Log4j2Object();
            Element prop = (Element)property.item(i);
            
            NamedNodeMap attributes = prop.getAttributes();
            //predpokladam, ze properties v sobe maji jen <property name="..." value="..." />
            if (attributes.getLength() != 2) {
                //System.err.println("!=2");
                continue;
            }
            
            Node attName = attributes.item(0);
            
            if (!attName.getNodeName().equals("name")) {
                System.err.println("name");
                continue;
            }
            
            Node attVal = attributes.item(1);
            if (!attVal.getNodeName().equals("value")) {
                System.err.println("value");
                continue;
            }
            
            child.addAttribute(attName.getNodeValue(), attVal.getNodeValue());
            o.addChild(child);
        }
               
        return o;
    }
    
    private Log4j2Object parseNode (Node node) {
        Log4j2Object o = new Log4j2Object();
        o.setName(node.getNodeName());
        if (node.hasAttributes()) {
            for (int i=0; i<node.getAttributes().getLength(); i++) {
                Node att = node.getAttributes().item(i);
                o.addAttribute(att.getNodeName(), att.getNodeValue());
            }
        }
        if (node.hasChildNodes()) {
            NodeList children = node.getChildNodes();
            for (int i=0; i<children.getLength(); i++) {
                if (children.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Log4j2Object child = parseNode(children.item(i));
                    o.addChild(child);
                }
            }
        }
        return o;
    }
}
