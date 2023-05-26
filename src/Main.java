import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.DoublePredicate;
import java.util.function.DoubleUnaryOperator;

public class Main {

    public static void main(String[] args) {
        DoubleUnaryOperator[] funcs = {
                Math::cos,
                i -> 2 * Math.sqrt(Math.abs(i - 1)) + 1,
                i -> -Math.pow(i / Math.PI, 2) - 2 * i + 5 * Math.PI,
                i -> {
                    double y = 0;
                    for (int k = 1; k <= 100; k++) {
                        double x = i + k * Math.PI;
                        if (x > 2 * Math.PI) {
                            break;
                        }
                        y += Math.pow(x / (Math.PI * k) - 1, 2);
                    }
                    return y;
                },
                i -> i < 0 ? 1.0 / 4.0 * Math.pow(Math.sin(i), 2) + 1 : 1.0 / 2.0 * Math.cos(i) - 1
        };

        System.out.println("(1) Y = cosX = \n");
        printFunctionResults(funcs[0]);

        System.out.println("----------------------------------------");

        System.out.println("\n(2) 2*sqrt(abs(X- 1)) + 1 = \n");
        printFunctionResults(funcs[1]);

        System.out.println("----------------------------------------");

        System.out.println("\n(3) -(X/pi)^2 -2*x + 5*pi = \n");
        printFunctionResults(funcs[2]);

        System.out.println("----------------------------------------");

        System.out.println("\n(4) (X/pi*k - 1)^2 , For k=1 to k=100 : \n");
        printFunctionResults(funcs[3]);

        System.out.println("----------------------------------------");

        System.out.println("\n(5) Y = 1/4sin^2*X + 1 if x < 0, else then y = 1/2cosX - 1 : \n");
        printFunctionResults(funcs[4]);

        System.out.println("----------------------------------------");

        System.out.println("\n----------- TASK 2 -----------\n");

        List<Double> points = generateRandomPoints(10);
        System.out.println("RANDOM ARRAY : " + points);

        List<List<Double>> results = calculateFunctionResults(funcs, points);

        int negativeNumbers = countNumbers(results, value -> value < 0);
        int inRangeNumbers = countNumbers(results, value -> value > -1 && value < 1);

        System.out.println("\nNegative numbers count : " + negativeNumbers);
        System.out.println("In range [-1,1] numbers count: " + inRangeNumbers);

        List<List<Double>> arrangedResults = arrangeResults(results);

        System.out.println("\nArranged array :");
        for (List<Double> array : arrangedResults) {
            System.out.println(array);
        }

        System.out.println("-----------------------------------------------------------------------");
    }

    public static void printFunctionResults(DoubleUnaryOperator func) {
        for (double i = -2 * Math.PI; i <= 2 * Math.PI; i += Math.PI / 6) {
            System.out.println(func.applyAsDouble(i));
        }
    }

    public static List<List<Double>> calculateFunctionResults(DoubleUnaryOperator[] funcs, List<Double> points) {
        List<List<Double>> results = new ArrayList<>();
        for (double point : points) {
            List<Double> pointResults = new ArrayList<>();
            for (DoubleUnaryOperator func : funcs) {
                pointResults.add(func.applyAsDouble(point));
            }
            results.add(pointResults);
        }
        return results;
    }

    public static int countNumbers(List<List<Double>> results, DoublePredicate predicate) {
        int count = 0;
        for (List<Double> pointResults : results) {
            for (Double value : pointResults) {
                if (predicate.test(value)) {
                    count++;
                }
            }
        }
        return count;
    }

    public static List<List<Double>> arrangeResults(List<List<Double>> results) {
        List<List<Double>> arrangedResults = new ArrayList<>();
// Find the maximum size of a sublist
        int maxSize = 0;
        for (List<Double> sublist : results) {
            maxSize = Math.max(maxSize, sublist.size());
        }

        // Create a new list for each function and add its values to the list
        for (int i = 0; i < maxSize; i++) {
            List<Double> functionResults = new ArrayList<>();
            for (List<Double> sublist : results) {
                if (i < sublist.size()) {
                    functionResults.add(sublist.get(i));
                }
            }
            arrangedResults.add(functionResults);
        }

        return arrangedResults;
    }

    public static List<Double> generateRandomPoints(int n) {
        Random rnd = new Random();
        List<Double> points = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            double point = rnd.nextDouble() * 20 - 10;
            points.add(point);
        }
        return points;
    }
}