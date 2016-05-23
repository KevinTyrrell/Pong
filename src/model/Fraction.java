package model;

/**
 * Defines a Mathematical fraction.
 */
public class Fraction
{
    /**************************
     * Instance Variables
     **************************/

    private double numerator, denominator;

    /**************************
     * Constructors
     **************************/

    public Fraction(double numerator)
    {
        this.numerator = numerator;
        denominator = 1;
    }

    public Fraction(double numerator, double denominator)
    {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public Fraction()
    {
        numerator = 0;
        denominator = 1;
    }

    /**************************
     * Methods
     **************************/

    public void divide(Fraction other)
    {
        numerator *= other.denominator;
        denominator *= other.numerator;
    }

    public double getNumerator()
    {
        return numerator;
    }

    public void setNumerator(double numerator)
    {
        this.numerator = numerator;
    }

    public double getDenominator()
    {
        return denominator;
    }

    public void setDenominator(double denominator)
    {
        this.denominator = denominator;
    }
}
