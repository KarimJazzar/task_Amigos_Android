package com.example.task_amigos_android;

import java.util.ArrayList;
import java.util.Date;

public class TaskModel {
    private int id;
    private String name;
    private String description;
    private String category;
    private boolean status;
    private ArrayList<TaskModel> subTaskModels;
    private ArrayList<String> images;
    private ArrayList<String> audios;
    private Date dueDate;
    private Date createdDate;
    private  boolean isSubtask;

    public TaskModel(int id, String name, String description, String category, boolean status, ArrayList<TaskModel> subTaskModels, ArrayList<String> images, ArrayList<String> audios, Date dueDate, Date createdDate, boolean isSubtask) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.status = status;
        this.subTaskModels = subTaskModels;
        this.images = images;
        this.audios = audios;
        this.dueDate = dueDate;
        this.createdDate = createdDate;
        this.isSubtask = isSubtask;
    }
    
    //getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<TaskModel> getSubTasks() {
        return subTaskModels;
    }

    public void setSubTasks(ArrayList<TaskModel> subTaskModels) {
        this.subTaskModels = subTaskModels;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public ArrayList<String> getAudios() {
        return audios;
    }

    public void setAudios(ArrayList<String> audios) {
        this.audios = audios;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isSubtask() {
        return isSubtask;
    }

    public void setSubtask(boolean subtask) {
        isSubtask = subtask;
    }
}
