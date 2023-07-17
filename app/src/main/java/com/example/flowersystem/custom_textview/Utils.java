package com.example.flowersystem.custom_textview;

import android.content.Context;
import android.graphics.Typeface;

public class Utils {
    private static Typeface alluraTF;
    private static Typeface greatvibesTF;
    private static Typeface quicksandTF;
    private static Typeface lobsterTF;
    private static Typeface carenyTF;

    public static Typeface getAlluraTF(Context context) {
        if (alluraTF == null) {
            alluraTF = Typeface.createFromAsset(context.getAssets(), "fonts/Allura-Regular.otf");
        }
        return alluraTF;
    }

    public static Typeface getGreatvibesTF(Context context) {
        if (greatvibesTF == null) {
            greatvibesTF = Typeface.createFromAsset(context.getAssets(), "fonts/GreatVibes-Regular.otf");
        }
        return greatvibesTF;
    }

    public static Typeface getQuicksandTF(Context context) {
        if (quicksandTF == null) {
            quicksandTF = Typeface.createFromAsset(context.getAssets(), "fonts/Quicksand-Regular.otf");
        }
        return quicksandTF;
    }

    public static Typeface getLobsterTF(Context context) {
        if (lobsterTF == null) {
            lobsterTF = Typeface.createFromAsset(context.getAssets(), "fonts/LobsterTwo-BoldItalic.otf");
        }
        return lobsterTF;
    }
    public static Typeface getCarenyTF(Context context) {
        if (carenyTF == null) {
            carenyTF = Typeface.createFromAsset(context.getAssets(), "fonts/CarenyRegular-GO27Z.otf");
        }
        return carenyTF;
    }
}
