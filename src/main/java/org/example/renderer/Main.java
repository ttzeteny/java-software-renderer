package org.example.renderer;

import org.example.renderer.app.App;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App().start());
    }
}