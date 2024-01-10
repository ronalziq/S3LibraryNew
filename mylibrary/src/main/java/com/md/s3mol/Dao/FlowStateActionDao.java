package com.md.s3mol.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.md.s3mol.Database.DataBaseUtil;
import com.md.s3mol.Models.FlowStateActions;

import java.util.ArrayList;
import java.util.List;

public class FlowStateActionDao {
    Context context;
    private SQLiteDatabase db;
    DataBaseUtil dbUtil;

    public FlowStateActionDao(Context context){
        this.context = context;
    }
    public Boolean add(FlowStateActions stateActions){
        try {
            dbUtil =  new DataBaseUtil(context);
            db = dbUtil.openConnection();
            ContentValues values =  new ContentValues();
            values.put(FlowStateActions.FSA_Id, stateActions.getFSA_ID());
            values.put(FlowStateActions.Action_Name, stateActions.getActionName());
            values.put(FlowStateActions.RequestPay_load, stateActions.getRequestPayload());
            values.put(FlowStateActions.ResponsePay_load, stateActions.getResponsePayload());
            values.put(FlowStateActions.EntryDateTime, stateActions.getActionStarDateTime());
            values.put(FlowStateActions.FK_fsid, stateActions.getFK_FSID());
            values.put(FlowStateActions.EndDateTime, stateActions.getActionEndDateTime());
            db.insert(FlowStateActions.TABLE_NAME, null,values);
             return true;


        }
        catch (Exception e){
            //AppLogger.i("DB Exception", E.getMessage());
            e.printStackTrace();
            return  false;
        }
        finally
        {
            dbUtil.closeConnection();
        }

    }
    public List<FlowStateActions> getFlowStateActionLogs(){
        List<FlowStateActions> actionList = new ArrayList<>();
        try {

            dbUtil = new DataBaseUtil(context);
            db = dbUtil.openConnection();
            Cursor cursor = db.rawQuery(FlowStateActions.SELECT_QUERY, null);
            if(cursor.moveToFirst()){
                do{
                    FlowStateActions  actions = FlowStateActions.ConvertToEntity(cursor);
                    actionList.add(actions);

                }
                while (cursor.moveToNext());
            }
            cursor.close();
            return actionList;}
        catch (SQLException e)
        {
           // AppLogger.i("DB Exception", e.toString());
            return actionList;
        }
        finally {
            dbUtil.closeConnection();}

    }
    public Boolean delete(FlowStateActions actions) {
        try {

            dbUtil = new DataBaseUtil(context);
            this.db = dbUtil.openConnection();
            db.delete(FlowStateActions.TABLE_NAME, "Id=?",
                    new String[]{String.valueOf(FlowStateActions.Id)});


            return true;

        } catch (Exception e) {
           // AppLogger.i("DB Exception", e.toString());
            return false;

        } finally {
            dbUtil.closeConnection();
        }

    }
}
