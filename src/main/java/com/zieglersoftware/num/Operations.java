package com.zieglersoftware.num;

import static com.zieglersoftware.assertions.Assertions.fals;
import static com.zieglersoftware.assertions.Assertions.notEqual;
import static com.zieglersoftware.assertions.Assertions.tru;
import static com.zieglersoftware.num.BigUtil.BI1;
import static com.zieglersoftware.num.BigUtil.bi;
import static com.zieglersoftware.num.BigUtil.equal;
import static com.zieglersoftware.num.BigUtil.fraction;
import static com.zieglersoftware.num.BigUtil.isNeg;
import static com.zieglersoftware.num.BigUtil.isOne;
import static com.zieglersoftware.num.BigUtil.isPos;
import static com.zieglersoftware.num.BigUtil.isZero;
import static com.zieglersoftware.num.BigUtil.less;
import static com.zieglersoftware.num.BigUtil.nthRoot;
import static com.zieglersoftware.num.MathUtil.gcd;
import static com.zieglersoftware.num.MathUtil.primeFactors;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;

/**
 * Package-private utility class that offers static methods to perform operations on ratios.
 * Unless otherwise stated, all methods expect the given numerators and denominators to be
 * non-null and in reduced form.
 */
final class Operations
{
	private Operations()
	{
	}

	private static final BigInteger[] ZERO = new BigInteger[] { BigInteger.ZERO, BigInteger.ONE };
	private static final BigInteger[] ONE = new BigInteger[] { BigInteger.ONE, BigInteger.ONE };

	/**
	 * Returns the reduced form of the given numerator and denominator.
	 * (The values do not need to already be reduced.) Does not change
	 * the sign of either the numerator or denominator.
	 */
	public static BigInteger[] reduce(BigInteger numerator, BigInteger denominator)
	{
		BigInteger gcd = numerator.gcd(denominator);
		return new BigInteger[] { numerator.divide(gcd), denominator.divide(gcd) };
	}

	/**
	 * Returns a numerator and denominator of the result, not necessarily reduced
	 */
	public static BigInteger[] add(BigInteger aNumerator, BigInteger aDenominator, BigInteger bNumerator, BigInteger bDenominator)
	{
		return new BigInteger[] {
				aNumerator.multiply(bDenominator).add(bNumerator.multiply(aDenominator)),
				aDenominator.multiply(bDenominator)
		};
	}

	/**
	 * Returns a numerator and denominator of the result, not necessarily reduced
	 */
	public static BigInteger[] subtract(BigInteger aNumerator, BigInteger aDenominator, BigInteger bNumerator, BigInteger bDenominator)
	{
		return new BigInteger[] {
				aNumerator.multiply(bDenominator).subtract(bNumerator.multiply(aDenominator)),
				aDenominator.multiply(bDenominator)
		};
	}

	/**
	 * Returns a numerator and denominator of the result, not necessarily reduced
	 */
	public static BigInteger[] multiply(BigInteger aNumerator, BigInteger aDenominator, BigInteger bNumerator, BigInteger bDenominator)
	{
		return new BigInteger[] {
				aNumerator.multiply(bNumerator),
				aDenominator.multiply(bDenominator)
		};
	}

	/**
	 * Returns a numerator and denominator of the result, not necessarily reduced.
	 * <p>
	 * {@code bNumerator} cannot be 0.
	 */
	public static BigInteger[] divide(BigInteger aNumerator, BigInteger aDenominator, BigInteger bNumerator, BigInteger bDenominator)
	{
		notEqual(bNumerator, 0, "bNumerator");
		return new BigInteger[] {
				aNumerator.multiply(bDenominator),
				aDenominator.multiply(bNumerator)
		};
	}

	/**
	 * Returns a numerator and denominator of the result, not necessarily reduced.
	 * <p>
	 * {@code baseNumerator} and {@code exponentNumerator} cannot both be 0. If
	 * {@code baseNumerator} is negative, {@code exponentDenominator} must be odd.
	 */
	public static BigInteger[] pow(BigInteger baseNumerator, BigInteger baseDenominator, BigInteger exponentNumerator, BigInteger exponentDenominator)
	{
		boolean baseNumeratorZero = isZero(baseNumerator);
		boolean exponentNumeratorZero = isZero(exponentNumerator);
		fals(baseNumeratorZero && exponentNumeratorZero, "Either the base or exponent must be nonzero");
		if (baseNumeratorZero)
			return ZERO;
		if (exponentNumeratorZero)
			return ONE;
		if (isOne(baseNumerator) && isOne(baseDenominator))
			return ONE;
		boolean negativeExponent = isNeg(exponentNumerator);
		exponentNumerator = negativeExponent ? exponentNumerator.negate() : exponentNumerator;
		BigInteger numeratorToPower = BigUtil.pow(baseNumerator, exponentNumerator).toBigInteger();
		BigInteger denominatorToPower = BigUtil.pow(baseDenominator, exponentNumerator).toBigInteger();
		if (isOne(exponentDenominator))
		{
			if (negativeExponent)
				return new BigInteger[] { denominatorToPower, numeratorToPower };
			else
				return new BigInteger[] { numeratorToPower, denominatorToPower };
		}
		BigDecimal numeratorToPowerAndRoot = nthRoot(numeratorToPower, exponentDenominator);
		BigInteger[] numeratorToPowerAndRootAsFraction = fraction(numeratorToPowerAndRoot);
		BigDecimal denominatorToPowerAndRoot = nthRoot(denominatorToPower, exponentDenominator);
		BigInteger[] denominatorToPowerAndRootAsFraction = fraction(denominatorToPowerAndRoot);
		if (negativeExponent)
			return new BigInteger[] { numeratorToPowerAndRootAsFraction[1].multiply(denominatorToPowerAndRootAsFraction[0]), numeratorToPowerAndRootAsFraction[0].multiply(denominatorToPowerAndRootAsFraction[1]) };
		else
			return new BigInteger[] { numeratorToPowerAndRootAsFraction[0].multiply(denominatorToPowerAndRootAsFraction[1]), numeratorToPowerAndRootAsFraction[1].multiply(denominatorToPowerAndRootAsFraction[0]) };
	}

	/**
	 * Returns a numerator and denominator of the result, not necessarily reduced.
	 * <p>
	 * {@code numerator} must be greater than 0.
	 */
	public static BigInteger[] ln(BigInteger numerator, BigInteger denominator)
	{
		tru(isPos(numerator), "numerator must be greater than 0. Was %s", numerator);
		if (isOne(numerator) && isOne(denominator))
			return ZERO;
		if (equal(numerator, Rat.E.numerator()) && equal(denominator, Rat.E.denominator()))
			return ONE;
		BigDecimal val = BigUtil.divide(numerator, denominator);
		BigDecimal ln = BigUtil.ln(val);
		return fraction(ln);
	}

	private static final BigInteger MAX_INT_TO_FACTOR = bi(1000000000);

	/**
	 * Returns a numerator and denominator of the result, not necessarily reduced.
	 * <p>
	 * {@code baseNumerator} must be greater than 0, and {@code baseNumerator} and
	 * {@code baseDenominator} cannot both be 1. {@code valNumerator} must be greater
	 * than 0.
	 */
	public static BigInteger[] log(BigInteger baseNumerator, BigInteger baseDenominator, BigInteger valNumerator, BigInteger valDenominator)
	{
		tru(isPos(baseNumerator), "base must be greater than 0. Was %s/%s", baseNumerator, baseDenominator);
		boolean baseNumeratorOne = equal(baseNumerator, BI1);
		boolean baseDenominatorOne = equal(baseDenominator, BI1);
		boolean valNumeratorOne = equal(valNumerator, BI1);
		boolean valDenominatorOne = equal(valDenominator, BI1);
		fals(isOne(baseNumerator) && baseDenominatorOne, "base cannot be 1");
		tru(isPos(valNumerator), "val must be greater than 0. Was %s/%s", valNumerator, valDenominator);
		if (valNumeratorOne && valDenominatorOne)
			return ZERO;
		if (equal(baseNumerator, valNumerator) && equal(baseDenominator, valDenominator))
			return ONE;
		if (less(baseNumerator, MAX_INT_TO_FACTOR) &&
			less(baseDenominator, MAX_INT_TO_FACTOR) &&
			less(valNumerator, MAX_INT_TO_FACTOR) &&
			less(valDenominator, MAX_INT_TO_FACTOR))
		{
			if (baseDenominatorOne || valDenominatorOne || baseNumeratorOne || valNumeratorOne)
			{
				if (baseDenominatorOne && valDenominatorOne)
				{
					Optional<BigInteger[]> rationalResult = intLog(baseNumerator.longValueExact(), valNumerator.longValueExact());
					if (rationalResult.isPresent())
						return rationalResult.get();
				}
				else if (baseNumeratorOne && valDenominatorOne)
				{
					Optional<BigInteger[]> negatedRationalResult = intLog(baseDenominator.longValueExact(), valNumerator.longValueExact());
					if (negatedRationalResult.isPresent())
						return new BigInteger[] { negatedRationalResult.get()[0].negate(), negatedRationalResult.get()[1] };
				}
				else if (baseDenominatorOne && valNumeratorOne)
				{
					Optional<BigInteger[]> negatedRationalResult = intLog(baseNumerator.longValueExact(), valDenominator.longValueExact());
					if (negatedRationalResult.isPresent())
						return new BigInteger[] { negatedRationalResult.get()[0].negate(), negatedRationalResult.get()[1] };
				}
				else if (baseNumeratorOne && valNumeratorOne)
				{
					Optional<BigInteger[]> rationalResult = intLog(baseDenominator.longValueExact(), valDenominator.longValueExact());
					if (rationalResult.isPresent())
						return rationalResult.get();
				}
			}
			else
			{
				Optional<BigInteger[]> numeratorsRationalResult = intLog(baseNumerator.longValueExact(), valNumerator.longValueExact());
				if (numeratorsRationalResult.isPresent())
				{
					Optional<BigInteger[]> denominatorsRationalResult = intLog(baseDenominator.longValueExact(), valDenominator.longValueExact());
					if (denominatorsRationalResult.isPresent() &&
						numeratorsRationalResult.get()[0] == denominatorsRationalResult.get()[0] &&
						numeratorsRationalResult.get()[1] == denominatorsRationalResult.get()[1])
						return numeratorsRationalResult.get();
				}
				else
				{
					Optional<BigInteger[]> invertedNumeratorsRationalResult = intLog(baseDenominator.longValueExact(), valNumerator.longValueExact());
					if (invertedNumeratorsRationalResult.isPresent())
					{
						Optional<BigInteger[]> invertedDenominatorsRationalResult = intLog(baseNumerator.longValueExact(), valDenominator.longValueExact());
						if (invertedDenominatorsRationalResult.isPresent() &&
							invertedNumeratorsRationalResult.get()[0] == invertedDenominatorsRationalResult.get()[0] &&
							invertedNumeratorsRationalResult.get()[1] == invertedDenominatorsRationalResult.get()[1])
							return new BigInteger[] { invertedNumeratorsRationalResult.get()[0].negate(), invertedNumeratorsRationalResult.get()[1] };
					}
				}
			}
		}
		BigDecimal base = BigUtil.divide(baseNumerator, baseDenominator);
		BigDecimal val = BigUtil.divide(valNumerator, valDenominator);
		BigDecimal log = BigUtil.log(base, val);
		return fraction(log);
	}

	/**
	 * Returns a rational result if one exists, otherwise returns empty.
	 * Expects that {@code base} and {@code val} are greater than 1 and not equal to each other.
	 */
	private static Optional<BigInteger[]> intLog(long base, long val)
	{
		Map<Long, Long> baseFactors = primeFactors(base);
		Map<Long, Long> valFactors = primeFactors(val);
		if (baseFactors.size() == valFactors.size())
		{
			long[] powerRatio = null;
			for (Map.Entry<Long, Long> baseFactor : baseFactors.entrySet())
			{
				long factor = baseFactor.getKey();
				long basePower = baseFactor.getValue();
				Long valPower = valFactors.get(factor);
				if (valPower == null)
					return Optional.empty();
				else
				{
					long gcd = gcd(basePower, valPower);
					long[] thisPowerRatio = new long[] { valPower / gcd, basePower / gcd };
					if (powerRatio == null)
						powerRatio = thisPowerRatio;
					else
					{
						if (thisPowerRatio[0] != powerRatio[0] || thisPowerRatio[1] != powerRatio[1])
							return Optional.empty();
					}
				}
			}
			return Optional.of(new BigInteger[] { bi(powerRatio[0]), bi(powerRatio[1]) });
		}
		else
			return Optional.empty();
	}
}
