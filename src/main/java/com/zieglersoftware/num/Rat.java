package com.zieglersoftware.num;

import static com.zieglersoftware.assertions.Assertions.fals;
import static com.zieglersoftware.assertions.Assertions.notNull;
import static com.zieglersoftware.num.BigUtil.BI0;
import static com.zieglersoftware.num.BigUtil.BI1;
import static com.zieglersoftware.num.BigUtil.BIM1;
import static com.zieglersoftware.num.BigUtil.bd;
import static com.zieglersoftware.num.BigUtil.bi;
import static com.zieglersoftware.num.BigUtil.fraction;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.zieglersoftware.assertions.Assertions;

/**
 * Immutable class that represents a rational number, i.e., a number that can
 * be expressed exactly as the ratio of two integers (including whole numbers).
 * Any reasonably sized rational number can be represented with infinite
 * precision, but if a numerator or denominator approaches the magnitude
 * {@code 2}<sup>{@code Integer.MAX_VALUE}</sup>, overflow may occur.
 */
public final class Rat
{
	private final BigInteger numerator;
	private final BigInteger denominator;

	private Rat(BigInteger numerator, BigInteger denominator)
	{
		this.numerator = numerator;
		this.denominator = denominator;
	}

	private static final BigInteger WHOLE_CACHE_SIZE = bi(1001);
	private static final BigInteger FRACTIONAL_CACHE_SIZE = bi(101);
	private static final Rat[] POS_WHOLE_CACHE = getWholeCacheValues(true);
	private static final Rat[] NEG_WHOLE_CACHE = getWholeCacheValues(false);
	private static final Rat[][] POS_FRACTIONAL_CACHE = getFractionalCacheValues(true);
	private static final Rat[][] NEG_FRACTIONAL_CACHE = getFractionalCacheValues(false);

	private static Rat[] getWholeCacheValues(boolean positive)
	{
		Rat[] cache = new Rat[WHOLE_CACHE_SIZE.intValue()];
		for (BigInteger value = BI0; BigUtil.less(value, WHOLE_CACHE_SIZE); value = value.add(BI1))
			cache[value.intValue()] = new Rat(
				value.multiply(positive ? BI1 : BIM1),
				BI1);
		return cache;
	}

	private static Rat[][] getFractionalCacheValues(boolean positive)
	{
		Rat[][] cache = new Rat[FRACTIONAL_CACHE_SIZE.intValue()][FRACTIONAL_CACHE_SIZE.intValue()];
		for (BigInteger numerator = BI0; BigUtil.less(numerator, FRACTIONAL_CACHE_SIZE); numerator = numerator.add(BI1))
		{
			for (BigInteger denominator = BI1; BigUtil.less(denominator, FRACTIONAL_CACHE_SIZE); denominator = denominator.add(BI1)) // Start at 1
			{
				BigInteger[] reduced = Operations.reduce(numerator, denominator);
				BigInteger reducedNumerator = reduced[0];
				BigInteger reducedDenominator = reduced[1];
				cache[numerator.intValue()][denominator.intValue()] = new Rat(
					reducedNumerator.multiply(positive ? BI1 : BIM1),
					reducedDenominator);
			}
		}
		return cache;
	}

	/**
	 * pi approximation to 100 decimal places
	 */
	public static final Rat PI = of("3.1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170680");

	/**
	 * e approximation to 100 decimal places
	 */
	public static final Rat E = of("2.7182818284590452353602874713526624977572470936999595749669676277240766303535475945713821785251664274");

	private static Rat ofReduced(BigInteger reducedNumerator, BigInteger reducedDenominator)
	{
		if (!BigUtil.isNeg(reducedNumerator) && BigUtil.less(reducedNumerator, FRACTIONAL_CACHE_SIZE) && BigUtil.less(reducedDenominator, FRACTIONAL_CACHE_SIZE))
			return POS_FRACTIONAL_CACHE[reducedNumerator.intValue()][reducedDenominator.intValue()];
		else if (BigUtil.isNeg(reducedNumerator) && BigUtil.less(reducedNumerator.negate(), FRACTIONAL_CACHE_SIZE) && BigUtil.less(reducedDenominator, FRACTIONAL_CACHE_SIZE))
			return NEG_FRACTIONAL_CACHE[reducedNumerator.negate().intValue()][reducedDenominator.intValue()];
		else
			return new Rat(reducedNumerator, reducedDenominator);
	}

	public static Rat of(BigInteger numerator, BigInteger denominator)
	{
		notNull(numerator, "numerator");
		notNull(denominator, "denominator");
		Assertions.notEqual(denominator, 0, "denominator");
		if (BigUtil.isNeg(denominator))
		{
			numerator = numerator.negate();
			denominator = denominator.negate();
		}
		if (!BigUtil.isNeg(numerator) && BigUtil.less(numerator, FRACTIONAL_CACHE_SIZE) && BigUtil.less(denominator, FRACTIONAL_CACHE_SIZE))
			return POS_FRACTIONAL_CACHE[numerator.intValue()][denominator.intValue()];
		else if (BigUtil.isNeg(numerator) && BigUtil.less(numerator.negate(), FRACTIONAL_CACHE_SIZE) && BigUtil.less(denominator, FRACTIONAL_CACHE_SIZE))
			return NEG_FRACTIONAL_CACHE[numerator.negate().intValue()][denominator.intValue()];
		else
		{
			BigInteger[] reduced = Operations.reduce(numerator, denominator);
			BigInteger reducedNumerator = reduced[0];
			BigInteger reducedDenominator = reduced[1];
			if (!BigUtil.isNeg(reducedNumerator) && BigUtil.less(reducedNumerator, FRACTIONAL_CACHE_SIZE) && BigUtil.less(reducedDenominator, FRACTIONAL_CACHE_SIZE))
				return POS_FRACTIONAL_CACHE[reducedNumerator.intValue()][reducedDenominator.intValue()];
			else if (BigUtil.isNeg(reducedNumerator) && BigUtil.less(reducedNumerator.negate(), FRACTIONAL_CACHE_SIZE) && BigUtil.less(reducedDenominator, FRACTIONAL_CACHE_SIZE))
				return NEG_FRACTIONAL_CACHE[reducedNumerator.negate().intValue()][reducedDenominator.intValue()];
			else
				return new Rat(reducedNumerator, reducedDenominator);
		}
	}

	private static Rat of(BigInteger[] ratio)
	{
		return of(ratio[0], ratio[1]);
	}

	public static Rat of(BigInteger value)
	{
		notNull(value, "value");
		if (!BigUtil.isNeg(value) && BigUtil.less(value, WHOLE_CACHE_SIZE))
			return POS_WHOLE_CACHE[value.intValue()];
		else if (BigUtil.isNeg(value) && BigUtil.less(value.negate(), WHOLE_CACHE_SIZE))
			return NEG_WHOLE_CACHE[value.negate().intValue()];
		else
			return new Rat(value, BI1);
	}

	public static Rat of(long value)
	{
		return of(bi(value));
	}

	public static Rat of(long numerator, long denominator)
	{
		return of(bi(numerator), bi(denominator));
	}

	public static Rat of(BigDecimal value)
	{
		notNull(value, "value");
		return of(fraction(value));
	}

	public static Rat of(double value)
	{
		fals(Double.isNaN(value), "Cannot be NaN");
		fals(Double.isInfinite(value), "Cannot be infinite");
		return of(fraction(bd(value)));
	}

	/**
	 * Evaluates the given expression and returns the result as a {@code Rat}.
	 * The expression must be a valid arithmetic expression, and may contain
	 * whitespace, digits, decimal points, parenthesis, and the operators
	 * {@code + - * / ^}.
	 */
	public static Rat of(String expression)
	{
		return Eval.eval(expression);
	}

	public boolean isInteger()
	{
		return BigUtil.isOne(denominator);
	}

	public boolean isPositive()
	{
		return BigUtil.isPos(numerator);
	}

	public boolean isNegative()
	{
		return BigUtil.isNeg(numerator);
	}

	public boolean isZero()
	{
		return BigUtil.isZero(numerator);
	}

	public Rat negate()
	{
		return ofReduced(numerator.negate(), denominator);
	}

	public Rat abs()
	{
		return isNegative() ? negate() : this;
	}

	public Rat invert()
	{
		if (isNegative())
			return ofReduced(denominator.negate(), numerator.negate());
		else
			return ofReduced(denominator, numerator);
	}

	public Rat plus(Rat other)
	{
		return of(Operations.add(this.numerator, this.denominator, other.numerator, other.denominator));
	}

	public Rat minus(Rat other)
	{
		return of(Operations.subtract(this.numerator, this.denominator, other.numerator, other.denominator));
	}

	public Rat times(Rat other)
	{
		return of(Operations.multiply(this.numerator, this.denominator, other.numerator, other.denominator));
	}

	public Rat dividedBy(Rat other)
	{
		return of(Operations.divide(this.numerator, this.denominator, other.numerator, other.denominator));
	}

	public Rat pow(Rat exponent)
	{
		return of(Operations.pow(this.numerator, this.denominator, exponent.numerator, exponent.denominator));
	}

	public Rat ln()
	{
		return of(Operations.ln(this.numerator, this.denominator));
	}

	public Rat log(Rat base)
	{
		return of(Operations.log(base.numerator, base.denominator, this.numerator, this.denominator));
	}

	public boolean equal(Rat other)
	{
		return BigUtil.equal(this.numerator, other.numerator) && BigUtil.equal(this.denominator, other.denominator);
	}

	public boolean notEqual(Rat other)
	{
		return BigUtil.notEqual(this.numerator, other.numerator) || BigUtil.notEqual(this.denominator, other.denominator);
	}

	public boolean greater(Rat other)
	{
		if (BigUtil.equal(this.denominator, other.denominator))
			return BigUtil.greater(this.numerator, other.numerator);
		else
			return BigUtil.greater(this.numerator.multiply(other.denominator), other.numerator.multiply(this.denominator));
	}

	public boolean less(Rat other)
	{
		if (BigUtil.equal(this.denominator, other.denominator))
			return BigUtil.less(this.numerator, other.numerator);
		else
			return BigUtil.less(this.numerator.multiply(other.denominator), other.numerator.multiply(this.denominator));
	}

	public boolean greaterOrEqual(Rat other)
	{
		if (BigUtil.equal(this.denominator, other.denominator))
			return BigUtil.greaterOrEqual(this.numerator, other.numerator);
		else
			return BigUtil.greaterOrEqual(this.numerator.multiply(other.denominator), other.numerator.multiply(this.denominator));
	}

	public boolean lessOrEqual(Rat other)
	{
		if (BigUtil.equal(this.denominator, other.denominator))
			return BigUtil.lessOrEqual(this.numerator, other.numerator);
		else
			return BigUtil.lessOrEqual(this.numerator.multiply(other.denominator), other.numerator.multiply(this.denominator));
	}

	public BigInteger numerator()
	{
		return numerator;
	}

	public BigInteger denominator()
	{
		return denominator;
	}

	public BigDecimal toBigDecimal()
	{
		return BigUtil.divide(numerator, denominator);
	}

	public double toDouble()
	{
		BigDecimal bigDecimalValue = toBigDecimal();
		double doubleValue = bigDecimalValue.doubleValue();
		fals(Double.isInfinite(doubleValue), "The magnitude of this value %s is too large to represent as a double", (numerator + "/" + denominator));
		return doubleValue;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof Rat))
			return false;
		Rat other = (Rat) obj;
		return equal(other);
	}

	@Override
	public int hashCode()
	{
		return 31 * numerator.hashCode() + denominator.hashCode();
	}

	@Override
	public String toString()
	{
		if (BigUtil.isOne(denominator))
			return String.valueOf(numerator);
		else
			return numerator + "/" + denominator;
	}
}