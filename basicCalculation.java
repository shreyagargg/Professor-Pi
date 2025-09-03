import java.util.Scanner;
public class basicCalculation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // int a = scanner.nextInt();
        // int b = scanner.nextInt();

        System.out.println("Ask me anything => \n");
        String query = scanner.next();

        // sum(a,b);
        // diff(a,b);
        // prod(a,b);
        // div(a,b);

        scanner.close();

    }

    public static int sum(int a, int b){
        int sum = a + b;
        return sum;
    }
    public static int diff(int a, int b){
        int diff = a - b;
        return diff;
    }
    public static int prod(int a, int b){
        int prod = a * b;
        return prod;
    }
    public static int div(int a, int b){
        if (b == 0) {

            return -1;
        }
        int div = a / b;
        return div;
    }
}