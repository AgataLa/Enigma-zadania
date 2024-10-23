package org.enigma;

public class Trunk {
    private double radius;
    private double height;
    private double lengthenSpeed;
    private double expandSpeed;
    private TrunkColor color;

    public Trunk(double radius, double height, double growSpeed, double expandSpeed, TrunkColor color) {
        this.radius = radius;
        this.height = height;
        this.lengthenSpeed = growSpeed;
        this.expandSpeed = expandSpeed;
        this.color = color;
    }

    public void grow() {
        this.height += lengthenSpeed;
        this.radius += expandSpeed;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getLengthenSpeed() {
        return lengthenSpeed;
    }

    public void setLengthenSpeed(double lengthenSpeed) {
        this.lengthenSpeed = lengthenSpeed;
    }

    public double getExpandSpeed() {
        return expandSpeed;
    }

    public void setExpandSpeed(double expandSpeed) {
        this.expandSpeed = expandSpeed;
    }

    public TrunkColor getColor() {
        return color;
    }

    public void setColor(TrunkColor color) {
        this.color = color;
    }
}
