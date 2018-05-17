/*
 * Copyright (C) 2014 nohana, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an &quot;AS IS&quot; BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.thn.imagealbum.album;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;


/**
 * Wrapper for {@link CursorLoader} to merge custom cursors.
 *
 * @author KeithYokoma
 * @version 1.0.0
 * @since 2014/03/26
 */
public class AlbumLoader extends CursorLoader {
    private static final String[] PROJECTION = {MediaStore.Images.Media.BUCKET_ID, MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.Images.Media._ID, "count(bucket_id) as cou"};
    private static final String BUCKET_GROUP_BY = ") GROUP BY  1,(2";
    private static final String BUCKET_ORDER_BY = "MAX(datetaken) DESC";
    private static final String MEDIA_ID_DUMMY = String.valueOf(-1);
    private static final String IS_LARGE_SIZE = " _size > ? or _size is null";

    public static CursorLoader newInstance(Context context, SelectionSpec selectionSpec) {
        return new AlbumLoader(context, IS_LARGE_SIZE + BUCKET_GROUP_BY, new String[]{selectionSpec.getMinPixels() + ""}, selectionSpec);
    }

    private AlbumLoader(Context context, String selection, String[] selectionArgs, SelectionSpec selectionSpec) {
        super(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, AlbumLoader.PROJECTION, selection, selectionArgs, AlbumLoader.BUCKET_ORDER_BY);
    }

    @Override
    public Cursor loadInBackground() {
        Cursor albums = super.loadInBackground();
        MatrixCursor allAlbum = new MatrixCursor(PROJECTION);

        long count = 0;
        if (albums.getCount() > 0) {
            while (albums.moveToNext()) {
                count += albums.getLong(3);
            }
        }
        allAlbum.addRow(new String[]{Album.ALBUM_ID_ALL, Album.ALBUM_NAME_ALL, MEDIA_ID_DUMMY, count + ""});

        return new MergeCursor(new Cursor[]{allAlbum, albums});
    }
}