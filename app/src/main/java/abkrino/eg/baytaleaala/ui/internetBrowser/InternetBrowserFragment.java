package abkrino.eg.baytaleaala.ui.internetBrowser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import abkrino.eg.baytaleaala.R;
import abkrino.eg.baytaleaala.ui.internet.IOnBackPressed;
import abkrino.eg.baytaleaala.ui.internet.InternetViewModel;

public class InternetBrowserFragment extends Fragment implements IOnBackPressed {

    WebView webView;
    String title;
    private InternetBrowserViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(InternetBrowserViewModel.class);
        View root = inflater.inflate(R.layout.fragment_internet_browser, container, false);
        webView = root.findViewById(R.id.webview);
        final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                title = s;
                textView.setText(s);
                getWeb();
            }
        });


        return root;
    }

    public void getWeb() {
        //webView
        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportMultipleWindows(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(title);
        if (onBackPressed()) {
            if (webView.canGoBack()) {
                webView.goBack();
            }
        }

        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setAppCacheEnabled(false);
        //webView
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }
}