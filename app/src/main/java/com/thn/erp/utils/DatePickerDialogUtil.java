package com.thn.erp.utils;

import android.app.Activity;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.thn.erp.R;

import java.util.Calendar;

/**
 * Created by lilin on 2017/7/25.
 */

public class DatePickerDialogUtil {

    public static void show(Activity activity, DatePickerDialog.OnDateSetListener listener){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                listener,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setYearRange(2000,now.get(Calendar.YEAR));
        dpd.setMaxDate(now);
        dpd.setFirstDayOfWeek(Calendar.MONDAY, Calendar.MONDAY);
        int color = activity.getResources().getColor(R.color.color_ff5a5f);
        dpd.setAccentColor(color);
        dpd.show(activity.getFragmentManager(), "Datepickerdialog");
    }
}
