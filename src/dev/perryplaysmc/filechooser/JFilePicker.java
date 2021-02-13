package dev.perryplaysmc.filechooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class JFilePicker{
    private JPanel spr;
    private JTextField textField;

    private JFileChooser fileChooser;

    private int mode;
    public static final int MODE_OPEN = 1;
    public static final int MODE_SAVE = 2;
    private final String home = System.getProperty("user.home");
    private Color textColor = new Color(210, 210, 210);

    public JFilePicker(JPanel spr, JTextField text, JButton button) {
        this.spr = spr;
        fileChooser = new JFileChooser();
        textField = text;
        changeColor(fileChooser);
        fileChooser.setOpaque(true);
        fileChooser.setBackground(new Color(45,45,45));
        button.addActionListener(this::buttonActionPerformed);
    }


    void changeColor(JComponent panel) {
        for(Component comp : panel.getComponents()) {
            if(comp instanceof JScrollPane) {
                ((JScrollPane) comp).setOpaque(true);
                comp.setBackground(new Color(80,80,80));
                changeColor((JScrollPane)comp);
                continue;
            }
            if(comp instanceof JPanel) {
                JPanel pan = (JPanel) comp;
                pan.setOpaque(true);
                pan.setBackground(new Color(45,45,45));
                changeColor(pan);
                continue;
            }
            if(comp instanceof JViewport) {
                JViewport port = (JViewport) comp;
                port.setOpaque(true);
                port.setBackground(new Color(80,80,80));
                changeColor(port);
                continue;
            }
            if(comp instanceof JList) {
                JList<?> list = (JList<?>) comp;
                list.setOpaque(true);
                list.setBackground(new Color(80,80,80));
                comp.setForeground(textColor);
                continue;
            }
            if(comp instanceof JScrollBar) {
                JScrollBar bar = (JScrollBar) comp;
                bar.setOpaque(true);
                bar.setBackground(new Color(80,80,80));
                continue;
            }
            comp.setBackground(new Color(80,80,80));
            comp.setForeground(textColor);
        }
    }



    private void buttonActionPerformed(ActionEvent evt) {
        if (mode == MODE_OPEN) {
            File file = new File(textField.getText().replace("~/", home + "/").replace("//", "/"));
            if(file.exists()) fileChooser.setSelectedFile(file);
            if (fileChooser.showOpenDialog(spr) == JFileChooser.APPROVE_OPTION) {
                textField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        } else if (mode == MODE_SAVE) {
            if (fileChooser.showSaveDialog(spr) == JFileChooser.APPROVE_OPTION) {
                textField.setText(fileChooser.getSelectedFile().getAbsolutePath().replace(home, "~"));
            }
        }
    }

    public void addFileTypeFilter(String extension, String description) {
        FileTypeFilter filter = new FileTypeFilter(extension, description);
        fileChooser.addChoosableFileFilter(filter);
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public String getSelectedFilePath() {
        return textField.getText();
    }

    public JFileChooser getFileChooser() {
        return this.fileChooser;
    }
}