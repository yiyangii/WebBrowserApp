package edu.temple.webbrowserapp;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.MalformedURLException;

public class PageViewerFragment extends Fragment {

    private WebView webview;
    private WebSettings setting;
    private int FID;

    public PageViewerFragment() {

    }
    // public void display(int item){
    //        viewpager.setCurrentItem(item);
    //    }
    //public void onAttach(@NonNull Context context) {
    //        super.onAttach(context);
    //
    //        if (context instanceof Interface) {
    //            parentActivity = (Interface) context;
    //        } else {
    //            throw new RuntimeException("You must implement selectInterface to attach this fragment");
    //        }
    //
    //    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @SuppressLint("JavascriptInterface")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View myFragmentView =inflater.inflate(R.layout.pageviewer, container, false);

        if (getArguments()!=null) {
            FID = getArguments().getInt("FID");
        }
        webview = (WebView)myFragmentView.findViewById(R.id.webview);
        webview.addJavascriptInterface(this,"android");
        webview.setWebViewClient(webViewClient);
        if(savedInstanceState != null){
            webview.restoreState(savedInstanceState);
        }


        return  myFragmentView;
    }


    private WebViewClient webViewClient = new WebViewClient(){

        @Override
        public void onPageFinished(WebView view, String url) {
            if (listener!=null){listener.StatusCheck(view.getTitle());}
        }


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (listener!=null){listener.UpdateURL(url);}
        }
    };

    public String Gettitle(){
        String str="";
        if (webview !=null)
            str= webview.getTitle();
        return str;
    }
    /*
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
    */

    public String GetURL(){
        String s="";
        if (webview !=null)
            s= webview.getUrl();
        return s;
    }

    public void LoadPage(String sURL) throws MalformedURLException {
        if (webview !=null)
            webview.loadUrl(sURL);
    }

    public void ButtonOp(int buttonid){
        if (buttonid==R.id.BackButton) {
            if (webview.canGoBack()) {
                webview.goBack();
            }
        }
        else if (buttonid==R.id.RedoButton){
            if (webview.canGoForward()) {
                webview.goForward();
            }
        }
    }
    private URLUpdate listener;
    public void OnpageListener(URLUpdate listener){
        this.listener = listener;
    }

    public interface URLUpdate {
        void UpdateURL(String sURL);
        void StatusCheck(String sTitle);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        webview.saveState(outState);
    }
}