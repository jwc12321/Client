package com.purchase.sls.common.unit;


public class PhoneNumberUtils {

    /**
     * 加密号码的倒数第8位（包括）之倒数第4位（不包括）
     *
     * @param phoneNumber 原号码
     * @return 加密后的号码
     */
    public static String encryptPhone(String phoneNumber) {
        if (phoneNumber == null) {
            return "";
        }
        int length = phoneNumber.length();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            if (i >= length - 8 && i < length - 4) {
                sb.append("*");
            } else {
                sb.append(phoneNumber.charAt(i));
            }
        }
        return sb.toString();
    }
}
