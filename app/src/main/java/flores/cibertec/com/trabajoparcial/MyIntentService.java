package flores.cibertec.com.trabajoparcial;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import flores.cibertec.com.trabajoparcial.DB.TaskContract;
import flores.cibertec.com.trabajoparcial.MODEL.Task;

import static flores.cibertec.com.trabajoparcial.TaskNew.AddedTasks;

/**
 * Created by JeanCarlo on 21/05/2017.
 */

public class MyIntentService extends IntentService{
    public static final String TAG_LIST = "MyIntentService";
    public static final String ACTION_TASK_STARTED = "action_task_started";
    public static final String ACTION_TASK_FINISHED = "action_task_strated";
    List<Task> lista ;

    public MyIntentService(){
        super(TAG_LIST);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
            Bundle extras = intent.getExtras();
            lista = extras.getParcelableArrayList("LISTA_DE_CONTACTOS");
            Intent startedIntent = new Intent();
            startedIntent.setAction(ACTION_TASK_STARTED);
            startedIntent.putParcelableArrayListExtra("LISTA", (ArrayList<Task>) lista);
            LocalBroadcastManager.getInstance(this).sendBroadcast(startedIntent);
            Log.d(TAG_LIST, "onHandleIntent: start task");
            try{
                Thread.sleep(1000);

            }catch (InterruptedException e){
                e.printStackTrace();
            }

            Intent finishedIntent = new Intent(ACTION_TASK_FINISHED);
            LocalBroadcastManager.getInstance(this).sendBroadcast(finishedIntent);
            Log.d(TAG_LIST, "onHandleIntent: finish task");
        }



}

