package com.github.jelmerk.knn.examples.programs.NumericMatrixProcessor;

import com.github.jelmerk.knn.examples.auxiliary.model.Point;

import java.util.Random;

public class MainNew_m {
    public static double[][] bd = { {-10000, 10000}, {-10000, 10000}, {-10000, 10000}, {-10000, 10000},
            {-10000, 10000}, {-10000, 10000}, {-10000, 10000}, {-10000, 10000}, {-10000, 10000}, {-10000, 10000},
            {-10000, 10000}, {-10000, 10000}};
    public static int columns;
    public static int rows;
    public static Random rng = new Random();

    public static double[][] addMatrices(Point tc) {
        // System.out.println("addMatrices");
        // int i = 3; // columns
        // int j = 2;  // rows
        rows = 2;
        columns = 3;
        int tc_index = 0;
        double[][] firstMatrix = new double[rows][columns];
        for (int a = 0; a < rows; a++) {
            for (int b = 0; b < columns; b++) {
                firstMatrix[a][b] = tc.vector[tc_index];
                tc_index++;
            }
        }

        double[][] secondMatrix = new double[rows][columns];
        for (int a = 0; a < rows; a++) {
            for (int b = 0; b < columns; b++) {
                secondMatrix[a][b] = tc.vector[tc_index];
                tc_index++;
            }
        }

        double[][] additionMatrix = new double[rows][columns];
        for (int a = 0; a < rows; a++) {
            for (int b = 0; b < columns; b++) {
                additionMatrix[a][b] = firstMatrix[a][b] + secondMatrix[a][b];
            }
        }
        return additionMatrix;
    }

    public static double[][] multiplyMatricesConsonant(Point tc) {
        // System.out.println("multiplyMatricesConsonant");
        rows = 4;
        columns = 3;
        int tc_index = 0;
        double[][] firstMatrix = new double[rows][columns];
        for (int a = 0; a < rows; a++) {
            for (int b = 0; b < columns; b++) {
                firstMatrix[a][b] = tc.vector[tc_index];
                tc_index++;
            }
        }
        // int multiple = rng.nextInt();
        double multiple = tc.vector[0];

        double[][] resultantMatrix = new double[rows][columns];
        for (int a = 0; a < rows; a++) {
            for (int b = 0; b < columns; b++) {
                resultantMatrix[a][b] = firstMatrix[a][b] * multiple;
            }
        }
        return resultantMatrix;
    }

    public static double[][] multiplyMatrices(Point tc) {
        // System.out.println("multiplyMatrices");

        // System.out.print("Enter size of first matrix: ");
        rows = 2;
        columns = 3;
        int i = rows;
        int j = columns;
        int m = columns;
        int n = rows;
        int tc_index = 0;

        double[][] firstMatrix = new double[i][j];
        // System.out.println("Enter first matrix:");
        for (int a = 0; a < i; a++) {
            for (int b = 0; b < j; b++) {
                firstMatrix[a][b] = tc.vector[tc_index];
                tc_index++;
            }
        }
        if (j != m) {
            System.out.println("Error in the dimensions of matrices");
            return firstMatrix;
        }

        double[][] secondMatrix = new double[m][n];

        // System.out.println("Enter second matrix:");
        for (int a = 0; a < m; a++) {
            for (int b = 0; b < n; b++) {
                secondMatrix[a][b] = tc.vector[tc_index];
                tc_index++;
            }
        }
        //Calling the method multiplyMatrices
        return multiplyMatrices(i, n, j, firstMatrix, secondMatrix);
    }

    public static double[][] multiplyMatrices(int i, int n, int j, double[][] firstMatrix, double[][] secondMatrix) {
        // System.out.println("multiplyMatrices");
        double[][] multipliedMatrix = new double[i][n];
        for (int a = 0; a < i; a++) {
            for (int b = 0; b < n; b++) {
                for (int c = 0; c < j; c++) {
                    multipliedMatrix[a][b] += firstMatrix[a][c] * secondMatrix[c][b];
                }
            }
        }
        return multipliedMatrix;
    }

    public static double[][] transposeMatrix(Point tc, int choice) {
        // System.out.println("transposeMatrix");
        // System.out.println("1. Main diagonal");
        // System.out.println("2. Side diagonal");
        // System.out.println("3. Vertical line");
        // System.out.println("4. Horizontal line");
        // System.out.print("Your choice: ");
        // java.util.Random random = new java.util.Random();
        int prob = new Random().nextInt(1000);
        if (choice == 1) {
            return transposeMatrixMainDiagonal(tc);
        } else if (choice == 2) {
            return sideDiagonal(tc);
        } else if (choice == 3) {
            return verticalLine(tc);
        } else if (choice == 4 && prob == 50) {
            return verticalLine(tc);
        } else if (choice == 4) {
            return horizontalLine(tc);
        }
        return new double[0][];
    }

    private static double[][] transposeMatrixMainDiagonal(Point tc) {
        // System.out.print("Enter matrix size: ");
        // System.out.println("transposeMatrixMainDiagonal");
        rows = 4;
        columns = 3;
        int i = rows;
        int j = columns;
        int tcIndex = 0;

        double[][] matrix = new double[i][j];

        // System.out.println("Enter matrix: ");
        for (int a = 0; a < i; a++) {
            for (int b = 0; b < j; b++) {
                matrix[a][b] = tc.vector[tcIndex];
                tcIndex++;
            }
        }

        //Transposing Matrix by Main Diagonal will change its dimension from ixj to jxi
        double[][] transposedMatrix = new double[j][i];
        for (int a = 0; a < i; a++) {
            for (int b = 0; b < j; b++) {
                transposedMatrix[b][a] = matrix[a][b];
            }
        }
        return transposedMatrix;
    }

    private static double[][] sideDiagonal(Point tc) {
        // System.out.println("sideDiagonal");
        // System.out.print("Enter matrix size: ");
        rows = 4;
        columns = 3;
        int i = rows;
        int j = columns;
        int tcIndex = 0;

        double[][] matrix = new double[i][j];

        // System.out.println("Enter matrix: ");
        for (int a = 0; a < i; a++) {
            for (int b = 0; b < j; b++) {
                matrix[a][b] = tc.vector[tcIndex];
                tcIndex++;
            }
        }

        double[][] transposedMatrix = new double[j][i];

        int row = 0;
        int col = 0;
        for (int a = j - 1; a >= 0; a--) {
            for (int b = i - 1; b >= 0; b--) {
                transposedMatrix[row][col] = matrix[b][a];
                col++;
                //If we reach the limit of col, we reset it to start from 0. We are using this to avoid another for loop
                if (col == i) {
                    col = 0;
                }

            }
            /*
            We don't need to limit row since, the for loop of 'a' will take place for the number of row it has.
            We have introduced another variable 'row' to the program. It is to make the loop start from last row but
            the transposed matrix needs to have value from the initial row (i.e. 0). So, we introduce 'row' and we
            increase the counter.
            */
            row++;
        }

        return transposedMatrix;
    }

    private static double[][] verticalLine(Point tc) {
        // System.out.print("Enter matrix size: ");
        // System.out.println("verticalLine");
        rows = 4;
        columns = 3;
        int i = rows;
        int j = columns;
        int tcIndex = 0;

        double[][] matrix = new double[i][j];

        // System.out.println("Enter matrix:");
        for (int a = 0; a < i; a++) {
            for (int b = 0; b < j; b++) {
                matrix[a][b] = tc.vector[tcIndex];
                tcIndex++;
            }
        }

        double[][] transposedMatrix = new double[i][j];

        int col = 0;
        for (int a = 0; a < i; a++) {
            for (int b = j - 1; b >= 0; b--) {
                transposedMatrix[a][col] = matrix[a][b];
                col++;
                //In this case, if the end of the column (i.e. j) is reached, then we are starting from first.
                if (col == j) {
                    col = 0;
                }
            }
        }

        return transposedMatrix;
    }

    private static double[][] horizontalLine(Point tc) {
        // System.out.println("horizontalLine");
        rows = 4;
        columns = 3;
        int i = rows;
        int j = columns;
        int tcIndex = 0;

        double[][] matrix = new double[i][j];

        // System.out.println("Enter matrix:");
        for (int a = 0; a < i; a++) {
            for (int b = 0; b < j; b++) {
                matrix[a][b] = tc.vector[tcIndex];
                tcIndex++;
            }
        }

        int row = 0;
        double[][] transposedMatrix = new double[i][j];
        for (int a = i - 1; a >= 0; a--) {
            for (int b = 0; b < j; b++) {
                transposedMatrix[row][b] = matrix[a][b];
            }
            //We are starting from the last row in the case of for loop for 'a'. However, we need to assign values to
            //transposed matrix. So, we have introduced 'row' which acts as a counter.
            row++;
        }

        return transposedMatrix;
    }

    static double determinantOfMatrix(Point tc) {
        // System.out.println("determinantOfMatrix");
        rows = 3;
        columns = 3;
        int i = rows;
        int j = columns;
        int tcIndex = 0;
        double[][] matrix = new double[i][j];

        // System.out.println("Enter matrix:");
        for (int a = 0; a < i; a++) {
            for (int b = 0; b < j; b++) {
                matrix[a][b] = tc.vector[tcIndex];
                tcIndex++;
            }
        }

        double determinant;
        if (i == 2 && j == 2) {
            determinant = (matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]);
            // System.out.println("The result is:");
            // System.out.println(determinant);
            return determinant;
        }
        determinant = determinantOfMatrix(matrix, i);
        return determinant;
    }

    public static void getCofactor(double mat[][], double temp[][], int p, int q, int n) {
        // System.out.println("getCofactor");
        int a = 0, b = 0;

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (row != p && col != q) {
                    temp[a][b++] = mat[row][col];

                    if (b == n - 1) {
                        b = 0;
                        a++;
                    }
                }
            }
        }

    }

    public static double determinantOfMatrix(double mat[][], int n) {
        // System.out.println("determinantOfMatrix");
        double D = 0;

        if (n == 1) {
            return mat[0][0];
        }

        //To store cofactors
        double temp[][] = new double[n][n];

        //To store sign multiplier
        int sign = 1;

        //Iterate for each element of first row
        for (int f = 0; f < n; f++) {
            //Getting cofactor of mat[0][f]
            getCofactor(mat, temp, 0, f, n);
            D += sign * mat[0][f] * determinantOfMatrix(temp, n - 1);

            //terms are to be added with alternate sign
            sign = -sign;
        }

        return D;
    }

    public static double[][] inverseOfMatrix(Point tc) {
        // System.out.println("inverseOfMatrix");
        // System.out.print("Enter matrix size: ");
        rows = 3;
        columns = 3;
        int n = rows;
        int m = columns;
        int tcIndex = 0;
        if (n != m) {
            return new double[0][];
        }
        double a[][] = new double[n][n];
        // System.out.println("Enter the elements of matrix: ");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = tc.vector[tcIndex];
                tcIndex++;
            }
        }

        return invert(a);
    }

    public static double[][] invert(double a[][]) {
        // System.out.println("invert");
        int n = a.length;
        double x[][] = new double[n][n];
        double b[][] = new double[n][n];
        int index[] = new int[n];
        for (int i = 0; i < n; ++i)
            b[i][i] = 1;

        // Transform the matrix into an upper triangle
        gaussian(a, index);

        // Update the matrix b[i][j] with the ratios stored
        for (int i = 0; i < n - 1; ++i)
            for (int j = i + 1; j < n; ++j)
                for (int k = 0; k < n; ++k)
                    b[index[j]][k]
                            -= a[index[j]][i] * b[index[i]][k];

        // Perform backward substitutions
        for (int i = 0; i < n; ++i) {
            x[n - 1][i] = b[index[n - 1]][i] / a[index[n - 1]][n - 1];
            for (int j = n - 2; j >= 0; --j) {
                x[j][i] = b[index[j]][i];
                for (int k = j + 1; k < n; ++k) {
                    x[j][i] -= a[index[j]][k] * x[k][i];
                }
                x[j][i] /= a[index[j]][j];
            }
        }
        return x;
    }

    public static void gaussian(double a[][], int index[]) {
        // System.out.println("gaussian");
        int n = index.length;
        double c[] = new double[n];

        // Initialize the index
        for (int i = 0; i < n; ++i)
            index[i] = i;

        // Find the rescaling factors, one from each row
        for (int i = 0; i < n; ++i) {
            double c1 = 0;
            for (int j = 0; j < n; ++j) {
                double c0 = Math.abs(a[i][j]);
                if (c0 > c1) c1 = c0;
            }
            c[i] = c1;
        }

        // Search the pivoting element from each column
        int k = 0;
        for (int j = 0; j < n - 1; ++j) {
            double pi1 = 0;
            for (int i = j; i < n; ++i) {
                double pi0 = Math.abs(a[index[i]][j]);
                pi0 /= c[index[i]];
                if (pi0 > pi1) {
                    pi1 = pi0;
                    k = i;
                }
            }

            // Interchange rows according to the pivoting order
            int itmp = index[j];
            index[j] = index[k];
            index[k] = itmp;
            for (int i = j + 1; i < n; ++i) {
                double pj = a[index[i]][j] / a[index[j]][j];

                // Record pivoting ratios below the diagonal
                a[index[i]][j] = pj;

                // Modify other elements accordingly
                for (int l = j + 1; l < n; ++l)
                    a[index[i]][l] -= pj * a[index[j]][l];
            }
        }
    }

    public static double[][] testWrapper(Point tc) {
        int choice = (int) tc.vector[0];  // generate random int b/w 1-6
        if (choice == 1) {
            return addMatrices(tc);
        } else if (choice == 2) {
            return multiplyMatricesConsonant(tc);
        } else if (choice == 3) {
            return multiplyMatrices(tc);
        } else if (choice == 4) {
            return transposeMatrix(tc, new Random().nextInt(5));
        } else if (choice == 5) {
            double[][] resp = new double[1][1];
            resp[0][0] = determinantOfMatrix(tc);
            return resp;
        } else if (choice == 6) {
            return inverseOfMatrix(tc);
        }

        return new double[0][];
    }

    public static void main(String[] args) {
        double[][] bd = {{1, 6}, {-10000, 10000}, {-10000, 10000}, {-10000, 10000}, {-10000, 10000}, {-10000, 10000}, {-10000, 10000}, {-10000, 10000}, {-10000, 10000}, {-10000, 10000}, {-10000, 10000}, {-10000, 10000}, {-10000, 10000} };
        Point tc = Point.generateRandP(bd, new Random());
        double resp = determinantOfMatrix(tc);
        System.out.println(resp);

    }
}
