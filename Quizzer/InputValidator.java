import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;

// This is a generic class to validate user input

public class InputValidator<T> {
    private final Scanner sc = new Scanner(System.in);
    
    /* T is a data type for fields, parameters, and return types 
    * HOW THE VALIDATION WORKS:
    * 1. String prompt parameter is the question to ask the user
    * 2. Function<String, T> parser parameter is a function that converts a String to a T
    * String is the input and T is the data type output
    * 3. Predicate<T> isValid is the condition that the input must meet*/
    
    public T getValidInput(String prompt, Function<String, T> parser, Predicate<T> isValid){
        while(true){
            try {
                System.out.print(prompt + ": ");
                String rawInput = sc.nextLine();
                T parsedValue = parser.apply(rawInput);
                
                if (isValid.test(parsedValue)) {
                    return parsedValue;
                } else {
                    System.out.println("Invalid input. Please try again.");
                }
            } catch (Exception e){
                System.out.println("Invalid format. Please enter the correct data type.");
            }
        }
    }
}
