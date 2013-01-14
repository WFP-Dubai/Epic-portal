package com.rcs.portlet.slider.util;

import com.rcs.portlet.slider.model.Slide;
import java.util.Comparator;

public class DateComparator
    implements Comparator
{

    public DateComparator()
    {
    }

    public int compare(Slide slide1, Slide slide2)
    {
        if(slide1.getId() > slide2.getId())
        {
            return -1;
        }
        return slide1.getId() >= slide2.getId() ? 0 : 1;
    }

    public volatile int compare(Object x0, Object x1)
    {
        return compare((Slide)x0, (Slide)x1);
    }
}
