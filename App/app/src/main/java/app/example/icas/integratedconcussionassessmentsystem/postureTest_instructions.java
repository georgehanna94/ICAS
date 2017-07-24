package app.example.icas.integratedconcussionassessmentsystem;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.os.CountDownTimer;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.animation.ValueAnimator;

//import com.wang.avi.AVLoadingIndicatorView;
//import com.wang.avi.Indicator;

import java.io.File;
import java.util.Date;

import static android.view.View.GONE;

/**
 * Posturography Fragment. Created by Maryam Kaka. This fragment will handle the display of posture
 * instructions prior to beginning data collection.
 */

public class postureTest_instructions extends Fragment implements SensorEventListener   {
    //Button next,skip;
    private int[] word_instructions;
    private int[] image_instructions;
    public Posture_test2 ParentActivity;
    private TextView Statusmsg;
    //Images of the instruction and the instruction sentence itself
    ImageView   instr_pic,instr_word;
    private int image_index =0,click_index =1;
    private Sensor mySensor;
    private SensorManager SM;
    private dbHelper db;
    private long testID;
    public String path = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/ICAS/Posture";
    private Boolean collectData = Boolean.FALSE;
    private CountDownTimer countDownTimer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posture_test_instructions, container, false);   /* Layout inflator takes the provided xml layout  */
    }

    /**
     * Create postureography test fragment
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        //Initialize database
        db = new dbHelper(getContext());

        //Create array of visual instructions and their corresponding text
        word_instructions = new int[]{R.drawable.postinstr1,R.drawable.postinstr2,R.drawable.postinstr3};
        image_instructions = new int[]{R.drawable.postinstpic1,R.drawable.postinstpic2,R.drawable.postinstpic3};

        instr_pic = (ImageView) getView().findViewById(R.id.instr_image);
        instr_word = (ImageView) getView().findViewById(R.id.instr_text);

        //Initialize Fragment with first instruction
        instr_pic.setImageResource(image_instructions[image_index]);
        instr_word.setImageResource(word_instructions[image_index]);
        image_index++;

        //Create our Sensor Manager
        SM = (SensorManager)getContext().getSystemService(Context.SENSOR_SERVICE);

        //Accelerometer Sensor
        mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //Register sensor listener
        SM.registerListener(this,mySensor,SensorManager.SENSOR_DELAY_NORMAL);

        Statusmsg = (TextView) getView().findViewById(R.id.statusmsg);
        Statusmsg.setVisibility(GONE);

        //Create timer object
        countDownTimer = new postureTest_instructions.MyCountDownTimer(10000, 1000);

        //Create File directory
        File dir = new File(path);
        dir.mkdirs();
    }

    /**
     * set testID
     * @param id
     */
    public void setTestID(long id){ testID = id; }

    /**
     * Handles loading of next test
     * @return load successful flag - returns false if unable to load next question (i.e. on last question)
     */
    public boolean nextQuestion() {
        System.out.println("Click_index is now" + click_index);

        //If BESS test is complete return to main page
        if (click_index == 6) {
            System.out.println("about to return");
            Statusmsg.setVisibility(View.GONE);
            //Temporary fix for inability of screen to return to main menu
            ParentActivity.enableBtns(getView());

            return false;
        }

        //swtich between instruction and data collection screens
        if(click_index%2!=0){
            //Disable next and previous buttons during collection
            ParentActivity.disableBtns(getView());

            //Hide instructions
            instr_pic.setVisibility(GONE);
            instr_word.setVisibility(GONE);

            Statusmsg.setVisibility(View.VISIBLE);
            countDownTimer.start();
            // avi.setVisibility(View.VISIBLE);
            // startAnim();

            click_index++;
            collectData = Boolean.TRUE;
        } else {
            //Enable next and previous buttons
            ParentActivity.enableBtns(getView());
            //Show instructions for next stance
            instr_pic.setVisibility(View.VISIBLE);
            instr_word.setVisibility(View.VISIBLE);
            // avi.setVisibility(GONE);

            Statusmsg.setVisibility(View.GONE);
            instr_pic.setImageResource(image_instructions[image_index]);
            instr_word.setImageResource(word_instructions[image_index]);
            image_index++;
            click_index++;
            collectData = Boolean.FALSE;
        }

        return true;
    }

    /**
     * Handles loading of prev test
     * @return load successful flag - returns false if unable to load prev test
     */
    public boolean prevQuestion(View view){
            return false;

    }

    /**
     * Countdown timer
     */
    public class MyCountDownTimer extends CountDownTimer{
        public MyCountDownTimer (long startTime, long interval){
            super(startTime,interval);
        }

        @Override
        public void onTick(long l) {

        }

        @Override
        public void onFinish() {
            System.out.println("Done Counting");
            nextQuestion();
        }

    }

    /**
     * Handles snesor change events
     * Saves accelerometer reading to database
     * @param sensorEvent
     */
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if(collectData){
            long timestamp = (new Date()).getTime() + (sensorEvent.timestamp - System.nanoTime()) / 1000000L; // Convert to UNIX Time
            db.addAccelData(timestamp, testID, sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        //Not in use
    }

}
