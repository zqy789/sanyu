package com.ybkj.syzs.deliver.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigkoo.pickerview.view.TimePickerView;
import com.ybkj.syzs.deliver.R;
import com.ybkj.syzs.deliver.utils.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Description 时间选择视图
 * Author Ren Xingzhi
 * Created on 2019/4/2.
 * Email 15384030400@163.com
 */
public class TimeSortView extends FrameLayout {
    private static final int MODE_MONTH = 101;
    private static final int MODE_YEAR = 102;
    private Context mContext;
    private TextView tvBig;
    private TextView tvSmall;
    private TextView tvMonth;
    private TextView tvYear;
    private LinearLayout layoutMode;
    private RadioGroup radioGroupTime;
    private LinearLayout layoutTimeSelect;
    private RadioButton radio_current;
    private RadioButton radio_this_year;
    private RadioButton radio_last_month;
    private RadioButton radio_recent_year;
    private RadioButton radio_all;
    private int mode = MODE_MONTH;

    private int selectColor = R.color.project_base_color_green;

    private TimePickerView yearPickerView, monthPickerView;
    private OnModeChangeListener onModeChangeListener;

    public TimeSortView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        LayoutInflater.from(mContext).inflate(R.layout.project_base_layout_time_sort, this);
        tvBig = findViewById(R.id.tv_big);
        tvSmall = findViewById(R.id.tv_small);
        tvMonth = findViewById(R.id.tv_month);
        tvYear = findViewById(R.id.tv_year);
        layoutMode = findViewById(R.id.layout_mode);
        radioGroupTime = findViewById(R.id.radio_group_time);
        radio_current = findViewById(R.id.radio_current);
        radio_this_year = findViewById(R.id.radio_this_year);
        radio_last_month = findViewById(R.id.radio_last_month);
        radio_recent_year = findViewById(R.id.radio_recent_year);
        radio_all = findViewById(R.id.radio_all);


        layoutTimeSelect = findViewById(R.id.layout_time_select);

        String currentTime = DateUtil.getTimeStr();
        tvBig.setText(currentTime.substring(5, 7) + "/");
        tvSmall.setText(currentTime.substring(0, 4));

        radioGroupTime.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_current) {
                    String currentTime = DateUtil.getTimeStr();
                    tvBig.setText(currentTime.substring(5, 7) + "/");
                    tvSmall.setText(currentTime.substring(0, 4));
                    if (onModeChangeListener != null) {
                        onModeChangeListener.onModeChange(Mode.CURRENT_MONTH, new Date());
                    }
                    setMode(MODE_MONTH);
                } else if (checkedId == R.id.radio_last_month) {
                    String currentTime = DateUtil.getTimeStr();
                    String month = currentTime.substring(5, 7);
                    String year = currentTime.substring(0, 4);
                    int monthInt = Integer.parseInt(month);
                    if (monthInt > 1) {
                        monthInt -= 1;
                        if (monthInt >= 10) {
                            month = String.valueOf(monthInt);
                        } else {
                            month = "0" + monthInt;
                        }
                    } else {
                        month = "12";
                        int yearInt = Integer.parseInt(year);
                        yearInt -= 1;
                        year = String.valueOf(yearInt);
                    }
                    tvBig.setText(month + "/");
                    tvSmall.setText(year);
                    if (onModeChangeListener != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = null;
                        try {
                            date = sdf.parse(year + "-" + month + "-01");
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        onModeChangeListener.onModeChange(Mode.LAST_MONTH, date);
                    }
                    setMode(MODE_MONTH);
                } else if (checkedId == R.id.radio_this_year) {
                    String currentTime = DateUtil.getTimeStr();
                    tvBig.setText("");
                    tvSmall.setText(currentTime.substring(0, 4));
                    if (onModeChangeListener != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = null;
                        try {
                            date = sdf.parse(currentTime.substring(0, 4) + "-01-01");
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        onModeChangeListener.onModeChange(Mode.CURRENT_YEAR, date);
                    }
                    setMode(MODE_YEAR);
                } else if (checkedId == R.id.radio_recent_year) {
                    String currentTime = DateUtil.getTimeStr();
                    tvBig.setText("");
                    tvSmall.setText(currentTime.substring(0, 4));
                    if (onModeChangeListener != null) {
                        onModeChangeListener.onModeChange(Mode.RECENT_YEAR, new Date());
                    }
                    setMode(MODE_YEAR);
                } else if (checkedId == R.id.radio_all) {
                    if (onModeChangeListener != null) {
                        onModeChangeListener.onModeChange(Mode.ALL, new Date());
                    }
                    String currentTime = DateUtil.getTimeStr();
                    tvBig.setText("");
                    tvSmall.setText(currentTime.substring(0, 4));
                    setMode(MODE_YEAR);
                }
            }
        });

        layoutMode.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mode == MODE_MONTH) {
                    radio_this_year.setChecked(true);
                } else {
                    radio_current.setChecked(true);
                }
            }
        });

        layoutTimeSelect.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mode == MODE_MONTH) {
                    showMonthPick();
                } else if (mode == MODE_YEAR) {
                    showYearPick();
                }
            }
        });

    }

    public TimeSortView(@NonNull Context context) {
        this(context, null);
    }

    public void setOnModeChangeListener(OnModeChangeListener onModeChangeListener) {
        this.onModeChangeListener = onModeChangeListener;
    }

    public void setSelectColor(int color) {
        selectColor = color;
        tvMonth.setTextColor(getResources().getColor(selectColor));
    }

    public void setSelectDrawable(int drawable) {
        radio_current.setBackgroundResource(drawable);
        radio_this_year.setBackgroundResource(drawable);
        radio_last_month.setBackgroundResource(drawable);
        radio_recent_year.setBackgroundResource(drawable);
        radio_all.setBackgroundResource(drawable);
    }

    private void setMode(int mode) {
        this.mode = mode;
        if (mode == MODE_MONTH) {
            tvMonth.setTextColor(getResources().getColor(selectColor));
            tvYear.setTextColor(getResources().getColor(R.color.auth_color_a4a));
        } else {
            tvYear.setTextColor(getResources().getColor(selectColor));
            tvMonth.setTextColor(getResources().getColor(R.color.auth_color_a4a));
        }
    }

    private void showYearPick() {
        if (yearPickerView == null) {
            Calendar current = Calendar.getInstance();
            current.setTimeInMillis(DateUtil.getCurrentTimeMillis());
            yearPickerView = TimeSelectView.getTimePickerWithLimit(mContext, new TimeSelectView.TimerListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    tvSmall.setText(DateUtil.dateToString(date, DateUtil.dateFormat4).substring(0, 4));
                    if (onModeChangeListener != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date newDate = null;
                        try {
                            newDate = sdf.parse(tvSmall.getText().toString().trim() + "-01-01");
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        onModeChangeListener.onModeChange(Mode.CUSTOM_YEAR, newDate);
                    }
                }

                @Override
                public void onCancel() {
                    yearPickerView.dismiss();
                }

                @Override
                public void onConform() {
                    yearPickerView.returnData();
                    yearPickerView.dismiss();
                }
            }, 3, DateUtil.getCalender("2018-12-01 00:00:00"), current, current);
        }
        yearPickerView.show();
    }

    private void showMonthPick() {
        if (monthPickerView == null) {
            Calendar current = Calendar.getInstance();
            current.setTimeInMillis(DateUtil.getCurrentTimeMillis());
            monthPickerView = TimeSelectView.getTimePickerWithLimit(mContext, new TimeSelectView.TimerListener() {
                @Override
                public void onTimeSelect(Date date, View v) {
                    tvBig.setText(DateUtil.dateToString(date, DateUtil.dateFormat4).substring(5, 7) + "/");
                    tvSmall.setText(DateUtil.dateToString(date, DateUtil.dateFormat4).substring(0, 4));
                    if (onModeChangeListener != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date newDate = null;
                        try {
                            newDate = sdf.parse(tvSmall.getText().toString().trim() + "-" + DateUtil.dateToString(date, DateUtil.dateFormat4).substring(5, 7) + "-01");
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        onModeChangeListener.onModeChange(Mode.CUSTOM_MONTH, newDate);
                    }
                }

                @Override
                public void onCancel() {
                    monthPickerView.dismiss();
                }

                @Override
                public void onConform() {
                    monthPickerView.returnData();
                    monthPickerView.dismiss();
                }
            }, 4, DateUtil.getCalender("2018-12-01 00:00:00"), current, current);
        }
        monthPickerView.show();
    }

    public enum Mode {
        CURRENT_MONTH, LAST_MONTH, CURRENT_YEAR, RECENT_YEAR, ALL, CUSTOM_MONTH, CUSTOM_YEAR
    }

    public interface OnModeChangeListener {
        void onModeChange(Enum mode, Date date);
    }
}
