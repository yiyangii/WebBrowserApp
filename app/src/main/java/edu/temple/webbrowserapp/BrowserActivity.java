package edu.temple.webbrowserapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;

public class BrowserActivity extends AppCompatActivity implements PageControlFragment.OnClickListener, BrowserControlFragment.OnNewButtonClickListener, PageListFragment.OnItemSelectedListener, PagerFragment.OnChangeListener {
    private FragmentManager fragmentmanager;
    private PageControlFragment fragmentcontralfm;
    private PageListFragment pagelistfragment;
    private PagerFragment pager;

    private BrowserControlFragment browsercontrolfragment;


    private int ID;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null){
            ID = savedInstanceState.getInt("ID",0);
        }
        else{
            ID = 0;
        }
        FragmentManager fragmentmanager;;
        setContentView(R.layout.activity_main);
        fragmentmanager = getSupportFragmentManager();

        /*if (pagelist == null) {
            pagelist = new PageListFragment();
            Bundle b = new Bundle();

            b.putParcelableArrayList("ArrayList",fragments);
            pagelist.setArguments(b);
            fm.beginTransaction()
                    .add(R.id.page_list, pagelist)
                    .commit();
        }else if((temp = fm.findFragmentById(R.id.page_list)) instanceof PageListFragment)
            pagelist = (PageListFragment) temp;

       */
        Fragment temp;

        if ((temp = fragmentmanager.findFragmentById(R.id.browse_control)) instanceof BrowserControlFragment)
            browsercontrolfragment = (BrowserControlFragment) temp;
        else {
            browsercontrolfragment = new BrowserControlFragment();
            fragmentmanager.beginTransaction().add(R.id.browse_control, browsercontrolfragment).commit();
        }
        browsercontrolfragment.addNewButtonListener(this);

        if ((temp = fragmentmanager.findFragmentById(R.id.page_control)) instanceof PageControlFragment)
            fragmentcontralfm = (PageControlFragment) temp;
        else {
            fragmentcontralfm = new PageControlFragment();
            fragmentmanager.beginTransaction().add(R.id.page_control, fragmentcontralfm).commit();
        }
        fragmentcontralfm.addButtonClickListener(this);


        if ((temp = fragmentmanager.findFragmentById(R.id.page_display)) instanceof PagerFragment)
            pager = (PagerFragment) temp;
        else {
            pager = new PagerFragment();
            fragmentmanager.beginTransaction().add(R.id.page_display, pager).commit();

        }
        /*ublic void DisplayInfo(String website) throws MalformedURLException {
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
        */
        pager.addOnChangeListener(this);

        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT){


            if ((temp = fragmentmanager.findFragmentById(R.id.page_list)) instanceof PageListFragment) {
                pagelistfragment = (PageListFragment) temp;
            }
            else {
                pagelistfragment = PageListFragment.newInstance(pager.getWebTitleList());

                fragmentmanager.beginTransaction().add(R.id.page_list, pagelistfragment).commit();
            }
            pagelistfragment.addSelectListener(this);
        }


    }


    @Override
    public void OnClick(int button ){

        if (button == R.id.search) {
            pager.LoadPageFromURL(fragmentcontralfm.getURL());
        }else{
            pager.BackNext(button );
        }
    }

    @Override
    public void OnPagerPageChangeURL(int position, String sURL) {
        fragmentcontralfm.setURL(sURL);
    }

    @Override
    public void OnPagerPageFinish(int position,String sTitle) {

        if (position== pager.getCurItemPosition()){
            getSupportActionBar().setTitle(pager.getCurItemTitle());
            fragmentcontralfm.setURL(pager.getCurItemURL());
        }
        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT) {
            pagelistfragment.UpdateList(pager.getWebTitleList());
        }
    }

    @Override
    public void OnPagerChanged(int position,String sTitle,String sURL){
        ID = pager.getCurItemPosition();
        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_PORTRAIT){

            pagelistfragment.UpdateList(pager.getWebTitleList());
        }

        fragmentcontralfm.setURL(pager.getCurItemURL());
        getSupportActionBar().setTitle(pager.getCurItemTitle());



    }

    @Override
    public void OnNewButtonClick() {

        getSupportActionBar().setTitle("");
        pager.AddFragment();

    }

    @Override
    public void onItemSelected(int iID) {
        pager.setCurrentFragment(iID);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("igCurPagerID", ID);

    }



}