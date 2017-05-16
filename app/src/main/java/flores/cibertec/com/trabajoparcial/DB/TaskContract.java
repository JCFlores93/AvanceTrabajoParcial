package flores.cibertec.com.trabajoparcial.DB;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by JeanCarlo on 05/05/2017.
 */

public class TaskContract {

    public static final  String CONTENT_AUTHORITY ="flores.cibertec.com.trabajoparcial";

    private static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    public static final String PATH_TASK ="task";
    private TaskContract(){

    }

    public static class Task implements BaseColumns{
        //encontramos la palabra 'task'
        public static final String TABLE_NAME ="task";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DATE = "date";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASK).build();
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "/" + PATH_TASK;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + "/" + PATH_TASK;

    }


}
