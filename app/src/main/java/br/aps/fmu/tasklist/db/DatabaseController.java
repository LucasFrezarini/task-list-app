package br.aps.fmu.tasklist.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Calendar;

import br.aps.fmu.tasklist.models.Task;

public class DatabaseController {

    private SQLiteDatabase db;
    private Database database;

    public DatabaseController(Context context) {
        this.database = new Database(context);
    }

    public Cursor loadTasks() {
        String[] fields = {Database.ID, Database.DATE, Database.DESCRIPTION, Database.TITLE, Database.DONE};

        this.db = database.getReadableDatabase();
        Cursor cursor = this.db
                .query(Database.TABLE, fields, null, null, null, null, null);

        if(cursor != null) {
            cursor.moveToFirst();
        }

        this.db.close();
        return cursor;
    }

    public boolean saveTask(Task task) {
        ContentValues cv = this.createContentValuesFromTask(task);

        this.db = database.getWritableDatabase();

        long result = this.db.insert(Database.TABLE, null, cv);

        return result != -1;
    }

    public boolean updateTask(Task task) {
        ContentValues cv = this.createContentValuesFromTask(task);

        String where = Database.ID + " = " + task.getId();

        this.db = database.getWritableDatabase();

        long result = this.db.update(Database.TABLE, cv, where, null);

        return result != -1;
    }

    public Task getTaskById(int id) {
        String[] fields = {Database.ID, Database.DATE, Database.DESCRIPTION, Database.TITLE, Database.DONE};

        this.db = database.getReadableDatabase();

        String where = Database.ID + " = " + id;

        Cursor cursor = this.db
                .query(Database.TABLE, fields, where, null, null, null, null);

        if(cursor != null) {
            cursor.moveToFirst();

            Task t = new Task();

            t.setTitle(cursor.getString(cursor.getColumnIndex(Database.TITLE)));
            t.setDescription(cursor.getString(cursor.getColumnIndex(Database.DESCRIPTION)));
            t.setDone(cursor.getInt(cursor.getColumnIndex(Database.DONE)) == 1);
            t.setId(cursor.getInt(cursor.getColumnIndex(Database.ID)));

            Calendar date = Calendar.getInstance();

            date.setTimeInMillis(cursor.getLong(cursor.getColumnIndex(Database.DATE)));
            t.setDate(date);

            this.db.close();

            return t;
        }


        this.db.close();
        return null;
    }

    private ContentValues createContentValuesFromTask(Task task) {
        ContentValues cv = new ContentValues();

        cv.put(Database.TITLE, task.getTitle());
        cv.put(Database.DATE, task.getDate().getTimeInMillis());
        cv.put(Database.DESCRIPTION, task.getDescription());
        cv.put(Database.DONE, task.isDone());

        return cv;
    }
}
