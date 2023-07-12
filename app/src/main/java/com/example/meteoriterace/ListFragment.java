package com.example.meteoriterace;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListFragment extends Fragment {

    private ListView list_LST_names;

    private ArrayList<String> data;

    private Location_Callback location_callback;

    private ScoreEntery s[];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container,false);

        list_LST_names = view.findViewById(R.id.list_LST_scores);


        //ArrayAdapter arrayAdapter = new ArrayAdapter(container.getContext(), R.layout.list_country, data);
        ArrayAdapter arrayAdapter = new ArrayAdapter(requireContext(), R.layout.list_item, getListItems());
        list_LST_names.setAdapter(arrayAdapter);

        list_LST_names.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(container.getContext(), position + " - " + data.get(position), Toast.LENGTH_SHORT).show();
                location_callback.setLocationOnMap(s[position].getLat(),s[position].getLon());
            }
        });

        return view;
    }

    public void setLocation_callback(Location_Callback location_callback){
        this.location_callback = location_callback;
    }

    private List getListItems() {

        data = new ArrayList<String>();
        s = mySP.getSP().readAll();
        for(ScoreEntery se : s){
            data.add(se.toString());
        }

        return data;
    }
}
