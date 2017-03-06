package com.example.a2017.mentoring;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a2017.mentoring.R;


public class UneditableMentorProfileFragment extends Fragment {

    TextView firstname,lastname,gender,phone,email,major,address,notes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_uneditable__mentor__profile, container, false);
        firstname = (TextView)view.findViewById(R.id.firstname);
        lastname  = (TextView)view.findViewById(R.id.lastname);
        gender    = (TextView)view.findViewById(R.id.gender);
        phone     = (TextView)view.findViewById(R.id.phone);
        email     = (TextView)view.findViewById(R.id.email);
        major     = (TextView)view.findViewById(R.id.major);
        address   = (TextView)view.findViewById(R.id.address);
        notes     = (TextView)view.findViewById(R.id.notes);
        return  view;
    }

}
