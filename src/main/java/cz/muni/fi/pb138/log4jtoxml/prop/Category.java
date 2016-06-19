/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml.prop;

import cz.muni.fi.pb138.log4jtoxml.constants.PropertiesConst;

/**
 *
 * @author Jakub
 */
public class Category extends Log4jObject{

    public Category(String name, String value) {
        super(name, value);
    }
    
    @Override
    public String getPrefix() {
        return PropertiesConst.joinConst(super.getPrefix(), PropertiesConst.CATEGORY);
    }
}
