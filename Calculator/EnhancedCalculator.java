import java.util.Scanner;

// ---------------- CALCULATOR CLASS ----------------
class Calculator {

    // --------------------------- BASIC ----------------------------------
    public double add(double a, double b) { return a + b; }
    public double sub(double a, double b) { return a - b; }
    public double mul(double a, double b) { return a * b; }

    public double div(double a, double b) {
        if (b == 0) throw new ArithmeticException("Divide by zero!");
        return a / b;
    }

    public double mod(double a, double b) { return a % b; }

    //----------------------- SCIENTIFIC --------------------------------
    public double sin(double a) { return Math.sin(Math.toRadians(a)); }
    public double cos(double a) { return Math.cos(Math.toRadians(a)); }
    public double tan(double a) { return Math.tan(Math.toRadians(a)); }
    public double log(double a) { return Math.log10(a); }
    public double ln(double a) { return Math.log(a); }
    public double sqrt(double a) { return Math.sqrt(a); }
    public double power(double a, double b) { return Math.pow(a, b); }

    public long fact(int n) {
        if (n < 0) throw new ArithmeticException("Negative factorial!");
        long f = 1;
        for (int i = 1; i <= n; i++) f *= i;
        return f;
    }
}

// ---------------- UNIT CONVERTER ----------------
class UnitConverter {

    public double meterToKm(double m) { return m / 1000; }
    public double kmToMeter(double km) { return km * 1000; }

    public double sqmToSqft(double sqm) { return sqm * 10.764; }

    public double literToMl(double l) { return l * 1000; }

    public double kgToGram(double kg) { return kg * 1000; }

    public double cToF(double c) { return (c * 9/5) + 32; }
    public double fToC(double f) { return (f - 32) * 5/9; }

    public double kmhToMs(double kmh) { return kmh * 0.2778; }

    public double atmToPascal(double atm) { return atm * 101325; }

    public double wattToKw(double w) { return w / 1000; }

    public double inrToUsd(double inr) { return inr / 83; }
}

// ---------------- MAIN ----------------
public class EnhancedCalculator {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        Calculator calc = new Calculator();
        UnitConverter conv = new UnitConverter();

        while (true) {
            System.out.println("\n===== MAIN MENU =====");
            System.out.println("1. Calculator");
            System.out.println("2. Unit Converter");
            System.out.println("0. Exit");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    calculatorMenu(calc);
                    break;

                case 2:
                    unitMenu(conv);
                    break;

                case 0:
                    System.out.println("Thank you!");
                    System.exit(0);
            }
        }
    }

    // ---------------- CALCULATOR MENU ----------------
    static void calculatorMenu(Calculator calc) {

        boolean running = true;

        System.out.print("Enter initial number: ");
        double result = sc.nextDouble();

        while (running) {

            System.out.println("\nCurrent Result: " + result);
            System.out.println("1. Basic Operations");
            System.out.println("2. Scientific Operations");
            System.out.println("0. Exit Calculator");

            int choice = sc.nextInt();

            try {

                switch (choice) {

                    //  ----------------------------------- BASIC ----------------------------------
                    case 1:
                        System.out.println("Choose: +  -  *  /  %");
                        String op = sc.next();
                        double num = sc.nextDouble();
                        switch (op) {
                            case "+": result = calc.add(result, num); break;
                            case "-": result = calc.sub(result, num); break;
                            case "*": result = calc.mul(result, num); break;
                            case "/": result = calc.div(result, num); break;
                            case "%": result = calc.mod(result, num); break;
                            default: System.out.println("Invalid operation");
                        }
                        break;

                    // ----------------------------------- SCIENTIFIC ---------------------------------------
                    case 2:
                        System.out.println("1.sin 2.cos 3.tan 4.log 5.ln");
                        System.out.println("6.sqrt 7.power 8.factorial");
                        int sci = sc.nextInt();
                        switch (sci) {
                            case 1: result = calc.sin(result); break;
                            case 2: result = calc.cos(result); break;
                            case 3: result = calc.tan(result); break;
                            case 4: result = calc.log(result); break;
                            case 5: result = calc.ln(result); break;
                            case 6: result = calc.sqrt(result); break;
                            case 7:
                                System.out.print("Enter exponent: ");
                                result = calc.power(result, sc.nextDouble());
                                break;
                            case 8:
                                result = calc.fact((int) result);
                                break;
                            default:
                                System.out.println("Invalid choice");
                        }
                        break;
                    case 0:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // ---------------- UNIT CONVERTER MENU ----------------
    static void unitMenu(UnitConverter conv) {

        System.out.println("\n===== UNIT CONVERTER =====");
        System.out.println("1. Length");
        System.out.println("2. Area");
        System.out.println("3. Volume");
        System.out.println("4. Weight");
        System.out.println("5. Temperature");
        System.out.println("6. Speed");
        System.out.println("7. Pressure");
        System.out.println("8. Power");
        System.out.println("9. Currency");
        int u = sc.nextInt();
        switch (u) {
            case 1:
                System.out.println("1. Meter → Km");
                System.out.println("2. Km → Meter");
                int l = sc.nextInt();
                System.out.print("Enter value: ");
                double len = sc.nextDouble();
                if (l == 1)
                    System.out.println("Result: " + conv.meterToKm(len) + " km");
                else if (l == 2)
                    System.out.println("Result: " + conv.kmToMeter(len) + " meter");
                break;
            case 2:
                System.out.println("1. Sqm → Sqft");
                int a = sc.nextInt();
                System.out.print("Enter value: ");
                double area = sc.nextDouble();
                if (a == 1)
                    System.out.println("Result: " + conv.sqmToSqft(area) + " sqft");
                break;
            case 3:
                System.out.println("1. Liter → ML");
                int v = sc.nextInt();
                System.out.print("Enter value: ");
                double vol = sc.nextDouble();
                if (v == 1)
                    System.out.println("Result: " + conv.literToMl(vol) + " ml");
                break;
            case 4:
                System.out.println("1. Kg → Gram");
                int w = sc.nextInt();
                System.out.print("Enter value: ");
                double weight = sc.nextDouble();
                if (w == 1)
                    System.out.println("Result: " + conv.kgToGram(weight) + " g");
                break;
            case 5:
                System.out.println("1. Celsius → Fahrenheit");
                System.out.println("2. Fahrenheit → Celsius");
                int t = sc.nextInt();
                System.out.print("Enter value: ");
                double temp = sc.nextDouble();
                if (t == 1)
                    System.out.println("Result: " + conv.cToF(temp) + " F");
                else if (t == 2)
                    System.out.println("Result: " + conv.fToC(temp) + " C");
                break;
            case 6:
                System.out.println("1. Km/h → m/s");
                int s = sc.nextInt();
                System.out.print("Enter value: ");
                double speed = sc.nextDouble();
                if (s == 1)
                    System.out.println("Result: " + conv.kmhToMs(speed) + " m/s");
                break;
            case 7:
                System.out.println("1. ATM → Pascal");
                int p = sc.nextInt();
                System.out.print("Enter value: ");
                double pres = sc.nextDouble();
                if (p == 1)
                    System.out.println("Result: " + conv.atmToPascal(pres) + " Pa");
                break;
            case 8:
                System.out.println("1. Watt → KW");
                int po = sc.nextInt();
                System.out.print("Enter value: ");
                double pow = sc.nextDouble();
                if (po == 1)
                    System.out.println("Result: " + conv.wattToKw(pow) + " kW");
                break;
            case 9:
                System.out.println("1. INR → USD");
                int c = sc.nextInt();
                System.out.print("Enter value: ");
                double money = sc.nextDouble();
                if (c == 1)
                    System.out.println("Result: $" + conv.inrToUsd(money));
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }
}