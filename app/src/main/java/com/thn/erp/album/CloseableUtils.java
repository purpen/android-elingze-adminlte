package com.thn.erp.album;


import java.io.Closeable;
import java.io.IOException;

/**
 * Created by laputan on 15-6-8.
 */
public class CloseableUtils {

    private static final String TAG = CloseableUtils.class.getSimpleName();

    private CloseableUtils() {
    }

    public static void close(Closeable closeable) {
        if(closeable != null) {
            try {
                closeable.close();
            } catch (IOException var2) {
            }

        }
    }
}
