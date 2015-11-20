package com.example.dmanjunathakinik.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 20;
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Reads the todo things from file
        readItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        //items = new ArrayList<>();
        //populates the list view
        itemsAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, items);
        lvItems.setAdapter(itemsAdapter);

        //setting up 2 listeners.
        //One for removing items
        //One for editing item
        setupListViewListener();
        itemClickListener();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    /**
     * Listener to remove an item on long click
     */
    private void setupListViewListener(){
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                //Removes item from the position
                items.remove(position);
                //Refreshes the grid
                itemsAdapter.notifyDataSetChanged();
                //Writes the new set to the file
                writeItems();
                return false;
            }
        });

    }

    /**
     * Listener to edit the item text by taking it to a different screen
     */
    public void itemClickListener(){
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Pass item position and text as a part of the intent to EditItemActivity class
                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                i.putExtra("position", Integer.toString(position));
                i.putExtra("text", items.get(position));

                //startActivity(i);
                //Start the subactivity
                startActivityForResult(i, REQUEST_CODE);
            }
        });
    }

    /**
     * Function called after the subactivity is completed
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == RESULT_OK){
            //Get the result back from Edit Activity
            String name = data.getExtras().getString("newItemData");
            int pos = data.getExtras().getInt("position");
            //Remove the item
            items.remove(pos);
            //Add the new item
            items.add(pos,name);
            //Refresh the grid
            itemsAdapter.notifyDataSetChanged();
            //update the items by writing it to the file
            writeItems();
        }
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

    /**
     * On button click listener
     * @param v
     */
    public  void onAddItem(View v){
        //Read the item text enetered
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        //Add the item to the list
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        //Write the new set to the file
        writeItems();
    }

    /**
     * Function to read the items from the file on load
     */
    private void readItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try{

            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        }
        catch(IOException e){
            items = new ArrayList<String>();
        }
    }

    /**
     * Function to write the items to the file
     */
    private void writeItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try{
            FileUtils.writeLines(todoFile,items);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }


}
