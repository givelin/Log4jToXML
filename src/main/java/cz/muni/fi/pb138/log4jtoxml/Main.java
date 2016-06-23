/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Petr
 */


//TODO - TEST - written on train...
public class Main {
    
    private static File getInFile(Scanner scan) {
        File file;
        String path;
        String extension;
                
        while (true) {
            System.out.println("Path to file to be converted: ");
            path = scan.nextLine();
            
            extension = getExtension(path);
            
            if (!(extension.toLowerCase().equals("xml") || extension.toLowerCase().equals("xml"))) {
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
                
                return file;
            }
            
            extension = getExtension(path);
            String inExtension = getExtension(inPath);
            
            if (!((extension.toLowerCase().equals("xml") || extension.toLowerCase().equals("xml")) 
                    && !extension.equals(inExtension) )) {
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
    
    
    private static String getExtension(String path) {
        String extension = "";

        int i = path.lastIndexOf('.');
        if (i > 0) {
            extension = path.substring(i+1);
        }

        return extension;
    }
    
    private static String getPathWithoutExtension(String path) {
        String extension = "";

        int i = path.lastIndexOf('.');
        if (i > 0) {
            extension = path.substring(0, i-1);
        }

        return extension;
    }
    
    public static void main(String[] args) {
        
        String inPath;
        String outPath;
        File inFile;
        File outFile;
        
        Scanner scan = new Scanner(System.in);
        inFile = getInFile(scan);
        outFile = getOutFile(scan, inFile.getAbsolutePath());
        
        if (getExtension(inFile.getAbsolutePath()).equals("xml")){
            //convert XML -> properties
        }
        else {
            //convert properties -> XML
        }
        
    }
    
}
