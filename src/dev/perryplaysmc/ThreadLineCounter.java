package dev.perryplaysmc;

import dev.perryplaysmc.filechooser.FileViewer;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.text.CharacterIterator;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.*;

/**
 * Copy Right ©
 * This code is private
 * Owner: PerryPlaysMC
 * From: 02/2021-Now
 * <p>
 * Any attempts to use these program(s) may result in a penalty of up to $1,000 USD
 **/

public class ThreadLineCounter extends Thread {

    private Runnable end;
    private File file;
    private boolean startCount;
    private long lines = 0, chars = 0, files = 0;
    private JTextArea console;
    private FileViewer fileView;
    private SimpleDateFormat format = new SimpleDateFormat("[HH:mm:ss]: ");
    private final String home = System.getProperty("user.home");

    public ThreadLineCounter(JTextArea console, FileViewer fileView) {
        super("Line-Counter");
        this.console = console;
        this.fileView = fileView;
    }

    @Override
    public void run() {
        if(startCount) {
            lines = 0;
            chars = 0;
            files = 0;
            if(file!=null&&file.exists()) {
                long start = System.currentTimeMillis();
                if(file.isDirectory()) {
                    console.append(format.format(new Date()) + "Starting scan: " + getFilePath(file) + "\n");
                    for(File f : getFiles(file)) {
                        if(f.getName().endsWith(".yml") || f.getName().endsWith(".java") || f.getName().endsWith(".json")) {
                            try {
                                files++;
                                console.append(format.format(new Date()) + "Scanning file: " +
                                        getFilePath(f).replace(file.getPath().replace(home, "~"), "") + " Size: "+ size(f.length())+"\n");
                                scanFile(f);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } else {
                    console.append(format.format(new Date()) + "Scanning single file: " + getFilePath(file) + " Size: " + size(file.length()) + "\n");
                    if(file.getName().endsWith(".yml") || file.getName().endsWith(".java") || file.getName().endsWith(".json")) {
                        try {
                            files++;
                            scanFile(file);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
                end.run();
                long end = System.currentTimeMillis();
                if(file.isDirectory())
                console.append(format.format(new Date()) + "Directory size: " + size(file.length()) + "\n");
                console.append(format.format(new Date()) + "Scan complete in " + ((end-start)/1000) + "s\n");
            }
            startCount = false;
        }
        stop();
    }

    private String size(long bytes) {
        if (-1000 < bytes && bytes < 1000) {
            return bytes + " B";
        }
        CharacterIterator ci = new StringCharacterIterator("kMGTPE");
        while (bytes <= -999_950 || bytes >= 999_950) {
            bytes /= 1000;
            ci.next();
        }
        return String.format("%.1f %cB", bytes / 1000.0, ci.current());
    }

    private void scanFile(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileReader(file));
        boolean start = false;
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if(line.isEmpty()) continue;
            A:while(line.charAt(0) == ' ' || line.charAt(0) == '\t') {
                line = line.substring(1);
                if(line.toCharArray().length == 0) break A;
            }
            if(line.length() > 0) {
                if(line.startsWith("/")) {
                    if(line.startsWith("/*"))start = true;
                    continue;
                }
                if(line.endsWith("*/") && start) {
                    start = false;
                    continue;
                }
                if(start) continue;
                chars += line.length();
                lines++;
            }
        }
    }

    private String getFilePath(File file) {
        return file.getPath().replace(home + "/", "~/");
    }


    public boolean isCounting() {
        return startCount;
    }

    public long getCharacters() {
        return chars;
    }

    public long getLines() {
        return lines;
    }

    public void onEnd(Runnable end) {
        this.end = end;
    }

    public long getFiles() {
        return files;
    }

    private List<File> getFiles(File directory) {
        if(directory == null || !directory.isDirectory()) return Collections.singletonList(directory);
        List<File> files = new ArrayList<>();
        if(directory.listFiles() == null) return new ArrayList<>();
        for(File f : directory.listFiles()) {
            if(f.isDirectory() && fileView.hasExtension(directory)) files.addAll(getFiles(f));
            if(f.getName().endsWith(".yml") || f.getName().endsWith(".java") || f.getName().endsWith(".json")) files.add(f);
        }
        return files;
    }



    public void count(File file) {
        startCount = true;
        this.file = file;
        start();
    }

}
