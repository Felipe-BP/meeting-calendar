package br.edu.utfpr.alunos.felipe.meetingcalendar;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.List;

import br.edu.utfpr.alunos.felipe.meetingcalendar.dao.MeetingDao;
import br.edu.utfpr.alunos.felipe.meetingcalendar.database.MeetingDatabase;
import br.edu.utfpr.alunos.felipe.meetingcalendar.model.Meeting;
import br.edu.utfpr.alunos.felipe.meetingcalendar.model.MeetingAndLocal;

public class Activity_List extends AppCompatActivity {

    private ListView listView;
    private FloatingActionButton fab;
    private static ArrayList<MeetingAndLocal> meetingsOfDay;
    private static ArrayAdapter<MeetingAndLocal> meetings;
    private static MeetingAndLocal meetingSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Bundle bundle = getIntent().getExtras();

        setTheme(bundle.getBoolean("Theme") ? R.style.DarkTheme : R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        meetingsOfDay = (ArrayList<MeetingAndLocal>) getMeetingsOf((CalendarDay) bundle.getParcelable("DaySelected"));

        fab = findViewById(R.id.delete_meeting);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = new ConfirmDialogFragment();
                dialog.show(getSupportFragmentManager(), "ConfirmDialogFragment");
            }
        });

        listView = findViewById(R.id.list_meetings);

        meetings = new ArrayAdapter<>(this,
                                            android.R.layout.simple_list_item_1,
                                            meetingsOfDay);

        listView.setAdapter(meetings);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(false);
                meetingSelected = meetings.getItem(position);
                Intent intent = new Intent(Activity_List.this, Activity_Tabs.class);
                intent.putExtra("Meeting", (Parcelable) meetingSelected.getMeeting());
                intent.putExtra("Local", (Parcelable) meetingSelected.getLocal());
                intent.putExtra("Date", CalendarDay.from(meetingSelected.getMeeting().getDate()));
                intent.putExtra("Theme", bundle.getBoolean("Theme"));
                startActivity(intent);
                fab.hide();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                meetingSelected = meetings.getItem(position);
                fab.show();
                return true;
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public List<MeetingAndLocal> getMeetingsOf(CalendarDay day) {
        MeetingDao meetingDao = MeetingDatabase.getDatabase(this).getMeetingDao();
        return meetingDao.getMeetingsOfDay(LocalDate.of(day.getYear(), day.getMonth(), day.getDay()).toString());
    }

    public static class ConfirmDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Build the dialog and set up the button click handlers
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.delete_dialog)
                    .setPositiveButton(R.string.delete_confirm, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Send the positive button event back to the host activity
                            meetings.remove(meetingSelected);
                            MeetingDao meetingDao = MeetingDatabase.getDatabase(MainActivity.getContext()).getMeetingDao();
                            meetingDao.delete(meetingSelected.getMeeting().getId());
                        }
                    })
                    .setNegativeButton(R.string.delete_cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Send the negative button event back to the host activity
                        }
                    });
            return builder.create();
        }
    }

}
