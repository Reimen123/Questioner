package org.main;

public class Answers {

    private String category;
    private String firstAnswer;
    private String secondAnswer;
    private String thirdAnswer;
    private String fourthAnswer;
    private String correctAnswer;
    private String description;
    private String explanation;

/*    public Answers(String category, String firstAnswer, String secondAnswer, String thirdAnswer, String fourthAnswer, String correctAnswer, String correctDescription, String explanation) {
        this.category = category;
        this.firstAnswer = firstAnswer;
        this.secondAnswer = secondAnswer;
        this.thirdAnswer = thirdAnswer;
        this.fourthAnswer = fourthAnswer;
        this.correctAnswer = correctAnswer;
        this.correctDescription = correctDescription;
        this.explanation = explanation;
    }*/

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setFirstAnswer(String firstAnswer) {
        this.firstAnswer = firstAnswer;
    }

    public String getFirstAnswer() {
        return firstAnswer;
    }

    public void setSecondAnswer(String secondAnswer) {
        this.secondAnswer = secondAnswer;
    }

    public String getSecondAnswer() {
        return secondAnswer;
    }

    public void setThirdAnswer(String thirdAnswer) {
        this.thirdAnswer = thirdAnswer;
    }

    public String getThirdAnswer() {
        return thirdAnswer;
    }

    public void setFourthAnswer(String fourthAnswer) {
        this.fourthAnswer = fourthAnswer;
    }

    public String getFourthAnswer() {
        return fourthAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return fourthAnswer;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getExplanation() {
        return explanation;
    }

}
