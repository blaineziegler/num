package com.zieglersoftware.num;

import java.util.IllegalFormatException;

/**
 * Package-private utility class for preconditions checks.
 */
final class Assertions
{
	// Do not instantiate
	private Assertions()
	{}

	/**
	 * Asserts that the given object is not null. Returns the given object if the assertion succeeds.
	 * <p>
	 * {@code variableName} should be the name of the variable being tested.
	 * It will be included in the exception message for debugging purposes.
	 */
	public static <T> T notNull(T object, String variableName)
	{
		if (variableName == null)
			throw new NullPointerException("variableName cannot be null. This is about the variable name, not the variable itself!");
		if (object == null)
			throw new NullPointerException("Variable \"" + variableName + "\" cannot be null");
		return object;
	}

	/**
	 * Asserts that the given boolean is true.
	 * <p>
	 * {@code exceptionMessageWithPlaceholders}, along with the (optional) {@code placeholderValues}, will be used as the exception message
	 * if {@code test} is false. {@code exceptionMessageWithPlaceholders} should be formatted exactly the same way as in
	 * {@link String#format(String, Object...)}, e.g., %d to represent a long or integer, and %s to represent a string.
	 */
	public static void tru(boolean test, String exceptionMessageWithPlaceholders, Object... placeholderValues)
	{
		if (exceptionMessageWithPlaceholders == null)
			throw new NullPointerException("exceptionMessageWithPlaceholders cannot be null");
		if (placeholderValues == null)
			throw new NullPointerException("placeholderValues cannot be null");
		String combinedExceptionMessage;
		try
		{
			combinedExceptionMessage = String.format(exceptionMessageWithPlaceholders, placeholderValues);
		}
		catch (IllegalFormatException e)
		{
			throw new IllegalArgumentException("exceptionMessageWithPlaceholders and placeholderValues are illegally formatted", e);
		}
		if (!test)
			throw new IllegalStateException(combinedExceptionMessage);
	}

	/**
	 * Asserts that the given boolean is false.
	 * <p>
	 * {@code exceptionMessageWithPlaceholders}, along with the (optional) {@code placeholderValues}, will be used as the exception message
	 * if {@code test} is true. {@code exceptionMessageWithPlaceholders} should be formatted exactly the same way as in
	 * {@link String#format(String, Object...)}, e.g., %d to represent a long or integer, and %s to represent a string.
	 */
	public static void fals(boolean test, String exceptionMessageWithPlaceholders, Object... placeholderValues)
	{
		if (exceptionMessageWithPlaceholders == null)
			throw new NullPointerException("exceptionMessageWithPlaceholders cannot be null");
		if (placeholderValues == null)
			throw new NullPointerException("placeholderValues cannot be null");
		String combinedExceptionMessage;
		try
		{
			combinedExceptionMessage = String.format(exceptionMessageWithPlaceholders, placeholderValues);
		}
		catch (IllegalFormatException e)
		{
			throw new IllegalArgumentException("exceptionMessageWithPlaceholders and placeholderValues are illegally formatted", e);
		}
		if (test)
			throw new IllegalStateException(combinedExceptionMessage);
	}

	/**
	 * Asserts that the given object is not equal to the given reference value. If both the given object and the reference value are
	 * null, the assertions fails. If one is null but the other is not, the assertion passes. If both are non-null,
	 * {@code object.equals(referenceVal)} is used to make the comparison. Returns the given object if the assertion succeeds.
	 * <p>
	 * {@code variableName} should be the name of the object being tested.
	 * It will be included in the exception message for debugging purposes.
	 */
	public static <T> T notEqual(T object, Object referenceVal, String variableName)
	{
		if (variableName == null)
			throw new NullPointerException("variableName cannot be null. This is about the variable name, not the variable itself!");
		if (object == null)
		{
			if (referenceVal == null)
				throw new IllegalStateException(
					"Variable \"" + variableName + "\" must not be equal to the given reference value. Both are null");
			else
				return object;
		}
		else if (object.equals(referenceVal))
			throw new IllegalStateException(
				"Variable \"" + variableName + "\" must not be equal to " + referenceVal);
		return object;
	}

	/**
	 * Asserts that the given string is not null and not empty. Returns the given string if the assertion succeeds.
	 * <p>
	 * {@code stringName} should be the name of the string being tested.
	 * It will be included in the exception message for debugging purposes.
	 */
	public static String notEmpty(String string, String stringName)
	{
		if (stringName == null)
			throw new NullPointerException("stringName cannot be null. This is about the name of the string, not the string itself!");
		if (string == null)
			throw new NullPointerException("String \"" + stringName + "\" cannot be null");
		if (string.isEmpty())
			throw new IllegalStateException("String \"" + stringName + "\" cannot be empty");
		return string;
	}

	/**
	 * Asserts that the given value is greater than the given reference value. Returns the given value if the assertion succeeds.
	 * <p>
	 * {@code variableName} should be the name of the variable being tested.
	 * It will be included in the exception message for debugging purposes.
	 */
	public static long greater(long val, long referenceVal, String variableName)
	{
		if (variableName == null)
			throw new NullPointerException("variableName cannot be null. This is about the variable name, not the variable itself!");
		if (val <= referenceVal)
			throw new IllegalStateException("Variable \"" + variableName + "\" must be greater than " + referenceVal + ". Was " + val);
		return val;
	}

	/**
	 * Asserts that the given value is between the two given reference values, inclusive, i.e.,
	 * {@code lowReferenceVal <= val <= highReferenceVal}. Returns the given value if the assertion succeeds.
	 * <p>
	 * {@code variableName} should be the name of the variable being tested.
	 * It will be included in the exception message for debugging purposes.
	 */
	public static long between(long val, long lowReferenceVal, long highReferenceVal, String variableName)
	{
		if (variableName == null)
			throw new NullPointerException("variableName cannot be null. This is about the variable name, not the variable itself!");
		if (val < lowReferenceVal || val > highReferenceVal)
			throw new IllegalStateException(
				"Variable \"" + variableName + "\" must be between " +
					lowReferenceVal + " and " + highReferenceVal + ", inclusive. Was " + val);
		return val;
	}
}