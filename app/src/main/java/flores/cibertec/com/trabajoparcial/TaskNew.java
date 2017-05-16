package flores.cibertec.com.trabajoparcial;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import flores.cibertec.com.trabajoparcial.DB.TaskContract;
import flores.cibertec.com.trabajoparcial.MODEL.Task;

import static flores.cibertec.com.trabajoparcial.R.id.contenedor;
import static flores.cibertec.com.trabajoparcial.R.id.edtFecha;
import static flores.cibertec.com.trabajoparcial.R.id.edtTitulo;

/**
 * Created by user on 06/05/2017.
 */



public class TaskNew extends Fragment
        implements View.OnClickListener{

    public static final String ARG_TASK= "task";
    private static final String TAG = "TaskNew";

    private Task task;
    EditText edtTitulo,edtFecha;
    private List<Task> AddedTasks;
    ListFragment fragment;

    TaskAdapter taskAdapter ;


    public static TaskNew newInstance(Task task){
        TaskNew tf = new TaskNew();
        Bundle args = new Bundle();
        args.putParcelable(ARG_TASK,task);
        tf.setArguments(args);
        return tf;
    }

    public TaskNew(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            task = getArguments().getParcelable(ARG_TASK);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.newfragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         edtTitulo = (EditText) view.findViewById(R.id.edtTitulo);
         edtFecha = (EditText) view.findViewById(R.id.edtFecha);
        EditText edtEstado = (EditText) view.findViewById(R.id.edtEstado);
        Button btnSave = (Button) view.findViewById(R.id.btnSave);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(this);
        btnSave.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSave :
                insert(getContentValues());
               // showTaskList();

            break;

            case R.id.btnCancel :
                FragmentTransaction fragmentManager = getFragmentManager().beginTransaction();
                fragmentManager.replace(contenedor,TaskFragment.newInstance(getTaskList().get(0)));
                fragmentManager.commit();
                break;

        }
    }

  private void showTaskList() {

/*taskAdapter.update((ArrayList<Task>) getTaskList());*/

   }

    private List<Task> getTaskList(){
        Cursor cursor = query();
        List<Task> tasks = new ArrayList<>();
        if (cursor != null){
            while (cursor.moveToNext()){
                Task task = cursorToTask(cursor);
                tasks.add(task);
            }
            cursor.close();
        }
        return tasks;
    }

    private Task cursorToTask(Cursor cursor) {
        Task task = new Task();
        task.setId(cursor.getLong(0)) ;
        task.setTaskTitle(cursor.getString(1));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            date = dateFormat.parse(cursor.getString(2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        task.setDay(date);

        return task;
    }

    private Cursor query() {
        ContentResolver cr = getContext().getContentResolver();
        String[] projection = new String[]{
                //Columnas de la tabla a recuperar
                TaskContract.Task._ID,
                TaskContract.Task.COLUMN_NAME_TITLE,
                TaskContract.Task.COLUMN_NAME_DATE
        };
        return cr.query(TaskContract.Task.CONTENT_URI,
                projection, null, null, null);
    }

    private void insert(ContentValues values){
       ContentResolver cr = getActivity().getContentResolver();
       Uri uri = cr.insert(TaskContract.Task.CONTENT_URI, values);
       if (uri != null) {
           Log.i(TAG, "Inserted row id: " + uri.getLastPathSegment());
       } else {
           Log.i(TAG, "Uri is null");
       }
   }


    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(TaskContract.Task.COLUMN_NAME_TITLE, edtTitulo.getText().toString());
        values.put(TaskContract.Task.COLUMN_NAME_DATE, edtFecha.getText().toString());
        return values;
    }




}
