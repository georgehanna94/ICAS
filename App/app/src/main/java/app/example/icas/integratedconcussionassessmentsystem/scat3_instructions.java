package app.example.icas.integratedconcussionassessmentsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.ImageView;

import com.gc.materialdesign.views.ButtonRectangle;

/**
 * Created by George Hanna. The following activity handles  the SCAT3 instruction screen
 */

public class scat3_instructions extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scat3_instructions);


        final int[] array = getIntent().getIntArrayExtra("SelectedTests");
        final int numones = getIntent().getIntExtra("NbOnes",1);

        //Set up button and on click listener for starting SCAT3 test
        final ButtonRectangle scat3start = (ButtonRectangle) findViewById(R.id.toscat3);
        scat3start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                //use intents to go to new activity
                Intent getScat3Screen = new Intent(v.getContext(), Scat3.class);
                getScat3Screen.putExtra("SelectedTests", array);
                getScat3Screen.putExtra("NbOnes",numones);
                startActivity(getScat3Screen);

            }
        });

    }


}
