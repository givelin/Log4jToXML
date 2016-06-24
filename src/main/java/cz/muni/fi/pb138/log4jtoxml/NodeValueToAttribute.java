/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml;

import javax.xml.bind.ValidationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Petr
 */
public class NodeValueToAttribute {
    private void process(Node n) throws ValidationException{
        if (n.getNodeType() == Node.TEXT_NODE) {
            String text = n.getTextContent();
            text = text.replaceAll("\\s+","");
            if (text.equals("")) {
                //System.out.println(n.getNodeName() + " has no text");
                return;
            }
            
            Element parent = (Element)n.getParentNode();
            
            NamedNodeMap attributes = parent.getAttributes();
            if (attributes.getLength() > 1) {
                System.out.println("more than one attribute in element with value");
                
                System.out.println(n.getNodeName());
                System.out.println();
                
                for (int i = 0; i < attributes.getLength(); i++) {
                    System.out.println(attributes.item(i).getNodeName() + "   :   " 
                            + attributes.item(i).getNodeValue());
                    
                }
                throw new ValidationException("unexpected number of attributes in element (>1)");
            }
            
            if (attributes.getLength() == 1) {
                /*
                I assume something like
                
                <Properties>
                    <Property name="filename">target/test-xml.log</Property>
                </Properties>
                */
                parent.setAttribute("value", n.getTextContent());
                parent.removeChild(n);
            }
            
            if (attributes.getLength() == 0) {
                /*
                I assume something like
                
                <PatternLayout>
                    <Pattern>%d %p %C{1.} [%t] %m%n</Pattern>
                </PatternLayout>
                */
                Element grandparent = (Element) parent.getParentNode(); //lets hope it wont ever be null
                grandparent.setAttribute(parent.getNodeName(), n.getTextContent());
                parent.removeChild(n);
                grandparent.removeChild(parent);
                
                //this is probably super bad way to do it
                NodeList children = grandparent.getChildNodes();
                for(int i = 0; i < children.getLength(); i++) {
                    Node child = children.item(i);
                    if (child.getNodeType() == Node.TEXT_NODE) {
                        grandparent.removeChild(child);
                    }
                }
            }
        }
        else {
            NodeList children = n.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                process(children.item(i));
            }
        }
    }
    
    //i guess this will actually modify the doc even if i dont return it
    public Document convert(Document doc){
        Node root = doc.getDocumentElement();
        try { 
            this.process(root);
        }
        catch (ValidationException e) {
            System.err.println("Unexpected format of XML. I quit!");
        }
        
        return doc;
    }
}
