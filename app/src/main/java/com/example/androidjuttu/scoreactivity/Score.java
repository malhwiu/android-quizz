package com.example.androidjuttu.scoreactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.androidjuttu.MainActivity;
import com.example.androidjuttu.R;
import com.example.androidjuttu.game.Question;

import java.util.List;
import java.util.Locale;

public class Score extends AppCompatActivity {
    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        recycler = findViewById(R.id.recyclerView);

        // populate recyclerview with questions
        setAdapter(MainActivity.questions.GetQuestions());

        int correctAnswers = MainActivity.questions.getCorrectAnswers();
        int wrongAnswers   = MainActivity.questions.getWrongAnswers();

        TextView points = findViewById(R.id.txtPoints);
        TextView answerRation = findViewById(R.id.txtAnswerRatio);

        points.setText(String.valueOf(correctAnswers));

        // set the percentage of how many questions were answered correctly
        answerRation.setText(String.format(Locale.ENGLISH,
                "%.2f%%",(
                        (float)correctAnswers/
                        (float)MainActivity.questions.GetNumberOfQuestions())*100.0F));

        TextView answers = findViewById(R.id.txtRigthWrong);
        answers.setText(String.format(Locale.ENGLISH,
                "%d/%d",correctAnswers,
                        wrongAnswers));

    }

    private void setAdapter(List<Question> question) {
        // these can be customized to give different animations to recyclerview
        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycler.setItemAnimator(new DefaultItemAnimator());

        // give recyclerview the questions that are going to be shown
        recycler.setAdapter(new QuestionRecyclerAdapter(question));
    }
}