package com.example.androidjuttu.game;

import java.util.ArrayList;
import java.util.List;

public class Questions {
    private final List<Question> questions;
    private int currentQuestion = 0, correctAnswers = 0, wrongAnswers = 0;

    // TODO: implement streaks
    // private int highestStreak;

    Questions() {
        this.questions = new ArrayList<>();
    }

    Questions(List<Question> questions) {
        this.questions = questions;
    }

    Questions(List<Question> questions, int questionStartIndex) {
        if (!CheckSize(questionStartIndex, questions))
            throw new RuntimeException("Given start index is out of bounds from given array.");

        this.questions = questions;
        this.currentQuestion = questionStartIndex;
    }

    public void AddQuestion(Question question) {
        this.questions.add(question);
    }

    // returns a Question at the given index. If CheckSize() fails a empty Question is returned
    public Question getQuestion(int position) {
        if (CheckSize())
            return questions.get(position);
        else
            return new Question();
    }

    // Goto the next question. unless we've reach the last question
    public void NextQuestion() {
        if (CheckSize())
            currentQuestion++;
    }

    // returns true if answer was right else false is returned
    public boolean ValidateAnswer(String answer) {
        // set the given answer
        questions.get(currentQuestion).setGivenAnswer(answer);

        if (answer.equals(questions.get(currentQuestion).getRightAnswer())) {
            questions.get(currentQuestion).setGaveRightAnswer(true);
            correctAnswers++;
            return true;
        }

        questions.get(currentQuestion).setGaveRightAnswer(false);
        wrongAnswers++;
        return false;
    }

    // returns a Question class at current index.
    public Question GetCurrentQuestion() {
        return this.questions.get(currentQuestion);
    }

    public String[] GetCurrentQuestionsAnswers() {
        return new String[]{
                this.questions.get(currentQuestion).getFirstAnswer(),
                this.questions.get(currentQuestion).getSecondAnswer(),
                this.questions.get(currentQuestion).getThirdAnswer()
        };
    }

    // Returns true if currentQuestion is smaller than questions size
    // or in other words returns true if we're not out of bounds
    private boolean CheckSize() {
        // return currentQuestion >= questions.size() - 1;
        return currentQuestion < questions.size();
    }

    private static boolean CheckSize(int position, List<Question> question) {
        return position < question.size();
    }

    // check if we're out of at tne position that was given
    private boolean CheckSize(int position) {
        // return currentQuestion >= questions.size() - 1;
        return position < questions.size();
    }

    public final String GetCurrentQuestionsRightAnswer() {
        return this.questions.get(currentQuestion).getRightAnswer();
    }

    public List<Question> GetQuestions() {
        return questions;
    }

    // This might need better naming
    // gets the Question classes question string
    public String GetCurrentQuestionQuestion() {
        return questions.get(currentQuestion).getQuestion();
    }

    // Get the current question index.
    public int GetCurrentQuestionIndex() {
        return currentQuestion;
    }

    public int GetNumberOfQuestions() {
        return questions.size();
    }

    // returns the number of correctly answered questions
    public int getCorrectAnswers() {
        return correctAnswers;
    }

    // returns the number of wrongly answered questions
    public int getWrongAnswers() {
        return wrongAnswers;
    }

    // returns a list of wrongly answered questions
    // if there are no wrongly answered questions null is returned or there are no questions
    public List<Question> GetWronglyAnsweredQuestions() {
        if(questions.isEmpty())
            return null;

        List<Question> questions = new ArrayList<>();

        for (Question question : this.questions)
            if (!question.getGaveRightAnswer())
                questions.add(question);

        if(questions.isEmpty())
            return null;

        return questions;
    }

    // returns a list of correctly answered questions
    // if there are no correctly answered questions null is returned or there are no questions
    public List<Question> GetCorrectlyAnsweredQuestions() {
        if(questions.isEmpty())
            return null;

        List<Question> questions = new ArrayList<>();

        for (Question question : this.questions)
            if (question.getGaveRightAnswer())
                questions.add(question);

        if(questions.isEmpty())
            return null;
       return questions;
    }

   // public int getHighestStreak() {
   //     return highestStreak;
   // }

   // public void setHighestStreak(int highestStreak) {
   //     this.highestStreak = highestStreak;
   // }
    }
