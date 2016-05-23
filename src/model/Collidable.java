package model;

import java.awt.geom.Point2D;

/**
 * Defines an object which can collide with other objects.
 */
public interface Collidable
{
    /**************************
     * Methods
     **************************/
    public boolean collided(Point2D.Double pt);
}
