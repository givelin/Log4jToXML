/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml;

import java.io.File;

/**
 *
 */
public interface Validator {
     /**
     * Check if file is valid.
     * 
     * @param file Input
     * @return Return true if configuration file is valid, else return false
     */
    public boolean isXMLFileValid(File file);
}
