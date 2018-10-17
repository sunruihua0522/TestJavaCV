package com.example.rickysun.testjavacv;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.widget.ImageView;
import android.widget.TextView;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.AndroidFrameConverter;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import java.io.File;
import java.util.Date;
import static org.bytedeco.javacpp.opencv_core.IPL_DEPTH_8U;
import static org.bytedeco.javacpp.opencv_core.cvFlip;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    // cv
    private SurfaceView surfaceView=null;
    private AndroidFrameConverter frameConverter=new AndroidFrameConverter();
    private FFmpegFrameGrabber grabber=null;
    private ImageView imageView=null;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Drawable drawable = this.idToDrawable(R.drawable.rr);
        Bitmap bitmap = this.drawableToBitmap(drawable);
        // 将Bitmap转化为IplImage
        IplImage iplImage = this.bitmapToIplImage(bitmap);
        // 处理图像，比如旋转图像

        cvFlip(iplImage, iplImage, 0);
        // 再将IplImage转化为Bitmap
        bitmap = this.IplImageToBitmap(iplImage);
        imageView=(ImageView) findViewById(R.id.imgView);
        imageView.setImageBitmap(bitmap);



    }
    /**
     * IplImage转化为Bitmap
     * @param iplImage
     * @return
     */
    public Bitmap IplImageToBitmap(IplImage iplImage) {
        Bitmap bitmap = null;
        bitmap = Bitmap.createBitmap(iplImage.width(), iplImage.height(),
                Bitmap.Config.ARGB_8888);
        bitmap.copyPixelsFromBuffer(iplImage.getByteBuffer());
        return bitmap;
    }

    /**
     * Bitmap转化为IplImage
     * @param bitmap
     * @return
     */
    public IplImage bitmapToIplImage(Bitmap bitmap) {
        IplImage iplImage;
        iplImage = IplImage.create(bitmap.getWidth(), bitmap.getHeight(),
                IPL_DEPTH_8U, 4);
        bitmap.copyPixelsToBuffer(iplImage.getByteBuffer());
        return iplImage;
    }

    /**
     * 将资源ID转化为Drawable
     * @param id
     * @return
     */
    public Drawable idToDrawable(int id) {
        return this.getResources().getDrawable(id);
    }

    /**
     * 将Drawable转化为Bitmap
     * @param drawable
     * @return
     */
    public Bitmap drawableToBitmap(Drawable drawable) {
        if(drawable == null)
            return null;
        return ((BitmapDrawable)drawable).getBitmap();
    }

}
