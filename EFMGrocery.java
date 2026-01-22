
import java.util.Scanner;

public class EFMGrocery {
    public static final String RESET = "\033[0m";  // Text Reset
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String strProdName, strAnotherP, strCustomer;
        char cCustomer = ' ', cAnotherP = ' ';
        double dQty, dBill, dPrice;
        double dTotal, dPay, dChange = 0;

        do {
            dBill = 0;
            StringBuilder receiptItems = new StringBuilder();
            do {
                System.out.println("\nWelcome to EFM Grocery");
                System.out.print("Input product name: ");
                strProdName = input.nextLine();
                System.out.print("Price: ");
                dPrice = input.nextDouble();
                input.nextLine();
                System.out.print("Quantity: ");
                dQty = input.nextDouble();
                input.nextLine();

                dTotal = dQty * dPrice;
                dBill = dBill + dTotal;

                // Append this item to our receipt string
                receiptItems.append(String.format("%-22s %.2f x %.0f\n", strProdName, dPrice, dQty));

                System.out.print("Another product Y/N: ");
                strAnotherP = input.nextLine();
                cAnotherP = strAnotherP.charAt(0);
            } while ((cAnotherP == 'Y') || (cAnotherP == 'y'));
            System.out.println("Bill: " + dBill);
            System.out.print("Payment: ");
            dPay = input.nextDouble();
            input.nextLine();
            if (dPay >= dBill) {
                dChange = dPay - dBill;
                // Printing the receipt
                System.out.println(WHITE_BOLD_BRIGHT + "\n          EFM Grocery          " + RESET);
                System.out.println("* * * * * * * * * * * * * * * *");
                System.out.println("          CASH RECEIPT         ");
                System.out.println("* * * * * * * * * * * * * * * *");
                System.out.println(WHITE_BOLD_BRIGHT + "Description            Price   " + RESET);
                System.out.print(receiptItems.toString()); // Prints all saved items
                System.out.println("* * * * * * * * * * * * * * * *");
                System.out.printf(WHITE_BOLD_BRIGHT + "TOTAL                  %.2f" + RESET + "\n", dBill);
                System.out.printf("Cash                   %.2f\n", dPay);
                System.out.printf("Change                 %.2f\n", dChange);
                System.out.println("* * * * * * * * * * * * * * * *");
                System.out.println("       THANK YOU FOR SHOPPING      \n");

            } else {
                System.out.println("Money is not enough!");
            }
            System.out.print("Another customer Y/N? ");
            strCustomer = input.nextLine();
            cCustomer = strCustomer.charAt(0);
        } while ((cCustomer == 'Y') || (cCustomer == 'y'));
        System.out.println("Grocery program is terminating...");

        input.close();
    }
}
