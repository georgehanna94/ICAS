package app.example.icas.integratedconcussionassessmentsystem;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by mkaka on 2016-12-05.
 */

public class symptomEvalFrag extends Fragment{
    symptomEvaluationQuestions questions;
    TextView questionTxt, scoreTxt;
    SeekBar answer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.symptom_eval, container, false);   /* Layout inflator takes the provided xml layout  */
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        questions = new symptomEvaluationQuestions();

        questionTxt = (TextView) getView().findViewById(R.id.question);
        answer = (SeekBar) getView().findViewById(R.id.answer);
        scoreTxt = (TextView) getView().findViewById(R.id.score);

        scoreTxt.setText("0");
        updateScreen();
        answer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                scoreTxt.setText(Integer.toString(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //scoreTxt.setText(value);
            }
        });
    }

    public void nextQuestion(View view){
        questions.incrementIndex();
        updateScreen();
    }

    public void prevQuestion(View view){
        questions.decrementIndex();
        updateScreen();
    }

    private void updateScreen(){
        if(questions.getIndex() >= questions.getMaxIndex()){
        } else if(questions.getIndex() < 0) {
        } else {
            questionTxt.setText(questions.getCurrentQuestion());
            answer.setProgress(0);
        }
    }
}