package com.thn.erp.album;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.view.View;

import com.thn.basemodule.tools.Constants;
import com.thn.basemodule.tools.LogUtil;
import com.thn.basemodule.tools.ToastUtils;
import com.thn.imagealbum.BuildConfig;
import com.thn.imagealbum.album.ImageLoaderEngine;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class ImageUtils {
    public static double[] location = null;//图片经纬度
    private static final File PHOTO_DIR = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera");

    //保存图片文件
    public static String saveToFile(String fileFolderStr, boolean isDir, Bitmap croppedImage) throws IOException {
        File jpgFile;
        if (isDir) {
            File fileFolder = new File(fileFolderStr);
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA); // 格式化时间
            String filename = format.format(date) + ".jpg";
            if (!fileFolder.exists()) { // 如果目录不存在，则创建一个名为"finger"的目录
                mkdir(fileFolder);
            }
            jpgFile = new File(fileFolder, filename);
        } else {
            jpgFile = new File(fileFolderStr);
//            Log.e("<<<", "jpgfile.path = " + jpgFile.getAbsolutePath());
            if (!jpgFile.getParentFile().exists()) { // 如果目录不存在，则创建一个名为"finger"的目录
                mkdir(jpgFile.getParentFile());
            }
        }
        FileOutputStream outputStream = new FileOutputStream(jpgFile); // 文件输出流
        croppedImage.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        outputStream.close();
//        Log.e("<<<", "path = " + jpgFile.getPath());
        return jpgFile.getPath();
    }

    //创建文件夹
    private static boolean mkdir(File file) {
        while (!file.getParentFile().exists()) {
            mkdir(file.getParentFile());
        }
        return file.mkdir();
    }


    //获取图片的位置信息
    public static double[] picLocation(String fileName) {
//        Log.e("<<<", "getLocation.fileName = " + fileName);
        ExifInterface exifInterface = null;
        try {
            exifInterface = new ExifInterface(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (exifInterface == null) {
            return null;
        }
        String latitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
        String longitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
        if (latitude != null && longitude != null) {
            String[] latitudes = latitude.split(",");
            double du = Integer.parseInt(latitudes[0].substring(0, latitudes[0].indexOf("/")));
            double fen = Integer.parseInt(latitudes[1].substring(0, latitudes[1].indexOf("/")));
            double miao = Integer.parseInt(latitudes[2].substring(0, latitudes[2].indexOf("/")));
            double weidu = du + fen / 60 + miao / 1000000 / 60 / 60;
            String[] longitudes = longitude.split(",");
            du = Integer.parseInt(longitudes[0].substring(0, longitudes[0].indexOf("/")));
            fen = Integer.parseInt(longitudes[1].substring(0, longitudes[1].indexOf("/")));
            miao = Integer.parseInt(longitudes[2].substring(0, longitudes[2].indexOf("/")));
            double jingdu = du + fen / 60 + miao / 1000000 / 60 / 60;
            return new double[]{jingdu, weidu};
        }
        return null;
    }

//    //判断图片是否需要裁剪
//    public static void processPhotoItem(Activity activity, PhotoItem photo) {
//        location = picLocation(photo.getImageUri());
//        Uri uri = photo.getImageUri().startsWith("file:") ? Uri.parse(photo
//                .getImageUri()) : Uri.parse("file://" + photo.getImageUri());
////        if (isFourToThree(photo.getImageUri())) {
////            Intent intent = new Intent(activity, EditPictureActivity.class);
////            intent.setData(uri);
////            activity.startActivity(intent);
////        } else {
//        Intent intent = new Intent(activity, CropPictureActivity.class);
//        intent.setData(uri);
//        activity.startActivity(intent);
////        }
//    }

    //判断图片是不是16:9
    public static boolean isFourToThree(String imagePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        return (options.outHeight * 9) == (options.outWidth * 16);
    }

    //从文件中读取照片

    /**
     * @param pathName
     * @param width
     * @param height
     * @param useBigger 图片会进行压缩。这个参数是选择使用较大的图还是较小的图，较大的图会比指定的宽高大，较小的图会比指定的宽高小
     * @return
     */
    public static Bitmap decodeBitmapWithSize(String pathName, int width, int height,
                                              boolean useBigger) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//只加载参数
        options.inInputShareable = true;//用于图片清空之后恢复
        options.inPurgeable = true;//当内存不足时可以清楚
        BitmapFactory.decodeFile(pathName, options);
        if (useBigger) {
            options.inSampleSize = (int) Math.min(((double) options.outWidth / width),
                    ((double) options.outHeight / height));
        } else {
            double scale = Math.max(((double) options.outWidth / width),
                    ((double) options.outHeight / height));
            if ((int) (scale * 10) % 10 != 0) {
                options.inSampleSize = (int) scale + 1;
            } else {
                options.inSampleSize = (int) scale;
            }


        }
//        Log.e("<<<压缩图片", "图片width=" + options.outWidth + ",height=" + options.outHeight + ",要求width=" + width + ",height=" + height+",压缩值="+options.inSampleSize);
        options.inJustDecodeBounds = false;
        Bitmap sourceBm = BitmapFactory.decodeFile(pathName, options);
        return sourceBm;
    }

    /**
     * 获取圆角位图的方法
     *
     * @param bitmap 需要转化成圆角的位图
     * @param pixels 圆角的度数，数值越大，圆角越大
     * @return 处理后的圆角位图
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    //异步加载本地图片
    public static void asyncLoadImage(Context context, Uri imageUri, LoadImageCallback callback) {
        new LoadImageUriTask(context, imageUri, callback).execute();
    }

    //异步加载图片
    public interface LoadImageCallback {
        void callback(Bitmap result);
    }

    private static class LoadImageUriTask extends AsyncTask<Void, Void, Bitmap> {
        private final Uri imageUri;
        private final Context context;
        private LoadImageCallback callback;

        public LoadImageUriTask(Context context, Uri imageUri, LoadImageCallback callback) {
            this.imageUri = imageUri;
            this.context = context;
            this.callback = callback;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                InputStream inputStream;
                if (imageUri.getScheme().startsWith("http")
                        || imageUri.getScheme().startsWith("https")) {
                    inputStream = new URL(imageUri.toString()).openStream();
                } else {
                    inputStream = context.getContentResolver().openInputStream(imageUri);
                }
                return BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            callback.callback(result);
        }
    }


    public static void loadBgImg(String url, View view) {
        new MyAsyncTask(url, view).execute();
    }

    private static class MyAsyncTask extends AsyncTask<String, Void, Bitmap> {
        private View view;
        private String url;

        public MyAsyncTask(String url, View view) {
            super();
            this.view = view;
            this.url = url;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                InputStream inputStream = null;
                if (params[0].startsWith("http")
                        || params[0].startsWith("https")) {
                    inputStream = new URL(params[0]).openStream();
                }
                return BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            view.setBackgroundDrawable(new BitmapDrawable(bitmap));
        }
    }

    public static Bitmap decodeUriAsBitmap(Context context,Uri uri) {

        Bitmap bitmap;
        try {

            bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));

        } catch (FileNotFoundException e) {

            e.printStackTrace();

            return null;

        }

        return bitmap;

    }

    public static Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        view.layout(view.getLeft(),view.getTop(), view.getLeft()+view.getMeasuredWidth(),view.getTop()+view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    //计算图片缩放比例
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    // 根据路径获得图片并压缩，返回bitmap用于显示
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * bitmap转字节数组
     *
     * @param bitmap
     * @return
     */
    public static byte[] bitmap2ByteArray(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        return baos.toByteArray();
    }

    /**
     * 从相机获得图
     *
     * @param activity
     * @param uri
     */
    public static void getImageFromCamera(Activity activity, Uri uri) {
        if (uri == null) return;
        String state = Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            ToastUtils.showInfo(activity,"请插入SD卡");
            return;
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        activity.startActivityForResult(intent, Constants.REQUEST_CODE_CAPTURE_CAMERA);
    }

    /**
     * 从相册获得图片
     *
     * @param activity
     * @param count
     */
    public static void getImageFromAlbum(Activity activity, int count) {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Picker.from(activity)
                    .count(count)
                    .enableCamera(true)
                    .setEngine(new ImageLoaderEngine())
                    .forResult(Constants.REQUEST_CODE_EXTERNAL_STORAGE);
        } else {
            ToastUtils.showError(activity,"未检测到SD卡");
        }
    }

    public static Uri getUriForFile(Context context,File file) {
        if (null == file) file = getDefaultFile();
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }

    public static File getDefaultFile() {
        File file;
        if (!PHOTO_DIR.exists()) {
            LogUtil.e("openCamera() failed");
            return null;
        }
        file = new File(PHOTO_DIR, getPhotoFileName());
        if (file.exists()) {
            file.delete();
        }
        return file;
    }

    public static String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyy_MM_dd_HH_mm_ss");
        return dateFormat.format(date) + ".jpg";
    }

    /**
     * 根据Uri获取图片文件的绝对路径
     */
    public static String getRealFilePath(Context context,final Uri uri) {
        if (null == uri) {
            return null;
        }
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri,
                    new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
}
