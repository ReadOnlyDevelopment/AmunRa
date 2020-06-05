package de.katzenpapst.amunra.helper;

public class MathHelperAR {

	/**
	 * Returns the largest int from any number of arguments
	 * 
	 * @param numbers
	 * @return
	 */
	public static int max(int... numbers) {
		int largest = Integer.MIN_VALUE;

		for (int number : numbers) {
			if (number > largest) {
				largest = number;
			}
		}

		return largest;
	}

	/**
	 * Returns the smallest int from any number of arguments
	 * 
	 * @param numbers
	 * @return
	 */
	public static int min(int... numbers) {
		int smallest = Integer.MAX_VALUE;

		for (int number : numbers) {
			if (number < smallest) {
				smallest = number;
			}
		}

		return smallest;
	}

}
