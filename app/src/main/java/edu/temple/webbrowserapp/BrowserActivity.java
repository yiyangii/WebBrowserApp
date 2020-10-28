package edu.temple.webbrowserapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.EditText;

import java.net.MalformedURLException;

public class BrowserActivity extends AppCompatActivity implements PageViewer.updateInterface, PageControl.Interface{

    PageControl pagecontrol;
    PageViewer pageviewer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentmanager = getSupportFragmentManager();


        pagecontrol = (PageControl)fragmentmanager.findFragmentById(R.id.containerurl);
        pageviewer =(PageViewer)fragmentmanager.findFragmentById(R.id.containerbrowse);


        //page fragment is null

        if(pagecontrol == null){
            pagecontrol = new PageControl();
            fragmentmanager.beginTransaction().add(R.id.containerurl,pagecontrol).commit();
        }
        if(pageviewer == null){
            pageviewer = new PageViewer();
            fragmentmanager.beginTransaction().add(R.id.containerbrowse,pageviewer).commit();
        }
    }

    public void onSaveInstanceState(@NonNull Bundle state) {
        super.onSaveInstanceState(state);
        state.putAll(state);
    }

    public void back() {
        pageviewer.goBack();
    }
    public void forward() {
        pageviewer.goForward();
    }

    @Override
    public void updateURL(String text) {
        pagecontrol.updateTheURL(text);
    }
    public void DisplayInfo(String url) throws MalformedURLException {
        pageviewer.setInfo(url);
    }
}