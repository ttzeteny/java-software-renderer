package org.example.renderer.mesh;

import java.util.Collections;
import java.util.List;

public class Mesh {

    private final String name;
    private final List<Triangle> triangles;

    public Mesh(String name, List<Triangle> triangles) {
        this.name = name;
        this.triangles = List.copyOf(triangles);
    }

    public String getName() {
        return name;
    }

    public List<Triangle> getTriangles() {
        return Collections.unmodifiableList(triangles);
    }

    @Override
    public String toString() {
        return name;
    }
}
