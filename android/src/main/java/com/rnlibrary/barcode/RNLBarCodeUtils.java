package com.rnlibrary.barcode;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ThemedReactContext;
import com.rnlibrary.barcode.decoder.Decoder;
import com.rnlibrary.barcode.decoder.GVisionDecoder;
import com.rnlibrary.barcode.decoder.ZXingDecoder;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javax.annotation.Nullable;

public final class RNLBarCodeUtils {

    @Nullable
    public static Decoder getDecoderByID(int id, ReactApplicationContext context) {
        Decoder decoder = null;
        if (id == 2 || id == 0) {
            // ZBar Auto
            decoder = new GVisionDecoder(context);
        } else if (id == 1) {
            // ZXing
            decoder = new ZXingDecoder();
        }
        return decoder;
    }

    @Nullable
    public static Bitmap takeScreenshot(Activity activity) {
        try {
            View windowView = activity.getWindow().getDecorView().getRootView();
            Bitmap screenshot = Bitmap.createBitmap(
                    windowView.getWidth(), windowView.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(screenshot);
            windowView.draw(canvas);
            return screenshot;
        } catch (Exception ignore) {

        }
        return null;
    }

    @Nullable
    public static Bitmap parseImageStr(String str) throws Exception {

        Bitmap image = null;
        try {
            FileInputStream in;
            BufferedInputStream buf;
            in = new FileInputStream(str);
            buf = new BufferedInputStream(in);
            image = BitmapFactory.decodeStream(buf);
            if (in != null) {
                in.close();
            }
            if (buf != null) {
                buf.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return image;
    }

    // https://stackoverflow.com/a/39800979/6428205
    public static byte[] rotateYUV90(byte[] data, int w, int h) {
        byte[] yuv = new byte[w * h * 3 / 2];
        int i = 0;
        for (int x = 0; x < w; x++) {
            for (int y = h - 1; y >= 0; y--) {
                yuv[i++] = data[y * w + x];
            }
        }
        i = w * h * 3 / 2 - 1;
        for (int x = w - 1; x > 0; x = x - 2) {
            for (int y = 0; y < h / 2; y++) {
                yuv[i--] = data[(w * h) + (y * w) + x];
                yuv[i--] = data[(w * h) + (y * w) + (x - 1)];
            }
        }
        return yuv;
    }

    public static byte[] rotateYUV180(byte[] data, int w, int h) {
        byte[] yuv = new byte[w * h * 3 / 2];
        int i;
        int count = 0;
        for (i = w * h - 1; i >= 0; i--) {
            yuv[count++] = data[i];
        }
        for (i = w * h * 3 / 2 - 1; i >= w * h; i -= 2) {
            yuv[count++] = data[i - 1];
            yuv[count++] = data[i];
        }
        return yuv;
    }

    public static byte[] rotateYUV270(byte[] data, int w, int h) {
        return rotateYUV180(rotateYUV90(data, w, h), w, h);
    }
}
