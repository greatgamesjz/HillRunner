package witkowski98.daiuusz.hillrunnerboy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.constraint.solver.widgets.Rectangle;

import java.util.Random;

public class Platform
{
    Matrix matrix=new Matrix();
    int sW,sH,x,y,sH2;
    int bold=40;
    int verticalSpace = 300;
    int horisontalSpace;
    private Paint paint;
    private Rect platformL;
    private Rect platformR;
    private Rectangle closeBar;
    private Random li;
    private int right;
    private boolean closed=false ,fullyclosed=false;
    double rotation=Math.PI;
    float x0,y0,x1,x2,x3,y1,y2,y3,xs,ys;
    float tx,ty;
    double pom;
    float sin1,cos1;
    private boolean firstDirChange=true;
    private boolean closingStarted=false;
    private int pointstatus=0;
    private float testRotation=90;
    private Bitmap texture,texture2,textureL,textureR,textureBar;
    int textureRight;
    int addBold;
    Path path = new Path();


    public Platform(int sW, int sH, int i, Bitmap texture,int texturetype)
    {
        this.sW=sW;
        this.sH=sH;
        platformL = new Rect();
        platformR = new Rect();
        horisontalSpace = sW/4;
        paint = new Paint();
        if(texturetype==2 || texturetype==4)
            paint.setColor(Color.WHITE);
        else if(texturetype==1)
            paint.setColor(Color.GRAY);
        else if (texturetype==3)
            paint.setColor(Color.rgb(127,75,2));
        else if(texturetype==5)
            paint.setColor(Color.BLACK);
        bold=sH/45;
        verticalSpace=sH/6;

        if(texturetype==1) {
            addBold = sH / 55;
            random(22);
            right=right*sW/22;
        }
        else if(texturetype==2) {
            addBold = sH / 43;
            random(22);
            right=right*sW/22;
        }
        else if (texturetype==3)
        {
            addBold=sH/55;
            random(22);
            right=right*sW/22;
        }
        else if(texturetype==4)
        {
            addBold=sH/43;
            random(22);
            right=right*sW/22;
        }
        else
        {
            addBold=sH/38;
            random(22);
            right=right*sW/22;
        }

        platformL.top=sH-bold-(i+1)*verticalSpace;
        platformL.left=0;
        platformL.right=right;
        platformL.bottom=platformL.top+bold;

        platformR.top=platformL.top;
        platformR.left=platformL.right+horisontalSpace;
        platformR.right=sW;
        platformR.bottom=platformL.bottom;
        this.texture=texture;
        textureRight=right;

        setTexture();






    }
    public void random(int bound)
    {
        do {
            li = new Random();
            right = li.nextInt(bound) + 1;
        }while (right<=1 || right>=bound-6);
    }
    private void setTexture()
    {
        try {
            texture2 = Bitmap.createScaledBitmap(texture, sW, bold + addBold, false);
            textureL = Bitmap.createBitmap(texture2, 0, 0, textureRight, bold + addBold);
            textureR = Bitmap.createBitmap(texture2, right, 0, sW - horisontalSpace - textureRight, bold + addBold);
        }catch(Exception e)
        {
        }
    }
    public void moveUp(int moved)
    {

        platformL.top+=moved;
        platformL.bottom+=moved;
        platformR.top+=moved;
        platformR.bottom+=moved;

        y0 +=moved;
        y1 +=moved;
        y2 +=moved;
        y3 +=moved;

        path.reset();
        path.moveTo(x0,y0);
        path.lineTo(x1,y1);
        path.lineTo(x2,y2);
        path.lineTo(x3,y3);
        path.close();

    }
    public int getTop()
    {
        return platformL.top;
    }
    public int getY()
    {
        return platformL.top;
    }
    public void close()
    {
        if(getPointStatus()==0)
            setPointStatus(1);
        sin1=(float)Math.sin(rotation);
        cos1=(float)Math.cos(rotation);

        setVerticles();

        path.reset();
        path.moveTo(x0,y0);
        path.lineTo(x1,y1);
        path.lineTo(x2,y2);
        path.lineTo(x3,y3);
        path.close();


        if(rotation>0)
            rotation-=Math.PI/10;
        else {
            fullyclosed = true;
            platformL.right=sW;
        }
        testRotation-=0.1f;
    }
    public void setPointStatus(int status)
    {
        pointstatus=status;
    }
    public int getPointStatus()
    {
        return pointstatus;
    }
    private void setVerticles()
    {
        x0 = platformL.right;
        y0 = platformL.top;
        x1 = platformL.right;
        x2 = platformR.left;
        x3 = platformR.left;
        y1 = platformL.bottom;
        y2 = platformR.bottom;
        y3 = platformR.top;
        xs = platformL.right;
        ys = y0+bold/2;

        x0-=xs;
        y0-=ys;
        x1-=xs;
        y1-=ys;
        x2-=xs;
        y2-=ys;
        x3-=xs;
        y3-=ys;

        tx=x1*cos1-y1*sin1;
        ty=x1*sin1+y1*cos1;
        x1=tx;
        y1=ty;

        tx=x0*cos1-y0*sin1;
        ty=x0*sin1+y0*cos1;
        x0=tx;
        y0=ty;

        tx=x2*cos1-y2*sin1;
        ty=x2*sin1+y2*cos1;
        x2=tx;
        y2=ty;

        tx=x3*cos1-y3*sin1;
        ty=x3*sin1+y3*cos1;
        x3=tx;
        y3=ty;

        xs*=-1;
        ys*=-1;
        x0-=xs;
        y0-=ys;
        x1-=xs;
        y1-=ys;
        x2-=xs;
        y2-=ys;
        x3-=xs;
        y3-=ys;

    }
    public int LgetR()
    {
        return platformL.right;
    }
    public int RgetL()
    {
        return platformR.left;
    }
    public boolean dirNotChanged()
    {
        return firstDirChange;
    }
    public void setDirChanged()
    {
        firstDirChange=false;
    }
    public void drawPlatform(Canvas canvas, Player player)
    {



        if(closed && !fullyclosed) {
            if(!closingStarted)
            {
                player.setLeftWallHitted();
                player.setRightWallHitted();
                closingStarted=true;
            }
            close();
            canvas.drawPath(path,paint);

        }
        if(closed && fullyclosed)
            canvas.drawPath(path,paint);
        canvas.drawBitmap(textureL,0,platformL.top-addBold/2,paint);
        canvas.drawBitmap(textureR,platformR.left,platformL.top-addBold/2,paint);
    }
    public boolean checkColission(Player player)
    {
        if(player.getStatus()) {
            if (player.getY() - 8 > platformL.top && player.getY() - player.getHeight() < platformL.bottom) {

                if (player.getX() + player.getWidth() > platformR.left) {
                    if(player.getY()-player.getHeight()<platformL.bottom-bold)
                        player.stopPlayer();
                    player.kill(getY());
                }
                if (player.getX() < platformL.right) {
                    if(player.getY()-player.getHeight()<platformL.bottom-bold)
                        player.stopPlayer();
                    player.kill(getY());
                }
            }
            if ((player.getY() - player.getHeight() < platformL.bottom && player.getY() - 8 >= platformL.top - verticalSpace)) {
                closed = true;

                player.setBlockPoint(platformL.top);


                return true;

            }
        }
        return false;
    }

}
