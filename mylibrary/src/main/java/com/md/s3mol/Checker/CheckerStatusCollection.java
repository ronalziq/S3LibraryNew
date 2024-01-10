package com.md.s3mol.Checker;

import java.util.ArrayList;
import java.util.List;

public class CheckerStatusCollection {
    public List<CheckerStatus> checkerStatuses = new ArrayList<>();
    public Boolean isOperationContinue;
    public String FSA_Id;

    public String getFSA_Id() {
        return FSA_Id;
    }

    public void setFSA_Id(String fsa_Id) {
        this.FSA_Id = fsa_Id;
    }

    public List<CheckerStatus> getCheckerStatuses() {
        return checkerStatuses;
    }

    public void setCheckerStatuses(List<CheckerStatus> checkerStatuses) {
        this.checkerStatuses = checkerStatuses;
    }

    public Boolean getOperationContinue() {
        return isOperationContinue;
    }

    public void setOperationContinue(Boolean operationContinue) {
        isOperationContinue = operationContinue;
    }
}
