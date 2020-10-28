package edu.temple.webbrowserapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.net.MalformedURLException;

public class PageViewer extends Fragment {
    WebView web;
    updateInterface parent;
    public PageViewer() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View l = inflater.inflate(R.layout.pageviewer, container, false);


        web = (WebView) l.findViewById(R.id.webview);


        WebViewClient wc = new WebViewClient() {
            @Override
            public void doUpdateVisitedHistory(WebView web, String url, boolean reload) {
                parent.updateURL(url);
            }
            @Override
            public void onLoadResource(WebView web, String url) {
                super.onLoadResource(web, url);
            }
        };
        web.setWebViewClient(wc);
        web.getSettings().setJavaScriptEnabled(true);

        if(savedInstanceState != null){
            web.restoreState(savedInstanceState);
        }


        return l;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof PageControl.Interface) {
            parent = (PageViewer.updateInterface) context;
        } else {
            throw new RuntimeException("You must implement passInfoInterface to attach this fragment");
        }

    }
    public interface updateInterface{
        void updateURL(String text);
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        web.saveState(outState);
    }

    public void setInfo(String urlString) throws MalformedURLException {
        web.loadUrl(urlString);
    }
    public void goBack(){
        if(web.canGoBack()){
            web.canGoBack();
            web.goBack();
        }
    }
    public void goForward(){
        if(web.canGoForward()){
            web.canGoForward();
            web.goForward();
        }
    }


}

