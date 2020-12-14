package com.github.jelmerk.knn.examples.programs.calGCD;


/**
 * 
 * @author zxz 
 * 求十个数的最大公约数
 */
public class CalGCD  {

	public static int gcd(int... numbers) {
		int gcd = numbers[0];

		for (int i = 1; i < numbers.length; i++)
			gcd = gcd(gcd, numbers[i]);

		return gcd;
	}

	/** Return the gcd of two integers */
	public static int gcd(int n1, int n2) {
		int gcd = 1; // Initial gcd is 1
		int k = 1; // Possible gcd

		while (k <= n1 && k <= n2) {
			if (n1 % k == 0 && n2 % k == 0)
				gcd = k; // Update gcd
			k++;
		}

		return gcd; // Return gcd
	}




	public boolean producesError(int x0, int x1, int x2, int x3, int x4, int x5, int x6, int x7, int x8, int x9) {
		// TODO Auto-generated method stub
		int[] numbers = { x0, x1, x2, x3, x4, x5, x6, x7, x8, x9 };
		int orig = CalGCD.gcd(numbers);
		int err = CalGCDErr.gcd(numbers);
		if (orig != err) {
			return true;
		}
		return false;
	}

}
