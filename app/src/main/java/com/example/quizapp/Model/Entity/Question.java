package com.example.quizapp.Model.Entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {
    private String category;
    private String type;
    private String difficulty;
    private String question;
    private String correctAnswer;
    private ArrayList<String> incorrectAnswers;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrect_answer() {
        return correctAnswer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correctAnswer = correct_answer;
    }

    public ArrayList<String> getIncorrect_answers() {
        return incorrectAnswers;
    }

    public void setIncorrect_answers(ArrayList<String> incorrect_answers) {
        this.incorrectAnswers = incorrect_answers;
    }

    public Question(String category, String type, String difficulty, String question, String correct_answer, ArrayList<String> incorrect_answers) {
        this.category = category;
        this.type = type;
        this.difficulty = difficulty;
        this.question = question;
        this.correctAnswer = correct_answer;
        this.incorrectAnswers = incorrect_answers;
    }
    public Question(){

    }

}