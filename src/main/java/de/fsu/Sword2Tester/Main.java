package de.fsu.Sword2Tester;

import de.fsu.Sword2Tester.view.Sword2GUI;

import javax.swing.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) throws InterruptedException {
        Sword2GUI sword2GUI = new Sword2GUI();
        sword2GUI.setVisible(true);
        sword2GUI.setBounds(100, 100, 800, 600);
        sword2GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        LOGGER.info("Form initialized..");
    }
}
