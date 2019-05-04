package br.edu.utfpr.alunos.felipe.meetingcalendar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.bentech.android.appcommons.AppCommons;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static Context mContext;

    private MenuItem aSwitch;
    private MenuItem about;
    private SharedPreferences sharedPreferences;
    private MaterialCalendarView materialCalendarView;
    private static List<Meeting> meetings = new ArrayList<>();
    private FloatingActionButton fab2;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        AppCommons.install(getApplication());
    }

    @Override
    protected void onResume() {
        super.onResume();

        System.out.println("ON RESUME");

        //add decoreted day if have a meeting
        materialCalendarView.addDecorator(new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                return existsMeeting(day);
            }

            @Override
            public void decorate(DayViewFacade view) {
                Object span = new DotSpan(10, 0);
                view.addSpan(span);
            }
        });

        boolean res = existsMeeting(materialCalendarView.getSelectedDate());
        if(res) {
            fab2.show();
        } else {
            fab2.hide();
        }
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        setTheme(getFlag()? R.style.DarkTheme : R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mContext = getApplicationContext();

        fab2 = findViewById(R.id.list_meeting);

        //set default calendar to User
        materialCalendarView = findViewById(R.id.calendarView);
        materialCalendarView.state().edit()
                .setMinimumDate(CalendarDay.from(LocalDate.now()))
                .commit();
        materialCalendarView.setSelectedDate(LocalDate.now());
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                boolean res = existsMeeting(date);
                if(res) {
                    fab2.show();
                } else {
                    fab2.hide();
                }
            }
        });

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

    public void listMeetingsOfDay(View v) {
        Intent intent = new Intent(MainActivity.this, Activity_List.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("DaySelected", materialCalendarView.getSelectedDate());
        bundle.putBoolean("Theme", getFlag());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public static void setMeeting(Meeting meeting) {
        meetings.add(meeting);
    }

    public static ArrayList<Meeting> findMeetingsOfDay(CalendarDay day) {
        ArrayList<Meeting> meetingsOfDay = new ArrayList<>();

        for(Meeting m : meetings) {
            if(CalendarDay.from(m.getDate()).equals(day)){
                meetingsOfDay.add(m);
            }
        }

        return meetingsOfDay;
    }

    public boolean existsMeeting(CalendarDay day) {
        for (Meeting m : meetings) {
            if (day.equals(CalendarDay.from(m.getDate()))) {
                return true;
            }
        }
        return false;
    }

    public static int findIndex(Meeting m) {
        for(int i = 0; i < meetings.size(); i++) {
            if(meetings.get(i).toString().equals(m.toString())){
                return i;
            }
        }
        return -1;
    }

    public static void updateMeeting(Meeting meeting, int index) {
        meetings.set(index, meeting);
    }

    public static void deleteMeeting(Meeting meeting) {
        meetings.remove(meeting);
    }

    public static Context getContext(){
        return mContext;
    }
}
