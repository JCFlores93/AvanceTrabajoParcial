package flores.cibertec.com.trabajoparcial.MODEL;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by JeanCarlo on 04/05/2017.
 */

public class Task implements Parcelable{
    private long id;
    private String taskTitle;
    private Date day ;


    public Task(){

    }

    protected Task(Parcel in){
        id=in.readLong();
        taskTitle=in.readString();
        day=(Date)in.readSerializable();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public String getTaskTitle() {return taskTitle;}
    public void setTaskTitle(String taskTitle) { this.taskTitle = taskTitle;}
    public Date getDay() {
        return day;
    }
    public void setDay(Date day) {
        this.day = day;
    }
    public long getId() {return id;}
    public void setId(long id) {this.id = id;}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(taskTitle);
        parcel.writeSerializable(day);
    }


   /*
    public String toString (){
        //TODO : ver lo de conversión de datos
    }*/
}
