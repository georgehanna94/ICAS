package app.example.icas.integratedconcussionassessmentsystem.firsttimeform;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.gc.materialdesign.views.ButtonRectangle;

import app.example.icas.integratedconcussionassessmentsystem.Homescreen;
import app.example.icas.integratedconcussionassessmentsystem.R;
import app.example.icas.integratedconcussionassessmentsystem.cogAssessmentFrag;
import app.example.icas.integratedconcussionassessmentsystem.dbHelper;
import app.example.icas.integratedconcussionassessmentsystem.digitsFrag;
import app.example.icas.integratedconcussionassessmentsystem.memoryFrag;
import app.example.icas.integratedconcussionassessmentsystem.monthsFrag;
import app.example.icas.integratedconcussionassessmentsystem.symptomEvalFrag;

/**
 * SCAT 3 Test, Fragment Manager
 */


public class background_form2 extends FragmentActivity{
    private tfparts tfparts = new tfparts();
    private typingpart typingpart = new typingpart();

    private boolean updateStatus;
    private int currentFrag = 0;
    private int fragcount = 0;
    private final FragmentManager fragmentManager = getFragmentManager();
    private ButtonRectangle next,prev;
    private dbHelper db;
    private long TestID;

    //Array containing test selection from dialog box
    private int[] testselection;
    //Array increased by 1 cell to accout for months backward frag

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //Forces full screen
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        setContentView(R.layout.background_form2);

        //Initialize database
        db = new dbHelper(this);
        //TestID = db.addSCAT3Test();

        next = (ButtonRectangle) findViewById(R.id.next);
        prev = (ButtonRectangle) findViewById(R.id.prev);

        //Select first Test to initialize
        fragmentManager.beginTransaction().add(R.id.fragment, typingpart).commit();

    }

    public void onNextClick(View view){

        if(currentFrag == 0){
            updateStatus = typingpart.nextQuestion(view);
        } else if (currentFrag == 1) {
            updateStatus = tfparts.nextQuestion(view);
        }

        //set next test fragment
        if(!updateStatus){
            currentFrag += 1;
            if(currentFrag == 1) {
                //db.addSymptomEvalScores(TestID, symptomEvalFrag.getScores());
                fragmentManager.beginTransaction().replace(R.id.fragment, tfparts).commit();
            } else {
                //db.addConcentrationScore(TestID, digitsFrag.getScore(), monthsFrag.getScore());
                //use intents to go to new activity
                Intent getHomeScreen = new Intent(view.getContext(), Homescreen.class);
                getHomeScreen.putExtra("callingAct", "Main Activity");
                startActivity(getHomeScreen);
            }
        }
    }

    public void onPrevClick(View view){
        if(currentFrag == 0){
            //updateStatus = symptomEvalFrag.prevQuestion();
        }}

    public void disableBtns(View view){
        next.setVisibility(View.INVISIBLE);
        prev.setVisibility(View.INVISIBLE);
    }

    public void enableBtns(View view){
        next.setVisibility(View.VISIBLE);
        prev.setVisibility(View.VISIBLE);
    }
}