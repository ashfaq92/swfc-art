package com.github.jelmerk.knn.examples.programs.NumericMatrixProcessor;

import java.util.Scanner;

public class NumericMatrixProcessor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("1. Add matrices");
            System.out.println("2. Multiply matrix to a constant");
            System.out.println("3. Multiply matrices");
            System.out.println("4. Transpose matrix");
            System.out.println("5. Calculate a determinant");
            System.out.println("6. Inverse Matrix");
            System.out.println("0. Exit");
            System.out.print("Your choice: ");
            int choice = scanner.nextInt();
            if (choice == 0) {
                break;
            } else if (choice == 1) {
                addMatrices(scanner);
            } else if (choice == 2) {
                multiplyMatricesConsonant(scanner);
            } else if (choice == 3) {
                multiplyMatrices(scanner);
            }else if (choice == 4){
                transposeMatrix(scanner);
            }else if (choice == 5){
                determinantOfMatrix(scanner);
            }else if (choice == 6){
                inverseOfMatrix(scanner);
            }
            System.out.println();
        }
    }

    public static void addMatrices(Scanner scanner){
        System.out.println("Enter size of first matrix: ");
        int i = scanner.nextInt();
        int j = scanner.nextInt();
        double[][] firstMatrix = new double[i][j];
        System.out.println("Enter matrix: ");
        for (int a = 0; a < i; a++){
            for (int b = 0; b < j; b++){
                firstMatrix[a][b] = scanner.nextDouble();
            }
        }
        System.out.println("Enter size of second matrix: ");
        int m = scanner.nextInt();
        int n = scanner.nextInt();
        if (m != i || n != j){
            System.out.println("Error in the dimension of the matrices");
        }

        double[][] secondMatrix = new double[m][n];
        System.out.println("Enter matrix: ");
        for (int a = 0; a < m; a++){
            for (int b = 0; b < n; b++){
                secondMatrix[a][b] = scanner.nextDouble();
            }
        }

        double[][] additionMatrix = new double[m][n];
        System.out.println("The resulting matrix is: ");
        for (int a = 0; a < m; a++){
            for (int b = 0; b < n; b++){
                additionMatrix[a][b] = firstMatrix[a][b] + secondMatrix[a][b];
                System.out.print(additionMatrix[a][b] + " ");
            }
            System.out.println();
        }
    }


    public static void multiplyMatricesConsonant(Scanner scanner){
        System.out.print("Enter size of first matrix: ");
        int i = scanner.nextInt();
        int j = scanner.nextInt();
        double[][] firstMatrix = new double[i][j];

        System.out.println("Enter matrix: ");
        for (int a = 0; a < i; a++){
            for (int b = 0; b < j; b++){
                firstMatrix[a][b] = scanner.nextDouble();
            }
        }

        System.out.print("Enter the scalar multiple: ");
        int multiple = scanner.nextInt();


        System.out.println("The multiplication result is:");
        for (int a = 0; a < i; a++){
            for (int b = 0; b < j; b++){
                System.out.print((firstMatrix[a][b] * multiple) + " ");
            }
            System.out.println();
        }
    }

    public static void multiplyMatrices(Scanner scanner) {
        System.out.print("Enter size of first matrix: ");

        int i = scanner.nextInt();
        int j = scanner.nextInt();

        double[][] firstMatrix = new double[i][j];
        System.out.println("Enter first matrix:");
        for (int a = 0; a < i; a++) {
            for (int b = 0; b < j; b++) {
                firstMatrix[a][b] = scanner.nextDouble();
            }
        }
        System.out.print("Enter size of second matrix: ");
        int m = scanner.nextInt();
        int n = scanner.nextInt();

        if (j != m) {
            System.out.println("Error in the dimensions of matrices");
            return;
        }
        double[][] secondMatrix = new double[m][n];

        System.out.println("Enter second matrix:");
        for (int a = 0; a < m; a++) {
            for (int b = 0; b < n; b++) {
                secondMatrix[a][b] = scanner.nextDouble();
            }
        }

        //Calling the method multiplyMatrices
        multiplyMatrices(i, n, j, firstMatrix, secondMatrix);

    }

    public static void multiplyMatrices(int i, int n, int j, double[][] firstMatrix, double[][] secondMatrix){
        double[][] multipliedMatrix = new double[i][n];
        for (int a = 0; a < i; a++){
            for (int b = 0; b < n; b++){
                for (int c = 0; c < j; c++){
                    multipliedMatrix[a][b] += firstMatrix[a][c] * secondMatrix[c][b];
                }
            }
        }
        System.out.println("The multiplication result is:");
        for (int a = 0; a < i; a++){
            for (int b = 0; b < n; b++){
                System.out.print(multipliedMatrix[a][b] + " ");
            }
            System.out.println();
        }
    }

    public static void transposeMatrix(Scanner scanner){
        System.out.println("1. Main diagonal");
        System.out.println("2. Side diagonal");
        System.out.println("3. Vertical line");
        System.out.println("4. Horizontal line");
        System.out.print("Your choice: ");
        int choice = scanner.nextInt();
        if (choice == 1){
            transposeMatrixMainDiagonal(scanner);
        }else if (choice == 2){
            sideDiagonal(scanner);
        }else if (choice == 3){
            verticalLine(scanner);
        }else if (choice == 4){
            horizontalLine(scanner);
        }
    }

    public static void transposeMatrixMainDiagonal(Scanner scanner){
        System.out.print("Enter matrix size: ");
        int i = scanner.nextInt();
        int j = scanner.nextInt();

        double[][] matrix = new double[i][j];

        System.out.println("Enter matrix: ");
        for (int a = 0; a < i; a++){
            for (int b = 0; b < j; b++){
                matrix[a][b] = scanner.nextDouble();
            }
        }

        //Transposing Matrix by Main Diagonal will change its dimension from ixj to jxi
        double[][] transposedMatrix = new double[j][i];
        for (int a = 0; a < i; a++){
            for (int b = 0; b < j; b++){
                transposedMatrix[b][a] = matrix[a][b];
            }
        }

        System.out.println("The result is: ");
        for (int a = 0; a < j; a++){
            for (int b = 0; b < i; b++){
                System.out.print(transposedMatrix[a][b]+" ");
            }
            System.out.println();
        }
    }

    public static void sideDiagonal(Scanner scanner){
        System.out.print("Enter matrix size: ");
        int i = scanner.nextInt();
        int j = scanner.nextInt();

        double[][] matrix = new double[i][j];

        System.out.println("Enter matrix: ");
        for (int a = 0; a < i; a++){
            for (int b = 0; b < j; b++){
                matrix[a][b] = scanner.nextDouble();
            }
        }

        double[][] transposedMatrix = new double[j][i];

        int row = 0;
        int col = 0;
        for (int a = j - 1; a >= 0; a--){
            for (int b = i - 1; b >= 0; b--) {
                transposedMatrix[row][col] = matrix[b][a];
                col++;
                //If we reach the limit of col, we reset it to start from 0. We are using this to avoid another for loop
                if (col == i){
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

        System.out.println("The result is: ");
        for (int a = 0; a < j; a++){
            for (int b = 0; b < i; b++){
                System.out.print(transposedMatrix[a][b] + " ");
            }
            System.out.println();
        }
    }

    public static void verticalLine(Scanner scanner){
        System.out.print("Enter matrix size: ");
        int i = scanner.nextInt();
        int j = scanner.nextInt();

        double[][] matrix = new double[i][j];

        System.out.println("Enter matrix:");
        for (int a = 0; a < i; a++){
            for (int b = 0; b < j; b++){
                matrix[a][b] = scanner.nextDouble();
            }
        }

        double[][] transposedMatrix = new double[i][j];

        int col = 0;
        for (int a = 0; a < i; a++){
            for (int b = j - 1; b >= 0; b--){
                transposedMatrix[a][col] = matrix[a][b];
                col++;
                //In this case, if the end of the column (i.e. j) is reached, then we are starting from first.
                if (col == j){
                    col = 0;
                }
            }
        }

        System.out.println("The result is: ");
        for (int a = 0; a < i; a++){
            for (int b =0; b < j; b++){
                System.out.print(transposedMatrix[a][b]+" ");
            }
            System.out.println();
        }
    }

    public static void horizontalLine(Scanner scanner){
        System.out.print("Enter matrix size: ");
        int i = scanner.nextInt();
        int j = scanner.nextInt();

        double[][] matrix = new double[i][j];

        System.out.println("Enter matrix:");
        for (int a = 0; a < i; a++){
            for (int b = 0; b < j; b++){
                matrix[a][b] = scanner.nextDouble();
            }
        }

        int row = 0;
        double [][] transposedMatrix = new double[i][j];
        for (int a = i - 1; a >= 0; a--){
            for (int b = 0; b < j; b++){
                transposedMatrix[row][b] = matrix[a][b];
            }
            //We are starting from the last row in the case of for loop for 'a'. However, we need to assign values to
            //transposed matrix. So, we have introduced 'row' which acts as a counter.
            row++;
        }

        System.out.println("The result is:");
        for (int a = 0; a < i; a++){
            for (int b = 0; b < j; b++){
                System.out.print(transposedMatrix[a][b]+" ");
            }
            System.out.println();
        }
    }

    public static void determinantOfMatrix(Scanner scanner) {
        System.out.print("Enter matrix size: ");
        int i = scanner.nextInt();
        int j = scanner.nextInt();
        double[][] matrix = new double[i][j];

        System.out.println("Enter matrix:");
        for (int a = 0; a < i; a++) {
            for (int b = 0; b < j; b++) {
                matrix[a][b] = scanner.nextDouble();
            }
        }

        double determinant;
        if (i == 2 && j == 2) {
            determinant = (matrix[0][0] * matrix[1][1]) - (matrix[0][1] * matrix[1][0]);
            System.out.println("The result is:");
            System.out.println(determinant);
            return;
        }

        System.out.println("The result is : ");
        System.out.println(determinantOfMatrix(matrix, i));

    }

    public static void getCofactor(double mat[][], double temp[][], int p, int q, int n){
        int a = 0, b = 0;

        for (int row = 0; row < n; row++){
            for (int col = 0; col < n; col++){
                if (row != p && col != q){
                    temp[a][b++] = mat[row][col];

                    if (b == n - 1){
                        b = 0;
                        a++;
                    }
                }
            }
        }

    }

    public static double determinantOfMatrix(double mat[][], int n){
        double D = 0;

        if (n == 1){
            return mat[0][0];
        }

        //To store cofactors
        double temp[][] = new double[n][n];

        //To store sign multiplier
        int sign = 1;

        //Iterate for each element of first row
        for (int f = 0; f < n; f++){
            //Getting cofactor of mat[0][f]
            getCofactor(mat, temp, 0, f, n);
            D += sign * mat[0][f] * determinantOfMatrix(temp, n - 1);

            //terms are to be added with alternate sign
            sign = - sign;
        }

        return D;
    }


    public static void inverseOfMatrix(Scanner scanner) {
        System.out.print("Enter matrix size: ");
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        if (n != m){
            return;
        }
        double a[][] = new double[n][n];
        System.out.println("Enter the elements of matrix: ");
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                a[i][j] = scanner.nextDouble();

        double d[][] = invert(a);

        System.out.println("The inverse is: ");
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                System.out.print(d[i][j] + "  ");
            }
            System.out.println();
        }
//        scanner.close();
    }

    public static double[][] invert(double a[][]) {
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

// Method to carry out the partial-pivoting Gaussian
// elimination.  Here index[] stores pivoting order.

    public static void gaussian(double a[][], int index[]) {
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
}
