package com.example.jonathan.sodukosolver;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;

public class Main extends AppCompatActivity {
    public static final String PREF = "Pref";
    private SudokuAdapter sudokuAdapter;
    private Toolbar toolbar;
    private ProgressBar bar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final GridView gridView = (GridView) findViewById(R.id.Grid);
        gridView.setNumColumns(9);
        sudokuAdapter = new SudokuAdapter();
        gridView.setAdapter(sudokuAdapter);
        sudokuAdapter.onCreate(this);
        gridView.setHorizontalSpacing(2);
        gridView.setVerticalSpacing(2);
        gridView.setVerticalScrollBarEnabled(false);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (toolbar == null) {
            throw new Error("Can't find tool bar, did you forget to add it in Activity layout file?");
        }

        bar = findViewById(R.id.prog_main);
        bar.setVisibility(View.INVISIBLE);
        SharedPreferences sp = getSharedPreferences(PREF,Context.MODE_PRIVATE);

    }

    /**
     *
     * @param menu Which is the option if 
     * @return Returns true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    private void alertMessage(String header, String content){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(content);
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.setTitle(header);
        alert11.show();
    }
    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sp = getSharedPreferences(PREF, Context.MODE_PRIVATE);
        ColorDrawable cd = (ColorDrawable) toolbar.getBackground();
        toolbar.setBackgroundColor(sp.getInt("Color", cd.getColor()));

    }
    @SuppressLint("StaticFieldLeak")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_clear:
                sudokuAdapter.clear();


                return true;

            case R.id.menu_item_solve:
                //if(!sudokuAdapter.solve()){
                  //  alertMessage("The Sudoku could not be solved!");
                //}

                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        bar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    protected Void doInBackground(Void... voids) {
                        sudokuAdapter.solve();
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void avoid) {
                        super.onPostExecute(avoid);
                        bar.setVisibility(View.INVISIBLE);
                        if (!sudokuAdapter.update()) {
                            alertMessage("Not Solved","The Sudoku could not be solved!\n Try with another combination of numbers.");

                        }
                    }

                }.execute();

                return true;
            case R.id.menu_item_tryExample:
                sudokuAdapter.clear();
                sudokuAdapter.fillExample();
                return true;
            case R.id.menu_item_settings:
                Intent intent = new Intent(this,SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_japan:
                sudokuAdapter.clear();
                sudokuAdapter.fillJapan();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }



}
