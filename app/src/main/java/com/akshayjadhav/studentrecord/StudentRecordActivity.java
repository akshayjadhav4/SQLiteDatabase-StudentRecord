package com.akshayjadhav.studentrecord;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

public class StudentRecordActivity extends AppCompatActivity {

//    Step1
    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_record);


//        Step2
        databaseHelper = new DatabaseHelper(this);
        Cursor cursor = databaseHelper.selectAllData();


//        Step3
        ListView studentListView = findViewById(R.id.list);
        StudentCursorAdapter cursorAdapter = new StudentCursorAdapter(this,cursor);
        studentListView.setAdapter(cursorAdapter);
        cursorAdapter.changeCursor(cursor);

    }



}
