package driveshare.patterns.chain;

// Concrete handler that validates question

public class Question1Handler extends SecurityQuestionHandler
{
    private String correctAnswer;

    public Question1Handler(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    // If answer correct pass control to next handler in chain
    // If answer incorrect stop chain and return false
    @Override
    public boolean handle(String[] answers, int index)
    {
        if (!correctAnswer.equalsIgnoreCase(answers[index])) {
            return false;
        }

        return nextHandler == null || nextHandler.handle(answers, index + 1);
    }
}