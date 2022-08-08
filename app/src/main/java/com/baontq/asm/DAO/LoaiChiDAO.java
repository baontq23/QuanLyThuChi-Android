package com.baontq.asm.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.baontq.asm.DTO.LoaiChi;
import com.baontq.asm.database.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class LoaiChiDAO {
    private SQLiteDatabase db;

    public LoaiChiDAO(Context context) {
        DBHelper helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    public List<LoaiChi> getAll() {
        List<LoaiChi> list = new ArrayList<>();
        String sql = "SELECT * from tbl_loaichi";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            list.add(new LoaiChi(cursor.getInt(0), cursor.getString(1)));
        }
        return list;
    }

    public LoaiChi getById(int id) {
        Cursor cursor = db.query("tbl_loaichi", null,"lc_id = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor.moveToNext()){
            return new LoaiChi(cursor.getInt(0), cursor.getString(1));
        }else {
            return null;
        }
    }

    public int insert(LoaiChi loaiChi) {
        ContentValues values = new ContentValues();
        values.put("lc_name", loaiChi.getName());
        return (int) db.insert("tbl_loaichi", null, values);
    }

    public int update(LoaiChi loaiChi) {
        ContentValues values =new ContentValues();
        values.put("lc_id", loaiChi.getId());
        values.put("lc_name", loaiChi.getName());
        return db.update("tbl_loaichi", values, "lc_id = ?", new String[]{String.valueOf(loaiChi.getId())});
    }

    public int delete(int id) {
        db.delete("tbl_khoanchi", "lc_id = ?", new String[]{String.valueOf(id)});
        return db.delete("tbl_loaichi", "lc_id = ?", new String[]{String.valueOf(id)});
    }
}
