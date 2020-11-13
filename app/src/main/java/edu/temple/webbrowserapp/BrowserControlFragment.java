package edu.temple.webbrowserapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class BrowserControlFragment extends Fragment {

    ImageButton btw;

    Interface3 inf;

    public BrowserControlFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);

    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putAll(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.browseractivity, container, false);
        btw = (ImageButton) view.findViewById(R.id.imagebutton);

        btw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inf.createNewInstance();
            }
        });

        return view;
    }
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof BrowserControlFragment.Interface3) {
            inf = (BrowserControlFragment.Interface3) context;
        } else {
            throw new RuntimeException("You must implement ViewPagerInterface to attach this fragment");
        }

    }

    public interface Interface3 {
        void createNewInstance();
    }
}