package flores.cibertec.com.trabajoparcial;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import flores.cibertec.com.trabajoparcial.DB.TaskContract;
import flores.cibertec.com.trabajoparcial.MODEL.Task;

/**
 * Created by JeanCarlo on 04/05/2017.
 */

public class ListFragment extends Fragment implements TaskAdapter.OnItemClickListener {

    private OnTaskClickListener mListener;
    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private  List<Task> listaTareas ;

    public ListFragment(){
        //It's required by Android in its creation
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        listaTareas =getTaskList();
        taskAdapter = new TaskAdapter(listaTareas,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(taskAdapter);
        //recyclerView.swapAdapter();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTaskClickListener){
                mListener = (OnTaskClickListener) context;
        }else{
            throw new RuntimeException(context.toString()
                    +"must implement OnItemClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener=null;
    }



    public List<Task> getTaskList() {
        Cursor cursor = query();
        List<Task> taskList = new ArrayList<>();
        if (cursor != null){
            while(cursor.moveToNext()){
                Task task = cursorToTask(cursor);
                taskList.add(task);
            }
            cursor.close();
        }
        return  taskList;
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

    public interface OnTaskClickListener{
        void onTaskClick(Task tarea);
    }



    @Override
    public void onItemClick(Task tarea) {
        mListener.onTaskClick(tarea);
    }

}
