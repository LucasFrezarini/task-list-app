package br.aps.fmu.tasklist;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.Locale;

import br.aps.fmu.tasklist.adapters.TaskListAdapter;
import br.aps.fmu.tasklist.db.Database;
import br.aps.fmu.tasklist.db.DatabaseController;

public class TaskList extends AppCompatActivity {

    private ListView tasksListView;
    private TaskListAdapter taskAdapter;
    private Spinner spnLanguage;
    private boolean spinnerLoaded = false;
    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        extras = getIntent().getExtras();

        if(extras == null) {
            extras = new Bundle();
        }

        DatabaseController dc = new DatabaseController(getBaseContext());
        final Cursor cursor = dc.loadTasks();

        this.taskAdapter = new TaskListAdapter(
                getBaseContext(), cursor, 0);

        this.tasksListView = findViewById(R.id.tasksList);
        this.tasksListView.setAdapter(this.taskAdapter);

        final String[] languages = new String[]{"Português", "English"};
        final String language = extras.getString("language", "Português");

        ArrayAdapter<String> langAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, languages);

        this.spnLanguage = findViewById(R.id.spinnerLanguage);
        this.spnLanguage.setAdapter(langAdapter);

        this.spnLanguage.setSelection(language.equals("Português") ? 0 : 1);

        this.tasksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cursor.moveToPosition(i);

                Intent it = new Intent(getBaseContext(), TaskForm.class);
                Bundle bundle = new Bundle();

                bundle.putInt("id", cursor.getInt(cursor.getColumnIndex(Database.ID)));
                bundle.putString("operation", "edit");

                it.putExtras(bundle);
                startActivity(it);
            }
        });
        // Lógica para mudar o idioma
        this.spnLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View selectedItemView, int position, long l) {
                if(spinnerLoaded) {
                    String selected = languages[position];

                    Resources res = getBaseContext().getResources();
                    DisplayMetrics dm = res.getDisplayMetrics();
                    android.content.res.Configuration conf = res.getConfiguration();

                    if(selected.equals("Português")) {
                        conf.setLocale(new Locale("pt-br"));
                    } else {
                        conf.setLocale(new Locale("en"));
                    }

                    res.updateConfiguration(conf, dm);

                    Intent it = new Intent(getBaseContext(), TaskList.class);

                    Bundle bundle = new Bundle();

                    bundle.putString("language", selected);

                    it.putExtras(bundle);

                    startActivity(it);

                    finish();
                } else {
                    spinnerLoaded = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        DatabaseController dc = new DatabaseController(getBaseContext());
        final Cursor cursor = dc.loadTasks();

        this.taskAdapter.swapCursor(cursor);
        this.taskAdapter.notifyDataSetChanged();
    }

    public void newTask(View view) {
        Intent it = new Intent(this, TaskForm.class);

        startActivity(it);
    }
}
