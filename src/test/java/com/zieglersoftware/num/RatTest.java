package com.zieglersoftware.num;

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
		assertEquals(Math.PI, Rat.PI.toDouble(), 0);
		assertEquals(Math.E, Rat.E.toDouble(), 0);
	}

	@Test
	public void of()
	{
		ofLong(0, 0);
		ofLong(1, 1);
		ofLong(-1, -1);
		ofLong(100, 100);
		ofLong(-100, -100);
		ofLong(999, 999);
		ofLong(-999, -999);
		ofLong(1000, 1000);
		ofLong(-1000, -1000);
		ofLong(1001, 1001);
		ofLong(-1001, -1001);
		ofLong(1000000000000000L, 1000000000000000L);
		ofLong(-1000000000000000L, -1000000000000000L);

		ofBigInteger(BigInteger.valueOf(0), 0, 1);
		ofBigInteger(BigInteger.valueOf(1), 1, 1);
		ofBigInteger(BigInteger.valueOf(-1), -1, 1);
		ofBigInteger(BigInteger.valueOf(100), 100, 1);
		ofBigInteger(BigInteger.valueOf(-100), -100, 1);
		ofBigInteger(new BigInteger("1000000000000000"), 1000000000000000L, 1);
		ofBigInteger(new BigInteger("-1000000000000000"), -1000000000000000L, 1);

		ofLongs(0, 1, 0);
		ofLongs(0, -1, 0);
		ofLongs(-0, -1, 0);
		ofLongs(0, 10, 0);
		ofLongs(-0, 10, 0);
		ofLongs(1, 1, 1);
		ofLongs(-1, 1, -1);
		ofLongs(1, -1, -1);
		ofLongs(-1, -1, 1);
		ofLongs(1, 2, 1, 2);
		ofLongs(-1, 2, -1, 2);
		ofLongs(2, 4, 1, 2);
		ofLongs(-2, 4, -1, 2);
		ofLongs(30, 40, 3, 4);
		ofLongs(30, -40, -3, 4);
		ofLongs(1, 3, 1, 3);
		ofLongs(-1, 3, -1, 3);
		ofLongs(2, 3, 2, 3);
		ofLongs(-2, 3, -2, 3);
		ofLongs(3, 3, 1);
		ofLongs(-3, 3, -1);
		ofLongs(5, 3, 5, 3);
		ofLongs(-5, 3, -5, 3);
		ofLongs(10, 6, 5, 3);
		ofLongs(-10, 6, -5, 3);
		ofLongs(9, 27, 1, 3);
		ofLongs(-9, 27, -1, 3);
		ofLongs(1, 10, 1, 10);
		ofLongs(1, 100, 1, 100);
		ofLongs(1, 1000, 1, 1000);
		ofLongs(1, 10000, 1, 10000);
		ofLongs(1, 100000, 1, 100000);

		ofBigIntegers(BigInteger.valueOf(0), BigInteger.valueOf(1), 0);
		ofBigIntegers(BigInteger.valueOf(0), BigInteger.valueOf(-1), 0);
		ofBigIntegers(BigInteger.valueOf(-0), BigInteger.valueOf(-1), 0);
		ofBigIntegers(BigInteger.valueOf(0), BigInteger.valueOf(10), 0);
		ofBigIntegers(BigInteger.valueOf(-0), BigInteger.valueOf(10), 0);
		ofBigIntegers(BigInteger.valueOf(1), BigInteger.valueOf(1), 1);
		ofBigIntegers(BigInteger.valueOf(-1), BigInteger.valueOf(1), -1);
		ofBigIntegers(BigInteger.valueOf(1), BigInteger.valueOf(-1), -1);
		ofBigIntegers(BigInteger.valueOf(-1), BigInteger.valueOf(-1), 1);
		ofBigIntegers(BigInteger.valueOf(1), BigInteger.valueOf(2), 1, 2);
		ofBigIntegers(BigInteger.valueOf(-1), BigInteger.valueOf(2), -1, 2);
		ofBigIntegers(BigInteger.valueOf(2), BigInteger.valueOf(4), 1, 2);
		ofBigIntegers(BigInteger.valueOf(-2), BigInteger.valueOf(4), -1, 2);
		ofBigIntegers(BigInteger.valueOf(30), BigInteger.valueOf(40), 3, 4);
		ofBigIntegers(BigInteger.valueOf(30), BigInteger.valueOf(-40), -3, 4);
		ofBigIntegers(new BigInteger("100000000000000000000000000000000000000000"), new BigInteger("100000000000000000000000000000000000000000"), 1);
		ofBigIntegers(new BigInteger("-100000000000000000000000000000000000000000"), new BigInteger("100000000000000000000000000000000000000000"), -1);
		ofBigIntegers(new BigInteger("100000000000000000000000000000000000000000"), new BigInteger("-100000000000000000000000000000000000000000"), -1);
		ofBigIntegers(new BigInteger("-100000000000000000000000000000000000000000"), new BigInteger("-100000000000000000000000000000000000000000"), 1);
		ofBigIntegers(new BigInteger("200000000000000000000000000000000000000000"), new BigInteger("100000000000000000000000000000000000000000"), 2);
		ofBigIntegers(new BigInteger("-200000000000000000000000000000000000000000"), new BigInteger("100000000000000000000000000000000000000000"), -2);
		ofBigIntegers(new BigInteger("200000000000000000000000000000000000000000"), new BigInteger("-100000000000000000000000000000000000000000"), -2);
		ofBigIntegers(new BigInteger("-200000000000000000000000000000000000000000"), new BigInteger("-100000000000000000000000000000000000000000"), 2);
		ofBigIntegers(new BigInteger("100000000000000000000000000000000000000000"), new BigInteger("200000000000000000000000000000000000000000"), 1, 2);
		ofBigIntegers(new BigInteger("-100000000000000000000000000000000000000000"), new BigInteger("200000000000000000000000000000000000000000"), -1, 2);
		ofBigIntegers(new BigInteger("100000000000000000000000000000000000000000"), new BigInteger("-200000000000000000000000000000000000000000"), -1, 2);
		ofBigIntegers(new BigInteger("-100000000000000000000000000000000000000000"), new BigInteger("-200000000000000000000000000000000000000000"), 1, 2);
		ofBigIntegers(BigInteger.valueOf(1), BigInteger.valueOf(10), 1, 10);
		ofBigIntegers(BigInteger.valueOf(1), BigInteger.valueOf(100), 1, 100);
		ofBigIntegers(BigInteger.valueOf(1), BigInteger.valueOf(1000), 1, 1000);
		ofBigIntegers(BigInteger.valueOf(1), BigInteger.valueOf(10000), 1, 10000);
		ofBigIntegers(BigInteger.valueOf(1), BigInteger.valueOf(100000), 1, 100000);

		ofBigDecimal(new BigDecimal(0), 0, 1);
		ofBigDecimal(new BigDecimal("00"), 0, 1);
		ofBigDecimal(new BigDecimal("0"), 0, 1);
		ofBigDecimal(new BigDecimal("0."), 0, 1);
		ofBigDecimal(new BigDecimal("0.0"), 0, 1);
		ofBigDecimal(new BigDecimal("0.00"), 0, 1);
		ofBigDecimal(new BigDecimal(1), 1, 1);
		ofBigDecimal(new BigDecimal(-1), -1, 1);
		ofBigDecimal(new BigDecimal("01"), 1, 1);
		ofBigDecimal(new BigDecimal("1"), 1, 1);
		ofBigDecimal(new BigDecimal("1."), 1, 1);
		ofBigDecimal(new BigDecimal("1.0"), 1, 1);
		ofBigDecimal(new BigDecimal("1.0000"), 1, 1);
		ofBigDecimal(new BigDecimal("-1.0000"), -1, 1);
		ofBigDecimal(new BigDecimal("-0.1"), -1, 10);
		ofBigDecimal(new BigDecimal("2.5000"), 5, 2);
		ofBigDecimal(new BigDecimal("-2.5000"), -5, 2);
		ofBigDecimal(new BigDecimal(".333"), 333, 1000);
		ofBigDecimal(new BigDecimal("-.333"), -333, 1000);
		ofBigDecimal(new BigDecimal(".333333"), 333333, 1000000);
		ofBigDecimal(new BigDecimal("-.333333"), -333333, 1000000);
		ofBigDecimal(new BigDecimal("33.333333"), (33 * 1000000) + 333333, 1000000);
		ofBigDecimal(new BigDecimal("-33.333333"), -(33 * 1000000) - 333333, 1000000);
		ofBigDecimal(new BigDecimal("11111.11111"), (11111 * 100000) + 11111, 100000);
		ofBigDecimal(new BigDecimal("-11111.11111"), -(11111 * 100000) - 11111, 100000);
		ofBigDecimal(new BigDecimal("0.500000000000000000000000000000000000000000000000000"), 1, 2);
		ofBigDecimal(new BigDecimal("-0.500000000000000000000000000000000000000000000000000"), -1, 2);
		ofBigDecimal(new BigDecimal("0.1"), 1, 10);
		ofBigDecimal(new BigDecimal("0.01"), 1, 100);
		ofBigDecimal(new BigDecimal("0.001"), 1, 1000);
		ofBigDecimal(new BigDecimal("0.0001"), 1, 10000);
		ofBigDecimal(new BigDecimal("0.00001"), 1, 100000);

		ofDouble(1D, 1, 1);
		ofDouble(-1D, -1, 1);
		ofDouble(0.1, 1, 10);
		ofDouble(-0.1, -1, 10);
		ofDouble(2.5, 5, 2);
		ofDouble(-2.5, -5, 2);
		ofDouble(.333, 333, 1000);
		ofDouble(-.333, -333, 1000);
		ofDouble(.333333, 333333, 1000000);
		ofDouble(-.333333, -333333, 1000000);
		ofDouble(33.333333, (33 * 1000000) + 333333, 1000000);
		ofDouble(-33.333333, -(33 * 1000000) - 333333, 1000000);
		ofDouble(11111.11111, (11111 * 100000) + 11111, 100000);
		ofDouble(-11111.11111, -(11111 * 100000) - 11111, 100000);
		ofDouble(0.1, 1, 10);
		ofDouble(0.01, 1, 100);
		ofDouble(0.001, 1, 1000);
		ofDouble(0.0001, 1, 10000);
		ofDouble(0.00001, 1, 100000);

		ofString("0", 0);
		ofString("00000000000", 0);
		ofString("1", 1);
		ofString("-1", -1);
		ofString("0001", 1);
		ofString("-001", -1);
		ofString("0001.000000", 1);
		ofString("-001.000000", -1);
		ofString("1.000000000", 1);
		ofString("-1.00000000000", -1);
		ofString("0.1", 1, 10);
		ofString("0.01", 1, 100);
		ofString("0.001", 1, 1000);
		ofString("0.0001", 1, 10000);
		ofString("0.00001", 1, 100000);
		ofString("1/10", 1, 10);
		ofString("1/100", 1, 100);
		ofString("1/1000", 1, 1000);
		ofString("1/10000", 1, 10000);
		ofString("1/100000", 1, 100000);
		ofString("-0.1", -1, 10);
		ofString(".1", 1, 10);
		ofString("-.1", -1, 10);
		ofString("-0.01", -1, 100);
		ofString(".01", 1, 100);
		ofString("-.01", -1, 100);
		ofString("1.0", 1, 1);
		ofString("-1.0", -1, 1);
		ofString("1.1", 11, 10);
		ofString("-1.1", -11, 10);
		ofString("1.10", 11, 10);
		ofString("-1.10", -11, 10);
		ofString("01.10", 11, 10);
		ofString("-01.10", -11, 10);
		ofString("1.111", 1111, 1000);
		ofString("-1.111", -1111, 1000);
		ofString(".5", 1, 2);
		ofString("-.5", -1, 2);
		ofString("0.5", 1, 2);
		ofString("-0.5", -1, 2);
		ofString(".75", 3, 4);
		ofString("-.75", -3, 4);
		ofString("1.75", 7, 4);
		ofString("-1.75", -7, 4);
		ofString("0.001", 1, 1000);
		ofString("-0.001", -1, 1000);
		ofString("0.002", 1, 500);
		ofString("-0.002", -1, 500);
		ofString("0.004", 1, 250);
		ofString("-0.004", -1, 250);
		ofString("0.005", 1, 200);
		ofString("-0.005", -1, 200);
		ofString("0.010", 1, 100);
		ofString("-0.010", -1, 100);
		ofString("--1", 1);
		ofString("0+5", 5);
		ofString("1+1", 2);
		ofString("1-1", 0);
		ofString("1+5", 6);
		ofString("-1+5", 4);
		ofString("0+-5", -5);
		ofString("1+-5", -4);
		ofString("-1+-5", -6);
		ofString("0-5", -5);
		ofString("1-5", -4);
		ofString("-1-5", -6);
		ofString("0--5", 5);
		ofString("1--5", 6);
		ofString("-1--5", 4);
		ofString("-3+4+5-10--30+15-51", -10);
		ofString("0*1", 0);
		ofString("2*-3*4", -24);
		ofString("0/1", 0);
		ofString("3/1.5", 2);
		ofString("1.5/3", 1, 2);
		ofString("1.5/3.5", 3, 7);
		ofString("-1.5/3.5", -3, 7);
		ofString("1.5/-3.5", -3, 7);
		ofString("-1.5/-3.5", 3, 7);
		ofString("2/-3/4", 2, -12);
		ofString("-2/-3*4*5/6", 40, 18);
		ofString("100/30", 10, 3);
		ofString("1+2*3", 7);
		ofString("1*2+3", 5);
		ofString("1-2/3+4", 13, 3);
		ofString("1-2+3*4", 11);
		ofString("1*2-3/4", 5, 4);
		ofString("1*2/3+4", 14, 3);
		ofString("1^0", 1);
		ofString("2^0", 1);
		ofString("0^1", 0);
		ofString("0^-1", 0);
		ofString("0^43.1234", 0);
		ofString("0.000^-43.1234", 0);
		ofString("1^1", 1);
		ofString("1^2", 1);
		ofString("1^-1", 1);
		ofString("-1^1", -1);
		ofString("-1^-1", -1);
		ofString("3^2", 9);
		ofString("2^3", 8);
		ofString("-3^2", -9);
		ofString("-2^3", -8);
		ofString("3^-2", 1, 9);
		ofString("2^-3", 1, 8);
		ofString("-3^-2", -1, 9);
		ofString("-2^-3", -1, 8);
		ofString("4^3^2", 262144);
		ofString("2^2^3-2^-2/3^2+4+5^2-3*2^2*5", 8099, 36);
		ofString("(0+5)", 5);
		ofString("(2)-(1)", 1);
		ofString("(1+-1)+2^2", 4);
		ofString("2(3*4)", 24);
		ofString("(1)(2)3(4)5(((6)7)8)", 40320);
		ofString("1+(1)(2)3(4)5(((6)7)8)+1", 40322);
		ofString("(3*4)2", 24);
		ofString("(1+2)(3*4)", 36);
		ofString("(1+2)-(3*4)", -9);
		ofString("(1+2)5(3*4)", 180);
		ofString("(1+2)-5(3*4)", -57);
		ofString("(1+2)(1+2)(1+2)", 27);
		ofString("(2*-3*4)", -24);
		ofString("(3^2)", 9);
		ofString("(-3)^2", 9);
		ofString("2^2^3-(2^2)^3*(1+2)/(3/(4))", 0);
		ofString("(-1)", -1);
		ofString(".0-(1)", -1);
		ofString("-1*(1)", -1);
		ofString("-(1)", -1);
		ofString("-(-(1))", 1);
		ofString("-(-(-(1)))", -1);
		ofString("-(1+2)+3", 0);
		ofString("-(-(1+2)+3)+4", 4);
		ofString("-(-(-(1+2)+3)+4)+5", 1);
		ofString("0-(1+2)", -3);
		ofString("-1*(1+2)", -3);
		ofString("-(1+2)", -3);
		ofString("2^(0-(1))", 1, 2);
		ofString("2^(-1*(1))", 1, 2);
		ofString("2^(-(1))", 1, 2);
		ofString("2^0-(2)", -1);
		ofString("2^(.0-(2))", 1, 4);
		ofString("2^(-1*(2))", 1, 4);
		ofString("2^-(2)", 1, 4);
		ofString("(-1)^1", -1);
		ofString("(-1)^3", -1);
		ofString("(-1)^(1/3)", -1);
		ofString("(-1)^(2/3)", 1);
		ofString("(-1)^(3/3)", -1);
		ofString("(-1)^(4/3)", 1);
		ofString("(-1)^(5/3)", -1);
		ofString("(-32)^(1/5)", -2);
		ofString("(-32)^(2/5)", 4);
		ofString("(-32)^(3/5)", -8);
		ofString("(-32)^(4/5)", 16);
		ofString("(-32)^(5/5)", -32);
		ofString("(-1/8)^(1/3)", -1, 2);
		ofString("(-1/8)^(2/3)", 1, 4);
		ofString("(-1/8)^(3/3)", -1, 8);
		ofString("(-1/8)^(4/3)", 1, 16);
		ofString("(-1/8)^(5/3)", -1, 32);
		ofString("2^-2", 1, 4);
		ofString("2^-1*2", 1);
		ofString("2^0-2", -1);
		ofString("2^-(1/(2^-2))", 1, 16);
		ofString("2^-(1/-(2^-2))", 16);
		ofString("3^(-1+2)", 3);
		ofString("3^--2", 9);
		ofString("3^--2^3", 6561);
		ofString("-2^2", -4);
		ofString("4^(1/2)", 2);
		ofString("4^(-1/2)", 1, 2);
		ofString("(1/9)^(1/2)", 1, 3);
		ofString("(1/9)^(-1/2)", 3);
		ofString("(4/9)^(1/2)", 2, 3);
		ofString("(4/9)^(-1/2)", 3, 2);
		ofString("4^(3/2)", 8);
		ofString("4^(-3/-2)", 8);
		ofString("4^(-3/2)", 1, 8);
		ofString("4^(3/-2)", 1, 8);
		ofString("(4/9)^(3/2)", 8, 27);
		ofString("(4/9)^(-3/2)", 27, 8);
		ofString("(4/9)^(-30/2)", 205891132094649L, 1073741824);
		ofString("-4^(1/2)", -2);
		ofString("-4^(-1/2)", -1, 2);
		ofString("9^(1/2)", 3);
		ofString("25^(1/2)", 5);
		ofString("100^(1/2)", 10);
		ofString("961^(1/2)", 31);
		ofString("9801^(1/2)", 99);
		ofString("10000^(1/2)", 100);
		ofString("8^(1/3)", 2);
		ofString("27^(1/3)", 3);
		ofString("1000^(1/3)", 10);
		ofString("8000^(1/3)", 20);
		ofString("4.0^(0.50000)", 2);
		ofString("--4^(-.5)", 1, 2);
		ofString("(-2)^2", 4);
		ofString("-(2)^2", -4);
		ofString("-(2+1)^2", -9);
		ofString("-2^-2", -1, 4);
		ofString("2^-2^2", 1, 16);
		ofString("-2^-2^2", -1, 16);
		ofString("-2^-(2)^2", -1, 16);
		ofString("-2^(-2)^2", -16);
		ofString("-4^-2^-1", -1, 2);
		ofString("-2^-2(4^-2^-1)^2", -1, 16);
		ofString("-2.0^-2.0(4.0^-2.0^-1.0)^(2.2-.2)", -1, 16);
	}

	private void ofLong(long val, long... expectedResult)
	{
		of(Rat.of(val), expectedResult[0], expectedResult.length == 2 ? expectedResult[1] : 1);
	}

	private void ofLongs(long numerator, long denominator, long... expectedResult)
	{
		of(Rat.of(numerator, denominator), expectedResult[0], expectedResult.length == 2 ? expectedResult[1] : 1);
	}

	private void ofBigInteger(BigInteger val, long... expectedResult)
	{
		of(Rat.of(val), expectedResult[0], expectedResult.length == 2 ? expectedResult[1] : 1);
	}

	private void ofBigIntegers(BigInteger numerator, BigInteger denominator, long... expectedResult)
	{
		of(Rat.of(numerator, denominator), expectedResult[0], expectedResult.length == 2 ? expectedResult[1] : 1);
	}

	private void ofBigDecimal(BigDecimal val, long... expectedResult)
	{
		of(Rat.of(val), expectedResult[0], expectedResult.length == 2 ? expectedResult[1] : 1);
	}

	private void ofDouble(double val, long... expectedResult)
	{
		of(Rat.of(val), expectedResult[0], expectedResult.length == 2 ? expectedResult[1] : 1);
	}

	private void ofString(String expression, long... expectedResult)
	{
		of(Rat.of(expression), expectedResult[0], expectedResult.length == 2 ? expectedResult[1] : 1);
	}

	private void of(Rat result, long expectedNumerator, long expectedDenominator)
	{
		Rat expectedResult = Rat.of(expectedNumerator, expectedDenominator);
		assertEquals(expectedResult, result);
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
		assertEquals(Rat.of(-27), Rat.of(-27).nthRoot(Rat.of(1)));
		assertEquals(Rat.of(-3), Rat.of(-27).nthRoot(Rat.of(3)));
	}

	private void nthRoot(int val, int n)
	{
		double expectedResult = Math.pow(val, 1.0 / n);
		double actualResult = Rat.of(val).nthRoot(Rat.of(n)).toDouble();
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
	}

	private void sqrt(long n, long expectedSquareRoot)
	{
		assertEquals(Rat.of(expectedSquareRoot), Rat.of(n).sqrt());
	}

	private void sqrt(BigDecimal n, BigDecimal expectedSquareRoot)
	{
		assertEquals(Rat.of(expectedSquareRoot), Rat.of(n).sqrt());
	}

	private void sqrt(double n)
	{
		assertEquals(Math.sqrt(n), Rat.of(n).sqrt().toDouble(), DOUBLE_COMPARISON_TOLERANCE);
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
		double actualResult = Rat.of(val).exp().toDouble();
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
		assertEquals(Rat.of(0), Rat.of(1).ln());
		assertEquals(Rat.of(1), Rat.E.ln());
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
		Rat expectedRat = Rat.of(expectedResult[0], expectedResult.length == 2 ? expectedResult[1] : BigInteger.ONE);
		Rat actualRat = Rat.of(valNumerator, valDenominator).log(Rat.of(baseNumerator, baseDenominator));
		assertEquals(expectedRat, actualRat);
	}

	@Test
	public void ln()
	{
		assertEquals(Rat.of(0), Rat.of(1).ln());
		assertEquals(Rat.of(1), Rat.E.ln());
		ln(2);
		ln(123456890123456890.12345678901234567890);
	}

	private void ln(double n)
	{
		assertEquals(Math.log(n), Rat.of(n).ln().toDouble(), DOUBLE_COMPARISON_TOLERANCE);
	}

	@Test
	public void utilities()
	{
		Rat neg = Rat.of(-1);
		Rat zero = Rat.of(0);
		Rat pos = Rat.of(1);
		Rat negFraction = Rat.of(-5, 2);
		Rat posFraction = Rat.of(5, 2);

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

		assertTrue(Rat.of(-1).isInteger());
		assertTrue(Rat.of(0).isInteger());
		assertTrue(Rat.of(1).isInteger());
		assertTrue(Rat.of(-1, 1).isInteger());
		assertTrue(Rat.of(0, 1).isInteger());
		assertTrue(Rat.of(1, 1).isInteger());
		assertTrue(Rat.of(-2, 2).isInteger());
		assertTrue(Rat.of(0, 2).isInteger());
		assertTrue(Rat.of(2, 2).isInteger());

		assertFalse(Rat.of(-1, 2).isInteger());
		assertFalse(Rat.of(1, 2).isInteger());
		assertFalse(Rat.of(-2, 3).isInteger());
		assertFalse(Rat.of(2, 3).isInteger());
		assertFalse(Rat.of(-8, 12).isInteger());
		assertFalse(Rat.of(8, 12).isInteger());

		assertEquals(Rat.of(-1).invert(), Rat.of(-1));
		assertEquals(Rat.of(1).invert(), Rat.of(1));
		assertEquals(Rat.of(-2).invert(), Rat.of(-1, 2));
		assertEquals(Rat.of(2).invert(), Rat.of(1, 2));
		assertEquals(Rat.of(-1, 2).invert(), Rat.of(-2));
		assertEquals(Rat.of(1, 2).invert(), Rat.of(2));
		assertEquals(Rat.of(-2, 3).invert(), Rat.of(-3, 2));
		assertEquals(Rat.of(2, 3).invert(), Rat.of(3, 2));
		assertEquals(Rat.of(-3, 2).invert(), Rat.of(-2, 3));
		assertEquals(Rat.of(3, 2).invert(), Rat.of(2, 3));
	}

	@Test
	public void comparison()
	{
		Rat a = Rat.of(0);
		Rat b = Rat.of(1);
		Rat c = Rat.of(-1);
		Rat d = Rat.of(1, 2);
		Rat e = Rat.of(-1, 2);
		Rat f = Rat.of("0.5");
		Rat g = Rat.of("-0.5");
		Rat h = Rat.of(2, 5);
		Rat i = Rat.of(-2, 5);

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
		Rat result = Rat.of(numerator, denominator).reducePrecision(maxDigits);
		assertEquals(expectedNumerator, result.numerator().longValue());
		assertEquals(expectedDenominator, result.denominator().longValue());
	}
}