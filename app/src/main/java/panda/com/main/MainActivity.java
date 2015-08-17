package panda.com.main;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Map;

import panda.com.jsondecode.R;
import panda.com.jsondecode.ReadJsonFile;
import panda.com.jsondecode.pJsonDecode;


public class MainActivity extends ActionBarActivity {

    private final static String jsonFileName = "test_dump.json";
    private EditText jstr_et;
    private EditText map_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jstr_et = (EditText) findViewById(R.id.jstr_et);
        map_et = (EditText) findViewById(R.id.map_et);

        String jsonStr;
        jsonStr = ReadJsonFile.readLocalJson(MainActivity.this, jsonFileName);

        jstr_et.setText(jsonStr);

        Log.i("jsonStr", jsonStr);

        Map map = pJsonDecode.jsonToObject(jsonStr);

        map_et.setText(map.toString());

        System.out.println("map<ccc,?> = " + map.get("ccc"));

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
