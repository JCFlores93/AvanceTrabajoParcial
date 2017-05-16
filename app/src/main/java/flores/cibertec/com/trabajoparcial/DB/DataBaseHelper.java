package flores.cibertec.com.trabajoparcial.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by JeanCarlo on 05/05/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper{

    public static final String DB_NAME="tasks.db";
    public static final int DB_VERSION= 1;

    public static final String SQL_CREATE_TASKS =
            "CREATE TABLE "+ TaskContract.Task.TABLE_NAME + " (" +
                    TaskContract.Task._ID + " INTEGER PRIMARY KEY, " +
                    TaskContract.Task.COLUMN_NAME_TITLE + " TEXT , " +
                    TaskContract.Task.COLUMN_NAME_DATE + " TEXT )" ;

    public static final String SQL_DELETE_TASKS =
            "DROP TABLE IF EXISTS " + TaskContract.Task.TABLE_NAME;


    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TASKS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TASKS);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
