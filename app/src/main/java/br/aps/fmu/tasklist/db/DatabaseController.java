package br.aps.fmu.tasklist.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
        ContentValues cv = new ContentValues();

        this.db = database.getWritableDatabase();

        cv.put(Database.TITLE, task.getTitle());
        cv.put(Database.DATE, task.getDate().getTimeInMillis());
        cv.put(Database.DESCRIPTION, task.getDescription());
        cv.put(Database.DONE, task.isDone());

        long result = this.db.insert(Database.TABLE, null, cv);

        return result != -1;
    }
}
