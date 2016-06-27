package cz.muni.fi.pb138.log4jtoxml;

import cz.muni.fi.pb138.log4jtoxml.impl.ConfigurationConverterFromPropToXml;
import cz.muni.fi.pb138.log4jtoxml.impl.ConfigurationConverterFromXmlToProp;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Petr
 */


public class Main {
    
/**
 * Reads input from console and reads file specified by path given by user
 * 
 * @param  scan Scanner used to scan for user input
 * @return      file that will be used as input for conversion
 */
    private static File getInFile(Scanner scan) {
        File file;
        String path;
        String extension;
                
        while (true) {
            System.out.println("Path to file to be converted: ");
            path = scan.nextLine();
            
            extension = getExtension(path);
            
            if (!(extension.toLowerCase().equals("properties") || extension.toLowerCase().equals("xml"))) {
                System.out.println();
                System.out.println("Invalid path.");
                continue;
            }
            
            file = new File(path);
            
            if (!file.exists()) {
                System.out.println();
                System.out.println("Invalid path.");
            }
            else break;            
        }
        return file;
    }
    
    /**
 * Creates a new file that is specified by path
 * 
 * @param  path path to the file that should be created
 * @return      File created or original file if exists
 */
    private static File createFile(String path) throws IOException {
        File file = new File(path);
        if (file.exists()) {
            //mozna by se dalo dat vedet uzivateli 
            //a zeptat se, jestli fakt pouzit uz existujici
            return file;
        }

        file.createNewFile();
        return file;
    }
    
    /**
 * Reads input from console and reads file specified by path given by user
 * 
 * @param  scan Scanner used to scan for user input
 * @param  inPath path to file that is used as input for conversion
 * @return      file that will be used as output for conversion
 */
    private static File getOutFile(Scanner scan, String inPath) {
        File file;
        String path;
        String extension;
                
        while (true) {
            System.out.println("Path to converted file (optional, if left blank same name and location will be used): ");
            path = scan.nextLine();
            
            if (path.equals("")) { //create file with the same name as input file
                path = getPathWithoutExtension(inPath);
                String inExtension = getExtension(inPath);
                if (inExtension.equals("xml"))
                    extension = "properties";
                else
                    extension = "xml";
                
                path = path+"."+extension;
                try {
                    file = createFile(path);
                }
                catch (IOException e) {
                    System.out.println();
                    System.out.println("Unable to create file. Path might be invalid.");
                    continue;
                }
                System.out.println(file.getAbsolutePath());
                return file;
            }
            
            extension = getExtension(path);
            String inExtension = getExtension(inPath);
            
            Boolean extensionValid = extension.toLowerCase().equals("properties") || extension.toLowerCase().equals("xml");
            extensionValid &= !extension.equals(inExtension);
            
            if (!extensionValid) {
                System.out.println();
                System.out.println("Invalid path.");
                continue;
            }
            
            try {
                file = createFile(path);
            }
            catch (IOException e) {
                System.out.println();
                System.out.println("Unable to create file. Path might be invalid.");
                continue;
            }
            
            break;            
        }
        return file;
    }
    
    /**
 * Returns the extension of a file
 * 
 * @param  path path to a file
 * @return      extension of a file
 */
    private static String getExtension(String path) {
        String extension = "";

        int i = path.lastIndexOf('.');
        if (i > 0) {
            extension = path.substring(i+1);
        }

        return extension;
    }
    
    /**
 * Returns path to a file without its extension
 * 
 * @param  path path to a file
 * @return      path without extension
 */
    private static String getPathWithoutExtension(String path) {
        String extension = "";

        int i = path.lastIndexOf('.');
        if (i > 0) {
            extension = path.substring(0, i);
        }

        return extension;
    }
    
    
    /**
 * Reads input and output files from user input and converts log4j2 
 * configuration from xml to properties and vice versa
 * 
 * @param  args not used
 */
    public static void main(String[] args) {        
        File inFile;
        File outFile;
        
        Scanner scan = new Scanner(System.in);
        inFile = getInFile(scan);
        outFile = getOutFile(scan, inFile.getAbsolutePath());
        
        if (getExtension(inFile.getAbsolutePath()).equals("xml")){
            //convert XML -> properties
            ConfigurationConverterFromXmlToProp converter = new ConfigurationConverterFromXmlToProp();
            converter.convert(inFile, outFile);
        }
        else {
            //convert properties -> XML
            ConfigurationConverterFromPropToXml converter = new ConfigurationConverterFromPropToXml();
            converter.convert(inFile, outFile);
        }
        
    }
    
}
