package com.mlm.gradebar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.mlm.gradebar.util.DipPxUtils;


/**
 * Created by zyl & xw ,  Android project Engineer From MLM.
 * Data : 2014-12-22  15:51
 * Describe:
 * Version : 1.0
 * Open Source ：GradeBar
 */
public class GradeBar extends View {


    private List<GradeDivider> listDivider = new ArrayList<GradeDivider>();

    private static final String V = "V";

    private static final int V_TOP_DP = 4;

    private static final int V_TEXT_SIZE = 12;// V-Text

    private float v_text_size = 0;

    private static final int V_SMALL_TEXT_SIZE = 6;

    private float v_small_text_size = 0;

    private float v_total_size = 0;

    private static final int CHOOSE_TEXT_COLOR = Color.parseColor("#ffff6a99");

    private static final int MISS_TEXT_COLOR = Color.parseColor("#ffd9d8d8");

    private static final int V_BOTTOM_DP = 10;

    private static final int RATE_HEIGHT_DP = 12;

    private static final int NAME_TOP_DP = 15;

    private static final int NAME_TEXT_SIZE = 12;// Name-Text

    private float name_text_size = 0;

    private float name_top = 0;

    private float v_top = 0;

    private float v_num_top = 0;

    private float rate_top = 0;

    private float rate_height = 0;

    private static final int BACKGROUND_BAR_ID = R.drawable.grade_bar_background;

    private static final int COVER_BAR_ID = R.drawable.grade_bar_cover;

    private Paint paint = new Paint();

    private Drawable drawBackgroundBar;

    private Drawable drawCoverBar;

    private int grade = 0;

    private float width_per_grade = 0;

    private int padding = 0;

    private boolean bAvg = true; // average weight

    public GradeBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public GradeBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GradeBar(Context context) {
        super(context);
        init();
    }

    private void init() {
        paint.setAntiAlias(true);

        drawBackgroundBar = getContext().getResources().getDrawable(
                BACKGROUND_BAR_ID);
        drawCoverBar = getContext().getResources().getDrawable(COVER_BAR_ID);

        v_text_size = DipPxUtils.dip2px(getContext(), V_TEXT_SIZE);
        v_small_text_size = DipPxUtils.dip2px(getContext(), V_SMALL_TEXT_SIZE);
        rate_top = DipPxUtils.dip2px(getContext(), V_TOP_DP + V_TEXT_SIZE
                + V_BOTTOM_DP);
        rate_height = DipPxUtils.dip2px(getContext(), RATE_HEIGHT_DP);
        v_total_size = v_text_size + v_small_text_size;
        name_text_size = DipPxUtils.dip2px(getContext(), NAME_TEXT_SIZE);
        v_top = DipPxUtils.dip2px(getContext(), V_TOP_DP);
        v_num_top = DipPxUtils.dip2px(getContext(), V_TOP_DP + V_TEXT_SIZE
                - V_SMALL_TEXT_SIZE);
        name_top = DipPxUtils.dip2px(getContext(), V_TOP_DP + V_TEXT_SIZE
                + V_BOTTOM_DP + RATE_HEIGHT_DP + NAME_TOP_DP);

    }

    /**
     * Input grade
     *
     * @param grade
     */
    public void setGrade(int grade) {
        this.grade = grade;
        invalidate();
    }

    /**
     * Add the line
     *
     * @param list
     */
    public void setDividerList(List<GradeDivider> list, boolean bAverageWeight) {
        this.bAvg = bAverageWeight;
        listDivider.clear();
        listDivider.addAll(list);
    }

    /**
     * Add line  from the map
     *
     * @param map
     * @param bAverageWeight
     */
    public void setDividerList(Map<Integer, String> map, boolean bAverageWeight) {
        this.bAvg = bAverageWeight;
        listDivider.clear();
        List<GradeDivider> list = new ArrayList<GradeBar.GradeDivider>();
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            list.add(new GradeDivider(entry.getKey(), entry.getValue()));
        }
        Collections.sort(list, new Comparator<GradeDivider>() {

            @Override
            public int compare(GradeDivider arg0, GradeDivider arg1) {
                if (arg0.score > arg1.score)
                    return 1;
                if (arg0.score < arg1.score)
                    return -1;
                return 0;
            }
        });
        listDivider.addAll(list);
    }

    /**
     * Draw  background  bar
     *
     * @param canvas
     * @param left
     * @param right
     */
    private void drawBackgroundBar(Canvas canvas, float left, float right) {
        drawBar(canvas, drawBackgroundBar, left, right);
    }

    private void drawCoverBar(Canvas canvas, float left, float right) {
        drawBar(canvas, drawCoverBar, left, right);
    }

    /**
     * 根据grade绘制覆盖条
     *
     * @param canvas
     */
    private void drawCoverBar(Canvas canvas) {
        int grade = 0;
        if (this.grade > listDivider.get(listDivider.size() - 1).score) {
            grade = listDivider.get(listDivider.size() - 1).score;
        } else {
            grade = this.grade;
        }
        float right = getCoverBarRight(grade);
        drawCoverBar(canvas, padding, right);
    }

    /**
     * 获取相应的覆盖条右端
     *
     * @param grade
     * @return
     */
    private float getCoverBarRight(int grade) {

        float right = padding + rate_height;

        if (!bAvg) {
            right += width_per_grade * grade;
        } else {
            int i = 0;
            GradeDivider gd = null;

            int size = listDivider.size();

            for (i = 0; i < listDivider.size(); i++) {
                gd = listDivider.get(i);
                if (grade < gd.score) {
                    right += (i - 1) * width_per_grade;
                    break;
                }
            }

            float per_width = 1f * width_per_grade
                    / (gd.score - listDivider.get(i - 1).score);
            right += per_width * (grade - listDivider.get(i - 1).score);

        }
        return right;
    }

    private void drawBar(Canvas canvas, Drawable draw, float left, float right) {
        draw.setBounds((int) left, (int) rate_top, (int) right,
                (int) (rate_top + rate_height));
        draw.draw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
                (int) getTotalHeight());
    }

    /**
     * Return the total height
     *
     * @return
     */
    private float getTotalHeight() {
        return DipPxUtils.dip2px(getContext(), V_TOP_DP + V_TEXT_SIZE
                + V_BOTTOM_DP + RATE_HEIGHT_DP + NAME_TOP_DP + NAME_TEXT_SIZE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (listDivider.size() == 0) {
            return;
        }
        // From right to left to draw
        measurePadding();
        int now_right = getMeasuredWidth() - padding;
        measurePerWidth();
        int size = listDivider.size();
        for (int i = size - 1; i >= 1; i--) {
            now_right = (int) drawOtherItem(canvas, i, now_right);
        }
        drawBothTextByCenter(canvas, paint, 0, padding + rate_height / 2);
        drawCoverBar(canvas);
    }

    /**
     * 计算左右两边的需要空缺
     */
    private void measurePadding() {
        paint.setTextSize(name_text_size);
        int left_size = (int) paint.measureText(listDivider.get(0).below_name);
        int right_size = (int) paint.measureText(listDivider.get(listDivider
                .size() - 1).below_name);
        padding = Math.max(left_size, right_size);
    }

    /**
     * 绘制背景和文字，返回下一个起点
     *
     * @param canvas
     * @param position
     * @param now_right
     * @return
     */
    private float drawOtherItem(Canvas canvas, int position, int now_right) {
        return drawItemAndTexts(canvas, position, now_right);
    }

    /**
     * 绘制背景和文字，返回下一个起点
     *
     * @param canvas
     * @param position
     * @param now_right
     * @return
     */
    private float drawItemAndTexts(Canvas canvas, int position, int now_right) {
        float left_center = 0;
        if (!bAvg) {
            left_center = listDivider.get(position - 1).score * width_per_grade
                    + rate_height / 2 + padding;
        } else {
            left_center = (position - 1) * width_per_grade + rate_height / 2
                    + padding;
        }
        drawBackgroundBar(canvas, left_center - rate_height / 2, now_right);
        drawBothTextByCenter(canvas, paint, position, now_right);
        return left_center + rate_height / 2;
    }

    /**
     * 计算单位分数对应的长度
     */
    private void measurePerWidth() {
        if (!bAvg) {
            width_per_grade = 1f
                    * (getMeasuredWidth() - rate_height - padding * 2)
                    / listDivider.get(listDivider.size() - 1).score;
        } else {
            width_per_grade = 1f
                    * (getMeasuredWidth() - rate_height - padding * 2)
                    / (listDivider.size() - 1);
        }
    }

    /**
     * 绘制V和其相应的名字 中心为准
     *
     * @param canvas
     * @param paint
     * @param position
     * @param center
     */
    private void drawBothTextByCenter(Canvas canvas, Paint paint, int position,
                                      float center) {
        paint.setTextSize(name_text_size);
        float left = center
                - paint.measureText(listDivider.get(position).below_name) / 2;
        drawBothText(canvas, paint, position, (int) left);
    }

    /**
     * 绘制V和其相应的名字 左边缘
     *
     * @param canvas
     * @param paint
     * @param position
     * @param left
     */
    private void drawBothText(Canvas canvas, Paint paint, int position, int left) {
        paint.setColor(getColor(listDivider.get(position).score));
        float below_length = drawBelowText(canvas, paint, position, left);
        float v_left = left + getVLeft(below_length);
        drawVText(canvas, paint, position, v_left);
    }

    /**
     * draw level  text
     * @param canvas
     * @param paint
     * @param position
     * @param left
     */
    private void drawVText(Canvas canvas, Paint paint, int position, float left) {
        paint.setTextSize(v_text_size);
        canvas.drawText(V, left, getTextY(paint, v_top), paint);
        left += paint.measureText(V);
        paint.setTextSize(v_small_text_size);
        canvas.drawText(String.valueOf(position), left,
                getTextY(paint, v_num_top), paint);
    }

    /**
     * 获取相应V文字的左边缘
     *
     * @param below_length
     * @return
     */
    private float getVLeft(float below_length) {
        return (below_length - v_total_size) / 2;
    }

    /**
     * 画出下方的文字并且返回该文字总长度
     *
     * @param canvas
     * @param paint
     * @param position
     * @param left
     * @return
     */
    private float drawBelowText(Canvas canvas, Paint paint, int position,
                                int left) {
        String name = listDivider.get(position).below_name;
        paint.setTextSize(name_text_size);
        canvas.drawText(name, left, getTextY(paint, name_top), paint);
        return paint.measureText(name);
    }

    /**
     * 获取drawText的Y坐标
     *
     * @param paint
     * @param text_center_y
     * @return
     */
    private float getTextY(Paint paint, float text_center_y) {
        FontMetrics fm = paint.getFontMetrics();
        float text_height = (float) (Math.ceil(fm.descent - fm.top) + 2);
        return text_center_y + text_height / 2 - fm.bottom;
    }

    /**
     * 根据目前grade计算当前颜色
     *
     * @return
     */
    private int getColor(int divider_score) {
        if (grade >= divider_score) {
            return CHOOSE_TEXT_COLOR;
        }
        return MISS_TEXT_COLOR;
    }

    /**
     *  分数分割线的设置
     */
    public static class GradeDivider {
        public int score;
        public String below_name;

        public GradeDivider() {

        }

        public GradeDivider(int score, String below_name) {
            super();
            this.score = score;
            this.below_name = below_name;
        }

    }
}
