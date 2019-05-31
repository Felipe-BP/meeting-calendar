package br.edu.utfpr.alunos.felipe.meetingcalendar.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import br.edu.utfpr.alunos.felipe.meetingcalendar.model.Local;

@Dao
public interface LocalDao {
    @Query("SELECT local_id FROM local WHERE name = :name AND address = :address")
    int getLocalMeeting(String name, String address);

    @Query("SELECT * FROM local")
    Local getLocal();

    @Insert
    void insert(Local... local);

    @Query("UPDATE local SET name = :name, local_description = :description, address = :address WHERE local_id = :id")
    void update(int id, String name, String description, String address);
}
