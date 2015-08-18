package panda.com.main;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import panda.com.jsondecode.R;
import panda.com.jsondecode.ReadJsonFile;
import panda.com.jsondecode.pJsonDecode;


public class MainActivity extends ActionBarActivity {

    private final static    String      jsonFileName = "test_dump.json";    //json文件名
    private                 EditText    jstr_et;
    private                 EditText    map_et;
    private                 EditText    get_et1;
    private                 EditText    get_et2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化控件
        jstr_et     = (EditText) findViewById(R.id.jstr_et);
        map_et      = (EditText) findViewById(R.id.map_et);
        get_et1     = (EditText) findViewById(R.id.get_et1);
        get_et2     = (EditText) findViewById(R.id.get_et2);

        String      jsonStr;    //用于存放读取json文件的字符串

        jsonStr = ReadJsonFile.readLocalJson(MainActivity.this, jsonFileName);
        //如果json文件中含有中文,则调用readLocalJsonWithChinese方法
        //jsonStr = ReadJsonFile.readLocalJsonWithChinese(MainActivity.this.jsonFileName);

        //Log.i("jsonStr", jsonStr);

        //调用jsonToObject方法，传入jsonStr,返回解析后的map对象
        Map map = pJsonDecode.jsonToObject(jsonStr);

        //遍历map
        Set keys = map.keySet();
        Iterator it = keys.iterator();

        while (it.hasNext()) {
            String key = (String) it.next();
            System.out.println( key + "--->" + map.get(key) );
        }

        //将遍历的部分结果传至activity当中显示
        jstr_et.setText(jsonStr);
        map_et.setText(map.toString());
        get_et1.setText(map.get("aaa").toString());
        get_et2.setText(((List)map.get("aaa")).get(0).toString());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
