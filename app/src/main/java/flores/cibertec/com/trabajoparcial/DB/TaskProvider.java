package flores.cibertec.com.trabajoparcial.DB;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by JeanCarlo on 05/05/2017.
 */

public class TaskProvider extends ContentProvider {

    public static final int TASKS = 1;
    public static final int TASK_ID = 2;

    public static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(TaskContract.CONTENT_AUTHORITY, TaskContract.PATH_TASK, TASKS);
        uriMatcher.addURI(TaskContract.CONTENT_AUTHORITY, TaskContract.PATH_TASK+"/#", TASK_ID);
    }

    private DataBaseHelper dataBaseHelper;

    @Override
    public boolean onCreate() {
        dataBaseHelper = new DataBaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        final SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        switch (uriMatcher.match(uri)) {
            case TASKS:
                if (TextUtils.isEmpty(sortOrder)){
                    sortOrder = TaskContract.Task.COLUMN_NAME_TITLE + " ASC";
                }
                    break;

            case TASK_ID:
                if (TextUtils.isEmpty(selection)){
                    selection = TaskContract.Task._ID  + " = " + uri.getLastPathSegment();
                }else {
                    selection += " AND " + TaskContract.Task._ID + " = " + uri.getLastPathSegment();
                }
                break;

            default:
                throw new UnsupportedOperationException("Unknow uri :"+ uri);
        }

        Cursor cursor = db.query(TaskContract.Task.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)){
            case TASKS :
                return TaskContract.Task.CONTENT_TYPE;
            case TASK_ID:
                return TaskContract.Task.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknow uri :"+ uri);
        }
           }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = dataBaseHelper.getWritableDatabase();
        final long id;
        switch (uriMatcher.match(uri)) {
            case TASKS:
                id = db.insert(TaskContract.Task.TABLE_NAME,null,values);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int contador=0;
        final SQLiteDatabase db= dataBaseHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case TASK_ID :
                contador = db.delete(TaskContract.Task.TABLE_NAME,selection, selectionArgs);
                break;
            case TASKS:
                contador = db.delete(TaskContract.Task.TABLE_NAME,selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        return contador;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int contador=0;
        final SQLiteDatabase db= dataBaseHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case TASK_ID :
                contador = db.update(TaskContract.Task.TABLE_NAME,values,selection,selectionArgs);
                break;
            case TASKS:
                contador = db.update(TaskContract.Task.TABLE_NAME,values,selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        return contador;
    }
}
