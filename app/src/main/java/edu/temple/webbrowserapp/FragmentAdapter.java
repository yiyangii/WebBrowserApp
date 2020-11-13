package edu.temple.webbrowserapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class FragmentAdapter extends FragmentStatePagerAdapter {
    ArrayList<PageViewer> fmarray;

    PageViewer pv;

    public FragmentAdapter(@NonNull FragmentManager fm,ArrayList<PageViewer> fragments2) {
        super(fm,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        fmarray = fragments2;
    }
    @Override
    public int getCount() {
        return fmarray.size();
    }

    @NonNull
    @Override
    public PageViewer getItem(int position) {
        return fmarray.get(position);
    }

}
