package org.example.renderer.render;

import org.example.renderer.math.Matrix3;
import org.example.renderer.math.Vertex;
import org.example.renderer.mesh.Triangle;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

public class Renderer {

    public void render(List<Triangle> triangles, Matrix3 transform, BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        double halfWidth = width / 2.0;
        double halfHeight = height / 2.0;

        double[] zBuffer = new double[width * height];
        for (int q = 0; q < zBuffer.length; q++) {
            zBuffer[q] = Double.NEGATIVE_INFINITY;
        }

        for (Triangle t : triangles) {
            Vertex v1 = transform.transform(t.v1);
            Vertex v2 = transform.transform(t.v2);
            Vertex v3 = transform.transform(t.v3);

            v1.x += halfWidth;
            v1.y += halfHeight;
            v2.x += halfWidth;
            v2.y += halfHeight;
            v3.x += halfWidth;
            v3.y += halfHeight;

            int minX = (int) Math.max(0, Math.ceil(Math.min(v1.x, Math.min(v2.x, v3.x))));
            int maxX = (int) Math.min(width - 1, Math.floor(Math.max(v1.x, Math.max(v2.x, v3.x))));
            int minY = (int) Math.max(0, Math.ceil(Math.min(v1.y, Math.min(v2.y, v3.y))));
            int maxY = (int) Math.min(height - 1, Math.floor(Math.max(v1.y, Math.max(v2.y, v3.y))));

            double triangleArea =
                    (v1.y - v3.y) * (v2.x - v3.x) + (v2.y - v3.y) * (v3.x - v1.x);

            Vertex ab = new Vertex(
                    v2.x - v1.x,
                    v2.y - v1.y,
                    v2.z - v1.z
            );
            Vertex ac = new Vertex(
                    v3.x - v1.x,
                    v3.y - v1.y,
                    v3.z - v1.z
            );
            Vertex norm = new Vertex(
                    ab.y * ac.z - ab.z * ac.y,
                    ab.z * ac.x - ab.x * ac.z,
                    ab.x * ac.y - ab.y * ac.x
            );
            double normalLength =
                    Math.sqrt(norm.x * norm.x + norm.y * norm.y + norm.z * norm.z);
            norm.x /= normalLength;
            norm.y /= normalLength;
            norm.z /= normalLength;

            double angleCos = Math.abs(norm.z);

            for (int y = minY; y <= maxY; y++) {
                for (int x = minX; x <= maxX; x++) {
                    double b1 = ((y - v3.y) * (v2.x - v3.x) + (v2.y - v3.y) * (v3.x - x)) / triangleArea;
                    double b2 = ((y - v1.y) * (v3.x - v1.x) + (v3.y - v1.y) * (v1.x - x)) / triangleArea;
                    double b3 = ((y - v2.y) * (v1.x - v2.x) + (v1.y - v2.y) * (v2.x - x)) / triangleArea;
                    if (b1 >= 0 && b1 <= 1 && b2 >= 0 && b2 <= 1 && b3 >= 0 && b3 <= 1) {
                        double depth = b1 * v1.z + b2 * v2.z + b3 * v3.z;
                        int zIndex = y * width + x;
                        if (zBuffer[zIndex] < depth) {
                            Color shaded = Shading.getShade(t.color, angleCos);
                            img.setRGB(x, y, shaded.getRGB());
                            zBuffer[zIndex] = depth;
                        }
                    }
                }
            }
        }
    }
}
