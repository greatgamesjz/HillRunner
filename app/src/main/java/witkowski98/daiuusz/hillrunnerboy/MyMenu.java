package witkowski98.daiuusz.hillrunnerboy;

import android.app.AppComponentFactory;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Random;

public class MyMenu
{
    int soundsize;
    Paint paint;
    Paint clearPaint;
    Rect rect;
    int barWidth=300,barHeight=100, barWidth2;
    int sW,sH;
    int a,b,c,d,a2,b2,c2,d2;
    Bitmap startpng,startpng2,continuepng,continuepng2,mute,mute2;
    Bitmap coin,coin2;
    SharedPreferences localStorage;
    SharedPreferences.Editor localStorageEditor;
    int points=0;
    int localPoints;
    int ilosccyfr,ilosccyfr2;
    boolean connected;

    public MyMenu(Context context, int sW, int sH, Bitmap startpng,Bitmap continuepng,Bitmap mute,boolean connected,Bitmap coin)
    {
        this.sW=sW;
        this.sH=sH;
        this.connected=connected;
        paint = new Paint();
        clearPaint=new Paint();
        rect = new Rect();
        soundsize=sW/10;
        barWidth=sW/2;
        barHeight=sH/8;
        barWidth2=sW/2+sW/3;
        this.startpng=startpng;
        this.continuepng=continuepng;
        this.mute=mute;
        this.coin=coin;
        setBars();
        localStorage=PreferenceManager.getDefaultSharedPreferences(context);
        localStorageEditor = localStorage.edit();

    }
    private void setBars()
    {
        startpng2=Bitmap.createScaledBitmap(startpng,barWidth,barHeight,false);
        continuepng2=Bitmap.createScaledBitmap(continuepng,barWidth2,barHeight,false);
        mute2=Bitmap.createScaledBitmap(mute,soundsize,soundsize,false);
        coin2=Bitmap.createScaledBitmap(coin,soundsize,soundsize,false);
    }
    public void draw(Canvas canvas,int deadWaiter,int dead2Waiter,boolean ableToContinue)
    {

        paint.setColor(Color.argb(deadWaiter,50,50,50));//kolor tła
        canvas.drawRect(0,0,canvas.getWidth(),canvas.getHeight(),paint); // tło

        paint.setColor(Color.argb(dead2Waiter,180,180,180)); //kolor prsotokąta

        setCenter(sW/2,sH/2,barWidth,barHeight); // ustawienie pozycji prostokąta

        canvas.drawBitmap(startpng2,a,b,paint); // rysowanie prostokąta
        if(deadWaiter!=255 && ableToContinue && connected)
            canvas.drawBitmap(continuepng2,a2,b2,paint);


        canvas.drawRect(sW-soundsize,0,sW,soundsize,paint); //kwadrat w prawym gornym
        canvas.drawBitmap(mute2,sW-soundsize,0,paint);
        paint.setTextSize(sW/10);
        paint.setTypeface(Typeface.create("Arial",Typeface.ITALIC));
        localPoints=localStorage.getInt("POINTS",0);
        if(localPoints<10)
            ilosccyfr=1;
        else if(localPoints<100)
            ilosccyfr=2;
        else if(localPoints<1000)
            ilosccyfr=3;
        else if(localPoints<10000)
            ilosccyfr=4;
        else ilosccyfr=5;

        if(points<10)
            ilosccyfr2=1;
        else if(points<100)
            ilosccyfr2=2;
        else if(points<1000)
            ilosccyfr2=3;
        else if(points<10000)
            ilosccyfr2=4;
        else ilosccyfr2=5;
        if(deadWaiter!=255) {
            canvas.drawText("SCORE", sW / 2 - (sW / 20 * 3)-5, sH / 7, paint);
            canvas.drawText(Integer.toString(points), sW / 2 - (sW / 25 * ilosccyfr2), sH / 5, paint);
        }
        canvas.drawText("HIGH SCORE",sW/2-(sW/20*6),sH/4+sH/30,paint);
        canvas.drawText(Integer.toString(localPoints),sW/2-(sW/25*ilosccyfr),sH/3,paint);

        canvas.drawBitmap(coin2,5,5,paint);
        paint.setTextSize(soundsize);
        canvas.drawText(Integer.toString(localStorage.getInt("COINS",0)),soundsize+5,soundsize,paint);

    }
    public void setCenter(int centerx,int centery,int width,int height)
    {
        a=centerx-width/2;
        b=centery-height;
        c=centerx+width/2;
        d=centery;
        a2=centerx-barWidth2/2;
        b2=b+2*barHeight;
        c2=centerx+barWidth2/2;
        d2=d+2*barHeight;
    }
    public int getBarCoords(String side)
    {
        if(side=="left")
            return a;
        else if(side == "top")
            return b;
        else if(side=="right")
            return c;
        else if(side =="bottom")
            return d;
        else return 0;
    }
    public int get2BarCoords(String side)
    {
        if(side=="left")
            return a2;
        else if(side == "top")
            return b2;
        else if(side=="right")
            return c2;
        else if(side =="bottom")
            return d2;
        else return 0;
    }
    public int getSoundsize()
    {
        return soundsize;
    }
    public void changeimage(Bitmap image)
    {
        mute2=Bitmap.createScaledBitmap(image,soundsize,soundsize,false);
    }
    public void setPoints(int points)
    {
        this.points=points;
    }

}
