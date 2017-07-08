package com.zieglersoftware.rational;

import static com.zieglersoftware.assertions.Assertions.between;
import static com.zieglersoftware.assertions.Assertions.notNull;
import static com.zieglersoftware.assertions.Assertions.tru;

import java.util.ArrayList;
import java.util.List;

/**
 * A list of tokens, each of which is either a {@code TNumber} or a character (, ), +, -, *, /, or ^.
 *
 * Package-private helper class for {@link Eval}
 */
final class Expression
{
	private final List<Object> tokens = new ArrayList<>();

	public static final Expression of(Object... tokens)
	{
		Expression expression = new Expression();
		for (Object token : tokens)
			expression.add(token);
		return expression;
	}

	public int size()
	{
		return tokens.size();
	}

	public boolean isEmpty()
	{
		return tokens.isEmpty();
	}

	public Expression add(Object token)
	{
		notNull(token, "token");
		if (token instanceof Character)
			return addSymbol((char) token);
		else if (token instanceof Rat)
			return addNumber((Rat) token);
		else
			throw new IllegalArgumentException("Token must be of type char or TNumber. Was " + token.getClass().getSimpleName());
	}

	public Expression addSymbol(char c)
	{
		tru(
			c == '(' || c == ')' || c == '+' || c == '-' || c == '*' || c == '/' || c == '^',
			"Symbol must be (, ), +, -, *, /, or ^. Was " + c);
		tokens.add(c);
		return this;
	}

	public Expression addNumber(Rat number)
	{
		notNull(number, "number");
		tokens.add(number);
		return this;
	}

	public Expression insert(int index, Object token)
	{
		notNull(token, "token");
		if (token instanceof Character)
			return insertSymbol(index, (char) token);
		else if (token instanceof Rat)
			return insertNumber(index, (Rat) token);
		else
			throw new IllegalArgumentException("Token must be of type char or TNumber. Was " + token.getClass().getSimpleName());
	}

	public Expression insertSymbol(int index, char c)
	{
		tru(
			c == '(' || c == ')' || c == '+' || c == '-' || c == '*' || c == '/' || c == '^',
			"Symbol must be (, ), +, -, *, /, or ^. Was " + c);
		between(index, 0, size(), "index");
		tokens.add(index, c);
		return this;
	}

	public Expression insertNumber(int index, Rat number)
	{
		notNull(number, "number");
		between(index, 0, size(), "index");
		tokens.add(index, number);
		return this;
	}

	public Expression set(int index, Object token)
	{
		notNull(token, "token");
		if (token instanceof Character)
			return setSymbol(index, (char) token);
		else if (token instanceof Rat)
			return setNumber(index, (Rat) token);
		else
			throw new IllegalArgumentException("Token must be of type char or TNumber. Was " + token.getClass().getSimpleName());
	}

	public Expression setSymbol(int index, char c)
	{
		tru(
			c == '(' || c == ')' || c == '+' || c == '-' || c == '*' || c == '/' || c == '^',
			"Symbol must be (, ), +, -, *, /, or ^. Was " + c);
		between(index, 0, size() - 1, "index");
		tokens.set(index, c);
		return this;
	}

	public Expression setNumber(int index, Rat number)
	{
		notNull(number, "number");
		between(index, 0, size() - 1, "index");
		tokens.set(index, number);
		return this;
	}

	public Expression addExpression(Expression expression)
	{
		notNull(expression, "expression");
		tokens.addAll(expression.tokens);
		return this;
	}

	public boolean isSymbol(int index)
	{
		tru(index >= 0 && index < tokens.size(), "index must at least 0 and less than %d. Was %d", tokens.size(), index);
		return tokens.get(index) instanceof Character;
	}

	public boolean isNumber(int index)
	{
		tru(index >= 0 && index < tokens.size(), "index must at least 0 and less than %d. Was %d", tokens.size(), index);
		return tokens.get(index) instanceof Rat;
	}

	public Object tokenAt(int index)
	{
		tru(index >= 0 && index < tokens.size(), "index must at least 0 and less than %d. Was %d", tokens.size(), index);
		return tokens.get(index);
	}

	public char symbolAt(int index)
	{
		Object o = tokenAt(index);
		tru((o instanceof Character), "Element at index %d was expected to be a char, but was %s %s instead", index, o.getClass().getSimpleName(), o);
		return (char) tokens.get(index);
	}

	public Rat numberAt(int index)
	{
		Object o = tokenAt(index);
		tru((o instanceof Rat), "Element at index %d was expected to be a TNumber, but was %s %s instead", index, o.getClass().getSimpleName(), o);
		return (Rat) tokens.get(index);
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for (Object o : tokens)
		{
			if (o instanceof Character)
				sb.append(o);
			else
				sb.append("[" + o + "]");
		}
		return sb.toString();
	}
}