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
    private JTextArea console;
    private JLabel fileLabel;
    private JLabel linesLabel, lines;
    private JLabel charactersLabel, characters;
    private ThreadLineCounter counter;
    private final String home = System.getProperty("user.home");
    private SimpleDateFormat format = new SimpleDateFormat("[HH:mm:ss]: ");
    private Color textColor = new Color(210, 210, 210);

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
        fileChooser.setCurrentDirectory(new File("/Users/vincente/workspace/DynamicPlugins/DynamicChat"));
        file.setText("~/workspace/DynamicPlugins/DynamicChat");
        console.setBackground(new Color(80, 80, 80));
        console.setForeground(new Color(46, 196, 119));
        scrollConsole.setBorder(new LineBorder(new Color(57, 55, 55),3));
        console.setBorder(new EmptyBorder(5,5,5,5));
        console.setEditable(false);
        setBackground(new Color(45,45,45));
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
                if(!file.exists()) return;
                counter = new ThreadLineCounter(console);
                counter.onEnd(() -> {
                    lines.setText(counter.getLines()+"");
                    characters.setText(counter.getCharacters()+"");
                });
                console.append(format.format(new Date()) + "Scanning");
                counter.count(file);

            }
        });
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Perry
        fileLabel = new JLabel();
        file = new JTextField();
        browse = new JButton();
        scrollConsole = new JScrollPane();
        console = new JTextArea();
        linesLabel = new JLabel();
        lines = new JLabel();
        charactersLabel = new JLabel();
        characters = new JLabel();
        run = new JButton();

        characters.setForeground(new Color(86, 175, 149));
        lines.setForeground(new Color(86, 175, 149));
        //---- file ----
        fileLabel.setText("File:");
        fileLabel.setForeground(textColor);
        file.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 2), new EmptyBorder(5,5,5,5)));
        file.setBackground(new Color(60, 60, 60));
        file.setForeground(textColor);

        //---- browse ----
        browse.setText("Browse");
        browse.setOpaque(true);
        browse.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 1), new EmptyBorder(5,5,5,5)));
        browse.setBackground(new Color(87, 84, 84));
        browse.setForeground(new Color(180, 180, 180));
        browse.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                browse.setBackground(new Color(54, 53, 53));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                browse.setBackground(new Color(87, 84, 84));
            }
        });


        run.setOpaque(true);
        run.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 1), new EmptyBorder(5,5,5,5)));
        run.setBackground(new Color(87, 84, 84));
        run.setForeground(new Color(180, 180, 180));
        run.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                run.setBackground(new Color(54, 53, 53));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                run.setBackground(new Color(87, 84, 84));
            }
        });
        setBorder(new CompoundBorder(new TitledBorder(new EmptyBorder(0,0,20, 0),
                "File Line/Character counter", TitledBorder.CENTER, TitledBorder.ABOVE_BOTTOM
                , new Font("Dialog", Font .BOLD,24), new Color(30, 57, 83)), getBorder()));

        //======== scrollConsole ========
        {
            scrollConsole.setViewportView(console);
        }

        //---- linesLabel ----
        linesLabel.setText("Lines:");
        linesLabel.setForeground(textColor);

        //---- lines ----
        lines.setText("0");

        //---- charactersLabel ----
        charactersLabel.setText("Characters:");
        charactersLabel.setForeground(textColor);

        //---- characters ----
        characters.setText("0");

        //---- button1 ----
        run.setText("run");

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
                                                .addComponent(file, GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(browse, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(linesLabel, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lines, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(run))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(charactersLabel, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(characters, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scrollConsole, GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup()
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(lines)
                                                .addComponent(linesLabel, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(run))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(charactersLabel, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(characters))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(browse)
                                        .addComponent(file, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(fileLabel, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
                                .addGap(47, 47, 47))
        );
    }

}
