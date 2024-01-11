package com.md.s3mol.remote;

import android.content.Context;

import com.md.s3mol.Dao.FlowStatesDao;
import com.md.s3mol.Models.FlowStates;

import java.util.ArrayList;
import java.util.List;


public class SyncFlowState {

    Context context;
    FlowStatesDao flowStatesDao;
    List<FlowStates> flowStatesList;
    public SyncFlowState(Context context){
        this.context = context;
    }

    /**
     *
     * @return Data from the table of FlowStates which have the status 'pending' as a list
     */
    public List<FlowStates> getFlowStatesList(){
        try {

            flowStatesDao = new FlowStatesDao(context);
            flowStatesList = flowStatesDao.getFlowStateLogsForPending();
        } catch (Exception e){
            e.printStackTrace();
        }
        if (flowStatesList == null){
            return flowStatesList = new ArrayList<>();
        }else {
            return flowStatesList;
        }
    }
}
