
package com.reactlibrary;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;
import com.reactlibrary.ResponseHelper;
import com.facebook.react.bridge.ReadableMap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import android.util.Base64;
import android.util.Log;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.os.AsyncTask;
import android.os.Build;
import android.graphics.ColorMatrixColorFilter;

public class RNImageFilterModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  Bitmap filterBitmap = null;

  ResponseHelper responseHelper;
  ReadableMap options;
  protected Callback callback;

  String IMAGE_SOURCE = "imageSource", 
          DATA_TYPE = "dataType", 
          FILTER_TYPE = "filterType";

  public RNImageFilterModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNImageFilter";
  }

  @ReactMethod
  public void getSourceImage(final ReadableMap options, final Callback callback) {
    try {
      responseHelper = new ResponseHelper();
      this.callback = callback;
      this.options = options;
      
      Bitmap bitmap = null;
      if(options.getString(IMAGE_SOURCE) == null) {
        responseHelper.invokeError(callback, "Source Null");
        return;
      } else {
        if (options.getString(DATA_TYPE).equals("Path")) {
          bitmap = BitmapFactory.decodeFile(options.getString(IMAGE_SOURCE));
        } else {
          bitmap = encodeBitmapFromBase64(options.getString(IMAGE_SOURCE));
        }
      }

      if (bitmap == null) {
        responseHelper.invokeError(callback, "Failed to decode. Path is incorrect or image is corrupted");
        return;
      } else {
        if(options.getInt(FILTER_TYPE) == -1) {
          responseHelper.putString("base64", decodeBipmapToBase64(bitmap));
          responseHelper.putInt("width", bitmap.getWidth());
          responseHelper.putInt("height", bitmap.getHeight());
          responseHelper.invokeResponse(callback);
          return;
        } else {
          initPaints();

          // ----- Image Processing

          MyAsynTaskFilter myAsynTaskFilter = new MyAsynTaskFilter();
          myAsynTaskFilter.execute(bitmap);

          // ----- Image Processing
        }
        

      }

    } catch (Exception ex) {
      Log.e("ERR", ex.getMessage());
    }

  }

  // code Image processing

  public Paint grayPaint;
  public Paint sepiaPaint;
  public Paint polaroidPaint;
  public Paint invertPaint;
  public Paint scrimPaint;
  public Paint abcPaint;
  public Paint abc1Paint;
  public Paint abc2Paint;
  public Paint abc3Paint;
  public Paint abc4Paint;
  public Paint purplePaint;
  public Paint yellowPaint;
  public Paint cyanPaint;
  public Paint bwPaint;
  public Paint oldtimesPaint;
  public Paint coldlifePaint;
  public Paint sepiumPaint;
  public Paint milkPaint;
  public Paint limePaint;
  public Paint peachyPaint;
  public Paint lightenPaint;
  public Paint darkenPaint;

  public class MyAsynTaskFilter extends AsyncTask<Bitmap, Void, Bitmap> {

    @Override
    protected Bitmap doInBackground(Bitmap... bitmaps) {
      Bitmap src = bitmaps[0];
      filterBitmap = src.copy(Bitmap.Config.ARGB_8888, true);
      Canvas cvs = new Canvas(filterBitmap);
      cvs.drawBitmap(src, 0.0f, 0.0f, new Paint());
      if (true) {
        pipeline(filterBitmap);
      } else {
        cancel(true);
      }
      return filterBitmap;

    }

    void pipeline(Bitmap btmPipe) {

      setFilter(options.getInt(FILTER_TYPE), btmPipe);

    }

    @Override
    protected void onProgressUpdate(Void... values) {
      super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
      super.onPostExecute(bitmap);
      String encodeIamge = "";
      if (bitmap != null) {
        encodeIamge = decodeBipmapToBase64(bitmap);
        responseHelper.putString("base64", encodeIamge);
        responseHelper.putInt("width", bitmap.getWidth());
        responseHelper.putInt("height", bitmap.getHeight());

        responseHelper.invokeResponse(callback);
      } else {
        responseHelper.invokeError(callback, "Failed! Bitmap Processed Null");
      }

      callback = null;
      options = null;
    }
  }

  // set Filter Bitmap
  public void setFilter(int index, Bitmap btm) {
    if (Build.VERSION.SDK_INT != 10 && index != -1) {
      if (index == 0) {
        new Canvas(btm).drawBitmap(btm, 0, 0, grayPaint);
      } else if (index == 1) {
        new Canvas(btm).drawBitmap(btm, 0, 0, sepiaPaint);
      } else if (index == 2) {
        new Canvas(btm).drawBitmap(btm, 0, 0, purplePaint);
      } else if (index == 3) {
        new Canvas(btm).drawBitmap(btm, 0, 0, yellowPaint);
      } else if (index == 4) {
        new Canvas(btm).drawBitmap(btm, 0, 0, milkPaint);
      } else if (index == 5) {
        new Canvas(btm).drawBitmap(btm, 0, 0, coldlifePaint);
      } else if (index == 6) {
        new Canvas(btm).drawBitmap(btm, 0, 0, bwPaint);
      } else if (index == 7) {
        new Canvas(btm).drawBitmap(btm, 0, 0, limePaint);
      } else if (index == 8) {
        new Canvas(btm).drawBitmap(btm, 0, 0, sepiumPaint);
      } else if (index == 9) {
        new Canvas(btm).drawBitmap(btm, 0, 0, oldtimesPaint);
      } else if (index == 10) {
        new Canvas(btm).drawBitmap(btm, 0, 0, cyanPaint);
      } else if (index == 11) {
        new Canvas(btm).drawBitmap(btm, 0, 0, polaroidPaint);
      } else if (index == 12) {
        new Canvas(btm).drawBitmap(btm, 0, 0, invertPaint);
      } else if (index == 13) {
        new Canvas(btm).drawBitmap(btm, 0, 0, abc1Paint);
      } else if (index == 14) {
        new Canvas(btm).drawBitmap(btm, 0, 0, abc4Paint);
      } else if (index == 15) {
        new Canvas(btm).drawBitmap(btm, 0, 0, lightenPaint);
      } else if (index == 16) {
        new Canvas(btm).drawBitmap(btm, 0, 0, abc3Paint);
      } else if (index == 17) {
        new Canvas(btm).drawBitmap(btm, 0, 0, scrimPaint);
      } else if (index == 18) {
        new Canvas(btm).drawBitmap(btm, 0, 0, abc2Paint);
      } else if (index == 19) {
        new Canvas(btm).drawBitmap(btm, 0, 0, darkenPaint);
      } else if (index == 20) {
        new Canvas(btm).drawBitmap(btm, 0, 0, abcPaint);
      } else if (index == 21) {
        new Canvas(btm).drawBitmap(btm, 0, 0, peachyPaint);
      }
    }
  }

  // init paints for Filter
  public void initPaints() {

    sepiaPaint = new Paint();
    ColorMatrix sepiaMatrix = new ColorMatrix();
    sepiaMatrix.set(new float[] { 0.393f, 0.769f, 0.189f, 0.0f, 0.0f, 0.349f, 0.686f, 0.168f, 0.0f, 0.0f, 0.272f,
        0.534f, 0.131f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f });
    sepiaPaint.setColorFilter(new ColorMatrixColorFilter(sepiaMatrix));

    grayPaint = new Paint();
    ColorMatrix cm = new ColorMatrix();
    cm.setSaturation(0.0f);
    grayPaint.setColorFilter(new ColorMatrixColorFilter(cm));

    invertPaint = new Paint();
    ColorMatrix invertMatrix = new ColorMatrix();
    invertMatrix
        .set(new ColorMatrix(new float[] { -1, 0, 0, 0, 255, 0, -1, 0, 0, 255, 0, 0, -1, 0, 255, 0, 0, 0, 1, 0 }));
    invertPaint.setColorFilter(new ColorMatrixColorFilter(invertMatrix));

    polaroidPaint = new Paint();
    ColorMatrix polaroidMatrix = new ColorMatrix();
    polaroidMatrix
        .set(new ColorMatrix(new float[] { 2, 0, 0, 0, -130, 0, 2, 0, 0, -130, 0, 0, 2, 0, -130, 0, 0, 0, 1, 0 }));
    polaroidPaint.setColorFilter(new ColorMatrixColorFilter(polaroidMatrix));

    scrimPaint = new Paint();
    ColorMatrix scrimMatrix = new ColorMatrix();
    scrimMatrix
        .set(new ColorMatrix(new float[] { 5, 0, 0, 0, -254, 0, 5, 0, 0, -254, 0, 0, 5, 0, -254, 0, 0, 0, 1, 0 }));
    scrimPaint.setColorFilter(new ColorMatrixColorFilter(scrimMatrix));

    abcPaint = new Paint();
    ColorMatrix abcMatrix = new ColorMatrix();
    abcMatrix.set(new ColorMatrix(new float[] { 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0 }));
    abcPaint.setColorFilter(new ColorMatrixColorFilter(abcMatrix));

    abc1Paint = new Paint();
    ColorMatrix abc1Matrix = new ColorMatrix();
    abc1Matrix.set(new ColorMatrix(new float[] { -0.36f, 1.691f, -0.32f, 0, 0, 0.325f, 0.398f, 0.275f, 0, 0, 0.79f,
        0.796f, -0.76f, 0, 0, 0, 0, 0, 1, 0 }));
    abc1Paint.setColorFilter(new ColorMatrixColorFilter(abc1Matrix));

    abc2Paint = new Paint();
    ColorMatrix abc2Matrix = new ColorMatrix();
    abc2Matrix.set(new ColorMatrix(new float[] { -0.41f, 0.539f, -0.873f, 0, 0, 0.452f, 0.666f, -0.11f, 0, 0, -0.3f,
        1.71f, -0.4f, 0, 0, 0, 0, 0, 1, 0 }));
    abc2Paint.setColorFilter(new ColorMatrixColorFilter(abc2Matrix));

    abc3Paint = new Paint();
    ColorMatrix abc3Matrix = new ColorMatrix();
    abc3Matrix.set(new ColorMatrix(new float[] { 3.074f, -1.82f, -0.24f, 0, 50.8f, -0.92f, 2.171f, -0.24f, 0, 50.8f,
        -0.92f, -1.82f, 3.754f, 0, 50.8f, 0, 0, 0, 1, 0 }));
    abc3Paint.setColorFilter(new ColorMatrixColorFilter(abc3Matrix));

    abc4Paint = new Paint();
    ColorMatrix abc4Matrix = new ColorMatrix();
    abc4Matrix.set(new ColorMatrix(new float[] { 0.14f, 0.45f, 0.05f, 0, 0, 0.12f, 0.39f, 0.04f, 0, 0, 0.08f, 0.28f,
        0.03f, 0, 0, 0, 0, 0, 1, 0 }));
    abc4Paint.setColorFilter(new ColorMatrixColorFilter(abc4Matrix));

    purplePaint = new Paint();
    ColorMatrix purpleMatrix = new ColorMatrix();
    purpleMatrix.set(
        new ColorMatrix(new float[] { 1, -0.2f, 0, 0, 0, 0, 1, 0, -0.1f, 0, 0, 1.2f, 1, 0.1f, 0, 0, 0, 1.7f, 1, 0 }));
    purplePaint.setColorFilter(new ColorMatrixColorFilter(purpleMatrix));

    yellowPaint = new Paint();
    ColorMatrix yellowMatrix = new ColorMatrix();
    yellowMatrix
        .set(new ColorMatrix(new float[] { 1, 0, 0, 0, 0, -0.2f, 1, 0.3f, 0.1f, 0, -3, 0, 1, 0, 0, 0, 0, 0, 1, 0 }));
    yellowPaint.setColorFilter(new ColorMatrixColorFilter(yellowMatrix));

    cyanPaint = new Paint();
    ColorMatrix cyanMatrix = new ColorMatrix();
    cyanMatrix.set(
        new ColorMatrix(new float[] { 1, 0, 0, 1.9f, -2.2f, 0, 1, 0, 0.0f, 0.3f, 3, 0, 1, 0, 0.5f, 0, 0, 0, 1, 0.2f }));
    cyanPaint.setColorFilter(new ColorMatrixColorFilter(cyanMatrix));

    bwPaint = new Paint();
    ColorMatrix bwMatrix = new ColorMatrix();
    bwMatrix.set(new ColorMatrix(new float[] { 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0 }));
    bwPaint.setColorFilter(new ColorMatrixColorFilter(bwMatrix));

    oldtimesPaint = new Paint();
    ColorMatrix oldtimesMatrix = new ColorMatrix();
    oldtimesMatrix.set(
        new ColorMatrix(new float[] { 1, 0, 0, 0, 0, -0.4f, 1.3f, -0.4f, 0.2f, -0.1f, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0 }));
    oldtimesPaint.setColorFilter(new ColorMatrixColorFilter(oldtimesMatrix));

    coldlifePaint = new Paint();
    ColorMatrix coldlifeMatrix = new ColorMatrix();
    coldlifeMatrix
        .set(new ColorMatrix(new float[] { 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, -0.2f, 0.2f, 0.1f, 0.4f, 0, 0, 0, 0, 1, 0 }));
    coldlifePaint.setColorFilter(new ColorMatrixColorFilter(coldlifeMatrix));

    sepiumPaint = new Paint();
    ColorMatrix sepiumMatrix = new ColorMatrix();
    sepiumMatrix.set(new ColorMatrix(
        new float[] { 1.3f, -0.3f, 1.1f, 0, 0, 0, 1.3f, 0.2f, 0, 0, 0, 0, 0.8f, 0.2f, 0, 0, 0, 0, 1, 0 }));
    sepiumPaint.setColorFilter(new ColorMatrixColorFilter(sepiumMatrix));

    milkPaint = new Paint();
    ColorMatrix milkMatrix = new ColorMatrix();
    milkMatrix.set(new ColorMatrix(new float[] { 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0.6f, 1, 0, 0, 0, 0, 0, 1, 0 }));
    milkPaint.setColorFilter(new ColorMatrixColorFilter(milkMatrix));

    limePaint = new Paint();
    ColorMatrix limeMatrix = new ColorMatrix();
    limeMatrix.set(new ColorMatrix(new float[] { 1, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0.5f, 0, 0, 0, 0, 1, 0 }));
    limePaint.setColorFilter(new ColorMatrixColorFilter(limeMatrix));

    peachyPaint = new Paint();
    ColorMatrix peachyMatrix = new ColorMatrix();
    peachyMatrix.set(new ColorMatrix(new float[] { 1, 0, 0, 0, 0, 0, 0.5f, 0, 0, 0, 0, 0, 0, 0.5f, 0, 0, 0, 0, 1, 0 }));
    peachyPaint.setColorFilter(new ColorMatrixColorFilter(peachyMatrix));

    lightenPaint = new Paint();
    ColorMatrix lightenMatrix = new ColorMatrix();
    lightenMatrix
        .set(new ColorMatrix(new float[] { 1.5f, 0, 0, 0, 0, 0, 1.5f, 0, 0, 0, 0, 0, 1.5f, 0, 0, 0, 0, 0, 1, 0 }));
    lightenPaint.setColorFilter(new ColorMatrixColorFilter(lightenMatrix));

    darkenPaint = new Paint();
    ColorMatrix darkenMatrix = new ColorMatrix();
    darkenMatrix.set(new ColorMatrix(new float[] { .5f, 0, 0, 0, 0, 0, .5f, 0, 0, 0, 0, 0, .5f, 0, 0, 0, 0, 0, 1, 0 }));
    darkenPaint.setColorFilter(new ColorMatrixColorFilter(darkenMatrix));

  }

  // ----------------------------------------------------------------------------------------------

  //
  public Bitmap encodeBitmapFromBase64(String base64) {
    Bitmap btm = null;
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      byte[] imageBytes = baos.toByteArray();
      imageBytes = Base64.decode(base64, Base64.DEFAULT);
      return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    } catch (Exception ex) {
      Log.e("ERR1", ex.getMessage());
    }
    return btm;
  }

  public String decodeBipmapToBase64(Bitmap bitmap) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
    byte[] b = baos.toByteArray();

    return Base64.encodeToString(b, Base64.DEFAULT);
  }
}