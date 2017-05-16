package flores.cibertec.com.trabajoparcial;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import flores.cibertec.com.trabajoparcial.MODEL.Task;

/**
 * Created by JeanCarlo on 04/05/2017.
 */

public class TaskActivity extends AppCompatActivity{

    public static final String EXTRA_TASK= "task";
    public static final String EXTRA_OPTION = "OPTION";//"new","UPDATE";
    public static final String EXTRA_UPDATE ="update";

    private Task task;
    private String option;
    private FragmentTransaction ft;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null){
            option = getIntent().getStringExtra(EXTRA_OPTION);
            task = getIntent().getParcelableExtra(EXTRA_TASK);

            switch (option){
                case "new" :
                     ft = getSupportFragmentManager().beginTransaction();
                    ft.add(android.R.id.content,TaskNew.newInstance(task));
                    ft.commit();
                    break;
                case "update" :
                    ft= getSupportFragmentManager().beginTransaction();
                    ft.add(android.R.id.content,UpdateTask.newInstance(task));
                    ft.commit();
                    break;
            }
    }else{
            task = getIntent().getParcelableExtra(EXTRA_TASK);
            FragmentTransaction ft= getSupportFragmentManager().beginTransaction();
            ft.add(android.R.id.content,TaskFragment.newInstance(task));
            ft.commit();
        }

    }
}
