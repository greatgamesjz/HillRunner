package witkowski98.daiuusz.hillrunnerboy;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.reward.AdMetadataListener;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

//import com.google.android.gms.ads.InterstitialAd;

//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.InterstitialAd;
//import com.google.android.gms.ads.InterstitialAd;
//import com.google.android.gms.ads.MobileAds;

public class Main extends AppCompatActivity
{
    int sW,sH;
    boolean running, showWhenLoaded=false;
    Menu menu;
    boolean adWatched;
    AdListener adInteListener;
    RewardedVideoAdListener adRewListener;
    Game game;
    int limitRew,limitInte;
    private InterstitialAd mInterstitialAd;
    private RewardedVideoAd mRewardedVideoAd;
    public boolean connected = false;
    ConnectivityManager connectivityManager;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        limitRew=15;
        limitInte=15;
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // ukrywanie tytułu
        getSupportActionBar().hide(); // ukrywanie paska tytułu
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // odpalanie fullscreena
        Point size = new Point();




        checkConnection(false);
        if(connected)
            MobileAds.initialize(this, "ca-app-pub-1190089553146361~7232846087");
        Display display = getWindowManager().getDefaultDisplay();
        display.getSize(size);
        sW = size.x;
        sH = size.y;

        adInteListener = new AdListener() {
            @Override
            public void onAdClosed()
            {
                System.out.println("AddClosed");
                game.newGame();
            }
            @Override
            public void onAdLeftApplication()
            {

            }
            @Override
            public void onAdImpression()
            {

            }
            @Override
            public void onAdLoaded()
            {

            }
            @Override
            public void onAdClicked()
            {

            }
            @Override
            public void onAdOpened()
            {

            }
            @Override
            public void onAdFailedToLoad(int Errorcode)
            {
                System.out.println("AddInteFailedToLoad");
                if(limitInte>0)
                {
                    loadInteAd();
                    limitInte--;
                }
                else
                    game.newGame();

            }
        };
        adRewListener = new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {
                System.out.println("AdRewLoaded");
                limitRew=15;
                if(showWhenLoaded)
                    showRewAd();
            }

            @Override
            public void onRewardedVideoAdOpened() {

            }

            @Override
            public void onRewardedVideoStarted() {

            }

            @Override
            public void onRewardedVideoAdClosed() {
                if(adWatched)
                    game.continueGame();
            }

            @Override
            public void onRewarded(RewardItem rewardItem) {

            }

            @Override
            public void onRewardedVideoAdLeftApplication() {

            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
                if(limitRew>0) {
                    loadRewAd();
                    limitRew--;
                }
                else {
                    checkConnection(true);
                    if(connected==true)
                        game.continueGame();
                    else
                        game.setConnection(connected);
                }
            }

            @Override
            public void onRewardedVideoCompleted() {
               adWatched=true;
            }
        };

        startGame();
        if(connected) {
            createInteAd();
            createRewAd();
            loadRewAd();
            loadInteAd();
        }

    }
    public void startGame()
    {
        game = new Game(this,sW,sH,this,connected);
        setContentView(game);
    }
    public boolean showInteAd()
    {

        if (mInterstitialAd.isLoaded())
        {
            limitInte=15;
            mInterstitialAd.show();
            loadInteAd();
            return true;
        }
        else {
            loadInteAd();
            return false;
        }
    }
    public boolean showRewAd()
    {
        boolean showed=false;
            if (mRewardedVideoAd.isLoaded()) {
                showWhenLoaded=false;
                adWatched = false;
                showed = true;
                mRewardedVideoAd.show();
                loadRewAd();
                return true;
            } else {
                loadRewAd();
                showWhenLoaded=true;
                return false;
            }

    }
    private void createInteAd()
    {
        //MobileAds.initialize(this, "ca-app-pub-9387863653867601~3146699751");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-1190089553146361/1924277104");
        mInterstitialAd.setAdListener(adInteListener);
    }
    private void createRewAd()
    {
        mRewardedVideoAd = new RewardedVideoAd() {
            @Override
            public void loadAd(String s, AdRequest adRequest) {

            }

            @Override
            public void loadAd(String s, PublisherAdRequest publisherAdRequest) {

            }

            @Override
            public boolean isLoaded() {
                return false;
            }

            @Override
            public void show() {

            }

            @Override
            public void setRewardedVideoAdListener(RewardedVideoAdListener rewardedVideoAdListener) {

            }

            @Override
            public void setAdMetadataListener(AdMetadataListener adMetadataListener) {

            }

            @Override
            public Bundle getAdMetadata() {
                return null;
            }

            @Override
            public void setUserId(String s) {

            }

            @Override
            public RewardedVideoAdListener getRewardedVideoAdListener() {
                return null;
            }

            @Override
            public String getUserId() {
                return null;
            }

            @Override
            public void pause() {

            }

            @Override
            public void pause(Context context) {

            }

            @Override
            public void resume() {

            }

            @Override
            public void resume(Context context) {

            }

            @Override
            public void destroy() {

            }

            @Override
            public void destroy(Context context) {

            }

            @Override
            public String getMediationAdapterClassName() {
                return null;
            }

            @Override
            public void setImmersiveMode(boolean b) {

            }

            @Override
            public void setCustomData(String s) {

            }

            @Override
            public String getCustomData() {
                return null;
            }
        };

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(adRewListener);
    }
    private void loadInteAd()
    {
        //createInteAd();

        mInterstitialAd.loadAd(new AdRequest.Builder().build());

    }
    private void loadRewAd()
    {
        //createRewAd();
        mRewardedVideoAd.loadAd("ca-app-pub-1190089553146361/6050035004",
                new AdRequest.Builder().build());
    }
    public void checkConnection(boolean started)
    {
        connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            connected = true;
        }
        else
        connected = false;
        if(started) {
            try{
                game.setConnection(connected);
            }catch(Exception e)
            {

            }
        }
    }
}
