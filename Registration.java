import java.util.Scanner;

public class Registration {
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        String[] strName = new String[100];
        int[] iPassportNum = new int[100];
        String[] strDestination = new String[100];
        int[] iClassType = new int[100];
        double[] dPrice = new double[100];

        int i = 0;
        do {
            System.out.print("""
                         Welcome to Voyage Airlines!
                    "Bridging the Islands, Touching the Sky."

                              Registration Form
                        """);
            System.out.print("Name: ");
            strName[i] = passengerName();
            System.out.print("Passport Number: ");
            iPassportNum[i] = passportID();
            System.out.print("Destination: ");
            strDestination[i] = destination();
            System.out.print("Class Type: ");
            System.out.print("Total Price: ");
            iClassType[i] = planeClass();
        } while (true);
    }

    public static String passengerName() {
        return sc.nextLine();
    }

    public static int passportID() {
        return sc.nextInt();
    }

    public static String destination() {
        return sc.nextLine();
    }

    public static int planeClass() {
        int classNum = sc.nextInt();

        String classType = " ";
        double price = 0;

        switch (classNum) {
            case 1:
                classType = "Economy";
                price = 1500;
                break;
            case 2:
                classType = "Business Class";
                price = 3000;
                break;
            case 3:
                classType = "First Class";
                price = 6000;
                break;
            default:
                System.out.println("Not in the choices");
        }

        return (int) (classNum + price);

    }

}
