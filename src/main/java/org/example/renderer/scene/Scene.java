package org.example.renderer.scene;

import org.example.renderer.math.Matrix3;
import org.example.renderer.mesh.Mesh;
import org.example.renderer.mesh.Shapes;
import org.example.renderer.mesh.Triangle;

import java.util.List;

public class Scene {

    private Mesh mesh;
    private double headingDegrees;
    private double pitchDegrees;

    public Scene() {
        this.mesh = Shapes.tetrahedron();
        this.headingDegrees = 180;
        this.pitchDegrees = 0;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public List<Triangle> getTriangles() {
        return mesh.getTriangles();
    }

    public void setHeadingDegrees(double headingDegrees) {
        this.headingDegrees = headingDegrees;
    }

    public void setPitchDegrees(double pitchDegrees) {
        this.pitchDegrees = pitchDegrees;
    }

    public double getHeadingDegrees() {
        return headingDegrees;
    }

    public double getPitchDegrees() {
        return pitchDegrees;
    }

    public Matrix3 getViewTransform() {
        Matrix3 headingTransform = Matrix3.rotationY(Math.toRadians(headingDegrees));
        Matrix3 pitchTransform = Matrix3.rotationX(Math.toRadians(pitchDegrees));
        return headingTransform.multiply(pitchTransform);
    }
}
