package app.example.icas.integratedconcussionassessmentsystem;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.codecrafters.tableview.SortableTableView;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.SwipeToRefreshListener;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import de.codecrafters.tableview.toolkit.SortStateViewProviders;

/**
 * Created by George Hanna
 This fragment handles the history tab of the posturography test in the ICAS app
 The table used in this section can be found on github: https://github.com/ISchwarz23/SortableTableView
 */

public class History_frag_posture extends Fragment {

    //Variables
    private final static String[][] DATA_TO_SHOW = new String[5][2];
    private ArrayList<String[]> data = new ArrayList();
    private static final String[] headerdata = {"1", "2"};
    private dbHelper db;
    private String date;
    private int posn_in_table = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history_frag, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Call database and load previous posture tests
        db = new dbHelper(getContext());
        data = db.getPostureTests();

        //Create Sortable Table
        SortableTableView<String[]> tableView = (SortableTableView<String[]>) view.findViewById(R.id.tableView);

        //Set Table Header
        final SimpleTableHeaderAdapter simpleTableHeaderAdapter = new SimpleTableHeaderAdapter(getActivity(),"Session","Date");
        simpleTableHeaderAdapter.setTextColor(ContextCompat.getColor(getActivity(),R.color.cardview_light_background));
        tableView.setHeaderSortStateViewProvider(SortStateViewProviders.darkArrows());
        tableView.setHeaderAdapter(simpleTableHeaderAdapter);

        updatetable();
        posn_in_table++;
        tableView.setDataAdapter(new SimpleTableDataAdapter(getActivity(),DATA_TO_SHOW));

        //Set Column Widths
        TableColumnWeightModel columnModel = new TableColumnWeightModel(2);
        columnModel.setColumnWeight(0,1);
        columnModel.setColumnWeight(1,2);
        tableView.setColumnModel(columnModel);

        //Handles table behaviour and colour
        tableView.setHeaderBackgroundColor(getResources().getColor(R.color.colorAccent));
        tableView.setSwipeToRefreshEnabled(true);
        tableView.setSwipeToRefreshListener(new SwipeToRefreshListener() {
            @Override
            public void onRefresh(RefreshIndicator refreshIndicator) {
                refreshIndicator.hide();
                updatetable();
                posn_in_table++;
                Toast toast = Toast.makeText(getContext(),"Refreshed",Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }

    //Updates table with new entries
    public void updatetable(){
        data = db.getPostureTests();
        if (!(data.isEmpty())) {
            for (int i = posn_in_table ; i<data.size();i++){
                date = data.get(i)[0];
                String session = String.valueOf(i + 1);
                DATA_TO_SHOW[i][0] = session;
                DATA_TO_SHOW[i][1] = date;
            }
        }
    }
}
