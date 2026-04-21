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
        return menuChoice.getValidInput(prompt, Integer::parseInt, i -> i >= 1 && i <= 3);
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
        boolean isPlaying = true;
        while (isPlaying) {
            Question q = quizList.get(index);
            System.out.println("\nQuestion " + (index + 1) + ". " + q.promptQuestions);
            for (String opt : q.options) {
                System.out.println(opt);
            }

            // 1. Display Menu and get selection
            int choice = getSubMenuChoice(q.isAnswered);

            if (choice == 4) {
                // 1. Get the list of skipped questions
                ArrayList<Integer> skipped = getSkippedQuestions();
                
                // Check if every question has been answered
                if (skipped.isEmpty()) {
                    System.out.println("\n[✔] Quiz submitted successfully!");

                    // Save the session to the file (appending, not overwriting)
                    savePlayerLocally(playerName, playerPassword, score, quizList.size());

                    isPlaying = false; // Exit the loop to return to menu
                    
                } else {
                    // Tell the player exactly what they missed
                    System.out.print("\n[!] UNABLE TO SUBMIT: You have not answered question(s): ");
                    for (int i = 0; i < skipped.size(); i++) {
                        System.out.print(skipped.get(i) + (i == skipped.size() - 1 ? "" : ", "));
                    }
                    System.out.println("\nPlease go back and complete them before submitting.");
                }
            } else {
                // Handle standard navigation and answering
                if (q.isAnswered) {
                    handleAnsweredNavigation(choice);
                } else {
                    handleUnansweredSelection(choice, q);
                }
            }
            System.out.println("Final Score: " + score + "/" + quizList.size());
        }
    }

    public static int getSubMenuChoice(boolean isAnswered) {
        String isCorrectAnswer = isCorrect[index] ? "It's Correct" : "Sorry, it's Incorrect";
        String menu = isAnswered ? "You have already answered this question!\n" + isCorrectAnswer + "\n| [2] Next | [3] Previous | [4] Submit |" : "| [1] Answer | [2] Next | [3] Previous | [4] Submit |";
        System.out.println("\n" + menu);

        int minChoice = isAnswered ? 2 : 1;

        return new InputValidator<Integer>().getValidInput("Selection ", Integer::parseInt, i -> i >= minChoice && i <= 4);
    }

    public static ArrayList<Integer> getSkippedQuestions() {
        ArrayList<Integer> skipped = new ArrayList<>();
        for (int i = 0; i < quizList.size(); i++) {
            if (!quizList.get(i).isAnswered) {
                skipped.add(i + 1); // Store the human-readable number (1-based)
            }
        }
        return skipped;
    }
    
    public static void handleAnsweredNavigation(int choice) {
        if (choice == 2) {
            if (index < quizList.size() - 1) {
                index++;
            } else {
                System.out.println("\n[!] This is the last question.");
            }
        } else if (choice == 3) {
            if (index > 0) {
                index--;
            } else {
                System.out.println("\n[!] You are already at the first question.");
            }
        }
    }

    public static boolean areAllQuestionsAnswered() {
        return getSkippedQuestions().isEmpty();
    }

    public static void handleUnansweredSelection(int choice, Question q) {
        if (choice == 1) {
            if (answerQuestion(q)) {
                score++;
            }
            q.isAnswered = true;
            if (index < quizList.size() - 1) {
                index++;
            } else {
                System.out.println("\n[!] You have reached the last question. Use [4] to Submit.");
            }
        } else {
            handleAnsweredNavigation(choice);
        }
    }

    public static boolean answerQuestion(Question q) {
        InputValidator<Character> validator = new InputValidator<>();
        // GENERIC CLASS FOR INPUT VALIDATION
        playerChoice = validator.getValidInput("Answer ", input -> {
                    // Parser: protect against empty input
                    String s = input.trim().toLowerCase();
                    return (s.length() == 1) ? s.charAt(0) : ' ';
                }, val -> val >= 'a' && val <= 'd' // Predicate: Rule for valid input
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
        playerName = stringValidator.getValidInput("        Name    ", input -> input.trim(), val -> !val.isEmpty() // Predicate checks if string has content
        );

        // Ensure Password is not empty
        playerPassword = stringValidator.getValidInput("        Password ", input -> input.trim(), val -> !val.isEmpty());

        // Check file for existing records
        if (checkIfExisting(playerName, playerPassword)) {
            System.out.println("\n[!] Account found. Welcome back, " + playerName + "!\n");
        } else {
            System.out.println("\n    " + playerName + " has been registered locally.\n");
            System.out.println("         Welcome, " + playerName + "!");
        }
    }

    public static boolean checkIfExisting(String name, String password) {
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
        File file = new File("PlayerRecords.txt");
        ArrayList<String> lines = new ArrayList<>();
        boolean updated = false;

        // 1. Read existing data
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) lines.add(line);
            } catch (IOException e) { return; }
        }

        // 2. Search and Update
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).equals("Name: " + name.trim())) {
                if (i + 1 < lines.size() && lines.get(i + 1).equals("Password: " + password.trim())) {
                    lines.set(i + 2, "Score: " + score + "/" + total);
                    updated = true;
                    break;
                }
            }
        }

        // 3. Append if new
        if (!updated) {
            lines.add("--- PLAYER RECORD ---");
            lines.add("Name: " + name.trim());
            lines.add("Password: " + password.trim());
            lines.add("Score: " + score + "/" + total);
            lines.add("----------------------------------");
        }

        // 4. Overwrite file with updated content
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            for (String l : lines) { bw.write(l); bw.newLine(); }
        } catch (IOException e) { System.out.println("Error saving."); }
    }
}
