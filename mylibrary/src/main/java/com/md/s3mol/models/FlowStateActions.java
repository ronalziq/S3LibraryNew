package com.md.s3mol.Models;

import android.database.Cursor;

public class FlowStateActions {
    private int id;
    private String FSA_ID;
    private String ActionName;
    private String RequestPayload;
    private String ResponsePayload;
    private String actionStarDateTime;

    public String getActionEndDateTime() {
        return actionEndDateTime;
    }

    public void setActionEndDateTime(String actionEndDateTime) {
        this.actionEndDateTime = actionEndDateTime;
    }

    private String actionEndDateTime;
    private String createdAt;
    private String updatedAt;
    private String FK_FSID;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFSA_ID() {
        return FSA_ID;
    }

    public void setFSA_ID(String FSA_ID) {
        this.FSA_ID = FSA_ID;
    }

    public String getActionName() {
        return ActionName;
    }

    public void setActionName(String actionName) {
        ActionName = actionName;
    }

    public String getRequestPayload() {
        return RequestPayload;
    }

    public void setRequestPayload(String requestPayload) {
        RequestPayload = requestPayload;
    }

    public String getResponsePayload() {
        return ResponsePayload;
    }

    public void setResponsePayload(String responsePayload) {
        ResponsePayload = responsePayload;
    }


    public String getFK_FSID() {
        return FK_FSID;
    }

    public void setFK_FSID(String FK_FSID) {
        this.FK_FSID = FK_FSID;
    }



    public String getActionStarDateTime() {
        return actionStarDateTime;
    }

    public void setActionStarDateTime(String actionStarDateTime) {
        this.actionStarDateTime = actionStarDateTime;
    }



    public static String  TABLE_NAME = "tFlowStateAction";
    public static String  Id = "id";
    public static String  FSA_Id = "PkId";
    public static String  Action_Name  = "ActionName";
    public static String  RequestPay_load  = "RequestPayload";
    public static String  ResponsePay_load  = "ResponsePayload";
    public static String  EntryDateTime  = "ActionStartDateTime";
    public static String  EndDateTime  = "ActionEndDateTime";

    public static String  FK_fsid  = "FlowStateActionId";

    public static String Status = "ServerStatus";

    public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("+
            Id  + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
            FSA_Id + " TEXT,"+ Action_Name +  " TEXT," +
            RequestPay_load+ " TEXT,"
            + EntryDateTime + " TEXT,"
            + EndDateTime + " TEXT,"
            + ResponsePay_load +" TEXT," +  FK_fsid +" TEXT"+")";

    public static String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;


    public static String SELECT_QUERY = "SELECT * FROM " + TABLE_NAME;

    public static FlowStateActions ConvertToEntity(Cursor cursor){
        FlowStateActions actions =   new FlowStateActions();
        try{
        actions.setId(cursor.getInt(0));
        actions.setFSA_ID(cursor.getString(1));
        actions.setActionName(cursor.getString(2));
        actions.setRequestPayload(cursor.getString(3));
        actions.setResponsePayload(cursor.getString(4));
        actions.setFK_FSID(cursor.getString(5));
        actions.setActionStarDateTime(cursor.getString(6));
        actions.setActionEndDateTime(cursor.getString(7));

        }
        catch (Exception ex){
            //AppLogger.i("cursor Exception", ""+ex.getMessage().toString());
        }


    return actions;}





}
