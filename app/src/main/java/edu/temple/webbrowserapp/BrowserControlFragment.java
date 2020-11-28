package edu.temple.webbrowserapp;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class BrowserControlFragment extends Fragment {
    private ImageButton button;
    private ImageButton bm;
    private ImageButton bts;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public void addNewButtonListener(BrowserControlFragment.OnNewButtonClickListener listener) {
        this.listener = listener;}

    public interface OnNewButtonClickListener{
        void OnNewButtonClick();
        void OnBookmark();
        void OnSave();
    }
    //public interface Interface3 {
    //        void createNewInstance();
    //    }
    private BrowserControlFragment.OnNewButtonClickListener listener;

    public BrowserControlFragment() {
        //
    }
    // public void onAttach(@NonNull Context context) {
    //        super.onAttach(context);
    //
    //        if (context instanceof BrowserControlFragment.Interface3) {
    //            inf = (BrowserControlFragment.Interface3) context;
    //        } else {
    //            throw new RuntimeException("You must implement ViewPagerInterface to attach this fragment");
    //        }
    //
    //    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View myFragmentView =
                inflater.inflate(R.layout.browseractivity, container, false);
        button =myFragmentView.findViewById(R.id.ImageButton);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                listener.OnNewButtonClick();
            }
        });
        bm = myFragmentView.findViewById(R.id.BookMarkButton);
        bm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                listener.OnBookmark();
            }
        });

        bts=myFragmentView.findViewById(R.id.SaveBookMark);
        bts.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                listener.OnSave();
            }
        });
        return myFragmentView;
    }
}