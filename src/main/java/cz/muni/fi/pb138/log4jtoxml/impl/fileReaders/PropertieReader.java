/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.log4jtoxml.impl.fileReaders;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 *
 * @author Jakub
 */
public class PropertieReader {
    
    private static Map<String,String> lines;
    
    private static Consumer<String> line = new Consumer<String>() {

        public void accept(String s) {
            if(s!=null) {
                String[] parseS = s.split("=");
                lines.put(parseS[0], parseS[1]);
            }
        }
    };
    
    public static Map<String, String> readPropertieFile(File file) throws IOException {
        lines = new HashMap<>();
        Path path = file.toPath();
        Files.lines(path).forEach(line);
        return lines;
    }
}
