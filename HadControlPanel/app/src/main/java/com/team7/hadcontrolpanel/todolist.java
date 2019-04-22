package com.team7.hadcontrolpanel;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class todolist extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private EditText itemET;
    private Button btn;
    private ListView itemsList;
    private FirebaseDatabase db;
    private DatabaseReference ref;
    private int itemID = 0;
    int listCount = 0;
    int fbId = 0;
    String key;

    private ArrayList<String> items;
    private ArrayAdapter<String> adapter;

    private ArrayList<String> itemCounter;
    //private ArrayAdapter<String> adapterCounter;


    Map <Integer, Integer> test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todolist);
        itemET = findViewById(R.id.item_edit_text);
        btn =findViewById(R.id.add_btn);
        itemsList=findViewById(R.id.items_list);
        items = filehelper.readData( this);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items );

        itemCounter = filehelper.readData( this);
        //adapterCounter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items );

        itemsList.setAdapter(adapter);
        btn.setOnClickListener(this );
        itemsList.setOnItemClickListener(this);
        test = new HashMap<Integer, Integer>() {{}};
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.add_btn:
                String itemEntered = itemET.getText().toString();
                adapter.add(itemEntered);
                itemCounter.add(itemEntered);
                itemET.setText("");

                filehelper.writeData(items,this);
                db = FirebaseDatabase.getInstance();
                ref = db.getReference("ToDo List");
                key = ref.push().getKey();
                ref.child("Item").child(String.valueOf(itemID)).setValue(itemEntered);

                itemID++;
                Toast.makeText(this, "item added", Toast.LENGTH_SHORT).show();
                test.put(listCount, itemID);
                break;
        }
    }
    private void deleteItem(String itemId) {
        DatabaseReference dItem = FirebaseDatabase.getInstance().getReference("ToDo List").child("Item").child(itemId);
        dItem.removeValue();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //fbId++;
        int key = 0;
        String item = items.get(position);
        System.out.println(position);

        for(int i=0; i < itemCounter.size(); i++) {
            if(itemCounter.get(i).contentEquals(item)) {
                key = i;
                System.out.println(key);
            }
        }
        items.remove(position);
        adapter.notifyDataSetChanged();
        listCount = adapter.getCount();

        filehelper.writeData(items,this);
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("ToDo List");
        //key = ref.child("Item").child(String.valueOf(position)).getKey();
        //System.out.println(key);

        //deleteItem(key);
        //String key = items.get(position);
        System.out.println(key);
        ref.child("Item").child(String.valueOf(key)).removeValue();
        //fbId++;
        //reassignKeys(position);

        Toast.makeText(this, "deleted", Toast.LENGTH_SHORT).show();
    }

    public void reassignKeys(int pos) {
        for (int i = pos; i <= listCount; i++) {
            int tempPos = i+1;
            int tempValue = test.get(tempPos);
            test.remove(i);
            test.put(i, tempValue);
        }
    }
}
