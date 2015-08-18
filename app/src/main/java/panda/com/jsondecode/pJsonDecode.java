package panda.com.jsondecode;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/8/17.
 */
public class pJsonDecode {

    /**
     * 传入jsonString，将其转换为Map对象
     * @param jsonString
     * @return
     */
    public static Map<String, Object> jsonToObject(String jsonString) {
        Map reMap = new HashMap();
        //去除首尾{}
        jsonString = jsonString.substring(1, jsonString.length() - 1);

        int lindex = 0;
        int rindex = 1;
        String keystring;
        while (true) {
            while (jsonString.charAt(rindex) != '\"' || jsonString.charAt(rindex+1) != ':') {
                rindex ++;
            }

            //字符串 "xxx" 从lindex+1开始对字符串切割，匹配键
            keystring = jsonString.substring(lindex + 1, rindex);
            System.out.println("keystring jsonString.charAt(" + rindex + ") = " + jsonString.charAt(rindex));
            lindex = rindex = rindex + 2;
            //匹配值
            Object value = "";
            if (jsonString.charAt(lindex) == '{') {
                rindex = findRightPartIndex(jsonString, lindex, rindex, '{');
                value = jsonToObject(jsonString.substring(lindex, rindex + 1));
            } else if (jsonString.charAt(lindex) == '[') {
                rindex = findRightPartIndex(jsonString, lindex, rindex, '[');
                value = jsonToArray(jsonString.substring(lindex, rindex + 1));
            } else if (jsonString.charAt(lindex) == '\"') {
                rindex = findRightPartIndex(jsonString, lindex, rindex, '\"');
                value = jsonString.substring(lindex + 1, rindex);
            } else {
                rindex = findRightPartIndex(jsonString, lindex, rindex);
                value = jsonString.substring(lindex, rindex + 1);
            }

            reMap.put(keystring, value);
            if(rindex == jsonString.length() - 1) {
                break;
            }

            lindex = rindex + 2;
            rindex += 3;
        }

        return reMap;
    }

    /**
     * 传入jsonString，将其转换为List对象
     * @param jsonString
     * @return
     */
    public static List jsonToArray(String jsonString) {
        List list = new ArrayList();
        //去除首尾[]
        jsonString = jsonString.substring(1, jsonString.length() - 1);
        int rindex = 0;
        int i = 0;
        while (i < jsonString.length()) {
            if (jsonString.charAt(i) == '{') {
                int leftPartCount = 1;
                rindex = i;
                Map.Entry<String, Object> entry;
                while (leftPartCount != 0) {
                    rindex ++;
                    if (jsonString.charAt(rindex) == '{') {
                        leftPartCount ++;
                    }
                    if (jsonString.charAt(rindex) == '}') {
                        leftPartCount --;
                    }
                }
                Map m = pJsonDecode.jsonToObject(jsonString.substring(i, rindex));
                list.add(m);
                i = rindex;
                if (rindex == jsonString.length() - 1) {
                    break;
                }
            }
            i ++;
        }

        return list;
    }

    /**
     * 查找c字符的另一半在在jsonString的位置
     * @param jsonString
     * @param lindex
     * @param rindex
     * @param c
     * @return
     */
    private static int findRightPartIndex(String jsonString, int lindex, int rindex, char c) {
        int leftPartCount = 1;
        char rc = '\"';

        if (c == '{') {
            rc = '}';
        } else if (c == '\"') {
            rc = '\"';
        } else if (c == '[') {
            rc = ']';
        } else if (c == ',') {
            return 0;
        }

        while (rindex < jsonString.length()) {
            rindex ++;
            char charIndex = jsonString.charAt(rindex);
            if (charIndex == rc) {
                leftPartCount --;
            } else if (charIndex == c) {
                leftPartCount ++;
            }

            if (leftPartCount == 0) {
                break;
            }
        }

        if (rindex == jsonString.length()) {
            throw new RuntimeException("jsonString 长度越界");
        }

        return rindex;
    }

    private static int findRightPartIndex(String js, int lindex, int rindex) {

        int leftPartCount = 1;

        while (js.length() - rindex > 4) {
            rindex++;
            char charIndex = js.charAt(rindex + 1);
            if (charIndex == ',')
                return rindex;

        }
        return js.length() - 1;
    }
}
