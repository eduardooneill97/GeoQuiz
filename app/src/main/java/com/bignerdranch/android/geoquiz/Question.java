package com.bignerdranch.android.geoquiz;

/**
 * Created by Eduardo O'Neill on 2/13/2018.
 */

public class Question {

    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean isAnswered = false;

    public Question(int textResId, boolean answerTrue){
        mTextResId = textResId;
        mAnswerTrue = answerTrue;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }
}
