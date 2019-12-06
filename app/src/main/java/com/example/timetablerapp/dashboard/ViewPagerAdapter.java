package com.example.timetablerapp.dashboard;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.example.timetablerapp.R;
import com.example.timetablerapp.dashboard.dialog.room.AddClassFragment;
import com.example.timetablerapp.dashboard.dialog.lecturer.AddLecturerFragment;
import com.example.timetablerapp.dashboard.dialog.AddUnitFragment;

/**
 * 02/09/19 -bernard
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private Activity activity;
    private int[] resourceId = {
            R.drawable.ic_dashboard,
            R.drawable.ic_lecturer,
            R.drawable.ic_room,
            R.drawable.ic_course
    };

    public ViewPagerAdapter(FragmentManager fm, Activity activity) {
        super(fm);
        this.activity = activity;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new Fragment();
                break;
            case 1:
                fragment = new AddLecturerFragment();
                break;
            case 2:
                fragment = new AddClassFragment();
                break;
            case 3:
                fragment = new AddUnitFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        Drawable drawable = activity.getResources().getDrawable(resourceId[position]);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        SpannableString sp = new SpannableString(" ");
        ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
        sp.setSpan(span, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp;
    }
}
