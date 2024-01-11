package com.md.s3mol.Models;

import android.database.Cursor;

public class GCDetail {



    private  int id;
    private  String GCD_ID;//GUID
    private String attempt;

    private String attemptResult;
    private String message;
    private String startDateTime;
    private String FK_GCID;// key from gc table and foriegnkey for the gcdetail




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getGCD_ID() {
        return GCD_ID;
    }

    public void setGCD_ID(String GCD_ID) {
        this.GCD_ID = GCD_ID;
    }


    public String getAttempt() {
        return attempt;
    }

    public void setAttempt(String attempt) {
        this.attempt = attempt;
    }

    public String getAttemptResult() {
        return attemptResult;
    }

    public void setAttemptResult(String attemptResult) {
        this.attemptResult = attemptResult;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }



    public String getFK_GCID() {
        return FK_GCID;
    }

    public void setFK_GCID(String FK_GCID)
    {
        this.FK_GCID = FK_GCID;
    }

    public static String TABLE_NAME = "tGCDetail";

    public static String ID = "Id";
    public static String gcDId  = "PkId";

    public static String Attempt = "Attempt";
    public static String AttemptResult = "AttemptResult";
    public static String Message = "AttemptMessage";

    public static String CreateddateTime = "CreatedDateTime";
    public static String Fk_gcid = "FK_GCID";

    public static String Status = "ServerStatus";


    public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("+
            ID  + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
            gcDId + " TEXT,"
            + Attempt +  " TEXT," +
            AttemptResult + " TEXT,"
            + Message + " TEXT,"
            + CreateddateTime + " TEXT,"
            + Fk_gcid + " TEXT" +
            ")";



    public static String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;


    public static String SELECT_QUERY = "SELECT * FROM " + TABLE_NAME;
    public static GCDetail ConvertToEntity(Cursor cursor){
        GCDetail detail = new GCDetail();
        try{
            detail.setId(cursor.getInt(0));
            detail.setFK_GCID(cursor.getString(1));
            detail.setGCD_ID(cursor.getString(2));
            detail.setAttemptResult(cursor.getString(3));
            detail.setMessage(cursor.getString(4));
            detail.setAttempt(cursor.getString(5));
            detail.setStartDateTime(cursor.getString(6));

        }
        catch (Exception ex){
           // AppLogger.i("cursor Exception", ""+ex.getMessage().toString());
        }

    return  detail;}










}
