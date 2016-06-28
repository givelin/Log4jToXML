/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml.prop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Objects;

/**
 *
 * @author tomf
 */
public class Log4j2Object {
    protected String name;
    protected String type;
    protected LinkedHashMap <String, String> attributes;
    protected ArrayList <Log4j2Object> children;
    
    public Log4j2Object(String name, String type, LinkedHashMap<String, String> attributes, ArrayList children) {
        this.name = name;
        this.type = type;
        this.attributes = attributes;
        this.children = children;
    }

    public Log4j2Object () {
        this.attributes = new LinkedHashMap<>();
        this.children = new ArrayList<>();
        this.name = null;
        this.type = null;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    

    public ArrayList<Log4j2Object> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Log4j2Object> children) {
        this.children = children;
    }
    
    public void addChild (Log4j2Object o) {
        children.add(o);
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(LinkedHashMap<String, String> attributes) {
        this.attributes = attributes;
    }

    public void addAttribute (String key, String value) {
        this.attributes.put(key, value);
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.name);
        for (Entry e : attributes.entrySet()) {
            hash = 79 * hash + Objects.hashCode(e.getValue());
        }       
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Log4j2Object other = (Log4j2Object) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.attributes, other.attributes)) {
            return false;
        }
        if (!Objects.equals(this.children, other.children)) {
            return false;
        }
        return true;
    }

}
