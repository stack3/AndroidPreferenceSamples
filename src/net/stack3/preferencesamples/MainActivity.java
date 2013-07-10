package net.stack3.preferencesamples;

import java.util.ArrayList;

import net.stack3.preferencesamples.sharedpreference.SharedPreferenceSampleActivity;

import com.example.preferencesamples.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {
    private ArrayList<MenuItem> items;
    private ListView listView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        
        MenuItem item;
        
        items = new ArrayList<MenuItem>();
        
        item = new MenuItem();
        item.setTitle("Preference");
        item.setActivityClass(SharedPreferenceSampleActivity.class);
        items.add(item);
        
        listView = (ListView)findViewById(android.R.id.list);
        
        ArrayAdapter<MenuItem> adapter = new ArrayAdapter<MenuItem>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter); 
        listView.setOnItemClickListener(listViewOnItemClickListener);
    }
    
    private OnItemClickListener listViewOnItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
            MenuItem item = items.get(position);
            Intent intent = new Intent(MainActivity.this, item.getActivityClass());
            startActivity(intent);
        }
    };
}
