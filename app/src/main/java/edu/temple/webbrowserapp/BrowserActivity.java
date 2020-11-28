package edu.temple.webbrowserapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class BrowserActivity extends AppCompatActivity implements PageControlFragment.OnClickListener, BrowserControlFragment.OnNewButtonClickListener, PageListFragment.OnItemSelectedListener, PagerFragment.OnChangeListener {
    private FragmentManager fragmentmanager;
    private PageControlFragment fragmentcontralfm;
    private PageListFragment pagelistfragment;
    private PagerFragment pager;

    private BrowserControlFragment browsercontrolfragment;

    private ArrayList<BookMarkFragment> bkm;
    private int ID;
    private static int ID2 = -1;
    private final int REQUEST_CODE=111;


    public static void BookMarkFragment(int ID2){
        ID2 = ID2;
    }

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
        FragmentManager fragmentmanager;

        bkm = LoadBookmark();

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

        if ((temp = fragmentmanager.findFragmentById(R.id.browser_control)) instanceof BrowserControlFragment)
            browsercontrolfragment = (BrowserControlFragment) temp;
        else {
            browsercontrolfragment = new BrowserControlFragment();
            fragmentmanager.beginTransaction().add(R.id.browser_control, browsercontrolfragment).commit();
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

        if (button == R.id.SearchButton) {
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

    public void OnSave(){
        String sURL= pager.getCurItemURL();
        String sTitle= pager.getCurItemTitle();

        if ((sURL!=null) && (sTitle!=null)){
            Log.v("KKK","canSave");
            for (int i=0;i < bkm.size();i++){
                String sTmp = bkm.get(i).getURL();
                if (sTmp.equals(sURL)){
                    Toast.makeText(getApplicationContext(),"Bookmark already exist.",Toast.LENGTH_LONG).show();
                    return;
                }
            }

            BookMarkFragment bkTmp=new BookMarkFragment();
            bkTmp.setVal(bkm.size(),sTitle,sURL);
            bkm.add(bkTmp);
            SaveBookmark();
            Toast.makeText(getApplicationContext(),"Bookmark save success.",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(),"   No Title or URL \nBookmark not save",Toast.LENGTH_LONG).show();
        }
    }

    public void OnBookmark(){
        Intent MyInform=new Intent (BrowserActivity.this,BookMarkActivity.class);
        MyInform.putExtra("myID",1);
        startActivityForResult(MyInform,REQUEST_CODE);
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
    private int SaveBookmark(){
        //SharedPreferences pref = getSharedPreferences("MyAppInfo" , MODE_MULTI_PROCESS);
        Context context = getApplicationContext();
        SharedPreferences pref = context.getSharedPreferences("MyAppInfo",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("TotalBookmark" ,bkm.size());

        for (int i=0; i < bkm.size();i++){
            editor.putInt("B_ID_"+i, bkm.get(i).getID());
            editor.putString("B_Title_"+i,bkm.get(i).getTitle());
            editor.putString("B_URL_"+i,bkm.get(i).getURL());
        }

        editor.apply();
        return 0;
    }
    @Override
    public void onWindowFocusChanged (boolean hasFocus){
        bkm=LoadBookmark();
        if (hasFocus && (ID2>=0)){
            pager.LoadPageFromURL(bkm.get(ID2).getURL());
            ID2 = -1;
        }
    }
    private ArrayList<BookMarkFragment> LoadBookmark(){
        ArrayList<BookMarkFragment> arrTemp=new ArrayList<>();

        Context context = getApplicationContext();
        SharedPreferences pref = context.getSharedPreferences("MyAppInfo",Context.MODE_PRIVATE);

        int itotalBookmark=pref.getInt("TotalBookmark" , 0);

        for (int i=0; i<itotalBookmark;i++){
            BookMarkFragment bkTmp=new BookMarkFragment();
            int iTmpID=pref.getInt("B_ID_"+i,-1);
            String iTmpTitle=pref.getString("B_Title_"+i,"");
            String iTmpURL=pref.getString("B_URL_"+i,"");
            bkTmp.setVal(iTmpID,iTmpTitle,iTmpURL);
            arrTemp.add(bkTmp);
        }
        return arrTemp;
    }



}