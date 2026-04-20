
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class QuizzerGame {

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Question> quizList = new ArrayList<>();
    static int index = 0;
    static String playerName = " ";
    static String playerPassword = " ";
    static int score = 0;
    static char playerChoice;
    static boolean[] isCorrect = new boolean[30];

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            menu();
            int choice = menuInput();
            switch (choice) {
                case 1:
                    playerRegistration();
                    break;
                case 2:
                    // Check if player has registered first
                    if (playerName.equals(" ")) {
                        System.out.println("\n[!] Please register first (Option 1).");
                    } else {
                        try {
                            // Clear previous questions if any and read the file
                            quizList.clear();
                            readFile();
                            index = 0; // Reset index for a new game
                            score = 0; // Reset score for a new game
                            playGame();
                        } catch (FileNotFoundException e) {
                            System.out.println("[!] MultipleChoice.txt not found!");
                        }
                    }
                    break;
                case 3:
                    System.out.println("\nExiting program. Goodbye!");
                    running = false;
                    break;
            }
        }
    }

    public static void menu() {
        System.out.println("""
                ┌──────────────────────────────────────┐
                │           TRIVIA MACHINE             │
                ├──────────────────────────────────────┤
                │        [1] Register / Log-in         │
                │        [2] Play Game                 │
                │        [3] Exit                      │
                └──────────────────────────────────────┘""");
    }

    public static int menuInput() {
        String prompt = "      Selection ";
        // Input validation
        InputValidator<Integer> menuChoice = new InputValidator<>();
        return menuChoice.getValidInput(
                prompt, Integer::parseInt,
                i -> i >= 1 && i <= 3);
    }

    public static void readFile() throws FileNotFoundException {

        // Read txt and then iterate the questions
        try {
            BufferedReader br = new BufferedReader(new FileReader("MultipleChoice.txt"));
            String line;
            while (((line = br.readLine()) != null)) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                String promptQuestions = line; // read the question in the txt file

                // this will iterate the choices a, b, c, d
                String[] options = new String[4];
                for (int i = 0; i < 4; i++) {
                    options[i] = br.readLine();
                }

                // read the answer key line in the text file
                String answerLine = br.readLine();
                char correctAnswer = ' ';

                if (answerLine != null && answerLine.contains("Correct Answer")) {
                    String[] parts = answerLine.split(":");
                    // Check if there is actually something after the colon to avoid crashes
                    if (parts.length >= 2 && !parts[1].trim().isEmpty()) {
                        correctAnswer = parts[1].trim().toLowerCase().charAt(0);
                    } else {
                        System.out.println("[!] Warning: No answer key found for this question.");
                    }
                }
                quizList.add(new Question(promptQuestions, options, correctAnswer));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void playGame() {
        while (index >= 0 && index < quizList.size()) {
            // Print the question and options
            Question q = quizList.get(index);

            System.out.println("\nQuestion " + (index + 1) + ". " + q.promptQuestions);
            for (String opt : q.options) {
                System.out.println(opt);
            }

            String subMenu_1 = """
                    ┌──────────────────────────────────────┐
                    │           TRIVIA MACHINE             │
                    ├──────────────────────────────────────┤
                    │        [2] Next Question             │
                    │        [3] Previous Question         │
                    └──────────────────────────────────────┘
                    """;

            String subMenu = """
                    ┌──────────────────────────────────────┐
                    │           TRIVIA MACHINE             │
                    ├──────────────────────────────────────┤
                    │        [1] Answer                    │
                    │        [2] Next Question             │
                    │        [3] Previous Question         │
                    └──────────────────────────────────────┘""";

            if (q.isAnswered) {
                System.out.println("[!] You have already answered this question!");
                System.out.println(isCorrect[index] == true ? "Correct" : "Wrong");
                System.out.println(subMenu_1);
                InputValidator<Integer> subMenuValidator_1 = new InputValidator<>();
                int choice = subMenuValidator_1.getValidInput(
                        "Selection ",
                        Integer::parseInt,
                        i -> i >= 2 && i <= 3);
                switch (choice) {
                    case 2:
                        // Move forward if not at the last question
                        if (index < quizList.size() - 1) {
                            index++;
                        } else  {
                            System.out.println("\n[!] This is the last question.");
                            return;
                        }
                        break;

                    case 3:
                        // Move backward if not at the first question
                        if (index > 0) {
                            index--;
                        } else {
                            System.out.println("\n[!] You are already at the first question.");
                        }
                        break;

                }
                System.out.println("Final Score: " + score + "/30");
            } else {
                System.out.println(subMenu);
                InputValidator<Integer> subMenuValidator = new InputValidator<>();
                int choice = subMenuValidator.getValidInput(
                        "Selection ",
                        Integer::parseInt,
                        i -> i >= 1 && i <= 3);
                switch (choice) {
                    case 1:
                        if (answerQuestion(q)) {
                            score++;
                        }
                        q.isAnswered = true; // Mark so it can't be answered again
                        index++;
                        break;
                    case 2:
                        // Move forward if not at the last question
                        if (index < quizList.size() - 1) {
                            index++;
                        } else {
                            System.out.println("\n[!] This is the last question.");
                            index = quizList.size(); // breaks the while loop
                        }
                        break;

                    case 3:
                        // Move backward if not at the first question
                        if (index > 0) {
                            index--;
                        } else {
                            System.out.println("\n[!] You are already at the first question.");
                        }
                        break;

                }
                System.out.println("Final Score: " + score + "/30");
            }
        }
    }

    public static boolean answerQuestion(Question q) {
        InputValidator<Character> validator = new InputValidator<>();
        // GENERIC CLASS FOR INPUT VALIDATION
        playerChoice = validator.getValidInput(
                "Answer ",
                input -> {
                    // Parser: protect against empty input
                    String s = input.trim().toLowerCase();
                    return s.isEmpty() ? ' ' : s.charAt(0);
                },
                val -> val >= 'a' && val <= 'd' // Predicate: Rule for valid input
        );

        if (playerChoice == q.answer) {
            System.out.println("Correct!");
            isCorrect[index] = true;
            return true;
        } else {
            System.out.println("Incorrect. The answer was: " + q.answer);
            isCorrect[index] = false;
            return false;
        }
    }

    public static void playerRegistration() {
        System.out.println("\n- - - P L A Y E R   R E G I S T R A T I O N - - -\n");

        InputValidator<String> stringValidator = new InputValidator<>();

        // Ensure Name is not empty
        playerName = stringValidator.getValidInput(
                "        Name    ",
                input -> input.trim(),
                val -> !val.isEmpty() // Predicate checks if string has content
        );

        // Ensure Password is not empty
        playerPassword = stringValidator.getValidInput(
                "        Password ",
                input -> input.trim(),
                val -> !val.isEmpty()
        );

        // Check file for existing records
        if (checkIfExisting(playerName, playerPassword)) {
            System.out.println("\n[!] Account found. Welcome back, " + playerName + "!\n");
        } else {
            savePlayerLocally(playerName, playerPassword, score, quizList.size());
            System.out.println("\n    " + playerName + " has been registered locally.\n");
            System.out.println("         Welcome, " + playerName + "!");
        }
    }

    private static boolean checkIfExisting(String name, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader("PlayerRecords.txt"))) {
            String line;
            String currentName = "";
            String currentPass = "";

            while ((line = br.readLine()) != null) {
                if (line.startsWith("Name: ")) {
                    currentName = line.substring(6).trim();
                } else if (line.startsWith("Password: ")) {
                    currentPass = line.substring(10).trim();

                    // Once we have both name and password from the file block, compare them
                    if (currentName.equalsIgnoreCase(name) && currentPass.equals(password)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            return false; // File doesn't exist yet
        }
        return false;
    }

    public static void savePlayerLocally(String name, String password, int score, int total) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("PlayerRecords.txt", true))) {
            bw.newLine();
            bw.write("--- GAME SESSION ---");
            bw.newLine();
            bw.write("Name: " + name);
            bw.newLine();
            bw.write("Password: " + password);
            bw.newLine();
            bw.write("Score: " + score + "/" + total);
            bw.newLine();
            bw.write("----------------------------------");
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error saving score: " + e.getMessage());
        }
    }
}
