package edu.rs.budgetmanager;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import edu.rs.budgetmanager.db.BudgetManagerDBHelper;
import edu.rs.budgetmanager.db.DBMetaData;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Callback} interface
 * to handle interaction events.
 * Use the {@link HowAndWhyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HowAndWhyFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Callback callback = null;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText amount = null;
    EditText description = null;

    public AutoCompleteTextView getmAutoCompleteTextView() {
        return mAutoCompleteTextView;
    }

    public void setmAutoCompleteTextView(MultiAutoCompleteTextView mAutoCompleteTextView) {
        this.mAutoCompleteTextView = mAutoCompleteTextView;
    }

    private MultiAutoCompleteTextView mAutoCompleteTextView;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HowAndWhyFragment.
     */

    public static HowAndWhyFragment newInstance(String param1, String param2) {
        HowAndWhyFragment fragment = new HowAndWhyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public HowAndWhyFragment() {
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
        View view = inflater.inflate(R.layout.fragment_how_and_why, container, false);
        amount = (EditText)view.findViewById(R.id.amount);
        description = (EditText)view.findViewById(R.id.descriptionText);
        mAutoCompleteTextView = (MultiAutoCompleteTextView)view.findViewById(R.id.autoCompleteTextView);
        //loadSpinnerData();
        loadLabels();
        mAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        return view ;
    }

    /**
     * loading the Spinner data from the database and setting the adapter.
     */
    private void loadLabels() {
        // database handler
        BudgetManagerDBHelper db = new BudgetManagerDBHelper(this.getActivity().getApplicationContext());

        //  Drop down elements
        List<String> labels =  db.getAllLabels();



        // removing null explicitly for a bug if works then get the not null values from the db.
       labels.removeAll(Collections.singleton(null));

        // remove duplicates.
        Set<String> labelSet = new HashSet<>(labels);

        // if the labels contains ,(commas) then you have to separate them and add as separate items in list.
        Iterator mIterator = labelSet.iterator();
        while (mIterator.hasNext()) {

            String item = (String)mIterator.next();
            String tokens[]=null;
            if(item.contains(",")){
                mIterator.remove();
                tokens = item.split(",");
            }
            if(tokens != null) {
                for (String str : tokens) {
                    labelSet.add(str);
                }
            }

        }
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1
                ,new ArrayList<>(labelSet));

        // Drop down layout style - list view with radio button
       // dataAdapter
         //       .setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // attaching data adapter to spinner
        //spinner.setAdapter(dataAdapter);
        mAutoCompleteTextView.setAdapter(dataAdapter);
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (callback != null) {
            callback.getData();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callback = (Callback) activity;


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
     * <p>Callback method to be invoked when an item in this view has been
     * selected. This callback is invoked only when the newly selected
     * position is different from the previously selected position or if
     * there was no selected item.</p>
     * <p/>
     * Impelmenters can call getItemAtPosition(position) if they need to access the
     * data associated with the selected item.
     *
     * @param parent   The AdapterView where the selection happened
     * @param view     The view within the AdapterView that was clicked
     * @param position The position of the view in the adapter
     * @param id       The row id of the item that is selected
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String label = parent.getItemAtPosition(position).toString();
        callback.setData("label",label);
        callback.setData("amount",amount.getText().toString());
        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "You selected: " + label,
                Toast.LENGTH_LONG).show();
    }

    /**
     * Callback method to be invoked when the selection disappears from this
     * view. The selection can disappear for instance when touch is activated
     * or when the adapter becomes empty.
     *
     * @param parent The AdapterView that now contains no selected item.
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
