package com.akshayjadhav.studentrecord;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class StudentCursorAdapter extends CursorAdapter {
    public StudentCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView textViewName = view.findViewById(R.id.student_name);
        TextView textViewEmail = view.findViewById(R.id.student_email);
        TextView textViewCC = view.findViewById(R.id.student_cc);

        String name = cursor.getString(cursor.getColumnIndex("NAME"));
        String email = cursor.getString(cursor.getColumnIndex("EMAIL"));
        String CC = cursor.getString(cursor.getColumnIndex("COURSE_COUNT"));

        textViewName.setText(name);
        textViewEmail.setText(email);
        textViewCC.setText(CC);
    }
}
