package com.md.s3mol.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.md.s3mol.Database.DataBaseUtil;
import com.md.s3mol.Models.FlowStates;

import java.util.ArrayList;
import java.util.List;

public class FlowStatesDao {
    Context context;
    private SQLiteDatabase db;
    DataBaseUtil dbUtil;

    public FlowStatesDao(Context context){
        this.context = context;
    }
    public  Boolean add(FlowStates states){
        try{
            dbUtil =new DataBaseUtil(context);
            db = dbUtil.openConnection();
            ContentValues values =  new ContentValues();
            values.put(FlowStates.FS_Id, states.getFS_ID());
            values.put(FlowStates.ParentScreenId, states.getParentScreenId());
            values.put(FlowStates.CurrentScreen, states.getCurrentScreen());
            values.put(FlowStates.Model_No,states.getModelNumber());
//            values.put(FlowStates.Project_Session_id,states.getProjectSessionid());
            values.put(FlowStates.PayLoad, states.getPayLoad());
            values.put(FlowStates.EntryDateTime, states.getEntryDateTime());
            db.insert(FlowStates.TABLE_NAME, null,values);


            return  true;


        }

        catch (SQLException E ){
           // AppLogger.i("DB Exception", E.getMessage());
            return  false;
        }
        finally
        {
            dbUtil.closeConnection();
        }

    }
    public List<FlowStates> getFlowStateLogs(){
        List<FlowStates> flowStatesList = new ArrayList<>();
        try {

            dbUtil = new DataBaseUtil(context);
            db = dbUtil.openConnection();
            Cursor cursor = db.rawQuery(FlowStates.SELECT_QUERY, null);
            if(cursor.moveToFirst()){
                do{
                    FlowStates states   = FlowStates.ConvertToEntity(cursor);
                    flowStatesList.add(states);

                }
                while (cursor.moveToNext());
            }
            cursor.close();
            return flowStatesList;}
        catch (SQLException e)
        {
            //AppLogger.i("DB Exception", e.toString());
            return flowStatesList;
        }
        finally {
            dbUtil.closeConnection();}

    }

    public List<FlowStates> getFlowStateLogsForPending(){
        List<FlowStates> flowStatesList = new ArrayList<>();
        String queryForPending = "SELECT * FROM "+FlowStates.TABLE_NAME+" WHERE "+ FlowStates.Status+" = 'pending';";
        try {

            dbUtil = new DataBaseUtil(context);
            db = dbUtil.openConnection();
            Cursor cursor = db.rawQuery(queryForPending, null);
            if(cursor.moveToFirst()){
                do{
                    FlowStates states   = FlowStates.ConvertToEntity(cursor);
                    flowStatesList.add(states);

                }
                while (cursor.moveToNext());
            }
            cursor.close();
            return flowStatesList;}
        catch (SQLException e)
        {
            //AppLogger.i("DB Exception", e.toString());
            return flowStatesList;
        }
        finally {
            dbUtil.closeConnection();}

    }
    }
    public Boolean delete(FlowStates states) {
        try {

            dbUtil = new DataBaseUtil(context);
            this.db = dbUtil.openConnection();
            db.delete(FlowStates.TABLE_NAME, "Id=?",
                    new String[] { String.valueOf(FlowStates.Id) });


            return true;

        } catch (Exception e) {
           // AppLogger.i("DB Exception", e.toString());
            return false;

        } finally {
            dbUtil.closeConnection();
        }


    }

    public boolean updateSyncDataId(String modelNo, String syncDataId){
        String UPDATE_SYNCDATAID_QUERY = "UPDATE "+FlowStates.TABLE_NAME +
                " SET "+FlowStates.SyncDataid + " = "+syncDataId+
                " WHERE "+FlowStates.SyncDataid + " IS NULL"+
                " AND " + FlowStates.Model_No + " = '"+modelNo+"';";

        dbUtil = new DataBaseUtil(context);
        this.db = dbUtil.openConnection();
        try {
            ContentValues values = new ContentValues();
            values.put(FlowStates.SyncDataid,syncDataId);
            String whereClause = FlowStates.SyncDataid + " = ? AND " + FlowStates.Model_No+" = ?";
//            int updatedRows = db.update(FlowStates.TABLE_NAME,values,whereClause, new String[]{"null",modelNo});
            db.execSQL(UPDATE_SYNCDATAID_QUERY);
//            System.out.println(updatedRows);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        } finally {
            dbUtil.closeConnection();
        }
    }
}
