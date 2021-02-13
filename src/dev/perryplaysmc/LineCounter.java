package dev.perryplaysmc;
import dev.perryplaysmc.filechooser.JFilePicker;

import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;
import javax.swing.border.*;
/*
 * Created by JFormDesigner on Fri Feb 12 20:19:21 EST 2021
 */



/**
 * @author Perry
 */
public class LineCounter extends JPanel {

    private JTextField file;
    private JButton browse, run;
    private JScrollPane scrollConsole;
    private JLabel filesLabel, files;
    private JTextArea console;
    private JLabel fileLabel;
    private JLabel linesLabel, lines;
    private JLabel charactersLabel, characters;
    private ThreadLineCounter counter;
    private final String home = System.getProperty("user.home");
    private SimpleDateFormat format = new SimpleDateFormat("[HH:mm:ss]: ");
    private Color textColor = new Color(210,210,210);
    public static final Color BACKGROUND_COLOR = new Color(80,80,80);
    public static final Color BACKGROUND_COLOR_PANEL = new Color(45,45,45);

    public LineCounter() {
        initComponents();
        JFilePicker jfp = new JFilePicker(this, file, browse);
        jfp.setMode(JFilePicker.MODE_OPEN);
        jfp.addFileTypeFilter(".folder", "List all folders");
        jfp.addFileTypeFilter(".java", "List all Java files");
        jfp.addFileTypeFilter(".yml", "List all YAML files");
        jfp.addFileTypeFilter(".json", "List all JSON files");
        JFileChooser fileChooser = jfp.getFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChooser.setCurrentDirectory(new File(home + "/"));
        file.setText("~/");
        console.setBackground(BACKGROUND_COLOR);
        console.setForeground(new Color(46, 196, 119));
        scrollConsole.setBorder(new LineBorder(new Color(57, 55, 55),3));
        console.setBorder(new EmptyBorder(5,5,5,5));
        console.setEditable(false);
        setBackground(BACKGROUND_COLOR_PANEL);
        int threshold = 2000;
        JScrollBar vbar = scrollConsole.getVerticalScrollBar();
        vbar.addAdjustmentListener(new AdjustmentListener() {
            int max = vbar.getMaximum();
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                int maxi = e.getAdjustable().getMaximum();
                int val = e.getAdjustable().getValue();
                if(maxi == max) {
                    if(val % 2 != 0) {
                        if(val+1 > maxi) val--;
                        else val++;
                        e.getAdjustable().setValue(val);
                    }
                    return;
                }
                if(maxi % 2 != 0) return;
                if(maxi > max) max = maxi;
                if(maxi-val <= (threshold)) e.getAdjustable().setValue(maxi);
            }
        });
        run.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(counter != null && counter.isCounting())  {
                    console.append(format.format(new Date()) + "Please wait for this process to finish\n\n");
                    return;
                }
                File file = new File(LineCounter.this.file.getText().replace("~/", home + "/").replace("//","/"));
                if(!file.exists()) {
                    console.append("Couldn't find file: " + file.getAbsolutePath() + "\n");
                    return;
                }
                counter = new ThreadLineCounter(console);
                counter.onEnd(() -> {
                    lines.setText(counter.getLines()+"");
                    characters.setText(counter.getCharacters()+"");
                    files.setText(counter.getFiles()+"");
                });
                console.append(format.format(new Date()) + "Scanning\n");
                counter.count(file);

            }
        });
    }

    private void setExtra() {

        fileLabel.setForeground(textColor);
        file.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 2), new EmptyBorder(5,5,5,5)));
        file.setBackground(new Color(60, 60, 60));
        file.setForeground(textColor);
        charactersLabel.setForeground(textColor);
        linesLabel.setForeground(textColor);
        filesLabel.setForeground(textColor);
        characters.setForeground(new Color(86, 175, 149));
        lines.setForeground(new Color(86, 175, 149));
        files.setForeground(new Color(86, 175, 149));
        colorButton(browse);
        colorButton(run);
    }

    private void colorButton(JButton button) {
        button.setOpaque(true);
        button.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 1), new EmptyBorder(5,5,5,5)));
        button.setBackground(new Color(87, 84, 84));
        button.setForeground(new Color(180, 180, 180));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(54, 53, 53));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(87, 84, 84));
            }
        });
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Perry
        file = new JTextField();
        browse = new JButton();
        scrollConsole = new JScrollPane();
        console = new JTextArea();
        linesLabel = new JLabel();
        lines = new JLabel();
        charactersLabel = new JLabel();
        characters = new JLabel();
        run = new JButton();
        fileLabel = new JLabel();
        filesLabel = new JLabel();
        files = new JLabel();
        //---- file ----
        fileLabel.setText("File:");

        //---- browse ----
        browse.setText("Browse");


        setBorder(new CompoundBorder(new TitledBorder(new EmptyBorder(0,0,20, 0),
                "File Line/Character counter", TitledBorder.CENTER, TitledBorder.ABOVE_BOTTOM
                , new Font("Dialog", Font .BOLD,24), new Color(30, 57, 83)), getBorder()));

        setExtra();
        file.setBorder(new LineBorder(Color.black, 2));

        //---- browse ----
        browse.setText("Browse");

        //======== scrollConsole ========
        {
            scrollConsole.setViewportView(console);
        }

        //---- linesLabel ----
        linesLabel.setText("Lines:");

        //---- lines ----
        lines.setText("0");

        //---- charactersLabel ----
        charactersLabel.setText("Characters:");

        //---- characters ----
        characters.setText("0");

        //---- run ----
        run.setText("run");

        //---- fileLabel ----
        fileLabel.setText("File:");

        //---- filesLabel ----
        filesLabel.setText("Files:");

        //---- files ----
        files.setText("0");

        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup()
                                        .addComponent(scrollConsole)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(fileLabel, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(file)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(browse, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                        .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                                .addComponent(linesLabel, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(lines, GroupLayout.PREFERRED_SIZE, 276, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                                .addComponent(charactersLabel, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(characters, GroupLayout.PREFERRED_SIZE, 224, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                                .addComponent(filesLabel, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(files, GroupLayout.PREFERRED_SIZE, 263, GroupLayout.PREFERRED_SIZE)))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 178, Short.MAX_VALUE)
                                                .addComponent(run)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scrollConsole, GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                                .addGap(1, 1, 1)
                                .addGroup(layout.createParallelGroup()
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(linesLabel, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(lines))
                                        .addComponent(run))
                                .addGap(1, 1, 1)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(charactersLabel, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(characters))
                                .addGap(1, 1, 1)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(filesLabel, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(files))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(file, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(fileLabel, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(browse))
                                .addGap(36, 36, 36))
        );
    }

}
