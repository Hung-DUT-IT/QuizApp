package com.example.quizapp.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {
    public String id ;
    public String category;
    public String type;
    public String difficulty;
    public String question;
    public String correctAnswer;
    public ArrayList<String> incorrectAnswers;
    public ArrayList<String> tags;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

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
        this.correctAnswer = correctAnswer;
    }

    public ArrayList<String> getIncorrect_answers() {
        return incorrectAnswers;
    }

    public void setIncorrect_answers(ArrayList<String> incorrect_answers) {
        this.incorrectAnswers = incorrectAnswers;
    }

    public Question(String id, String category, String type, String difficulty, String question, String correctAnswer, ArrayList<String> incorrectAnswers, ArrayList<String> tags) {
        this.id = id;
        this.category = category;
        this.type = type;
        this.difficulty = difficulty;
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
        this.tags = tags;
    }
    public Question(String category){
        this.category = category;
    }
    public Question(){

    }

}
