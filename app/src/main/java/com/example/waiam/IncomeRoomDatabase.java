package com.example.waiam;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Database(entities = {Income.class}, version = 10) //TODO: migration strategy
@TypeConverters({Converters.class})
public abstract class IncomeRoomDatabase extends RoomDatabase {
    public abstract IncomeDao incomeDao();

    private static IncomeRoomDatabase INSTANCE;

    static IncomeRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null) {
            synchronized (IncomeRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            IncomeRoomDatabase.class, "income_database")
                            .fallbackToDestructiveMigration() //resets database, because im too lazy to create migration pattern yet
                            .addCallback(sRoomDatabaseCallback) //make sure to edit this out if you want the database to persist
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /*static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL(" DROP COLUMN id INTEGER");        }
    };*/


    //callback resets the database on opening the app
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final IncomeDao mDao;

        PopulateDbAsync(IncomeRoomDatabase db) {
            mDao = db.incomeDao();
        }

        //this is a dummy holder
        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();

            Date dateIn = new Date(2018,8,22, 4, 21);
            long timeWorked = 2000;
            Income income = new Income(dateIn, timeWorked, 100.00);
            mDao.insert(income);

            return null;
        }
    }
}
