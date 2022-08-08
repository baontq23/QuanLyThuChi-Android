package com.baontq.asm.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {
    public static final int DB_VERSION = 3;
    public static final String DB_NAME = "DB_MOB202_ASM";
    public static final String TABLE_LOAITHU_CREATE = "CREATE TABLE " +
            "tbl_loaithu (" +
            "lt_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "lt_name TEXT NOT NULL" +
            ")";
    public static final String TABLE_KHOANTHU_CREATE = "CREATE TABLE " +
            "tbl_khoanthu (" +
            "kt_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "kt_name TEXT NOT NULL, " +
            "kt_time TEXT, " +
            "lt_id INTEGER, " +
            "kt_cost REAL NOT NULL, " +
            "FOREIGN KEY (lt_id) REFERENCES tbl_loaithu (lt_id))";
    public static final String TABLE_LOAICHI_CREATE = "CREATE TABLE " +
            "tbl_loaichi (" +
            "lc_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "lc_name TEXT NOT NULL" +
            ")";
    public static final String TABLE_KHOANCHI_CREATE = "CREATE TABLE " +
            "tbl_khoanchi (" +
            "kc_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "kc_name TEXT NOT NULL, " +
            "kc_time TEXT, " +
            "lc_id INTEGER, " +
            "kc_cost REAL NOT NULL, " +
            "FOREIGN KEY (lc_id) REFERENCES tbl_loaichi (lc_id))";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_LOAITHU_CREATE);
        sqLiteDatabase.execSQL(TABLE_KHOANTHU_CREATE);
        sqLiteDatabase.execSQL(TABLE_LOAICHI_CREATE);
        sqLiteDatabase.execSQL(TABLE_KHOANCHI_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tbl_khoanthu");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tbl_loaithu");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tbl_khoanchi");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tbl_loaichi");
        onCreate(sqLiteDatabase);
    }
}
