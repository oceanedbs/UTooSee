package fr.volvo.utc.utoosee;

import java.util.ArrayList;
import java.util.List;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.utils.Converters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.SeekBar;

public class OpenCvActivity extends Activity implements OnTouchListener, CvCameraViewListener2 {
    private static final String  TAG              = "MainActivity";

    private boolean              mIsColorSelected = false;
    private Mat                  mRgba;
    private Mat                  contourRgba;
    private Scalar               mBlobColorRgba;
    private Scalar               mBlobColorHsv;
    private ColorBlobDetector    mDetector;
    private Mat                  mSpectrum;
    private Size                 SPECTRUM_SIZE;
    private Scalar               CONTOUR_COLOR;
    private boolean capturedPicture;

    private CameraBridgeViewBase mOpenCvCameraView;

    private BaseLoaderCallback  mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");
                    mOpenCvCameraView.enableView();
                    mOpenCvCameraView.setOnTouchListener(OpenCvActivity.this);
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    public OpenCvActivity() {
        Log.i(TAG, "Instantiated new " + this.getClass());
    }

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "called onCreate");
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_open_cv);
        capturedPicture = false;
        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.color_blob_detection_activity_surface_view);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);
        final Button capture = (Button) findViewById(R.id.capture);
        final Button returnButton = (Button) findViewById(R.id.returnButton);
        final Button sendPicture = (Button) findViewById(R.id.sendPicture);
        sendPicture.setEnabled(false);

        returnButton.setVisibility(View.INVISIBLE);
        capture.setVisibility(View.VISIBLE);
        capture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                capturedPicture = true;
                returnButton.setVisibility(View.VISIBLE);
sendPicture.setEnabled(true);
                capture.setVisibility(View.INVISIBLE);
            }
        });
        returnButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                capturedPicture = false;
                sendPicture.setEnabled(false);
                returnButton.setVisibility(View.INVISIBLE);
                capture.setVisibility(View.VISIBLE);
            }
        });
        sendPicture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
//close activity
                Intent intent = new Intent (OpenCvActivity.this, Measuring.class);
                startActivity(intent);
            }
        });
    /*    SeekBar simpleSeekBar = (SeekBar) findViewById(R.id.seekBarPrecision); // initiate the Seek bar

        int maxValue=simpleSeekBar.getMax(); // get maximum value of the Seek bar
        simpleSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                if(progress == 0)
                    progress = 1;
                mDetector.setColorRadius(new Scalar(progress, progress, progress, 0));
                mDetector.setHsvColor(mBlobColorHsv);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });
*/

    }

    private void captureFunc() {
       /* List<MatOfPoint> contours = mDetector.getContours();
        if(contours.size()==0)
        {
            return;
        }
        Log.i(TAG, "Button");*/
       capturedPicture = true;
    /*    Mat image = mRgba.clone();
        for(int i=0; i< contours.size();i++){
            if (Imgproc.contourArea(contours.get(i)) > 50 ){
                Rect rect = Imgproc.boundingRect(contours.get(i));
                if (rect.height > 28){
            //        Imgproc.rectangle(image, new Point(rect.x,rect.y), new Point(rect.x+rect.width,rect.y+rect.height),new Scalar(0,0,255));
                    Mat ROI = image.submat(rect.y, rect.y + rect.height, rect.x, rect.x + rect.width);

                    Imgcodecs.imwrite(Environment.getExternalStorageDirectory() + "/test4.png",ROI);

                }
            }
        }
/*
        int max = 0;
        int index = 0;
        for(int k = 0; k<contours.size(); k++){
         //   if(contours.get(k).toList().)
        }
        int m = mRgba.cols();
        int n = mRgba.rows();
        Mat mask = new MatOfInt();
        mask.create(m,n, 0);
        MatOfPoint matOfPoint = contours.get(index);

        for (int i = 0; i < matOfPoint.rows(); i++) {
                mask.put((int)matOfPoint.get(i,0)[0],(int)matOfPoint.get(i,0)[1], 1);
        }
        for(int i =0; i<n;i++) {
            for (int j = 0; j < m; j++) {
                if (mask.get(j, i)[0] == 1) {
                    mask.put(j, i, mRgba.get(j, i));
                    Log.i(TAG, String.format("i : %d j : %d value : %d", i, j, (int)mRgba.get(j, i)[0]));
                }
            }
        }*/
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            Log.d(TAG, "OpenCV library found inside package. Using it!");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    public void onCameraViewStarted(int width, int height) {
        mRgba = new Mat(height, width, CvType.CV_8UC4);
        mDetector = new ColorBlobDetector();
        mSpectrum = new Mat();
        mBlobColorRgba = new Scalar(255);
        mBlobColorHsv = new Scalar(255);
        SPECTRUM_SIZE = new Size(200, 64);
        CONTOUR_COLOR = new Scalar(255,0,0,255);
    }

    public void onCameraViewStopped() {
        mRgba.release();
    }

    public boolean onTouch(View v, MotionEvent event) {
        int cols = mRgba.cols();
        int rows = mRgba.rows();

        int xOffset = (mOpenCvCameraView.getWidth() - cols) / 2;
        int yOffset = (mOpenCvCameraView.getHeight() - rows) / 2;

        int x = (int)event.getX() - xOffset;
        int y = (int)event.getY() - yOffset;

        Log.i(TAG, "Touch image coordinates: (" + x + ", " + y + ")");

        if ((x < 0) || (y < 0) || (x > cols) || (y > rows)) return false;

        Rect touchedRect = new Rect();

        touchedRect.x = (x>4) ? x-4 : 0;
        touchedRect.y = (y>4) ? y-4 : 0;

        touchedRect.width = (x+4 < cols) ? x + 4 - touchedRect.x : cols - touchedRect.x;
        touchedRect.height = (y+4 < rows) ? y + 4 - touchedRect.y : rows - touchedRect.y;

        Mat touchedRegionRgba = mRgba.submat(touchedRect);

        Mat touchedRegionHsv = new Mat();
        Imgproc.cvtColor(touchedRegionRgba, touchedRegionHsv, Imgproc.COLOR_RGB2HSV_FULL);

        // Calculate average color of touched region
        mBlobColorHsv = Core.sumElems(touchedRegionHsv);
        int pointCount = touchedRect.width*touchedRect.height;
        for (int i = 0; i < mBlobColorHsv.val.length; i++)
            mBlobColorHsv.val[i] /= pointCount;

        mBlobColorRgba = converScalarHsv2Rgba(mBlobColorHsv);

        Log.i(TAG, "Touched rgba color: (" + mBlobColorRgba.val[0] + ", " + mBlobColorRgba.val[1] +
                ", " + mBlobColorRgba.val[2] + ", " + mBlobColorRgba.val[3] + ")");

        mDetector.setHsvColor(mBlobColorHsv);

        Imgproc.resize(mDetector.getSpectrum(), mSpectrum, SPECTRUM_SIZE, 0, 0, Imgproc.INTER_LINEAR_EXACT);

        mIsColorSelected = true;

        touchedRegionRgba.release();
        touchedRegionHsv.release();

        return false; // don't need subsequent touch events
    }

    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        if(capturedPicture)
        {
            return contourRgba;
        }
        mRgba = inputFrame.rgba();

        Mat gray = new Mat();
        Imgproc.cvtColor(mRgba, gray, Imgproc.COLOR_BGR2GRAY);


        int kernel_size = 11;
        Size s = new Size(kernel_size, kernel_size);
        Mat blur_gray = new Mat();
        Imgproc.GaussianBlur(gray, blur_gray, s, 0);
        Mat kernel = new Mat(5, 5, CvType.CV_8S, Scalar.all(1));
        Point anchor = new Point(-1, -1);
        Imgproc.erode(blur_gray, blur_gray, kernel, anchor, 2);
        Imgproc.dilate(blur_gray, blur_gray, kernel, anchor, 1);

        Mat image = blur_gray.clone();

        int low_threshold = 50;
        int high_threshold = 150;
        Mat edges = new Mat();
        Imgproc.Canny(image,edges, low_threshold, high_threshold);

     /*   if (mIsColorSelected) {
            mDetector.process(mRgba);
            List<MatOfPoint> contours = mDetector.getContours();
            Log.e(TAG, "Contours count: " + contours.size());
      //      Imgproc.drawContours(mRgba, contours, -1, CONTOUR_COLOR);

            Mat colorLabel = mRgba.submat(4, 68, 4, 68);
            colorLabel.setTo(mBlobColorRgba);

            Mat spectrumLabel = mRgba.submat(4, 4 + mSpectrum.rows(), 70, 70 + mSpectrum.cols());
            mSpectrum.copyTo(spectrumLabel);
        }
*/
        return edges;
    }

    private Scalar converScalarHsv2Rgba(Scalar hsvColor) {
        Mat pointMatRgba = new Mat();
        Mat pointMatHsv = new Mat(1, 1, CvType.CV_8UC3, hsvColor);
        Imgproc.cvtColor(pointMatHsv, pointMatRgba, Imgproc.COLOR_HSV2RGB_FULL, 4);

        return new Scalar(pointMatRgba.get(0, 0));
    }


}
