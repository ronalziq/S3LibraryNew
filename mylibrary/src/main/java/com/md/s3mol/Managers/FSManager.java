package com.md.s3mol.Managers;

import android.app.Activity;
import android.content.Context;

import com.md.s3mol.Checker.CheckerStatusCollection;
import com.md.s3mol.Common.S3MOLConstants;
import com.md.s3mol.Common.SystemHelper;
import com.md.s3mol.Common.SystemInfo;
import com.md.s3mol.Common.Utility;
import com.md.s3mol.Dao.FlowStateActionDao;
import com.md.s3mol.Dao.FlowStatesDao;
import com.md.s3mol.Interfaces.State;
import com.md.s3mol.Models.FlowStateActions;
import com.md.s3mol.Models.FlowStates;

/**
 *
 */
public class FSManager {
    /**
     *
     */
    public static FSManager instance ;
    /**
     *
     */
    public Context context;
    /**
     *
     */
    FSDBManager fsdbManager;
    /**
     *
     */
    public GCManager gcManager;
    SystemInfo device;
    /**
     * this is the flow state id, which represent the actually state in database.
     */
    int flowState_Id;

    private FSManager(){}

    public GCManager getGcManager() {
        return gcManager;
    }

    public void setGcManager(GCManager gcManager) {
        this.gcManager = gcManager;
    }

    /**
     *
     * @return
     */
    public static  FSManager getInstance() {
        if (instance == null){
            instance =new FSManager();
        }
        return instance;
    }

    /**
     *
     * @param newContext
     */
    public void setContext(Context newContext){

        context = newContext;
    }


    public void initializeDevice(Activity context){
        device = SystemHelper.getSystemInformation(context);
    }
    /**
     *
     * @param stateObject
     * @return
     */
    public String initState(State stateObject){
        String fs_id = "";
        if(stateObject != null) {
            fs_id = registerState(stateObject);
        }
        return fs_id;
    }

    /**
     *
     * @return
     */
    private FSDBManager getFSdbManager(){
        if (fsdbManager == null){
            return new FSDBManager();
        }
        return fsdbManager;
    }

    /**
     *
     * @param stateObject
     * @return
     */
    private String registerState(State stateObject){
        instance.fsdbManager = getFSdbManager();

        return instance.fsdbManager.registerFlowState(
                stateObject.getSyncDataId(),
                stateObject.getCurrentScreenName(),
                stateObject.getPayLoad(),
                stateObject.getParentScreenId());
    }

    public void updateSyncDataId(String syncDataId){
        instance.fsdbManager.updateSyncDataId(syncDataId);
    }
    public String registerStatusAction(String actionName,
                                                        String requestPayload,
                                                        String responsePayload,
                                                        String startDT,
                                                        String endDT,
                                                        String flowStateId) {
        instance.fsdbManager = getFSdbManager();
        String fsa_id = instance.fsdbManager.registerFSAction(actionName,
                requestPayload,
                responsePayload,
                startDT,
                endDT,
                flowStateId);
        return fsa_id;
    }
    /**
     *
     * @param actionName
     * @param requestPayload
     * @param responsePayload
     * @param startDT
     * @param endDT
     * @param flowStateId
     * @param isGCRun
     * @param condition_Constant
     * @return
     */
    public CheckerStatusCollection registerStatusAction(String actionName,
                                              String requestPayload,
                                              String responsePayload,
                                              String startDT,
                                              String endDT,
                                              String flowStateId,
                                              Boolean isGCRun,
                                              String condition_Constant){
        instance.fsdbManager = getFSdbManager();
        String fsa_id = instance.fsdbManager.registerFSAction(actionName,
                requestPayload,
                responsePayload,
                startDT,
                endDT,
                flowStateId);
        CheckerStatusCollection operation_status = null;
        if(isGCRun){
            gcManager = GCManager.getInstance();
            gcManager.setContext(context);
            operation_status = gcManager.registerGC(fsa_id, condition_Constant);
            if (S3MOLConstants.GC_ACTIONS_NONE != condition_Constant)
                operation_status.setFSA_Id(fsa_id);
        }
        return operation_status;
    }

    private class FSDBManager {
        String FS_GUID = "";
        String FSA_GUID = "";

        public String registerFlowState(String syncDataId,
                                        String currentScreen,
                                        String payload,
                                        String previousScreen){
            FlowStates flowStates = new FlowStates();
            try {
                FS_GUID = Utility.createTransactionID();
            } catch (Exception e){
                e.printStackTrace();
                //AppLogger.i("Exception", e.getMessage());
            }
            flowStates.setFS_ID(FS_GUID);
            flowStates.setCurrentScreen(currentScreen);
            flowStates.setPayLoad(payload);
            flowStates.setParentScreenId(previousScreen);

            flowStates.setModelNumber(device.getModel());
            flowStates.setProjectSessionid(0);


            flowStates.setEntryDateTime(Utility.getCurrentDateTime());
            flowStates.setSyncDataId(syncDataId);
            FlowStatesDao flowStatesDao = new FlowStatesDao(context);
            flowStatesDao.add(flowStates);
            Utility.exportDatabase(context,665);
            return FS_GUID;
        }

        public String registerFSAction(String actionName,
                                     String requestPayload,
                                     String responsePayload,
                                     String startDT,
                                     String endDT,
                                     String flowStateId){
            try{
            FlowStateActions flowStateActions = new FlowStateActions();

                FSA_GUID = Utility.createTransactionID();

            flowStateActions.setFSA_ID(FSA_GUID);
            flowStateActions.setActionName(actionName);
            flowStateActions.setRequestPayload(requestPayload);
            flowStateActions.setResponsePayload(responsePayload);
            flowStateActions.setActionStarDateTime(startDT);
            flowStateActions.setActionEndDateTime(endDT);
            flowStateActions.setFK_FSID(flowStateId);
            FlowStateActionDao actionDao =new FlowStateActionDao(context);
            actionDao.add(flowStateActions);
            }catch (Exception e){
                e.printStackTrace();
            }
            return FSA_GUID;

        }
        public boolean updateSyncDataId(String syncDataId){
            try {
                FlowStatesDao flowStatesDao = new FlowStatesDao(context);
                flowStatesDao.updateSyncDataId(device.getModel(), syncDataId);
                return true;
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }

        }
    }
}
