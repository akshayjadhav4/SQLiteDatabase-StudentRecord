package com.akshayjadhav.studentrecord;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

//    Step3
    public final static String DATABASE_NAME = "MyStudent.db";
    public final static String TABLE_NAME = "mystudent_table";
    public final static String COL_1 = "_id";
    public final static String COL_2 = "NAME";
    public final static String COL_3 = "EMAIL";
    public final static String COL_4 = "COURSE_COUNT";


//    Step2
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

//    Step1.1
    @Override
    public void onCreate(SQLiteDatabase db) {

//        Step4 First time creates a table if not exists
//        (take care of blank spaces between queries )
        db.execSQL("CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME +
                " (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " NAME TEXT," +
                " EMAIL TEXT," +
                " COURSE_COUNT INTEGER)");
    }
//    Step1.2
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

//        Step5 delete database and then create new
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

//    Step6  insert data method
    public  boolean insertData(String name, String email, String courseCount ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
//        get data
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,email);
        contentValues.put(COL_4,courseCount);

//        add data
        long result = db.insert(TABLE_NAME,null,contentValues);
//        Check data added or not
        if (result == -1){
            return false;
        }else{
            return true;
        }
    }

//    Step7 update data
    public boolean updateData(String id,String name, String email, String courseCount ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
//        get data
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,email);
        contentValues.put(COL_4,courseCount);

//        update data
        db.update(TABLE_NAME,contentValues,"_id=?",new String[]{id});
        return true;

    }

//    Step8 get data with id(one record only)
    public Cursor getData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE _id='"+id+"'";
        Cursor cursor = db.rawQuery(query,null);
        return cursor;
    }

//    Step9 delete data with id(one record only)
    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME,"_id=?",new String[]{id});
    }


//    Step10 delete all data
    public void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,null,null);
    }

//    Step11 select all data
    public  Cursor selectAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return cursor;
    }
}
