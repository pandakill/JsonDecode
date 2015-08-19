package panda.com.jsondecode;

/**
 * Created by Administrator on 2015/8/17.
 */
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.res.AssetManager;

public class ReadJsonFile {

    /**
     * 通过字符流从assets资源文件读取json文件，返回字符串
     * @param context
     * @param fileName
     * @return jsonString
     */
    public static String readLocalJson(Context context,  String fileName){
        String  jsonString       =   "";
        String  resultString     =   "";
        try {
            AssetManager assetManager = context.getResources().getAssets();
            BufferedReader bufferedReader;
            bufferedReader = new BufferedReader( new InputStreamReader(assetManager.open(fileName)) );
            while ((jsonString = bufferedReader.readLine() ) != null) {
                resultString+=jsonString;
            }
            //去掉字符串当中的所有空格
            resultString = resultString.replaceAll(" ","");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString;
    }

    /**
     * 通过IO流从assets文件中读取含有中文字符串的json文件，并返回jsonString
     * @param context
     * @param fileName
     * @return jsonString
     */
    public static String readLocalJsonWithChinese(Context context, String fileName){
        String jsonString = "";
        try {
            AssetManager assetManager = context.getResources().getAssets();
            InputStream inputStream = assetManager.open( fileName );
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            jsonString = new String(buffer, "GB2312");
        }catch ( Exception e ){
            e.printStackTrace();
        }
        return jsonString;
    }


}
