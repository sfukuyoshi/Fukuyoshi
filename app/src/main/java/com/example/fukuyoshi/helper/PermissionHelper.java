package com.example.fukuyoshi.helper;


import static androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.activity.result.ActivityResultLauncher;
import androidx.core.content.ContextCompat;

import com.example.fukuyoshi.R;

public class PermissionHelper {

    public interface PermissionHelperInterface {
        void onGranted();
    }
    public void makePermissionRequest(ActivityResultLauncher<String> requestPermissionLauncher) {
        requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS);
    }


    public void checkPermissionRequest(Activity act, ActivityResultLauncher<String> requestPermissionLauncher, PermissionHelperInterface permissionHelperInterface) {
        if (ContextCompat.checkSelfPermission(act,
                Manifest.permission.READ_CONTACTS)
                == PackageManager.PERMISSION_GRANTED) {
            permissionHelperInterface.onGranted();
        } else if (shouldShowRequestPermissionRationale(act, Manifest.permission.READ_CONTACTS)) {
            showAlertDialog(act, requestPermissionLauncher);
        } else {
            makePermissionRequest(requestPermissionLauncher);
        }
    }

    public void showAlertDialog(Activity act, ActivityResultLauncher<String> requestPermissionLauncher) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(act);
        alertDialogBuilder.setMessage(act.getResources().getString(R.string.permission_confirmation));
                alertDialogBuilder.setPositiveButton(R.string.yes,
                        (arg0, arg1) -> makePermissionRequest(requestPermissionLauncher));

        alertDialogBuilder.setNegativeButton(R.string.no, (dialog, which) -> {
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
