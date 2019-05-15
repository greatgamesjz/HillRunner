package witkowski98.daiuusz.hillrunnerboy;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.constraint.solver.widgets.Rectangle;
import android.widget.ImageView;

public class Player {
    private double sW,sH,pW,pH,sPX,sPY,movementSpeed,amovementSpeed;
    private double jumpStartSpeed=45,jumpSlower=3,fallStartSpeed=0,fallFaster=3;
    private double blockPoint;
    private double jumpSpeed,fallSpeed=fallStartSpeed;
    private boolean direction = true;
    private Paint paint;
    private Rect player;
    private boolean alive;
    private int platformPosH;
    private boolean directionHasChanged;
    private boolean leftWallHitted=false,rightWallHitted=false;
    private Bitmap p1left,p2left,p3left,p4left,p0left;
    private Bitmap p1l,p2l,p3l,p4l,p0l;
    private Bitmap p1right,p2right,p3right,p4right,p0right;
    private Bitmap p1r,p2r,p3r,p4r,p0r;
    private int stepcounter=0;
    boolean foot;


    //private boolean landed=true;
    public Player(int sW, int sH, Bitmap p0left, Bitmap p1left, Bitmap p2left, Bitmap p3left,Bitmap p4left,Bitmap p0right, Bitmap p1right, Bitmap p2right, Bitmap p3right,Bitmap p4right)
    {
        this.p0left=p0left;
        this.p1left=p1left;
        this.p2left=p2left;
        this.p3left=p3left;
        this.p4left=p4left;
        this.p0right=p0right;
        this.p1right=p1right;
        this.p2right=p2right;
        this.p3right=p3right;
        this.p4right=p4right;
        foot = true;
        jumpStartSpeed=sH/40d;
        jumpSlower=sH/600d;
        fallFaster=sH/600d;
        alive=true;
        this.sW=sW;
        this.sH=sH;
        pW=sW/15d;
        pH=sH/15d;
        sPX = sW/2d;
        sPY=sH;
        player = new Rect();
        paint=new Paint();
        paint.setColor(Color.GREEN);
        blockPoint=sH;
        movementSpeed=sW/50d;
        amovementSpeed=sW/50d;
        setPlayer();

    }
    public void stopPlayer()
    {
        amovementSpeed=0;
    }
    public void setLeftWallHitted()
    {
        leftWallHitted=false;
    }
    public void setRightWallHitted()
    {
        rightWallHitted=false;
    }
    public boolean getStatus()
    {
        return alive;
    }
    public double getX()
    {
        return sPX;
    }
    public int getDeadH(){return platformPosH;}
    public void setX(double X)
    {
        sPX=X;
    }
    public double getY()
    {
        return sPY;
    }
    public void setY(double Y)
    {
        sPY=Y;
    }
    public double getWidth()
    {
        return pW;
    }
    public void setWidth(int W)
    {
        pW=W;
    }
    public double getHeight()
    {
        return pH;
    }
    public void setHeight(int H)
    {
        pH=H;
    }
    public void moveUp(int i)
    {
        setY(getY()+i);
    }
    public void speedUp(int number)
    {
        if(number==1) {
            movementSpeed = sW / 45d;
            amovementSpeed = sW / 45d;
        }else if(number==2) {
            movementSpeed = sW / 40d;
            amovementSpeed = sW / 40d;
            jumpStartSpeed=sH/38d;
        }else if(number==3)
        {
            movementSpeed=sW/35d;
            amovementSpeed=sW/35d;
            jumpStartSpeed=sH/30d;
            jumpSlower=sH/350d;
        }else if(number==4)
        {
            movementSpeed=sW/32d;
            amovementSpeed=sW/32d;
            jumpStartSpeed=sH/28d;
            jumpSlower=sH/300d;
        }else if(number==5)
        {
            movementSpeed=sW/30d;
            amovementSpeed=sW/30d;
            jumpStartSpeed=sH/28d;
            jumpSlower=sH/300d;
        }
        else if(number==6)
        {
            movementSpeed=sW/28d;
            amovementSpeed=sW/28d;
            jumpStartSpeed=sH/28d;
            jumpSlower=sH/300d;
        }
        else if(number==7)
        {
            movementSpeed=sW/26d;
            amovementSpeed=sW/26d;
            jumpStartSpeed=sH/26d;
            jumpSlower=sH/280d;
        }
        else if(number==8)
        {
            movementSpeed=sW/24d;
            amovementSpeed=sW/24d;
            jumpStartSpeed=sH/26d;
            jumpSlower=sH/280d;
        }
        else if(number==9)
        {
            movementSpeed=sW/22d;
            amovementSpeed=sW/22d;
            jumpStartSpeed=sH/26d;
            jumpSlower=sH/280d;
        }
    }
    private void setPlayer()
    {
        p0l=Bitmap.createScaledBitmap(p0left,(int)getWidth()+(int)getWidth()/3,(int)getHeight(),false);
        p1l=Bitmap.createScaledBitmap(p1left,(int)getWidth()+(int)getWidth()/3,(int)getHeight(),false);
        p2l=Bitmap.createScaledBitmap(p2left,(int)getWidth()+(int)getWidth()/3,(int)getHeight(),false);
        p3l=Bitmap.createScaledBitmap(p3left,(int)getWidth()+(int)getWidth()/3,(int)getHeight(),false);
        p4l=Bitmap.createScaledBitmap(p4left,(int)getWidth()+(int)getWidth()/3,(int)getHeight(),false);
        p0r=Bitmap.createScaledBitmap(p0right,(int)getWidth()+(int)getWidth()/3,(int)getHeight(),false);
        p1r=Bitmap.createScaledBitmap(p1right,(int)getWidth()+(int)getWidth()/3,(int)getHeight(),false);
        p2r=Bitmap.createScaledBitmap(p2right,(int)getWidth()+(int)getWidth()/3,(int)getHeight(),false);
        p3r=Bitmap.createScaledBitmap(p3right,(int)getWidth()+(int)getWidth()/3,(int)getHeight(),false);
        p4r=Bitmap.createScaledBitmap(p4right,(int)getWidth()+(int)getWidth()/3,(int)getHeight(),false);
    }
    public void kill(int platformPosH)
    {
        this.platformPosH=platformPosH;
        jumpSpeed=0;
        blockPoint=sH+getHeight();
        alive=false;
    }

    public void drawPlayer(Canvas canvas)
    {
        if(direction==true) {
            if(stepcounter==0 ||stepcounter==1)
                canvas.drawBitmap(p0r, (float) sPX, (float) (sPY - pH), paint);
            else if(stepcounter==2||stepcounter==3)
                canvas.drawBitmap(p1r, (float) sPX, (float) (sPY - pH), paint);
            else if(stepcounter==4||stepcounter==5)
                canvas.drawBitmap(p2r, (float) sPX, (float) (sPY - pH), paint);
            else if(stepcounter==6||stepcounter==7)
                canvas.drawBitmap(p1r, (float) sPX, (float) (sPY - pH), paint);
            else if(stepcounter==8||stepcounter==9)
                canvas.drawBitmap(p0r, (float) sPX, (float) (sPY - pH), paint);
            else if(stepcounter==10||stepcounter==11)
                canvas.drawBitmap(p3r, (float) sPX, (float) (sPY - pH), paint);
            else if(stepcounter==12||stepcounter==13)
                canvas.drawBitmap(p4r, (float) sPX, (float) (sPY - pH), paint);
            else if(stepcounter==14||stepcounter==15)
                canvas.drawBitmap(p3r, (float) sPX, (float) (sPY - pH), paint);
        }
        else {
            if(stepcounter==0 ||stepcounter==1)
                canvas.drawBitmap(p0l, (float) sPX, (float) (sPY - pH), paint);
            else if(stepcounter==2||stepcounter==3)
                canvas.drawBitmap(p1l, (float) sPX, (float) (sPY - pH), paint);
            else if(stepcounter==4||stepcounter==5)
                canvas.drawBitmap(p2l, (float) sPX, (float) (sPY - pH), paint);
            else if(stepcounter==6||stepcounter==7)
                canvas.drawBitmap(p1l, (float) sPX, (float) (sPY - pH), paint);
            else if(stepcounter==8||stepcounter==9)
                canvas.drawBitmap(p0l, (float) sPX, (float) (sPY - pH), paint);
            else if(stepcounter==10||stepcounter==11)
                canvas.drawBitmap(p3l, (float) sPX, (float) (sPY - pH), paint);
            else if(stepcounter==12||stepcounter==13)
                canvas.drawBitmap(p4l, (float) sPX, (float) (sPY - pH), paint);
            else if(stepcounter==14||stepcounter==15)
                canvas.drawBitmap(p3l, (float) sPX, (float) (sPY - pH), paint);

        }
        stepcounter++;
        if(stepcounter==16)
            stepcounter=0;
    }
    public void horizontalMove()
    {

        if(direction==true)
        {
            if (getX() + getWidth() >= sW)
            {
                direction = false;
                amovementSpeed = movementSpeed;
                directionHasChanged = true;
                if(getY()+getHeight()<sH-getHeight()) {
                    rightWallHitted = true;
                }
            }
        }
        else {
            if (getX() <= 0)
            {
                direction = true;
                amovementSpeed = movementSpeed;
                directionHasChanged = true;
                if(getY()+getHeight()<sH-getHeight()) {
                    leftWallHitted = true;
                }
            }
        }
        if(direction)
            setX(getX()+amovementSpeed);
        else
            setX(getX()-amovementSpeed);
    }
    public void jump()
    {
        if(jumpSpeed<=0 && fallSpeed<=0) {
            jumpSpeed = jumpStartSpeed;

        }
    }
    public int setDirection(int xl,int xr)
    {
        if(!direction && xl>getX()+getWidth()) {
            directionHasChanged=false;
            return 1;
        }
        if(direction && xr<getX()) {
            directionHasChanged=false;
            return -1;
        }
        return 0;
    }
    public void changeDirection(int dir)
    {
        if(dir!=0)
        {
            if (amovementSpeed > 1 && directionHasChanged==false)
            {
                amovementSpeed-=sW/800d;
                directionHasChanged = false;
            } else if (amovementSpeed <= 1 && directionHasChanged == false)
            {
                amovementSpeed = 0;
                if (dir == 1)
                    direction = true;
                else
                    direction = false;
                directionHasChanged = true;

            }
            if (amovementSpeed < movementSpeed - 1 && directionHasChanged == true)
                amovementSpeed+=sW/800;
            else if (amovementSpeed >= movementSpeed - 1 && directionHasChanged == true)
            {
                amovementSpeed = movementSpeed;
            }
        }
    }
    public void setBlockPoint(double blockPoint)
    {
        if(getStatus())
        {
            this.blockPoint=blockPoint;
        }
    }
    public double getBlockPoint()
    {
        return blockPoint;
    }
    public void verticalMove()
    {
        if(jumpSpeed>0) {
            setY(getY() - jumpSpeed);
            jumpSpeed -= jumpSlower;
            fallSpeed=0;
        }
        else
        {
            if(getY()<blockPoint)
            {
                if(fallSpeed<=blockPoint-getY()) {
                    setY(getY() + fallSpeed);
                    fallSpeed += fallFaster;
                }
                else {
                    setY(blockPoint);
                }
            }
            else {
                fallSpeed = 0;
            }
        }
    }
}
