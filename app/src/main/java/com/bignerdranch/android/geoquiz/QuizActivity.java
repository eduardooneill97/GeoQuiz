package com.bignerdranch.android.geoquiz;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPreviousButton;
    private TextView mQuestionTextView;

    private Question[] mQuestionBank = {
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_austria, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_asia, true),
            new Question(R.string.question_oceans, true)
    };

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mQuestionTextView = findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateQuestion(true);
            }
        });

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateQuestion(true);
            }
        });

        mPreviousButton = findViewById(R.id.previous_button);
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateQuestion(false);
            }
        });

        updateQuestion(true);
    }

    private void updateQuestion(boolean isNext){
        if(isNext) mCurrentIndex = (mCurrentIndex+1)%mQuestionBank.length;
        else if(!isNext && mCurrentIndex == 0)mCurrentIndex = mQuestionBank.length - 1;
        else mCurrentIndex = (mCurrentIndex-1)%mQuestionBank.length;
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue){
        int resId = 0;

        if(userPressedTrue == mQuestionBank[mCurrentIndex].isAnswerTrue()){
            resId = R.string.correct_toast;
        }
        else{
            resId = R.string.incorrect_toast;
        }
        Toast t = Toast.makeText(QuizActivity.this, resId, Toast.LENGTH_SHORT);
        t.setGravity(Gravity.TOP, 0, 200);
        t.show();
    }
}
