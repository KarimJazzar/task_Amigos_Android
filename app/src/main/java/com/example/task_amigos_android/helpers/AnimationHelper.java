package com.example.task_amigos_android.helpers;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

public class AnimationHelper {
    public static void animateX(float x, View view) {
        ObjectAnimator animation = ObjectAnimator.ofFloat(view, "translationX", x);
        animation.setDuration(500);
        animation.start();
    }

    public static void animateAlpha(float alpha, View view) {
        ObjectAnimator animation = ObjectAnimator.ofFloat(view, "alpha", alpha);
        animation.setDuration(500);
        animation.start();
    }
}
