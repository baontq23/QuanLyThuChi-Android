package com.baontq.asm.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.baontq.asm.DTO.KhoanThu;
import com.baontq.asm.database.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class KhoanThuDAO {
    private final SQLiteDatabase db;

    public KhoanThuDAO(Context context) {
        DBHelper helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    public List<KhoanThu> getAll() {
        List<KhoanThu> list = new ArrayList<>();
        Cursor cursor = db.query("tbl_khoanthu", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            list.add(new KhoanThu(cursor.getInt(0), cursor.getInt(3), cursor.getString(1), cursor.getString(2), cursor.getDouble(4)));
        }
        return list;
    }

    public List<KhoanThu> getByMonth(String month) {
        List<KhoanThu> list = new ArrayList<>();
        Cursor cursor = db.query("tbl_khoanthu", null, "strftime('%m', kt_time) = ?", new String[]{month}, null, null, null);
        while (cursor.moveToNext()) {
            list.add(new KhoanThu(cursor.getInt(0), cursor.getInt(3), cursor.getString(1), cursor.getString(2), cursor.getDouble(4)));
        }
        return list;
    }

    public int insert(KhoanThu khoanThu) {
        ContentValues values = new ContentValues();
        values.put("kt_name", khoanThu.getName());
        values.put("kt_time", khoanThu.getTime());
        values.put("lt_id", khoanThu.getIdLt());
        values.put("kt_cost", khoanThu.getCost());
        return (int) db.insert("tbl_khoanthu", null, values);
    }

    public int update(KhoanThu khoanThu) {
        ContentValues values = new ContentValues();
        values.put("kt_name", khoanThu.getName());
        values.put("kt_time", khoanThu.getTime());
        values.put("lt_id", khoanThu.getIdLt());
        values.put("kt_cost", khoanThu.getCost());
        return db.update("tbl_khoanthu", values, "kt_id = ?", new String[]{String.valueOf(khoanThu.getId())});
    }

    public int delete(int id) {
        return db.delete("tbl_khoanthu", "kt_id = ?", new String[]{String.valueOf(id)});
    }
}
