package edu.temple.webbrowserapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.net.MalformedURLException;
import java.util.ArrayList;

public class PagerFragment extends Fragment implements PageViewerFragment.URLUpdate {


    private PagerFragment.OnChangeListener listener;
    public PagerFragment() {

    }
    public void addOnChangeListener(PagerFragment.OnChangeListener listener){
        this.listener = listener;}

    public interface  OnChangeListener{
        void OnPagerPageChangeURL(int position, String sURL);
        void OnPagerPageFinish(int position,String sTitle);
        void OnPagerChanged(int position,String sTitle,String sURL);
    }
    private ViewPager2 viewpager;
    private ViewPagerFragmentStateAdapter pageradapter;
    ArrayList<PageViewerFragment> fmarray;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.pager, container, false);
        viewpager = view.findViewById(R.id.ViewPager2);

        if (savedInstanceState!=null){

        }
        else {
            fmarray = new ArrayList<>();
            fmarray.add(new PageViewerFragment());
            PageViewerFragment pvfCurrent = fmarray.get(fmarray.size()-1);
            pvfCurrent.OnpageListener(this);


        }


        pageradapter =new ViewPagerFragmentStateAdapter(this.getActivity(), fmarray);
        viewpager.setAdapter(pageradapter);

        viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (listener!=null){
                    listener.OnPagerChanged(position, fmarray.get(position).Gettitle(), fmarray.get(position).GetURL());
                }
            }
        });
        return  view;
    }

    public int getCurItemPosition(){return viewpager.getCurrentItem();}

    public String getCurItemTitle(){return fmarray.get(viewpager.getCurrentItem()).Gettitle();}

    public String getCurItemURL(){return fmarray.get(viewpager.getCurrentItem()).GetURL();}

    @Override
    public void UpdateURL(String sURL) {
        if (listener!=null){listener.OnPagerPageChangeURL(viewpager.getCurrentItem(),sURL);}
    }

    @Override
    public void StatusCheck(String sTitle) {
        if (listener!=null){listener.OnPagerPageFinish(viewpager.getCurrentItem(),sTitle);}

    }
    // public interface Interface {
    //        void updateUrlSlide(int position);
    //    }

    //    public void createInstance(){
    //        viewpager.setAdapter(fa);
    //        viewpager.getAdapter().notifyDataSetChanged();
    //    }
    //    @Override
    //    public void onSaveInstanceState(@NonNull Bundle outState){
    //        outState.putParcelableArrayList("Array", fmlist);
    //        this.setRetainInstance(true);
    //    }



    public class ViewPagerFragmentStateAdapter extends FragmentStateAdapter {
        ArrayList<PageViewerFragment> arrMyWeb;
        public ViewPagerFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity,ArrayList<PageViewerFragment> arrWeb) {
            super(fragmentActivity);
            this.arrMyWeb=arrWeb;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return arrMyWeb.get(position);
        }
        @Override
        public int getItemCount() {
            return arrMyWeb.size();
        }
    }

    public ArrayList<String> getWebTitleList(){
        ArrayList<String> arrgWebTitle=new ArrayList<>();

        for (int i = 0; i< fmarray.size(); i++){
            arrgWebTitle.add(fmarray.get(i).Gettitle());
        }
        return arrgWebTitle;
    }

    public void LoadPageFromURL(String sURL) {
        PageViewerFragment pvfCurrent;
        pvfCurrent = fmarray.get(viewpager.getCurrentItem());

        try {
            pvfCurrent.LoadPage(sURL);
        }
        catch(MalformedURLException q) {
            q.printStackTrace();
        }
    }


    public void BackNext(int iBtn){
        PageViewerFragment pvfCurrent;
        pvfCurrent = fmarray.get(viewpager.getCurrentItem());
        pvfCurrent.ButtonOp(iBtn);
    }


    public void AddFragment(){
        fmarray.add(new PageViewerFragment());
        PageViewerFragment pvfCurrent = fmarray.get(fmarray.size()-1);
        pvfCurrent.OnpageListener(this);

        pageradapter.notifyItemInserted(fmarray.size()- 1);
        viewpager.setCurrentItem(fmarray.size()-1);

    }

    //set current fragment
    public void setCurrentFragment(int position){
        viewpager.setCurrentItem(position);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {

    }
}
