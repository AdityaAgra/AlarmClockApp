package com.example.alarmclockdb.alarmdata;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AlarmDao {
    @Insert
    void insert(AlarmEntity alarm);

    @Query("DELETE FROM alarm_table")
    void deleteAll();

    @Query("DELETE FROM alarm_table WHERE alarmId = :alarmId")
    void deleteByAlarmID(int alarmId);

    @Query("SELECT * FROM alarm_table ORDER BY created ASC")
    List<AlarmEntity>getAlarms();

    @Query("SELECT * FROM alarm_table WHERE alarmId = :alarmId")
    AlarmEntity getAlarmById(int alarmId);

    @Update
    void update(AlarmEntity alarm);
}
