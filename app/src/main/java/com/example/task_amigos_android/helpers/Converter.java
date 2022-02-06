package com.example.task_amigos_android.helpers;

import android.text.TextUtils;
import androidx.room.TypeConverter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Converter {
    @TypeConverter
    public static Date timestampToDate(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static List<String> strongToList(String value) {
        return  value == null ? null : Arrays.asList(value.split(","));
    }

    @TypeConverter
    public static String listToString(List<String> list) {
        return list == null ? null : TextUtils.join(",", list);
    }
}

