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

    public static String getShortenMonth(Context context, int month) {
        String[] shortenMonths = new String[] {
                context.getResources().getString(R.string.short_january),
                context.getResources().getString(R.string.short_february),
                context.getResources().getString(R.string.short_march),
                context.getResources().getString(R.string.short_april),
                context.getResources().getString(R.string.short_may),
                context.getResources().getString(R.string.short_june),
                context.getResources().getString(R.string.short_july),
                context.getResources().getString(R.string.short_august),
                context.getResources().getString(R.string.short_september),
                context.getResources().getString(R.string.short_october),
                context.getResources().getString(R.string.short_november),
                context.getResources().getString(R.string.short_december),
        };

        return shortenMonths[month];
    }
}
