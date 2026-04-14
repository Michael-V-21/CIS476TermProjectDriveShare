package driveshare.patterns.chain;

// Concrete handler that validates question

public class Question2Handler extends SecurityQuestionHandler {
    private String correctAnswer;

    public Question2Handler(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean handle(String[] answers, int index) {
        if (!correctAnswer.equalsIgnoreCase(answers[index])) {
            return false;
        }
        return nextHandler == null || nextHandler.handle(answers, index + 1);
    }
}