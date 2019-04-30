package br.edu.utfpr.alunos.felipe.meetingcalendar;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bentech.android.appcommons.utils.EditTextUtils;
import com.bentech.android.appcommons.validator.EditTextRequiredInputValidator;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.Calendar;

public class Activity_Tabs extends AppCompatActivity implements FragmentMeeting.OnFragmentInteractionListener, FragmentLocal.OnFragmentInteractionListener {

    private TextView txView2;
    private TextView txView3;
    private TextView txViewDate;
    private EditText editTextNameLocal;
    private EditText editTextNameMeeting;
    private EditText editTextDescriptionMeeting;
    private EditText editTextDescriptionLocal;
    private EditText editTextLocalAddress;
    private CalendarDay calendarDay;

    private boolean validateHour;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Bundle bundle = getIntent().getExtras();

        setTheme(bundle.getBoolean("Theme")? R.style.DarkTheme : R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__tabs);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //setup arrow to back main activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //binding objects
        Bundle bundle = getIntent().getExtras();
        editTextNameLocal = findViewById(R.id.editTextNameLocal);
        editTextDescriptionLocal = findViewById(R.id.editTextDescriptionLocal);
        editTextLocalAddress = findViewById(R.id.editTextLocalAddress);
        editTextNameMeeting = findViewById(R.id.editTextNameMeeting);
        editTextDescriptionMeeting = findViewById(R.id.editTextDescriptionMeeting);
        calendarDay = bundle.getParcelable("Date");
        txView2 = findViewById(R.id.textView2);
        txView3 = findViewById(R.id.textView3);
        txViewDate = findViewById(R.id.textViewDate);

        //set the date recieved in TextView
        txViewDate.setText(txViewDate.getText()
                            + String.valueOf(calendarDay.getDay())
                            + "/" + String.valueOf(calendarDay.getMonth())
                            + "/" + String.valueOf(calendarDay.getYear()));

        validateHour = false;

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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_activity__tabs, container, false);
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new FragmentMeeting();
                    break;
                case 1:
                    fragment = new FragmentLocal();
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }
    }

    @SuppressLint("ValidFragment")
    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        TextView resultText;
        Boolean validate;

        @SuppressLint("ValidFragment")
        public TimePickerFragment(TextView tx, boolean validateH) {
            resultText = tx;
            validate = validateH;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            resultText.setText(resultText.getText());
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            resultText.setTextColor(getResources().getColor(R.color.black));
            validate = !validate;
            if(minute < 10) {
                resultText.setText(resultText.getText() + String.valueOf(hourOfDay) + ":" + "0" + String.valueOf(minute));
            } else {
                resultText.setText(resultText.getText() + String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
            }
        }
    }

    public void showTimePicker(View v) {
        switch (v.getId()) {
            case R.id.textView2:
                DialogFragment newFragment = new TimePickerFragment(txView2, validateHour);
                newFragment.show(getSupportFragmentManager(), "timePicker");
                break;
            case R.id.textView3:
                DialogFragment newFragment2 = new TimePickerFragment(txView3, validateHour);
                newFragment2.show(getSupportFragmentManager(), "timePicker");
                break;
        }
    }

    public void createMeeting(View v) {
        validateInputs(v);
    }

    public void validateInputs(View v) {
        System.out.println(validateHour);
        if(!validateHour) {
            txView2.setTextColor(getResources().getColor(R.color.error_fg_color));
            txView3.setTextColor(getResources().getColor(R.color.error_fg_color));
        }
        if(EditTextUtils.isInValid(
                new EditTextRequiredInputValidator(editTextNameLocal),
                new EditTextRequiredInputValidator(editTextDescriptionLocal),
                new EditTextRequiredInputValidator(editTextLocalAddress),
                new EditTextRequiredInputValidator(editTextNameMeeting),
                new EditTextRequiredInputValidator(editTextDescriptionMeeting)
        )) {
            Snackbar.make(v, R.string.invalid_input, Snackbar.LENGTH_LONG).show();
        }
    }
}
