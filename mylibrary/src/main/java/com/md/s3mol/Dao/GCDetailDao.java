package com.md.s3mol.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.md.s3mol.Database.DataBaseUtil;
import com.md.s3mol.Models.GCDetail;

import java.util.ArrayList;
import java.util.List;

public class GCDetailDao {

    Context context;
    private SQLiteDatabase db;
    DataBaseUtil dbUtil;

    public GCDetailDao(Context context) {
        this.context = context;
    }

    public Boolean add(GCDetail gcDetail) {
        try {
            dbUtil = new DataBaseUtil(context);
            db = dbUtil.openConnection();
            ContentValues values = new ContentValues();
            values.put(GCDetail.gcDId, gcDetail.getGCD_ID());
            values.put(GCDetail.Attempt, gcDetail.getAttempt());
            values.put(GCDetail.AttemptResult, gcDetail.getAttemptResult());
            values.put(GCDetail.Message, gcDetail.getMessage());
            values.put(GCDetail.CreateddateTime, gcDetail.getStartDateTime());
            values.put(GCDetail.Fk_gcid, gcDetail.getFK_GCID());

            db.insert(GCDetail.TABLE_NAME, null, values);
            return true;
        } catch (SQLException E) {
          //  AppLogger.i("DB Exception", E.getMessage());
            return false;
        } finally {
            dbUtil.closeConnection();
        }
    }

    public List<GCDetail> getGCDetailLogs() {
        List<GCDetail> gcDetailList = new ArrayList<>();
        try {

            dbUtil = new DataBaseUtil(context);
            db = dbUtil.openConnection();
            Cursor cursor = db.rawQuery(GCDetail.SELECT_QUERY, null);
            if (cursor.moveToFirst()) {
                do {
                    GCDetail detail = GCDetail.ConvertToEntity(cursor);
                    gcDetailList.add(detail);

                }
                while (cursor.moveToNext());
            }
            cursor.close();
            return gcDetailList;
        } catch (SQLException e) {
           // AppLogger.i("DB Exception", e.toString());
            return gcDetailList;
        } finally {
            dbUtil.closeConnection();
        }


    }
    public Boolean delete(GCDetail detail) {
        try {

            dbUtil = new DataBaseUtil(context);
            this.db = dbUtil.openConnection();
            db.delete(GCDetail.TABLE_NAME, "Id=?",
                    new String[]{String.valueOf(GCDetail.ID)});


            return true;

        } catch (Exception e) {
           // AppLogger.i("DB Exception", e.toString());
            return false;

        } finally {
            dbUtil.closeConnection();
        }
    }
}