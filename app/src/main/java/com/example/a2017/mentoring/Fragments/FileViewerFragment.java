package com.example.a2017.mentoring.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import com.example.a2017.mentoring.R;
import com.example.a2017.mentoring.RetrofitApi.BaseUrl;

/**
 * Created by 2017 on 02/03/2017.
 */

public class FileViewerFragment extends Fragment
{
    private WebView webView;
    private boolean isResume;
    private  int id ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getArgument();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.file_viewer_fragment , container ,false);
        webView = (WebView) view.findViewById(R.id.webView);
        setUpWebView();
        return view;
    }

    private void setUpWebView()
    {
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.setContentDescription("application/pdf");
        webView.getSettings().setUseWideViewPort(true);
            if(isResume)
            {
                webView.loadUrl(BaseUrl.GOOGLE_DOC+BaseUrl.MENTORING_DOCX+id);
            }
            else
            {
                webView.loadUrl(BaseUrl.GOOGLE_DOC+BaseUrl.MENTORING_PDF+id);
            }

    }

    private void getArgument()
    {
        isResume = getArguments().getBoolean("isResume");
        id = getArguments().getInt("userid");

    }

}
