package cz.muni.fi.pb138.log4jtoxml;

import java.io.File;

/**
 *
 */
public interface ConfigurationConverterFromProp extends ConfigurationConverter {
    /**
     * Check if file is valid.
     * 
     * @param file Input
     * @return Return true if configuration file is valid, else return false
     */
    public boolean validatePropConfiguration(File file);
}
