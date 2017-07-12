package app.example.icas.integratedconcussionassessmentsystem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by George on 2017-02-10.
 * The following fragment handles the About I-CAS section of the app
 * Most of the information displayed on the user's screen is static and
 * can be found in the abouticas.xml file
 */

public class abouticas extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.abouticas,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("About I-CAS");
    }
}
