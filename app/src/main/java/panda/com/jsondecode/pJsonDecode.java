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

        Map     reMap       = new HashMap();    //存放解析后的结果
        int     lindex      = 0;                //左下标
        int     rindex      = 1;                //右下标
        String  keystring;                      //存放Map表的key值

        //去除jsonString首尾的{}
        jsonString = jsonString.substring(1, jsonString.length() - 1);

        while (true) {
            while ( (jsonString.charAt(rindex) != '\"'
                    || jsonString.charAt(rindex+1) != ':')
                    && rindex < jsonString.length() ) {
                rindex ++;
            }

            //字符串 "xxx" 从lindex+1开始对字符串切割，匹配键
            keystring = jsonString.substring(lindex + 1, rindex);
            System.out.println("keystring jsonString.charAt(" + rindex + ") = " + jsonString.charAt(rindex));
            System.out.println("jsonString=" + jsonString);
            System.out.println("keystring=" + keystring);
            lindex = rindex = rindex + 2;

            //匹配值,自动装箱匹配各自返回的值类型
            Object value = "";

            /**
             * 如果遇到"{",说明keyString所对应的值为map对象,则找到"}"下标,递归调用jsonToObject方法
             * 如果遇到"[",说明keyString所对应的值为List对象,则找到"]"下标,调用jsonToArray方法
             * 如果为""",则改值为String类型,直接将"""去掉,直接存入value即可
             * 如果无符号,则说明该值为int、double、float等值、强制将其转换为String类型存入value
             */
            if (jsonString.charAt(lindex) == '{') {
                rindex = findRightPartIndex(jsonString, lindex, rindex, '{');
                value = jsonToObject(jsonString.substring(lindex, rindex + 2));
            } else if (jsonString.charAt(lindex) == '[') {
                rindex = findRightPartIndex(jsonString, lindex, rindex, '[');
                value = jsonToArray(jsonString.substring(lindex, rindex + 1));
            } else if (jsonString.charAt(lindex) == '\"') {
                rindex = findRightPartIndex(jsonString, lindex, rindex, '\"');
                value = jsonString.substring(lindex + 1, rindex);
            } else {
                rindex = findRightPartIndex(jsonString, lindex, rindex);
                value = jsonString.substring(lindex, rindex + 2);
            }

            //将键值对存入reMap中
            reMap.put(keystring, value);

            if(rindex == jsonString.length() - 1) {
                break;
            }

            //若未到尾部，此时rindex在逗号前面
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

        List    list    = new ArrayList();  //存放解析后的结果
        int     lindex  = 0;                //左下标
        int     rindex  = 0;                //右下标
        int     i       = 0;                //数组游标,标示符

        //去除jsonString的首尾[]
        jsonString = jsonString.substring(1, jsonString.length() - 1);

        /**
         * 如果遇到有{，说明此处为object对象，则取出该段字符串，调用jsonToObject方法放回map对象、并将其加进list中
         * 其他类型则直接将其强制转换为String类型加入list当中即可
         */
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
                Map m = pJsonDecode.jsonToObject(jsonString.substring(i, rindex+1));
                list.add(m);
                i = rindex;
                if (rindex == jsonString.length() - 1) {
                    break;
                }
            } else if (jsonString.charAt(i) == ',') {
                rindex = i;
                String str = jsonString.substring(lindex, rindex);
                lindex = rindex + 1;
                list.add(str);
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

        int     leftPartCount   =   1;
        char    rc              =   '\"';

        if (c == '{') {
            rc = '}';
        } else if (c == '\"') {
            rc = '\"';
        } else if (c == '[') {
            rc = ']';
        } else if (c == ',') {
            return 0;
        }

        //System.out.println("before while rindex=" + rindex);

        while (rindex < jsonString.length()) {
            rindex ++;
            System.out.println("jsonString.length()=" + jsonString.length() + " rindex=" + rindex);
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

    /**
     * 除去{["三种类型,无标点符号括起来的字符,传入字符串,找到","即返回字符串右下标
     * @param js
     * @param lindex
     * @param rindex
     * @return
     */
    private static int findRightPartIndex(String js, int lindex, int rindex) {

        while (js.length() - rindex > 4) {
            rindex++;
            char charIndex = js.charAt(rindex + 1);
            if (charIndex == ',')
                return rindex;
        }
        return js.length() - 1;
    }
}
