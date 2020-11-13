package edu.temple.webbrowserapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class PagerFragment extends Fragment {
    ViewPager viewpager;

    Interface parentActivity;
    ArrayList<PageViewer> fmlist;
    FragmentAdapter fa;


    public PagerFragment() {

    }




    public void display(int item){
        viewpager.setCurrentItem(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            fmlist = getArguments().getParcelableArrayList("Array");
        }
    }
    public void setCurrentFragment(int position){
        viewpager.setCurrentItem(position);
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager, container, false);

        viewpager = view.findViewById(R.id.ViewPager2);

        fa = new FragmentAdapter(getChildFragmentManager(), fmlist);

        if (savedInstanceState != null) {
            // fragments2 = savedInstanceState.getParcelableArrayList("Array");
            viewpager.setAdapter(fa);
            viewpager.getAdapter().notifyDataSetChanged();

            viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    parentActivity.updateUrlSlide(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                parentActivity.updateUrlSlide(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        return view;
    }
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof Interface) {
            parentActivity = (Interface) context;
        } else {
            throw new RuntimeException("You must implement selectInterface to attach this fragment");
        }

    }
    public interface Interface {
        void updateUrlSlide(int position);
    }


    public void createInstance(){
        viewpager.setAdapter(fa);
        viewpager.getAdapter().notifyDataSetChanged();
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState){
        outState.putParcelableArrayList("Array", fmlist);
        this.setRetainInstance(true);
    }
}