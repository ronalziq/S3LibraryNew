package com.md.s3mol.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.md.s3mol.Models.FlowStateActions;
import com.md.s3mol.Models.FlowStates;
import com.md.s3mol.Models.GC;
import com.md.s3mol.Models.GCDetail;


public class DataBaseUtil extends SQLiteOpenHelper {


    private static String DB_NAME = "s3mol_db";
    private static int DB_VERSION = 1;
    public DataBaseUtil(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // execute the query string to the database.

        db.execSQL(GC.CREATE_TABLE);
        db.execSQL(GCDetail.CREATE_TABLE);
        db.execSQL(FlowStates.CREATE_TABLE);
        db.execSQL(FlowStateActions.CREATE_TABLE);


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion >= newVersion)
            return;

        // drop tables.

        db.execSQL(GC.DROP_TABLE);


        onCreate(db);
    }

    public SQLiteDatabase openConnection() {
        SQLiteDatabase dbConnection = this.getWritableDatabase();
        return dbConnection;
    }

    public void closeConnection() {
        this.close();
    }
//    public SQLiteDatabase openConnection() {
//        String path = "/storage/emulated/0/.MarkitSurvey/DBBackup/Markit_V2_db_1696840403263_1006.db";
//        File sd = new File(path);
////       String backupDBPath = "Markit_V2_db_1690991824241_821";
////        File dbfile = new File(sd,backupDBPath);
//        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(sd, null);
////        SQLiteDatabase db = SQLiteDatabase.openDatabase(sd.getPath(),null, SQLiteDatabase.OPEN_READWRITE);
//        //SQLiteDatabase dbConnection = this.getWritableDatabase();
//        return db;
//    }
}
