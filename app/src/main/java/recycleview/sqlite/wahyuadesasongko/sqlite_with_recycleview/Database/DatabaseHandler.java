package recycleview.sqlite.wahyuadesasongko.sqlite_with_recycleview.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Wahyu Ade Sasongko on 5/14/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    //initialize database name, version
    private static final String DATABASE_NAME = "mydbase";
    private static final int DATABASE_VERSION = 1;

    //initialize news table
    private static final String TABLE_NEWS = "news";

    //columm news table
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_BODY = "body";
    private static final String KEY_DATE = "date";
    private static final String KEY_LIKES = "likes";
    private static final String KEY_DISLIKES = "dislikes";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create table news
        String CREATE_NEWS_TABLE = "CREATE TABLE "+ TABLE_NEWS +"(" +
                KEY_ID + " INTEGER PRIMARY KEY,"+
                KEY_TITLE + " TEXT,"+
                KEY_BODY + " TEXT,"+
                KEY_DATE + " LONG,"+
                KEY_LIKES + " INTEGER,"+
                KEY_DISLIKES + " INTEGER"+
                ")";
        db.execSQL(CREATE_NEWS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWS);
        onCreate(db);
    }

    //Method to operate data (Create Read Update Delete)
    public String createNews(News data){
        String CREATE_NEWS = "INSERT INTO "+TABLE_NEWS+" ("+
                KEY_TITLE+","+
                KEY_BODY+","+
                KEY_DATE+","+
                KEY_LIKES+","+
                KEY_DISLIKES+")" + " VALUES ('"+
                data.getTitle()+"','"+
                data.getBody()+"',"+
                data.getDate()+","+
                data.getLikes()+","+
                data.getDislikes()+
        ");";
        Log.d("tes", CREATE_NEWS);
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(CREATE_NEWS);
        Cursor cur = db.query(TABLE_NEWS, new String[] {KEY_ID},KEY_DATE+"="+Long.toString(data.getDate()),null,null,null,null);
        cur.moveToPosition(0);
        String id  = cur.getString(0);
        db.close();
        return id;
    }

    public News readNews(int ID){
        //select readable database (mydbase)
        SQLiteDatabase db = this.getReadableDatabase();

        //catch in cursor query
        Cursor cursor = db.query(TABLE_NEWS, new String[]{
                KEY_TITLE,
                KEY_BODY,
                KEY_DATE,
                KEY_LIKES,
                KEY_DISLIKES},
                KEY_ID + "=?", new String[] {Integer.toString(ID)},null,null,null);
        if(cursor != null)
            cursor.moveToFirst();

        //save the result of query to model projection of news data (without id)
        News data =  new News(cursor.getString(0), cursor.getString(1), Long.parseLong(cursor.getString(2)), Integer.parseInt(cursor.getString(3)),Integer.parseInt(cursor.getString(4)));
        db.close();
        return  data;
    }

    public News[] readAllNews(){
        //initialize query get field data
        String READ_ALL_NEWS = "SELECT * FROM "+TABLE_NEWS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(READ_ALL_NEWS, null);
        News data_all[] = new News[cursor.getCount()];
        for (int cc=0; cc<cursor.getCount();cc++){
            cursor.moveToPosition(cc);
            data_all[cc] = new News(Integer.parseInt(cursor.getString(0)),cursor.getString(1), cursor.getString(2), Long.parseLong(cursor.getString(3)), Integer.parseInt(cursor.getString(4)),Integer.parseInt(cursor.getString(5)));
        }
        db.close();
        return data_all;
    }

    public boolean updateNews(int ID){
        return true;
    }

    public boolean deleteNews(int ID){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_NEWS,KEY_ID+"="+Integer.toString(ID),null);
        db.close();
        return true;
    }

    public boolean addLike(int ID, String like){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues new_val = new ContentValues();
        new_val.put(KEY_LIKES, like);
        db.update(TABLE_NEWS, new_val, KEY_ID+"="+ID,null);
        db.close();
        return true;
    }
    public boolean addDislike(int ID, String dislike){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues new_val = new ContentValues();
        new_val.put(KEY_DISLIKES, dislike);
        db.update(TABLE_NEWS, new_val, KEY_ID+"="+ID,null);
        db.close();
        return true;
    }
}