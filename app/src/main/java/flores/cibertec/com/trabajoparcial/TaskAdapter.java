package flores.cibertec.com.trabajoparcial;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import flores.cibertec.com.trabajoparcial.MODEL.Task;

/**
 * Created by JeanCarlo on 04/05/2017.
 */

public class TaskAdapter
        extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{

    private List<Task> tareas ;
    private OnItemClickListener listener;
    private int selectedItem=0;

    public TaskAdapter(@NonNull List<Task> tareas ,
                       OnItemClickListener listener) {
            this.tareas=tareas;
            this.listener=listener;
    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).
               inflate(R.layout.task_item,parent,false);
        return new ViewHolder(view,listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(tareas.get(position),position);
    }

    @Override
    public int getItemCount() {
        return tareas.size();
    }


    public void update(ArrayList<Task> addedTasks) {
        tareas.clear();
        tareas.addAll(addedTasks);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

       TextView txtTitulo,txtHora,txtMinute,txtFecha;
       SwitchCompat swtCmpat;
       LinearLayout contenedorLinearLayout;
       OnItemClickListener listener;



        public ViewHolder(View itemView,OnItemClickListener listener) {
            super(itemView);
            this.listener=listener;
            txtTitulo = (TextView)itemView.findViewById(R.id.txtTitulo);
            txtFecha = (TextView)itemView.findViewById(R.id.txtFecha);
            txtHora = (TextView)itemView.findViewById(R.id.txtHora);
            txtMinute =(TextView)itemView.findViewById(R.id.txtMinute);
            contenedorLinearLayout = (LinearLayout)itemView.findViewById(R.id.contenedorLayout);
        }

        public void bind(final Task task,final int position) {
            txtTitulo.setText(String.valueOf(task.getTaskTitle()));
            SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String newDay = date.format(task.getDay());
            //String day = task.getDay();
            txtFecha.setText(newDay);
            /*txtHora.setText(String.valueOf(task.getHour()));
            txtMinute.setText(String.valueOf(task.getMinute()));*/
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener!=null){
                        listener.onItemClick(task);
                        selectedItem = position;
                        notifyDataSetChanged();
                    }
                }
            });

            if (selectedItem == position){
                contenedorLinearLayout.setBackgroundColor(Color.parseColor("#567845"));
            }
            else{
                contenedorLinearLayout.setBackgroundColor(Color.parseColor("#ffffff"));
            }

        }
    }


    public interface OnItemClickListener{
        void onItemClick(Task tarea);
    }



}
