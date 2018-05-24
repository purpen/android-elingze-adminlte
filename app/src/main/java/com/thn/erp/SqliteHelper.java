package com.thn.erp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.thn.erp.overview.bean.CustomMenuBean;
import com.thn.erp.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class SqliteHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "home_menu";

    public SqliteHelper(Context context) {
        super(context, "erp.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,title TEXT NOT NULL,position INTEGER NOT NULL,icon_id INTEGER NOT NULL,selected INTEGER NOT NULL)";
        db.execSQL(sql);
        LogUtil.e("fun onCreate table " + TABLE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 插入记录
     *
     * @param bean
     */
    public void insert(CustomMenuBean bean) {
        if (bean == null) {
            LogUtil.e("fun insert err:bean is null");
            return;
        }
        if (TextUtils.isEmpty(bean.title)) {
            LogUtil.e("fun insert err:title is empty");
            return;
        }

        if (bean.iconId == 0) {
            LogUtil.e("fun insert err:iconId ==0");
            return;
        }

        SQLiteDatabase db = getWritableDatabase();
        //开启事务
        db.beginTransaction();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", bean.title);
        contentValues.put("position", bean.pos);
        contentValues.put("icon_id", bean.iconId);
        if (bean.selected) {
            contentValues.put("selected", 1);
        } else {
            contentValues.put("selected", 0);
        }
        long l = db.insert(TABLE_NAME, null, contentValues);
        db.endTransaction();

        LogUtil.e("fun insert table result=" + l);
    }

    //删除某条记录
    public void delete(String title) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "title=?";
        String[] whereArgs = {title};
        int i = db.delete(TABLE_NAME, whereClause, whereArgs);
        LogUtil.e("fun delete table result=" + i);
    }

    //更新数据
    public void update(String title, int position, int selected) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("position", position);
        values.put("selected", selected);
        String whereClause = "title=?";
        String[] whereArgs = {title};
        db.update(TABLE_NAME, values, whereClause, whereArgs);
    }

    //查询符合选中条件记录
    public List<CustomMenuBean> queryItemsBySelection(int selection) {
        SQLiteDatabase db = getReadableDatabase();
        List<CustomMenuBean> list = new ArrayList<>();
        String[] selectionArgs = {String.valueOf(selection)};
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE selected = ? ORDER BY position ASC", selectionArgs);
        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex("title"));
            int position = cursor.getInt(cursor.getColumnIndex("position"));
            int selected = cursor.getInt(cursor.getColumnIndex("selected"));
            int iconId = cursor.getInt(cursor.getColumnIndex("icon_id"));
            CustomMenuBean bean = new CustomMenuBean();
            bean.title = title;
            bean.pos = position;
            if (selected == 1) {
                bean.selected = true;
            } else {
                bean.selected = false;
            }
            bean.iconId = iconId;
            list.add(bean);
        }
        return list;
    }


    //查询所有记录
    public List<CustomMenuBean> queryAll() {
        SQLiteDatabase db = getReadableDatabase();
        List<CustomMenuBean> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex("title"));
            int position = cursor.getInt(cursor.getColumnIndex("position"));
            int selected = cursor.getInt(cursor.getColumnIndex("selected"));
            int iconId = cursor.getInt(cursor.getColumnIndex("icon_id"));

            CustomMenuBean bean = new CustomMenuBean();
            bean.title = title;
            bean.pos = position;
            if (selected == 1) {
                bean.selected = true;
            } else {
                bean.selected = false;
            }
            bean.iconId = iconId;
            list.add(bean);
        }
        return list;
    }
}
