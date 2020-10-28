package edu.temple.webbrowserapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.net.MalformedURLException;

public class PageControl  extends Fragment {
    EditText Text;
    ImageButton backbutton;
    ImageButton redobutton;
    ImageButton searchbutton;
    Interface parent;
    public PageControl(){

    }
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Interface) {
            parent = (Interface) context;
        } else {
            throw new RuntimeException("You must implement passInfoInterface to attach this fragment");
        }

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }
    //update user input string
    public void updateTheURL(String text){
        Text.setText(text);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pagecontrol, container, false);

        Text = (EditText) view.findViewById(R.id.URL);
        searchbutton = (ImageButton) view.findViewById(R.id.search);
        backbutton = (ImageButton) view.findViewById(R.id.backbutton);
        redobutton = (ImageButton) view.findViewById(R.id.redobutton);


        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread t = new Thread();
                String s = Text.getText().toString();
                if(!s.startsWith("https://")){
                    s = "https://" + s;
                    Text.setText(s);
                }
                try {
                    parent.DisplayInfo(s);

                }
                catch(MalformedURLException q) {
                    q.printStackTrace();
                }
                t.start();
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

    public interface Interface{

        void forward();

        void DisplayInfo(String website) throws MalformedURLException;
        void back();
    }
}
