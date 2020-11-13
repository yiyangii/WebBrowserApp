package edu.temple.webbrowserapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.MalformedURLException;

public class PageViewer extends Fragment implements Parcelable {
    WebView webview;
    int position;
    Interface2 parent;
    String url;

    public PageViewer() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(position);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.pageviewer, container, false);
        webview = view.findViewById(R.id.webview);

        WebViewClient webclient = new WebViewClient() {
            @Override
            public void onPageStarted(WebView w, String url, Bitmap favicon) {
                super.onPageStarted(w, url, favicon);
                String pageTitle = w.getTitle();
                parent.UpdateTitle(pageTitle);
            }
            @Override
            public void onPageFinished(WebView w, String url){
                super.onPageFinished(w,url);
                String pageTitle = w.getTitle();
                parent.UpdateTitle(pageTitle);
            }
            @Override
            public void doUpdateVisitedHistory(WebView web, String url, boolean reload) {

                String pageTitle = web.getTitle();
                parent.UpdateTitle(pageTitle);
                parent.getcount(pageTitle);
                parent.updateURL(url);
                PageViewer.this.url = url;
            }

            @Override
            public void onLoadResource(WebView web, String url) {
                super.onLoadResource(web, url);
            }
        };
        webview.setWebViewClient(webclient);
        webview.getSettings().setJavaScriptEnabled(true);

        if(savedInstanceState != null){
            webview.restoreState(savedInstanceState);
        }

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }
    protected PageViewer(Parcel in) {
        position = in.readInt();
    }


    public static final Creator<PageViewer> CREATOR = new Creator<PageViewer>() {
        @Override
        public PageViewer[] newArray(int size) {
            return new PageViewer[size];
        }
        @Override
        public PageViewer createFromParcel(Parcel in) {
            return new PageViewer(in);
        }


    };

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof PageControl.Interface) {
            parent = (Interface2) context;
        } else {
            throw new RuntimeException("Error");
        }

    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        webview.saveState(outState);
    }

    public interface Interface2 {
        void updateURL(String text);
        void UpdateTitle(String pageTitle);
        void getcount(String pageTitle);
    }




    public void setURL(final String urlString) throws MalformedURLException {
        webview.loadUrl(urlString);
    }
    public void Back(){
        if(webview.canGoBack()){
            webview.canGoBack();
            webview.goBack();
        }
    }
    public void Forward(){
        if(webview.canGoForward()){
            webview.canGoForward();
            webview.goForward();
        }
    }

}