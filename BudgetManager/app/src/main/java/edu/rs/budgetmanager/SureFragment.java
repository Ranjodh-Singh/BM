package edu.rs.budgetmanager;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import edu.rs.budgetmanager.db.BudgetManagerDBHelper;
import edu.rs.budgetmanager.db.DBMetaData;
import edu.rs.budgetmanager.edu.rs.budgetmanager.util.Constants;
import edu.rs.budgetmanager.edu.rs.budgetmanager.util.InputValidator;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Callback} interface
 * to handle interaction events.
 * Use the {@link SureFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SureFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button button ;
    TextView amount;
    TextView label;
    TextView desc;
    TextView time;
    TextView date;
    private Callback callback;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SureFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SureFragment newInstance(String param1, String param2) {
        SureFragment fragment = new SureFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SureFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_sure, container, false);
        RelativeLayout relativeLayout = (RelativeLayout)inflate.findViewById(R.id.relativeId);

        button =(Button)inflate.findViewById(R.id.Submit);
        amount =(TextView)relativeLayout.findViewById(R.id.amountValueId);
        label =(TextView)relativeLayout.findViewById(R.id.reviewLabelValueId);
        desc =(TextView)relativeLayout.findViewById(R.id.reviewDescValue);
        time =(TextView)relativeLayout.findViewById(R.id.reviewTimeValue);
        date =(TextView)relativeLayout.findViewById(R.id.reviewDateValue);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(InputValidator.validate(callback.getData()).size() == 0){
                    // store the value in the database.
                    BudgetManagerDBHelper budgetManagerDBHelper = new BudgetManagerDBHelper(getActivity());
                    SQLiteDatabase db = budgetManagerDBHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(DBMetaData.RecordsEntry.COLUMN_NAME_ENTRY_ID, ++Constants.id);
                    values.put(DBMetaData.RecordsEntry.COLUMN_NAME_AMOUNT, callback.getData().get(Constants.AMOUNT));
                    values.put(DBMetaData.RecordsEntry.COLUMN_NAME_DESC, callback.getData().get(Constants.DESC));
                    // TODO:Label ID should come from the labels table.
                    values.put(DBMetaData.RecordsEntry.COLUMN_NAME_LABEL_ID, callback.getData().get(Constants.LABEL));
                    values.put(DBMetaData.RecordsEntry.COLUMN_NAME_TIME, callback.getData().get(Constants.TIME));
                    values.put(DBMetaData.RecordsEntry.COLUMN_NAME_DATE, callback.getData().get(Constants.DATE));
                    long newRowID = db.insert(DBMetaData.RecordsEntry.TABLE_NAME, null,values );
                    Log.d(this.toString(),"Values inserted into the databse with the primary key as  : "+ newRowID);
                    Toast.makeText(getActivity(),"Your Data has been saved . ",Toast.LENGTH_SHORT);
                }

            }
        });
        return inflate;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (callback != null) {
            callback.getData();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (Callback) activity;
            amount.setText(callback.getData().get(Constants.AMOUNT));
            label.setText(callback.getData().get(Constants.LABEL));
            desc.setText(callback.getData().get(Constants.DESC));
            time.setText(callback.getData().get(Constants.TIME));
            date.setText(callback.getData().get(Constants.DATE));
            Log.d(this.getTag(),"data collected is : "+callback.getData().size());
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */


}
