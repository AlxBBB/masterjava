package ru.javaops.masterjava.matrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

/**
 * gkislin
 * 03.07.2016
 */
public class MatrixUtil {

  private static final int CALC_THREAD = 10;

  // TODO implement parallel multiplication matrixA*matrixB
  public static int[][] concurrentMultiply(int[][] matrixA, int[][] matrixB,
      ExecutorService executor) throws InterruptedException, ExecutionException {
    final int matrixSize = matrixA.length;
    int[][] matrixC = new int[matrixSize][matrixSize];
    int[][] matrixT = new int[matrixSize][matrixSize];

    class CalcIter implements Callable<Boolean> {

      private int iter;

      private CalcIter(int iter) {
        this.iter = iter;
      }

      @Override
      public Boolean call() {
        int[] row = matrixA[iter];
        for (int i = 0; i < matrixSize; i++) {
          int sum = 0;
          int[] column = matrixT[i];
          for (int j = 0; j < matrixSize; j++) {
            sum += row[j] * column[j];
          }
          matrixC[iter][i] = sum;
        }
        return true;
      }
    }

    List<Callable<Boolean>> tasks = new ArrayList<>();
    for (int i = 0; i < matrixSize; i++) {
      for (int j = 0; j < matrixSize; j++) {
        matrixT[j][i] = matrixB[i][j];
      }
      tasks.add(new CalcIter(i));
    }
    executor.invokeAll(tasks);
    return matrixC;
  }


  // TODO optimize by https://habrahabr.ru/post/114797/
  public static int[][] singleThreadMultiply(int[][] matrixA, int[][] matrixB) {
    final int matrixSize = matrixA.length;
    int[][] matrixC = new int[matrixSize][matrixSize];
    int[] matrixColumn = new int[matrixSize];

    try {
      for (int i = 0; ; i++) { // i - столбец
        for (int j = 0; j < matrixSize; j++) {
          matrixColumn[j] = matrixB[j][i];
        }
        for (int j = 0; j < matrixSize; j++) { //  j - строка
          int sum = 0;
          int[] matrixRow = matrixA[j];
          for (int k = 0; k < matrixSize; k++) {
            sum += matrixRow[k] * matrixColumn[k];
          }
          matrixC[j][i] = sum;
        }
      }
    } catch (IndexOutOfBoundsException ignored) {
    }
    return matrixC;
  }

  public static int[][] create(int size) {
    int[][] matrix = new int[size][size];
    Random rn = new Random();

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        matrix[i][j] = rn.nextInt(10);
      }
    }
    return matrix;
  }

  public static boolean compare(int[][] matrixA, int[][] matrixB) {
    final int matrixSize = matrixA.length;
    for (int i = 0; i < matrixSize; i++) {
      for (int j = 0; j < matrixSize; j++) {
        if (matrixA[i][j] != matrixB[i][j]) {
          return false;
        }
      }
    }
    return true;
  }
}
