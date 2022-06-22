package my.gps_attendance;

import static java.sql.DriverManager.println;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

public class DBHelper extends SQLiteOpenHelper {
    public static String DB_NAME = "student.db";
    public static String TABLE_NAME = "STUDENT";
    private static int VERSION = 1;

    public DBHelper(@NonNull Context context){
        super(context, DB_NAME, null, VERSION);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase){
        String sql = "create table if not exists " + DBHelper.TABLE_NAME + "(" + " sNum integer PRIMARY KEY autoincrement, " + " sName text, " + " sPW text, " + " sMajor text, " + " sGrade integer)";

        sqLiteDatabase.execSQL(sql);
    }

    public void onOpen(SQLiteDatabase sqLiteDatabase){
        println("onOpen 호출됨");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if(newVersion > 1){
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(sqLiteDatabase);
        }
    }
}
