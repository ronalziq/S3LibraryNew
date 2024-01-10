package com.md.s3mol.Checker;

public class CheckerStatus {
    public Boolean isProcessContinue = false;
    public String message;
    public String operation;
    public String result;

    public Boolean getProcessContinue() {
        return isProcessContinue;
    }

    public void setProcessContinue(Boolean processContinue) {
        isProcessContinue = processContinue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
