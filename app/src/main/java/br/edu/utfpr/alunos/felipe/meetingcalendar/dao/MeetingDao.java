package br.edu.utfpr.alunos.felipe.meetingcalendar.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import br.edu.utfpr.alunos.felipe.meetingcalendar.model.Meeting;
import br.edu.utfpr.alunos.felipe.meetingcalendar.model.MeetingAndLocal;

@Dao
public interface MeetingDao {
    @Query("SELECT meeting.*, local.local_id, name, local_description, address FROM meeting JOIN local ON meeting.local_Id = local.local_id " +
            "WHERE meeting.date = :date")
    List<MeetingAndLocal> getMeetingsOfDay(String date);

    @Insert
    void insert(Meeting... meeting);

    @Query("DELETE FROM meeting WHERE meeting_id = :meetingId")
    void delete(int meetingId);

    @Query("UPDATE meeting SET title = :title, meeting_description = :description, timeStart = :start, timeEnd = :end, date = :date WHERE meeting_id = :id")
    void update(int id, String title, String description, String start, String end, String date);
}
