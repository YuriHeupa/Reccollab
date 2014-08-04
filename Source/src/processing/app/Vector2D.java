package processing.app;

import java.awt.geom.Point2D;

/**
 * An extension to the relatively impotent java.awt.geom.Point2D.Double,
 * Vector2D allows mathematical manipulation of 2-component vectors.
 */
public class Vector2D extends Point2D.Float {

    /**
     * The serial version
     */
    private static final long serialVersionUID = -696363522702031304L;

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.geom.Point2D.Double#Point2D.Double()
     */
    public Vector2D(double x, double y) {
        super((float) x, (float) y);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.geom.Point2D.Double#Point2D.Double()
     */
    public Vector2D(float x, float y) {
        super(x, y);
    }

    /**
     * Copy constructor
     */
    public Vector2D(Vector2D v) {
        x = v.x;
        y = v.y;
    }

    /**
     * @return the radius (length, modulus) of the vector in polar coordinates
     */
    public float getR() {
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * Sets the vector's radius, preserving its angle.
     */
    public void setR(double r) {
        double t = getTheta();
        setPolar(r, t);
    }

    /**
     * @return the angle (argument) of the vector in polar coordinates in the
     * range [-pi/2, pi/2]
     */
    public float getTheta() {
        return (float) Math.atan2(y, x);
    }

    /**
     * Sets the vector's angle, preserving its radius.
     */
    public void setTheta(double t) {
        double r = getR();
        setPolar(r, t);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.awt.geom.Point2D.Double#setLocation(double, double)
     */
    public void set(double x, double y) {
        super.setLocation(x, y);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.awt.geom.Point2D.Double#setLocation(Vector2D)
     */
    public void set(Vector2D rhs) {
        super.setLocation(rhs);
    }

    /**
     * Sets the vector given polar arguments.
     *
     * @param r The new radius
     * @param t The new angle, in radians
     */
    public void setPolar(double r, double t) {
        super.setLocation(r * Math.cos(t), r * Math.sin(t));
    }

    /**
     * The sum of the vector and rhs
     */
    public Vector2D plus(Vector2D rhs) {
        return new Vector2D(x + rhs.x, y + rhs.y);
    }

    /**
     * The sum of the vector and scalar
     */
    public Vector2D plusScalar(float scalar) {
        return new Vector2D(x + scalar, y + scalar);
    }

    /**
     * The difference of the vector and rhs: this - rhs
     */
    public Vector2D minus(Vector2D rhs) {
        return new Vector2D(x - rhs.x, y - rhs.y);
    }

    /**
     * Product of the vector and scalar
     */
    public Vector2D multScalar(float scalar) {
        return new Vector2D(scalar * x, scalar * y);
    }

    /**
     * Multiply the vector by other: <this.x*rhs.x, this.y*rhs.y>
     */
    public Vector2D mult(Vector2D rhs) {
        return new Vector2D(x * rhs.x, y * rhs.y);
    }

    /**
     * Divide the the vector by other: <this.x/rhs.x, this.y/rhs.y>
     */
    public Vector2D div(Vector2D rhs) {
        return new Vector2D(x / rhs.x, y / rhs.y);
    }

    /**
     * Divide the the vector by scalar: <this.x/scalar, this.y/scalar>
     */
    public Vector2D divScalar(float scalar) {
        return new Vector2D(x / scalar, y / scalar);
    }

    public boolean equals(Vector2D rhs) {
        return x == rhs.x && y == rhs.y;
    }

    /**
     * An alias for getR()
     *
     * @return the length of this
     */
    public double length() {
        return getR();
    }

    /**
     * Returns a new vector with the same direction as the vector but with
     * length 1, except in the case of zero vectors, which return a copy of
     * themselves.
     */
    public Vector2D unitVector() {
        if (getR() != 0) {
            return new Vector2D(x / getR(), y / getR());
        }
        return new Vector2D(0, 0);
    }

    /**
     * Polar version of the vector, with radius in x and angle in y
     */
    public Vector2D toPolar() {
        return new Vector2D(Math.sqrt(x * x + y * y), Math.atan2(y, x));
    }

    /**
     * Rectangular version of the vector, assuming radius in x and angle in y
     */
    public Vector2D toRect() {
        return new Vector2D(x * Math.cos(y), x * Math.sin(y));
    }

    /**
     * @return Standard string representation of a vector: "<x, y>"
     */
    public String toString() {
        return "<" + x + ", " + y + ">";
    }
}