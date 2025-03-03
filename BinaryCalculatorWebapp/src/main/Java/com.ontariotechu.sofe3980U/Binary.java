package com.ontariotechu.sofe3980U;

/**
 * Unsigned integer Binary variable
 *
 */
public class Binary {
	private String number = "0"; // string containing the binary value '0' or '1'

	/**
	 * A constructor that generates a binary object.
	 *
	 * @param number a String of the binary values. It should contain only zeros or ones with any length and order. otherwise, the value of "0" will be stored. Trailing zeros will be excluded and empty string will be considered as zero.
	 */
	public Binary(String number) {
		if (number == null || number.isEmpty()) {
			this.number = "0"; // Default to "0" for null or empty input
			return;
		}

		// Validate the binary string (only '0' or '1' allowed)
		for (int i = 0; i < number.length(); i++) {
			char ch = number.charAt(i);
			if (ch != '0' && ch != '1') {
				this.number = "0"; // Default to "0" for invalid input
				return;
			}
		}

		// Remove leading zeros
		int beg;
		for (beg = 0; beg < number.length(); beg++) {
			if (number.charAt(beg) != '0') {
				break;
			}
		}

		// If all digits are '0', ensure number is "0"
		this.number = (beg == number.length()) ? "0" : number.substring(beg);

		// Ensure empty strings are replaced with "0"
		if (this.number.isEmpty()) {
			this.number = "0";
		}
	}

	/**
	 * Return the binary value of the variable
	 *
	 * @return the binary value in a string format.
	 */
	public String getValue() {
		return this.number;
	}

	/**
	 * Adding two binary variables. For more information, visit <a href="https://www.wikihow.com/Add-Binary-Numbers"> Add-Binary-Numbers </a>.
	 *
	 * @param num1 The first addend object
	 * @param num2 The second addend object
	 * @return A binary variable with a value of <i>num1+num2</i>.
	 */
	public static Binary add(Binary num1, Binary num2) {
		int ind1 = num1.number.length() - 1;
		int ind2 = num2.number.length() - 1;
		int carry = 0;
		String num3 = ""; // the binary value of the sum

		while (ind1 >= 0 || ind2 >= 0 || carry != 0) { // loop until all digits are processed
			int sum = carry; // previous carry
			if (ind1 >= 0) {
				sum += (num1.number.charAt(ind1) == '1') ? 1 : 0; // convert the digit to int and add it to sum
				ind1--; // update ind1
			}
			if (ind2 >= 0) {
				sum += (num2.number.charAt(ind2) == '1') ? 1 : 0; // convert the digit to int and add it to sum
				ind2--; // update ind2
			}
			carry = sum / 2; // the new carry
			sum = sum % 2; // the resultant digit
			num3 = ((sum == 0) ? "0" : "1") + num3; // convert sum to string and append it to num3
		}

		return new Binary(num3);
	}

	/**
	 * Perform bitwise logical OR on two binary variables.
	 *
	 * @param num1 The first operand
	 * @param num2 The second operand
	 * @return A binary variable with the result of num1 OR num2
	 */
	public static Binary or(Binary num1, Binary num2) {
		int maxLength = Math.max(num1.number.length(), num2.number.length());
		StringBuilder result = new StringBuilder();

		for (int i = 0; i < maxLength; i++) {
			char bit1 = (i < num1.number.length()) ? num1.number.charAt(num1.number.length() - 1 - i) : '0';
			char bit2 = (i < num2.number.length()) ? num2.number.charAt(num2.number.length() - 1 - i) : '0';

			result.append((bit1 == '1' || bit2 == '1') ? '1' : '0');
		}

		return new Binary(result.reverse().toString());
	}

	/**
	 * Perform bitwise logical AND on two binary variables.
	 *
	 * @param num1 The first operand
	 * @param num2 The second operand
	 * @return A binary variable with the result of num1 AND num2
	 */
	public static Binary and(Binary num1, Binary num2) {
		String binary1 = num1.getValue();
		String binary2 = num2.getValue();

		// Align lengths by padding with leading zeros
		while (binary1.length() < binary2.length()) {
			binary1 = "0" + binary1;
		}
		while (binary2.length() < binary1.length()) {
			binary2 = "0" + binary2;
		}

		// Perform bitwise AND
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < binary1.length(); i++) {
			char bit1 = binary1.charAt(i);
			char bit2 = binary2.charAt(i);
			result.append((bit1 == '1' && bit2 == '1') ? "1" : "0");
		}

		return new Binary(result.toString());
	}

	/**
	 * Multiply two binary variables.
	 *
	 * @param num1 The first operand
	 * @param num2 The second operand
	 * @return A binary variable with the result of num1 * num2
	 */
	public static Binary multiply(Binary num1, Binary num2) {
		Binary result = new Binary("0");
		for (int i = 0; i < num2.number.length(); i++) {
			if (num2.number.charAt(num2.number.length() - 1 - i) == '1') {
				String shifted = num1.number + "0".repeat(i);
				result = add(result, new Binary(shifted));
			}
		}
		return result;
	}
}