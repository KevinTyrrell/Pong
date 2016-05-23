package model;

import java.util.Random;

/**
 * Class which represents a given trajectory in a 2D plane.
 */
public class Trajectory
{
    /**************************
     * Instance Variables
     **************************/

    /** The current angle of this trajectory object. */
    private short angle;
    /** The maximum amount of degrees of the unit circle. */
    private final short MAX_DEGREES = 360;

    /**************************
     * Constructors
     **************************/

    /**
     * Constructor if provided an angle.
     * The angle must be fixed in case it lies
     * outside of the bounds of the unit circle.
     * @param angle - Starting angle.
     */
    public Trajectory(short angle)
    {
        this.angle = rationalizeAngle(angle);
    }

    /**
     * Constructor if not provided an angle.
     * Sets the angle to a random value which
     * is either to the left or the right and
     * is within 0-45 degrees up or down from its origin.
     */
    public Trajectory()
    {
        angle = getRandomAngle();
    }

    /**************************
     * Methods
     **************************/

    /**
     * Fixes a given angle so it represents a location on the standard unit circle.
     * For example, 480 degrees would be rationalized as 120 degrees.
     */
    private short rationalizeAngle(short angle)
    {
        // Reduces any angle down to -359 to 359.
        if (Math.abs(angle) > MAX_DEGREES)
            angle = (short) (angle % MAX_DEGREES);

        // Corrects negative angles by going the opposite direction on the unit circle.
        return (angle < 0) ? (short) (MAX_DEGREES - Math.abs(angle)) : angle;
    }

    /**
     * Returns a pseudo-random angle between 45 degrees and
     * 315 degrees (right side) or between 135 degrees and
     * 225 degrees (left side) of the unit circle.
     * @return - random angle.
     */
    public short getRandomAngle()
    {
        Random generator = new Random();
        return Math.random() < 0.5 ? ((short) (135 + generator.nextInt(90)))
                : rationalizeAngle((short) (315 + generator.nextInt(90)));
    }

    /**
     * Adds to this angle by a certain amount of degrees.
     * @param modificaiton - change in our angle.
     */
    public void add(int modificaiton)
    {
        angle = rationalizeAngle((short) (angle + modificaiton));
    }

    public void setAngle(short change)
    {
        this.angle = rationalizeAngle(change);
    }

    public short getAngle() { return angle; }
}