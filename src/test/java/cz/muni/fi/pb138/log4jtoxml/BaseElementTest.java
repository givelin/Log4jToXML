/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.w3c.dom.Document;

/**
 *
 * @author Jakub
 */
public class BaseElementTest {
    protected Document doc;
    @Before
    public void init() {
        try {
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(BaseElementTest.class.getName()).log(Level.SEVERE, null, ex);
            fail("Document not created");
        }
    }
}
