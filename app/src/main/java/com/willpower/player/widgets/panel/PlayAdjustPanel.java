/**
 * Android Jungle-MediaPlayer framework project.
 *
 * Copyright 2016 Arno Zhang <zyfgood12@163.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.willpower.player.widgets.panel;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.willpower.player.R;
import com.willpower.player.base.MediaPlayerUtils;
import com.willpower.touch.image.AppRoundImageButton;

/**
 * 播放暂停
 */
public class PlayAdjustPanel extends FrameLayout {

    public PlayAdjustPanel(Context context) {
        super(context);
        initLayout(context);
    }

    public PlayAdjustPanel(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout(context);
    }

    public PlayAdjustPanel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context);
    }

    private void initLayout(Context context) {
        View.inflate(context, R.layout.layout_play_adjust_panel, this);
    }
    public void showPanel() {
        enterAnim();
    }

    public void hidePanel() {
        exitAnim();
    }

    private void enterAnim(){
        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(this,"scaleX",0,1);
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(this,"scaleY",0,1);
        AnimatorSet set = new AnimatorSet();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        set.playTogether(objectAnimatorX,objectAnimatorY);
        set.start();
    }
    private void exitAnim(){
        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(this,"scaleX",1,0);
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(this,"scaleY",1,0);
        AnimatorSet set = new AnimatorSet();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        set.playTogether(objectAnimatorX,objectAnimatorY);
        set.start();
    }
}
