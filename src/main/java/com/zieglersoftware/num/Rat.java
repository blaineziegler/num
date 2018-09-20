package com.zieglersoftware.num;

import static com.zieglersoftware.assertions.Assertions.fals;
import static com.zieglersoftware.assertions.Assertions.notNull;
import static com.zieglersoftware.num.BigUtil.BI0;
import static com.zieglersoftware.num.BigUtil.BI1;
import static com.zieglersoftware.num.BigUtil.BIM1;
import static com.zieglersoftware.num.BigUtil.bd;
import static com.zieglersoftware.num.BigUtil.bi;
import static com.zieglersoftware.num.BigUtil.digitCount;
import static com.zieglersoftware.num.BigUtil.divide;
import static com.zieglersoftware.num.BigUtil.fraction;
import static com.zieglersoftware.num.BigUtil.round;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import com.zieglersoftware.assertions.Assertions;

/**
 * Immutable class that represents a rational number; i.e., a number that can be
 * expressed exactly as the ratio of two integers (including whole numbers, with
 * a denomiator of one). The fraction will always be viewed in lowest terms when
 * calling methods such as {@link #toString()}, {@link #numerator()}, and
 * {@link #denominator()}.
 * <p>
 * Instances of {@code Rat} are acquired from the static {@code rat()} methods.
 * It is recommended to statically import {@code rat()} for convenience. There
 * are many overloads of {@code rat()} for acquiring {@code Rat} from different
 * inputs. The string overload takes an arbitrary arithmetic expression as its
 * argument, evaluates it, and returns the result as a {@code Rat}. The static
 * instances {@code ZERO}, {@code ONE}, {@code PI}, and {@code E} are also
 * available.
 * <p>
 * Arithmetic methods include {@code negate}, {@code abs}, {@code invert},
 * {@code plus}, {@code minus}, {@code times}, {@code dividedBy}, {@code sqrt},
 * {@code nthRoot}, {@code pow}, {@code exp}, {@code log}, and {@code ln}.
 * Whenever possible, these arithmetic methods return an exact result; for example,
 * 
 * <pre>
 * {@code rat(-8,27).pow(rat(-2,3)) ->  9/4}
 * {@code rat(9,4).log(rat(8,27))   -> -2/3}
 * </pre>
 * 
 * If the result is irrational, a very high-precision approximation is returned
 * (at least 100 significant figures in the decimal representation).
 * <p>
 * Comparison methods {@code equal}, {@code notEqual}, {@code greater}, {@code less},
 * {@code greaterOrEqual}, {@code lessOrEqual}, {@code isPositive}, {@code isNegative},
 * and {@code isZero} are provided, as well as the method {@code isInteger}.
 * <p>
 * All method arguments must not be null unless otherwise stated.
 * <p>
 * Any reasonably sized rational number can be represented exactly, but if a numerator
 * or denominator approaches the magnitude {@code 2}<sup>{@code Integer.MAX_VALUE}</sup>,
 * overflow may occur.
 */
public final class Rat implements Comparable<Rat>
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
	 * Zero
	 */
	public static final Rat ZERO = rat(0);

	/**
	 * One
	 */
	public static final Rat ONE = rat(1);

	/**
	 * A {@code Rat} representation of {@code pi} approximated to 100 decimal places
	 */
	public static final Rat PI = rat("3.1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170680");

	/**
	 * A {@code Rat} representation of {@code e} approximated to 100 decimal places
	 */
	public static final Rat E = rat("2.7182818284590452353602874713526624977572470936999595749669676277240766303535475945713821785251664274");

	private static Rat ofReduced(BigInteger reducedNumerator, BigInteger reducedDenominator)
	{
		if (!BigUtil.isNeg(reducedNumerator) && BigUtil.less(reducedNumerator, FRACTIONAL_CACHE_SIZE) && BigUtil.less(reducedDenominator, FRACTIONAL_CACHE_SIZE))
			return POS_FRACTIONAL_CACHE[reducedNumerator.intValue()][reducedDenominator.intValue()];
		else if (BigUtil.isNeg(reducedNumerator) && BigUtil.less(reducedNumerator.negate(), FRACTIONAL_CACHE_SIZE) && BigUtil.less(reducedDenominator, FRACTIONAL_CACHE_SIZE))
			return NEG_FRACTIONAL_CACHE[reducedNumerator.negate().intValue()][reducedDenominator.intValue()];
		else
			return new Rat(reducedNumerator, reducedDenominator);
	}

	/**
	 * Returns a {@code Rat} equal to {@code numerator/denominator}.
	 * <p>
	 * {@code denominator} cannot be zero.
	 */
	public static Rat rat(BigInteger numerator, BigInteger denominator)
	{
		notNull(numerator, "numerator");
		notNull(denominator, "denominator");
		Assertions.notEqual(denominator, BigInteger.ZERO, "denominator");
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

	private static Rat rat(BigInteger[] ratio)
	{
		return rat(ratio[0], ratio[1]);
	}

	/**
	 * Returns a {@code Rat} equal to {@code value/1}.
	 */
	public static Rat rat(BigInteger value)
	{
		notNull(value, "value");
		if (!BigUtil.isNeg(value) && BigUtil.less(value, WHOLE_CACHE_SIZE))
			return POS_WHOLE_CACHE[value.intValue()];
		else if (BigUtil.isNeg(value) && BigUtil.less(value.negate(), WHOLE_CACHE_SIZE))
			return NEG_WHOLE_CACHE[value.negate().intValue()];
		else
			return new Rat(value, BI1);
	}

	/**
	 * Returns a {@code Rat} equal to {@code value/1}.
	 */
	public static Rat rat(long value)
	{
		return rat(bi(value));
	}

	/**
	 * Returns a {@code Rat} equal to {@code numerator/denominator}.
	 * <p>
	 * {@code denominator} cannot be zero.
	 */
	public static Rat rat(long numerator, long denominator)
	{
		return rat(bi(numerator), bi(denominator));
	}

	/**
	 * Returns a {@code Rat} equal to the given value.
	 */
	public static Rat rat(BigDecimal value)
	{
		notNull(value, "value");
		return rat(fraction(value));
	}

	/**
	 * Returns a {@code Rat} equal to the given value.
	 */
	public static Rat rat(double value)
	{
		fals(Double.isNaN(value), "Cannot be NaN");
		fals(Double.isInfinite(value), "Cannot be infinite");
		return rat(fraction(bd(value)));
	}

	/**
	 * Evaluates the given expression and returns the result as a {@code Rat}.
	 * The expression must be a valid arithmetic expression, and may contain
	 * whitespace, digits, decimal points, parenthesis, and the operators
	 * {@code + - * / ^}.
	 */
	public static Rat rat(String expression)
	{
		return Eval.eval(expression);
	}

	/**
	 * Returns true if this {@code Rat} can be exactly represented with a denominator of one.
	 * <p>
	 * Whether or not it is small enough to be represented as an {@code int} is irrelevant.
	 */
	public boolean isInteger()
	{
		return BigUtil.isOne(denominator);
	}

	/**
	 * Returns true if this {@code Rat} is greater than zero.
	 */
	public boolean isPositive()
	{
		return BigUtil.isPos(numerator);
	}

	/**
	 * Returns true if this {@code Rat} is less than zero.
	 */
	public boolean isNegative()
	{
		return BigUtil.isNeg(numerator);
	}

	/**
	 * Returns true if this {@code Rat} is equal to zero.
	 */
	public boolean isZero()
	{
		return BigUtil.isZero(numerator);
	}

	/**
	 * Returns {@code -1*this}.
	 */
	public Rat negate()
	{
		return ofReduced(numerator.negate(), denominator);
	}

	/**
	 * Returns the absolute value of this {@code Rat}.
	 */
	public Rat abs()
	{
		return isNegative() ? negate() : this;
	}

	/**
	 * Returns {@code 1/this}.
	 * <p>
	 * If {@code this} is zero, an exception is thrown.
	 */
	public Rat invert()
	{
		if (isNegative())
			return ofReduced(denominator.negate(), numerator.negate());
		else
			return ofReduced(denominator, numerator);
	}

	/**
	 * Returns {@code this+other}.
	 */
	public Rat plus(Rat other)
	{
		return rat(Operations.add(this.numerator, this.denominator, other.numerator, other.denominator));
	}

	/**
	 * Returns {@code this-other}.
	 */
	public Rat minus(Rat other)
	{
		return rat(Operations.subtract(this.numerator, this.denominator, other.numerator, other.denominator));
	}

	/**
	 * Returns {@code this*other}.
	 */
	public Rat times(Rat other)
	{
		return rat(Operations.multiply(this.numerator, this.denominator, other.numerator, other.denominator));
	}

	/**
	 * Returns {@code this/other}.
	 * <p>
	 * {@code other} cannot be zero.
	 */
	public Rat dividedBy(Rat other)
	{
		return rat(Operations.divide(this.numerator, this.denominator, other.numerator, other.denominator));
	}

	/**
	 * Returns {@code this}<sup>{@code exponent}</sup>.
	 * <p>
	 * {@code this} and {@code exponent} cannot both be zero. If {@code this} is negative,
	 * the deonominator of {@code exponent} must be odd.
	 */
	public Rat pow(Rat exponent)
	{
		return rat(Operations.pow(this.numerator, this.denominator, exponent.numerator, exponent.denominator));
	}

	/**
	 * Returns the {@code n}th root of {@code this}; i.e. {@code this}<sup>{@code 1/n}</sup>.
	 * <p>
	 * {@code n} must be greater than zero. If {@code this} is negative, {@code n} must be odd.
	 */
	public Rat nthRoot(long n)
	{
		return rat(Operations.nthRoot(this.numerator, this.denominator, n));
	}

	/**
	 * Returns the square root of {@code this}.
	 * <p>
	 * {@code this} cannot be negative.
	 */
	public Rat sqrt()
	{
		return rat(Operations.sqrt(numerator, denominator));
	}

	/**
	 * Returns {@code e}<sup>{@code this}</sup>.
	 */
	public Rat exp()
	{
		return rat(Operations.exp(numerator, denominator));
	}

	/**
	 * Returns the log of {@code this} with the given base.
	 * <p>
	 * {@code this} must be greater than zero. {@code base} must be greater than zero and not equal to one.
	 */
	public Rat log(Rat base)
	{
		return rat(Operations.log(base.numerator, base.denominator, this.numerator, this.denominator));
	}

	/**
	 * Returns the natural log of {@code this}; i.e., the log of {@code this} with base {@code e}.
	 * <p>
	 * {@code this} must be greater than zero.
	 */
	public Rat ln()
	{
		return rat(Operations.ln(numerator, denominator));
	}

	/**
	 * Returns true if {@code this} is numerically equal to {@code other}.
	 * <p>
	 * The comparison is about numeric equivalence, so, for example,
	 * {@code rat(1,2).equal(rat(2,4)) => true}.
	 * <p>
	 * This method is equivalent to {@link #equals(Object)} in the case
	 * that the given object is a non-null {@code Rat}.
	 */
	public boolean equal(Rat other)
	{
		return BigUtil.equal(this.numerator, other.numerator) && BigUtil.equal(this.denominator, other.denominator);
	}

	/**
	 * Returns true if {@code this} is not numerically equal to {@code other}.
	 * <p>
	 * The comparison is about numeric equivalence, so, for example,
	 * {@code rat(1,2).notEqual(rat(2,4)) => false}.
	 */
	public boolean notEqual(Rat other)
	{
		return BigUtil.notEqual(this.numerator, other.numerator) || BigUtil.notEqual(this.denominator, other.denominator);
	}

	/**
	 * Returns true if {@code this} is greater than {@code other}.
	 */
	public boolean greater(Rat other)
	{
		if (BigUtil.equal(this.denominator, other.denominator))
			return BigUtil.greater(this.numerator, other.numerator);
		else
			return BigUtil.greater(this.numerator.multiply(other.denominator), other.numerator.multiply(this.denominator));
	}

	/**
	 * Returns true if {@code this} is less than {@code other}.
	 */
	public boolean less(Rat other)
	{
		if (BigUtil.equal(this.denominator, other.denominator))
			return BigUtil.less(this.numerator, other.numerator);
		else
			return BigUtil.less(this.numerator.multiply(other.denominator), other.numerator.multiply(this.denominator));
	}

	/**
	 * Returns true if {@code this} is greater than or numerically equal to {@code other}.
	 */
	public boolean greaterOrEqual(Rat other)
	{
		if (BigUtil.equal(this.denominator, other.denominator))
			return BigUtil.greaterOrEqual(this.numerator, other.numerator);
		else
			return BigUtil.greaterOrEqual(this.numerator.multiply(other.denominator), other.numerator.multiply(this.denominator));
	}

	/**
	 * Returns true if {@code this} is less than or numerically equal to {@code other}.
	 */
	public boolean lessOrEqual(Rat other)
	{
		if (BigUtil.equal(this.denominator, other.denominator))
			return BigUtil.lessOrEqual(this.numerator, other.numerator);
		else
			return BigUtil.lessOrEqual(this.numerator.multiply(other.denominator), other.numerator.multiply(this.denominator));
	}

	/**
	 * Returns a positive result if {@code this} is greater than {@code other},
	 * a negative result if {@code this} is less than {@code other}, and zero
	 * if {@code this} is numerically equal to {@code other}.
	 */
	@Override
	public int compareTo(Rat other)
	{
		if (BigUtil.equal(this.denominator, other.denominator))
			return this.numerator.compareTo(other.numerator);
		else
			return this.numerator.multiply(other.denominator).compareTo(other.numerator.multiply(this.denominator));
	}

	/**
	 * Returns the numerator of {@code this}, in its reduced-form representation.
	 */
	public BigInteger numerator()
	{
		return numerator;
	}

	/**
	 * Returns the denominator of {@code this}, in its reduced-form representation.
	 */
	public BigInteger denominator()
	{
		return denominator;
	}

	/**
	 * If both the numerator and denominator of this Rat have more digits than the given maximum,
	 * returns a different Rat where both the numerator and denominator have been divided by a power of 10
	 * such that the <i>shorter</i> of the two has the requested number of digits. If rounding is necessary, {@link RoundingMode#HALF_UP}
	 * is used.
	 * <p>
	 * Note that the resulting fraction will be reduced if possible, so may contain different leading digits than this Rat.
	 * <p>
	 * If either the numerator or denominator of this Rat does not exceed the requested number of digits, a Rat of the same value is returned.
	 * <p>
	 * Examples:
	 * 
	 * <pre>
	 * 1234/2345, 3 ->  123/235 
	 * 1234/2345, 4 -> 1234/2345 
	 * 1234/2345, 5 -> 1234/2345
	 *  123/4567, 2 ->   12/457
	 *  123/4561, 2 ->    1/38 (12/456 reduced)
	 *  123/4567, 3 ->  123/4567
	 *  123/4567, 4 ->  123/4567
	 * </pre>
	 */
	public Rat reducePrecision(int maxDigits)
	{
		int leastDigits = Math.min(digitCount(numerator), digitCount(denominator));
		if (leastDigits <= maxDigits)
			return this;
		BigInteger divisor = bi(10).pow(leastDigits - maxDigits);
		BigInteger newNumerator = round(divide(numerator, divisor));
		BigInteger newDenominator = round(divide(denominator, divisor));
		return rat(newNumerator, newDenominator);
	}

	/**
	 * Returns a {@code BigDecimal} representation of {@code this}.
	 */
	public BigDecimal toBigDecimal()
	{
		return BigUtil.divide(numerator, denominator);
	}

	/**
	 * Returns a {@code double} representation of {@code this}. Precision may be lost
	 * because {@code double} has limited precision. If the magnitude of {@code this}
	 * is too large to be represented as a {@code double}, an exception is thrown.
	 */
	public double toDouble()
	{
		BigDecimal bigDecimalValue = toBigDecimal();
		double doubleValue = bigDecimalValue.doubleValue();
		fals(Double.isInfinite(doubleValue), "The magnitude of this value %s is too large to represent as a double", (numerator + "/" + denominator));
		return doubleValue;
	}

	/**
	 * Returns true if the given object is a non-null {@code Rat} that is numerically
	 * equivalent to {@code this}.
	 * <p>
	 * This method is equivalent to {@link #equal(Rat)} in the case that the given object
	 * is a non-null {@code Rat}.
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (!(obj instanceof Rat))
			return false;
		return equal((Rat) obj);
	}

	/**
	 * Returns a hash code of {@code this}.
	 */
	@Override
	public int hashCode()
	{
		return 31 * numerator.hashCode() + denominator.hashCode();
	}

	/**
	 * Returns a string representation of {@code this}.
	 * The representation will be reduced to lowest terms.
	 */
	@Override
	public String toString()
	{
		if (BigUtil.isOne(denominator))
			return String.valueOf(numerator);
		else
			return numerator + "/" + denominator;
	}
}