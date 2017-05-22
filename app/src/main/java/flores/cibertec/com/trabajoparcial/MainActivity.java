package flores.cibertec.com.trabajoparcial;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Calendar;

import flores.cibertec.com.trabajoparcial.DB.TaskContract;
import flores.cibertec.com.trabajoparcial.MODEL.Task;

public class MainActivity extends AppCompatActivity
implements ListFragment.OnTaskClickListener{


    private boolean isDualPane = false;
    Task task,modifiedTask;
    private Button btnNew,btnUpdate,btnDelete,btnRefrescar;
    private String option="new";
    private static final String TAG = "MainActivity";
    private MyReceiver myReceiver;
    private ArrayList<Task> nuevasTareas;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isDualPane = getResources().getBoolean(R.bool.dual_pane);

        btnNew = (Button) findViewById(R.id.btnNew);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnRefrescar = (Button) findViewById(R.id.btnRefrescar);

        myReceiver = new MyReceiver();



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

        btnRefrescar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(),MyIntentService.class);
                    startService(intent);
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

        super.onStart();

        IntentFilter filter = new IntentFilter();
        filter.addAction(MyIntentService.ACTION_TASK_STARTED);
        filter.addAction(MyIntentService.ACTION_TASK_FINISHED);

        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver, filter);

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

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver);
    }

    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (TextUtils.equals(action, MyIntentService.ACTION_TASK_STARTED)) {
                // Task started
                Log.d(TAG, "onReceive: task stared");
                nuevasTareas=intent.getParcelableArrayListExtra("LISTA");
                Intent intent1 = new Intent(getBaseContext(),ListFragment.class);
                intent1.putParcelableArrayListExtra("TareasAgregar",nuevasTareas);
                intent1.putExtra("nuevaLista","nuevaLista");



            } else if (TextUtils.equals(action, MyIntentService.ACTION_TASK_FINISHED)) {
                // Task finished
                Log.d(TAG, "onReceive: task finished");

            }
        }
    }








}
