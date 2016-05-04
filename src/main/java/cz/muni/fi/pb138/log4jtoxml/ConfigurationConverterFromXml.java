package cz.muni.fi.pb138.log4jtoxml;

import java.io.File;

/**
 *
 */
public interface ConfigurationConverterFromXml extends ConfigurationConverter {
    /**
     * Check if file is valid.
     * 
     * @param file Input
     * @return Return true if configuration file is valid, else return false
     */
    public boolean validateXmlConfiguration(File file);
}
