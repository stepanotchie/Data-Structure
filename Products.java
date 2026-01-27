import java.util.Scanner;

public class Products {
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Initialize variables
        int iprodId = 0;
        String strprodName = "";
        String strProdDesc = "";
        int iQty = 0;
        double dPrice = 0;
        double dDiscount = 0;

        System.out.println("Please input the following: ");

        // pass the current value and capture the NEW value returned by the method
        iprodId = inputIntId(iprodId);

        // Clearing the buffer after a number input before reading a String
        sc.nextLine();

        strprodName = inputName(strprodName);
        strProdDesc = inputDesc(strProdDesc);

        iQty = inputQty(iQty);
        dPrice = inputPrice(dPrice);
        dDiscount = inputDiscount(dDiscount);

        System.out.printf("""
                \n%d %s
                Priced at %.2f for %d pieces
                Discounted at %.2f
                Subtotal: %.2f
                """, iprodId, strprodName, dPrice, iQty, dDiscount, SubTotal(dPrice, iQty, dDiscount));

        System.out.println(printFinalReceipt(iprodId, strprodName, strProdDesc, dPrice, iQty, dDiscount));
    }

    // Each method takes a parameter and returns the updated value
    public static int inputIntId(int id) { // the inputted value will be stored in the parameter same as the other
                                           // methods
        System.out.print("Product ID: ");
        id = sc.nextInt();
        return id;
    }

    public static String inputName(String name) {
        System.out.print("Name: ");
        name = sc.nextLine();
        return name;
    }

    public static String inputDesc(String desc) {
        System.out.print("Description: ");
        desc = sc.nextLine();
        return desc;
    }

    public static int inputQty(int qty) {
        System.out.print("Quantity: ");
        qty = sc.nextInt();
        return qty;
    }

    public static double inputPrice(double price) {
        System.out.print("Price: ");
        price = sc.nextDouble();
        return price;
    }

    public static double inputDiscount(double discount) {
        System.out.print("Discount: ");
        discount = sc.nextDouble();
        return discount;
    }

    public static double SubTotal(double price, double qty, double discount) {
        return (price * qty) - discount;
    }

    public static String printFinalReceipt(int id, String name, String desc, double price, int qty, double discount) {
        System.out.println("\n--- PRODUCT SUMMARY ---");
        String receipt = String.format("""
                ID:          %d
                Name:        %s
                Description: %s
                Price:       %.2f
                Quantity:    %d
                Discount:    %.2f
                -----------------------
                Subtotal:    %.2f
                """,
                id, name, desc,
                price, qty, discount,
                SubTotal(price, qty, discount));

        return receipt;
    }
}