package com.bignerdranch.android.geoquiz;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_SCORE = "score";
    private static final String KEY_QUESTIONSANSWERED = "questionsAnswered";
    private static final String KEY_ISANSWERED = "isAnswered";
    private static final String KEY_PRESSABLE = "pressable";

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    //private ImageButton mPreviousButton;
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
    private int score = 0;
    private int questionsAnswered = 0;
    private boolean[] answered = new boolean[mQuestionBank.length];
    private boolean isPressable = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreated(Bundle) called");
        setContentView(R.layout.activity_quiz);

        for(int i = 0; i<answered.length; i++){
            answered[i] = false;
        }

        if(savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            score = savedInstanceState.getInt(KEY_SCORE, 0);
            questionsAnswered = savedInstanceState.getInt(KEY_QUESTIONSANSWERED, 0);
            answered = savedInstanceState.getBooleanArray(KEY_ISANSWERED);
            isPressable = savedInstanceState.getBoolean(KEY_PRESSABLE, true);
        }

        mQuestionTextView = findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex+1)%mQuestionBank.length;
                updateQuestion();
            }
        });

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
                answered[mCurrentIndex] = true;
                updateAnswerButtons();
                checkFinished();
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
                answered[mCurrentIndex] = true;
                updateAnswerButtons();
                checkFinished();
            }
        });

        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex+1)%mQuestionBank.length;
                updateQuestion();
                updateAnswerButtons();
            }
        });

//        mPreviousButton = findViewById(R.id.previous_button);
//        mPreviousButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(mCurrentIndex == 0)mCurrentIndex = mQuestionBank.length - 1;
//                else mCurrentIndex = (mCurrentIndex-1)%mQuestionBank.length;
//                updateQuestion();
//            }
//        });

        updateQuestion();
        updateAnswerButtons();

    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putInt(KEY_QUESTIONSANSWERED, questionsAnswered);
        savedInstanceState.putInt(KEY_SCORE, score);
        savedInstanceState.putBooleanArray(KEY_ISANSWERED, answered);
        savedInstanceState.putBoolean(KEY_PRESSABLE, isPressable);
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    private void updateQuestion(){
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }

    private void updateAnswerButtons(){
        if(answered[mCurrentIndex]){
            isPressable = false;
        }
        else {
            isPressable = true;
        }
        mTrueButton.setClickable(isPressable);
        mFalseButton.setClickable(isPressable);
    }

    private void checkFinished(){
        if(questionsAnswered == mQuestionBank.length){
            Toast.makeText(QuizActivity.this, "You scored "+(int)((float)score/(float)questionsAnswered*100)+"%", Toast.LENGTH_LONG).show();
        }
    }

    private void checkAnswer(boolean userPressedTrue){
        int resId = 0;

        if(userPressedTrue == mQuestionBank[mCurrentIndex].isAnswerTrue()){
            resId = R.string.correct_toast;
            score++;
            questionsAnswered++;
        }
        else{
            resId = R.string.incorrect_toast;
            questionsAnswered++;
        }
        Toast t = Toast.makeText(QuizActivity.this, resId, Toast.LENGTH_SHORT);
        t.setGravity(Gravity.TOP, 0, 200);
        t.show();
    }
}
