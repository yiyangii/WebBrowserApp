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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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


    public static void BookMarkFragment(int id){
        ID2 = id;
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


        bkm = Load();

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
            pager.BackNext(button);
        }
    }

    @Override
    public void OnPagerPageChangeURL(int position, String sURL) {
        fragmentcontralfm.setURL(sURL);
    }
    //public void addPage(){//adds page to list of bookmarked pages after clicking "save page" button
    //        Toast.makeText(this, "Page Saved!", Toast.LENGTH_SHORT).show();
    //
    //        StringBuilder sb = new StringBuilder();
    //        savedPageTitles.add(savedTitle);
    //        Url2.add(url);
    //
    //      //  Url.add(url);
    //        StringBuilder sb2 = new StringBuilder();
    //
    //        for(String s: Url2){
    //            sb2.append(s);
    //            sb2.append(",");
    //        }
    //
    //        for(String s: savedPageTitles){
    //            sb.append(s);
    //            sb.append(",");
    //        }
    //
    //        SharedPreferences prefer = getSharedPreferences("element",0);
    //        SharedPreferences.Editor editor = prefer.edit();
    //        editor.putString("elements", sb.toString());
    //        editor.apply();
    //
    //        SharedPreferences prefer2 = getSharedPreferences("element2",0);
    //        SharedPreferences.Editor editor2 = prefer2.edit();
    //        editor2.putString("elements2", sb2.toString());
    //        editor2.apply();
    //
    //    }
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
            SaveBookMarkLs();
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


    private ArrayList<BookMarkFragment> Load(){
        ArrayList<BookMarkFragment> arrTemp=new ArrayList<>();

        Context context = getApplicationContext();
        SharedPreferences preference = context.getSharedPreferences("MyAppInfo",Context.MODE_PRIVATE);

        int count = preference.getInt("Total" , 0);

        for (int i=0; i<count ;i++){
            BookMarkFragment bkm1=new BookMarkFragment();
            int ID3 = preference.getInt("B_ID_"+i,-1);
            String title = preference.getString("B_Title_"+i,"");
            String url=preference.getString("B_URL_"+i,"");
            bkm1.setVal(ID3,title,url);
            arrTemp.add(bkm1);
        }
        return arrTemp;
    }
    //public void buttonClicked(){
    //
    //        savedPageTitles.removeAll(savedPageTitles);
    //        SharedPreferences pref = getSharedPreferences("element",MODE_PRIVATE);
    //        String web2 = pref.getString("elements", "Google");
    //        String[] items = web2.split(",");
    //        savedPageTitles.addAll(Arrays.asList(items));
    //
    //        Url2.removeAll(Url2);
    //       // Url.removeAll(Url);
    //        SharedPreferences pref2 = getSharedPreferences("element2",MODE_PRIVATE);
    //        String web3 = pref2.getString("elements2", "google.com");
    //        String[] items2 = web3.split(",");
    //        Url2.addAll(Arrays.asList(items2));
    //      //  Url.addAll(Arrays.asList(items2));
    //
    //
    //        Intent ActivityIntent = new Intent(BrowserActivity.this, BookmarksActivity.class);
    //        ActivityIntent.putStringArrayListExtra("Save",savedPageTitles);
    //        ActivityIntent.putStringArrayListExtra("Url",Url2);
    //        startActivity(ActivityIntent);

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("igCurPagerID", ID);

    }
    private int SaveBookMarkLs(){
        //SharedPreferences pref = getSharedPreferences("MyAppInfo" , MODE_MULTI_PROCESS);


        Context context = getApplicationContext();
        SharedPreferences preference = context.getSharedPreferences("MyAppInfo",Context.MODE_PRIVATE);
        SharedPreferences.Editor preferenceeditor = preference.edit();
        preferenceeditor.putInt("TotalBookmark" ,bkm.size());

        for (int i=0; i < bkm.size();i++){
            preferenceeditor.putInt("B_ID_"+i, bkm.get(i).getID());
            preferenceeditor.putString("B_Title_"+i,bkm.get(i).getTitle());
            preferenceeditor.putString("B_URL_"+i,bkm.get(i).getURL());
        }

        preferenceeditor.apply();
        return 0;
    }
    @Override
    public void onWindowFocusChanged (boolean v){
        bkm= Load();
        if (v && (ID2 >= 0)){

            pager.LoadPageFromURL(bkm.get(ID2).getURL());
            ID2 = -1;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    // share button click function
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId()==R.id.sharebutton){
            String sWeb=pager.getCurItemURL();
            String sTt=pager.getCurItemTitle();
            if ((sWeb==null) || (sTt==null)){
                Toast.makeText(getApplicationContext(),
                        "Please double check the website is being searched",
                        Toast.LENGTH_LONG).show();
            }
            else {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, sTt+" - "+sWeb);
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }
        }
        return super.onOptionsItemSelected(item);
    }





}