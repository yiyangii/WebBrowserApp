package edu.temple.webbrowserapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;

public class BookMarkActivity extends AppCompatActivity {
    private TrashBinAdapter bmadapter;
    private ArrayList<BookMarkFragment> bookmarkarray;
    private ArrayList<String> ls;
    //ListView savedList;
    //BaseAdapter SaveListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        // Intent intent = getIntent();
        //final ArrayList<String> savedPageTitles = intent.getStringArrayListExtra("Save");
        //final ArrayList<String> savedUrls = intent.getStringArrayListExtra("Url");


        ListView list_view = (ListView) findViewById(R.id.ListView);

        getSupportActionBar().setTitle("BookMark");



        bookmarkarray = LoadBookmark();


        ls = new ArrayList<String>();

        for (int i = 0; i< bookmarkarray.size(); i++){
            ls.add(bookmarkarray.get(i).getTitle());
        }
        //savedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        //
        //            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //
        //                Intent ReverseActivityIntent = new Intent(BookmarksActivity.this,BrowserActivity.class);
        //                ReverseActivityIntent.putExtra("position", savedUrls.get(position));
        //                startActivity(ReverseActivityIntent);
        //            }
        //});
        bmadapter = new TrashBinAdapter(this, ls);

        bmadapter.setAttentionClickListener(new TrashBinAdapter.AttentionClickListener() {
            @Override
            public void DeleteItem(int iID) {
                bookmarkarray.remove(iID);
                SaveBookmark();
            }
            @Override
            public void OnBookmartClick(int index){
                BrowserActivity.BookMarkFragment(index);
                finish();
            }
        });
        list_view.setAdapter(bmadapter);
        Button Clostbuttom = (Button) findViewById(R.id.CloseButton);
        Clostbuttom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }




    private ArrayList<BookMarkFragment> LoadBookmark(){
        ArrayList<BookMarkFragment> bmarray = new ArrayList<>();

        Context context = getApplicationContext();

        SharedPreferences pref = context.getSharedPreferences("MyAppInfo",Context.MODE_PRIVATE);

        int count = pref.getInt("Total" , 0);

        for (int i = 0; i < count;i++){

            BookMarkFragment bm = new BookMarkFragment();

            int ID = pref.getInt("B_ID_" + i,-1);
            String title = pref.getString("B_Title_"+i,"");
            String url = pref.getString("B_URL_"+i,"");


            bm.setVal(ID,title,url);
            bmarray.add(bm);
        }
        return bmarray;
    }


    private int SaveBookmark(){
        Context context = getApplicationContext();



        SharedPreferences preferences = context.getSharedPreferences("MyAppInfo",Context.MODE_PRIVATE);
        SharedPreferences.Editor preferenceeditor = preferences.edit();


        preferenceeditor.putInt("Total" , bookmarkarray.size());

        for (int i = 0; i< bookmarkarray.size(); i++){

            preferenceeditor.putInt("B_ID_"+i, bookmarkarray.get(i).getID());
            preferenceeditor.putString("B_Title_"+i, bookmarkarray.get(i).getTitle());
            preferenceeditor.putString("B_URL_"+i, bookmarkarray.get(i).getURL());
        }

        preferenceeditor.apply();

        return 0;
    }


}