package com.md.s3mol.remote;

import android.content.Context;

import com.md.s3mol.Dao.FlowStateActionDao;
import com.md.s3mol.Models.FlowStateActions;

import java.util.ArrayList;
import java.util.List;

public class SyncFlowStateAction {
    Context context;
    FlowStateActionDao flowStateActionDao;
    List<FlowStateActions> flowStateActionsList;

    public SyncFlowStateAction(Context context){
        this.context = context;
    }

    public List<FlowStateActions> getFlowStateActionsList (){
        try{
            flowStateActionDao = new FlowStateActionDao(context);
            flowStateActionsList = flowStateActionDao.getFlowStateActionLogs();
        } catch (Exception e){
            e.printStackTrace();
        }
        if (flowStateActionsList == null){
            return flowStateActionsList = new ArrayList<>();
        }else {
            return flowStateActionsList;
        }
    }
}
