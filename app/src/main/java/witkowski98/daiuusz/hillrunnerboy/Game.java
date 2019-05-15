package witkowski98.daiuusz.hillrunnerboy;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

import static witkowski98.daiuusz.hillrunnerboy.GameThread.canvas;

public class Game extends SurfaceView implements SurfaceHolder.Callback
{
    private int losecounter;
    int randombound;
    int restartPos;
    public int points=0;
    int indextokill=-1;
    Paint textpaint;
    private Player player;
    private ArrayList<Platform> platforms;
    private ArrayList<Coin> coins;
    private GameThread thread;
    private int sW,sH;
    private int dir;
    private int coinCounter;
    private boolean gameOn;
    private boolean muted;
    private boolean ableToContinue=true;
    private boolean threadrunned=false;
    private int platformCounter=0;
    private int platformType=0;
    private int movedTimer=60;
    MediaPlayer mp;
    MediaPlayer lose;

    SharedPreferences localStorage;
    SharedPreferences.Editor localStorageEditor;
    private int playerMoved;
    int move1,move2,movestable;
    private boolean moved;
    int deadWaiter,dead2Waiter;
    Bitmap p0left,p1left,p2left,p3left,p4left,p0right,p1right,p2right,p3right,p4right;
    Bitmap startpng,continuepng,stonyPlatform,snowyStonyPlatform,sandyPlatform,snowySandyPlatform,dirtyPlatform,background,background2,mute,sound;
    Bitmap coin;
    boolean connected;
    MyMenu menu;
    Random random;
    int randomInt;
    int tempcoins;
    Main main;
    public Game(Context context,int sW, int sH,Main main,boolean connected)
    {

        super(context);
        loadGraphics();
        coinCounter=0;
        randombound=15;
        muted=false;
        this.connected=connected;
        moved=false;
        this.main=main;
        menu=new MyMenu(context,sW,sH,startpng,continuepng,mute,connected,coin);
        getHolder().addCallback(this);
        this.sW=sW;
        this.sH=sH;
        move1=sH/250;
        move2=sH/180;
        movestable=sH/350;
        thread=new GameThread(getHolder(),this);
        thread.setActivity(2);
        setFocusable(true);
        gameOn=true;
        losecounter=0;
        losecounter=0;
        indextokill=-1;
        background=BitmapFactory.decodeResource(getResources(),R.drawable.tlo);
        background2=Bitmap.createScaledBitmap(background,sW,sH,false);
        localStorage=PreferenceManager.getDefaultSharedPreferences(context);
        localStorageEditor = localStorage.edit();
        mp=MediaPlayer.create(context,R.raw.coin);
        lose=MediaPlayer.create(context,R.raw.lose);
        textpaint = new Paint();
        textpaint.setTextSize(sW/7);
        textpaint.setTypeface(Typeface.create("Arial",Typeface.ITALIC));
        textpaint.setColor(Color.argb(200,180,180,180)); //kolor prsotokąta

    }
    public void lose()
    {

        if(!muted)
            lose.start();
        menu.setPoints(points);
        if(localStorage.getInt("POINTS",0)<points)
        {
            localStorageEditor.putInt("POINTS", points);
            localStorageEditor.commit();
        }
        tempcoins=localStorage.getInt("COINS",0);
        tempcoins+=coinCounter;
        localStorageEditor.putInt("COINS",tempcoins);
        localStorageEditor.commit();
        tempcoins=0;
        coinCounter=0;
        restartPos = player.getDeadH();
        thread.setActivity(0);
    }
    public void newGame()
    {
        randombound=15;
        platformCounter=0;
        indextokill=-1;
        if(losecounter>=4 && connected && points>5) {
            losecounter=0;
            if(main.connected)
                main.showInteAd();
        }
        else {
            main.checkConnection(false);
            movedTimer=60;
            player = new Player(sW, sH,p0left,p1left,p2left,p3left,p4left,p0right,p1right,p2right,p3right,p4right);
            moved=false;
            ableToContinue=true;
            points=0;
            losecounter++;
            deadWaiter = 0;
            dead2Waiter = 0;
            platforms = new ArrayList<Platform>();
            coins = new ArrayList<Coin>();
            for (int i = 0; i < 6; i++) {
                platformCounter++;
                platforms.add(new Platform(sW, sH, platforms.size(), stonyPlatform,1));
            }

            thread.setActivity(1);
        }
    }
    public void continueGame()
    {
        moved=false;
        ableToContinue=false;
        deadWaiter=0;
        dead2Waiter=0;
        player = new Player(sW, sH,p0left,p1left,p2left,p3left,p4left,p0right,p1right,p2right,p3right,p4right);
        thread.setActivity(1);
        player.setY((double)restartPos);
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {

        thread.setRunning(true);
        //if(threadrunned=false)
            thread.start();
        threadrunned=true;

    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        thread.setRunning(true);
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        boolean retry=true;
        while(retry)
        {
            try{
                thread.setRunning(false);
                thread.join();
            }catch(InterruptedException e) {
                e.printStackTrace();
            }
            retry=false;
        }
    }
    public void setConnection(boolean connected)
    {
        this.connected=connected;
        menu.connected=connected;
    }
    private void loadGraphics()
    {
        p0left = BitmapFactory.decodeResource(getResources(),R.drawable.playerleft0);
        p1left = BitmapFactory.decodeResource(getResources(),R.drawable.playerleft1);
        p2left = BitmapFactory.decodeResource(getResources(),R.drawable.playerleft2);
        p3left = BitmapFactory.decodeResource(getResources(),R.drawable.playerleft3);
        p4left = BitmapFactory.decodeResource(getResources(),R.drawable.playerleft4);

        p0right = BitmapFactory.decodeResource(getResources(),R.drawable.playerright0);
        p1right = BitmapFactory.decodeResource(getResources(),R.drawable.playerright1);
        p2right = BitmapFactory.decodeResource(getResources(),R.drawable.playerright2);
        p3right = BitmapFactory.decodeResource(getResources(),R.drawable.playerright3);
        p4right = BitmapFactory.decodeResource(getResources(),R.drawable.playerright4);

        startpng = BitmapFactory.decodeResource(getResources(),R.drawable.start);
        continuepng = BitmapFactory.decodeResource(getResources(),R.drawable.continuepng);
        stonyPlatform=BitmapFactory.decodeResource(getResources(),R.drawable.stonyplatform);
        snowyStonyPlatform=BitmapFactory.decodeResource(getResources(),R.drawable.snowystonyplatform);
        sandyPlatform=BitmapFactory.decodeResource(getResources(),R.drawable.sandyplatform);
        snowySandyPlatform=BitmapFactory.decodeResource(getResources(),R.drawable.snowysandyplatform);
        dirtyPlatform=BitmapFactory.decodeResource(getResources(),R.drawable.dirtyplatform);
        mute=BitmapFactory.decodeResource(getResources(),R.drawable.mute);
        sound=BitmapFactory.decodeResource(getResources(),R.drawable.sound);

        coin = BitmapFactory.decodeResource(getResources(),R.drawable.coin);

    }
    public void drawMyStuff(final Canvas canvas)
    {

        Paint paint =new Paint();

        canvas.drawBitmap(background2,0,0,paint);
        player.drawPlayer(canvas);
        for(Platform x:platforms) {
            if (x.getPointStatus() == 1 && player.getStatus())
            {
                if(points==8)
                    movestable=sH/330;
                else if(points==16)
                    movestable=sH/310;
                else if(points==24)
                    movestable=sH/290;
                else if(points==32)
                    movestable=sH/270;
                else if(points==40)
                    movestable=sH/250;
                else if(points==50)
                {
                    movestable=sH/220;
                    move1=sH/200;
                }
                points++;
                if(!muted)
                 mp.start();
                x.setPointStatus(2);
            }
            if(player.getStatus())
            {
                if (player.getY() < sH / 5 * 2 || playerMoved == 2) {
                    x.moveUp(move2);
                    if(movedTimer<=0)
                        moved=true;

                } else if (player.getY() < sH / 5 * 3 || playerMoved == 1) {
                    x.moveUp(move1);
                    if(movedTimer<=0)
                        moved=true;
                } else if(moved)
                {
                    x.moveUp(movestable);
                }
            }

            x.drawPlatform(canvas,player);

            if(player.getStatus())
            {
                if(x.checkColission(player))
                {
                    player.setBlockPoint(x.getTop());
                    if(platforms.get(platforms.indexOf(x)+1).dirNotChanged()) {
                        dir=player.setDirection(platforms.get(platforms.indexOf(x) + 1).LgetR(), platforms.get(platforms.indexOf(x) + 1).RgetL());
                        platforms.get(platforms.indexOf(x) + 1).setDirChanged();
                    }
                    if(!platforms.get(platforms.indexOf(x)+1).dirNotChanged())
                    {
                        player.changeDirection(dir);
                    }
                }
            }



        }
        for(Coin x:coins)
        {
            if(player.getStatus())
            {
                if(player.getStatus())
                {
                    if (player.getY() < sH / 5 * 2 || playerMoved == 2)
                    {
                        x.moveUp(move2);
                    } else if (player.getY() < sH / 5 * 3 || playerMoved == 1)
                    {
                        x.moveUp(move1);
                    } else if(moved)
                    {
                        x.moveUp(movestable);
                    }
                }
            }
            x.draw(canvas);
        }
        for(Coin x:coins)
        {
            if(x.checkColission(player))
            {
                if(!muted)
                    mp.start();
                coinCounter++;
                indextokill=coins.indexOf(x);

                break;
            }
        }
        if(indextokill!=-1) {
            coins.remove(indextokill);
            indextokill = -1;
        }
        if(player.getStatus())
        {
            canvas.drawText(Integer.toString(points),0,sW/7,textpaint);
        }

    }
    public void drawMenu()
    {
        if(thread.getActivity()==0) {
            if (deadWaiter < 150)
                deadWaiter += 10;
            else
                deadWaiter = 150;
            if(dead2Waiter<250)
                dead2Waiter+=10;
            else
                dead2Waiter=255;
        }
        else
        {
            dead2Waiter=255;
            deadWaiter=255;
        }
        menu.draw(canvas,deadWaiter,dead2Waiter,ableToContinue);
    }
    public void update()
    {
        for(Coin x:coins)
            if(x.getY()>sH)
                indextokill=coins.indexOf(x);
        if(indextokill!=-1) {
            coins.remove(indextokill);
            indextokill=-1;
        }
        if(points==1)
            player.speedUp(1*15);
        else if(points==2*15)
            player.speedUp(2);
        else if(points==3*15)
            player.speedUp(3);
        else if(points==4*15)
            player.speedUp(4);
        else if(points==5*15)
            player.speedUp(5);
        else if(points==6*15)
            player.speedUp(6);
        else if(points==7*15)
            player.speedUp(7);
        else if(points==8*15)
            player.speedUp(8);
        else if(points==9*15)
            player.speedUp(9);
        if(player.getY()>sH+20 && player.getStatus()&& moved && movedTimer==0) {
            player.kill(0);
            ableToContinue=false;
        }
        if(movedTimer>0)
            movedTimer--;
        if(player.getStatus()) {
            if (platforms.get(0).getY() >= sH) {
                platformCounter++;
                platforms.remove(0);
                if(platformCounter<=10)
                    platforms.add(new Platform(sW, sH, platforms.size(),stonyPlatform,1));
                else if(platformCounter<=20)
                    platforms.add(new Platform(sW, sH, platforms.size(),snowyStonyPlatform,2));
                else if(platformCounter<=30)
                    platforms.add(new Platform(sW, sH, platforms.size(),sandyPlatform,3));
                else if(platformCounter<=40)
                    platforms.add(new Platform(sW, sH, platforms.size(),snowySandyPlatform,4));
                else
                    platforms.add(new Platform(sW, sH, platforms.size(),dirtyPlatform,5));
                if(coinRandomer())
                {
                    coins.add(new Coin(sW,sH,coins.size(),coin,1,platforms.get(platforms.size()-1).getTop()));
                }
            }
        }
        player.horizontalMove();

        player.verticalMove();
        if(player.getStatus()) {
            if (player.getY() < sH / 5 * 2) {
                player.moveUp(move2);
                playerMoved = 2;
            } else if (player.getY() < sH / 5 * 3) {
                player.moveUp(move1);
                playerMoved = 1;
            } else if(moved)
            {
                player.moveUp(movestable);
                playerMoved = 3;
            }
            else playerMoved = 0;
        }
        if(!player.getStatus() && thread.getActivity()!=0)
        {
            lose();
        }
    }
    private boolean coinRandomer()
    {
        if(points%2==0)
            randombound++;
        random = new Random();
        randomInt = random.nextInt(100);
        if(randomInt<randombound)
            return true;
        else
            return false;
    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction()==MotionEvent.ACTION_DOWN)
        {
            if(thread.getActivity()==1 && player.getStatus()) {
                player.jump();

            }
            else if(thread.getActivity()!=1)
            {
                if(event.getX()>menu.getBarCoords("left") && event.getX()<menu.getBarCoords("right")
                        && event.getY()>menu.getBarCoords("top") && event.getY()<menu.getBarCoords("bottom"))
                {
                    newGame();
                }
                if(event.getX()>menu.get2BarCoords("left") && event.getX()<menu.get2BarCoords("right")
                        && event.getY()>menu.get2BarCoords("top") && event.getY()<menu.get2BarCoords("bottom") && ableToContinue)
                {
                    if(main.connected)
                        main.showRewAd();
                }
                if(event.getX()>=sW-menu.getSoundsize() && event.getY()<=menu.getSoundsize()){
                    if(!muted)
                    {
                        menu.changeimage(sound);
                        muted=true;
                    }
                    else {
                        menu.changeimage(mute);
                        muted = false;
                    }
                }
            }
        }
        return true;
    }

}
