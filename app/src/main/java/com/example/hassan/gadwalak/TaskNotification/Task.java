package com.example.hassan.gadwalak.TaskNotification;

/**
 * Created by PRO_Abdo on 3/20/2018.
 */

public class Task {
public static String subname;
    private int NumberOfTask ;

    private String taskName ;

    private String taskDetails ;

    private String date ;

    private String time ;

    private int AlarmTask_Id ;

    public static String getSubname() {
        return subname;
    }

    public static void setSubname(String subname) {
        Task.subname = subname;
    }

    public Task (){}

    public Task(int numberOfTask , String taskname , String taskdetails , String date , String time , int alarmTask_Id){

        this.NumberOfTask = numberOfTask ;

        this.taskName = taskname ;

        this.taskDetails = taskdetails ;

        this.date = date ;

        this.time = time ;

        this.AlarmTask_Id = alarmTask_Id ;

    }

    public int getNumberOfTask() {
        return NumberOfTask;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDetails() {
        return taskDetails;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getAlarmTask_Id() {
        return AlarmTask_Id;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskDetails(String taskDetails) {
        this.taskDetails = taskDetails;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
