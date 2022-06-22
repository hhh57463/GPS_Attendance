package my.gps_attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class Login extends AppCompatActivity {
    SQLiteDatabase db;
    DBHelper dbHelper;

    EditText id, pw;
    String sId, sPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button Login_Btn = findViewById(R.id.btn_login);
        id = findViewById(R.id.Text_ID);
        pw = findViewById(R.id.Text_PW);

        try {
            createDatabase();
            //db.execSQL("DROP TABLE IF EXISTS " + DBHelper.TABLE_NAME);
        }
        catch (Exception e) {
            //Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        id.setText("");
        pw.setText("");

        Login_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sId = id.getText().toString();
                sPw = pw.getText().toString();

                if(!sId.isEmpty() && !sPw.isEmpty()) {
                    try {
                        searchRecord(Integer.parseInt(sId), sPw);
                    }
                    catch (Exception e){
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                else
                    Toast.makeText(Login.this, "학번 또는 비밀번호가 입력되지 않았습니다.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void createDatabase() {
        //dbHelper = new DBHelper(this);
        //db = dbHelper.getWritableDatabase();
        db = openOrCreateDatabase(DBHelper.DB_NAME, MODE_PRIVATE, null);
        createTable();
        //db.execSQL("DROP TABLE IF EXISTS " + DBHelper.TABLE_NAME);
    }

    private void createTable() {
        if(db == null)
            return;
        db.execSQL("create table if not exists " + DBHelper.TABLE_NAME + "(" + " sNum integer PRIMARY KEY autoincrement, " + " sName text, " + " sPW text, " + " sMajor text, " + " sGrade integer)");
        insertRecord();
    }

    private void insertRecord() {
        if(db == null)
            return;
        db.execSQL("insert into " + DBHelper.TABLE_NAME + "(sNum, sName, sPW, sMajor, sGrade) " + " values " + "( 20181375, '심민석', '1234', '컴퓨터공학과', 3 )");
        db.execSQL("insert into " + DBHelper.TABLE_NAME + "(sNum, sName, sPW, sMajor, sGrade) " + " values " + "( 20181426, '홍승엽', '4321', '컴퓨터공학과', 3 )");
        db.execSQL("insert into " + DBHelper.TABLE_NAME + "(sNum, sName, sPW, sMajor, sGrade) " + " values " + "( 20221234, '강아지', '1111', '컴퓨터공학과', 1 )");
    }

    private void searchRecord(int num, String pw){
        Cursor cursor = db.rawQuery("select * from " + DBHelper.TABLE_NAME + " where sNum = " + num + " AND sPW = \"" + pw + "\"", null);
        //Cursor cursor = db.rawQuery("select * from student", null);
        for(int i = 0; i < cursor.getCount(); i++){
            cursor.moveToNext();
            int getNum = cursor.getInt(0);
            String getName = cursor.getString(1);
            String getPW = cursor.getString(2);
            String getMajor = cursor.getString(3);
            int getGrade = cursor.getInt(4);

            if(getNum == num && getPW.equals(pw)){
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("num", getNum);
                intent.putExtra("name", getName);
                intent.putExtra("major", getMajor);
                intent.putExtra("grade", getGrade);
                startActivity(intent);
                break;
            }
        }
        cursor.close();
    }
}

