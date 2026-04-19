
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class QuestionsExtraction {

    public static void main(String[] args) throws IOException {
        File file = new File("MultipleChoice.txt");
        Scanner sc;
        try (Scanner FR = new Scanner(file)) {
            String[] questions = new String[30];
            String[] optionA = new String[30];
            String[] optionB = new String[30];
            String[] optionC = new String[30];
            String[] optionD = new String[30];
            String[] answers = new String[30];
            char[] correctAnswer = new char[30];
            int counter = 0;
            int charIndex = 0;
            sc = new Scanner(System.in);
            while (FR.hasNextLine()) {
                questions[counter] = FR.nextLine();
                optionA[counter] = FR.nextLine();
                optionB[counter] = FR.nextLine();
                optionC[counter] = FR.nextLine();
                optionD[counter] = FR.nextLine();
                answers[counter] = FR.nextLine();
                for (int i = 0; i < answers.length; i++) {
                    // Use the built-in String.indexOf() method to find the character
                    charIndex = answers[i].indexOf(16);
                    counter++;
                    sc.hasNextLine();
                }
                for (int i = 0; i < counter; i++) {
                    System.out.println(
                            i + 1 + " " + questions[i] + "\n" + optionA[i] + "\n" + optionB[i] + "\n" + optionC[i]
                                    + "\n" + optionD[i] + "\n" + charIndex);
                }
            }
            sc.close();
        }
    }
}
