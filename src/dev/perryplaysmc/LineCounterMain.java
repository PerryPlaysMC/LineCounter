package dev.perryplaysmc;

import javax.swing.*;
import java.awt.*;

/**
 * Copy Right ©
 * This code is private
 * Owner: PerryPlaysMC
 * From: 02/2021-Now
 * <p>
 * Any attempts to use these program(s) may result in a penalty of up to $1,000 USD
 **/

public class LineCounterMain {


    public static void main(String[] args) {
        JFrame frame = new JFrame("Line counter");
        frame.setSize(620, 560);
        frame.setMinimumSize(new Dimension(420, 560));
        frame.setContentPane(new LineCounter());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
