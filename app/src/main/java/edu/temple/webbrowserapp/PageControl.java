package edu.temple.webbrowserapp;

import android.content.Context;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import java.net.MalformedURLException;

import androidx.fragment.app.Fragment;

public class PageControl extends Fragment {
    EditText Text;
    ImageButton searchbutton;
    ImageButton redobutton;
    ImageButton backbutton;
    Interface parent;
    public PageControl() {

    }

    public interface Interface {
        void back();
        void forward();
        void DisplayInfo(String website) throws MalformedURLException;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pagecontrol, container, false);

        Text =  view.findViewById(R.id.URL);
        searchbutton =  view.findViewById(R.id.search);
        redobutton = view.findViewById(R.id.redobutton);
        backbutton =  view.findViewById(R.id.backbutton);


        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread();
                String s = Text.getText().toString();
                if(!s.startsWith("https://") && !s.endsWith(".com")){
                    s = "https://" + s + ".com";
                    Text.setText(s);
                }else if(s.startsWith("https://") && !s.endsWith(".com"))
                    s =  s + ".com";
                Text.setText(s);
                try {
                    parent.DisplayInfo(s);

                }
                catch(MalformedURLException u) {
                    u.printStackTrace();
                }
                thread.start();
            }
        });
        backbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                parent.back();
            }
        });

        redobutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                parent.forward();
            }
        });

        return view;
    }
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Interface) {
            parent = (Interface) context;
        } else {
            throw new RuntimeException("Error");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }
    public void setURL(String text){
        Text.setText(text);
    }



}