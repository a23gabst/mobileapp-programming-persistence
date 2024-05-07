package com.example.persistence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;
    private Button readBtn, writeBtn;
    private TextView textDisplay;
    private EditText nameEditText, heightEditText, idEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readBtn=findViewById(R.id.read_btn);
        writeBtn=findViewById(R.id.write_btn);
        textDisplay=findViewById(R.id.textShow);
        nameEditText=findViewById(R.id.first_edtxt);
        heightEditText=findViewById(R.id.second_edtxt);
        idEditText=findViewById(R.id.third_edtxt);

        databaseHelper=new DatabaseHelper(this);
        database=databaseHelper.getWritableDatabase();

        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                int height = Integer.parseInt(heightEditText.getText().toString());
                addMountain(name, height);
                textDisplay.setText("Row inserted to database.");
            }
        });

        readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> mountainList = getMountains();
                StringBuilder stringBuilder = new StringBuilder();
                for (String mountain : mountainList) {
                    stringBuilder.append(mountain).append("\n");
                }
                textDisplay.setText(stringBuilder.toString());
            }
        });

        readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> mountainList = getMountains();
                StringBuilder stringBuilder = new StringBuilder();
                for (String mountain : mountainList) {
                    stringBuilder.append(mountain).append("\n");
                }
                textDisplay.setText(stringBuilder.toString());
            }
        });
    }
    private void addMountain(String name, int height) {
        ContentValues values = new ContentValues();
        values.put(DatabaseTables.Mountain.COLUMN_NAME_NAME, name);
        values.put(DatabaseTables.Mountain.COLUMN_NAME_HEIGHT, height);
        database.insert(DatabaseTables.Mountain.TABLE_NAME, null, values);
    }

    private List<String> getMountains() {
        Cursor cursor = database.query(DatabaseTables.Mountain.TABLE_NAME, null, null, null, null, null, null);
        List<String> mountains = new ArrayList<>();
        while (cursor.moveToNext()) {
            String mountainName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseTables.Mountain.COLUMN_NAME_NAME));
            int mountainHeight = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseTables.Mountain.COLUMN_NAME_HEIGHT));
            mountains.add("Name: " + mountainName + ", Height: " + mountainHeight);
        }
        cursor.close();
        return mountains;
    }

}
