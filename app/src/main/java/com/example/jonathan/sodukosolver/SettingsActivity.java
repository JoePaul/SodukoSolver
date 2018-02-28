package com.example.jonathan.sodukosolver;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;


import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;

import static com.example.jonathan.sodukosolver.Main.PREF;

/**
 * The settings activity that creates a new app view.
 * Extends AppCompatActivity.
 */

public class SettingsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ColorPicker colorPicker;


    /**
     * When this activity is called upon, onCreate sets the content and and the creates a colorpicker.
     * @param saved
     */
    public void onCreate(Bundle saved) {
        super.onCreate(saved);
        setContentView(R.layout.settings_layout);
        toolbar = findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbar);
        if (toolbar == null) {
            throw new Error("Can't find tool bar, did you forget to add it in Activity layout file?");
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ColorDrawable cd = (ColorDrawable) toolbar.getBackground();
        SharedPreferences sharedPreferences = getSharedPreferences(PREF, Context.MODE_PRIVATE);
        int start = sharedPreferences.getInt("Color",cd.getColor());
        int Red = (start >> 16) & 0xFF;
        int Green = (start >> 8) & 0xFF;
        int Blue = start & 0xFF;
        toolbar.setBackgroundColor(start);
        colorPicker = new ColorPicker(this, Red, Green, Blue);
    }

    /**
     * Checks if the backbutton is pressed.
     * @return Returns a boolean. True if back is pressed, false if not.
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /**
     * Sets the color of the toolbar with the help of a colorpicker.
     * @param view Not used
     */
    public void setColorPicker(View view){
        colorPicker.show();
        colorPicker.setCallback(new ColorPickerCallback() {
            @Override
            public void onColorChosen(int color) {
                SharedPreferences sp = getSharedPreferences(PREF, Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putInt("Color", colorPicker.getColor());
                edit.commit();
                toolbar.setBackgroundColor(colorPicker.getColor());
                colorPicker.dismiss();
            }
        });
    }


}

