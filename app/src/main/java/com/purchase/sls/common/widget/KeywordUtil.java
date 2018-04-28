package com.purchase.sls.common.widget;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by JWC on 2016/12/29.
 */

public class KeywordUtil {
    public static SpannableString matcherSearchTitle(int color, String text,
                                                     String keyword) {
        if(keyword!=null) {
            SpannableString s = new SpannableString(text);
            Pattern p = Pattern.compile(keyword);
            Matcher m = p.matcher(s);
            while (m.find()) {
                int start = m.start();
                int end = m.end();
                s.setSpan(new ForegroundColorSpan(color), start, end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            return s;
        }
        return null;
    }

    public static SpannableString matcherActivity(int color, String text) {
            SpannableString s = new SpannableString(text);
            Pattern p = Pattern.compile("[0-9.]*");
            Matcher m = p.matcher(s);
            while (m.find()) {
                int start = m.start();
                int end = m.end();
                s.setSpan(new ForegroundColorSpan(color), start, end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            return s;
    }

    public static String matcherHtml(String text){
        String s="";
        String regex="(http|ftp|https):\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(text);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            s=text.substring(start,end);
        }
        return s;
    }
}
