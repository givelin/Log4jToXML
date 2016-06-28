package cz.muni.fi.pb138.log4jtoxml.fileWriters.xml;

import cz.muni.fi.pb138.log4jtoxml.constants.XMLConst;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Class for child appender xml element.
 * @author Jakub
 */
public class CreateAppendersElement {
    /**
     * Static method for creating of appeneder xml child
     * 
     * @param document xml document
     * @param properties data
     * @return Element - appender
     */
    public static Element createAppendersElement(Document document, Properties properties) {
        
        Element appendersElement = document.createElement(XMLConst.APPENDERS);
        Set<String> propNames = properties.stringPropertyNames();
        Iterator<String> iterator = propNames.iterator();
        while(iterator.hasNext()) {
            String name = iterator.next();
            if(!name.startsWith("appender"))
                iterator.remove();
        }
        
        while(!propNames.isEmpty()) {
            appendersElement.appendChild(createAppender(document, properties, getAppendersOfOneType(propNames)));
            
        }
        if(appendersElement.hasChildNodes()) {
            return appendersElement;
        }
        return null;
    }
    private static Element createAppender(Document document, Properties properties, Set<String> propNames) {
        
        String firstName = propNames.iterator().next();
        String[] splitName = firstName.split("\\.");
        String prefix = splitName[0]+"."+splitName[1];
        Element appendeer = document.createElement(properties.getProperty(prefix+".type"));
        appendeer.setAttribute("name", properties.getProperty(prefix+".name"));
        if(propNames.contains(prefix+".fileName")) {
            appendeer.setAttribute("fileName", properties.getProperty(prefix+".fileName"));
        }

        Element layout = appenderLayout(document, properties, propNames, prefix);
        if (layout != null) {
            appendeer.appendChild(layout);
        }
        
        Element policies = appenderPolicies(document, properties, propNames, prefix);
        if (policies != null) {
            appendeer.appendChild(policies);
        }
        Element filters = appenderFilters(document, properties, propNames, prefix);
        if (filters != null) {
            appendeer.appendChild(filters);
        }
        
        return appendeer;
    }
    private static Element appenderLayout(Document document, Properties properties, Set<String> propNames, String prefix) {
        if(propNames.contains(prefix+".layout.type")) {            
            Element layout = document.createElement(properties.getProperty(prefix+".layout.type"));
            layout.setAttribute("pattern", properties.getProperty(prefix+".layout.pattern"));
            return layout;
        }
        return null;
    }
    
    private static Element createAppenderPolicy(Document document, Properties properties, Set<String> policyPropNames, String typePath) {
        //Element filterEl = document.createElement(XMLConst.FILTER);
        /*
        input should look like:
        
        appender.rolling.policies.type = Policies
        appender.rolling.policies.time.type = TimeBasedTriggeringPolicy
        appender.rolling.policies.time.interval = 2
        appender.rolling.policies.time.modulate = true
        appender.rolling.policies.size.type = SizeBasedTriggeringPolicy
        appender.rolling.policies.size.size=100MB      
        
        ill try to write it in a general way so that it works even if its a bit different
        */
        
        
        String type = properties.getProperty(typePath+"type");
        if (type == null) {
            return null;
        }
        Element policy = document.createElement(type);
        
        Set<String> policyNames = getAppenderPolicyPropNames(properties, typePath);
        policyPropNames.remove(typePath+"type");
        policyNames.remove(typePath+"type");
        
        Iterator<String> it = policyNames.iterator();
        
        
        
        while(it.hasNext()){
            String current = it.next();
            int attPosition = current.lastIndexOf(".");            
            String att = current.substring(attPosition+1);
            String val = properties.getProperty(current);
            /*ive noticed that if its size, it shouldnt contain units
            i guess nobody knows why...
            */
            if (att.equals("size")) {
                val = val.replaceAll("[^0-9.]+","");
            }
            
            policy.setAttribute(att, val);
            policyPropNames.remove(current);
        }
        
        return policy;
    }
    
    private static Set<String> getAppenderPoliciesPropNames(Properties properties, String prefix) {
        Set<String> names = new HashSet<>();
        for (String s : properties.stringPropertyNames()) {
            if(s.startsWith(prefix+".policies")) {
                names.add(s);
            }
        }
        return names;
    }
    
    private static Set<String> getAppenderPolicyPropNames(Properties properties, String prefix) {
        Set<String> names = new HashSet<>();
        for (String s : properties.stringPropertyNames()) {
            if(s.startsWith(prefix)) {
                names.add(s);
            }
        }
        return names;
    }
    
    private static Element appenderPolicies(Document document, Properties properties, Set<String> propNames, String prefix) {
        Element policiesElement = document.createElement(XMLConst.POLICIES);
        
        if(!propNames.contains(prefix+".policies.type")) {
            return null;
        }
        
        Set<String> appenderPolicies = getAppenderPoliciesPropNames(properties, prefix);
        appenderPolicies.remove(prefix+".policies.type");
        
        while(!appenderPolicies.isEmpty()) {
            Iterator<String> it = appenderPolicies.iterator();
            String first = it.next();
            String useless = "appender.rolling.policies.";
            String type = first.substring(useless.length());
            int typeEnd = type.indexOf(".");
            type = type.substring(0,typeEnd+1);
            String typePath = first.substring(0, useless.length() + type.length());
            
            Element policy = createAppenderPolicy(document, properties, appenderPolicies, typePath);
            if (policy == null)
                break;
            policiesElement.appendChild(policy);
        }
        
        if(policiesElement.hasChildNodes()) {
            return policiesElement;
        }
        return null;
    }
    
    
    private static Element appenderFilters(Document document, Properties properties, Set<String> propNames, String prefix) {
        Element filtersElement = document.createElement(XMLConst.FILTERS);
        Set<String> appenderFilters = getAppenderFilterPropNames(properties, prefix);
        
        while(!appenderFilters.isEmpty()) {
            Element filter = createAppendFilter(document, properties, appenderFilters);
            if (filter == null)
                break;
            filtersElement.appendChild(filter);
        }
        
        if(filtersElement.hasChildNodes()) {
            return filtersElement;
        }
        return null;
    }

    private static Element createAppendFilter(Document document, Properties properties, Set<String> filterPropNames) {
        Element filterEl = document.createElement(XMLConst.FILTER);
        /*
        input should look like:
        
        appender.list.filter.threshold.type = ThresholdFilter
        appender.list.filter.threshold.level = error        
        
        ill try to write it in a general way so that it works even if its a bit different
        */
        
        String[] splitSubName = filterPropNames.iterator().next().split("\\.");
        String filterPrefix = ""; // = splitSubName[0]+"."+splitSubName[1]+"."+splitSubName[2];
        int i = 0;
        while (i<splitSubName.length && !splitSubName[i].equals("filter")) {
            filterPrefix += splitSubName[i]+".";
            i++;
        }
        if (i+1 < splitSubName.length) {
            filterPrefix += splitSubName[i]+"."+splitSubName[i+1];
        }
        else {
            return null;
        }
        
        System.out.println(filterPrefix);
        
        boolean containKeyPair = false;
        for(String s : filterPropNames) {
            if(s.startsWith(filterPrefix+".pair")) {
                containKeyPair = true;
                break;
            }
        }
        if(containKeyPair) {
            Element keyPair = document.createElement(properties.getProperty(filterPrefix+".pair.type"));
            filterPropNames.remove(filterPrefix+".pair.type");
            keyPair.setAttribute("key", properties.getProperty(filterPrefix+".pair.key"));
            filterPropNames.remove(filterPrefix+".pair.key");
            keyPair.setAttribute("value", properties.getProperty(filterPrefix+".pair.value"));
            filterPropNames.remove(filterPrefix+".pair.value");
            filterEl.appendChild(keyPair);
        }
        
        Iterator<String> it = filterPropNames.iterator();
        while (it.hasNext()) {
            String current = it.next();
            String attName = current.substring(filterPrefix.length()+1);
            filterEl.setAttribute(attName, properties.getProperty(filterPrefix+"."+attName));
            it.remove();
        }
        /*
        
        filterEl.setAttribute("type", properties.getProperty(filterPrefix+".type"));
        filterPropNames.remove(filterPrefix+".type");
        if(filterPropNames.contains(filterPrefix+".level")) {
            filterEl.setAttribute("level", properties.getProperty(filterPrefix+".level"));
            filterPropNames.remove(filterPrefix+".level");
        }
        if(filterPropNames.contains(filterPrefix+".marker")) {
            filterEl.setAttribute("marker", properties.getProperty(filterPrefix+".marker"));
            filterPropNames.remove(filterPrefix+".marker");
        }
        if(filterPropNames.contains(filterPrefix+".onMatch")) {
            filterEl.setAttribute("onMatch", properties.getProperty(filterPrefix+".onMatch"));
            filterPropNames.remove(filterPrefix+".onMatch");
        }
        if(filterPropNames.contains(filterPrefix+".onMismatch")) {
            filterEl.setAttribute("onMismatch", properties.getProperty(filterPrefix+".onMismatch"));
            filterPropNames.remove(filterPrefix+".onMismatch");
        }
*/
        
        return filterEl;
    }
    
    private static Set<String> getAppendersOfOneType(Set<String> names) {
        if(names.isEmpty()) return Collections.EMPTY_SET; //should never happen
        Iterator<String> it = names.iterator();
        String firstName = it.next();        
        String[] splitName = firstName.split("\\.");
        Set<String> out = new HashSet<>();
        
        it = names.iterator(); //reset iterator
        while(it.hasNext()) {
            String n = it.next();
            if(n.startsWith(splitName[0]+"."+splitName[1])) {
                out.add(n);
                it.remove();
            }
        }
        return out;
    }
    
    private static Set<String> getAppenderFilterPropNames(Properties properties, String prefix) {
        Set<String> names = new HashSet<>();
        for (String s : properties.stringPropertyNames()) {
            if(s.startsWith(prefix+".filter")) {
                names.add(s);
            }
        }
        return names;
    }
}