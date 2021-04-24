package dev.perryplaysmc.filechooser;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class FileViewer extends FileFilter {

    private Set<String> extensions = new HashSet<>();

    public void addAcceptableFile(String extension) {
        extensions.add(extension);
    }
     
    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return hasExtension(file);
        }
        for(String extension : extensions) {
            if(file.getName().toLowerCase().endsWith(extension.toLowerCase()))return true;
        }
        return false;
    }

    public boolean hasExtension(File directory) {
        if(directory.listFiles()==null||directory.listFiles().length==0)return false;
        for(File file : directory.listFiles())
            if(file.isDirectory()) {
                if(hasExtension(file)) return true;
            }else {
                for(String extension : extensions) {
                    if(file.getName().toLowerCase().endsWith(extension.toLowerCase()))return true;
                }
            }
        return false;
    }
     
    public String getDescription() {
        return "";
    }
}