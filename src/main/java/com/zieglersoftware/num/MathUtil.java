package com.zieglersoftware.num;

import static com.zieglersoftware.assertions.Assertions.greater;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Package-private utility class that offers static methods for math operations.
 */
final class MathUtil
{
	public static long gcd(long a, long b)
	{
		if (b == 0)
			return a;
		return gcd(b, a % b);
	}

	/**
	 * Returns a map of {@code [factor, power]} entries, representing the prime factorization
	 * of the given value. {@code val} must be greater than 1.
	 */
	public static Map<Long, Long> primeFactors(long val)
	{
		greater(val, 1, "val");
		if (val <= MAX_CACHED_PRIME_FACTORS)
			return PRIME_FACTORS_CACHE.get((int) val);
		return calculatePrimeFactors(val, new HashMap<>());
	}

	private static final int MAX_CACHED_PRIME_FACTORS = 100;
	private static final List<Map<Long, Long>> PRIME_FACTORS_CACHE = getPrimeFactorsCache();

	private static List<Map<Long, Long>> getPrimeFactorsCache()
	{
		List<Map<Long, Long>> cache = new ArrayList<>(MAX_CACHED_PRIME_FACTORS + 1);
		cache.add(null);
		cache.add(null);
		for (int i = 2; i <= MAX_CACHED_PRIME_FACTORS; i++)
			cache.add(calculatePrimeFactors(i, new HashMap<>()));
		return cache;
	}

	/**
	 * Expects that {@code val} is greater than 1. Does no cache lookups or preconditions checks.
	 */
	private static Map<Long, Long> calculatePrimeFactors(long val, Map<Long, Long> factors)
	{
		if (val == 2)
		{
			incrementPower(factors, 2);
			return factors;
		}
		else
		{
			long maxPossibleFactor = ((long) Math.sqrt(val)) + 1;
			for (long i = 2; i <= maxPossibleFactor; i++)
			{
				if (val % i == 0)
				{
					incrementPower(factors, i);
					return calculatePrimeFactors(val / i, factors);
				}
			}
			incrementPower(factors, val);
			return factors;
		}
	}

	private static void incrementPower(Map<Long, Long> factors, long factor)
	{
		Long currentPower = factors.get(factor);
		if (currentPower == null)
			factors.put(factor, 1L);
		else
			factors.put(factor, currentPower + 1);
	}
}
