package org.example.renderer.app;

import org.example.renderer.mesh.Mesh;
import org.example.renderer.mesh.Shapes;
import org.example.renderer.render.Renderer;
import org.example.renderer.scene.Scene;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Container;
import java.util.Map;
import java.util.function.Supplier;

public class App {

    public void start() {
        Scene scene = new Scene();
        Renderer renderer = new Renderer();
        RenderPanel renderPanel = new RenderPanel(scene, renderer);

        JFrame frame = new JFrame("3D Renderer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());

        Map<String, Supplier<Mesh>> catalog = Shapes.catalog();
        JComboBox<String> shapeCombo = new JComboBox<>(catalog.keySet().toArray(new String[0]));
        shapeCombo.setSelectedItem(scene.getMesh().getName());
        shapeCombo.addActionListener(e -> {
            String selected = (String) shapeCombo.getSelectedItem();
            if (selected != null) {
                scene.setMesh(catalog.get(selected).get());
                renderPanel.repaint();
            }
        });

        JPanel topBar = new JPanel();
        topBar.add(new JLabel("Shape:"));
        topBar.add(shapeCombo);
        pane.add(topBar, BorderLayout.NORTH);

        JSlider headingSlider = new JSlider(0, 360, (int) scene.getHeadingDegrees());
        pane.add(headingSlider, BorderLayout.SOUTH);

        JSlider pitchSlider = new JSlider(SwingConstants.VERTICAL, -180, 180, (int) scene.getPitchDegrees());
        pane.add(pitchSlider, BorderLayout.EAST);

        headingSlider.addChangeListener(e -> {
            scene.setHeadingDegrees(headingSlider.getValue());
            renderPanel.repaint();
        });
        pitchSlider.addChangeListener(e -> {
            scene.setPitchDegrees(pitchSlider.getValue());
            renderPanel.repaint();
        });

        pane.add(renderPanel, BorderLayout.CENTER);

        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
