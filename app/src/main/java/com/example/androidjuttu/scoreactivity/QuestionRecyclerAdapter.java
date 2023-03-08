package com.example.androidjuttu.scoreactivity;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidjuttu.R;
import com.example.androidjuttu.game.Question;

import java.util.List;

/* resources used:
 * https://youtu.be/__OMnFR-wZU
 * https://youtu.be/Mc0XT58A1Z4
 */

public final class QuestionRecyclerAdapter extends
        RecyclerView.Adapter<QuestionRecyclerAdapter.ViewHolder> {
    private final List<Question> questions;

    // this uses list_items.xml to show the questions in the recyclerview
    public final static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView topic, question, givenAnswer, rightAnswer;
        private final CardView card;
        public ViewHolder(final View view) {
            super(view);

            topic       = view.findViewById(R.id.txtTopic);
            question    = view.findViewById(R.id.txtQuestion);
            givenAnswer = view.findViewById(R.id.txtGivenAnswer);
            rightAnswer = view.findViewById(R.id.txtCorrectAnswer);
            card        = view.findViewById(R.id.cardView);
        }
    }

    public QuestionRecyclerAdapter(List<Question> questions) {
        this.questions = questions;
    }

    @NonNull
    @Override
    public QuestionRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                 int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items,
                                            parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String topic       = questions.get(position).getTopic();
        String question    = questions.get(position).getQuestion();
        String rightAnswer = questions.get(position).getRightAnswer();
        String givenAnswer = questions.get(position).getGivenAnswer();

        holder.topic.setText(topic);
        holder.question.setText(question);
        holder.rightAnswer.setText(rightAnswer);
        holder.givenAnswer.setText(givenAnswer);

        // set cardviews color based on if question was right
        if(questions.get(position).getGaveRightAnswer())
            holder.card.setCardBackgroundColor(Color.GREEN);
        else
            holder.card.setCardBackgroundColor(Color.RED);

    }

    @Override
    public int getItemCount() {
        return questions.size();
    }
}
