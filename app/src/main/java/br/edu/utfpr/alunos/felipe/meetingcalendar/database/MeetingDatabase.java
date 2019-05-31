package br.edu.utfpr.alunos.felipe.meetingcalendar.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import br.edu.utfpr.alunos.felipe.meetingcalendar.dao.LocalDao;
import br.edu.utfpr.alunos.felipe.meetingcalendar.dao.MeetingDao;
import br.edu.utfpr.alunos.felipe.meetingcalendar.model.Local;
import br.edu.utfpr.alunos.felipe.meetingcalendar.model.Meeting;

@Database(entities = {Meeting.class, Local.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class MeetingDatabase extends RoomDatabase {
    public abstract MeetingDao getMeetingDao();

    public abstract LocalDao getLocalDao();

    private static MeetingDatabase instance;

    public static MeetingDatabase getDatabase(final Context context) {

        if (instance == null) {
            synchronized (MeetingDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context,
                            MeetingDatabase.class,
                            "meeting").allowMainThreadQueries().build();
                }
            }
        }
        return instance;
    }
}
