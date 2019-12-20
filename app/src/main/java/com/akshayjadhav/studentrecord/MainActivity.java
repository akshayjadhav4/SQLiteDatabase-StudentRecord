package com.akshayjadhav.studentrecord;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

//    Step1
    DatabaseHelper databaseHelper;

    EditText editTextID,editTextName,editTextEmail,editTextCourseCount;
    Button buttonAdd,buttonGetData,buttonUpdate,buttonDelete, buttonViewAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Step2
        databaseHelper = new DatabaseHelper(this);

        editTextID = findViewById(R.id.editText_id);
        editTextName = findViewById(R.id.editText_name);
        editTextEmail = findViewById(R.id.editText_email);
        editTextCourseCount = findViewById(R.id.editText_CC);

        buttonAdd = findViewById(R.id.button_add);
        buttonGetData = findViewById(R.id.button_view);
        buttonUpdate = findViewById(R.id.button_update);
        buttonDelete = findViewById(R.id.button_delete);
        buttonViewAll = findViewById(R.id.button_viewAll);

        addData();
        getDataWithID();
//        showAllData();
        updateData();
        deleteData();

        buttonViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,StudentRecordActivity.class);
                startActivity(intent);
            }
        });

    }

//    Step3 method for showing message
    private void showMessage(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.create();
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

//    Step4 add data
    public void addData(){
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = databaseHelper.insertData(
                        editTextName.getText().toString(),
                        editTextEmail.getText().toString(),
                        editTextCourseCount.getText().toString());

                if (isInserted == true){
                    Toast.makeText(MainActivity.this,"Record added Successfully.",Toast.LENGTH_SHORT).show();
                    editTextName.setText("");
                    editTextEmail.setText("");
                    editTextCourseCount.setText("");
                }else{
                    Toast.makeText(MainActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    Step5 get data with id
    public void getDataWithID(){
        buttonGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = editTextID.getText().toString();
                if (TextUtils.isEmpty(id)){
                    editTextID.setError("Enter ID");
                    return;
                }

                Cursor cursor = databaseHelper.getData(id);
                String data = null;
                if (cursor.moveToNext()){
                    data = "ID: "+cursor.getString(0)+"\n"+
                            "Name: "+cursor.getString(1)+"\n"+
                            "Email: "+cursor.getString(2)+"\n"+
                            "Course Count: "+cursor.getString(3)+"\n";
                }
                showMessage("Student Info",data);
            }
        });
    }


//    Step6 get all data
    public void showAllData(){
        buttonViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = databaseHelper.selectAllData();

                if (cursor.getCount() ==0){
                    showMessage("Error","Nothing to show.");
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();
                while (cursor.moveToNext()){
                    stringBuffer.append("ID: "+cursor.getString(0)+"\n");
                    stringBuffer.append("Name: "+cursor.getString(1)+"\n");
                    stringBuffer.append("Email: "+cursor.getString(2)+"\n");
                    stringBuffer.append("Course Count: "+cursor.getString(3)+"\n\n");
                }
                showMessage("All Data",stringBuffer.toString());
            }
        });
    }

//    Step7 update data
    public void updateData(){
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = databaseHelper.updateData(
                        editTextID.getText().toString(),
                        editTextName.getText().toString(),
                        editTextEmail.getText().toString(),
                        editTextCourseCount.getText().toString());
                if (isUpdate == true){
                    Toast.makeText(MainActivity.this,"Updated successfully.",Toast.LENGTH_SHORT).show();
                    editTextID.setText("");
                    editTextName.setText("");
                    editTextEmail.setText("");
                    editTextCourseCount.setText("");
                }else{
                    Toast.makeText(MainActivity.this,"Update Error.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    Step8 delete data (only with id)
    public void deleteData(){
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editTextID.getText().toString())){
                    editTextID.setError("Enter ID");
                }
                Integer deletedRow = databaseHelper.deleteData(editTextID.getText().toString());
                if (deletedRow >0){
                    Toast.makeText(MainActivity.this,"Deleted successfully.",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"Delete Error",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
