package com.zieglersoftware.num;

import static com.zieglersoftware.assertions.Assertions.fals;
import static com.zieglersoftware.assertions.Assertions.notEmpty;
import static com.zieglersoftware.assertions.Assertions.notNull;
import static com.zieglersoftware.assertions.Assertions.tru;

import java.math.BigInteger;

/**
 * Package-private utility class that implements the evaluation
 * of a string arithmetic expression to a {@link Rat}
 */
final class Eval
{
	private Eval()
	{
	}

	/**
	 * Evaluates the given expression and returns the result as a {@code Rat}.
	 * The expression must be a valid arithmetic expression, and may contain
	 * whitespace, digits, decimal points, parenthesis, and the operators
	 * {@code + - * / ^}.
	 */
	public static Rat eval(String expression)
	{
		notNull(expression, "expression");
		fals(expression.isEmpty(), "Expression empty");
		StringBuilder trimmedBuilder = new StringBuilder();
		for (int i = 0; i < expression.length(); i++)
		{
			char c = expression.charAt(i);
			if (!Character.isWhitespace(c))
				trimmedBuilder.append(c);
		}
		String trimmed = trimmedBuilder.toString();
		if (trimmed.isEmpty())
			throw new IllegalArgumentException("Expression contains only whitespace");
		Expression asExpression = new Expression();
		for (int i = 0; i < trimmed.length(); i++)
		{
			String upcomingNumberChars = upcomingNumberChars(trimmed, i);
			int upcomingNumberLength = upcomingNumberChars.length();
			if (upcomingNumberLength > 0)
			{
				asExpression.addNumber(parse(upcomingNumberChars));
				i += upcomingNumberLength - 1;
			}
			else
				asExpression.addSymbol(trimmed.charAt(i));
		}
		Rat result = evalExpression(asExpression);
		return result;
	}

	/**
	 * Returns the consecutive upcoming characters beginning at {@code initialPosition}
	 * that are digit characters or the . character
	 */
	private static String upcomingNumberChars(String string, int initialPosition)
	{
		StringBuilder result = new StringBuilder();
		for (int i = initialPosition; i < string.length(); i++)
		{
			char c = string.charAt(i);
			if ((c >= '0' && c <= '9') || c == '.')
				result.append(c);
			else
				break;
		}
		return result.toString();
	}

	/**
	 * Expects digits and the decimal character only
	 */
	private static Rat parse(String value)
	{
		notEmpty(value, "value");
		StringBuilder preDecimal = new StringBuilder();
		StringBuilder postDecimal = null;
		for (int i = 0; i < value.length(); i++)
		{
			char c = value.charAt(i);
			if (c == '.')
			{
				if (postDecimal != null)
					throw new IllegalArgumentException("There may be at most one '.' character. Was " + value);
				postDecimal = new StringBuilder();
			}
			else if (postDecimal == null)
				preDecimal.append(c);
			else
				postDecimal.append(c);
		}
		if (preDecimal.length() == 0)
			preDecimal.append('0');
		if (postDecimal != null && postDecimal.length() == 0)
			throw new IllegalArgumentException("Digits must occur after the '.' character. Was " + value);
		if (postDecimal == null)
		{
			BigInteger bigIntValue = new BigInteger(preDecimal.toString());
			return Rat.of(bigIntValue);
		}
		else
		{
			BigInteger wholePart = new BigInteger(preDecimal.toString());
			BigInteger decimalPart = new BigInteger(postDecimal.toString());
			BigInteger denominator = BigInteger.TEN;
			for (int i = 1; i < postDecimal.length(); i++)
				denominator = denominator.multiply(BigInteger.TEN);
			BigInteger numerator = wholePart.multiply(denominator).add(decimalPart);
			return Rat.of(numerator, denominator);
		}
	}

	private static Rat evalExpression(Expression expression)
	{
		notNull(expression, "expression");
		fals(expression.isEmpty(), "Expression empty");
		int size = expression.size();
		int latestOpenParenIndex = -1;
		int earliestCloseParenIndex = -1;
		for (int i = 0; i < size; i++)
		{
			Object token = expression.tokenAt(i);
			if (token.equals('('))
				latestOpenParenIndex = i;
			else if (token.equals(')'))
			{
				if (latestOpenParenIndex == -1)
					throw new IllegalArgumentException("Mismatched parenthesis. Expression: " + expression);
				earliestCloseParenIndex = i;
				break;
			}
		}
		if (latestOpenParenIndex != -1 && earliestCloseParenIndex == -1)
			throw new IllegalArgumentException("Mismatched parenthesis. Expression: " + expression);
		if (latestOpenParenIndex == -1)
			return evalFlat(expression);
		else
		{
			Expression innerMostParensEliminated = new Expression();
			for (int i = 0; i < latestOpenParenIndex; i++)
				innerMostParensEliminated.add(expression.tokenAt(i));
			if (latestOpenParenIndex > 0 && (expression.isNumber(latestOpenParenIndex - 1) || expression.symbolAt(latestOpenParenIndex - 1) == ')'))
				innerMostParensEliminated.addSymbol('*');
			Expression withinInnerMostParens = new Expression();
			for (int i = latestOpenParenIndex + 1; i < earliestCloseParenIndex; i++)
				withinInnerMostParens.add(expression.tokenAt(i));
			Rat innerMostParensResult = evalFlat(withinInnerMostParens);
			innerMostParensEliminated.addNumber(innerMostParensResult);
			if (earliestCloseParenIndex + 1 < size && expression.isNumber(earliestCloseParenIndex + 1))
				innerMostParensEliminated.addSymbol('*');
			for (int i = earliestCloseParenIndex + 1; i < size; i++)
				innerMostParensEliminated.add(expression.tokenAt(i));
			Rat result = evalExpression(innerMostParensEliminated);
			return result;
		}
	}

	private static Rat evalFlat(Expression expression)
	{
		Expression exponentsEliminated = eliminateExponents(expression);
		Expression negativesEliminated = eliminateNegatives(exponentsEliminated);
		Expression multiplicationDivisionEliminated = eliminateMultiplicationDivision(negativesEliminated);
		Rat additionSubtractionEliminated = eliminateAdditionSubtraction(multiplicationDivisionEliminated);
		return additionSubtractionEliminated;
	}

	private static Expression eliminateExponents(Expression expression)
	{
		notNull(expression, "expression");
		fals(expression.isEmpty(), "Expression empty");
		int size = expression.size();
		fals(expression.tokenAt(0).equals('^'), "The first token of expression cannot be '^'. Expression: %s", expression);
		fals(expression.tokenAt(size - 1).equals('^'), "The last token of expression cannot be '^'. Expression: %s", expression);
		int finalExponentIndex = -1;
		for (int i = size - 2; i >= 1; i--)
		{
			if (expression.tokenAt(i).equals('^'))
			{
				finalExponentIndex = i;
				break;
			}
		}
		if (finalExponentIndex == -1)
			return expression;
		else
		{
			Object priorToken = expression.tokenAt(finalExponentIndex - 1);
			tru(priorToken instanceof Rat, "Expected a number at index %d, but got %s", finalExponentIndex - 1, priorToken);
			Rat base = (Rat) priorToken;
			int finalNegativeAfterExponentIndex = finalExponentIndex;
			int factor = 1;
			for (; finalNegativeAfterExponentIndex < size - 2 && expression.tokenAt(finalNegativeAfterExponentIndex + 1).equals('-'); finalNegativeAfterExponentIndex++, factor *= -1)
				;
			Object nextTokenAfterNegatives = expression.tokenAt(finalNegativeAfterExponentIndex + 1);
			if (!(nextTokenAfterNegatives instanceof Rat))
				throw new IllegalArgumentException("Expected a number at index " + (finalNegativeAfterExponentIndex + 1) + ", but got " + nextTokenAfterNegatives + ". Expression: " + expression);
			Rat power = apply((Rat) nextTokenAfterNegatives, Rat.of(factor), '*');
			Rat exponentResult = apply(base, power, '^');
			Expression result = new Expression();
			for (int i = 0; i < finalExponentIndex - 1; i++)
				result.add(expression.tokenAt(i));
			result.addNumber(exponentResult);
			for (int i = finalNegativeAfterExponentIndex + 2; i < size; i++)
				result.add(expression.tokenAt(i));
			return eliminateExponents(result);
		}
	}

	private static Expression eliminateNegatives(Expression expression)
	{
		notNull(expression, "expression");
		fals(expression.isEmpty(), "Expression empty");
		int size = expression.size();
		Expression result = new Expression();
		boolean expectNumber = true;
		for (int i = 0; i < size; i++)
		{
			Object token = expression.tokenAt(i);
			if (expectNumber)
			{
				if (token instanceof Rat)
					result.addNumber((Rat) token);
				else if (token.equals('-'))
				{
					int factor = -1;
					for (i = i + 1; i < size - 1 && expression.tokenAt(i).equals('-'); i++, factor *= -1)
						;
					Object nextTokenAfterNegatives = expression.tokenAt(i);
					if (!(nextTokenAfterNegatives instanceof Rat))
						throw new IllegalArgumentException("Expected a number at index " + i + ", but got " + nextTokenAfterNegatives + ". Expression: " + expression);
					result.addNumber(apply((Rat) nextTokenAfterNegatives, Rat.of(factor), '*'));
				}
				else
					throw new IllegalArgumentException("Expected a number or '-' at index " + i + ", but got " + token + ". Expression: " + expression);
			}
			else
			{
				if (token instanceof Character)
					result.add((char) token);
				else
					throw new IllegalArgumentException("Expected a symbol at index " + i + ", but got a number. Expression: " + expression);
			}
			expectNumber = !expectNumber;
		}
		return result;
	}

	private static Expression eliminateMultiplicationDivision(Expression expression)
	{
		notNull(expression, "expression");
		fals(expression.isEmpty(), "Expression empty");
		int size = expression.size();
		tru(size % 2 == 1, "The number of tokens in expression must be odd. Expression: %s", expression);
		tru(expression.isNumber(0), "The first token of expression must be a number. Expression: %s", expression);
		Expression result = new Expression().addNumber(expression.numberAt(0));
		for (int i = 1; i < size; i += 2)
		{
			tru(expression.isSymbol(i), "Odd-indexed tokens of expression must be +, -, *, or /. Expression: %s", expression);
			char symbol = expression.symbolAt(i);
			tru(symbol == '+' || symbol == '-' || symbol == '*' || symbol == '/', "Odd-indexed tokens of expression must be +, -, *, or /. Expression: %s", expression);
			tru(expression.isNumber(i + 1), "Even-indexed tokens of expression must be numbers. Expression: %s", expression);
			Rat number = expression.numberAt(i + 1);
			if (symbol == '+' || symbol == '-')
			{
				result.addSymbol(symbol);
				result.addNumber(number);
			}
			else if (symbol == '*' || symbol == '/')
			{
				Rat lastNumberInResult = result.numberAt(result.size() - 1);
				Rat newLastNumberInResult = apply(lastNumberInResult, number, symbol);
				result.setNumber(result.size() - 1, newLastNumberInResult);
			}
			else
				throw new IllegalArgumentException("Expected +, -, *, or / but got " + symbol + ". Expression: " + expression);
		}
		return result;
	}

	private static Rat eliminateAdditionSubtraction(Expression expression)
	{
		notNull(expression, "expression");
		fals(expression.isEmpty(), "Expression empty");
		int size = expression.size();
		tru(size % 2 == 1, "The number of tokens in expression must be odd. Expression: %s", expression);
		tru(expression.isNumber(0), "The first token of expression must be a number. Expression: %s", expression);
		Rat result = expression.numberAt(0);
		for (int i = 1; i < size; i += 2)
		{
			tru(expression.isSymbol(i), "Odd-indexed tokens of expression must be + or -. Expression: %s", expression);
			char symbol = expression.symbolAt(i);
			tru(symbol == '+' || symbol == '-', "Odd-indexed tokens of expression must be + or -. Expression: %s", expression);
			tru(expression.isNumber(i + 1), "Even-indexed tokens of expression must be numbers. Expression: %s", expression);
			Rat number = expression.numberAt(i + 1);
			result = apply(result, number, symbol);
		}
		return result;
	}

	private static Rat apply(Rat number1, Rat number2, char operation)
	{
		switch (operation)
		{
			case '+':
				return number1.plus(number2);
			case '-':
				return number1.minus(number2);
			case '*':
				return number1.times(number2);
			case '/':
				return number1.dividedBy(number2);
			case '^':
				return number1.pow(number2);
			default:
				throw new IllegalArgumentException("Expected +, -, *, /, or ^. Got " + operation);
		}
	}
}