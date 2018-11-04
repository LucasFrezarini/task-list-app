package br.aps.fmu.tasklist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import br.aps.fmu.tasklist.db.Database;
import br.aps.fmu.tasklist.db.DatabaseController;
import br.aps.fmu.tasklist.models.Task;

public class TaskForm extends AppCompatActivity {

    private EditText txtTaskTitle;
    private EditText txtTaskDescription;
    private EditText txtTaskDay;
    private EditText txtTaskMonth;
    private EditText txtTaskYear;
    private CheckBox chkTaskDone;
    private String operation;
    private int taskId;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        this.txtTaskTitle = findViewById(R.id.inputTaskTitle);
        this.txtTaskDescription = findViewById(R.id.inputTaskDescription);
        this.chkTaskDone = findViewById(R.id.isTaskDone);
        this.txtTaskDay = findViewById(R.id.inputTaskDay);
        this.txtTaskMonth = findViewById(R.id.inputTaskMonth);
        this.txtTaskYear = findViewById(R.id.inputTaskYear);

        this.extras = getIntent().getExtras();

        if(this.extras == null) {
            this.extras = new Bundle();
        }

        this.operation = extras.getString("operation", "create");
        this.taskId = extras.getInt("id",0);

        if (this.operation.equals("edit") && taskId != 0) {
            DatabaseController dc = new DatabaseController(this.getBaseContext());

            Task task = dc.getTaskById(taskId);

            this.txtTaskTitle.setText(task.getTitle());
            this.txtTaskDescription.setText(task.getDescription());
            this.chkTaskDone.setChecked(task.isDone());
            this.txtTaskDay.setText(String.valueOf(task.getDate().get(Calendar.DAY_OF_MONTH)));
            this.txtTaskMonth.setText(String.valueOf(task.getDate().get(Calendar.MONTH) + 1));
            this.txtTaskYear.setText(String.valueOf(task.getDate().get(Calendar.YEAR)));
        }
    }

    public void saveTask(View view) {
        Calendar date = Calendar.getInstance();

        int day = Integer.parseInt(txtTaskDay.getText().toString());
        int month = Integer.parseInt(txtTaskMonth.getText().toString()) - 1;
        int year = Integer.parseInt(txtTaskYear.getText().toString());

        date.set(year, month, day);

        Task task = new Task(txtTaskTitle.getText().toString(), txtTaskDescription.getText().toString(), date, chkTaskDone.isChecked());

        DatabaseController dc = new DatabaseController(this.getBaseContext());

        if(this.operation.equals("create")) {
            if(dc.saveTask(task)) {
                Toast.makeText(getBaseContext(), getBaseContext().getString(R.string.save_success), Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(getBaseContext(), getBaseContext().getString(R.string.save_error), Toast.LENGTH_LONG).show();
            }
        } else {
            task.setId(taskId);

            if(dc.updateTask(task)) {
                Toast.makeText(getBaseContext(), getBaseContext().getString(R.string.edit_success), Toast.LENGTH_LONG).show();
                finish();
            } else {
                Toast.makeText(getBaseContext(), getBaseContext().getString(R.string.edit_error), Toast.LENGTH_LONG).show();
            }
        }
    }
}
