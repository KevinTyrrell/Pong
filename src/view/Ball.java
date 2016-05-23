package view;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.Collidable;
import model.Fraction;
import model.Trajectory;

import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.Iterator;


/**
 * Class which represents the ball on the game field.
 */
public class Ball extends Circle
{
    /**************************
     * Instance Variables
     **************************/

    /** Direction in which the ball is heading (0-360). */
    private Trajectory trajectory;
    /** The speed of the ball, in pixels. */
    private double speed;
    /** Preset speed of the ball in case one is not provided. */
    private final double DEFAULT_BALL_SPEED = 5;

    /**************************
     * Constructors
     **************************/

    /**
     * Creates an enhanced Circle used for scoring on the opponent.
     * @param centerX - x coordinate.
     * @param centerY - y coordinate.
     * @param radius - radius of the ball.
     * @param speed - speed of the ball.
     */
    public Ball(double centerX, double centerY, double radius, int speed)
    {
        super(centerX, centerY, radius);
        setFill(Color.WHITE);
        this.speed = speed;
        trajectory = new Trajectory();
    }

    /**
     * Creates an enhanced Circle used for scoring on the opponent.
     * This constructor sets the ball to its' default speed.
     * @param centerX - x coordinate.
     * @param centerY - y coordinate.
     * @param radius - radius of the ball.
     */
    public Ball(double centerX, double centerY, double radius)
    {
        super(centerX, centerY, radius);
        setFill(Color.WHITE);
        speed = DEFAULT_BALL_SPEED;
        trajectory = new Trajectory();
    }

    /**************************
     * Methods
     **************************/

    public void move(Collection<Collidable> collisionList)
    {
        // Get the X,Y location of where the ball WANTS to go to.
        double desiredX = getCenterX() + speed * Math.cos(Math.toRadians((double) trajectory.getAngle()));
        double desiredY = getCenterY() + speed * -1 * Math.sin(Math.toRadians((double) trajectory.getAngle()));
        // Leading edge of the ball, first pixel that could touch other objects.
        Point2D.Double edge = getOuterPoint();

        // Between the edge and the desired points, find the slope.
        Fraction slope = new Fraction(desiredY - edge.getY(), desiredX - edge.getX());
        // Change the slope so the 'run' is 1.
        slope.setNumerator(slope.getNumerator() / Math.abs(slope.getDenominator()));
        slope.setDenominator(slope.getDenominator() / Math.abs(slope.getDenominator()));
        // RISE OVER RUN IS NOW IN THE FORM:
        //  SLOPE       <---- RISE
        //  -----
        //    1         <---- RUN

        // Copy the edge of our ball.
        Point2D.Double trace = new Point2D.Double(edge.getX(), edge.getY());

        // Loop every pixel on our way to the desired x,y.
        while ((edge.getX() > desiredX && trace.getX() > desiredX) || (edge.getX() < desiredX && trace.getX() < desiredX))
        {
            //System.out.println(trace);
            trace.setLocation(trace.getX() + slope.getDenominator(),
                    trace.getY() + slope.getNumerator());

            Iterator<Collidable> iter = collisionList.iterator();
            while (iter.hasNext())
            {
                Collidable obj = iter.next();
                if (obj.collided(trace))
                {
                    setCenterX(trace.getX() - Math.cos(Math.toRadians(trajectory.getAngle())) * getRadius());
                    setCenterY(trace.getY() - Math.sin(Math.toRadians(trajectory.getAngle())) * getRadius());
                    getTrajectory().setAngle((short) (Math.abs(trajectory.getAngle() - 540)));

                    return;
                }
            }
        }

        setCenterX(desiredX);
        setCenterY(desiredY);
    }

    /**
     * Increases the ball's speed by a given amount.
     * @param increase - Amount to increase by.
     */
    public void accelerate(int increase)
    {
    	speed += increase;
    }

    public Point2D.Double getOuterPoint()
    {
        return new Point2D.Double(getCenterX() + getRadius() * Math.cos(Math.toRadians((double) trajectory.getAngle())),
                getCenterY() + getRadius() * -1 * Math.sin(Math.toRadians((double) trajectory.getAngle())));
    }

    /**
     * Resets the balls speed to the standard amount.
     */
    public void resetSpeed()
    {
    	speed = DEFAULT_BALL_SPEED;
    }
    
    public Trajectory getTrajectory()
    {
        return trajectory;
    }
}