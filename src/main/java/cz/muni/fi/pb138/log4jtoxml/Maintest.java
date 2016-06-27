/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml;

import cz.muni.fi.pb138.log4jtoxml.fileReaders.InputLoader;
import cz.muni.fi.pb138.log4jtoxml.fileWriters.PropertiesWriter2;
import cz.muni.fi.pb138.log4jtoxml.impl.XMLValidatorImpl;
import cz.muni.fi.pb138.log4jtoxml.parser.XMLParser3;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author tomf
 */
public class Maintest {
    public static void main(String[] args) throws FileNotFoundException, ParserConfigurationException, IOException, SAXException {
        /*
        XMLValidatorImpl val = new XMLValidatorImpl();
        System.out.println(val.isXMLFileValid(new File("log4j2xsdtest.xml")));
        */
        /*
        InputLoader in = new InputLoader("testXML.xml");
        XMLParser3 par = new XMLParser3(in.getDOM());
        PropertiesWriter2 wr = new PropertiesWriter2(par.parse());
        wr.writeData("testXMLresult.properties");
        */
    }
}
