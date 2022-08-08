package com.baontq.asm.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.baontq.asm.DTO.LoaiThu;
import com.baontq.asm.database.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class LoaiThuDAO {
    private final SQLiteDatabase db;

    public LoaiThuDAO(Context context) {
        DBHelper helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    public List<LoaiThu> getAll() {
        List<LoaiThu> list = new ArrayList<>();
        Cursor cursor = db.query("tbl_loaithu", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            list.add(new LoaiThu(cursor.getInt(0), cursor.getString(1)));
        }

        return list;
    }

    public LoaiThu getById(int id) {
        Cursor cursor = db.query("tbl_loaithu", null,"lt_id = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor.moveToNext()){
            return new LoaiThu(cursor.getInt(0), cursor.getString(1));
        }else {
            return null;
        }
    }

    public int update(LoaiThu loaiThu) {
        ContentValues values = new ContentValues();
        values.put("lt_id", loaiThu.getId());
        values.put("lt_name", loaiThu.getName());
        return db.update("tbl_loaithu", values, "lt_id = ?", new String[]{String.valueOf(loaiThu.getId())});
    }

    public int insert(LoaiThu loaiThu) {
        ContentValues values = new ContentValues();
        values.put("lt_name", loaiThu.getName());
        return (int) db.insert("tbl_loaithu", null, values);
    }

    public int delete(int id) {
        db.delete("tbl_khoanthu", "lt_id = ?", new String[]{String.valueOf(id)});
        return db.delete("tbl_loaithu", "lt_id = ?", new String[]{String.valueOf(id)});
    }
}
