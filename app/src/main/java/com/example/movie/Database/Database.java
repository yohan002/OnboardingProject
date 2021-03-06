package com.example.movie.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    Context context;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Movie.db";
    public static final String TABLE_NAME = "movie";
    public static final String MOVIE_ID = "movie_id";
    public static final String MOVIE_IMAGE = "movie_image";
    public static final String MOVIE_TITLE = "movie_title";
    public static final String MOVIE_RELESASEDATE = "movie_releasedate";
    public static final String MOVIE_RATING = "movie_rating";
    public static final String MOVIE_OVERVIEW = "movie_overview";
    public static final String MOVIE_RUNTIME = "movie_runtime";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS " +TABLE_NAME+"("+
                MOVIE_ID+" VARCHAR PRIMARY KEY,"+
                MOVIE_IMAGE+" VARCHAR,"+
                MOVIE_TITLE+" VARCHAR,"+
                MOVIE_RELESASEDATE+" VARCHAR,"+
                MOVIE_RATING+" VARCHAR,"+
                MOVIE_OVERVIEW+" VARCHAR,"+
                MOVIE_RUNTIME+" VARCHAR"+
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(query);
        onCreate(db);
    }

    public boolean insertData(String id, String image,String title,
                              String releasedate,String rating,
                              String overview,String runtime){
        Integer count = 1;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MOVIE_ID,id);
        values.put(MOVIE_IMAGE,image);
        values.put(MOVIE_TITLE,title);
        values.put(MOVIE_RELESASEDATE,releasedate);
        values.put(MOVIE_RATING,rating);
        values.put(MOVIE_OVERVIEW,overview);
        values.put(MOVIE_RUNTIME,runtime);

        long result = db.insert(TABLE_NAME,null,values);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();

        Integer result = db.delete(TABLE_NAME,"movie_id = ?",new String[]{id});
        return result;
    }

    public Boolean checkData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM movie WHERE movie_id = ?",new String[]{id});
        if(cursor.getCount() > 0){
            return true;
        }else{
            return false;
        }
    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM movie",null);

        return result;
    }
}
