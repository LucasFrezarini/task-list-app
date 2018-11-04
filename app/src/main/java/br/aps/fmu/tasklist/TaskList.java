package br.aps.fmu.tasklist;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.Calendar;

import br.aps.fmu.tasklist.adapters.TaskListAdapter;
import br.aps.fmu.tasklist.db.Database;
import br.aps.fmu.tasklist.db.DatabaseController;
import br.aps.fmu.tasklist.models.Task;

public class TaskList extends Activity {

    private ListView tasksListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        DatabaseController dc = new DatabaseController(getBaseContext());
        final Cursor cursor = dc.loadTasks();

        TaskListAdapter taskAdapter = new TaskListAdapter(
                getBaseContext(), cursor, 0);

        this.tasksListView = findViewById(R.id.tasksList);
        this.tasksListView.setAdapter(taskAdapter);

    }

    public void newTask(View view) {
        Intent it = new Intent(this, NewTask.class);

        startActivity(it);
    }
}
