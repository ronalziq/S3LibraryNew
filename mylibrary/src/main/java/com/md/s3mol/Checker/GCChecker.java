package com.md.s3mol.Checker;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.content.ContextCompat;

import com.md.s3mol.Common.S3MOLConstants;
import com.md.s3mol.Common.S3MOLMessages;
import com.md.s3mol.Common.SystemHelper;
import com.md.s3mol.Common.Utility;

import java.util.List;


public class GCChecker {

    public Context context;
    public GCChecker(Context context){
        this.context = context;
    }

    public CheckerStatusCollection INTERNET_AND_MEMORY(){
        CheckerStatusCollection statusCollection = new CheckerStatusCollection();
        CheckerStatus checkerStatus = null;
        List<CheckerStatus> checkerStatusList = statusCollection.getCheckerStatuses();
        checkerStatus = _internetChecker();
        checkerStatusList.add(checkerStatus);
        checkerStatus = _memoryChecker();
        checkerStatusList.add(checkerStatus)
        ;

        return statusCollection;}
    public CheckerStatusCollection PERMISSION_AND_MEMORY(){
        CheckerStatusCollection statusCollection = new CheckerStatusCollection();
        CheckerStatus checkerStatus = null;
        List<CheckerStatus> checkerStatusList = statusCollection.getCheckerStatuses();
        checkerStatus = _permissionChecker();
        checkerStatusList.add(checkerStatus);
        checkerStatus = _memoryChecker();
        checkerStatusList.add(checkerStatus);


        return statusCollection;}
    public CheckerStatusCollection INTERNET_AND_PERMISSION(){
        CheckerStatusCollection statusCollection = new CheckerStatusCollection();
        CheckerStatus checkerStatus = null;
        List<CheckerStatus> checkerStatusList = statusCollection.getCheckerStatuses();
        checkerStatus = _permissionChecker();
        checkerStatusList.add(checkerStatus);
        checkerStatus = _internetChecker();
        checkerStatusList.add(checkerStatus);


        return statusCollection;}

    public CheckerStatusCollection gcCheckerAll(){
        //init Checker Status Collection
        CheckerStatusCollection statusCollection = new CheckerStatusCollection();

        CheckerStatus checkerStatus = null;
        List<CheckerStatus> checkerStatusList = statusCollection.getCheckerStatuses();

        checkerStatus = _permissionChecker();
        checkerStatusList.add(checkerStatus);

        checkerStatus = _internetChecker();
        checkerStatusList.add(checkerStatus);

        checkerStatus = _memoryChecker();
        checkerStatusList.add(checkerStatus);

        statusCollection.setOperationContinue(false);
        return statusCollection;
    }
    public CheckerStatusCollection memoryChecker(){
        CheckerStatusCollection statusCollection = new CheckerStatusCollection();
        CheckerStatus checkerStatus = _memoryChecker();
        statusCollection.getCheckerStatuses().add(checkerStatus);

    return statusCollection;}

    private CheckerStatus _memoryChecker(){
        double availMem = 0;
        // init the system helper to get the available memory
        availMem = SystemHelper.getAvailableRam(context);
        // init Checker Status
        CheckerStatus checkerStatus = new CheckerStatus();
        //init Checker Status Collection
        CheckerStatusCollection statusCollection = new CheckerStatusCollection();
        // Action is memory check
        checkerStatus.setOperation(S3MOLConstants.GC_ACTION_LOAD);
        if (availMem >= 300.0 ){
            checkerStatus.setProcessContinue(S3MOLConstants.GC_ACTION_REQUIRED);
            checkerStatus.setResult(S3MOLConstants.GC_ACTION_RESULT_YES);
            checkerStatus.setMessage(S3MOLMessages.GC_MESSAGE_MEMORY_AVAILABLE);
        }
        else {
            checkerStatus.setProcessContinue(S3MOLConstants.GC_ACTION_NOT_REQUIRED);
            checkerStatus.setResult(S3MOLConstants.GC_ACTION_RESULT_NO);
            checkerStatus.setMessage(S3MOLMessages.GC_MESSAGE_MEMORY_NOT_AVAILABLE);
        }
        return  checkerStatus;
    }

    public CheckerStatusCollection permissionChecker(){
        //init Checker Status Collection
        CheckerStatusCollection statusCollection = new CheckerStatusCollection();
        CheckerStatus checkerStatus = _permissionChecker();
        statusCollection.getCheckerStatuses().add(checkerStatus);
        return statusCollection;
    }
    private CheckerStatus _permissionChecker() {
        // init Checker Status
        CheckerStatus checkerStatus = new CheckerStatus();

        checkerStatus.setOperation(S3MOLConstants.GC_ACTION_PERMISSION);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            final int checkAudio = ContextCompat.checkSelfPermission(context,
                    Manifest.permission.RECORD_AUDIO);
            final int checkStorage = ContextCompat.checkSelfPermission(context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            final int checkLocation = ContextCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_COARSE_LOCATION);
            final int camera = ContextCompat.checkSelfPermission(context,
                    Manifest.permission.CAMERA);

            if (checkAudio == PackageManager.PERMISSION_GRANTED &&
                    checkStorage == PackageManager.PERMISSION_GRANTED &&
                    checkLocation == PackageManager.PERMISSION_GRANTED &&
                    camera == PackageManager.PERMISSION_GRANTED) {
                checkerStatus.setProcessContinue(S3MOLConstants.GC_ACTION_REQUIRED);
                checkerStatus.setResult(S3MOLConstants.GC_ACTION_RESULT_YES);
                checkerStatus.setMessage(S3MOLMessages.GC_MESSAGE_PERMISSION_GIVEN);

            }
            else{
                checkerStatus.setProcessContinue(S3MOLConstants.GC_ACTION_NOT_REQUIRED);
                checkerStatus.setResult(S3MOLConstants.GC_ACTION_RESULT_NO);
                StringBuilder messageBuilder = new StringBuilder();
                messageBuilder.append(checkAudio == PackageManager.PERMISSION_GRANTED ?
                        S3MOLMessages.GC_MESSAGE_PERMISSION_AUDIO_GIVEN : S3MOLMessages.GC_MESSAGE_PERMISSION_AUDIO_NOT_GIVEN);
                messageBuilder.append(checkStorage == PackageManager.PERMISSION_GRANTED ?
                        S3MOLMessages.GC_MESSAGE_PERMISSION_STORAGE_GIVEN : S3MOLMessages.GC_MESSAGE_PERMISSION_STORAGE_NOT_GIVEN);
                messageBuilder.append(checkLocation == PackageManager.PERMISSION_GRANTED ?
                        S3MOLMessages.GC_MESSAGE_PERMISSION_LOCATION_GIVEN : S3MOLMessages.GC_MESSAGE_PERMISSION_LOCATION_NOT_GIVEN);
                messageBuilder.append(camera == PackageManager.PERMISSION_GRANTED ?
                        S3MOLMessages.GC_MESSAGE_PERMISSION_CAMERA_GIVEN : S3MOLMessages.GC_MESSAGE_PERMISSION_CAMERA_NOT_GIVEN);
                checkerStatus.setMessage(messageBuilder.toString());

            }
        }
        return checkerStatus;
    }

    public CheckerStatusCollection internetChecker(){
        CheckerStatus checkerStatus = _internetChecker();
        //init Checker Status Collection
        CheckerStatusCollection statusCollection = new CheckerStatusCollection();
        statusCollection.getCheckerStatuses().add(checkerStatus);
        if(checkerStatus.isProcessContinue)
            statusCollection.setOperationContinue(S3MOLConstants.GC_ACTION_REQUIRED);
        else
            statusCollection.setOperationContinue(S3MOLConstants.GC_ACTION_NOT_REQUIRED);
        return statusCollection;
    }
    private CheckerStatus _internetChecker(){
        //Core Internet checking working
        boolean internet_permission = Utility.isNetworkAvailable(context);

        //init checker status object for internet
        CheckerStatus checkerStatus = new CheckerStatus();
        checkerStatus.setOperation(S3MOLConstants.GC_ACTION_INTERNET);

        if(internet_permission){
            checkerStatus.setMessage(S3MOLMessages.GC_MESSAGE_INTERNET_AVAILABLE);
            checkerStatus.setResult(S3MOLConstants.GC_ACTION_RESULT_YES);
            checkerStatus.setProcessContinue(S3MOLConstants.GC_ACTION_REQUIRED);
        }
        else{
            checkerStatus.setMessage(S3MOLMessages.GC_MESSAGE_INTERNET_NOT_AVAILABLE);
            checkerStatus.setResult(S3MOLConstants.GC_ACTION_RESULT_NO);
            checkerStatus.setProcessContinue(S3MOLConstants.GC_ACTION_NOT_REQUIRED);
        }
        return checkerStatus;
    }
}
