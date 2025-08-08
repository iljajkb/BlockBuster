package game.core.entities;

public class MyVector {
    public double x, y;

    public MyVector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public MyVector add(MyVector other) {
        return new MyVector(this.x + other.x, this.y + other.y);
    }

    public MyVector scale(double scalar) {
        return new MyVector(this.x * scalar, this.y * scalar);
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public MyVector normalize() {
        double mag = magnitude();
        return mag == 0 ? new MyVector(0, 0) : new MyVector(x / mag, y / mag);
    }

    public MyVector flipX() {
        return new MyVector(-x, y);
    }

    public MyVector flipY() {
        return new MyVector(x, -y);
    }

    // value-object
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MyVector other)) {
            return false;
        } else {
            return this.x == other.x && this.y == other.y;
        }
    }

}

