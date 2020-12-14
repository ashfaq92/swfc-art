package com.github.jelmerk.knn.examples.programs.calGCD;

public class CalGCDErr {

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

		while (k <= n1 && k <= n2 ) { 
			if (n1 % k == 0 && n2 / k == 0)    //Error if (n1 % k == 0 && n2 % k == 0)
				gcd = k; // Update gcd
			k++;
		} 
		return gcd; // Return gcd
	}

}
