package com.github.jelmerk.knn.examples.programs.select;

import java.util.Arrays;

public class Select  {

	public static void shell(int num, float[] a) {

		int inc = 1;
		while (((3 * inc) + 1) < num) {
			inc = (3 * inc) + 1;
		}
		while (inc > 0) {

			for (int k = inc - 1; k < num; k = k + 1) {
				float v = a[k];
				int l = k;
				for (l = k; (l >= inc) && (a[l - inc] > v); l = l - inc) {
					a[l] = a[l - inc];
				}
				a[l] = v;
			}
			inc = inc / 3;
		}

	}

	public float selip(int k, float[] arr) throws Exception {
		int M = 10;
		float BIG = Float.MAX_VALUE;
		int i;
		int j;
		int n = arr.length;

		if (k < 1 || k > n) {
			throw new Exception("Bad Input to Selip");
		}
		int[] isel = new int[M + 2];
		float[] sel = new float[M + 2];
		int kk = k;
		float ahi = BIG;
		float alo = -BIG;
		while (true) {
			int mm = 0;
			int number_lo = 0;
			float sum = 0f;
			int nxtmm = M + 1;
			for (i = 0; i < n; i = i + 1) {
				if ((arr[i] >= alo) && (arr[i] <= ahi)) {
					mm = mm + 1;
					if (arr[i] == alo) {
						number_lo = number_lo + 1;
					}
					if (mm <= M) {
						sel[(mm - 1)] = arr[i];
					} else if (mm == nxtmm) {
						nxtmm = mm + mm / M;
						int index = i + mm + kk;
						index = index % M;
						sel[index] = arr[i];
					}
					sum = sum + arr[i];
				}
			}
			if (kk <= number_lo) {
				return alo;
			} else if (mm <= M) {
				shell(mm, sel);
				return sel[kk - 1];
			}
			sel[M] = sum / mm;
			shell(M + 1, sel);
			sel[M + 1] = ahi;
			for (j = 0; j < M + 1; j = j + 1) {

				isel[j] = 0;
			}
			for (i = 0; i < n; i = i + 1) {

				if ((arr[i] >= alo) && (arr[i] <= ahi)) {

					int j_lo = 0;
					int j_up = M + 1;
					while (j_up - j_lo > 1) {
						int j_mid = (j_up + j_lo) / 2;
						if (arr[i] >= sel[j_mid]) {
							j_lo = j_mid;
						} else {
							j_up = j_mid;
						}

					}
					if ((j_lo == 0) && (arr[i] < sel[j_lo])) {
						j_up = j_lo;
					}
					isel[j_up] = isel[j_up] + 1;
				}
			}

			j = 0;
			while (kk > isel[j]) {
				alo = sel[j];
				kk = kk - isel[j];
				j = j + 1;
			}
			ahi = sel[j];
		}
	}

	public float selipErr(int k, float[] arr) throws Exception {
		int M = 5; // Error int M = 10;
		float BIG = Float.MAX_VALUE;
		int i;
		int j;
		int n = arr.length;

		if (k < 1 || k > n) {
			throw new Exception("Bad Input to Selip");
		}
		int[] isel = new int[M + 2];
		float[] sel = new float[M + 2];
		int kk = k;
		float ahi = BIG;
		float alo = -BIG;
		while (true) {
			int mm = 0;
			int number_lo = 0;
			float sum = 0f;
			int nxtmm = M + 1;
			for (i = 0; i < n; i = i + 1) {
				if ((arr[i] >= alo) && (arr[i] <= ahi)) {
					mm = mm + 1;
					if (arr[i] == alo) {
						number_lo = number_lo + 1;
					}
					if (mm <= M) {
						sel[(mm - 1)] = arr[i];
					} else if (mm == nxtmm) {
						nxtmm = mm + mm / M;
						int index = i + mm + kk;
						index = index % M;
						sel[index] = arr[i];
					}
					sum = sum + arr[i];
				}
			}
			if (kk <= number_lo) {
				if (kk + number_lo == mm + 3) { // ERROR 该if判断为放入的bug
					return 0;
				}
				return alo;
			} else if (mm <= M) {
				shell(mm, sel);
				return sel[kk - 1];
			}
			sel[M] = sum / mm;
			shell(M + 1, sel);
			sel[M + 1] = ahi;
			for (j = 0; j < M + 1; j = j + 1) {

				isel[j] = 0;
			}
			for (i = 0; i < n; i = i + 1) {

				if ((arr[i] >= alo) && (arr[i] <= ahi)) {

					int j_lo = 0;
					int j_up = M + 1;
					while (j_up - j_lo > 1) {
						int j_mid = (j_up + j_lo) / 2;
						if (arr[i] >= sel[j_mid]) {
							j_lo = j_mid;
						} else {
							j_up = j_mid;
						}

					}
					if ((j_lo == 0) && (arr[i] < sel[j_lo])) {
						j_up = j_lo;
					}
					isel[j_up] = isel[j_up] + 1;
				}
			}

			j = 0;
			while (kk > isel[j]) {
				alo = sel[j];
				kk = kk - isel[j];
				j = j + 1;
			}
			ahi = sel[j];
		}
	}

	public boolean producesError(int x0, int x1, int x2, int x3, int x4, int x5, int x6, int x7, int x8, int x9,
			int x10) throws Exception {
		// TODO Auto-generated method stub
		int k = x0;
		float[] arrOrig = { x1, x2, x3, x4, x5, x6, x7, x8, x9, x10 };
		float[] arrErr = { x1, x2, x3, x4, x5, x6, x7, x8, x9, x10 };
		float orig = selip(k, arrOrig);
		float err = selipErr(k, arrErr);
		if (Math.abs(orig - err) > 1e-20) {
			return true;
		}
		return false;
	}
}
