package org.example.renderer.mesh;

import org.example.renderer.math.Vertex;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public final class Shapes {

    private Shapes() {
    }

    public static Map<String, Supplier<Mesh>> catalog() {
        Map<String, Supplier<Mesh>> catalog = new LinkedHashMap<>();
        catalog.put("Tetrahedron", Shapes::tetrahedron);
        catalog.put("Cube", Shapes::cube);
        catalog.put("Pyramid", Shapes::pyramid);
        return catalog;
    }

    public static Mesh tetrahedron() {
        List<Triangle> triangles = new ArrayList<>();
        triangles.add(new Triangle(
                new Vertex(100, 100, 100),
                new Vertex(-100, -100, 100),
                new Vertex(-100, 100, -100),
                Color.WHITE));
        triangles.add(new Triangle(
                new Vertex(100, 100, 100),
                new Vertex(-100, -100, 100),
                new Vertex(100, -100, -100),
                Color.RED));
        triangles.add(new Triangle(
                new Vertex(-100, 100, -100),
                new Vertex(100, -100, -100),
                new Vertex(100, 100, 100),
                Color.GREEN));
        triangles.add(new Triangle(
                new Vertex(-100, 100, -100),
                new Vertex(100, -100, -100),
                new Vertex(-100, -100, 100),
                Color.BLUE));
        return new Mesh("Tetrahedron", triangles);
    }

    public static Mesh cube() {
        double s = 100;
        Vertex a = new Vertex(-s, -s, -s);
        Vertex b = new Vertex(s, -s, -s);
        Vertex c = new Vertex(s, s, -s);
        Vertex d = new Vertex(-s, s, -s);
        Vertex e = new Vertex(-s, -s, s);
        Vertex f = new Vertex(s, -s, s);
        Vertex g = new Vertex(s, s, s);
        Vertex h = new Vertex(-s, s, s);

        List<Triangle> triangles = new ArrayList<>();
        // Front (+z)
        addQuad(triangles, e, f, g, h, Color.RED);
        // Back (-z)
        addQuad(triangles, b, a, d, c, Color.BLUE);
        // Right (+x)
        addQuad(triangles, f, b, c, g, Color.GREEN);
        // Left (-x)
        addQuad(triangles, a, e, h, d, Color.YELLOW);
        // Top (+y)
        addQuad(triangles, h, g, c, d, Color.CYAN);
        // Bottom (-y)
        addQuad(triangles, a, b, f, e, Color.MAGENTA);
        return new Mesh("Cube", triangles);
    }

    public static Mesh pyramid() {
        double s = 100;
        Vertex apex = new Vertex(0, s, 0);
        Vertex bl = new Vertex(-s, -s, s);
        Vertex br = new Vertex(s, -s, s);
        Vertex fr = new Vertex(s, -s, -s);
        Vertex fl = new Vertex(-s, -s, -s);

        List<Triangle> triangles = new ArrayList<>();
        triangles.add(new Triangle(apex, bl, br, Color.RED));
        triangles.add(new Triangle(apex, br, fr, Color.GREEN));
        triangles.add(new Triangle(apex, fr, fl, Color.BLUE));
        triangles.add(new Triangle(apex, fl, bl, Color.YELLOW));
        // Base (two triangles)
        triangles.add(new Triangle(bl, fr, br, Color.WHITE));
        triangles.add(new Triangle(bl, fl, fr, Color.LIGHT_GRAY));
        return new Mesh("Pyramid", triangles);
    }

    private static void addQuad(List<Triangle> triangles, Vertex v0, Vertex v1, Vertex v2, Vertex v3, Color color) {
        triangles.add(new Triangle(v0, v1, v2, color));
        triangles.add(new Triangle(v0, v2, v3, color));
    }
}
