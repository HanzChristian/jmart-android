package com.HanzChristianJmartMH;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterFragment extends Fragment {

    private String[] productCategory = {"BOOK", "KITCHEN", "ELECTRONIC", "FASHION", "GAMING", "GADGET", "MOTHERCARE",
            "COSMETICS", "HEALTHCARE", "FURNITURE", "JEWELRY", "TOYS", "FNB", "STATIONERY", "SPORTS", "AUTOMOTIVE",
            "PETCARE", "ART_CRAFT", "CARPENTRY", "MISCELLANEOUS", "PROPERTY", "TRAVEL", "WEDDING"};

    public FilterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FilterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FilterFragment newInstance() {
        FilterFragment fragment = new FilterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        EditText editName = view.findViewById(R.id.name);
        EditText lowestPrice = view.findViewById(R.id.lowestprice);
        EditText highestPrice = view.findViewById(R.id.highestprice);
        CheckBox checkNew = view.findViewById(R.id.checknew);
        CheckBox checkUsed = view.findViewById(R.id.checkused);
        Button applyButton = view.findViewById(R.id.appplybutton);
        Button clearButton = view.findViewById(R.id.clearbutton);

        //Spinner
        Spinner spinner = (Spinner) view.findViewById(R.id.spinnerproduct);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, productCategory);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        return view;
    }
}