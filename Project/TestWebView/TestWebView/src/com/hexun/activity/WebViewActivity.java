package com.hexun.activity;

import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.hexun.network.DataCleanManager;

public class WebViewActivity extends Activity {

	private WebView webView;
	private ProgressBar pb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.webview);
		webView = (WebView) findViewById(R.id.webView);
		
		pb = (ProgressBar)findViewById(R.id.pb_web);
	}

	@SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		WebSettings webSettings = webView.getSettings();
		webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		// WebView加载web资源
		String ul = "http://t.hexunfsd.com/weixin/publicweb/home/index?knowChannel="+ "APP_LCK_ADR_KC" +"&random"+String.valueOf(new Random().nextInt(10000000));
		webView.loadUrl(ul);
		// 从assets目录下面的加载html
		// webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		webView.addJavascriptInterface(this, "javatojs");
		webView.getSettings().setJavaScriptEnabled(true);
		
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// 返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onReceivedSslError(WebView view,
					SslErrorHandler handler, SslError error) {
				// 重写此方法可以让webview处理https请求。
				super.onReceivedSslError(view, handler, error);
				handler.proceed();
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// 在页面加载开始时调用。
				super.onPageStarted(view, url, favicon);
				webView.setVisibility(View.GONE);
				pb.setVisibility(View.VISIBLE);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// 在页面加载结束时调用。
				super.onPageFinished(view, url);
				webView.setVisibility(View.VISIBLE);
				pb.setVisibility(View.GONE);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// 网络不通，加载失败
				super.onReceivedError(view, errorCode, description, failingUrl);
				view.loadData("网络连接失败,请稍后重试!", "text/html; charset=utf-8",
						"utf-8");
			}

			@Override
			public void onLoadResource(WebView view, String url) {
				// 在加载页面资源时会调用，每一个资源（比如图片）的加载都会调用一次。
				super.onLoadResource(view, url);
			}

		});

		// 主要处理解析，渲染网页等浏览器做的事情 辅助WebView处理Javascript的对话框，网站图标，网站title，加载进度等
		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onReceivedTitle(WebView view, String title) {
				setTitle(title);
			}
		});

	}

	@JavascriptInterface
	public void loginBack(final String custId, final String sessionKey,
			String userName, String phoneNum) {
		Log.i("WebViewActivity", "custId:" + custId + "==sessionKey:" + sessionKey
				+ "==userName:" + userName + "==phoneNum:" + phoneNum);
	}

	@JavascriptInterface
	public void reqDataFromApp() {
		webView.loadUrl("javascript:getDataToH5('" + "sessionKey" + "')");
	}

	@JavascriptInterface
	public void tradeBack() {
		finish();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		DataCleanManager.cleanInternalCache(this);
		DataCleanManager.cleanSharedPreference(this);

		// 清除cookie即可彻底清除缓存
		CookieSyncManager.createInstance(this);
		CookieSyncManager.getInstance().startSync();
		CookieManager.getInstance().removeAllCookie();
		Log.i("WebViewActivity", "<<<<<<<<<<<<<<<<<<<<<<<");
		webView.clearCache(true);
		webView.clearHistory();

		System.gc();
		finish();
		super.onStop();
	}

}