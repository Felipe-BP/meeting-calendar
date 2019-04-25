package br.edu.utfpr.alunos.felipe.meetingcalendar;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class Add_Meeting extends AppCompatActivity {
    private FloatingActionButton fab;
    private TabLayout tabLayout;
    private ConstraintLayout constraintLayout;
    private TextView txView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__meeting);

        constraintLayout = findViewById(R.id.layout_meeting);
        tabLayout = findViewById(R.id.tabs);
        fab = findViewById(R.id.fabMeeting);
        txView2 = findViewById(R.id.textView2);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabLayout.getTabAt(1).select();
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_forward_black_24dp));
                        constraintLayout.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_done_black_24dp));
                        constraintLayout.setVisibility(View.INVISIBLE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @SuppressLint("ValidFragment")
    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        TextView resultText;

        @SuppressLint("ValidFragment")
        public TimePickerFragment(TextView tx) {
            resultText = tx;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String time = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);
            resultText.setText(time);
        }
    }

    public void showTimePicker(View v) {
        DialogFragment newFragment = new TimePickerFragment(txView2);
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
}
