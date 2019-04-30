package br.edu.utfpr.alunos.felipe.meetingcalendar;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class Activity_About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();

        setTheme(bundle.getBoolean("Theme") ? R.style.DarkTheme : R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__about);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
