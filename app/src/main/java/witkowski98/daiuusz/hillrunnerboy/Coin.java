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

public class Coin
{
    int sW, sH;
    Bitmap texture;
    int texturetype;
    int coinsize;
    int x,y;
    Paint paint;
    int index;
    Random random;
    int randomInt;
    public Coin(int sW, int sH, int i, Bitmap texture,int texturetype,int pt)
    {
        this.sH=sH;
        this.sW=sW;
        coinsize=sW/15;
        paint = new Paint();
        setTexture(texture,coinsize);
        x=coinRandomer();
        y=pt-coinsize*2;
        index=i;

    }
    private void setTexture(Bitmap texture, int coinsize)
    {
        this.texture= Bitmap.createScaledBitmap(texture,coinsize,coinsize,false);
    }
    boolean checkColission(Player player)
    {
        if((player.getY()-player.getHeight()<y+coinsize && player.getY()>y) && player.getX()+player.getWidth()>x && player.getX()<x+coinsize) {
            return true;
        }
        else
            return false;
    }
    public int getIndex()
    {
        return index;
    }
    public int getY()
    {
        return y;
    }
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(texture,x,y,paint);
    }
    public void moveUp(int moved)
    {
        y+=moved;
    }
    private int coinRandomer()
    {
        random = new Random();
        randomInt = random.nextInt(sW-coinsize);
        return randomInt;
    }
}