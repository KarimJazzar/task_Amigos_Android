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

        //AnimationSet replaceAnimation = new AnimationSet(false);
        //replaceAnimation.setFillAfter(true);

        //int animType = TranslateAnimation.ABSOLUTE;
        //float colLeft = incompleteCol.getLeft();
        //float colTop = incompleteCol.getTop();
        //float colWidth = incompleteCol.getMeasuredWidth();
        //int[] location = new int[2];
        //incompleteCol.getLocationInWindow(location);

        //TranslateAnimation trans = new TranslateAnimation(type, fromX, type, toX, type, y, type, y);
        //trans.setDuration(500);

        //replaceAnimation.addAnimation(trans);
        //view.startAnimation(replaceAnimation);
    }
}
