package br.edu.utfpr.alunos.felipe.meetingcalendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Switch;

import com.bentech.android.appcommons.AppCommons;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import org.threeten.bp.LocalDate;


public class MainActivity extends AppCompatActivity {

    private MenuItem aSwitch;
    private MenuItem about;
    private SharedPreferences sharedPreferences;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        AppCommons.install(getApplication());
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        setTheme(getFlag()? R.style.DarkTheme : R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //set default calendar to User
        final MaterialCalendarView materialCalendarView = findViewById(R.id.calendarView);
        materialCalendarView.setSelectedDate(LocalDate.now());

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call second activity
                Intent intent = new Intent(MainActivity.this, Activity_Tabs.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("Date", materialCalendarView.getSelectedDate());
                bundle.putBoolean("Theme", getFlag());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    public void setFlag(boolean flag) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("dark", flag);
        editor.commit();
    }

    public boolean getFlag() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getBoolean("dark", false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        about = menu.findItem(R.id.action_settings);
        about.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(MainActivity.this, Activity_About.class);
                Bundle bundle = new Bundle();
                bundle.putBoolean("Theme", getFlag());
                intent.putExtras(bundle);
                startActivity(intent);

                return false;
            }
        });


        aSwitch = menu.findItem(R.id.swith_dark_mode);
        aSwitch.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                setFlag(!getFlag());

                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
