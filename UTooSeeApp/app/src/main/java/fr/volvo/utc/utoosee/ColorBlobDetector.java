package fr.volvo.utc.utoosee;
import android.os.Environment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class ColorBlobDetector {
    // Lower and Upper bounds for range checking in HSV color space
    private Scalar mLowerBound = new Scalar(0);
    private Scalar mUpperBound = new Scalar(0);
    // Minimum contour area in percent for contours filtering
    private static double mMinContourArea = 0.1;
    // Color radius for range checking in HSV color space
    private Scalar mColorRadius = new Scalar(25,25,25,0);
    private Mat mSpectrum = new Mat();
    private List<MatOfPoint> mContours = new ArrayList<MatOfPoint>();

    // Cache
    Mat mPyrDownMat = new Mat();
    Mat mHsvMat = new Mat();
    Mat mMask = new Mat();
    Mat mDilatedMask = new Mat();
    Mat mHierarchy = new Mat();

    public void setColorRadius(Scalar radius) {
        mColorRadius = radius;
    }

    public void setHsvColor(Scalar hsvColor) {
        double minH = (hsvColor.val[0] >= mColorRadius.val[0]) ? hsvColor.val[0]-mColorRadius.val[0] : 0;
        double maxH = (hsvColor.val[0]+mColorRadius.val[0] <= 255) ? hsvColor.val[0]+mColorRadius.val[0] : 255;

        mLowerBound.val[0] = minH;
        mUpperBound.val[0] = maxH;

        mLowerBound.val[1] = hsvColor.val[1] - mColorRadius.val[1];
        mUpperBound.val[1] = hsvColor.val[1] + mColorRadius.val[1];

        mLowerBound.val[2] = hsvColor.val[2] - mColorRadius.val[2];
        mUpperBound.val[2] = hsvColor.val[2] + mColorRadius.val[2];

        mLowerBound.val[3] = 0;
        mUpperBound.val[3] = 255;

        Mat spectrumHsv = new Mat(1, (int)(maxH-minH), CvType.CV_8UC3);

        for (int j = 0; j < maxH-minH; j++) {
            byte[] tmp = {(byte)(minH+j), (byte)255, (byte)255};
            spectrumHsv.put(0, j, tmp);
        }

        Imgproc.cvtColor(spectrumHsv, mSpectrum, Imgproc.COLOR_HSV2RGB_FULL, 4);
    }

    public Mat getSpectrum() {
        return mSpectrum;
    }

    public void setMinContourArea(double area) {
        mMinContourArea = area;
    }

    public Mat myProcess(Mat rgbaImage) {
        Mat img = rgbaImage.clone();
        Mat gray = new Mat();
        Imgproc.cvtColor(img, gray, Imgproc.COLOR_BGR2GRAY);


        int kernel_size = 11;
        Size s = new Size(kernel_size, kernel_size);
        Mat blur_gray = new Mat();
        Imgproc.GaussianBlur(gray, blur_gray, s, 0);
        Mat kernel = new Mat(5, 5, CvType.CV_8S, Scalar.all(1));
        Point anchor = new Point(-1, -1);
        Imgproc.erode(blur_gray, blur_gray, kernel, anchor, 2);
        Imgproc.dilate(blur_gray, blur_gray, kernel, anchor, 1);
//blur_gray = Imgproc.medianBlur(blur_gray, 5)
        Mat image1 = blur_gray.clone();
        Imgcodecs.imwrite(Environment.getExternalStorageDirectory() + "/'image1.jpg", image1);


        Imgproc.GaussianBlur(blur_gray, blur_gray, s, 0);
        kernel = new Mat(3, 3, CvType.CV_8S, Scalar.all(1));
        Imgproc.erode(blur_gray, blur_gray, kernel, anchor, 1);
        Imgproc.dilate(blur_gray, blur_gray, kernel, anchor, 1);
//blur_gray = Imgproc.medianBlur(blur_gray, 5)
        Mat image2 = blur_gray.clone();
        s = new Size(5, 5);
        Imgcodecs.imwrite(Environment.getExternalStorageDirectory() + "/'image2.jpg", image2);

        Imgproc.GaussianBlur(blur_gray, blur_gray, s, 0);
        kernel = new Mat(5, 5, CvType.CV_8S, Scalar.all(1));
        Imgproc.erode(blur_gray, blur_gray, kernel, anchor, 1);
        Imgproc.dilate(blur_gray, blur_gray, kernel, anchor, 1);
//blur_gray = Imgproc.medianBlur(blur_gray, 5)
        Mat image3 = blur_gray.clone();
        Imgcodecs.imwrite(Environment.getExternalStorageDirectory() + "/'image3.jpg", image3);


        Imgproc.GaussianBlur(blur_gray, blur_gray, s, 0);
        kernel = new Mat(3, 3, CvType.CV_8S, Scalar.all(1));
        Imgproc.erode(blur_gray, blur_gray, kernel, anchor, 1);
//blur_gray = Imgproc.medianBlur(blur_gray, 5)
        Mat image4 = blur_gray.clone();
        s = new Size(3, 3);
        Imgcodecs.imwrite(Environment.getExternalStorageDirectory() + "/'image4.jpg", image4);

        Imgproc.GaussianBlur(blur_gray, blur_gray, s, 0);
        Imgproc.erode(blur_gray, blur_gray, kernel, anchor, 5);
        Imgproc.dilate(blur_gray, blur_gray, kernel, anchor, 30);
        Mat image5 = blur_gray.clone();

        Imgcodecs.imwrite(Environment.getExternalStorageDirectory() + "/'PREPROCESSED.jpg", blur_gray);

        Mat image = blur_gray.clone();

//Contour detection
        Mat thresh = new Mat();
        double ret = Imgproc.threshold(image1, thresh, 127, 255, 0);
        List<MatOfPoint> cnts = new ArrayList<>();
        Imgproc.findContours(thresh.clone(), cnts, mHierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        //  MatOfPoint cnts = cntsList.get(0);//if crash take the 1
        double maxArea = 0;
        MatOfPoint max_contour = new MatOfPoint();
        Iterator<MatOfPoint> iterator = cnts.iterator();
        while (iterator.hasNext()) {
            MatOfPoint contour = iterator.next();
            double area = Imgproc.contourArea(contour);
            if (area > maxArea) {
                maxArea = area;
                max_contour = contour;
            }
        }
        cnts.set(0, max_contour);
        Imgproc.drawContours(image1, cnts, 0, new Scalar(0, 255, 0), 3);
        Imgcodecs.imwrite(Environment.getExternalStorageDirectory() + "/'Image1.jpg", image1);

        ret = Imgproc.threshold(image5, thresh, 127, 255, 0);
        Imgproc.findContours(thresh.clone(), cnts, mHierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        maxArea = 0;
        iterator = cnts.iterator();
        while (iterator.hasNext()) {
            MatOfPoint contour = iterator.next();
            double area = Imgproc.contourArea(contour);
            if (area > maxArea) {
                maxArea = area;
                max_contour = contour;
            }
        }
        cnts.set(0, max_contour);

        Imgproc.drawContours(image5, cnts, 0, new Scalar(0, 255, 0), 3);
        Imgcodecs.imwrite(Environment.getExternalStorageDirectory() + "/'Image5.jpg", image5);


        Imgproc.drawContours(image5, cnts, 0, new Scalar(0, 255, 0), 3);
        Imgcodecs.imwrite(Environment.getExternalStorageDirectory() + "/'Image_CONTOURS.jpg", image5);

        mContours.clear();
        iterator = cnts.iterator();
        while (iterator.hasNext()) {
            MatOfPoint contour = iterator.next();
            if (Imgproc.contourArea(contour) > mMinContourArea * maxArea) {
                Core.multiply(contour, new Scalar(4, 4), contour);
                mContours.add(contour);
            }
        }

     //   return image5;

//#================================================Lines detection
              image= image1;

    int low_threshold = 50;
    int high_threshold = 150;
    Mat edges = new Mat();
     Imgproc.Canny(image,edges, low_threshold, high_threshold);
        Imgcodecs.imwrite(Environment.getExternalStorageDirectory() + "/'EDGES.jpg",edges);


    int rho = 1 ; //# distance resolution in pixels of the Hough grid
    double theta = Math.PI / 180 ; //# angular resolution in radians of the Hough grid
    int threshold = 15 ;// # minimum number of votes (intersections in Hough grid cell)
    int min_line_length = 400 ;// # minimum number of pixels making up a line
    int max_line_gap = 100; // # maximum gap in pixels between connectable line segments
      //      lines_image = np.copy(img) * 0 // # creating a blank to draw lines on
        Mat line_image = new Mat(img.rows(), img.cols(), CvType.CV_8UC4, Scalar.all(0));
        Mat lines_image = new Mat(img.rows(), img.cols(), CvType.CV_8UC4, Scalar.all(0));
Mat lines = new Mat();
//# Run Hough on edge detected image
//# Output "lines" is an array containing endpoints of detected line segments
             Imgproc.HoughLinesP(edges,lines, rho, theta, threshold,
    min_line_length, max_line_gap);
        for (int i = 0; i < lines.cols(); i++) {
            double[] points = lines.get(0, i);
            double x1, y1, x2, y2;

            x1 = points[0];
            y1 = points[1];
            x2 = points[2];
            y2 = points[3];

            Point pt1 = new Point(x1, y1);
            Point pt2 = new Point(x2, y2);

            //Drawing lines on an image
            Imgproc.line(line_image, pt1, pt2, new Scalar(255, 0, 0), 1);
            Imgproc.line(lines_image, pt1, pt2, new Scalar(255, 0, 0), 1);
        }


Mat lines_edges = new Mat();
     Core.addWeighted(img, 0.8, line_image, 1, 0,lines_edges);


        Imgcodecs.imwrite(Environment.getExternalStorageDirectory() + "/image_lines.jpg", lines_edges);

    Mat gray_lines = new Mat();
    Imgproc.cvtColor(lines_image,gray_lines,Imgproc.COLOR_BGR2GRAY);
        Imgcodecs.imwrite(Environment.getExternalStorageDirectory() + "/lines.jpg", gray_lines);

return edges;

    }
    public void process(Mat rgbaImage) {
        Imgproc.pyrDown(rgbaImage, mPyrDownMat);
        Imgproc.pyrDown(mPyrDownMat, mPyrDownMat);

        Imgproc.cvtColor(mPyrDownMat, mHsvMat, Imgproc.COLOR_RGB2HSV_FULL);

        Core.inRange(mHsvMat, mLowerBound, mUpperBound, mMask);
        Imgproc.dilate(mMask, mDilatedMask, new Mat());

        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();

        Imgproc.findContours(mDilatedMask, contours, mHierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // Find max contour area
        double maxArea = 0;
        Iterator<MatOfPoint> each = contours.iterator();
        while (each.hasNext()) {
            MatOfPoint wrapper = each.next();
            double area = Imgproc.contourArea(wrapper);
            if (area > maxArea)
                maxArea = area;
        }

        // Filter contours by area and resize to fit the original image size
        mContours.clear();
        each = contours.iterator();
        while (each.hasNext()) {
            MatOfPoint contour = each.next();
            if (Imgproc.contourArea(contour) > mMinContourArea*maxArea) {
                Core.multiply(contour, new Scalar(4,4), contour);
                mContours.add(contour);
            }
        }
    }

    public List<MatOfPoint> getContours() {
        return mContours;
    }
}
