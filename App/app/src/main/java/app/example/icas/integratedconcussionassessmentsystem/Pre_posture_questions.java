package app.example.icas.integratedconcussionassessmentsystem;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class Pre_posture_questions extends Fragment {

    BESSEvaluationQuestions questions;
    TextView questionTxt;
    RadioGroup Q1;
    RadioButton r;
    private dbHelper db;
    private String answers[][] = {
            {"None", "Shoes", "Sandals", "Flip-Flops", "Cleats"},
            {"Right", "Left", "", "", ""},
            {"Hardwood", "Grass", "Asphalt", "Tiles", "Rug"}
    };
    private String[] response  =  new String[3];
    private long testID;
    private int i;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pre_posture_questions, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //initalize database
        db = new dbHelper(getContext());

        questions = new BESSEvaluationQuestions();

        questionTxt = (TextView) getView().findViewById(R.id.question);
        Q1  = (RadioGroup) view.findViewById(R.id.Q1);

        //TO-DO: STORE RADIOBUTTON ANSWERS
        Q1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup Q1, int selectedId){
                selectedId = Q1.getCheckedRadioButtonId();
                r = (RadioButton) getView().findViewById(selectedId);
                response[i] = r.getText().toString();
            }
        });


        //First Posture Question
        Q1.setVisibility(view.VISIBLE);
        updateScreen(view);
    }

    public boolean nextQuestion(View view){
        questions.incrementIndex();

        if(questions.getIndex() >= questions.getMaxIndex()){
            //store final results in database
            testID = db.addPostureTest(response[0],response[1],response[2]);

            return false;
        }

        updateScreen(view);
        return true;
    }

    public boolean prevQuestion(View view){
        questions.decrementIndex();
        if(questions.getIndex() < 0){
            return false;
        }
        updateScreen(view);
        return true;
    }

    public long getTestID(){ return testID; }

    private void updateScreen(View view){
        //set questions
        i = questions.getIndex();
        questionTxt.setText(questions.getCurrentQuestion());

        if (i == 1) {
            (Q1.getChildAt(2)).setVisibility(view.INVISIBLE);
            (Q1.getChildAt(3)).setVisibility(view.INVISIBLE);
            (Q1.getChildAt(4)).setVisibility(view.INVISIBLE);
        } else {
            (Q1.getChildAt(2)).setVisibility(view.VISIBLE);
            (Q1.getChildAt(3)).setVisibility(view.VISIBLE);
            (Q1.getChildAt(4)).setVisibility(view.VISIBLE);
        }

        //Update Text
        ((RadioButton) Q1.getChildAt(0)).setText(answers[i][0]);
        ((RadioButton) Q1.getChildAt(1)).setText(answers[i][1]);
        ((RadioButton) Q1.getChildAt(2)).setText(answers[i][2]);
        ((RadioButton) Q1.getChildAt(3)).setText(answers[i][3]);
        ((RadioButton) Q1.getChildAt(4)).setText(answers[i][4]);

    }

}
