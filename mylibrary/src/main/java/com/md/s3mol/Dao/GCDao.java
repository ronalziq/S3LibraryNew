package com.md.s3mol.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


import com.md.s3mol.Database.DataBaseUtil;
import com.md.s3mol.Models.GC;

import java.util.ArrayList;
import java.util.List;

public class GCDao {

    Context context;
    private SQLiteDatabase db;
    DataBaseUtil dbUtil;

    public GCDao(Context context){
        this.context = context;
    }

   public Boolean add(GC globalChecker){
        try{
            dbUtil= new DataBaseUtil(context);
            db = dbUtil.openConnection();
            ContentValues values  = new ContentValues();
            values.put(GC.GC_Id, globalChecker.getGC_ID());
            values.put(GC.StartDate, globalChecker.getStartDateTime());
            values.put(GC.EndDate, globalChecker.getEndDateTime());

            values.put(GC.Process, globalChecker.getProcess());
            values.put(GC.Server_Status,globalChecker.getStatus());

            values.put(GC.StateFlowActionId, globalChecker.getFK_FSAID());
            db.insert(GC.TABLE_NAME_gc, null,values);
            return true;
        }
        catch (SQLException e)
        {
            //AppLogger.i("DB Exception", e.getMessage());

            return false;
        }
        finally
        {
            dbUtil.closeConnection();
        }



   }
//   public Boolean updateSyncID(String modalNumber, String newSyncId){
//        try{
//            dbUtil= new DataBaseUtil(context);
//            db = dbUtil.openConnection();
//            ContentValues values  = new ContentValues();
//            values.put(GC.Sync_Id, newSyncId);
//            db.update(GC.TABLE_NAME_gc,values,GC.Model_No + "=? ",
//                    new String[]{String.valueOf(modalNumber)});
//
//            return true;
//        }
//        catch (SQLException e)
//        {
//            //AppLogger.i("DB Exception", e.getMessage());
//
//            return false;
//        }
//        finally
//        {
//            dbUtil.closeConnection();
//        }
//
//
//
//  }
  public List<GC>  getGCLogs(){
        List<GC> gcList = new ArrayList<>();
      try {

          dbUtil = new DataBaseUtil(context);
          db = dbUtil.openConnection();
          Cursor cursor = db.rawQuery(GC.SELECT_QUERY, null);
          if(cursor.moveToFirst()){
              do{
                  GC gc  = GC .ConvertToEntity(cursor);
                  gcList.add(gc);

              }
              while (cursor.moveToNext());
          }
          cursor.close();
          return gcList;}
      catch (SQLException e)
      {
         // AppLogger.i("DB Exception", e.toString());
          return gcList;
      }
      finally {
          dbUtil.closeConnection();}

    }
    public Boolean delete(GC gc) {
        try {

            dbUtil = new DataBaseUtil(context);
            this.db = dbUtil.openConnection();
            db.delete(GC.TABLE_NAME_gc, "Id=?",
                    new String[] { String.valueOf(GC.ID) });


            return true;

        } catch (Exception e) {
           // AppLogger.i("DB Exception", e.toString());
            return false;

        } finally {
            dbUtil.closeConnection();
        }

    }

}
