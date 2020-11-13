package edu.temple.webbrowserapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.ListView;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Objects;

public class BrowserActivity extends AppCompatActivity implements PageListFragment.selectInterface, PagerFragment.Interface, PageViewer.Interface2, PageControl.Interface, BrowserControlFragment.Interface3 {
    PageControl pagecontrol;
    PageViewer pageviewer;
    BrowserControlFragment browsecontrol;
    PageListFragment pagelist;
    PagerFragment pagefragment;
    ArrayList<PageViewer> fragments;
    FragmentAdapter fmarray;
    ArrayList<String> titles;
    ArrayList<String> Url;
    int ID;


    BaseAdapter ListAdapter;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        fragments = new ArrayList<>();
        if (savedInstanceState!=null){
            ID = savedInstanceState.getInt("ID",0);
        }
        else{
            ID = 0;
        }
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment temp;


        browsecontrol = (BrowserControlFragment) fm.findFragmentById((R.id.browser_control));
        if (browsecontrol == null) {
            browsecontrol = new BrowserControlFragment();

            fm.beginTransaction()
                    .add(R.id.browser_control, browsecontrol)
                    .commit();

        }else if((temp = fm.findFragmentById(R.id.browser_control)) instanceof BrowserControlFragment)
            browsecontrol = (BrowserControlFragment) temp;

        pagelist = (PageListFragment) fm.findFragmentById(R.id.page_list);
        if (pagelist == null) {
            pagelist = new PageListFragment();
            Bundle b = new Bundle();

            b.putParcelableArrayList("ArrayList",fragments);
            pagelist.setArguments(b);
            fm.beginTransaction()
                    .add(R.id.page_list, pagelist)
                    .commit();
        }else if((temp = fm.findFragmentById(R.id.page_list)) instanceof PageListFragment)
            pagelist = (PageListFragment) temp;

        pagecontrol = (PageControl) fm.findFragmentById(R.id.page_control);
        if (pagecontrol == null) {
            pagecontrol = new PageControl();
            fm.beginTransaction()
                    .add(R.id.page_control, pagecontrol)
                    .commit();

        }else if((temp = fm.findFragmentById(R.id.page_control)) instanceof PageControl)
            pagecontrol = (PageControl) temp;


        pagefragment = (PagerFragment) fm.findFragmentById(R.id.page_display);
        if (pagefragment == null && pageviewer == null) {
            pagefragment = new PagerFragment();

            Bundle b = new Bundle();
            b.putParcelableArrayList("Array", fragments);
            pagefragment.setArguments(b);
            fm.beginTransaction()

                    .add(R.id.page_display, pagefragment)
                    .commit();
        }else if((temp = fm.findFragmentById(R.id.page_control)) instanceof PagerFragment)
            pagefragment = (PagerFragment) temp;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle state) {
        super.onSaveInstanceState(state);
    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList("Array",fragments);
        super.onRestoreInstanceState(outState);
    }
    public void DisplayInfo(String website) throws MalformedURLException {
        pageviewer.setURL(website);
    }
    public void UpdateTitle(String pageTitle){
        Objects.requireNonNull(getSupportActionBar()).setTitle(pageTitle);
        pagelist.createInstance();
    }

    public void getcount(String pageTitle){

        final String TAG1 = "test";
        titles.add(pageTitle);
        pagelist.passList(titles);

    }

    @Override
    public void OnNewButtonClick() {
        Log.v("AAA","NewButton");
        getSupportActionBar().setTitle("");


    }

    @Override
    public void onItemSelected(int iID) {
        pagefragment.setCurrentFragment(iID);
    }

    @Override


    public void back() {
        pageviewer.Back();
    }
    public void forward() {
        pageviewer.Forward();
    }
    public void updateURL(String text) {
        Url.add(text);
        pagecontrol.setURL(text);

    }


    public void createNewInstance(){
        final String TAG = "test";

        pageviewer = new PageViewer();
        fragments.add(pageviewer);

        Log.d(TAG,"Fragment array size: " + fragments.size() );
        pagefragment.createInstance();
    }

    public void itemSelected(int item,ArrayList<PageViewer>f){
        pagefragment.display(item);
    }

    @Override
    public void passList(ListView list) {
        list.setAdapter(ListAdapter);
        list.getAdapter();
    }

    // @Override
    public void updateUrlSlide(int position) {


        UpdateTitle(titles.get(position));
        updateURL(Url.get(position));

    }
}

