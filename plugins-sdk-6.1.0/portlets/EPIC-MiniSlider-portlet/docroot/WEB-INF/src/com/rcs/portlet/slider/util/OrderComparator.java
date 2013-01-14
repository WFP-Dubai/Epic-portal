package com.rcs.portlet.slider.util;

import com.rcs.portlet.slider.model.Slide;
import java.util.Comparator;

public class OrderComparator
    implements Comparator
{

    public OrderComparator()
    {
    }

    public int compare(Slide slide1, Slide slide2)
    {
        if(slide1.getOrder() < slide2.getOrder())
        {
            return -1;
        }
        return slide1.getOrder() <= slide2.getOrder() ? 0 : 1;
    }

    public volatile int compare(Object x0, Object x1)
    {
        return compare((Slide)x0, (Slide)x1);
    }
}
