package witkowski98.daiuusz.hillrunnerboy;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread {
    private int FPS = 30;
    private double avgFPS;
    private SurfaceHolder surfaceHolder;
    private Game game;
    int activity;
    private boolean running;
    public static Canvas canvas;
    public GameThread(SurfaceHolder surfaceHolder, Game game)
    {
        super();
        this.surfaceHolder = surfaceHolder;

        this.game = game;
    }
    @Override
    public void run()
    {

        long startTime;
        long timeMillis;
        long waitTIme;
        long totalTime=0;
        int frameCount=0;
        long targetTime = 1000/FPS;
        while(running)
        {
            startTime=System.nanoTime();
            canvas=null;
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder)
                {
                    if(activity!=2) {
                        this.game.update();
                        this.game.drawMyStuff(canvas);
                    }
                    if(activity!=1 && activity!=3)
                    {
                        this.game.drawMenu();
                    }
                    /*if(activity!=1 && activity!=2)
                    {
                        this.game.drawRetry();
                    }*/

                }
            }catch(Exception e) {
                e.printStackTrace();
            }finally {
                if(canvas != null)
                {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            timeMillis = (System.nanoTime() - startTime)/1000000;
            waitTIme = targetTime - timeMillis;
            try{
                this.sleep(waitTIme);
            }catch (Exception e){
                //e.printStackTrace();
            }
            totalTime+=System.nanoTime()-startTime;
            frameCount++;
            if(frameCount==FPS)
            {
                avgFPS=1000/((totalTime/frameCount)/1000000);
                frameCount=0;
                totalTime=0;
                //System.out.println(avgFPS);
            }
        }
    }
    public void setRunning(boolean isRunning)
    {
        running=isRunning;
    }
    public void setActivity(int activity)
    {
        this.activity = activity;
    }
    public int getActivity()
    {
        return activity;
    }
}
