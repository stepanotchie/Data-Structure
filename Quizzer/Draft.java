public class Draft {
    /* public static void playGame() {
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
                savePlayerLocally(playerName, playerPassword, score, 30);
            }
        }
    }
     */


     /* public static void playGame() {
    boolean inGame = true;
    while (inGame) {
        Question q = quizList.get(index);
        System.out.println("\nQuestion " + (index + 1) + ". " + q.promptQuestions);
        for (String opt : q.options) System.out.println(opt);

        // Check completion status
        boolean allAnswered = quizList.stream().allMatch(qu -> qu.isAnswered);

        // Submenu: Added option 4 to Finish/Exit
        System.out.println(q.isAnswered ? "\n[!] Already answered." : "");
        System.out.println("| [1] Answer | [2] Next | [3] Previous |" + (allAnswered ? " [4] Finish & Save |" : " [4] Exit to Menu |"));
        
        int choice = new InputValidator<Integer>().getValidInput("Selection ", Integer::parseInt, i -> i >= 1 && i <= 4);

        if (choice == 1 && !q.isAnswered) {
            if (answerQuestion(q)) score++;
            q.isAnswered = true;
        } else if (choice == 4) {
            if (allAnswered) {
                savePlayerLocally(playerName, playerPassword, score, quizList.size());
                System.out.println("Quiz finished! Results saved.");
                inGame = false;
            } else {
                System.out.println("[!] Please answer all questions before finishing.");
            }
        } else {
            handleNavigation(choice);
        }
    }
}
      */
}
