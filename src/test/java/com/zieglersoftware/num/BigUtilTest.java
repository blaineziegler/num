package com.zieglersoftware.num;

import static com.zieglersoftware.num.BigUtil.BD1;
import static com.zieglersoftware.num.BigUtil.bd;
import static com.zieglersoftware.num.BigUtil.bi;
import static com.zieglersoftware.num.BigUtil.invert;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class BigUtilTest
{
	@Test
	public void comparisons()
	{
		BigInteger a = bi(-3);
		BigDecimal b1 = bd("-2.3");
		BigDecimal b2 = bd("-2.300");
		BigDecimal c1 = bd("-2.000");
		BigDecimal c2 = bd("-2");
		BigInteger c3 = bi(-2);
		BigDecimal d1 = bd("0");
		BigDecimal d2 = bd("0.00");
		BigInteger d3 = bi(0);
		BigDecimal e = bd("0.5");
		BigDecimal f1 = bd("2");
		BigDecimal f2 = bd("2.000");
		BigInteger f3 = bi(2);
		BigDecimal g1 = bd("2.300");
		BigDecimal g2 = bd("2.3");
		assertTrue(BigUtil.equal(b1, b2));
		assertTrue(BigUtil.equal(c1, c2));
		assertTrue(BigUtil.equal(c1, c3));
		assertTrue(BigUtil.equal(c2, c3));
		assertTrue(BigUtil.equal(d1, d2));
		assertTrue(BigUtil.equal(d1, d3));
		assertTrue(BigUtil.equal(d2, d3));
		assertTrue(BigUtil.equal(f1, f2));
		assertTrue(BigUtil.equal(f1, f3));
		assertTrue(BigUtil.equal(f2, f3));
		assertTrue(BigUtil.equal(g1, g2));
		assertFalse(BigUtil.equal(b2, c1));
		assertFalse(BigUtil.equal(b2, g1));
		assertTrue(BigUtil.notEqual(b2, c1));
		assertTrue(BigUtil.notEqual(b2, g1));
		assertFalse(BigUtil.notEqual(b1, b2));
		assertFalse(BigUtil.notEqual(c1, c2));
		assertFalse(BigUtil.notEqual(c1, c3));
		assertFalse(BigUtil.notEqual(c2, c3));
		assertFalse(BigUtil.notEqual(d1, d2));
		assertFalse(BigUtil.notEqual(d1, d3));
		assertFalse(BigUtil.notEqual(d2, d3));
		assertFalse(BigUtil.notEqual(f1, f2));
		assertFalse(BigUtil.notEqual(f1, f3));
		assertFalse(BigUtil.notEqual(f2, f3));
		assertFalse(BigUtil.notEqual(g1, g2));
		assertTrue(BigUtil.greater(b1, a));
		assertTrue(BigUtil.greater(f3, e));
		assertFalse(BigUtil.greater(c1, c2));
		assertFalse(BigUtil.greater(c1, c3));
		assertTrue(BigUtil.less(a, b1));
		assertTrue(BigUtil.less(e, f3));
		assertFalse(BigUtil.less(c1, c2));
		assertFalse(BigUtil.less(c1, c3));
		assertTrue(BigUtil.greaterOrEqual(c1, c2));
		assertTrue(BigUtil.greaterOrEqual(c1, c3));
		assertTrue(BigUtil.lessOrEqual(c1, c2));
		assertTrue(BigUtil.lessOrEqual(c1, c3));
	}

	@Test
	public void isInt()
	{
		isIntAssert("1000000000000000000000000000", true);
		isIntAssert("1000000000000000000000000000.000000", true);
		isIntAssert("1000000000000000000000000000.000001", false);
		isIntAssert("0", true);
		isIntAssert("0.0", true);
		isIntAssert("-1000000000000000000000000000", true);
		isIntAssert("-1000000000000000000000000000.000000", true);
		isIntAssert("-1000000000000000000000000000.000001", false);
	}

	private void isIntAssert(String input, boolean expectedIsInt)
	{
		assertEquals(expectedIsInt, BigUtil.isInt(bd(input)));
	}

	@Test
	public void round()
	{
		roundAssert("1.8", 2);
		roundAssert("1.5", 2);
		roundAssert("1.2", 1);
		roundAssert("1.0", 1);
		roundAssert("1", 1);
		roundAssert("0.8", 1);
		roundAssert("0.5", 1);
		roundAssert("0.2", 0);
		roundAssert("0.0", 0);
		roundAssert("0", 0);
		roundAssert("-0.2", 0);
		roundAssert("-0.5", -1);
		roundAssert("-0.8", -1);
		roundAssert("-1", -1);
		roundAssert("-1.0", -1);
		roundAssert("-1.2", -1);
		roundAssert("-1.5", -2);
		roundAssert("-1.8", -2);
		roundAssert("1.9803", 0, "2");
		roundAssert("1.9803", 1, "2");
		roundAssert("1.9803", 2, "1.98");
		roundAssert("1.9803", 3, "1.98");
		roundAssert("1.9805", 3, "1.981");
		roundAssert("1.9803", 4, "1.9803");
		roundAssert("1.9803", 5, "1.9803");
		roundAssert("0.00", 0, "0");
		roundAssert("0.00", 1, "0");
		roundAssert("0.00", 2, "0");
		roundAssert("0.00", 3, "0");
		roundAssert("-1.0", 1, "-1");
		roundAssert("-1.55", 0, "-2");
		roundAssert("-1.55", 1, "-1.6");
		roundAssert("-1.55", 2, "-1.55");
		roundAssert("-1.55", 3, "-1.55");
	}

	private void roundAssert(String input, long expectedRounded)
	{
		assertEquals(bi(expectedRounded), BigUtil.round(bd(input)));
	}

	private void roundAssert(String input, int decimalPlaces, String expectedRounded)
	{
		assertEquals(bd(expectedRounded), BigUtil.round(bd(input), decimalPlaces));
	}

	@Test
	public void wholePart()
	{
		wholePartAssert("3.80", 3);
		wholePartAssert("3.0", 3);
		wholePartAssert("3", 3);
		wholePartAssert("0.5", 0);
		wholePartAssert("0", 0);
		wholePartAssert("-3.80", -3);
		wholePartAssert("-3.0", -3);
		wholePartAssert("-3", -3);
		wholePartAssert("-0.5", 0);
	}

	private void wholePartAssert(String input, long expectedWholePart)
	{
		assertEquals(bi(expectedWholePart), BigUtil.wholePart(bd(input)));
	}

	@Test
	public void decimalPart()
	{
		decimalPartAssert("3.80", "0.8");
		decimalPartAssert("3.0", "0");
		decimalPartAssert("3", "0");
		decimalPartAssert("0.5", "0.5");
		decimalPartAssert("0", "0");
		decimalPartAssert("-3.80", "-0.8");
		decimalPartAssert("-3.0", "0");
		decimalPartAssert("-3", "0");
		decimalPartAssert("-0.5", "-0.5");
	}

	private void decimalPartAssert(String input, String expectedDecimalPart)
	{
		assertEquals(bd(expectedDecimalPart), BigUtil.decimalPart(bd(input)));
	}

	@Test
	public void wholeAndDecimalParts()
	{
		wholeAndDecimalPartsAssert("3.80", 3, 8);
		wholeAndDecimalPartsAssert("3.0", 3, 0);
		wholeAndDecimalPartsAssert("3", 3, 0);
		wholeAndDecimalPartsAssert("0.5", 0, 5);
		wholeAndDecimalPartsAssert("0", 0, 0);
		wholeAndDecimalPartsAssert("-3.80", -3, -8);
		wholeAndDecimalPartsAssert("-3.0", -3, 0);
		wholeAndDecimalPartsAssert("-3", -3, 0);
		wholeAndDecimalPartsAssert("-0.5", 0, -5);
	}

	private void wholeAndDecimalPartsAssert(String input, long expectedWholePart, long expectedDecimalPart)
	{
		assertArrayEquals(new BigInteger[] { bi(expectedWholePart), bi(expectedDecimalPart) }, BigUtil.wholeAndDecimalParts(bd(input)));
	}

	@Test
	public void mixedNumber()
	{
		mixedNumberAssert("3.80", 3, 8, 10);
		mixedNumberAssert("3.0", 3, 0, 1);
		mixedNumberAssert("3", 3, 0, 1);
		mixedNumberAssert("0.5", 0, 5, 10);
		mixedNumberAssert("0", 0, 0, 1);
		mixedNumberAssert("-3.80", -3, -8, 10);
		mixedNumberAssert("-3.0", -3, 0, 1);
		mixedNumberAssert("-3", -3, 0, 1);
		mixedNumberAssert("-0.5", 0, -5, 10);
	}

	private void mixedNumberAssert(String input, long expectedWholePart, long expectedNumerator, long expectedDenominator)
	{
		assertArrayEquals(new BigInteger[] { bi(expectedWholePart), bi(expectedNumerator), bi(expectedDenominator) }, BigUtil.mixedNumber(bd(input)));
	}

	@Test
	public void fraction()
	{
		fractionAssert("3.80", 38, 10);
		fractionAssert("3.0", 3, 1);
		fractionAssert("3", 3, 1);
		fractionAssert("0.5", 5, 10);
		fractionAssert("0", 0, 1);
		fractionAssert("-3.80", -38, 10);
		fractionAssert("-3.0", -3, 1);
		fractionAssert("-3", -3, 1);
		fractionAssert("-0.5", -5, 10);
	}

	private void fractionAssert(String input, long expectedNumerator, long expectedDenominator)
	{
		assertArrayEquals(new BigInteger[] { bi(expectedNumerator), bi(expectedDenominator), }, BigUtil.fraction(bd(input)));
	}

	@Test
	public void digitsBigDecimal()
	{
		digitsBigDecimalAssert("123.45600", Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6));
		digitsBigDecimalAssert("3.0", Arrays.asList(3), Arrays.asList());
		digitsBigDecimalAssert("3", Arrays.asList(3), Arrays.asList());
		digitsBigDecimalAssert("0.5", Arrays.asList(0), Arrays.asList(5));
		digitsBigDecimalAssert("0", Arrays.asList(0), Arrays.asList());
		digitsBigDecimalAssert("-3.0", Arrays.asList(3), Arrays.asList());
		digitsBigDecimalAssert("-123.45600", Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6));
	}

	private void digitsBigDecimalAssert(String input, List<Integer> expectedWholeDigits, List<Integer> expectedDecimalDigits)
	{
		assertArrayEquals(new Object[][] { expectedWholeDigits.toArray(), expectedDecimalDigits.toArray() }, BigUtil.digits(bd(input)));
	}

	@Test
	public void digitsBigInteger()
	{
		digitsBigIntegerAssert(123, 1, 2, 3);
		digitsBigIntegerAssert(3, 3);
		digitsBigIntegerAssert(0, 0);
		digitsBigIntegerAssert(-3, 3);
		digitsBigIntegerAssert(-123, 1, 2, 3);
	}

	private void digitsBigIntegerAssert(long input, int... expectedDigits)
	{
		assertArrayEquals(expectedDigits, BigUtil.digits(bi(input)));
	}

	@Test
	public void digitCount()
	{
		digitCountAssert(123, 3);
		digitCountAssert(3, 1);
		digitCountAssert(0, 1);
		digitCountAssert(-3, 1);
		digitCountAssert(-123, 3);
	}

	private void digitCountAssert(long input, int expectedDigitCount)
	{
		assertEquals(expectedDigitCount, BigUtil.digitCount(bi(input)));
	}

	@Test
	public void multiply()
	{
		multiplyAssert("0", "1", "0");
		multiplyAssert("1", "2", "2");
		multiplyAssert("-1", "2", "-2");
		multiplyAssert("1", "-2", "-2");
		multiplyAssert("1", "-2", "-2");
		multiplyAssert("-1", "-2", "2");
		multiplyAssert("60", "1", "60");
		multiplyAssert("6E1", "1", "60");
		multiplyAssert("60", "0.1", "6");
		multiplyAssert("6E1", "0.1", "6");
		multiplyAssert("60.0", "0.1", "6");
	}

	private void multiplyAssert(String a, String b, String expectedResult)
	{
		assertEquals(bd(expectedResult), BigUtil.multiply(bd(a), bd(b)));
	}

	@Test
	public void divide()
	{
		divideAssert(2, 1, "2");
		divideAssert(1, 2, "0.5");
		divideAssert(-2, 1, "-2");
		divideAssert(-1, 2, "-0.5");
		divideAssert(2, -1, "-2");
		divideAssert(1, -2, "-0.5");
		divideAssert(-2, -1, "2");
		divideAssert(-1, -2, "0.5");
		divideAssert("1200", "2", "600");
		divideAssert("1200.0", "2", "600");
		divideAssert("1200", "2.0", "600");
		divideAssert("1200.0", "2.0", "600");
		String oneThird = "0.33333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333";
		divideAssert(3, 1, "3");
		divideAssert(1, 3, oneThird);
		divideAssert(-3, 1, "-3");
		divideAssert(-1, 3, '-' + oneThird);
		divideAssert(3, -1, "-3");
		divideAssert(1, -3, '-' + oneThird);
		divideAssert(-3, -1, "3");
		divideAssert(-1, -3, oneThird);
		divideAssert(100, 3, "33.333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333333");
		divideAssert(1, 99, "0.01010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101");
		divideAssert(10, 99, "0.1010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101010101");
		divideAssert(
			"100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000001",
			"1000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000",
			"0.1");
		divideAssert(
			"72036132161033157404651802854995145321405681650722343644452412176617642028800857002468569835359447770574239068",
			"719641679930401172873644384165785667546510306201022414030493628138038381906102467557128569783810667038703687",
			"100.1");
	}

	private void divideAssert(int n, int d, String expectedResult)
	{
		assertEquals(bd(expectedResult), BigUtil.divide(bd(n), bd(d)));
	}

	private void divideAssert(String n, String d, String expectedResult)
	{
		assertEquals(bd(expectedResult), BigUtil.divide(bd(n), bd(d)));
	}

	@Test
	public void pow()
	{
		powAssert("0", "1", "0");
		powAssert("0", "1234.5678", "0");
		powAssert("1", "0", "1");
		powAssert("1", "1", "1");
		powAssert("1", "2", "1");
		powAssert("1", "0.5", "1");
		powAssert("1", "0.2", "1");
		powAssert("1", "1234.5678", "1");
		powAssert("1", "-1", "1");
		powAssert("1", "-2", "1");
		powAssert("2", "0", "1");
		powAssert("2", "1", "2");
		powAssert("2", "2", "4");
		powAssert("2", "-1", "0.5");
		powAssert("2", "-2", "0.25");
		powAssert("4", "0.5", "2");
		powAssert("4", "-0.5", "0.5");
		powAssert("32", "0.2", "2");
		powAssert("32", "-0.2", "0.5");
		powAssert(BigUtil.pow(bd("21.34"), bd("19.92")).toString(), invert(bd("19.92")).toString(), "21.34");
		powAssert(BigUtil.pow(bd("21.34"), bd("-19.92")).toString(), invert(bd("-19.92")).toString(), "21.34");
		powAssert("-1", "0", "1");
		powAssert("-1", "1", "-1");
		powAssert("-1", "2", "1");
		powAssert("-1", "0.2", "-1");
		powAssert("-1", "-1", "-1");
		powAssert("-1", "-2", "1");
		powAssert("-2", "0", "1");
		powAssert("-2", "1", "-2");
		powAssert("-2", "2", "4");
		powAssert("-2", "-1", "-0.5");
		powAssert("-2", "-2", "0.25");
		powAssert("-32", "0.2", "-2");
		powAssert("-32", "-0.2", "-0.5");
		powAssert("2", "0.5");
		powAssert("2", "-0.5");
		powAssert("2", "17.3");
		powAssert("2", "-17.3");
		powAssert("0.123", "5.168");
		powAssert("0.123", "-5.168");
		powAssert("123.123", "5.168");
		powAssert("123.123", "-5.168");
	}

	private void powAssert(String base, String power, String expectedResult)
	{
		assertEquals(bd(expectedResult), BigUtil.pow(bd(base), bd(power)));
	}

	private void powAssert(String base, String power)
	{
		assertEquals(
			Math.pow(Double.valueOf(base), Double.valueOf(power)),
			BigUtil.pow(bd(base), bd(power)).doubleValue(),
			0.0001);
	}

	@Test
	public void nthRoot()
	{
		nthRootAssert("0", 1, "0");
		nthRootAssert("1", 1, "1");
		nthRootAssert("-1", 1, "-1");
		nthRootAssert("1", 2, "1");
		nthRootAssert("1", 3, "1");
		nthRootAssert("-1", 3, "-1");
		nthRootAssert("2", 1, "2");
		nthRootAssert("-2", 1, "-2");
		nthRootAssert("8", 1, "8");
		nthRootAssert("-8", 1, "-8");
		nthRootAssert("8", 3, "2");
		nthRootAssert("-8", 3, "-2");
	}

	private void nthRootAssert(String base, long n, String expectedResult)
	{
		assertEquals(bd(expectedResult), BigUtil.nthRoot(bd(base), n));
	}

	@Test
	public void sqrt()
	{
		sqrtAssert("0", "0");
		sqrtAssert("1", "1");
		sqrtAssert("4", "2");
		sqrtAssert("9", "3");
		sqrtAssert("16", "4");
		sqrtAssert("25", "5");
		sqrtAssert("36", "6");
		sqrtAssert("100", "10");
		sqrtAssert("10000000000000000000000", "100000000000");
	}

	private void sqrtAssert(String val, String expectedResult)
	{
		assertEquals(bd(expectedResult), BigUtil.sqrt(bd(val)));
	}

	@Test
	public void exp()
	{
		expAssert("0");
		expAssert("1");
		expAssert("-1");
		expAssert("2");
		expAssert("-2");
		expAssert("10");
		expAssert("-10");
		expAssert("0.12345");
		expAssert("-0.12345");
		expAssert("23.12345");
		expAssert("-23.12345");
	}

	private void expAssert(String power)
	{
		assertEquals(
			Math.exp(Double.valueOf(power)),
			BigUtil.exp(bd(power)).doubleValue(),
			0.0001);
	}

	@Test
	public void ln()
	{
		lnAssert("1");
		lnAssert("2");
		lnAssert("10");
		lnAssert("10000000000000000000000");
		lnAssert("0.12345");
		lnAssert("23.12345");
		lnAssert("1.105099999999999999");
		lnAssert("1.05");
		lnAssert(BigUtil.exp(bd("1")).toString());
		lnAssert(BigUtil.exp(bd("2")).toString());
		lnAssert(BigUtil.exp(bd("-2")).toString());
		lnAssert(BigUtil.exp(bd("0.5")).toString());
		lnAssert(BigUtil.exp(bd("-0.5")).toString());
		lnAssert(BigUtil.exp(bd("12.3")).toString());
		lnAssert(BigUtil.exp(bd("-12.3")).toString());
		lnAssertByChecking("1.000000000000000000000000000000005");
	}

	private void lnAssert(String val)
	{
		BigDecimal expected = bd(Math.log(Double.valueOf(val)));
		BigDecimal actual = BigUtil.ln(bd(val));
		assertBdApproximate(expected, actual);
	}

	// For values where Math.log() is not precise enough
	private void lnAssertByChecking(String val)
	{
		BigDecimal bdVal = bd(val);
		BigDecimal lnResult = BigUtil.ln(bdVal);
		BigDecimal check = BigUtil.exp(lnResult);
		assertTrue(check.compareTo(bdVal) == 0);
	}

	@Test
	public void log()
	{
		logAssert("2", "4");
		logAssert("4", "2");
		logAssert("2", "8");
		logAssert("8", "2");
		logAssert("4", "8");
		logAssert("8", "4");
		logAssert("12.3", BigUtil.pow(bd("12.3"), bd("4.56")).toString());
		logAssert("12.3", BigUtil.pow(bd("12.3"), bd("-4.56")).toString());
		logAssert("10", "100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
		logAssert("1.05", "100000000000000000000000000000000000000000000000000000000000000000000000000000000000000000.123456879123456789");
		logAssertByChecking("1.000000000000000000000000000000005", "12.345");
	}

	private void logAssert(String base, String val)
	{
		BigDecimal expected = bd(Math.log(Double.valueOf(val)) / Math.log(Double.valueOf(base)));
		BigDecimal actual = BigUtil.log(bd(base), bd(val));
		assertBdApproximate(expected, actual);
	}

	// For values where Math.log() is not precise enough
	private void logAssertByChecking(String base, String val)
	{
		BigDecimal bdBase = bd(base);
		BigDecimal bdVal = bd(val);
		BigDecimal logResult = BigUtil.log(bdBase, bdVal);
		BigDecimal check = BigUtil.pow(bdBase, logResult);
		assertTrue(check.compareTo(bdVal) == 0);
	}

	private void assertBdApproximate(BigDecimal expected, BigDecimal actual)
	{
		if (BigUtil.isZero(actual))
			assertTrue(BigUtil.isZero(expected));
		else
			assertTrue(BigUtil.divide(expected, actual).subtract(BD1).abs().compareTo(bd("0.000000000000001")) < 0);
	}
}