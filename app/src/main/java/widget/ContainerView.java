/*
 * Copyright 2015 LuMeng
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package widget;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import presenter.impl.HorizontalScrollViewAdapter;

/**
 * @author lumeng on 16/1/4.
 */
public class ContainerView extends FrameLayout {

    private static final Interpolator INTERPOLATOR = new DecelerateInterpolator();

    public static final int PREVIEW = 0;
    public static final int AFTERVIEW = 1;

    private Context context;

    private AttributeSet attributeSet;
    private int r;

    private int b;

    public ContainerView(Context context) {
        this(context, null);
    }

    public ContainerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ContainerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        this.attributeSet = attrs;
        initChild();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        getChildAt(0).onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public void invalidate() {
        Rebound scroll = (Rebound) getChildAt(0);
        scroll.invalidate();
        super.invalidate();
    }

    /**
     * Init view
     */
    private void initChild() {
        Rebound scrollView = new Rebound(context, attributeSet);
        LinearLayout linearLayout = new LinearLayout(context, attributeSet);

        LayoutParams linearParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        ViewGroup.LayoutParams scrollParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        scrollView.addView(linearLayout, linearParams);
        scrollView.initDatas(new HorizontalScrollViewAdapter());
        scrollView.setHorizontalScrollBarEnabled(false);

        addView(scrollView, scrollParams);
    }

    /**
     * Add a new {@link ReboundHorizontalScrollView}
     */
    public void addRebound(int status) {
        Rebound scrollView = new Rebound(context, attributeSet);
        LinearLayout linearLayout = new LinearLayout(context, attributeSet);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        ViewGroup.LayoutParams scrollParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        scrollView.addView(linearLayout, layoutParams);
        scrollView.initDatas(new HorizontalScrollViewAdapter());
        scrollView.setHorizontalScrollBarEnabled(false);

        addView(scrollView, scrollParams);

        if (status == AFTERVIEW) {
            scrollView.layout(r, getChildAt(0).getTop(), r * 2, b);
        } else if (status == PREVIEW) {
            scrollView.layout(-r, getChildAt(0).getTop(), 0, b);
        }

        translate(status);
    }

    /**
     * Do translate animation
     *
     * @param direction which direction that should animate to
     */
    private void translate(int direction) {
        if (getChildCount() == 2) {
            View scroll = getChildAt(0);
            View newScroll = getChildAt(1);

            scroll.setTranslationX(scroll.getX());
            newScroll.setTranslationX(newScroll.getX());

            if (direction == AFTERVIEW) {
                scroll.animate().translationX(-r).setStartDelay(300).setDuration(500).setInterpolator(INTERPOLATOR);
            }
            if (direction == PREVIEW) {
                scroll.animate().translationX(r).setStartDelay(300).setDuration(500).setInterpolator(INTERPOLATOR);
            }

            newScroll.animate().translationX(0).setStartDelay(300).setDuration(500).setInterpolator(INTERPOLATOR).setListener(new animationListener()).start();
        }

        if (getChildCount() > 2) {
            removeViewAt(0);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        this.r = r;
        this.b = b;
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    private class animationListener implements Animator.AnimatorListener {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            if (getChildCount() >= 2) {
                removeViewAt(0);
                notifySubtreeAccessibilityStateChanged(getChildAt(0), ContainerView.this, 1);
            }
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }

}
