package edu.temple.webbrowserapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

public class PageControlFragment extends Fragment {
    private ImageButton[] btn = new ImageButton[3];
    private EditText txtURL;
    private int[] btn_id = {R.id.search, R.id.backbutton, R.id.redobutton};


    public void addButtonClickListener(OnClickListener listener){this.listener = listener;}

    public interface OnClickListener{
        void OnClick(int btnID);
    }

    private OnClickListener listener;

    public PageControlFragment() {

    }

    public static PageControlFragment newInstance() {
        return new PageControlFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        txtURL=(EditText)getActivity().findViewById(R.id.URL);

        for(int i = 0; i < btn.length; i++){
            btn[i] = (ImageButton) getActivity().findViewById(btn_id[i]);
            btn[i].setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    listener.OnClick(view.getId());
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View fm =inflater.inflate(R.layout.pagecontrol, container, false);

        return  fm;
    }

    public void setURL(String sURL){
        txtURL.setText(sURL);
    }

    public String getURL(){
        String temp=txtURL.getText().toString();

        if (!temp.startsWith("https://") &&
                !temp.endsWith(".com")) {

            temp="https://"+ temp + ".com";
        }
        txtURL.setText(temp);
        return temp;
    }
}


