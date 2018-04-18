package com.purchase.sls.common.unit;

import android.Manifest;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JWC on 2017/4/20.
 */

public class PermissionUtil {
    /**
     * android.permission-group.CALENDAR:android.permission.READ_CALENDAR;android.permission.WRITE_CALENDAR
     * android.permission-group.CAMERA:android.permission.CAMERA
     *android.permission-group.CONTACTS:android.permission.READ_CONTACTS;android.permission.WRITE_CONTACTSandroid.permission.GET_ACCOUNTS
     * android.permission-group.LOCATION:android.permission.ACCESS_FINE_LOCATION;android.permission.ACCESS_COARSE_LOCATION
     * android.permission-group.MICROPHONE:android.permission.RECORD_AUDIO
     * android.permission-group.PHONE:android.permission.READ_PHONE_STATE;android.permission.CALL_PHONE;android.permission.READ_CALL_LOG;android.permission.WRITE_CALL_LOG;com.android.voicemail.permission.ADD_VOICEMAIL;android.permission.USE_SIP;android.permission.PROCESS_OUTGOING_CALLS
     * android.permission-group.SENSORS:android.permission.BODY_SENSORS
     * android.permission-group.SMS:android.permission.SEND_SMS;android.permission.RECEIVE_SMS;android.permission.READ_SMS;android.permission.RECEIVE_WAP_PUSH;android.permission.RECEIVE_MMS;android.permission.READ_CELL_BROADCASTS
     * android.permission-group.STORAGE:android.permission.READ_EXTERNAL_STORAGE;android.permission.WRITE_EXTERNAL_STORAGE
     * @param groups
     * @return
     */
    public static String[] permissionGroup(List<String> groups, List<String> noGroupPermissions){
        List<String> permissions = new ArrayList<>();
        if (groups!= null && !groups.isEmpty()){
            for (String group : groups){
                if (TextUtils.equals(Manifest.permission_group.CALENDAR,group))
                    permissions.add(Manifest.permission.READ_CALENDAR);
                else if (TextUtils.equals(Manifest.permission_group.CAMERA,group))
                    permissions.add(Manifest.permission.CAMERA);
                else if (TextUtils.equals(Manifest.permission_group.CONTACTS,group))
                    permissions.add(Manifest.permission.READ_CONTACTS);
                else if (TextUtils.equals(Manifest.permission_group.LOCATION,group))
                    permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
                else if (TextUtils.equals(Manifest.permission_group.MICROPHONE,group))
                    permissions.add(Manifest.permission.RECORD_AUDIO);
                else if (TextUtils.equals(Manifest.permission_group.PHONE,group))
                    permissions.add(Manifest.permission.READ_PHONE_STATE);
                else if (TextUtils.equals(Manifest.permission_group.SENSORS,group))
                    permissions.add(Manifest.permission.BODY_SENSORS);
                else if (TextUtils.equals(Manifest.permission_group.SMS,group))
                    permissions.add(Manifest.permission.SEND_SMS);
                else if (TextUtils.equals(Manifest.permission_group.STORAGE,group))
                    permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }
        if (noGroupPermissions != null &&!noGroupPermissions.isEmpty()){
            permissions.addAll(noGroupPermissions);
        }

        return permissions.toArray(new String[permissions.size()]);
    }
}
