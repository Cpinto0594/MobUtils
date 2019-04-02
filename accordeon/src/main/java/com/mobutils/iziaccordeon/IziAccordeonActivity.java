package com.mobutils.iziaccordeon;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.StyleableRes;
import android.util.AttributeSet;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mobutils.carlosp.accordeon.R;

public class IziAccordeonActivity extends ScrollView {

    TextView headerTitle;


    public IziAccordeonActivity(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initComponent(context, attrs, defStyleAttr);
    }

    private void initComponents() {

        this.headerTitle = findViewById(R.id.headerTitle);

    }

    private void initComponent(Context context, AttributeSet attrs, int defStyleAttr) {
        inflate(context, R.layout.izi_accordeon_layout, this);
        int[] sets = {R.attr.headerTitle, R.attr.headerTitleColor, R.attr.headerTitleStyle};
        TypedArray typedArray = context.obtainStyledAttributes(attrs, sets);

        CharSequence headerTitle = typedArray.getText(0);

        @StyleableRes int indexColor = 1;
        int color = typedArray.getColor(indexColor, getResources().getColor(R.color.defaultBlack));
        typedArray.recycle();

        initComponents();
        setHeaderTitleText(headerTitle);
        this.headerTitle.setTextColor(color);

    }

    public CharSequence getHeaderTitleText() {
        return this.headerTitle.getText();
    }

    public void setHeaderTitleText(CharSequence value) {
        this.headerTitle.setText(value);
    }


}
