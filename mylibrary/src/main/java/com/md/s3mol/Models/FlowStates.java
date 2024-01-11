package com.md.s3mol.Models;

import android.database.Cursor;



public class FlowStates {
    private int id;
    private String  FS_ID;  //flow state id
    private String  SyncDataId;  //flow state id
    private  String    ModelNumber;
    private  int ProjectSessionid;

    private String parentScreenId;  //==?? SELF JOIN integer or string
    private String currentScreen; //Screen name
    private String entryDateTime;// date time
    private String payLoad; // what data it takes with it self

    public String getSyncDataId() {
        return SyncDataId;
    }

    public void setSyncDataId(String syncDataId) {
        SyncDataId = syncDataId;
    }


//    1,dt234wefewt,null,start-app,,null,
//    2,faf3sdg453,dt234wefewt,{'fa':'etetr'},login-screen,



    public int getProjectSessionid() {
        return ProjectSessionid;
    }

    public void setProjectSessionid(int projectSessionid) {
        ProjectSessionid = projectSessionid;
    }

    public String getModelNumber() {
        return ModelNumber;
    }

    public void setModelNumber(String modelNumber) {
        ModelNumber = modelNumber;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFS_ID() {
        return FS_ID;
    }

    public void setFS_ID(String FS_ID) {
        this.FS_ID = FS_ID;
    }

    public String getParentScreenId() {
        return parentScreenId;
    }

    public void setParentScreenId(String parentScreenId) {
        this.parentScreenId = parentScreenId;
    }

    public String getCurrentScreen() {
        return currentScreen;
    }

    public void setCurrentScreen(String currentScreen) {
        this.currentScreen = currentScreen;
    }

    public String getEntryDateTime() {
        return entryDateTime;
    }

    public void setEntryDateTime(String entryDateTime) {
        this.entryDateTime = entryDateTime;
    }

    public String getPayLoad() {
        return payLoad;
    }

    public void setPayLoad(String payLoad) {
        this.payLoad = payLoad;
    }



    public static String  TABLE_NAME = "tFlowState";
    public static String  Id = "Id";
    public static String  FS_Id = "PkId";
    public static String  SyncDataid = "SyncDataId";
    public static String  ParentScreenId = "ParentScreenId";
    public static String  CurrentScreen = "CurrentScreen";
    public static String  EntryDateTime = "EntryDateTime";
    public static String  PayLoad =     "PayLoad";
    public static String  Model_No = "ModelNo";
    public static String  Project_Session_id = "ProjectSessionId";

    public static String Status = "ServerStatus";

    //Queries
    public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("+
            Id  + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
            FS_Id + " TEXT,"+
            ParentScreenId +  " TEXT,"+
              Model_No + " TEXT,"+
           Project_Session_id +  " INTEGER,"
            + CurrentScreen + " TEXT,"
            +SyncDataid + " TEXT,"
            + EntryDateTime + " TEXT,"
            + PayLoad +" TEXT" + ")";

    public static String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;


    public static String SELECT_QUERY = "SELECT * FROM " + TABLE_NAME;





    public static FlowStates ConvertToEntity(Cursor cursor){
        FlowStates states = new FlowStates();
        try{
            states.setId(cursor.getInt(0));
            states.setFS_ID(cursor.getString(1));
            states.setParentScreenId(cursor.getString(2));
            states.setProjectSessionid(cursor.getInt(3));
            states.setModelNumber(cursor.getString(4));
            states.setCurrentScreen(cursor.getString(5));
            states.setSyncDataId(cursor.getString(6));
            states.setPayLoad(cursor.getString(7));
            states.setEntryDateTime(cursor.getString(8));
        }
        catch (Exception ex){
            //AppLogger.i("cursor Exception", ""+ex.getMessage().toString());
        }



    return states ;}












}
