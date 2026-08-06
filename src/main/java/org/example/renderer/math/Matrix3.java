package org.example.renderer.math;

public class Matrix3 {

    private final double[] values;

    public Matrix3(double[] values) {
        this.values = values;
    }

    public static Matrix3 identity() {
        return new Matrix3(new double[] {
                1, 0, 0,
                0, 1, 0,
                0, 0, 1
        });
    }

    public static Matrix3 rotationX(double radians) {
        double c = Math.cos(radians);
        double s = Math.sin(radians);
        return new Matrix3(new double[] {
                1, 0, 0,
                0, c, s,
                0, -s, c
        });
    }

    public static Matrix3 rotationY(double radians) {
        double c = Math.cos(radians);
        double s = Math.sin(radians);
        return new Matrix3(new double[] {
                c, 0, -s,
                0, 1, 0,
                s, 0, c
        });
    }

    public static Matrix3 rotationZ(double radians) {
        double c = Math.cos(radians);
        double s = Math.sin(radians);
        return new Matrix3(new double[] {
                c, s, 0,
                -s, c, 0,
                0, 0, 1
        });
    }

    public Matrix3 multiply(Matrix3 other) {
        double[] result = new double[9];

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                for (int i = 0; i < 3; i++) {
                    result[row * 3 + col] += this.values[row * 3 + i] * other.values[i * 3 + col];
                }
            }
        }

        return new Matrix3(result);
    }

    public Vertex transform(Vertex in) {
        return new Vertex(
                in.x * values[0] + in.y * values[3] + in.z * values[6],
                in.x * values[1] + in.y * values[4] + in.z * values[7],
                in.x * values[2] + in.y * values[5] + in.z * values[8]
        );
    }
}
