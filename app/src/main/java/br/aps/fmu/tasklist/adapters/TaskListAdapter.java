package br.aps.fmu.tasklist.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

import br.aps.fmu.tasklist.R;
import br.aps.fmu.tasklist.db.Database;
import br.aps.fmu.tasklist.utils.DateHelper;

public class TaskListAdapter extends CursorAdapter {

    private LayoutInflater cursorInflater;

    public TaskListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        cursorInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return cursorInflater.inflate(R.layout.list_layout, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView txtViewDay = view.findViewById(R.id.rowDay);
        TextView txtViewMonth = view.findViewById(R.id.rowDayOfMonth);
        TextView txtViewTitle = view.findViewById(R.id.rowTitle);
        TextView txtViewDescription = view.findViewById(R.id.rowDescription);
        LinearLayout legendIsDone = view.findViewById(R.id.legendDone);

        String title = cursor.getString(cursor.getColumnIndex(Database.TITLE));
        String description = cursor.getString(cursor.getColumnIndex(Database.DESCRIPTION));
        boolean done = cursor.getInt(cursor.getColumnIndex(Database.DONE)) == 1;

        long timeInMillis = cursor.getLong(cursor.getColumnIndex(Database.DATE));

        Calendar date = Calendar.getInstance();

        date.setTimeInMillis(timeInMillis);

        txtViewTitle.setText(title);
        txtViewDescription.setText(description);
        txtViewDay.setText(String.valueOf(date.get(Calendar.DAY_OF_MONTH)));

        String month = DateHelper.getShortenMonth(context, date.get(Calendar.MONTH));

        txtViewMonth.setText(month);

        if(!done) {
            legendIsDone.setBackgroundResource(R.color.colorPrimary);
        }
    }
}
