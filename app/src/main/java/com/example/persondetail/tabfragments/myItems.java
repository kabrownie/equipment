package com.example.persondetail.tabfragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.persondetail.Additems;
import com.example.persondetail.R;


public class myItems extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view= inflater.inflate(R.layout.my_items, container, false);

        Button additems= view.findViewById(R.id.addItemButton);
        additems.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Additems.class);
                startActivity(intent);

            }
        });
        return view;
    }
}