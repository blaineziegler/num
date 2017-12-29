package com.zieglersoftware.num;

import static com.zieglersoftware.assertions.Assertions.tru;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Any calculation returning a {@code BigDecimal} will be accurate to at least 100 significant figures.
 * Any calculation returning a {@code BigInteger} will be an exact result.
 */
public final class BigUtil
{
	public static final BigDecimal BD0 = bd(0);
	public static final BigDecimal BD1 = bd(1);
	public static final BigDecimal BDM1 = bd(-1);
	public static final BigInteger BI0 = bi(0);
	public static final BigInteger BI1 = bi(1);
	public static final BigInteger BIM1 = bi(-1);

	private static final BigDecimal E110 = bd("2.71828182845904523536028747135266249775724709369995957496696762772407663035354759457138217852516642742746639193");
	private static final BigInteger BI2 = bi(2);
	private static final MathContext MC110 = new MathContext(110);
	private static final MathContext MC106 = new MathContext(106);
	private static final MathContext MC100 = new MathContext(100);
	private static final BigInteger EXP_DIVISION = bi(1000);
	private static final int EXP_MAX_ORDER = 51;
	private static final BigDecimal[] FACTORIALS = cacheFactorials();
	private static final int LN_MAX_ITERATIONS = 100;
	private static final BigInteger MAX_CACHED_LN = bi(100);
	private static final List<BigDecimal> LN_CACHE = getLnCache();
	private static final BigInteger MAX_CACHED_SQRT = bi(100);
	private static final List<BigDecimal> SQRT_CACHE = getSqrtCache();

	/**
	 * Returns a {@code BigDecimal} representation of {@code val}.
	 */
	public static BigDecimal bd(String val)
	{
		return new BigDecimal(val);
	}

	/**
	 * Returns a {@code BigDecimal} representation of {@code val}.
	 */
	public static BigDecimal bd(double val)
	{
		return BigDecimal.valueOf(val);
	}

	/**
	 * Returns a {@code BigDecimal} representation of {@code val}.
	 */
	public static BigDecimal bd(BigInteger val)
	{
		return new BigDecimal(val);
	}

	/**
	 * Returns a {@code BigDecimal} representation of {@code val}.
	 */
	public static BigDecimal bd(long val)
	{
		return BigDecimal.valueOf(val);
	}

	/**
	 * Returns a {@code BigInteger} representation of {@code val}.
	 */
	public static BigInteger bi(String val)
	{
		return new BigInteger(val);
	}

	/**
	 * Returns a {@code BigInteger} representation of {@code val}.
	 */
	public static BigInteger bi(long val)
	{
		return BigInteger.valueOf(val);
	}

	/**
	 * Returns true if {@code val} is greater than 0, false otherwise.
	 */
	public static boolean isPos(BigDecimal val)
	{
		return val.signum() == 1;
	}

	/**
	 * Returns true if {@code val} is greater than 0, false otherwise.
	 */
	public static boolean isPos(BigInteger val)
	{
		return val.signum() == 1;
	}

	/**
	 * Returns true if {@code val} is less than 0, false otherwise.
	 */
	public static boolean isNeg(BigDecimal val)
	{
		return val.signum() == -1;
	}

	/**
	 * Returns true if {@code val} is less than 0, false otherwise.
	 */
	public static boolean isNeg(BigInteger val)
	{
		return val.signum() == -1;
	}

	/**
	 * Returns true if {@code val} is equal in magnitude to 0 (ignoring scale), false otherwise.
	 */
	public static boolean isZero(BigDecimal val)
	{
		return val.signum() == 0;
	}

	/**
	 * Returns true if {@code val} is equal to 0, false otherwise.
	 */
	public static boolean isZero(BigInteger val)
	{
		return val.signum() == 0;
	}

	/**
	 * Returns true if {@code val} is equal in magnitude to 1 (ignoring scale), false otherwise.
	 */
	public static boolean isOne(BigDecimal val)
	{
		return val.compareTo(BD1) == 0;
	}

	/**
	 * Returns true if {@code val} is equal to 1, false otherwise.
	 */
	public static boolean isOne(BigInteger val)
	{
		return val.compareTo(BI1) == 0;
	}

	/**
	 * Returns true if {@code a} and {@code b} are the same value,
	 * ignoring scale.
	 */
	public static boolean equal(BigDecimal a, BigDecimal b)
	{
		return a.compareTo(b) == 0;
	}

	/**
	 * Returns true if {@code a} and {@code b} are the same value,
	 * ignoring the scale of {@code a}.
	 */
	public static boolean equal(BigDecimal a, BigInteger b)
	{
		return a.compareTo(bd(b)) == 0;
	}

	/**
	 * Returns true if {@code a} and {@code b} are the same value,
	 * ignoring the scale of {@code b}.
	 */
	public static boolean equal(BigInteger a, BigDecimal b)
	{
		return bd(a).compareTo(b) == 0;
	}

	/**
	 * Returns true if {@code a} and {@code b} are equal.
	 */
	public static boolean equal(BigInteger a, BigInteger b)
	{
		return a.equals(b);
	}

	/**
	 * Returns true if {@code a} and {@code b} are not
	 * the same value, ignoring scale.
	 */
	public static boolean notEqual(BigDecimal a, BigDecimal b)
	{
		return a.compareTo(b) != 0;
	}

	/**
	 * Returns true if {@code a} and {@code b} are not
	 * the same value, ignoring the scale of {@code a}.
	 */
	public static boolean notEqual(BigDecimal a, BigInteger b)
	{
		return a.compareTo(bd(b)) != 0;
	}

	/**
	 * Returns true if {@code a} and {@code b} are not
	 * the same value, ignoring the scale of {@code b}.
	 */
	public static boolean notEqual(BigInteger a, BigDecimal b)
	{
		return bd(a).compareTo(b) != 0;
	}

	/**
	 * Returns true if {@code a} and {@code b} are not equal.
	 */
	public static boolean notEqual(BigInteger a, BigInteger b)
	{
		return !a.equals(b);
	}

	/**
	 * Returns true if {@code a} is greater than {@code b}.
	 */
	public static boolean greater(BigDecimal a, BigDecimal b)
	{
		return a.compareTo(b) > 0;
	}

	/**
	 * Returns true if {@code a} is greater than {@code b}.
	 */
	public static boolean greater(BigDecimal a, BigInteger b)
	{
		return a.compareTo(bd(b)) > 0;
	}

	/**
	 * Returns true if {@code a} is greater than {@code b}.
	 */
	public static boolean greater(BigInteger a, BigDecimal b)
	{
		return bd(a).compareTo(b) > 0;
	}

	/**
	 * Returns true if {@code a} is greater than {@code b}.
	 */
	public static boolean greater(BigInteger a, BigInteger b)
	{
		return a.compareTo(b) > 0;
	}

	/**
	 * Returns true if {@code a} is less than {@code b}.
	 */
	public static boolean less(BigDecimal a, BigDecimal b)
	{
		return a.compareTo(b) < 0;
	}

	/**
	 * Returns true if {@code a} is less than {@code b}.
	 */
	public static boolean less(BigDecimal a, BigInteger b)
	{
		return a.compareTo(bd(b)) < 0;
	}

	/**
	 * Returns true if {@code a} is less than {@code b}.
	 */
	public static boolean less(BigInteger a, BigDecimal b)
	{
		return bd(a).compareTo(b) < 0;
	}

	/**
	 * Returns true if {@code a} is less than {@code b}.
	 */
	public static boolean less(BigInteger a, BigInteger b)
	{
		return a.compareTo(b) < 0;
	}

	/**
	 * Returns true if {@code a} is greater than or equal to {@code b},
	 * ignoring scale.
	 */
	public static boolean greaterOrEqual(BigDecimal a, BigDecimal b)
	{
		return a.compareTo(b) >= 0;
	}

	/**
	 * Returns true if {@code a} is greater than or equal to {@code b},
	 * ignoring the scale of {@code a}.
	 */
	public static boolean greaterOrEqual(BigDecimal a, BigInteger b)
	{
		return a.compareTo(bd(b)) >= 0;
	}

	/**
	 * Returns true if {@code a} is greater than or equal to {@code b},
	 * ignoring the scale of {@code b}.
	 */
	public static boolean greaterOrEqual(BigInteger a, BigDecimal b)
	{
		return bd(a).compareTo(b) >= 0;
	}

	/**
	 * Returns true if {@code a} is greater than or equal to {@code b}.
	 */
	public static boolean greaterOrEqual(BigInteger a, BigInteger b)
	{
		return a.compareTo(b) >= 0;
	}

	/**
	 * Returns true if {@code a} is less than or equal to {@code b},
	 * ignoring scale.
	 */
	public static boolean lessOrEqual(BigDecimal a, BigDecimal b)
	{
		return a.compareTo(b) <= 0;
	}

	/**
	 * Returns true if {@code a} is less than or equal to {@code b},
	 * ignoring the scale of {@code a}.
	 */
	public static boolean lessOrEqual(BigDecimal a, BigInteger b)
	{
		return a.compareTo(bd(b)) <= 0;
	}

	/**
	 * Returns true if {@code a} is less than or equal to {@code b},
	 * ignoring the scale of {@code b}.
	 */
	public static boolean lessOrEqual(BigInteger a, BigDecimal b)
	{
		return bd(a).compareTo(b) <= 0;
	}

	/**
	 * Returns true if {@code a} is less than or equal to {@code b}.
	 */
	public static boolean lessOrEqual(BigInteger a, BigInteger b)
	{
		return a.compareTo(b) <= 0;
	}

	/**
	 * Returns true if {@code val} is an integer, false otherwise.
	 * {@code val} is considered an integer if it has no decimal
	 * digits or if every decimal digit is a 0. {@code val} need not
	 * be able to fit into an {@code int} to be considered an integer.
	 * <p>
	 * If this method returns true, {@code val.toBigIntegerExact()}
	 * would succeed. If this method returns false, that would fail.
	 * <p>
	 * Examples:
	 * 
	 * <pre>
	 *  1000000000000000000000000000        -> true
	 *  1000000000000000000000000000.000000 -> true
	 *  1000000000000000000000000000.000001 -> false
	 *  0   -> true
	 *  0.0 -> true
	 * -1000000000000000000000000000        -> true
	 * -1000000000000000000000000000.000000 -> true
	 * -1000000000000000000000000000.000001 -> false
	 * </pre>
	 */
	public static boolean isInt(BigDecimal val)
	{
		if (val.scale() <= 0)
			return true;
		BigDecimal roundedToInt = val.setScale(0, RoundingMode.HALF_UP);
		return val.compareTo(roundedToInt) == 0;
	}

	/**
	 * Returns {@code val} rounded to the nearest {@code BigInteger}.
	 * Uses {@link RoundingMode#HALF_UP}.
	 * <p>
	 * Examples:
	 * 
	 * <pre>
	 *  1.8 ->  2
	 *  1.5 ->  2
	 *  1.2 ->  1
	 *  1.0 ->  1
	 *  1   ->  1
	 *  0.8 ->  1
	 *  0.5 ->  1
	 *  0.2 ->  0
	 *  0.0 ->  0
	 *  0   ->  0
	 * -0.2 ->  0
	 * -0.5 -> -1
	 * -0.8 -> -1
	 * -1   -> -1
	 * -1.0 -> -1
	 * -1.2 -> -1
	 * -1.5 -> -2
	 * -1.8 -> -2
	 * </pre>
	 */
	public static BigInteger round(BigDecimal val)
	{
		return val.setScale(0, RoundingMode.HALF_UP).toBigIntegerExact();
	}

	/**
	 * Returns {@code val} rounded to a {@code BigInteger} according to
	 * the given {@link RoundingMode}.
	 */
	public static BigInteger round(BigDecimal val, RoundingMode roundingMode)
	{
		return val.setScale(0, roundingMode).toBigIntegerExact();
	}

	/**
	 * Returns the whole number part of {@code val}. The returned value will have the same sign sign as {@code val},
	 * unless {@code val} is a negative number between 0 and -1, in which case the returned value will be 0.
	 * <p>
	 * Examples:
	 * 
	 * <pre>
	 *  3.80 ->   3
	 *  3.0  ->   3
	 *  3    ->   3
	 *  0.5  ->   0
	 *  0    ->   0
	 * -3.80 ->  -3
	 * -3.0  ->  -3
	 * -3    ->  -3
	 * -0.5  ->   0
	 * </pre>
	 */
	public static BigInteger wholePart(BigDecimal val)
	{
		return val.toBigInteger();
	}

	/**
	 * Returns the decimal part of {@code val} (as a decimal, not scaled to an integer value).
	 * The returned value will have the same sign sign as {@code val}, unless {@code val} is an integer,
	 * in which case 0 will be returned. Any trailing zeros will be removed.
	 * <p>
	 * Examples:
	 * 
	 * <pre>
	 *  3.80 ->  0.8
	 *  3.0  ->  0
	 *  3    ->  0
	 *  0.5  ->  0.5
	 *  0    ->  0
	 * -3.80 -> -0.8
	 * -3.0  ->  0
	 * -3    ->  0
	 * -0.5  -> -0.5
	 * </pre>
	 */
	public static BigDecimal decimalPart(BigDecimal val)
	{
		BigDecimal decimalPart = subtract(val, val.toBigInteger());
		return decimalPart.stripTrailingZeros();
	}

	/**
	 * Returns a 2-element array of {@code BigIntegers}, consisting of:
	 * <ol>
	 * <li>{@link #wholePart(BigDecimal)}</li>
	 * <li>{@link #decimalPart(BigDecimal)}, scaled as an integer</li>
	 * </ol>
	 * <p>
	 * Examples:
	 * 
	 * <pre>
	 *  3.80 -> [ 3,  8]
	 *  3.0  -> [ 3,  0]
	 *  3    -> [ 3,  0]
	 *  0.5  -> [ 0,  5]
	 *  0    -> [ 0,  0]
	 * -3.80 -> [-3, -8]
	 * -3.0  -> [-3,  0]
	 * -3    -> [-3,  0]
	 * -0.5  -> [ 0, -5]
	 * </pre>
	 */
	public static BigInteger[] wholeAndDecimalParts(BigDecimal val)
	{
		Object[] wholePartDecimalPartFinalNonZeroIndexDecimalIndex = wholePartDecimalPartFinalNonZeroIndexDecimalIndex(val);
		BigInteger whole = (BigInteger) wholePartDecimalPartFinalNonZeroIndexDecimalIndex[0];
		if (wholePartDecimalPartFinalNonZeroIndexDecimalIndex.length == 1)
			return new BigInteger[] { whole, BI0 };
		BigInteger decimal = (BigInteger) wholePartDecimalPartFinalNonZeroIndexDecimalIndex[1];
		return new BigInteger[] { whole, decimal };
	}

	/**
	 * Returns a mixed number representation of {@code val}, i.e., {@code a+b/c},
	 * where {@code a} is the whole part of {@code val}, {@code b} is the decimal
	 * part scaled as an integer, and {@code c} is 10 raised to the power of the
	 * number of decimal digits. Note that the {@code b/c} fraction will not necessarily
	 * be reduced.
	 * <p>
	 * The returned value is a 3-element array of {@code BigIntegers}, consisting of:
	 * <ol>
	 * <li>{@link #wholeAndDecimalParts(BigDecimal)}{@code [0]}</li>
	 * <li>{@code wholeAndDecimalParts[1]}</li>
	 * <li>10 raised to the power of the number of decimal digits in {@code val}, excluding trailing zeros. (1 if {@code val} is an integer.)</li>
	 * </ol>
	 * <p>
	 * Examples:
	 * 
	 * <pre>
	 *  3.80 -> [ 3,  8, 10]
	 *  3.0  -> [ 3,  0,  1]
	 *  3    -> [ 3,  0,  1]
	 *  0.5  -> [ 0,  5, 10]
	 *  0    -> [ 0,  0,  1]
	 * -3.80 -> [-3, -8, 10]
	 * -3.0  -> [-3,  0,  1]
	 * -3    -> [-3,  0,  1]
	 * -0.5  -> [ 0, -5, 10]
	 * </pre>
	 */
	public static BigInteger[] mixedNumber(BigDecimal val)
	{
		Object[] wholePartDecimalPartFinalNonZeroIndexDecimalIndex = wholePartDecimalPartFinalNonZeroIndexDecimalIndex(val);
		BigInteger whole = (BigInteger) wholePartDecimalPartFinalNonZeroIndexDecimalIndex[0];
		if (wholePartDecimalPartFinalNonZeroIndexDecimalIndex.length == 1)
			return new BigInteger[] { whole, BI0, BI1 };
		BigInteger decimal = (BigInteger) wholePartDecimalPartFinalNonZeroIndexDecimalIndex[1];
		int finalNonZeroIndex = (int) wholePartDecimalPartFinalNonZeroIndexDecimalIndex[2];
		int decimalIndex = (int) wholePartDecimalPartFinalNonZeroIndexDecimalIndex[3];
		int denominatorPowerOfTen = finalNonZeroIndex - decimalIndex;
		StringBuilder denominatorBuilder = new StringBuilder(denominatorPowerOfTen + 1);
		denominatorBuilder.append('1');
		for (int i = 0; i < denominatorPowerOfTen; i++)
			denominatorBuilder.append('0');
		BigInteger denominator = bi(denominatorBuilder.toString());
		return new BigInteger[] { whole, decimal, denominator };
	}

	/**
	 * Returns a fraction representation of {@code val}.
	 * <p>
	 * The returned value is a 2-element array of {@code BigIntegers}, consisting of:
	 * <ol>
	 * <li>{@link #mixedNumber(BigDecimal)}{@code [0] * mixedNumber[2] + mixedNumber[1]}</li>
	 * <li>mixedNumber[2]</li>
	 * </ol>
	 * Note that the fraction will not necessarily be reduced.
	 * <p>
	 * Examples:
	 * 
	 * <pre>
	 *  3.80 -> [ 38, 10]
	 *  3.0  -> [  3,  1]
	 *  3    -> [  3,  1]
	 *  0.5  -> [  5, 10]
	 *  0    -> [  0,  1]
	 * -3.80 -> [-38, 10]
	 * -3.0  -> [- 3,  1]
	 * -3    -> [- 3,  1]
	 * -0.5  -> [- 5, 10]
	 * </pre>
	 */
	public static BigInteger[] fraction(BigDecimal val)
	{
		BigInteger[] mixedNumber = mixedNumber(val);
		BigInteger numerator = mixedNumber[0].multiply(mixedNumber[2]).add(mixedNumber[1]);
		BigInteger denominator = mixedNumber[2];
		return new BigInteger[] { numerator, denominator };
	}

	/**
	 * If {@code val} is an integer, returns {@code [BigInteger val]}. Otherwise, returns
	 * {@code [BigInteger wholePart, BigInteger numerator, int finalNonZeroIndex, int decimalIndex]}.
	 */
	private static Object[] wholePartDecimalPartFinalNonZeroIndexDecimalIndex(BigDecimal val)
	{
		BigInteger whole = val.toBigInteger();
		if (isInt(val))
			return new BigInteger[] { whole };
		String s = val.toPlainString();
		int decimalIndex = s.indexOf('.');
		int finalNonZeroIndex;
		for (finalNonZeroIndex = s.length() - 1; s.charAt(finalNonZeroIndex) == '0'; finalNonZeroIndex--)
			;
		String sign = val.signum() == -1 ? "-" : "";
		String numeratorString = sign + s.substring(decimalIndex + 1, finalNonZeroIndex + 1);
		BigInteger numerator = bi(numeratorString);
		return new Object[] { whole, numerator, finalNonZeroIndex, decimalIndex };
	}

	/**
	 * Returns 2 arrays, the first of which contains the digits of {@code val} before the decimal point,
	 * and the second of which contains the digits after the decimal point. If {@code val} is an integer,
	 * the second array will be empty. The second array will never have trailing zeros.
	 * <p>
	 * {@code val} may negative, but the returned arrays will never include any negative digits.
	 * <p>
	 * Examples:
	 * 
	 * <pre>
	 *  123.45600 -> [ [1,2,3], [4,5,6] ]
	 *    3.0     -> [ [3],     []      ]
	 *    3       -> [ [3],     []      ]
	 *    0.5     -> [ [0],     [5]     ]
	 *    0       -> [ [0],     []      ]
	 * -  3.0     -> [ [3],     []      ]
	 * -123.45600 -> [ [1,2,3], [4,5,6] ]
	 * </pre>
	 */
	public static int[][] digits(BigDecimal val)
	{
		BigInteger[] wholeAndDecimalParts = wholeAndDecimalParts(val);
		BigInteger wholePart = wholeAndDecimalParts[0];
		BigInteger decimalPart = wholeAndDecimalParts[1];
		if (isZero(decimalPart))
			return new int[][] { digits(wholePart), new int[] {} };
		else
			return new int[][] { digits(wholePart), digits(decimalPart) };
	}

	/**
	 * Returns the digits of {@code val}.
	 * <p>
	 * {@code val} may negative, but the returned array will never include any negative digits.
	 * <p>
	 * Examples:
	 * 
	 * <pre>
	 *  123 -> [1,2,3]
	 *    3 -> [3]
	 *    0 -> [0]
	 *   -3 -> [3]
	 * -123 -> [1,2,3]
	 * </pre>
	 */
	public static int[] digits(BigInteger val)
	{
		String absString = val.abs().toString(10);
		int length = absString.length();
		int[] digits = new int[length];
		for (int i = 0; i < length; i++)
			digits[i] = absString.charAt(i) - '0';
		return digits;
	}

	/**
	 * Returns the number of digits of {@code val}.
	 * <p>
	 * {@code val} may negative, but this method only considers {@code abs(val)}.
	 * If {@code val} is 0, 1 is returned.
	 * <p>
	 * Examples:
	 * 
	 * <pre>
	 *  123 -> 3
	 *    3 -> 1
	 *    0 -> 1
	 *   -3 -> 1
	 * -123 -> 3
	 * </pre>
	 */
	public static int digitCount(BigInteger val)
	{
		String absString = val.abs().toString(10);
		return absString.length();
	}

	public static BigDecimal add(BigDecimal a, BigDecimal b, BigDecimal... others)
	{
		BigDecimal result = a.add(b, MC110);
		for (BigDecimal other : others)
			result = result.add(other, MC110);
		return result;
	}

	public static BigDecimal add(BigDecimal a, BigInteger b, BigInteger... others)
	{
		BigDecimal result = a.add(bd(b), MC110);
		for (BigInteger other : others)
			result = result.add(bd(other), MC110);
		return result;
	}

	public static BigDecimal add(BigInteger a, BigDecimal b, BigDecimal... others)
	{
		BigDecimal result = bd(a).add(b, MC110);
		for (BigDecimal other : others)
			result = result.add(other, MC110);
		return result;
	}

	public static BigInteger add(BigInteger a, BigInteger b, BigInteger... others)
	{
		BigInteger result = a.add(b);
		for (BigInteger other : others)
			result = result.add(other);
		return result;
	}

	public static BigDecimal subtract(BigDecimal a, BigDecimal b)
	{
		return a.subtract(b, MC110);
	}

	public static BigDecimal subtract(BigDecimal a, BigInteger b)
	{
		return a.subtract(bd(b), MC110);
	}

	public static BigDecimal subtract(BigInteger a, BigDecimal b)
	{
		return b.subtract(bd(a), MC110);
	}

	public static BigInteger subtract(BigInteger a, BigInteger b)
	{
		return a.subtract(b);
	}

	public static BigDecimal multiply(BigDecimal a, BigDecimal b, BigDecimal... others)
	{
		BigDecimal result = a.multiply(b, MC110);
		for (BigDecimal other : others)
			result = result.multiply(other, MC110);
		return result;
	}

	public static BigDecimal multiply(BigDecimal a, BigInteger b, BigInteger... others)
	{
		BigDecimal result = a.multiply(bd(b), MC110);
		for (BigInteger other : others)
			result = result.multiply(bd(other), MC110);
		return result;
	}

	public static BigDecimal multiply(BigInteger a, BigDecimal b, BigDecimal... others)
	{
		BigDecimal result = bd(a).multiply(b, MC110);
		for (BigDecimal other : others)
			result = result.multiply(other, MC110);
		return result;
	}

	public static BigInteger multiply(BigInteger a, BigInteger b, BigInteger... others)
	{
		BigInteger result = a.multiply(b);
		for (BigInteger other : others)
			result = result.multiply(other);
		return result;
	}

	public static BigDecimal divide(BigDecimal a, BigDecimal b)
	{
		return a.divide(b, MC110);
	}

	public static BigDecimal divide(BigDecimal a, BigInteger b)
	{
		return a.divide(bd(b), MC110);
	}

	public static BigDecimal divide(BigInteger a, BigDecimal b)
	{
		return b.divide(bd(a), MC110);
	}

	/**
	 * Returns a {@code BigDecimal} since the result may not be an integer.
	 */
	public static BigDecimal divide(BigInteger a, BigInteger b)
	{
		return bd(a).divide(bd(b), MC110);
	}

	public static BigDecimal invert(BigDecimal val)
	{
		return divide(BD1, val);
	}

	public static BigDecimal invert(BigInteger val)
	{
		return divide(BD1, val);
	}

	public static BigInteger[] floorDivideAndRemainder(BigInteger a, BigInteger b)
	{
		return a.divideAndRemainder(b);
	}

	/**
	 * Returns {@code base^power}.
	 * <p>
	 * If {@code base} is 0, {@code power} must be greater than 0. Will succeed without overflow
	 * if {@code abs(base)} is less than 1,000,000,000,000,000,000,000 and
	 * {@code abs(power)} is less than 100,000,000. If one value is smaller than its
	 * limit and the other value is somewhat larger than its limit, the calculation
	 * might succeed.
	 */
	public static BigDecimal pow(BigDecimal base, long power)
	{
		BigDecimal result = powInternal(base, power);
		BigDecimal finalized = finalize(result);
		return finalized;
	}

	/**
	 * Returns {@code base^power}.
	 * <p>
	 * If {@code base} is 0, {@code power} must be greater than 0. Will succeed without overflow
	 * if {@code abs(base)} is less than 1,000,000,000,000,000,000,000 and
	 * {@code abs(power)} is less than 100,000,000. If one value is smaller than its
	 * limit and the other value is somewhat larger than its limit, the calculation
	 * might succeed.
	 */
	public static BigDecimal pow(BigDecimal base, BigInteger power)
	{
		BigDecimal result = powInternal(base, power);
		BigDecimal finalized = finalize(result);
		return finalized;
	}

	/**
	 * Returns {@code base^power}.
	 * <p>
	 * If {@code base} is negative, {@code power} must either be an integer or a
	 * number that can be expressed as a fraction with an odd denominator.
	 * <p>
	 * If {@code base} is 0, {@code power} must be greater than 0. Will succeed without overflow
	 * if {@code abs(base)} is less than 1,000,000,000,000,000,000,000 and
	 * {@code abs(power)} is less than 100,000,000. If one value is smaller than its
	 * limit and the other value is somewhat larger than its limit, the calculation
	 * might succeed.
	 */
	public static BigDecimal pow(BigDecimal base, BigDecimal power)
	{
		BigDecimal result = powInternal(base, power);
		BigDecimal finalized = finalize(result);
		return finalized;
	}

	/**
	 * Returns {@code base^power}.
	 * <p>
	 * If {@code base} is 0, {@code power} must be greater than 0. Will succeed without overflow
	 * if {@code abs(base)} is less than 1,000,000,000,000,000,000,000 and
	 * {@code abs(power)} is less than 100,000,000. If one value is smaller than its
	 * limit and the other value is somewhat larger than its limit, the calculation
	 * might succeed.
	 * <p>
	 * Returns a {@code BigDecimal} since the result may not be an integer (when {@code power} is negative).
	 */
	public static BigDecimal pow(BigInteger base, long power)
	{
		BigDecimal result = powInternal(base, power);
		BigDecimal finalized = finalize(result);
		return finalized;
	}

	/**
	 * Returns {@code base^power}.
	 * <p>
	 * If {@code base} is 0, {@code power} must be greater than 0. Will succeed without overflow
	 * if {@code abs(base)} is less than 1,000,000,000,000,000,000,000 and
	 * {@code abs(power)} is less than 100,000,000. If one value is smaller than its
	 * limit and the other value is somewhat larger than its limit, the calculation
	 * might succeed.
	 * <p>
	 * Returns a {@code BigDecimal} since the result may not be an integer (when {@code power} is negative).
	 */
	public static BigDecimal pow(BigInteger base, BigInteger power)
	{
		BigDecimal result = powInternal(base, power);
		BigDecimal finalized = finalize(result);
		return finalized;
	}

	/**
	 * Returns {@code base^power}.
	 * <p>
	 * If {@code base} is negative, {@code power} must either be an integer or a
	 * number that can be expressed as a fraction with an odd denominator.
	 * <p>
	 * If {@code base} is 0, {@code power} must be greater than 0. Will succeed without overflow
	 * if {@code abs(base)} is less than 1,000,000,000,000,000,000,000 and
	 * {@code abs(power)} is less than 100,000,000. If one value is smaller than its
	 * limit and the other value is somewhat larger than its limit, the calculation
	 * might succeed.
	 */
	public static BigDecimal pow(BigInteger base, BigDecimal power)
	{
		BigDecimal result = powInternal(base, power);
		BigDecimal finalized = finalize(result);
		return finalized;
	}

	/**
	 * Equivalent to {@link #pow(BigDecimal, BigDecimal)} with {@code invert(n)} as the exponent,
	 * except that {@code n} must be greater than zero, and in the case that {@code base} is negative,
	 * {@code n} is odd, and {@code invert(n)} can't be expressed exactly as a decimal, this method
	 * will succeed whereas {@code pow(base, invert(n))} will likely fail due to representing the
	 * denominator of the exponent as an even number rather than odd.
	 * <p>
	 * Examples:
	 * 
	 * <pre>
	 *  {@code nthRoot(-1, 3)}          -> -1
	 *      {@code pow(-1, 0.33333...)} -> {@code ArithmeticException}
	 *  {@code nthRoot(-8, 3)}          -> -2
	 *      {@code pow(-8, 0.33333...)} -> {@code ArithmeticException}
	 *  {@code nthRoot(pow(-8, 5)), 3)} -> -32
	 *      {@code pow(-8, 1.6666...)}  -> {@code ArithmeticException}
	 *  {@code nthRoot( 1, 3)}          -> 1
	 *      {@code pow( 1, 0.33333...)} -> 1
	 *  {@code nthRoot( 8, 3)}          -> 2
	 *      {@code pow( 8, 0.33333...)} -> 2
	 * </pre>
	 */
	public static BigDecimal nthRoot(BigDecimal base, long n)
	{
		return nthRoot(base, bi(n));
	}

	private static List<BigDecimal> getSqrtCache()
	{
		int maxCachedSqrtInt = MAX_CACHED_SQRT.intValue();
		List<BigDecimal> cache = new ArrayList<>(maxCachedSqrtInt + 1);
		for (int i = 0; i <= maxCachedSqrtInt; i++)
			cache.add(calcNthRoot(bd(i), bi(2)));
		return cache;
	}

	/**
	 * Equivalent to {@link #pow(BigDecimal, BigDecimal)} with {@code invert(n)} as the exponent,
	 * except that {@code n} must be greater than zero, and in the case that {@code base} is negative,
	 * {@code n} is odd, and {@code invert(n)} can't be expressed exactly as a decimal, this method
	 * will succeed whereas {@code pow(base, invert(n))} will likely fail due to representing the
	 * denominator of the exponent as an even number rather than odd.
	 * <p>
	 * Examples:
	 * 
	 * <pre>
	 *  {@code nthRoot(-1, 3)}          -> -1
	 *      {@code pow(-1, 0.33333...)} -> {@code ArithmeticException}
	 *  {@code nthRoot(-8, 3)}          -> -2
	 *      {@code pow(-8, 0.33333...)} -> {@code ArithmeticException}
	 *  {@code nthRoot(pow(-8, 5)), 3)} -> -32
	 *      {@code pow(-8, 1.6666...)}  -> {@code ArithmeticException}
	 *  {@code nthRoot( 1, 3)}          -> 1
	 *      {@code pow( 1, 0.33333...)} -> 1
	 *  {@code nthRoot( 8, 3)}          -> 2
	 *      {@code pow( 8, 0.33333...)} -> 2
	 * </pre>
	 */
	public static BigDecimal nthRoot(BigDecimal base, BigInteger n)
	{
		tru(isPos(n), "n must be positive. Was %s", n);
		if (equal(n, BI2) && isInt(base) && lessOrEqual(base, MAX_CACHED_SQRT) && isPos(base))
			return SQRT_CACHE.get(base.intValueExact());
		return calcNthRoot(base, n);
	}

	/**
	 * Expects that {@code n} is greater than 0. Does no cache lookups or preconditions checks.
	 */
	private static final BigDecimal calcNthRoot(BigDecimal base, BigInteger n)
	{
		if (isNeg(base))
		{
			if (isZero(n.mod(bi(2))))
				throw new ArithmeticException("Base " + base + " is negative, but n " + n + " is even");
			BigDecimal negativeResult = powInternal(base.negate(), invert(n));
			BigDecimal result = negativeResult.negate();
			return finalize(result);
		}
		else
		{
			BigDecimal result = powInternal(base, invert(n));
			return finalize(result);
		}
	}

	/**
	 * Equivalent to {@link #pow(BigDecimal, BigDecimal)} with {@code invert(n)} as the exponent,
	 * except that {@code n} must be greater than zero, and in the case that {@code base} is negative,
	 * {@code n} is odd, and {@code invert(n)} can't be expressed exactly as a decimal, this method
	 * will succeed whereas {@code pow(base, invert(n))} will likely fail due to representing the
	 * denominator of the exponent as an even number rather than odd.
	 * <p>
	 * Examples:
	 * 
	 * <pre>
	 *  {@code nthRoot(-1, 3)}          -> -1
	 *      {@code pow(-1, 0.33333...)} -> {@code ArithmeticException}
	 *  {@code nthRoot(-8, 3)}          -> -2
	 *      {@code pow(-8, 0.33333...)} -> {@code ArithmeticException}
	 *  {@code nthRoot(pow(-8, 5)), 3)} -> -32
	 *      {@code pow(-8, 1.6666...)}  -> {@code ArithmeticException}
	 *  {@code nthRoot( 1, 3)}          -> 1
	 *      {@code pow( 1, 0.33333...)} -> 1
	 *  {@code nthRoot( 8, 3)}          -> 2
	 *      {@code pow( 8, 0.33333...)} -> 2
	 * </pre>
	 */
	public static BigDecimal nthRoot(BigInteger base, long n)
	{
		return nthRoot(bd(base), bi(n));
	}

	/**
	 * Equivalent to {@link #pow(BigDecimal, BigDecimal)} with {@code invert(n)} as the exponent,
	 * except that {@code n} must be greater than zero, and in the case that {@code base} is negative,
	 * {@code n} is odd, and {@code invert(n)} can't be expressed exactly as a decimal, this method
	 * will succeed whereas {@code pow(base, invert(n))} will likely fail due to representing the
	 * denominator of the exponent as an even number rather than odd.
	 * <p>
	 * Examples:
	 * 
	 * <pre>
	 *  {@code nthRoot(-1, 3)}          -> -1
	 *      {@code pow(-1, 0.33333...)} -> {@code ArithmeticException}
	 *  {@code nthRoot(-8, 3)}          -> -2
	 *      {@code pow(-8, 0.33333...)} -> {@code ArithmeticException}
	 *  {@code nthRoot(pow(-8, 5)), 3)} -> -32
	 *      {@code pow(-8, 1.6666...)}  -> {@code ArithmeticException}
	 *  {@code nthRoot( 1, 3)}          -> 1
	 *      {@code pow( 1, 0.33333...)} -> 1
	 *  {@code nthRoot( 8, 3)}          -> 2
	 *      {@code pow( 8, 0.33333...)} -> 2
	 * </pre>
	 */
	public static BigDecimal nthRoot(BigInteger base, BigInteger n)
	{
		return nthRoot(bd(base), n);
	}

	/**
	 * Returns the square root of the given value. {@code val} must be non-negative.
	 */
	public static BigDecimal sqrt(BigDecimal val)
	{
		return nthRoot(val, 2);
	}

	/**
	 * Returns the square root of the given value. {@code val} must be non-negative.
	 */
	public static BigDecimal sqrt(BigInteger val)
	{
		return nthRoot(val, 2);
	}

	/**
	 * Returns {@code e^power}
	 * <p>
	 * {@code abs(power)} must be less or equal to 4,900,000,000.
	 */
	public static BigDecimal exp(long power)
	{
		BigDecimal result = expInternal(power);
		BigDecimal finalized = finalize(result);
		return finalized;
	}

	/**
	 * Returns {@code e^power}
	 * <p>
	 * {@code abs(power)} must be less or equal to 4,900,000,000.
	 */
	public static BigDecimal exp(BigInteger power)
	{
		BigDecimal result = expInternal(power);
		BigDecimal finalized = finalize(result);
		return finalized;
	}

	/**
	 * Returns {@code e^power}
	 * <p>
	 * {@code abs(power)} must be less or equal to 4,900,000,000.
	 */
	public static BigDecimal exp(BigDecimal power)
	{
		BigDecimal result = expInternal(power);
		BigDecimal finalized = finalize(result);
		return finalized;
	}

	/**
	 * {@code val} must be greater than 0.
	 */
	public static BigDecimal ln(long val)
	{
		BigDecimal result = lnInternal(val);
		BigDecimal finalized = finalize(result);
		return finalized;
	}

	/**
	 * {@code val} must be greater than 0. More specifically, {@code val}
	 * must be greater than 10^-10,000,000 and less than 10^10,000,000.
	 */
	public static BigDecimal ln(BigInteger val)
	{
		BigDecimal result = lnInternal(val);
		BigDecimal finalized = finalize(result);
		return finalized;
	}

	/**
	 * {@code val} must be greater than 0. More specifically, {@code val}
	 * must be greater than 10^-10,000,000 and less than 10^10,000,000.
	 */
	public static BigDecimal ln(BigDecimal val)
	{
		BigDecimal result = lnInternal(val);
		BigDecimal finalized = finalize(result);
		return finalized;
	}

	/**
	 * Returns the log of the given {@code base} of {@code val}.
	 * <p>
	 * {@code base} must be greater than 0 and not equal to 1.
	 * {@code val} must be greater than 0.
	 */
	public static BigDecimal log(long base, long val)
	{
		BigDecimal result = logInternal(base, val);
		BigDecimal finalized = finalize(result);
		return finalized;
	}

	/**
	 * Returns the log of the given {@code base} of {@code val}.
	 * <p>
	 * {@code base} must be greater than 0 and not equal to 1.
	 * {@code val} must be greater than 0.
	 */
	public static BigDecimal log(BigInteger base, long val)
	{
		BigDecimal result = logInternal(base, val);
		BigDecimal finalized = finalize(result);
		return finalized;
	}

	/**
	 * Returns the log of the given {@code base} of {@code val}.
	 * <p>
	 * {@code base} must be greater than 0 and not equal to 1.
	 * {@code val} must be greater than 0.
	 */
	public static BigDecimal log(BigDecimal base, long val)
	{
		BigDecimal result = logInternal(base, val);
		BigDecimal finalized = finalize(result);
		return finalized;
	}

	/**
	 * Returns the log of the given {@code base} of {@code val}.
	 * <p>
	 * {@code base} must be greater than 0 and not equal to 1.
	 * {@code val} must be greater than 0.
	 */
	public static BigDecimal log(long base, BigInteger val)
	{
		BigDecimal result = logInternal(base, val);
		BigDecimal finalized = finalize(result);
		return finalized;
	}

	/**
	 * Returns the log of the given {@code base} of {@code val}.
	 * <p>
	 * {@code base} must be greater than 0 and not equal to 1.
	 * {@code val} must be greater than 0.
	 */
	public static BigDecimal log(BigInteger base, BigInteger val)
	{
		BigDecimal result = logInternal(base, val);
		BigDecimal finalized = finalize(result);
		return finalized;
	}

	/**
	 * Returns the log of the given {@code base} of {@code val}.
	 * <p>
	 * {@code base} must be greater than 0 and not equal to 1.
	 * {@code val} must be greater than 0.
	 */
	public static BigDecimal log(BigDecimal base, BigInteger val)
	{
		BigDecimal result = logInternal(base, val);
		BigDecimal finalized = finalize(result);
		return finalized;
	}

	/**
	 * Returns the log of the given {@code base} of {@code val}.
	 * <p>
	 * {@code base} must be greater than 0 and not equal to 1.
	 * {@code val} must be greater than 0.
	 */
	public static BigDecimal log(long base, BigDecimal val)
	{
		BigDecimal result = logInternal(base, val);
		BigDecimal finalized = finalize(result);
		return finalized;
	}

	/**
	 * Returns the log of the given {@code base} of {@code val}.
	 * <p>
	 * {@code base} must be greater than 0 and not equal to 1.
	 * {@code val} must be greater than 0.
	 */
	public static BigDecimal log(BigInteger base, BigDecimal val)
	{
		BigDecimal result = logInternal(base, val);
		BigDecimal finalized = finalize(result);
		return finalized;
	}

	/**
	 * Returns the log of the given {@code base} of {@code val}.
	 * <p>
	 * {@code base} must be greater than 0 and not equal to 1.
	 * {@code val} must be greater than 0.
	 */
	public static BigDecimal log(BigDecimal base, BigDecimal val)
	{
		BigDecimal result = logInternal(base, val);
		BigDecimal finalized = finalize(result);
		return finalized;
	}

	private static final int MAX_SAFE_POWER = 100000000;
	private static final long MAX_SAFE_POWER_SQUARED = 10000000000000000L;

	/**
	 * Returns {@code base^power}.
	 * <p>
	 * If {@code base} is 0, {@code power} must be greater than 0. Will succeed without overflow
	 * if {@code abs(base)} is less than 1,000,000,000,000,000,000,000 and
	 * {@code abs(power)} is less than 100,000,000. If one value is smaller than its
	 * limit and the other value is somewhat larger than its limit, the calculation
	 * might succeed.
	 */
	private static BigDecimal powInternal(BigDecimal base, long power)
	{
		if (base.compareTo(BigDecimal.ZERO) == 0 && power == 0)
			throw new ArithmeticException("0^0 undefined");
		if (power == 0)
			return BD1;
		if (power == 1)
			return base;
		if (power >= -MAX_SAFE_POWER && power <= MAX_SAFE_POWER)
			return base.pow((int) power, MC110);
		if (power >= MAX_SAFE_POWER_SQUARED || power <= -MAX_SAFE_POWER_SQUARED)
			throw new IllegalArgumentException("Power " + power + " too large");
		int a = (int) (power / MAX_SAFE_POWER);
		int b = (int) (power % MAX_SAFE_POWER);
		// power is a*SAFE_POWER + b, so base^power is (base^SAFE_POWER)^a * base^b
		BigDecimal result = multiply(
			base.pow(MAX_SAFE_POWER, MC110).pow(a, MC110),
			base.pow(b, MC110));
		return result;
	}

	/**
	 * Returns {@code base^power}.
	 * <p>
	 * If {@code base} is 0, {@code power} must be greater than 0. Will succeed without overflow
	 * if {@code abs(base)} is less than 1,000,000,000,000,000,000,000 and
	 * {@code abs(power)} is less than 100,000,000. If one value is smaller than its
	 * limit and the other value is somewhat larger than its limit, the calculation
	 * might succeed.
	 */
	private static BigDecimal powInternal(BigDecimal base, BigInteger power)
	{
		return powInternal(base, power.longValueExact());
	}

	/**
	 * Returns {@code base^power}.
	 * <p>
	 * If {@code base} is negative, {@code power} must either be an integer or a
	 * number that can be expressed as a fraction with an odd denominator.
	 * <p>
	 * If {@code base} is 0, {@code power} must be greater than 0. Will succeed without overflow
	 * if {@code abs(base)} is less than 1,000,000,000,000,000,000,000 and
	 * {@code abs(power)} is less than 100,000,000. If one value is smaller than its
	 * limit and the other value is somewhat larger than its limit, the calculation
	 * might succeed.
	 */
	private static BigDecimal powInternal(BigDecimal base, BigDecimal power)
	{
		if (isInt(power))
			return powInternal(base, power.longValueExact());
		int baseSignum = base.signum();
		if (baseSignum == 0)
			return BD0;
		boolean negativeBase = false;
		if (baseSignum == -1)
		{
			BigInteger[] powerMixedNumber = mixedNumber(power);
			BigInteger gcd = powerMixedNumber[1].gcd(powerMixedNumber[2]);
			BigInteger powerDenominator = powerMixedNumber[2].divide(gcd);
			if (isZero(powerDenominator.mod(bi(2))))
				throw new ArithmeticException("Base " + base + " is negative, but power " + power + " has an even denominator");
			negativeBase = true;
			base = base.negate();
		}
		BigDecimal lnBase = lnInternal(base);
		BigDecimal powerTimesLnBase = multiply(power, lnBase);
		BigDecimal result = expInternal(powerTimesLnBase);
		BigDecimal negatedResult = negativeBase ? result.negate() : result;
		return negatedResult;
	}

	/**
	 * Returns {@code base^power}.
	 * <p>
	 * If {@code base} is 0, {@code power} must be greater than 0. Will succeed without overflow
	 * if {@code abs(base)} is less than 1,000,000,000,000,000,000,000 and
	 * {@code abs(power)} is less than 100,000,000. If one value is smaller than its
	 * limit and the other value is somewhat larger than its limit, the calculation
	 * might succeed.
	 * <p>
	 * Returns a {@code BigDecimal} since the result may not be an integer (when {@code power} is negative).
	 */
	private static BigDecimal powInternal(BigInteger base, long power)
	{
		return powInternal(bd(base), power);
	}

	/**
	 * Returns {@code base^power}.
	 * <p>
	 * If {@code base} is 0, {@code power} must be greater than 0. Will succeed without overflow
	 * if {@code abs(base)} is less than 1,000,000,000,000,000,000,000 and
	 * {@code abs(power)} is less than 100,000,000. If one value is smaller than its
	 * limit and the other value is somewhat larger than its limit, the calculation
	 * might succeed.
	 * <p>
	 * Returns a {@code BigDecimal} since the result may not be an integer (when {@code power} is negative).
	 */
	private static BigDecimal powInternal(BigInteger base, BigInteger power)
	{
		return powInternal(bd(base), power.longValueExact());
	}

	/**
	 * Returns {@code base^power}.
	 * <p>
	 * If {@code base} is negative, {@code power} must either be an integer or a
	 * number that can be expressed as a fraction with an odd denominator.
	 * <p>
	 * If {@code base} is 0, {@code power} must be greater than 0. Will succeed without overflow
	 * if {@code abs(base)} is less than 1,000,000,000,000,000,000,000 and
	 * {@code abs(power)} is less than 100,000,000. If one value is smaller than its
	 * limit and the other value is somewhat larger than its limit, the calculation
	 * might succeed.
	 */
	private static BigDecimal powInternal(BigInteger base, BigDecimal power)
	{
		return powInternal(bd(base), power);
	}

	private static BigDecimal[] cacheFactorials()
	{
		BigDecimal[] factorials = new BigDecimal[EXP_MAX_ORDER + 1];
		factorials[0] = BD1;
		BigInteger product = BI1;
		for (int i = 1; i <= EXP_MAX_ORDER; i++)
		{
			product = product.multiply(bi(i));
			factorials[i] = bd(product);
		}
		return factorials;
	}

	/**
	 * Returns {@code e^power}
	 * <p>
	 * {@code abs(power)} must be less or equal to 4,900,000,000.
	 */
	private static BigDecimal expInternal(long power)
	{
		return powInternal(E110, power);
	}

	/**
	 * Returns {@code e^power}
	 * <p>
	 * {@code abs(power)} must be less or equal to 4,900,000,000.
	 */
	private static BigDecimal expInternal(BigInteger power)
	{
		return powInternal(E110, power.longValueExact());
	}

	/**
	 * Returns {@code e^power}
	 * <p>
	 * {@code abs(power)} must be less or equal to 4,900,000,000.
	 */
	private static BigDecimal expInternal(BigDecimal power)
	{
		if (isInt(power))
			return powInternal(E110, power.longValueExact());
		boolean negative = isNeg(power);
		if (negative)
			power = power.negate();
		BigDecimal wholePartContribution = powInternal(E110, wholePart(power));
		power = decimalPart(power);
		power = divide(power, EXP_DIVISION);
		BigDecimal previousResult = null;
		BigDecimal currentResult = BD0;
		BigDecimal powerToTheIMinusTwo = invert(power);
		for (int i = 1; i <= EXP_MAX_ORDER; i += 2)
		{
			BigDecimal powerToTheIMinusOne = multiply(powerToTheIMinusTwo, power);
			BigDecimal powerToTheI = multiply(powerToTheIMinusOne, power);
			BigDecimal term = divide(
				add(
					multiply(
						powerToTheIMinusOne,
						bd(i)),
					powerToTheI),
				FACTORIALS[i]);
			BigDecimal nextResult = add(currentResult, term);
			powerToTheIMinusTwo = powerToTheI;
			if (nextResult.compareTo(currentResult) == 0 || (previousResult != null && nextResult.compareTo(previousResult) == 0))
			{
				currentResult = nextResult;
				break;
			}
			previousResult = currentResult;
			currentResult = nextResult;
		}
		BigDecimal result = multiply(wholePartContribution, powInternal(currentResult, EXP_DIVISION));
		BigDecimal invertedResult = negative ? invert(result) : result;
		return invertedResult;
	}

	/**
	 * {@code val} must be greater than 0.
	 */
	private static BigDecimal lnInternal(long val)
	{
		return lnInternal(bd(val));
	}

	/**
	 * {@code val} must be greater than 0. More specifically, {@code val}
	 * must be greater than 10^-10,000,000 and less than 10^10,000,000.
	 */
	private static BigDecimal lnInternal(BigInteger val)
	{
		return lnInternal(bd(val));
	}

	private static List<BigDecimal> getLnCache()
	{
		int maxCachedLnInt = MAX_CACHED_LN.intValue();
		List<BigDecimal> cache = new ArrayList<>(maxCachedLnInt + 1);
		cache.add(null);
		cache.add(null);
		for (int i = 2; i <= maxCachedLnInt; i++)
			cache.add(calcLnInternal(bd(i), 1));
		return cache;
	}

	/**
	 * {@code val} must be greater than 0. More specifically, {@code val}
	 * must be greater than 10^-10,000,000 and less than 10^10,000,000.
	 */
	private static BigDecimal lnInternal(BigDecimal val)
	{
		tru(isPos(val), "Val must be greater than 0. Was %s", val);
		int compareToOne = val.compareTo(BD1);
		if (compareToOne == 0)
			return BD0;
		if (isInt(val) && lessOrEqual(val, MAX_CACHED_LN))
			return LN_CACHE.get(val.intValueExact());
		return calcLnInternal(val, compareToOne);
	}

	/**
	 * Expects that {@code val} is not equal to 1, and that {@code compareToOne}
	 * is greater than 1 if {@code val} is greater than 1, and {@code compareToOne}
	 * is less than 1 if {@code val} is less than 1. Does no cache lookups or
	 * preconditions checks.
	 */
	private static BigDecimal calcLnInternal(BigDecimal val, int compareToOne)
	{
		BigDecimal valInverted = val;
		if (compareToOne > 0)
			valInverted = invert(val);
		BigDecimal currentResult = bd(lnEstimate(valInverted));
		BigDecimal previousDifference = null;
		for (int i = 0; i < LN_MAX_ITERATIONS; i++)
		{
			BigDecimal check = expInternal(currentResult);
			BigDecimal nextResult = add(
				subtract(currentResult, BD1),
				divide(valInverted, check));
			// To achieve convergence, need the guess to have less precision than the check
			nextResult = nextResult.round(MC106);
			BigDecimal currentDifference = nextResult.subtract(currentResult).abs();
			if (isZero(currentDifference))
			{
				currentResult = nextResult;
				break;
			}
			else if (previousDifference != null && currentDifference.compareTo(previousDifference) >= 0)
			{
				currentResult = nextResult;
				break;
			}
			else
			{
				previousDifference = currentDifference;
				currentResult = nextResult;
			}
		}
		BigDecimal result = compareToOne > 0 ? currentResult.negate() : currentResult;
		return result;
	}

	private static final double INVERSE_LOG_10_E = 1.0 / Math.log10(Math.E);

	/**
	 * Assumes that {@code val} is between 0 and 1, exclusive
	 */
	private static double lnEstimate(BigDecimal val)
	{
		String string = val.toPlainString();
		int numberOfLeadingZeros = 0;
		for (; string.charAt(numberOfLeadingZeros + 2) == '0'; numberOfLeadingZeros++)
			;
		double log10Estimate = -numberOfLeadingZeros - 0.5;
		double lnEstimate = log10Estimate * INVERSE_LOG_10_E;
		return lnEstimate;
	}

	/**
	 * Returns the log of the given {@code base} of {@code val}.
	 * <p>
	 * {@code base} must be greater than 0 and not equal to 1.
	 * {@code val} must be greater than 0.
	 */
	private static BigDecimal logInternal(long base, long val)
	{
		return logInternal(bd(base), bd(val));
	}

	/**
	 * Returns the log of the given {@code base} of {@code val}.
	 * <p>
	 * {@code base} must be greater than 0 and not equal to 1.
	 * {@code val} must be greater than 0.
	 */
	private static BigDecimal logInternal(BigInteger base, long val)
	{
		return logInternal(bd(base), bd(val));
	}

	/**
	 * Returns the log of the given {@code base} of {@code val}.
	 * <p>
	 * {@code base} must be greater than 0 and not equal to 1.
	 * {@code val} must be greater than 0.
	 */
	private static BigDecimal logInternal(BigDecimal base, long val)
	{
		return logInternal(base, bd(val));
	}

	/**
	 * Returns the log of the given {@code base} of {@code val}.
	 * <p>
	 * {@code base} must be greater than 0 and not equal to 1.
	 * {@code val} must be greater than 0.
	 */
	private static BigDecimal logInternal(long base, BigInteger val)
	{
		return logInternal(bd(base), bd(val));
	}

	/**
	 * Returns the log of the given {@code base} of {@code val}.
	 * <p>
	 * {@code base} must be greater than 0 and not equal to 1.
	 * {@code val} must be greater than 0.
	 */
	private static BigDecimal logInternal(BigInteger base, BigInteger val)
	{
		return logInternal(bd(base), bd(val));
	}

	/**
	 * Returns the log of the given {@code base} of {@code val}.
	 * <p>
	 * {@code base} must be greater than 0 and not equal to 1.
	 * {@code val} must be greater than 0.
	 */
	private static BigDecimal logInternal(BigDecimal base, BigInteger val)
	{
		return logInternal(base, bd(val));
	}

	/**
	 * Returns the log of the given {@code base} of {@code val}.
	 * <p>
	 * {@code base} must be greater than 0 and not equal to 1.
	 * {@code val} must be greater than 0.
	 */
	private static BigDecimal logInternal(long base, BigDecimal val)
	{
		return logInternal(bd(base), val);
	}

	/**
	 * Returns the log of the given {@code base} of {@code val}.
	 * <p>
	 * {@code base} must be greater than 0 and not equal to 1.
	 * {@code val} must be greater than 0.
	 */
	private static BigDecimal logInternal(BigInteger base, BigDecimal val)
	{
		return logInternal(bd(base), val);
	}

	/**
	 * Returns the log of the given {@code base} of {@code val}.
	 * <p>
	 * {@code base} must be greater than 0 and not equal to 1.
	 * {@code val} must be greater than 0.
	 */
	private static BigDecimal logInternal(BigDecimal base, BigDecimal val)
	{
		if (!isPos(base))
			throw new ArithmeticException("Base must be greater than 0. Was " + base);
		if (isOne(base))
			throw new ArithmeticException("Base cannot be 1");
		if (!isPos(val))
			throw new ArithmeticException("Val must be greater than 0. Was " + val);
		if (isOne(val))
			return BD0;
		if (equal(base, val))
			return BD1;
		BigDecimal lnVal = lnInternal(val);
		BigDecimal lnBase = lnInternal(base);
		BigDecimal result = divide(lnVal, lnBase);
		return result;
	}

	/**
	 * If {@code val} is an integer, returns {@code val}. Otherwise, rounds {@code val}
	 * to 100 decimal places, strips trailing zeros, and returns the result.
	 * <p>
	 * This method is useful for removing the noisy, unreliable decimal digits beyond 100,
	 * caused by repeated rounded calculations.
	 */
	private static BigDecimal finalize(BigDecimal val)
	{
		if (val.scale() <= 0)
			return val;
		val = val.round(MC100);
		BigDecimal roundedToInt = val.setScale(0, RoundingMode.HALF_UP);
		if (val.compareTo(roundedToInt) == 0)
			return roundedToInt;
		return val.stripTrailingZeros();
	}
}