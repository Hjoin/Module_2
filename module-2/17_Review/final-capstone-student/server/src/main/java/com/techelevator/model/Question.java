package com.techelevator.model;

public class Question {
    private String title;
    private String question;
    private long id;

    public Question(){}//empty constructor

    public Question(String question, String title, int id) {
        this.question = question;
        this.title = title;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
