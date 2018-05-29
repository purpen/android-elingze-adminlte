package com.thn.erp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.thn.erp.overview.bean.CustomMenuBean;
import com.thn.basemodule.tools.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class SqliteHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "home_menu";

    public SqliteHelper(Context context) {
        super(context, "erp.db", null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE if not EXISTS " + TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,title TEXT NOT NULL,position INTEGER NOT NULL,icon_id INTEGER NOT NULL,selected INTEGER NOT NULL)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.execute();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 获取表中的记录数
     * @return
     */
    public long getCountOfRecordInTable(){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT COUNT(*) FROM " + TABLE_NAME;
        SQLiteStatement statement = db.compileStatement(sql);
        return statement.simpleQueryForLong();
    }

    /**
     * 打印数据库所有表
     */
    public void printTablesInDB(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select name from sqlite_master where type='table';", null);
        while(cursor.moveToNext()){
            //遍历出表名
            String name = cursor.getString(0);
            LogUtil.e("printTablesInDB ="+name);
        }
        cursor.close();
        db.close();
    }

    /**
     * 批量插入记录
     * @return 插入行数
     * @param list
     */
    public int insert(List<CustomMenuBean> list) {
        if (list == null) {
            LogUtil.e("fun insert err:list == null");
            return -1;
        }

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues;
        db.beginTransaction();
        for (CustomMenuBean bean:list){
            contentValues = new ContentValues();
            contentValues.put("title", bean.title);
            contentValues.put("position", bean.pos);
            contentValues.put("icon_id", bean.iconId);
            if (bean.selected) {
                contentValues.put("selected", 1);
            } else {
                contentValues.put("selected", 0);
            }
            db.insert(TABLE_NAME, null, contentValues);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        return list.size();
    }

    //删除某条记录
    public void delete(String title) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "title=?";
        String[] whereArgs = {title};
        int i = db.delete(TABLE_NAME, whereClause, whereArgs);
        LogUtil.e("fun delete table result=" + i);
        db.close();
    }

    //批量更新数据
    public void update(List<CustomMenuBean> list) {
        if (list == null) {
            LogUtil.e("fun update err:list == null");
            return;
        }
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues;
        db.beginTransaction();
        for (CustomMenuBean bean:list){
            contentValues = new ContentValues();
            contentValues.put("title", bean.title);
            contentValues.put("position", bean.pos);
            contentValues.put("icon_id", bean.iconId);
            if (bean.selected) {
                contentValues.put("selected", 1);
            } else {
                contentValues.put("selected", 0);
            }
            String whereClause = "title=?";
            String[] whereArgs = {bean.title};
            db.update(TABLE_NAME, contentValues, whereClause, whereArgs);
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    //查询符合选中条件记录
    public List<CustomMenuBean> queryItemsBySelection(int selection) {
        SQLiteDatabase db = getReadableDatabase();
        List<CustomMenuBean> list = new ArrayList<>();
        String[] selectionArgs = {String.valueOf(selection)};
        SQLiteStatement statement = db.compileStatement("");
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
        cursor.close();
        db.close();
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
        cursor.close();
        db.close();
        return list;
    }
}
