package com.example.task_amigos_android.helpers;// Created by FebinRukfan on 05-02-2022.

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateHelper {

    public String todaysDate(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd - MMM - yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        return formattedDate;
    }
}
