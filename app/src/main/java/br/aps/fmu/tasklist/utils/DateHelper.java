package br.aps.fmu.tasklist.utils;

import android.content.Context;
import android.content.res.Resources;

import br.aps.fmu.tasklist.R;

public class DateHelper {
    public static String getShortenDayOfWeek(Context context, int day) {

        String[] shortenDaysOfWeek = new String[] {
                context.getResources().getString(R.string.short_sunday),
                context.getResources().getString(R.string.short_monday),
                context.getResources().getString(R.string.short_tuesday),
                context.getResources().getString(R.string.short_wednesday),
                context.getResources().getString(R.string.short_thursday),
                context.getResources().getString(R.string.short_friday),
                context.getResources().getString(R.string.short_saturday),
        };

        return shortenDaysOfWeek[day];
    }
}
