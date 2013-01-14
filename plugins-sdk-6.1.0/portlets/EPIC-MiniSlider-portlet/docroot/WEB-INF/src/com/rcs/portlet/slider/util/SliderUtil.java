package com.rcs.portlet.slider.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.rcs.portlet.slider.model.Slide;
import java.util.*;
import javax.portlet.*;

// Referenced classes of package com.rcs.portlet.slider.util:
//            OrderComparator

public class SliderUtil
{

    public SliderUtil()
    {
    }

    public static List getSlides(PortletRequest request, PortletResponse response)
        throws PortalException, SystemException
    {
        return getSlidesByComparator(request, response, new OrderComparator());
    }

    public static int getLastSlide(ActionRequest request, ActionResponse response)
        throws PortalException, SystemException
    {
        List slidesByOrder = getSlides(request, response);
        if(slidesByOrder != null && slidesByOrder.size() > 0)
        {
            Slide slide = (Slide)slidesByOrder.get(slidesByOrder.size() - 1);
            if(slide != null)
            {
                return slide.getOrder() + 1;
            }
        }
        return 0;
    }

    public static List getSlidesByComparator(PortletRequest request, PortletResponse response, Comparator comparator)
        throws PortalException, SystemException
    {
        String portletResource = ParamUtil.getString(request, "portletResource");
        PortletPreferences portletPreferences = getPreference(request, portletResource);
        List slides = new ArrayList();
        Enumeration prefMap = portletPreferences.getNames();
        do
        {
            if(!prefMap.hasMoreElements())
            {
                break;
            }
            String slideId = (String)prefMap.nextElement();
            if(slideId.startsWith("slides_"))
            {
                String values[] = portletPreferences.getValues(slideId, null);
                if(Validator.isNotNull(values))
                {
                    Slide slide = getSlide(slideId, values);
                    slides.add(slide);
                }
            }
        } while(true);
        Collections.sort(slides, comparator);
        return slides;
    }

    public static PortletPreferences getPreference(PortletRequest request, String portletResource)
        throws PortalException, SystemException
    {
        if(portletResource == null || portletResource.trim().equals(""))
        {
            ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute("THEME_DISPLAY");
            portletResource = themeDisplay.getPortletDisplay().getId();
        }
        return PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
    }

    public static String buildSlides(PortletRequest renderRequest, PortletResponse renderResponse)
        throws PortalException, SystemException
    {
        List slides = getSlides(renderRequest, renderResponse);
        StringBuilder slidesBuilder = new StringBuilder();
        Iterator i$ = slides.iterator();
        do
        {
            if(!i$.hasNext())
            {
                break;
            }
            Slide slide = (Slide)i$.next();
            if(Validator.isNotNull(slide.getLink()))
            {
                slidesBuilder.append("<a href=\"");
                slidesBuilder.append(slide.getLink());
                slidesBuilder.append("\">");
            }
            slidesBuilder.append("<img src=\"");
            slidesBuilder.append(slide.getImageUrl());
            slidesBuilder.append("\" ");
            
            slidesBuilder.append(" title=\"");
            slidesBuilder.append(slide.getTitle());
            slidesBuilder.append("\" ");
            
            if(Validator.isNotNull(slide.getDesc()))
            {
                slidesBuilder.append(" alt=\"");
                slidesBuilder.append(slide.getDesc());
                slidesBuilder.append("\" ");
            }
            slidesBuilder.append("/>");
            if(Validator.isNotNull(slide.getLink()))
            {
                slidesBuilder.append("</a>");
            }
        } while(true);
        return slidesBuilder.toString();
    }

    public static String buildSettings(PortletRequest renderRequest, PortletResponse renderResponse)
        throws PortalException, SystemException
    {
        PortletPreferences preferences = getPreference(renderRequest, null);
        String effectSelectedValue = preferences.getValue("settings-effect", "random");
        String slicesValue = preferences.getValue("settings-slices", "15");
        String boxColumnValue = preferences.getValue("settings-boxColumn", "8");
        String animationSpeedValue = preferences.getValue("settings-animationSpeed", "500");
        String pauseTimeValue = preferences.getValue("settings-pauseTime", "3000");
        String startSlideValue = preferences.getValue("settings-startSlide", "0");
        String randomSlideValue = preferences.getValue("settings-randomSlide", "false");
        String directionNav = preferences.getValue("settings-direction-naviation", "true");
        String prevTextValue = preferences.getValue("settings-previous-text", "Prev");
        String nextTextValue = preferences.getValue("settings-next-text", "Next");
        String autoHideNav = preferences.getValue("settings-auto-hide-nav", "false");
        String controlNavValue = preferences.getValue("settings-control-nav", "true");
        String keyboardNavValue = preferences.getValue("settings-keyboard-nav", "true");
        String pauseOnHoverValue = preferences.getValue("settings-pause-onhover", "true");
        String manualAdvanceValue = preferences.getValue("settings-manual-advance", "false");
        String opacityValue = preferences.getValue("settings-opacity", "0.8");
        StringBuilder settings = new StringBuilder();
        settings.append((new StringBuilder()).append("effect:'").append(effectSelectedValue).append("'").toString());
        settings.append((new StringBuilder()).append(", slices:").append(slicesValue).toString());
        settings.append((new StringBuilder()).append(", boxCols:").append(boxColumnValue).toString());
        if(Validator.isNotNull(animationSpeedValue) && Validator.isNumber(animationSpeedValue))
        {
            settings.append((new StringBuilder()).append(", animSpeed:").append(animationSpeedValue).toString());
        }
        if(Validator.isNotNull(pauseTimeValue) && Validator.isNumber(pauseTimeValue))
        {
            settings.append((new StringBuilder()).append(", pauseTime:").append(pauseTimeValue).toString());
        }
        if(Validator.isNull(startSlideValue) && Validator.isNumber(startSlideValue))
        {
            settings.append((new StringBuilder()).append(", startSlide:").append(startSlideValue).toString());
        }
        if(Validator.isNotNull(randomSlideValue) && Validator.isNumber(randomSlideValue))
        {
            settings.append((new StringBuilder()).append(", randomStart:").append(randomSlideValue).toString());
        }
        settings.append((new StringBuilder()).append(", directionNav:").append(directionNav).toString());
        settings.append((new StringBuilder()).append(", directionNavHide:").append(autoHideNav).toString());
        if(Validator.isNull(prevTextValue))
        {
            settings.append((new StringBuilder()).append(", prevText:'").append(prevTextValue).append("'").toString());
        }
        if(Validator.isNull(nextTextValue))
        {
            settings.append((new StringBuilder()).append(", nextText:'").append(nextTextValue).append("'").toString());
        }
        settings.append((new StringBuilder()).append(", controlNav:").append(controlNavValue).toString());
        settings.append((new StringBuilder()).append(", keyboardNav:").append(keyboardNavValue).toString());
        settings.append((new StringBuilder()).append(", pauseOnHover:").append(pauseOnHoverValue).toString());
        settings.append((new StringBuilder()).append(", manualAdvance:").append(manualAdvanceValue).toString());
        settings.append((new StringBuilder()).append(", captionOpacity:").append(opacityValue).toString());
        return settings.toString();
    }

    public static Slide getSlide(PortletRequest request, String slideId)
        throws PortalException, SystemException
    {
        String portletResource = ParamUtil.getString(request, "portletResource");
        PortletPreferences portletPreferences = getPreference(request, portletResource);
        String values[] = portletPreferences.getValues(slideId, null);
        if(Validator.isNotNull(values))
        {
            return getSlide(slideId, values);
        } else
        {
            return new Slide();
        }
    }

    public static Long getSlideId(String slideId)
    {
        if(Validator.isNotNull(slideId))
        {
            slideId = slideId.replaceAll("slides_", "");
            return Long.valueOf(Long.parseLong(slideId));
        } else
        {
            return null;
        }
    }

    public static Slide getSlide(String slideId, String values[])
    {
        String title = values[0];
        String link = values[1];
        String desc = values[2];
        String imageUrl = values[3];
        String timeMillis = values[4];
        String order = values[5];
        Slide slide = new Slide(slideId, title, link, imageUrl, desc, timeMillis, Integer.parseInt(order));
        return slide;
    }
}
