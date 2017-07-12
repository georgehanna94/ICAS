package app.example.icas.integratedconcussionassessmentsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by George on 2017-02-10. Activity handling the display of all four ICAS tests to the user.
 */

public class mainmenu extends Fragment {
    //Variables
    private ImageButton scat3, posture, eyeGaze, EEG;
    private dbHelper db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mainmenu,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Home");
        db = new dbHelper(getContext());

        //Gridview for ICAS Test options
        final ImageButton scat3 = (ImageButton) view.findViewById(R.id.scat3);
        final ImageButton posture = (ImageButton) view.findViewById(R.id.posture);
        final ImageButton eeg = (ImageButton) view.findViewById(R.id.eeg);
        final ImageButton eyegaze = (ImageButton) view.findViewById(R.id.eyegaze);
        scat3.setVisibility(View.VISIBLE);
        posture.setVisibility(View.VISIBLE);
        eeg.setVisibility(View.VISIBLE);
        eyegaze.setVisibility(View.VISIBLE);

        //Welcome text
        TextView welcomeTxt = (TextView) view.findViewById(R.id.welcomeTxt);
        welcomeTxt.setText("Hello, " + db.getUser());

        //Makes Images Interactive to access SCAT3
        scat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                //use intents to go to new activity
                Intent getScat3Screen = new Intent(v.getContext(), Scat3_landing.class);
                getScat3Screen.putExtra("callingAct", "Main Activity");
                startActivity(getScat3Screen);

            }
        });

        //Makes Images Interactive to access Posture Test
        posture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                Intent getPosturagraphyScreen = new Intent(v.getContext(), Posturography.class);
                getPosturagraphyScreen.putExtra("callingAct", "Main Activity");
                startActivity(getPosturagraphyScreen);

            }
        });

        //Makes Images Interactive to access EEG
        eeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                Toast toast = Toast.makeText(getActivity(), "Under Construction", Toast.LENGTH_SHORT);
                toast.show();

            }
        });

        //Makes Images Interactive to access EYegaze
        eyegaze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                Toast toast = Toast.makeText(getActivity(), "Under Construction", Toast.LENGTH_SHORT);
                toast.show();

            }
        });
    }
}
