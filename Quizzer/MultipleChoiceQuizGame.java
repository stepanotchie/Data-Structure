import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;



public class MultipleChoiceQuizGame {

    public static final Scanner sc = new Scanner(System.in);
    // Global variables to hold session data
    public static String playerName = " ";
    public static String playerPassword = " ";

    public static void main(String[] args) {
        menu();
        boolean isRunning = true;
        while (isRunning) {
            System.out.print("Enter choice : ");
            int userChoice = choice();
            switch (userChoice) {
                case 1 -> {
                    playerRegistration();
                    menu();
                }
                case 2 -> {
                    playGame();
                    isRunning = false;
                }
                case 3 -> {
                    System.out.println("Game closed...");
                    isRunning = false;
                }
                default -> System.out.println("Invalid input! Please enter a number from 1 to 3.");
            }
        }
    }

    public static void menu() {
        System.out.println("""
                \n================================================
                            WELCOME TO TRIVIA MACHINE

                            [1] Register Player
                            [2] Play Game
                            [3] Exit

                ================================================
                """);
    }

    public static int choice() {
        while (true) {
            if (sc.hasNextInt()) {
                int input = sc.nextInt();
                sc.nextLine();
                return input;
            } else {
                System.out.println("Error: Please enter a numeric value.");
                sc.nextLine();
                System.out.print("Enter choice : ");
            }
        }
    }

    public static void playerRegistration() {
        System.out.println("\n- - - P L A Y E R   R E G I S T R A T I O N - - -");
        System.out.print("\n        Name     : ");
        playerName = sc.nextLine();
        System.out.print("        Password : ");
        playerPassword = sc.nextLine();
        System.out
                .println("\n    " + playerName + " has been registered locally.\n         Welcome " + playerName + "!");
    }

    public static void playGame() {
        ArrayList<Question> quizList = new ArrayList<>();

        // 1. Load data into ArrayList
        try (BufferedReader br = new BufferedReader(new FileReader("MultipleChoice.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty())
                    continue;

                String promptQuestions = line; // read the question in the txt file
                // this will iterate the choices a, b, c, d
                String[] options = new String[4];
                for (int i = 0; i < 4; i++) {
                    options[i] = br.readLine();
                }

                String answerLine = br.readLine(); // reads the answer key line in the text file
                char correctAnswer = ' '; // player answer
                // if there's an answer key in the txt file and it contains the keyword "Correct
                // Answer"
                // then player's answer will be converted to lowercase to avoid case sensitivity
                if (answerLine != null && answerLine.contains("Correct Answer")) {
                    correctAnswer = answerLine.split(":")[1].trim().toLowerCase().charAt(0);
                }
                quizList.add(new Question(promptQuestions, options, correctAnswer)); // loading to arraylist
            }
        } catch (IOException e) {
            System.out.println("Error: Could not read MultipleChoice.txt.");
            return;
        }

        // 2. Play Game
        int score = 0;
        int questionNumber = 1;
        for (Question q : quizList) {
            System.out.println("\n" + questionNumber++ + ". " + q.promptQuestions);
            for (String opt : q.options) {
                if (opt != null)
                    System.out.println(opt.trim());
            }
            System.out.println("\nLetter only or else it will be marked as wrong");
            System.out.print("Answer (a, b, c, or d): ");
            String input = sc.nextLine().toLowerCase();

            // 1. Check if length is exactly 1
            // 2. Check if that character is within the allowed set
            
            // Check for valid input format
            if (input.length() != 1 || !input.matches("[abcd]")) {
                System.out.println("Not following instructions. \nWRONG.");
                System.out.println("The correct answer was: " + q.answer);
            }
            // If valid, check if the answer is actually correct
            else if (input.charAt(0) == q.answer) {
                System.out.println("CORRECT!");
                score++;
            }
            // If valid but wrong letter
            else {
                System.out.println("WRONG. The correct answer was: " + q.answer);
            }
        }

        // 3. Final Summary & File Saving
        System.out.println("\nFinal Score: " + score + " / " + quizList.size());
        savePlayerRecord(playerName, playerPassword, score, quizList.size());
    }

    /**
     * Appends player data to PlayerRecords.txt
     */
    public static void savePlayerRecord(String name, String password, int score, int total) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("PlayerRecords.txt", true))) {
            writer.write("Name: " + name);
            writer.newLine();
            writer.write("Password: " + password);
            writer.newLine();
            writer.write("Score: " + score + "/" + total);
            writer.newLine();
            writer.write("\n");
            writer.newLine();
            System.out.println("Record successfully saved to PlayerRecords.txt!");
        } catch (IOException e) {
            System.out.println("Error: Could not save player record.");
        }
    }
}