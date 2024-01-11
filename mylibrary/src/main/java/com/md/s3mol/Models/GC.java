package com.md.s3mol.Models;

import android.database.Cursor;



//
public class GC {
    private  int id;
    private  String GC_ID; //guid
    private  String startDateTime;
    private  String endDateTime;
    private  String process;//shows tag that what gc is doing
    private  String FK_FSAID; //key from  the flowstate action table  IS FK for gc

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    private  String Status;


    public  int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }


    public String getStartDateTime() {
        return startDateTime;
    }


    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }



    public String getGC_ID() {
        return GC_ID;
    }

    public void setGC_ID(String GC_ID) {
        this.GC_ID = GC_ID;
    }

    public String getFK_FSAID() {
        return FK_FSAID;
    }

    public void setFK_FSAID(String FK_FSAID) {
        this.FK_FSAID = FK_FSAID;
    }

    public static String TABLE_NAME_gc = "tGC";
    public static String ID = "Id";
    public static String  GC_Id= "PkId";
    public static String Process  = "Process";
    public static String StartDate = "StartDate";
    public static String EndDate = "EndDate";
    public static String StateFlowActionId = "fk_FlowStateActionId";
    public static String Server_Status = "ServerStatus";



    public static String CREATE_TABLE = "CREATE TABLE " +
            TABLE_NAME_gc + "("+
            ID  + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
            GC_Id + " TEXT,"+
            Process + " TEXT,"+
             Server_Status + " TEXT,"+
            StartDate + " TEXT," +
            EndDate +" TEXT,"
            + StateFlowActionId + " TEXT"+")";
    public static String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME_gc;


    public static String SELECT_QUERY = "SELECT * FROM " + TABLE_NAME_gc;
    public  static  GC ConvertToEntity(Cursor cursor){
        GC gc =  new GC();
        try {
        gc.setId(cursor.getInt(0));
        gc.setGC_ID(cursor.getString(1));
        gc.setProcess(cursor.getString(2));
        gc.setStartDateTime(cursor.getString(3));
        gc.setEndDateTime(cursor.getString(4));
        gc.setFK_FSAID(cursor.getString(5));
        gc.setStatus(cursor.getString(6));}
        catch (Exception ex){
          //  AppLogger.i("cursor Exception", ""+ex.getMessage().toString());
        }

        return gc;
    }









}
