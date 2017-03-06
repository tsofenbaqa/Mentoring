package com.example.a2017.mentoring.Fragments;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private String fileUri;
    private  int id ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.download, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_download)
        {
            startDownload();
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                fileUri = BaseUrl.MENTORING_DOCX+id;
            }
            else
            {
                fileUri = BaseUrl.MENTORING_PDF+id;
            }
        webView.loadUrl(BaseUrl.GOOGLE_DOC+fileUri);

    }

    private void getArgument()
    {
        isResume = getArguments().getBoolean("isResume");
        id = getArguments().getInt("userid");

    }

    private void startDownload()
    {
        Uri uri = Uri.parse(fileUri);
        DownloadManager downloadManager = (DownloadManager)getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        downloadManager.enqueue(request);
    }

}
