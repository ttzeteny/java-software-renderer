package org.example.renderer.app;

import org.example.renderer.render.Renderer;
import org.example.renderer.scene.Scene;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class RenderPanel extends JPanel {

    private final Scene scene;
    private final Renderer renderer;

    public RenderPanel(Scene scene, Renderer renderer) {
        this.scene = scene;
        this.renderer = renderer;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());

        if (getWidth() <= 0 || getHeight() <= 0) {
            return;
        }

        BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        renderer.render(scene.getTriangles(), scene.getViewTransform(), img);
        g2.drawImage(img, 0, 0, null);
    }
}
