package com.baontq.asm.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.baontq.asm.DTO.KhoanChi;
import com.baontq.asm.database.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class KhoanChiDAO {
    private final SQLiteDatabase db;

    public KhoanChiDAO(Context context) {
        DBHelper helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    public List<KhoanChi> getAll() {
        List<KhoanChi> list = new ArrayList<>();
        Cursor cursor = db.query("tbl_khoanchi", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            list.add(new KhoanChi(cursor.getInt(0), cursor.getInt(3), cursor.getString(1), cursor.getString(2), cursor.getDouble(4)));
        }
        return list;
    }

    public List<KhoanChi> getByMonth(String month) {
        List<KhoanChi> list = new ArrayList<>();
        Cursor cursor = db.query("tbl_khoanchi", null, "strftime('%m', kc_time) = ?", new String[]{month}, null, null, null);
        while (cursor.moveToNext()) {
            list.add(new KhoanChi(cursor.getInt(0), cursor.getInt(3), cursor.getString(1), cursor.getString(2), cursor.getDouble(4)));
        }
        return list;
    }

    public int insert(KhoanChi khoanChi) {
        ContentValues values = new ContentValues();
        values.put("kc_name", khoanChi.getName());
        values.put("kc_time", khoanChi.getTime());
        values.put("lc_id", khoanChi.getIdLc());
        values.put("kc_cost", khoanChi.getCost());
        return (int) db.insert("tbl_khoanchi", null, values);
    }

    public int update(KhoanChi khoanChi) {
        ContentValues values = new ContentValues();
        values.put("kc_name", khoanChi.getName());
        values.put("kc_time", khoanChi.getTime());
        values.put("lc_id", khoanChi.getIdLc());
        values.put("kc_cost", khoanChi.getCost());
        return db.update("tbl_khoanchi", values, "kc_id = ?", new String[]{String.valueOf(khoanChi.getId())});
    }

    public int delete(int id) {
        return db.delete("tbl_khoanchi", "kc_id = ?", new String[]{String.valueOf(id)});
    }
}
