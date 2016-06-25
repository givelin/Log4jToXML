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
import cz.muni.fi.pb138.log4jtoxml.prop.Log4j2Object;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
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
                switch (children.item(i).getNodeName()) {
                    case CUSTOM_LEVELS:
                        customLevels = parseNode(children.item(i));
                        break;
                    case PROPERTIES:
                        properties = parseNode(children.item(i));
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
                    default:
                        throw new IllegalArgumentException();
                }
            }
        }
        ArrayList<Log4j2Object> result = new ArrayList<>();
        result.add(config);
        result.add(customLevels);
        result.add(properties);
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
