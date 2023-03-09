package com.example.androidjuttu.game;

public final class Question {

    public enum Topic {
        MATEMATIIKKA {
            public String toString() {
                return "Matematiikka";
            }
        },
        YLEISTIETO {
            public String toString() {
                return "Yleistieto";
            }
        },
        TIETOTEKNIIKKA {
            public String toString() {
                return "Tietotekniikka";
            }
        },
        HISTORIA {
            public String toString() {
                return "Historia";
            }
        },
        URHEILU {
            public String toString() {
                return "Urheilu";
            }
        },
        ALL {
            public String toString() {
                return "*";
            }
        }, // get every topic
    }

    private String topic = "";
    private String question = "", rightAnswer = "", givenAnswer = "";
    private String firstAnswer = "", secondAnswer = "", thirdAnswer = "";
    private Boolean gaveRightAnswer;

    public Question() {}

    public Question(String topic, String question,
                    String answer1, String answer2, String answer3,
                    String rightAnswer) {

        this.topic = topic;
        this.question = question;
        this.firstAnswer = answer1;
        this.secondAnswer = answer2;
        this.thirdAnswer = answer3;
        this.rightAnswer = rightAnswer;
    }

    public String getFirstAnswer() {
        return firstAnswer;
    }

    public String getSecondAnswer() {
        return secondAnswer;
    }

    public String getThirdAnswer() {
        return thirdAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public String getTopic() {
        return topic;
    }



    public String getRightAnswer() {
        return rightAnswer;
    }

    public Boolean getGaveRightAnswer() {
        return gaveRightAnswer;
    }

    public void setGaveRightAnswer(Boolean ans) {
        gaveRightAnswer = ans;
    }

    public String getGivenAnswer() {
        return givenAnswer;
    }

    // TODO: check if the answer that was given is actually an answer this class has
    public void setGivenAnswer(String givenAnswer) {
        this.givenAnswer = givenAnswer;
    }
}
