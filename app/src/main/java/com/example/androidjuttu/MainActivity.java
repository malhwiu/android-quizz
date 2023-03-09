package com.example.androidjuttu;

import static com.example.androidjuttu.R.id.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

// nää taas yritystä yhdistää tietokantaan.. (ei) saa poistaa :D
// import android.database.sqlite.SQLiteOpenHelper;

import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import com.example.androidjuttu.game.Question;
import com.example.androidjuttu.game.Questions;
import com.example.androidjuttu.game.VisaHelper;
import com.example.androidjuttu.scoreactivity.Score;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    // (yritys siitä miten sen yhdistää siihen, ei toimi.)
    // String path = "tietokannan tiedostosijainti?";

    private static int points = 0;
    private static final String dbName = "tiovisa.db";
    public static Questions questions;
    public static final int numOfQuestions = 10;
    private TextView progress, ajastin, question;



    private Button topButton, centerButton, bottomButton;

    public static int getPoints() {
        return points;
    }



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progress = findViewById(R.id.Progress);
        question = findViewById(R.id.question_Text);
        ajastin = findViewById(R.id.timer);


        topButton = findViewById(R.id.Answer1);
        centerButton = findViewById(R.id.Answer2);
        bottomButton = findViewById(R.id.Answer3);

        // get questions from database
        questions = VisaHelper.MakeServer(this, dbName).GetQuestions(
                Question.Topic.ALL, numOfQuestions);
        UpdateView();
    }


    private void UpdateView() {
        // Launch a new activity if the last question was answered
        if (questions.GetCurrentQuestionIndex() ==
                questions.GetNumberOfQuestions()) {
            startActivity(new Intent(this, Score.class));
        } else {

            SetAnswersToButtons();
            SetQuestion();
            UpdateProgress();
        }


    }


    @SuppressLint("DefaultLocale")
    private void UpdateProgress() {
        progress.setText(String.format("%d/%d", questions.GetCurrentQuestionIndex() + 1,
                questions.GetNumberOfQuestions()));
    }

    private void SetQuestion() {
        question.setText(questions.GetCurrentQuestionQuestion());
    }

    private void SetAnswersToButtons() {
        String[] answers = questions.GetCurrentQuestionsAnswers();
        topButton.setText(answers[0]);
        centerButton.setText(answers[1]);
        bottomButton.setText(answers[2]);
    }

    // tää tässä alla kutsutaan kun joku painaa jotain niistä napeista
    // (pitäisikö jokaiselle napille olla oma kutsunsa?)
    public void painallus(View v) {

        Button answerButton = (Button) v;
        // if the button that was pressed is the correct answer
        if (questions.ValidateAnswer(answerButton.getText().toString())) {
            points++;
        }

        questions.NextQuestion();
        UpdateView();
    }

    private final int interval = 1000; // 1 Second
    private Timer handler = new Timer();
    private Runnable runnable = new Runnable(){
        public void run() {
            ajastin.setText((CharSequence) handler);
        }
    };



}
