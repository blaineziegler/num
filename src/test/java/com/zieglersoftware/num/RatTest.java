package com.zieglersoftware.num;

import static com.zieglersoftware.num.Rat.rat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class RatTest
{
	private static final double DOUBLE_COMPARISON_TOLERANCE = 0.000000000000000000001;

	@Test
	public void constants()
	{
		assertEquals(Math.PI, Rat.PI.toDouble(), DOUBLE_COMPARISON_TOLERANCE);
		assertEquals(Math.E, Rat.E.toDouble(), DOUBLE_COMPARISON_TOLERANCE);
	}

	@Test
	public void factories()
	{
		factoryFromLong(0, 0);
		factoryFromLong(1, 1);
		factoryFromLong(-1, -1);
		factoryFromLong(100, 100);
		factoryFromLong(-100, -100);
		factoryFromLong(999, 999);
		factoryFromLong(-999, -999);
		factoryFromLong(1000, 1000);
		factoryFromLong(-1000, -1000);
		factoryFromLong(1001, 1001);
		factoryFromLong(-1001, -1001);
		factoryFromLong(1000000000000000L, 1000000000000000L);
		factoryFromLong(-1000000000000000L, -1000000000000000L);

		factoryFromBigInteger(BigInteger.valueOf(0), 0, 1);
		factoryFromBigInteger(BigInteger.valueOf(1), 1, 1);
		factoryFromBigInteger(BigInteger.valueOf(-1), -1, 1);
		factoryFromBigInteger(BigInteger.valueOf(100), 100, 1);
		factoryFromBigInteger(BigInteger.valueOf(-100), -100, 1);
		factoryFromBigInteger(new BigInteger("1000000000000000"), 1000000000000000L, 1);
		factoryFromBigInteger(new BigInteger("-1000000000000000"), -1000000000000000L, 1);

		factoryFromLongs(0, 1, 0);
		factoryFromLongs(0, -1, 0);
		factoryFromLongs(-0, -1, 0);
		factoryFromLongs(0, 10, 0);
		factoryFromLongs(-0, 10, 0);
		factoryFromLongs(1, 1, 1);
		factoryFromLongs(-1, 1, -1);
		factoryFromLongs(1, -1, -1);
		factoryFromLongs(-1, -1, 1);
		factoryFromLongs(1, 2, 1, 2);
		factoryFromLongs(-1, 2, -1, 2);
		factoryFromLongs(2, 4, 1, 2);
		factoryFromLongs(-2, 4, -1, 2);
		factoryFromLongs(30, 40, 3, 4);
		factoryFromLongs(30, -40, -3, 4);
		factoryFromLongs(1, 3, 1, 3);
		factoryFromLongs(-1, 3, -1, 3);
		factoryFromLongs(2, 3, 2, 3);
		factoryFromLongs(-2, 3, -2, 3);
		factoryFromLongs(3, 3, 1);
		factoryFromLongs(-3, 3, -1);
		factoryFromLongs(5, 3, 5, 3);
		factoryFromLongs(-5, 3, -5, 3);
		factoryFromLongs(10, 6, 5, 3);
		factoryFromLongs(-10, 6, -5, 3);
		factoryFromLongs(9, 27, 1, 3);
		factoryFromLongs(-9, 27, -1, 3);
		factoryFromLongs(1, 10, 1, 10);
		factoryFromLongs(1, 100, 1, 100);
		factoryFromLongs(1, 1000, 1, 1000);
		factoryFromLongs(1, 10000, 1, 10000);
		factoryFromLongs(1, 100000, 1, 100000);

		factoryFromBigIntegers(BigInteger.valueOf(0), BigInteger.valueOf(1), 0);
		factoryFromBigIntegers(BigInteger.valueOf(0), BigInteger.valueOf(-1), 0);
		factoryFromBigIntegers(BigInteger.valueOf(-0), BigInteger.valueOf(-1), 0);
		factoryFromBigIntegers(BigInteger.valueOf(0), BigInteger.valueOf(10), 0);
		factoryFromBigIntegers(BigInteger.valueOf(-0), BigInteger.valueOf(10), 0);
		factoryFromBigIntegers(BigInteger.valueOf(1), BigInteger.valueOf(1), 1);
		factoryFromBigIntegers(BigInteger.valueOf(-1), BigInteger.valueOf(1), -1);
		factoryFromBigIntegers(BigInteger.valueOf(1), BigInteger.valueOf(-1), -1);
		factoryFromBigIntegers(BigInteger.valueOf(-1), BigInteger.valueOf(-1), 1);
		factoryFromBigIntegers(BigInteger.valueOf(1), BigInteger.valueOf(2), 1, 2);
		factoryFromBigIntegers(BigInteger.valueOf(-1), BigInteger.valueOf(2), -1, 2);
		factoryFromBigIntegers(BigInteger.valueOf(2), BigInteger.valueOf(4), 1, 2);
		factoryFromBigIntegers(BigInteger.valueOf(-2), BigInteger.valueOf(4), -1, 2);
		factoryFromBigIntegers(BigInteger.valueOf(30), BigInteger.valueOf(40), 3, 4);
		factoryFromBigIntegers(BigInteger.valueOf(30), BigInteger.valueOf(-40), -3, 4);
		factoryFromBigIntegers(new BigInteger("100000000000000000000000000000000000000000"), new BigInteger("100000000000000000000000000000000000000000"), 1);
		factoryFromBigIntegers(new BigInteger("-100000000000000000000000000000000000000000"), new BigInteger("100000000000000000000000000000000000000000"), -1);
		factoryFromBigIntegers(new BigInteger("100000000000000000000000000000000000000000"), new BigInteger("-100000000000000000000000000000000000000000"), -1);
		factoryFromBigIntegers(new BigInteger("-100000000000000000000000000000000000000000"), new BigInteger("-100000000000000000000000000000000000000000"), 1);
		factoryFromBigIntegers(new BigInteger("200000000000000000000000000000000000000000"), new BigInteger("100000000000000000000000000000000000000000"), 2);
		factoryFromBigIntegers(new BigInteger("-200000000000000000000000000000000000000000"), new BigInteger("100000000000000000000000000000000000000000"), -2);
		factoryFromBigIntegers(new BigInteger("200000000000000000000000000000000000000000"), new BigInteger("-100000000000000000000000000000000000000000"), -2);
		factoryFromBigIntegers(new BigInteger("-200000000000000000000000000000000000000000"), new BigInteger("-100000000000000000000000000000000000000000"), 2);
		factoryFromBigIntegers(new BigInteger("100000000000000000000000000000000000000000"), new BigInteger("200000000000000000000000000000000000000000"), 1, 2);
		factoryFromBigIntegers(new BigInteger("-100000000000000000000000000000000000000000"), new BigInteger("200000000000000000000000000000000000000000"), -1, 2);
		factoryFromBigIntegers(new BigInteger("100000000000000000000000000000000000000000"), new BigInteger("-200000000000000000000000000000000000000000"), -1, 2);
		factoryFromBigIntegers(new BigInteger("-100000000000000000000000000000000000000000"), new BigInteger("-200000000000000000000000000000000000000000"), 1, 2);
		factoryFromBigIntegers(BigInteger.valueOf(1), BigInteger.valueOf(10), 1, 10);
		factoryFromBigIntegers(BigInteger.valueOf(1), BigInteger.valueOf(100), 1, 100);
		factoryFromBigIntegers(BigInteger.valueOf(1), BigInteger.valueOf(1000), 1, 1000);
		factoryFromBigIntegers(BigInteger.valueOf(1), BigInteger.valueOf(10000), 1, 10000);
		factoryFromBigIntegers(BigInteger.valueOf(1), BigInteger.valueOf(100000), 1, 100000);

		factoryFromBigDecimal(new BigDecimal(0), 0, 1);
		factoryFromBigDecimal(new BigDecimal("00"), 0, 1);
		factoryFromBigDecimal(new BigDecimal("0"), 0, 1);
		factoryFromBigDecimal(new BigDecimal("0."), 0, 1);
		factoryFromBigDecimal(new BigDecimal("0.0"), 0, 1);
		factoryFromBigDecimal(new BigDecimal("0.00"), 0, 1);
		factoryFromBigDecimal(new BigDecimal(1), 1, 1);
		factoryFromBigDecimal(new BigDecimal(-1), -1, 1);
		factoryFromBigDecimal(new BigDecimal("01"), 1, 1);
		factoryFromBigDecimal(new BigDecimal("1"), 1, 1);
		factoryFromBigDecimal(new BigDecimal("1."), 1, 1);
		factoryFromBigDecimal(new BigDecimal("1.0"), 1, 1);
		factoryFromBigDecimal(new BigDecimal("1.0000"), 1, 1);
		factoryFromBigDecimal(new BigDecimal("-1.0000"), -1, 1);
		factoryFromBigDecimal(new BigDecimal("-0.1"), -1, 10);
		factoryFromBigDecimal(new BigDecimal("2.5000"), 5, 2);
		factoryFromBigDecimal(new BigDecimal("-2.5000"), -5, 2);
		factoryFromBigDecimal(new BigDecimal(".333"), 333, 1000);
		factoryFromBigDecimal(new BigDecimal("-.333"), -333, 1000);
		factoryFromBigDecimal(new BigDecimal(".333333"), 333333, 1000000);
		factoryFromBigDecimal(new BigDecimal("-.333333"), -333333, 1000000);
		factoryFromBigDecimal(new BigDecimal("33.333333"), (33 * 1000000) + 333333, 1000000);
		factoryFromBigDecimal(new BigDecimal("-33.333333"), -(33 * 1000000) - 333333, 1000000);
		factoryFromBigDecimal(new BigDecimal("11111.11111"), (11111 * 100000) + 11111, 100000);
		factoryFromBigDecimal(new BigDecimal("-11111.11111"), -(11111 * 100000) - 11111, 100000);
		factoryFromBigDecimal(new BigDecimal("0.500000000000000000000000000000000000000000000000000"), 1, 2);
		factoryFromBigDecimal(new BigDecimal("-0.500000000000000000000000000000000000000000000000000"), -1, 2);
		factoryFromBigDecimal(new BigDecimal("0.1"), 1, 10);
		factoryFromBigDecimal(new BigDecimal("0.01"), 1, 100);
		factoryFromBigDecimal(new BigDecimal("0.001"), 1, 1000);
		factoryFromBigDecimal(new BigDecimal("0.0001"), 1, 10000);
		factoryFromBigDecimal(new BigDecimal("0.00001"), 1, 100000);

		factoryFromDouble(1D, 1, 1);
		factoryFromDouble(-1D, -1, 1);
		factoryFromDouble(0.1, 1, 10);
		factoryFromDouble(-0.1, -1, 10);
		factoryFromDouble(2.5, 5, 2);
		factoryFromDouble(-2.5, -5, 2);
		factoryFromDouble(.333, 333, 1000);
		factoryFromDouble(-.333, -333, 1000);
		factoryFromDouble(.333333, 333333, 1000000);
		factoryFromDouble(-.333333, -333333, 1000000);
		factoryFromDouble(33.333333, (33 * 1000000) + 333333, 1000000);
		factoryFromDouble(-33.333333, -(33 * 1000000) - 333333, 1000000);
		factoryFromDouble(11111.11111, (11111 * 100000) + 11111, 100000);
		factoryFromDouble(-11111.11111, -(11111 * 100000) - 11111, 100000);
		factoryFromDouble(0.1, 1, 10);
		factoryFromDouble(0.01, 1, 100);
		factoryFromDouble(0.001, 1, 1000);
		factoryFromDouble(0.0001, 1, 10000);
		factoryFromDouble(0.00001, 1, 100000);

		factoryFromString("0", 0);
		factoryFromString("00000000000", 0);
		factoryFromString("1", 1);
		factoryFromString("-1", -1);
		factoryFromString("0001", 1);
		factoryFromString("-001", -1);
		factoryFromString("0001.000000", 1);
		factoryFromString("-001.000000", -1);
		factoryFromString("1.000000000", 1);
		factoryFromString("-1.00000000000", -1);
		factoryFromString("0.1", 1, 10);
		factoryFromString("0.01", 1, 100);
		factoryFromString("0.001", 1, 1000);
		factoryFromString("0.0001", 1, 10000);
		factoryFromString("0.00001", 1, 100000);
		factoryFromString("1/10", 1, 10);
		factoryFromString("1/100", 1, 100);
		factoryFromString("1/1000", 1, 1000);
		factoryFromString("1/10000", 1, 10000);
		factoryFromString("1/100000", 1, 100000);
		factoryFromString("-0.1", -1, 10);
		factoryFromString(".1", 1, 10);
		factoryFromString("-.1", -1, 10);
		factoryFromString("-0.01", -1, 100);
		factoryFromString(".01", 1, 100);
		factoryFromString("-.01", -1, 100);
		factoryFromString("1.0", 1, 1);
		factoryFromString("-1.0", -1, 1);
		factoryFromString("1.1", 11, 10);
		factoryFromString("-1.1", -11, 10);
		factoryFromString("1.10", 11, 10);
		factoryFromString("-1.10", -11, 10);
		factoryFromString("01.10", 11, 10);
		factoryFromString("-01.10", -11, 10);
		factoryFromString("1.111", 1111, 1000);
		factoryFromString("-1.111", -1111, 1000);
		factoryFromString(".5", 1, 2);
		factoryFromString("-.5", -1, 2);
		factoryFromString("0.5", 1, 2);
		factoryFromString("-0.5", -1, 2);
		factoryFromString(".75", 3, 4);
		factoryFromString("-.75", -3, 4);
		factoryFromString("1.75", 7, 4);
		factoryFromString("-1.75", -7, 4);
		factoryFromString("0.001", 1, 1000);
		factoryFromString("-0.001", -1, 1000);
		factoryFromString("0.002", 1, 500);
		factoryFromString("-0.002", -1, 500);
		factoryFromString("0.004", 1, 250);
		factoryFromString("-0.004", -1, 250);
		factoryFromString("0.005", 1, 200);
		factoryFromString("-0.005", -1, 200);
		factoryFromString("0.010", 1, 100);
		factoryFromString("-0.010", -1, 100);
		factoryFromString("--1", 1);
		factoryFromString("0+5", 5);
		factoryFromString("1+1", 2);
		factoryFromString("1-1", 0);
		factoryFromString("1+5", 6);
		factoryFromString("-1+5", 4);
		factoryFromString("0+-5", -5);
		factoryFromString("1+-5", -4);
		factoryFromString("-1+-5", -6);
		factoryFromString("0-5", -5);
		factoryFromString("1-5", -4);
		factoryFromString("-1-5", -6);
		factoryFromString("0--5", 5);
		factoryFromString("1--5", 6);
		factoryFromString("-1--5", 4);
		factoryFromString("-3+4+5-10--30+15-51", -10);
		factoryFromString("0*1", 0);
		factoryFromString("2*-3*4", -24);
		factoryFromString("0/1", 0);
		factoryFromString("3/1.5", 2);
		factoryFromString("1.5/3", 1, 2);
		factoryFromString("1.5/3.5", 3, 7);
		factoryFromString("-1.5/3.5", -3, 7);
		factoryFromString("1.5/-3.5", -3, 7);
		factoryFromString("-1.5/-3.5", 3, 7);
		factoryFromString("2/-3/4", 2, -12);
		factoryFromString("-2/-3*4*5/6", 40, 18);
		factoryFromString("100/30", 10, 3);
		factoryFromString("1+2*3", 7);
		factoryFromString("1*2+3", 5);
		factoryFromString("1-2/3+4", 13, 3);
		factoryFromString("1-2+3*4", 11);
		factoryFromString("1*2-3/4", 5, 4);
		factoryFromString("1*2/3+4", 14, 3);
		factoryFromString("1^0", 1);
		factoryFromString("2^0", 1);
		factoryFromString("0^1", 0);
		factoryFromString("0^-1", 0);
		factoryFromString("0^43.1234", 0);
		factoryFromString("0.000^-43.1234", 0);
		factoryFromString("1^1", 1);
		factoryFromString("1^2", 1);
		factoryFromString("1^-1", 1);
		factoryFromString("-1^1", -1);
		factoryFromString("-1^-1", -1);
		factoryFromString("3^2", 9);
		factoryFromString("2^3", 8);
		factoryFromString("-3^2", -9);
		factoryFromString("-2^3", -8);
		factoryFromString("3^-2", 1, 9);
		factoryFromString("2^-3", 1, 8);
		factoryFromString("-3^-2", -1, 9);
		factoryFromString("-2^-3", -1, 8);
		factoryFromString("4^3^2", 262144);
		factoryFromString("2^2^3-2^-2/3^2+4+5^2-3*2^2*5", 8099, 36);
		factoryFromString("(0+5)", 5);
		factoryFromString("(2)-(1)", 1);
		factoryFromString("(1+-1)+2^2", 4);
		factoryFromString("2(3*4)", 24);
		factoryFromString("(1)(2)3(4)5(((6)7)8)", 40320);
		factoryFromString("1+(1)(2)3(4)5(((6)7)8)+1", 40322);
		factoryFromString("(3*4)2", 24);
		factoryFromString("(1+2)(3*4)", 36);
		factoryFromString("(1+2)-(3*4)", -9);
		factoryFromString("(1+2)5(3*4)", 180);
		factoryFromString("(1+2)-5(3*4)", -57);
		factoryFromString("(1+2)(1+2)(1+2)", 27);
		factoryFromString("(2*-3*4)", -24);
		factoryFromString("(3^2)", 9);
		factoryFromString("(-3)^2", 9);
		factoryFromString("2^2^3-(2^2)^3*(1+2)/(3/(4))", 0);
		factoryFromString("(-1)", -1);
		factoryFromString(".0-(1)", -1);
		factoryFromString("-1*(1)", -1);
		factoryFromString("-(1)", -1);
		factoryFromString("-(-(1))", 1);
		factoryFromString("-(-(-(1)))", -1);
		factoryFromString("-(1+2)+3", 0);
		factoryFromString("-(-(1+2)+3)+4", 4);
		factoryFromString("-(-(-(1+2)+3)+4)+5", 1);
		factoryFromString("0-(1+2)", -3);
		factoryFromString("-1*(1+2)", -3);
		factoryFromString("-(1+2)", -3);
		factoryFromString("2^(0-(1))", 1, 2);
		factoryFromString("2^(-1*(1))", 1, 2);
		factoryFromString("2^(-(1))", 1, 2);
		factoryFromString("2^0-(2)", -1);
		factoryFromString("2^(.0-(2))", 1, 4);
		factoryFromString("2^(-1*(2))", 1, 4);
		factoryFromString("2^-(2)", 1, 4);
		factoryFromString("(-1)^1", -1);
		factoryFromString("(-1)^3", -1);
		factoryFromString("(-1)^(1/3)", -1);
		factoryFromString("(-1)^(2/3)", 1);
		factoryFromString("(-1)^(3/3)", -1);
		factoryFromString("(-1)^(4/3)", 1);
		factoryFromString("(-1)^(5/3)", -1);
		factoryFromString("(-32)^(1/5)", -2);
		factoryFromString("(-32)^(2/5)", 4);
		factoryFromString("(-32)^(3/5)", -8);
		factoryFromString("(-32)^(4/5)", 16);
		factoryFromString("(-32)^(5/5)", -32);
		factoryFromString("(-1/8)^(1/3)", -1, 2);
		factoryFromString("(-1/8)^(2/3)", 1, 4);
		factoryFromString("(-1/8)^(3/3)", -1, 8);
		factoryFromString("(-1/8)^(4/3)", 1, 16);
		factoryFromString("(-1/8)^(5/3)", -1, 32);
		factoryFromString("2^-2", 1, 4);
		factoryFromString("2^-1*2", 1);
		factoryFromString("2^0-2", -1);
		factoryFromString("2^-(1/(2^-2))", 1, 16);
		factoryFromString("2^-(1/-(2^-2))", 16);
		factoryFromString("3^(-1+2)", 3);
		factoryFromString("3^--2", 9);
		factoryFromString("3^--2^3", 6561);
		factoryFromString("-2^2", -4);
		factoryFromString("4^(1/2)", 2);
		factoryFromString("4^(-1/2)", 1, 2);
		factoryFromString("(1/9)^(1/2)", 1, 3);
		factoryFromString("(1/9)^(-1/2)", 3);
		factoryFromString("(4/9)^(1/2)", 2, 3);
		factoryFromString("(4/9)^(-1/2)", 3, 2);
		factoryFromString("4^(3/2)", 8);
		factoryFromString("4^(-3/-2)", 8);
		factoryFromString("4^(-3/2)", 1, 8);
		factoryFromString("4^(3/-2)", 1, 8);
		factoryFromString("(4/9)^(3/2)", 8, 27);
		factoryFromString("(4/9)^(-3/2)", 27, 8);
		factoryFromString("(4/9)^(-30/2)", 205891132094649L, 1073741824);
		factoryFromString("-4^(1/2)", -2);
		factoryFromString("-4^(-1/2)", -1, 2);
		factoryFromString("9^(1/2)", 3);
		factoryFromString("25^(1/2)", 5);
		factoryFromString("100^(1/2)", 10);
		factoryFromString("961^(1/2)", 31);
		factoryFromString("9801^(1/2)", 99);
		factoryFromString("10000^(1/2)", 100);
		factoryFromString("8^(1/3)", 2);
		factoryFromString("27^(1/3)", 3);
		factoryFromString("1000^(1/3)", 10);
		factoryFromString("8000^(1/3)", 20);
		factoryFromString("4.0^(0.50000)", 2);
		factoryFromString("--4^(-.5)", 1, 2);
		factoryFromString("(-2)^2", 4);
		factoryFromString("-(2)^2", -4);
		factoryFromString("-(2+1)^2", -9);
		factoryFromString("-2^-2", -1, 4);
		factoryFromString("2^-2^2", 1, 16);
		factoryFromString("-2^-2^2", -1, 16);
		factoryFromString("-2^-(2)^2", -1, 16);
		factoryFromString("-2^(-2)^2", -16);
		factoryFromString("-4^-2^-1", -1, 2);
		factoryFromString("-2^-2(4^-2^-1)^2", -1, 16);
		factoryFromString("-2.0^-2.0(4.0^-2.0^-1.0)^(2.2-.2)", -1, 16);
	}

	private void factoryFromLong(long val, long... expectedResult)
	{
		factory(rat(val), expectedResult[0], expectedResult.length == 2 ? expectedResult[1] : 1);
	}

	private void factoryFromLongs(long numerator, long denominator, long... expectedResult)
	{
		factory(rat(numerator, denominator), expectedResult[0], expectedResult.length == 2 ? expectedResult[1] : 1);
	}

	private void factoryFromBigInteger(BigInteger val, long... expectedResult)
	{
		factory(rat(val), expectedResult[0], expectedResult.length == 2 ? expectedResult[1] : 1);
	}

	private void factoryFromBigIntegers(BigInteger numerator, BigInteger denominator, long... expectedResult)
	{
		factory(rat(numerator, denominator), expectedResult[0], expectedResult.length == 2 ? expectedResult[1] : 1);
	}

	private void factoryFromBigDecimal(BigDecimal val, long... expectedResult)
	{
		factory(rat(val), expectedResult[0], expectedResult.length == 2 ? expectedResult[1] : 1);
	}

	private void factoryFromDouble(double val, long... expectedResult)
	{
		factory(rat(val), expectedResult[0], expectedResult.length == 2 ? expectedResult[1] : 1);
	}

	private void factoryFromString(String expression, long... expectedResult)
	{
		factory(rat(expression), expectedResult[0], expectedResult.length == 2 ? expectedResult[1] : 1);
	}

	private void factory(Rat result, long expectedNumerator, long expectedDenominator)
	{
		Rat expectedResult = rat(expectedNumerator, expectedDenominator);
		assertEquals(expectedResult, result);
	}

	@Test
	public void pow()
	{
		pow(2, 1, 3, 1, 8, 1);
		pow(-8, 27, -2, 3, 9, 4);
	}

	private void pow(long baseNumerator, long baseDenominator, long exponentNumerator, long exponentDenominator, long expectedResultNumerator, long expectedResultDenominator)
	{
		Rat result = rat(baseNumerator, baseDenominator).pow(rat(exponentNumerator, exponentDenominator));
		assertEquals(expectedResultNumerator, result.numerator().longValue());
		assertEquals(expectedResultDenominator, result.denominator().longValue());
	}

	@Test
	public void nthRoot()
	{
		nthRoot(0, 1);
		nthRoot(0, 2);
		nthRoot(0, 3);
		nthRoot(1, 1);
		nthRoot(1, 2);
		nthRoot(1, 3);
		nthRoot(27, 1);
		nthRoot(27, 2);
		nthRoot(27, 3);
		assertEquals(rat(-27), rat(-27).nthRoot(1));
		assertEquals(rat(-3), rat(-27).nthRoot(3));
	}

	private void nthRoot(int val, int n)
	{
		double expectedResult = Math.pow(val, 1.0 / n);
		double actualResult = rat(val).nthRoot(n).toDouble();
		assertEquals(expectedResult, actualResult, DOUBLE_COMPARISON_TOLERANCE);
	}

	@Test
	public void sqrt()
	{
		sqrt(0, 0);
		sqrt(1, 1);
		sqrt(4, 2);
		sqrt(1234567890L * 1234567890L, 1234567890);
		sqrt(BigDecimal.valueOf(1234.5678).multiply(BigDecimal.valueOf(1234.5678)), BigDecimal.valueOf(1234.5678));
		sqrt(10);
		sqrt(123456890123456890.12345678901234567890);
		sqrt(4, 9, 2, 3);
	}

	private void sqrt(long n, long expectedSquareRoot)
	{
		assertEquals(rat(expectedSquareRoot), rat(n).sqrt());
	}

	private void sqrt(BigDecimal n, BigDecimal expectedSquareRoot)
	{
		assertEquals(rat(expectedSquareRoot), rat(n).sqrt());
	}

	private void sqrt(double n)
	{
		assertEquals(Math.sqrt(n), rat(n).sqrt().toDouble(), DOUBLE_COMPARISON_TOLERANCE);
	}

	private void sqrt(long numerator, long denominator, long expectedResultNumerator, long expectedResultDenominator)
	{
		Rat result = rat(numerator, denominator).sqrt();
		assertEquals(expectedResultNumerator, result.numerator().longValue());
		assertEquals(expectedResultDenominator, result.denominator().longValue());
	}

	@Test
	public void exp()
	{
		exp(0);
		exp(1);
		exp(10);
	}

	private void exp(int val)
	{
		double expectedResult = Math.exp(val);
		double actualResult = rat(val).exp().toDouble();
		assertEquals(expectedResult, actualResult, DOUBLE_COMPARISON_TOLERANCE);
	}

	@Test
	public void log()
	{
		log(789, 27846, 1, 1, 0);
		log(34875, 3345, 34875, 3345, 1);
		log(10, 1, 1, 100, -2);
		log(10, 1, 1, 10, -1);
		log(10, 1, 1, 1, 0);
		log(10, 1, 10, 1, 1);
		log(10, 1, 100, 1, 2);
		log(1, 100, 10, 1, -1, 2);
		log(1, 10, 10, 1, -1);
		log(100, 1, 10, 1, 1, 2);
		log(3, 1, 9, 1, 2);
		log(9, 1, 3, 1, 1, 2);
		log(3, 1, 1, 9, -2);
		log(1, 9, 3, 1, -1, 2);
		log(1, 3, 9, 1, -2);
		log(9, 1, 1, 3, -1, 2);
		log(1, 3, 1, 9, 2);
		log(1, 9, 1, 3, 1, 2);
		log(3, 1, 27, 1, 3);
		log(27, 1, 3, 1, 1, 3);
		log(3, 1, 1, 27, -3);
		log(1, 27, 3, 1, -1, 3);
		log(1, 3, 27, 1, -3);
		log(27, 1, 1, 3, -1, 3);
		log(1, 3, 1, 27, 3);
		log(1, 27, 1, 3, 1, 3);
		log(2, 1, 8, 1, 3);
		log(8, 1, 2, 1, 1, 3);
		log(2, 1, 1, 8, -3);
		log(1, 8, 2, 1, -1, 3);
		log(1, 2, 8, 1, -3);
		log(8, 1, 1, 2, -1, 3);
		log(1, 2, 1, 8, 3);
		log(1, 8, 1, 2, 1, 3);
		log(9, 1, 27, 1, 3, 2);
		log(27, 1, 9, 1, 2, 3);
		log(9, 1, 1, 27, -3, 2);
		log(1, 27, 9, 1, -2, 3);
		log(1, 9, 27, 1, -3, 2);
		log(27, 1, 1, 9, -2, 3);
		log(1, 9, 1, 27, 3, 2);
		log(1, 27, 1, 9, 2, 3);
		log(4, 9, 8, 27, 3, 2);
		log(8, 27, 4, 9, 2, 3);
		log(4, 9, 27, 8, -3, 2);
		log(27, 8, 4, 9, -2, 3);
		log(9, 4, 8, 27, -3, 2);
		log(8, 27, 9, 4, -2, 3);
		log(9, 4, 27, 8, 3, 2);
		log(27, 8, 9, 4, 2, 3);
		log(6, 1, 36, 1, 2);
		log(36, 1, 6, 1, 1, 2);
		log(6, 1, 1, 36, -2);
		log(1, 36, 6, 1, -1, 2);
		log(1, 6, 36, 1, -2);
		log(36, 1, 1, 6, -1, 2);
		log(1, 6, 1, 36, 2);
		log(1, 36, 1, 6, 1, 2);
		log(6, 1, 1296, 1, 4);
		log(1296, 1, 6, 1, 1, 4);
		log(6, 1, 1, 1296, -4);
		log(1, 1296, 6, 1, -1, 4);
		log(1, 6, 1296, 1, -4);
		log(1296, 1, 1, 6, -1, 4);
		log(1, 6, 1, 1296, 4);
		log(1, 1296, 1, 6, 1, 4);
		log(36, 1, 1296, 1, 2);
		log(1296, 1, 36, 1, 1, 2);
		log(36, 1, 1, 1296, -2);
		log(1, 1296, 36, 1, -1, 2);
		log(1, 36, 1296, 1, -2);
		log(1296, 1, 1, 36, -1, 2);
		log(1, 36, 1, 1296, 2);
		log(1, 1296, 1, 36, 1, 2);
		log(216, 1, 1296, 1, 4, 3);
		log(1296, 1, 216, 1, 3, 4);
		log(216, 1, 1, 1296, -4, 3);
		log(1, 1296, 216, 1, -3, 4);
		log(1, 216, 1296, 1, -4, 3);
		log(1296, 1, 1, 216, -3, 4);
		log(1, 216, 1, 1296, 4, 3);
		log(1, 1296, 1, 216, 3, 4);
		log(12 * 12, 1, 12 * 12 * 12, 1, 3, 2);
		log(1, 12 * 12, 12 * 12 * 12, 1, -3, 2);
		log(12 * 12, 1, 1, 12 * 12 * 12, -3, 2);
		log(1, 12 * 12, 1, 12 * 12 * 12, 3, 2);
		log(12 * 12 * 12, 1, 12 * 12, 1, 2, 3);
		log(12 * 12 * 12, 1, 1, 12 * 12, -2, 3);
		log(1, 12 * 12 * 12, 12 * 12, 1, -2, 3);
		log(1, 12 * 12 * 12, 1, 12 * 12, 2, 3);
		log(12 * 12 * 12, 1, 12 * 12 * 12 * 12, 1, 4, 3);
		log(72, 1, 72 * 72, 1, 2);
		log(72 * 72, 1, 72 * 72 * 72, 1, 3, 2);
		log(72 * 72 * 72, 1, 72 * 72 * 72 * 72, 1, 4, 3);
		log(1, 72 * 72 * 72, 72 * 72 * 72 * 72, 1, -4, 3);
		log(72 * 72 * 72, 1, 1, 72 * 72 * 72 * 72, -4, 3);
		log(1, 72 * 72 * 72, 1, 72 * 72 * 72 * 72, 4, 3);
		log(72 * 72 * 72 * 72, 1, 72 * 72 * 72, 1, 3, 4);
		log(72 * 72 * 72 * 72, 1, 1, 72 * 72 * 72, -3, 4);
		log(1, 72 * 72 * 72 * 72, 72 * 72 * 72, 1, -3, 4);
		log(1, 72 * 72 * 72 * 72, 1, 72 * 72 * 72, 3, 4);

		log(72 * 72 * 72, 5 * 5 * 5, 72 * 72 * 72 * 72, 5 * 5 * 5 * 5, 4, 3);
		log(5 * 5 * 5, 72 * 72 * 72, 72 * 72 * 72 * 72, 5 * 5 * 5 * 5, -4, 3);
		log(72 * 72 * 72, 5 * 5 * 5, 5 * 5 * 5 * 5, 72 * 72 * 72 * 72, -4, 3);
		log(5 * 5 * 5, 72 * 72 * 72, 5 * 5 * 5 * 5, 72 * 72 * 72 * 72, 4, 3);
		log(72 * 72 * 72 * 72, 5 * 5 * 5 * 5, 72 * 72 * 72, 5 * 5 * 5, 3, 4);
		log(72 * 72 * 72 * 72, 5 * 5 * 5 * 5, 5 * 5 * 5, 72 * 72 * 72, -3, 4);
		log(5 * 5 * 5 * 5, 72 * 72 * 72 * 72, 72 * 72 * 72, 5 * 5 * 5, -3, 4);
		log(5 * 5 * 5 * 5, 72 * 72 * 72 * 72, 5 * 5 * 5, 72 * 72 * 72, 3, 4);
		log(BigInteger.valueOf(72).pow(4), BigInteger.ONE, BigInteger.valueOf(72).pow(6), BigInteger.ONE, BigInteger.valueOf(3), BigInteger.valueOf(2));
		assertEquals(rat(0), rat(1).ln());
		assertEquals(rat(1), Rat.E.ln());
	}

	private void log(long baseNumerator, long baseDenominator, long valNumerator, long valDenominator, long... expectedResult)
	{
		BigInteger[] expectedResultBi = new BigInteger[expectedResult.length];
		for (int i = 0; i < expectedResult.length; i++)
			expectedResultBi[i] = BigInteger.valueOf(expectedResult[i]);
		log(BigInteger.valueOf(baseNumerator), BigInteger.valueOf(baseDenominator), BigInteger.valueOf(valNumerator), BigInteger.valueOf(valDenominator), expectedResultBi);
	}

	private void log(BigInteger baseNumerator, BigInteger baseDenominator, BigInteger valNumerator, BigInteger valDenominator, BigInteger... expectedResult)
	{
		Rat expected = rat(expectedResult[0], expectedResult.length == 2 ? expectedResult[1] : BigInteger.ONE);
		Rat actual = rat(valNumerator, valDenominator).log(rat(baseNumerator, baseDenominator));
		assertEquals(expected, actual);
	}

	@Test
	public void ln()
	{
		assertEquals(rat(0), rat(1).ln());
		assertEquals(rat(1), Rat.E.ln());
		ln(2);
		ln(123456890123456890.12345678901234567890);
	}

	private void ln(double n)
	{
		assertEquals(Math.log(n), rat(n).ln().toDouble(), DOUBLE_COMPARISON_TOLERANCE);
	}

	@Test
	public void utilities()
	{
		Rat neg = rat(-1);
		Rat zero = rat(0);
		Rat pos = rat(1);
		Rat negFraction = rat(-5, 2);
		Rat posFraction = rat(5, 2);

		assertFalse(neg.isPositive());
		assertTrue(neg.isNegative());
		assertFalse(neg.isZero());
		assertEquals(pos, neg.negate());
		assertEquals(pos, neg.abs());

		assertFalse(zero.isPositive());
		assertFalse(zero.isNegative());
		assertTrue(zero.isZero());
		assertEquals(zero, zero.negate());
		assertEquals(zero, zero.abs());

		assertTrue(pos.isPositive());
		assertFalse(pos.isNegative());
		assertFalse(pos.isZero());
		assertEquals(neg, pos.negate());
		assertEquals(pos, pos.abs());

		assertFalse(negFraction.isPositive());
		assertTrue(negFraction.isNegative());
		assertFalse(negFraction.isZero());
		assertEquals(posFraction, negFraction.negate());
		assertEquals(posFraction, negFraction.abs());

		assertTrue(posFraction.isPositive());
		assertFalse(posFraction.isNegative());
		assertFalse(posFraction.isZero());
		assertEquals(negFraction, posFraction.negate());
		assertEquals(posFraction, posFraction.abs());

		assertTrue(rat(-1).isInteger());
		assertTrue(rat(0).isInteger());
		assertTrue(rat(1).isInteger());
		assertTrue(rat(-1, 1).isInteger());
		assertTrue(rat(0, 1).isInteger());
		assertTrue(rat(1, 1).isInteger());
		assertTrue(rat(-2, 2).isInteger());
		assertTrue(rat(0, 2).isInteger());
		assertTrue(rat(2, 2).isInteger());

		assertFalse(rat(-1, 2).isInteger());
		assertFalse(rat(1, 2).isInteger());
		assertFalse(rat(-2, 3).isInteger());
		assertFalse(rat(2, 3).isInteger());
		assertFalse(rat(-8, 12).isInteger());
		assertFalse(rat(8, 12).isInteger());

		assertEquals(rat(-1).invert(), rat(-1));
		assertEquals(rat(1).invert(), rat(1));
		assertEquals(rat(-2).invert(), rat(-1, 2));
		assertEquals(rat(2).invert(), rat(1, 2));
		assertEquals(rat(-1, 2).invert(), rat(-2));
		assertEquals(rat(1, 2).invert(), rat(2));
		assertEquals(rat(-2, 3).invert(), rat(-3, 2));
		assertEquals(rat(2, 3).invert(), rat(3, 2));
		assertEquals(rat(-3, 2).invert(), rat(-2, 3));
		assertEquals(rat(3, 2).invert(), rat(2, 3));
	}

	@Test
	public void comparison()
	{
		Rat a = rat(0);
		Rat b = rat(1);
		Rat c = rat(-1);
		Rat d = rat(1, 2);
		Rat e = rat(-1, 2);
		Rat f = rat("0.5");
		Rat g = rat("-0.5");
		Rat h = rat(2, 5);
		Rat i = rat(-2, 5);

		assertEquals(true, a.equals(a));
		assertEquals(true, d.equals(d));
		assertEquals(true, f.equals(f));
		assertEquals(true, d.equals(f));
		assertEquals(true, f.equals(d));
		assertEquals(false, b.equals(c));
		assertEquals(false, c.equals(b));
		assertEquals(false, e.equals(f));
		assertEquals(false, f.equals(e));

		assertEquals(true, a.equal(a));
		assertEquals(true, d.equal(d));
		assertEquals(true, f.equal(f));
		assertEquals(true, d.equal(f));
		assertEquals(true, f.equal(d));
		assertEquals(false, b.equal(c));
		assertEquals(false, c.equal(b));
		assertEquals(false, e.equal(f));
		assertEquals(false, f.equal(e));

		assertEquals(false, a.notEqual(a));
		assertEquals(false, d.notEqual(d));
		assertEquals(false, f.notEqual(f));
		assertEquals(false, d.notEqual(f));
		assertEquals(false, f.notEqual(d));
		assertEquals(true, b.notEqual(c));
		assertEquals(true, c.notEqual(b));
		assertEquals(true, e.notEqual(f));
		assertEquals(true, f.notEqual(e));

		assertEquals(false, a.greater(a));
		assertEquals(false, d.greater(d));
		assertEquals(false, a.greater(b));
		assertEquals(true, a.greater(c));
		assertEquals(true, b.greater(a));
		assertEquals(false, c.greater(a));
		assertEquals(true, b.greater(c));
		assertEquals(false, c.greater(b));
		assertEquals(false, d.greater(f));
		assertEquals(false, f.greater(d));
		assertEquals(false, e.greater(g));
		assertEquals(false, g.greater(e));
		assertEquals(true, d.greater(e));
		assertEquals(false, e.greater(d));
		assertEquals(true, d.greater(h));
		assertEquals(false, h.greater(d));
		assertEquals(false, e.greater(i));
		assertEquals(true, i.greater(e));

		assertEquals(true, a.lessOrEqual(a));
		assertEquals(true, d.lessOrEqual(d));
		assertEquals(true, a.lessOrEqual(b));
		assertEquals(false, a.lessOrEqual(c));
		assertEquals(false, b.lessOrEqual(a));
		assertEquals(true, c.lessOrEqual(a));
		assertEquals(false, b.lessOrEqual(c));
		assertEquals(true, c.lessOrEqual(b));
		assertEquals(true, d.lessOrEqual(f));
		assertEquals(true, f.lessOrEqual(d));
		assertEquals(true, e.lessOrEqual(g));
		assertEquals(true, g.lessOrEqual(e));
		assertEquals(false, d.lessOrEqual(e));
		assertEquals(true, e.lessOrEqual(d));
		assertEquals(false, d.lessOrEqual(h));
		assertEquals(true, h.lessOrEqual(d));
		assertEquals(true, e.lessOrEqual(i));
		assertEquals(false, i.lessOrEqual(e));

		assertEquals(false, a.less(a));
		assertEquals(false, d.less(d));
		assertEquals(true, a.less(b));
		assertEquals(false, a.less(c));
		assertEquals(false, b.less(a));
		assertEquals(true, c.less(a));
		assertEquals(false, b.less(c));
		assertEquals(true, c.less(b));
		assertEquals(false, d.less(f));
		assertEquals(false, f.less(d));
		assertEquals(false, e.less(g));
		assertEquals(false, g.less(e));
		assertEquals(false, d.less(e));
		assertEquals(true, e.less(d));
		assertEquals(false, d.less(h));
		assertEquals(true, h.less(d));
		assertEquals(true, e.less(i));
		assertEquals(false, i.less(e));

		assertEquals(true, a.greaterOrEqual(a));
		assertEquals(true, d.greaterOrEqual(d));
		assertEquals(false, a.greaterOrEqual(b));
		assertEquals(true, a.greaterOrEqual(c));
		assertEquals(true, b.greaterOrEqual(a));
		assertEquals(false, c.greaterOrEqual(a));
		assertEquals(true, b.greaterOrEqual(c));
		assertEquals(false, c.greaterOrEqual(b));
		assertEquals(true, d.greaterOrEqual(f));
		assertEquals(true, f.greaterOrEqual(d));
		assertEquals(true, e.greaterOrEqual(g));
		assertEquals(true, g.greaterOrEqual(e));
		assertEquals(true, d.greaterOrEqual(e));
		assertEquals(false, e.greaterOrEqual(d));
		assertEquals(true, d.greaterOrEqual(h));
		assertEquals(false, h.greaterOrEqual(d));
		assertEquals(false, e.greaterOrEqual(i));
		assertEquals(true, i.greaterOrEqual(e));

		assertEquals(-1, a.compareTo(b));
		assertEquals(1, b.compareTo(a));
		assertEquals(1, a.compareTo(c));
		assertEquals(-1, c.compareTo(a));
		assertEquals(-1, a.compareTo(d));
		assertEquals(1, d.compareTo(a));
		assertEquals(-1, a.compareTo(f));
		assertEquals(1, f.compareTo(a));
		assertEquals(1, b.compareTo(c));
		assertEquals(-1, c.compareTo(b));
		assertEquals(1, b.compareTo(d));
		assertEquals(-1, d.compareTo(b));
		assertEquals(0, d.compareTo(f));
		assertEquals(0, f.compareTo(d));
		assertEquals(1, d.compareTo(g));
		assertEquals(-1, g.compareTo(d));
		assertEquals(-1, e.compareTo(f));
		assertEquals(1, f.compareTo(e));
		assertEquals(0, e.compareTo(g));
		assertEquals(0, g.compareTo(e));
		List<Rat> sortedRats = Arrays.asList(a, b, c, d, e, f, g, h, i);
		sortedRats.sort(null);
		assertEquals(Arrays.asList(c, e, g, i, a, h, d, f, b), sortedRats);
	}

	@Test
	public void reducePrecision()
	{
		reducePrecision(0, 1, 1, 0, 1);
		reducePrecision(1234, 2345, 3, 123, 235);
		reducePrecision(1234, 2345, 4, 1234, 2345);
		reducePrecision(1234, 2345, 5, 1234, 2345);
		reducePrecision(123, 4567, 2, 12, 457);
		reducePrecision(123, 4561, 2, 1, 38);
		reducePrecision(123, 4567, 3, 123, 4567);
		reducePrecision(123, 4567, 4, 123, 4567);
		reducePrecision(-1234, 2345, 3, -123, 235);
		reducePrecision(-1234, 2345, 4, -1234, 2345);
		reducePrecision(-1234, 2345, 5, -1234, 2345);
		reducePrecision(-123, 4567, 2, -12, 457);
		reducePrecision(-123, 4561, 2, -1, 38);
		reducePrecision(-123, 4567, 3, -123, 4567);
		reducePrecision(-123, 4567, 4, -123, 4567);
	}

	private void reducePrecision(long numerator, long denominator, int maxDigits, long expectedNumerator, long expectedDenominator)
	{
		Rat result = rat(numerator, denominator).reducePrecision(maxDigits);
		assertEquals(expectedNumerator, result.numerator().longValue());
		assertEquals(expectedDenominator, result.denominator().longValue());
	}
}