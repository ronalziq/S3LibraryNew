package com.md.s3mol.Managers;

import android.app.Activity;
import android.content.Context;

import com.md.s3mol.Checker.CheckerStatus;
import com.md.s3mol.Checker.CheckerStatusCollection;
import com.md.s3mol.Checker.GCChecker;
import com.md.s3mol.Common.S3MOLConstants;
import com.md.s3mol.Common.SystemHelper;
import com.md.s3mol.Common.SystemInfo;
import com.md.s3mol.Common.Utility;
import com.md.s3mol.Dao.GCDao;
import com.md.s3mol.Dao.GCDetailDao;
import com.md.s3mol.Models.GC;
import com.md.s3mol.Models.GCDetail;

public class GCManager {
    public static GCManager instance;
    public static Context context;
    public SystemInfo device;

    GCDBManager gcdbManager;

    private GCManager(){}

    public static  GCManager getInstance() {
        if (instance == null){
            instance =new GCManager();
        }
        return instance;
    }

    public  void setContext(Context newContext){
        context = newContext;
        device = SystemHelper.getSystemInformation((Activity) context);}

    /**
     *
     * @return
     */
    private GCManager.GCDBManager getGCdbManager(){
        if (gcdbManager == null){
            return new GCManager.GCDBManager();
        }
        return gcdbManager;
    }

    public String registerGC_For_LocationChecking(String fsa_id){
        //init gc database manager
        instance.gcdbManager =  getGCdbManager();
        String gc_id = instance.gcdbManager.addGC(fsa_id, S3MOLConstants.GC_ACTION_LOCATION_CHECKING);
        return gc_id;
    }
    public String registerGC(String fsa_id){
        //init gc database manager
        instance.gcdbManager =  getGCdbManager();
        String gc_id = instance.gcdbManager.addGC(fsa_id, S3MOLConstants.GC_ACTION_INDEPENDENT_CONDITION);
        return gc_id;
    }
    public void addGCDetail(String gc_id,
                            String operation_name,
                            String result,
                            String message){
        instance.gcdbManager.addGCDetail(gc_id,
                operation_name,
                result,
                message);
    }

    public CheckerStatusCollection registerGC(String fsa_id, String condition_Constant){
        //init gc database manager
        instance.gcdbManager =  getGCdbManager();
        String gc_id = instance.gcdbManager.addGC(fsa_id, condition_Constant);

        //init Global Checker
        GCChecker gcChecker = new GCChecker(context);
        CheckerStatusCollection statusCollection = null;

        //Global Checker for all GC actions
        if(condition_Constant == S3MOLConstants.GC_ACTIONS_ALL){
            statusCollection = gcChecker.gcCheckerAll();
        }
        //Global Checker for internet
        else if (condition_Constant == S3MOLConstants.GC_ACTION_INTERNET){
            statusCollection = gcChecker.internetChecker();
        }
        //Global Checker for permission
        else if (condition_Constant == S3MOLConstants.GC_ACTION_PERMISSION){
            statusCollection = gcChecker.permissionChecker();
        }
        //Global Checker for memory
        else if (condition_Constant == S3MOLConstants.GC_ACTION_LOAD){
            statusCollection = gcChecker.memoryChecker();
       }
        //Global Checker for internet and memory
        else if (condition_Constant == S3MOLConstants.GC_ACTION_INTERNET_AND_MEMORY){
            statusCollection = gcChecker.INTERNET_AND_MEMORY();
        }
        //Global Checker for permission and memory
        else if (condition_Constant == S3MOLConstants.GC_ACTION_PERMISSION_AND_MEMORY){
            statusCollection = gcChecker.PERMISSION_AND_MEMORY();
        }
        //Global Checker for internet and memory
        else if (condition_Constant == S3MOLConstants.GC_ACTION_INTERNET_AND_PERMISSION){
            statusCollection = gcChecker.INTERNET_AND_PERMISSION();
        }

        //inserting operation details in database
        if(statusCollection != null) {
            for (CheckerStatus operation_status : statusCollection.getCheckerStatuses()) {
                instance.gcdbManager.addGCDetail(gc_id,
                        operation_status.getOperation(),
                        operation_status.getResult(),
                        operation_status.getMessage());
            }
        }
        return statusCollection;
    }


    public class GCDBManager {
        public String addGC(String fsa_id, String condition_Constant){
            String GC_ID = "";
            try{
                GC_ID = Utility.createTransactionID();
                GC gc = new GC();

                gc.setProcess(condition_Constant);
                gc.setGC_ID(GC_ID);
                gc.setFK_FSAID(fsa_id);
                gc.setStartDateTime(Utility.getCurrentDateTime());
                gc.setEndDateTime(Utility.getCurrentDateTime());
                gc.setStatus(S3MOLConstants.GC_ACTION_DEFAULT_STATUS);
                GCDao gcDao = new GCDao(context);
                gcDao.add(gc);
            }catch (Exception e){}
            return GC_ID;
        }
        public void addGCDetail(String gc_id,
                                String attempt,
                                String  attemptResult,
                                String message){
            String GCD_ID = "";
            try{
                GCD_ID = Utility.createTransactionID();
                GCDetail detail = new GCDetail();
                detail.setGCD_ID(GCD_ID);
                detail.setAttempt(attempt);
                detail.setAttemptResult(attemptResult);
                detail.setMessage(message);
                detail.setStartDateTime(Utility.getCurrentDateTime());
                detail.setFK_GCID(gc_id);
                GCDetailDao gcDetailDao = new GCDetailDao(context);
                gcDetailDao.add(detail);
            }
        catch (Exception e ) {}
        }
    }
}
