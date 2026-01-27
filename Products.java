import java.util.Scanner;

public class Products {
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        // Initialize variables
        int iprodId = 0;
        String strprodName = "";
        String strProdDesc = "";
        double dQty = 0;
        double dPrice = 0;
        double dDiscount = 0;

        System.out.println("Please input the following: ");

        // We pass the current value and capture the NEW value returned by the method
        iprodId = inputInt(iprodId);
        
        // Clearing the buffer after a number input before reading a String
        sc.nextLine(); 
        
        strprodName = inputName(strprodName);
        strProdDesc = inputDesc(strProdDesc);
        
        dQty = inputQty(dQty);
        dPrice = inputPrice(dPrice);
        dDiscount = inputDiscount(dDiscount);

        System.out.println("\n--- PRODUCT SUMMARY ---");
        System.out.printf("""
                ID:          %d 
                Name:        %s
                Description: %s
                Price:       %.2f 
                Quantity:    %.2f
                Discount:    %.2f
                -----------------------
                Subtotal:    %.2f
                """, 
                iprodId, strprodName, strProdDesc, 
                dPrice, dQty, dDiscount, 
                calculateSubTotal(dPrice, dQty, dDiscount)); 
    }

    // Each method takes a parameter and returns the updated value
    public static int inputInt(int id) {
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

    public static double inputQty(double qty) {
        System.out.print("Quantity: ");
        qty = sc.nextDouble();
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

    public static double calculateSubTotal(double price, double qty, double discount) {
        return (price * qty) - discount;
    }
}


/*
System.out.printf("""
                %d %s
                Priced at %.2f for %.2f
                Discounted at %.2f
                Subtotal: %.2f
                """, iprodId, strprodName, dPrice, dQty, dDiscount, SubTotal(dPrice, dQty, dDiscount)); 
        
    }
 */