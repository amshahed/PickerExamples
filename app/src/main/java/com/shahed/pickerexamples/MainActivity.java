package com.shahed.pickerexamples;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private String time;
    private TextView timeText;
    private Calendar calendar;
    private SimpleDateFormat timeFormat, dateFormat, monthFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendar = Calendar.getInstance();
        timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        dateFormat = new SimpleDateFormat("dd MMM, yyyy", Locale.getDefault());
        monthFormat = new SimpleDateFormat("MMM, yyyy", Locale.getDefault());
        time = dateFormat.format(calendar.getTime());
        timeText = findViewById(R.id.pickerText);
        timeText.setText(time);

        Button timePicker = findViewById(R.id.timePicker);
        timePicker.setOnClickListener(new View.OnClickListener() {
            int hour = calendar.get(Calendar.HOUR);
            int minute = calendar.get(Calendar.MINUTE);

            @Override
            public void onClick(View view) {
                new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                        calendar.set(Calendar.MINUTE, selectedMinute);
                        time = timeFormat.format(calendar.getTime());
                        timeText.setText(time);
                    }
                }, hour, minute, false).show();
            }
        });

        Button datePicker = findViewById(R.id.datePicker);
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                        calendar.set(Calendar.YEAR, selectedYear);
                        calendar.set(Calendar.MONTH, selectedMonth);
                        calendar.set(Calendar.DAY_OF_MONTH, selectedDay);
                        time = dateFormat.format(calendar.getTime());
                        timeText.setText(time);
                    }
                }, year, month, day);
                dialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                dialog.show();
            }
        });

        Button monthPicker = findViewById(R.id.monthPicker);
        monthPicker.setOnClickListener(new View.OnClickListener() {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);

            @Override
            public void onClick(View view) {
                new MonthPickerDialog.Builder(MainActivity.this, new MonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int selectedMonth, int selectedYear) {
                        calendar.set(Calendar.YEAR, selectedYear);
                        calendar.set(Calendar.MONTH, selectedMonth);
                        time = monthFormat.format(calendar.getTime());
                        timeText.setText(time);
                    }
                }, year, month).build().show();
            }
        });

        Button yearPicker = findViewById(R.id.yearPicker);
        yearPicker.setOnClickListener(new View.OnClickListener() {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);

            @Override
            public void onClick(View view) {
                new MonthPickerDialog.Builder(MainActivity.this, new MonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int selectedMonth, int selectedYear) {
                        calendar.set(Calendar.YEAR, selectedYear);
                        time = String.valueOf(calendar.get(Calendar.YEAR));
                        timeText.setText(time);
                    }
                }, year, month)
                        .showYearOnly()
                        .setMinYear(1993)
                        .setMaxYear(calendar.get(Calendar.YEAR))
                        .build()
                        .show();
            }
        });

        Button rangePicker = findViewById(R.id.rangePicker);
        rangePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout pickerLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.layout_date_range_picker, null, false);
                DateRangeCalendarView datePicker = (DateRangeCalendarView) pickerLayout.getChildAt(0);
                datePicker.setCalendarListener(new DateRangeCalendarView.CalendarListener() {
                    @Override
                    public void onFirstDateSelected(Calendar start) {
                        time = dateFormat.format(start.getTime());
                    }

                    @Override
                    public void onDateRangeSelected(Calendar start, Calendar end) {
                        time = dateFormat.format(start.getTime()).concat(" - ").concat(dateFormat.format(end.getTime()));
                    }
                });

                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("শুরুর দিন এবং শেষ দিনে ক্লিক করে দিনের পরিসীমা সিলেক্ট করুন")
                        .setView(pickerLayout)
                        .setCancelable(true)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                timeText.setText(time);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
            }
        });
    }
}
