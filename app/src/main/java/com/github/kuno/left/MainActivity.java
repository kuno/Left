package com.github.kuno.left;

import org.threeten.bp.LocalDate;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import android.os.Bundle;
import android.os.Handler;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends WearableActivity {

    private static final SimpleDateFormat AMBIENT_DATE_FORMAT =
            new SimpleDateFormat("HH:mm", Locale.US);

    private static final int EXPECT_LIFT_EXTENTENCY = 82;
    private static final SimpleDateFormat BIRTH_AND_DEAD_DAY_FORMAT =
            new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    private static final int BIRTH_YEAR = 1980;
    private static final int BIRTH_MONTH = 2;
    private static final int BIRTH_DAY = 22;

    private BoxInsetLayout mContainerView;
    private TextView mTextView;
    private TextView mClockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAmbientEnabled();

        mContainerView = (BoxInsetLayout) findViewById(R.id.container);
        mTextView = (TextView) findViewById(R.id.text);
        mClockView = (TextView) findViewById(R.id.clock);

        //LocalDate birthDate = new LocalDate(1980, 2, 22);
        LocalDate deathDate = LocalDate.of(BIRTH_YEAR + EXPECT_LIFT_EXTENTENCY, BIRTH_MONTH, BIRTH_DAY);

        //Interval interval = new Interval(birthDate.toDateTimeAtStartOfDay(), deathDate.toDateTimeAtStartOfDay());

        ZonedDateTime zdt = deathDate.atStartOfDay().atZone(ZoneId.of("Asia/Shanghai"));

        String text = (zdt.toInstant().toEpochMilli() - System.currentTimeMillis()) / (1000 * 60 * 60 * 24) + " Days Left to Live";

        mTextView.setText(text);
    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
        updateDisplay();
    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();
        updateDisplay();
    }

    @Override
    public void onExitAmbient() {
        updateDisplay();
        super.onExitAmbient();
    }

    private void updateDisplay() {
        if (isAmbient()) {
            mContainerView.setBackgroundColor(getResources().getColor(android.R.color.black));
            mTextView.setTextColor(getResources().getColor(android.R.color.white));
            mClockView.setVisibility(View.VISIBLE);

            mClockView.setText(AMBIENT_DATE_FORMAT.format(new Date()));
        } else {
            mContainerView.setBackground(null);
            mTextView.setTextColor(getResources().getColor(android.R.color.black));
            mClockView.setVisibility(View.GONE);
        }
    }
}
