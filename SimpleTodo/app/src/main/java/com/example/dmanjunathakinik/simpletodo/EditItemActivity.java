package com.example.dmanjunathakinik.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    private int pos = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        //Get the text from the intent and display it on the textbox
        String itemText = getIntent().getStringExtra("text");

        pos = Integer.parseInt(getIntent().getStringExtra("position"));
        EditText editText = (EditText) findViewById(R.id.itemText);
        editText.setText(itemText);
        editText.setSelection(editText.getText().length());



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    /**
     * Button click listener
     * @param v
     */
    public void updateItem(View v){
        //Gets the new text
        EditText editText = (EditText)findViewById(R.id.itemText);
        //Create a new intent and populate the new text and position
        Intent data = new Intent();
        data.putExtra("newItemData", editText.getText().toString());
        data.putExtra("position", pos);
        setResult(RESULT_OK, data);
        //Send it back to parent
        finish();
    }

}
