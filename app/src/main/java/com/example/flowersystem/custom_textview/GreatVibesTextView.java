package com.example.flowersystem.custom_textview;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class GreatVibesTextView extends AppCompatTextView {
    public GreatVibesTextView(@NonNull Context context) {
        super(context);
        setFontTextView();
    }

    public GreatVibesTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFontTextView();
    }

    public GreatVibesTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFontTextView();
    }
    private void setFontTextView(){
        Typeface typeface = Utils.getGreatvibesTF(getContext());
        setTypeface(typeface);
    }
}
