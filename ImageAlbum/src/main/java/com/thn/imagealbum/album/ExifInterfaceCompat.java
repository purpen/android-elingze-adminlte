
package com.thn.imagealbum.album;

import android.media.ExifInterface;
import android.text.TextUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public final class ExifInterfaceCompat {
    public static final String TAG = ExifInterfaceCompat.class.getSimpleName();

    private ExifInterfaceCompat() {
    }

    public static ExifInterface newInstance(String filename) throws IOException {
        if(filename == null) {
            throw new NullPointerException("filename should not be null");
        } else {
            return new ExifInterface(filename);
        }
    }

    public static Date getExifDateTime(String filepath) {
        ExifInterface exif;

        try {
            exif = newInstance(filepath);
        } catch (IOException var5) {
            return null;
        }

        String date = exif.getAttribute("DateTime");
        if(TextUtils.isEmpty(date)) {
            return null;
        } else {
            try {
                SimpleDateFormat e = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
                e.setTimeZone(TimeZone.getTimeZone("UTC"));
                return e.parse(date);
            } catch (ParseException var4) {
                return null;
            }
        }
    }

    public static long getExifDateTimeInMillis(String filepath) {
        Date datetime = getExifDateTime(filepath);
        return datetime == null?-1L:datetime.getTime();
    }

    public static int getExifOrientation(String filepath) {
        ExifInterface exif;

        try {
            exif = newInstance(filepath);
        } catch (IOException var3) {
            return -1;
        }

        int orientation = exif.getAttributeInt("Orientation", -1);
        if(orientation == -1) {
            return 0;
        } else {
            switch(orientation) {
                case 3:
                    return 180;
                case 6:
                    return 90;
                case 8:
                    return 270;
                default:
                    return 0;
            }
        }
    }
}
