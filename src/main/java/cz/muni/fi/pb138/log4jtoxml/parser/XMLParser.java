/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml.parser;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import javax.xml.bind.ValidationException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Petr
 */
public class XMLParser {
    private Document doc;
    private Properties prop = new Properties();
    private enum XMLSkip {Configuration, Appenders};
    
    private static Document loadXML(File xmlFile) throws SAXException, ParserConfigurationException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(xmlFile);
    }
    
    public Properties loadAndSave(File xmlFile){
        try{
            this.doc = loadXML(xmlFile);
        }
        catch(SAXException | ParserConfigurationException | IOException e)
        {
        
        }
        
        Element root = doc.getDocumentElement();
        try{
        this.processChildren(root, "");
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        

            return prop;
        }
    
    //predpokladam, ze properties v sobe maji jen <property name="...">value</..>
    private void processProperties(Element properties) throws ValidationException{
        NodeList property = properties.getElementsByTagName("Property");
        for (int i = 0; i < property.getLength(); i++) {
            Element prop = (Element)property.item(i);
            
            NamedNodeMap attributes = prop.getAttributes();
            if (attributes.getLength() > 1) {
                System.out.println(">1");
                throw new ValidationException("unexpected number of attributes in property (>1)");
            }
            
            Node att = attributes.item(0);
            String attName = att.getNodeName();  
            String attVal = att.getNodeValue();
            
            if (!attName.equals("name")) {
                System.out.println("name");
                throw new ValidationException("property attribute isn't name");
            }
            this.prop.setProperty("property."+attVal, prop.getTextContent());
            System.out.println("property."+attVal + " = " + prop.getTextContent());

        }
    }
    
    private void processChildren(Node n, String path) throws ValidationException{
        if (n.getNodeName().equals("Properties")) {
            
            this.processProperties((Element)n);
            return;
        }
        
        if (!n.getNodeName().equals("Configuration")) {     
            NamedNodeMap attributes = n.getAttributes();
            for (int i = 0; i < attributes.getLength(); i++) {
                String attName = attributes.item(i).getNodeName();
                String attVal = attributes.item(i).getNodeValue();
                
                //tenhle if muzu pak smazat, ono to uklada pokazdy spravne, jen vypis byl mimo
                if(path.equals("")) {
                    this.prop.setProperty(path+attName, attVal);   
                    System.out.println(path+attName + " = " + attVal);
                }
                else {
                    this.prop.setProperty(path+attName, attVal);   
                    System.out.println(path+"."+attName + " = " + attVal);
                }
                    
            }
        }
        else {
            //get only status and name attributes for now - there might be many more needed, idk
            Node status = n.getAttributes().getNamedItem("status");
            if (status != null) {
                 this.prop.setProperty("status", status.getNodeValue());
                 System.out.println("status = " + status.getNodeValue());
            }
            
            Node name = n.getAttributes().getNamedItem("name");
            if (name != null) {
                this.prop.setProperty("name", name.getNodeValue());
                System.out.println("name = " + name.getNodeValue());
            }          
        }
        
        NodeList children = n.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node node = children.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                if (skip(node.getNodeName())) {
                    processChildren(node, path);
                }
                else {
                    if (path.equals(""))
                        processChildren(node, node.getNodeName());
                    else
                        processChildren(node, path+"."+node.getNodeName());
                }
            }
            
    }
    }
    
    private boolean skip(String s) {
    for (XMLSkip skip : XMLSkip.values()) {
        if (skip.name().equals(s)) {
            return true;
        }
    }
    return false;
    }
}

