**Num** provides two core classes:

- `BigUtil`, a math and utilities library for more convenient and powerful usage of `BigDecimal` and `BigInteger`, including calculations not otherwise available such as BigDecimal raised to a BigDecimal power
- `Rat`, a rational number datatype for perfect-precision rational arithmetic and high-precision irrational arithmetic, including calculations such as nth root, rational raised to a rational power, and log of a rational with a rational base

# BigUtil

`BigUtil` provides static methods to improve both the convenience and capabilities of working with `BigDecimal` and `BigInteger`.

Convenience improvements begin with acquiring instances. Rather than trying to remember whether to use a constructor or `valueOf`, or which way is the safe way to get a BigDecimal from a double, just use `bd(val)` to get a BigDecimal or `bi(val)` to get a BigInteger. The argument can be any reasonable datatype. `bd` and `bi` can be statically imported for brevity since their names are unlikely to collide with other static methods. Additionally, the constants `BD0`, `BD1`, `BI0`, and `BI1` are provided, which can also be statically imported.

Comparisons are simplified. Rather than use the clumsy `a.compareTo(b) <op> 0` idiom, use BigUtil's `equal`, `notEqual`, `greater`, `less`, `greaterOrEqual`, or `lessOrEqual` methods. When using `equal` on a BigDecimal, scale and trailing zeros are ignored so that only numerical value is compared. Additionally, these methods, along with many other BigUtil methods, have overloads to take mixtures of BigDecimal and BigInteger, so you can compare values across the two types.

BigUtil offers calculation capabilities that are not available natively. BigDecimal and BigInteger do not offer `sqrt` or `log` methods, and their `pow` methods cannot take decimal values as the exponent. All these calculations are available with BigUtil.

Additionally, many arithmetic and utility methods offer improved convenience over what is provided natively. As stated earlier, many methods allow mixing and matching of BigDecimal and BigInteger types. Division automatically rounds to an extremely high precision if necessary, and otherwise returns the exact result without irritating scale. The `round` method, unlike `BigDecimal.round()`, rounds to the given number of decimal places, and unlike `BigDecimal.setScale()`, it doesn't require you to think about trailing zeros and BigDecimal's `scale` concept, which might have been better left as an implementation detail. Many other helpful methods are provided as well. See the BigUtil Javadoc for details.

# Rat

`Rat` is an immuatble numeric datatype that can exactly represent a rational number. A number like 1/3 is better represented as a Rat than as a BigDecimal or double because of the repeating decimal.

To acquire an instance of a Rat, use one of the overloaded `rat` factory methods, which can take many different kinds of inputs. Statically import `rat` for convenience. No matter what input is given to which `rat` method, the fraction will always be seen in reduced form when using methods such as `toString`, `numerator`, and `denominator`.

One `rat` factory method overload in particular take a string argument; this string is interpreted as an arithmetic expression and is evaluated to produce a rational number result. For example, `rat("2^-(1/(2^-2))")` returns `1/16`. 

Rats can be compared using `equal`, `notEqual`, `greater`, `less`, `greaterOrEqual`, or `lessOrEqual`. Rat implements `Comparable`, but these methods are provided for convenience in preference to `compareTo`.

Arithmetic methods are provided, including calculations such as `log`, `nthRoot`, and `pow`. If a result is irrational, a high-precision rational approximation is returned. If the result is rational, the exact result is returned. For example:

- `rat(4,9).sqrt()` returns `2/3`
- `rat(-0.03125).nthRoot(5)` returns `-1/2` &nbsp;&nbsp; *(0.03125 is 1/32)*
- `rat(16,81).pow(rat(-0.75))` returns `27/8`
- `rat(8,125).log(rat(25,4))` returns `-3/2` &nbsp;&nbsp; *(Read as the log of 8/125 with base 25/4)*

For more details, see the Rat Javadoc.