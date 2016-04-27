package yzx.study.frame2.errorCollect;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ECManager {

    private static Context context;


    public static void install(Context context) {
        ECManager.context = context.getApplicationContext();
    }


    //----------------------------------------


    public static void put(String title, String desc) {
        if (!openDB())
            return;
        ContentValues cv = new ContentValues();
        cv.put(C_TITLE, title == null ? "无标题" : title);
        cv.put(C_DESC, desc == null ? "无详细说明" : desc);
        cv.put(C_TIME, "" + System.currentTimeMillis());
        db.insert(Table_Name, null, cv);
        closeDB();
    }


    public static void put(String title, Exception e) {
        String desc = getErrorInfo(e);
        put(title == null ? e.toString() : title, desc);
    }


    protected static void delete(int id) {
        if (!openDB())
            return;
        db.delete(Table_Name, "_id = " + id, null);
        closeDB();
    }


    protected static List<ErrorBean> getAll() {
        if (!openDB())
            return null;
        Cursor c = db.rawQuery("select * from " + Table_Name, null);
        if (c == null)
            return null;
        if (c.getCount() < 1) {
            c.close();
            return null;
        }
        ArrayList<ErrorBean> list = new ArrayList<>();
        while (c.moveToNext()) {
            ErrorBean b = new ErrorBean();
            b._id = c.getInt(c.getColumnIndex("_id"));
            b.err_time = c.getString(c.getColumnIndex(C_TIME));
            b.err_desc = c.getString(c.getColumnIndex(C_DESC));
            b.err_title = c.getString(c.getColumnIndex(C_TITLE));
            list.add(b);
        }
        c.close();
        closeDB();
        Collections.reverse(list);
        return list;
    }


    //-------------------------------------------


    private static SQLiteDatabase db;
    private static final String Table_Name = "error_s";
    private static final String C_TITLE = "err_title";
    private static final String C_DESC = "err_desc";
    private static final String C_TIME = "err_time";

    private static boolean openDB() {
        closeDB();
        db = SQLiteDatabase.openOrCreateDatabase(context.getFilesDir().getAbsolutePath() + "/ErrCollec.db", null);
        if (db == null)
            return false;
        db.execSQL("create table if not exists " + Table_Name +
                " (_id integer primary key autoincrement , " + C_TITLE + " text , " + C_DESC + " text , " + C_TIME + " text) ");
        return true;
    }

    private static void closeDB() {
        if (db == null)
            return;
        db.close();
        db = null;
    }


    //-------------------------------------------


    public static class ErrorBean {
        public int _id;
        public String err_title;
        public String err_desc;
        public String err_time;

        public String getErrTimeFormat() {
            if (err_time == null)
                return "time error";
            try {
                long timeL = Long.parseLong(err_time);
                SimpleDateFormat f = new SimpleDateFormat("yy-MM-dd  HH:mm:ss", Locale.getDefault());
                return f.format(new Date(timeL));
            } catch (Exception e) {
                return "time error";
            }
        }
    }


    //--------------------------------


    public static String getErrorInfo(Throwable arg1) {
        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        arg1.printStackTrace(pw);
        pw.close();
        String error = writer.toString();
        try {
            writer.close();
        } catch (IOException e) {
        }
        return error;
    }

}
