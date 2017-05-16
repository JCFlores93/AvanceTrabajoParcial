package flores.cibertec.com.trabajoparcial;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

/**
 * Created by JeanCarlo on 08/05/2017.
 */

public class UpdateTask extends Fragment implements View.OnClickListener{

    public static final String ARG_TASK= "task";
    private static final String TAG = "UpdateTask";

    private Task task;
    EditText edtTitulo,edtFecha;
    private List<Task> AddedTasks;
    private String taskToModify;
    ListFragment fragment;

    TaskAdapter taskAdapter ;


    public static UpdateTask newInstance(Task task){
        UpdateTask tf = new UpdateTask();
        Bundle args = new Bundle();
        args.putParcelable(ARG_TASK,task);
        tf.setArguments(args);
        return tf;
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
        return inflater.inflate(R.layout.updatefragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtTitulo = (EditText) view.findViewById(R.id.edtTitulo);
        edtFecha = (EditText) view.findViewById(R.id.edtFecha);
        EditText edtEstado = (EditText) view.findViewById(R.id.edtEstado);
        Button btnSave = (Button) view.findViewById(R.id.btnSave);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
        taskToModify = task.getTaskTitle();
        edtTitulo.setText(taskToModify);
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String day = sdf.format(task.getDay());
        edtFecha.setText(day);
        edtEstado.setText("Activado");

        btnCancel.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnSave :
                update(getContentValues(),taskToModify);
                break;

            case R.id.btnDelete :
                delete(getContentValues(),edtTitulo.getText().toString());
                break; 
            case R.id.btnCancel :
                FragmentTransaction fragmentManager = getFragmentManager().beginTransaction();
                fragmentManager.replace(contenedor,TaskFragment.newInstance(getTaskList().get(0)));
                fragmentManager.commit();
                break;
        }
    }

    private void update(ContentValues contentValues,String titulo) {
        ContentResolver cr = getActivity().getContentResolver();
        long id = busqueda(getTaskList(),titulo);
        int uri = cr.update(TaskContract.Task.CONTENT_URI,contentValues,TaskContract.Task._ID + "=" + id +"",null);
        if (uri != 0) {
            Log.i(TAG, "Updated row id: " + id);
        } else {
            Log.i(TAG, "Uri is null");
        }
    }

    private void delete(ContentValues values,String nombre){
        ContentResolver cr = getActivity().getContentResolver();
        long id =  busqueda(getTaskList(),nombre);
        int uri = cr.delete(TaskContract.Task.CONTENT_URI,TaskContract.Task._ID + " = "+ id +" ",null);
        if (uri != 0) {
            Log.i(TAG, "Deleted row id: " + id);
        } else {
            Log.i(TAG, "Name is null");
        }
    }

    private long busqueda(List<Task> lista,String nombre) {
        long idEncontrado=0;
        for (Task task: lista) {
            if(task.getTaskTitle().trim().equals(nombre)) {
                idEncontrado=task.getId();
                break;
            }else{
                Log.i(TAG, "ERROR NO SE ENCUENTRA EL TITULO");
            }
        }
        return idEncontrado;
    }

    private List<Task> getTaskList() {
        Cursor cursor = query();
        List<Task> contactList = new ArrayList<>();
        if (cursor != null) {
            while(cursor.moveToNext()) {
                Task contact = cursorToTask(cursor);
                contactList.add(contact);
            }
            cursor.close();
        }
        return contactList;
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

    private Task cursorToTask(Cursor cursor) {
        Task task = new Task();
        task.setId(cursor.getLong(0));
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

    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(TaskContract.Task.COLUMN_NAME_TITLE, edtTitulo.getText().toString());
        values.put(TaskContract.Task.COLUMN_NAME_DATE, edtFecha.getText().toString());
        return values;
    }
}
