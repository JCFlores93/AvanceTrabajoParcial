package flores.cibertec.com.trabajoparcial;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

import flores.cibertec.com.trabajoparcial.MODEL.Task;

public class MainActivity extends AppCompatActivity
implements ListFragment.OnTaskClickListener{

    private boolean isDualPane = false;
    Task task,modifiedTask;
    private Button btnNew,btnUpdate,btnDelete;
    private String option="new";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isDualPane = getResources().getBoolean(R.bool.dual_pane);

        btnNew = (Button) findViewById(R.id.btnNew);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);


        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDualPane){
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.contenedor,TaskNew.newInstance(null));
                    ft.commit();
                }else{
                    option = "new";
                    Intent intent = new Intent(getBaseContext(),TaskActivity.class);
                    intent.putExtra(TaskActivity.EXTRA_TASK, task);
                    intent.putExtra(TaskActivity.EXTRA_OPTION,option);
                    startActivity(intent);
                }

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDualPane){
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.contenedor,UpdateTask.newInstance(modifiedTask));
                    ft.commit();
                }else{
                    option = "update";
                    Intent intent = new Intent(getBaseContext(),TaskActivity.class);
                    intent.putExtra(TaskActivity.EXTRA_TASK, task);
                    intent.putExtra(TaskActivity.EXTRA_OPTION,option);
                    startActivity(intent);
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onTaskClick(Task task) {
       if (isDualPane){
            this.task= task;
           this.modifiedTask = task;
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.contenedor, TaskFragment.newInstance(task));
            ft.commit();
        }else{
            Intent intent = new Intent(this, TaskActivity.class);
            intent.putExtra(TaskActivity.EXTRA_TASK, task);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {

        super.onStart();
        if (isDualPane)
        {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Task task = new Task();
            task.setTaskTitle("Contact "+0);
            Calendar calendar = Calendar.getInstance();
            task.setDay(calendar.getTime());
            TaskFragment fragmentDemo = TaskFragment.newInstance(task);
            ft.replace(R.id.contenedor,fragmentDemo);
            ft.commit();
        }else{
            Intent intent = new Intent(this, TaskActivity.class);
            intent.putExtra(TaskActivity.EXTRA_TASK, task);
            startActivity(intent);
        }

    }
}
