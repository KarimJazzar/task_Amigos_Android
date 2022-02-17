package com.example.task_amigos_android.model;// Created by FebinRukfan on 07-02-2022.

public class ImagesModel {
    public ImagesModel(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private String name;
    private String path;

}
