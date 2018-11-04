package br.aps.fmu.tasklist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.Calendar;

import br.aps.fmu.tasklist.db.Database;
import br.aps.fmu.tasklist.db.DatabaseController;
import br.aps.fmu.tasklist.models.Task;

public class NewTask extends Activity {

    private EditText txtTaskTitle;
    private EditText txtTaskDescription;
    private EditText txtTaskDay;
    private EditText txtTaskMonth;
    private EditText txtTaskYear;
    private CheckBox chkTaskDone;

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
    }

    public void saveTask(View view) {
        Calendar date = Calendar.getInstance();

        int day = Integer.parseInt(txtTaskDay.getText().toString());
        int month = Integer.parseInt(txtTaskMonth.getText().toString());
        int year = Integer.parseInt(txtTaskYear.getText().toString());

        date.set(year, month, day);

        Task task = new Task(txtTaskTitle.getText().toString(), txtTaskDescription.getText().toString(), date, chkTaskDone.isChecked());

        DatabaseController dc = new DatabaseController(this.getBaseContext());
        if(dc.saveTask(task)) {
            Intent it = new Intent(this, TaskList.class);
            startActivity(it);
        }
    }
}
