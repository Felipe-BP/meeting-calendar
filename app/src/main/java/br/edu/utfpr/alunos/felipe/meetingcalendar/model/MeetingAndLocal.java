package br.edu.utfpr.alunos.felipe.meetingcalendar.model;

import android.arch.persistence.room.Embedded;

import br.edu.utfpr.alunos.felipe.meetingcalendar.MainActivity;
import br.edu.utfpr.alunos.felipe.meetingcalendar.R;

public class MeetingAndLocal {
    @Embedded
    private Meeting meeting;

    @Embedded
    private Local local;

    @Override
    public String toString() {
        return MainActivity.getContext().getString(R.string.input_title) + ": " + getMeeting().getTitle() + "\n \n"
                + MainActivity.getContext().getString(R.string.input_description) + ": " + getMeeting().getDescription() + "\n \n"
                + MainActivity.getContext().getString(R.string.title_activity__list_label_init) + getMeeting().getTimeStart() + "\n"
                + MainActivity.getContext().getString(R.string.title_activity__list_label_end) + getMeeting().getTimeEnd() + "\n \n"
                + MainActivity.getContext().getString(R.string.title_activity__list_label_local)+ "\n"
                + MainActivity.getContext().getString(R.string.input_local_name) + ": " + getLocal().getName() + "\n"
                + MainActivity.getContext().getString(R.string.input_local_description) + ": " + getLocal().getDescription() + "\n"
                + MainActivity.getContext().getString(R.string.input_local_address) + ": " + getLocal().getAddress() + "\n";
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }
}
