package br.edu.utfpr.alunos.felipe.meetingcalendar.database;

import android.arch.persistence.room.TypeConverter;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;

public class Converters {
    @TypeConverter
    public static LocalDate dateFromString(String date) {
        return date == null ? null : LocalDate.parse(date);
    }

    @TypeConverter
    public static String stringToDate(LocalDate date) {
        return date == null ? null : date.toString();
    }

    @TypeConverter
    public static LocalTime timeFromString(String time) {
        return time == null ? null : LocalTime.parse(time);
    }

    @TypeConverter
    public static String stringToTime(LocalTime time) {
        return time == null ? null : time.toString();
    }
}
