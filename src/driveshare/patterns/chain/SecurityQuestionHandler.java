package driveshare.patterns.chain;

// Abstract handler -- CHAIN OF RESPONSIBILITIES PATTERN
// Handler checks security question and passes control to next handler

public abstract class SecurityQuestionHandler
{
    // Next handler in chain
    protected SecurityQuestionHandler nextHandler;

    public void setNextHandler(SecurityQuestionHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    // Handle validation of specific questions
    public abstract boolean handle(String[] answers, int index);
}