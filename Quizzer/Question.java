public class Question {
    String promptQuestions;
    String[] options;
    char answer;
    boolean isAnswered;

    public Question(String promptQuestions, String[] options, char answer) {
        this.promptQuestions = promptQuestions;
        this.options = options;
        this.answer = answer;
        this.isAnswered = false;
    }
}