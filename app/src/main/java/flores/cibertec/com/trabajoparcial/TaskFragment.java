package flores.cibertec.com.trabajoparcial;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.text.SimpleDateFormat;

import flores.cibertec.com.trabajoparcial.MODEL.Task;

/**
 * Created by JeanCarlo on 05/05/2017.
 */

public class TaskFragment extends Fragment{

    public static final String ARG_TASK= "task";

    private Task task;

    public static TaskFragment newInstance(Task task){
        TaskFragment tf = new TaskFragment();
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
        return inflater.inflate(R.layout.fragament_task, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText edtTitulo = (EditText) view.findViewById(R.id.edtTitulo);
        EditText edtFecha = (EditText) view.findViewById(R.id.edtFecha);
        EditText edtEstado = (EditText) view.findViewById(R.id.edtEstado);

        if (task != null){

            edtTitulo.setText(task.getTaskTitle());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String probandoDia = task.getDay().toString();
            String day = dateFormat.format(task.getDay());
            edtFecha.setText(probandoDia);
            edtEstado.setText("Activado");
        }else{
            edtTitulo.setText("Tarea 1");
            edtFecha.setText("2017-05-04 17:10:55");
            edtEstado.setText("Activado");
        }


    }

}
