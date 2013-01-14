package com.rcs.portlet.slider.action;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.rcs.portlet.slider.model.Slide;
import com.rcs.portlet.slider.util.SliderUtil;
import java.util.*;
import javax.portlet.*;

public class ConfigurationActionImpl
    implements ConfigurationAction
{

    private Log _log;

    public ConfigurationActionImpl()
    {
        _log = LogFactoryUtil.getLog(com/rcs/portlet/slider/action/ConfigurationActionImpl);
    }

    public String render(PortletConfig portletConfig, RenderRequest renderRequest, RenderResponse renderResponse)
        throws Exception
    {
        String cmd = renderRequest.getParameter("CMD");
        if(Validator.isNotNull(cmd))
        {
            processRequest(renderRequest, renderResponse, cmd);
        }
        return "/jsps/config/configuration.jsp";
    }

    public void processAction(PortletConfig portletConfig, ActionRequest request, ActionResponse response)
        throws Exception
    {
        String cmd = request.getParameter("CMD");
        if(Validator.isNull(cmd))
        {
            throw new Exception("exception-occurred");
        }
        try
        {
            if(cmd.equals("UPDATE"))
            {
                savePreferences(request, response);
                response.setRenderParameter("tab", "slides");
            } else
            if(cmd.equals("UPDATE_SETTINGS"))
            {
                String tab = ParamUtil.getString(request, "tab");
                if(tab != null)
                {
                    response.setRenderParameter("tab", tab);
                }
                updateSettings(request, response);
            } else
            if(cmd.equals("DELETE"))
            {
                deleteSlide(request, response);
                response.setRenderParameter("tab", "slides");
            }
            SessionMessages.add(request, "request-successfully");
        }
        catch(Exception e)
        {
            _log.error(e.getMessage());
            SessionErrors.add(request, e.getMessage());
        }
    }

    private void processRequest(PortletRequest request, PortletResponse response, String cmd)
    {
        try
        {
            if(cmd.equals("DELETE"))
            {
                deleteSlide(request, response);
            } else
            if(cmd.equals("SLIDE_MOVE_DOWN"))
            {
                updateSlideOrder(request, response, true);
            } else
            if(cmd.equals("SLIDE_MOVE_UP"))
            {
                updateSlideOrder(request, response, false);
            }
            SessionMessages.add(request, "request-successfully");
        }
        catch(Exception e)
        {
            _log.error(e.getMessage());
            SessionErrors.add(request, e.getMessage());
        }
    }

    private void updateSlideOrder(PortletRequest request, PortletResponse response, boolean slideDown)
        throws Exception
    {
        String slideIdParam = ParamUtil.getString(request, "slideId", null);
        _log.info((new StringBuilder()).append("updateSlideOrder - slideId=").append(slideIdParam).toString());
        if(Validator.isNull(slideIdParam))
        {
            throw new IllegalArgumentException("exception-occurred");
        }
        Long slideId = SliderUtil.getSlideId(slideIdParam);
        List slides = SliderUtil.getSlides(request, response);
        Iterator i$ = slides.iterator();
        do
        {
            if(!i$.hasNext())
            {
                break;
            }
            Slide slide = (Slide)i$.next();
            if(slide.getId() != slideId.longValue())
            {
                continue;
            }
            Slide nextSlide = null;
            int indexOf = slides.indexOf(slide);
            int nextSlideIndex = slideDown ? indexOf + 1 : indexOf - 1;
            if(nextSlideIndex >= 0 && nextSlideIndex < slides.size())
            {
                nextSlide = (Slide)slides.get(nextSlideIndex);
                if(nextSlide != null)
                {
                    switchSlide(request, response, slide, nextSlide);
                }
            }
            break;
        } while(true);
    }

    private void switchSlide(PortletRequest request, PortletResponse response, Slide slide, Slide nextSlide)
        throws Exception
    {
        int slideOrder = slide.getOrder();
        int nextSlideOrder = nextSlide.getOrder();
        saveSlideOrder(request, response, slide, nextSlideOrder);
        saveSlideOrder(request, response, nextSlide, slideOrder);
    }

    private void updateSettings(ActionRequest actionRequest, ActionResponse actionResponse)
        throws Exception
    {
        _log.info("updateAnimation - start");
        String portletResource = ParamUtil.getString(actionRequest, "portletResource");
        PortletPreferences preferences = PortletPreferencesFactoryUtil.getPortletSetup(actionRequest, portletResource);
        Enumeration parameterNames = actionRequest.getParameterNames();
        do
        {
            if(!parameterNames.hasMoreElements())
            {
                break;
            }
            String param = (String)parameterNames.nextElement();
            if(param.startsWith("settings"))
            {
                String value = ParamUtil.get(actionRequest, param, "");
                _log.info((new StringBuilder()).append("save param=").append(param).append(", value=").append(value).toString());
                preferences.setValue(param, value);
            }
        } while(true);
        preferences.store();
        _log.info("updateAnimation - end");
    }

    private void deleteSlide(PortletRequest request, PortletResponse response)
        throws Exception
    {
        String slideId = ParamUtil.getString(request, "slideId", null);
        _log.info((new StringBuilder()).append("deleteSlide - slideId=").append(slideId).toString());
        if(Validator.isNotNull(slideId))
        {
            String portletResource = ParamUtil.getString(request, "portletResource");
            PortletPreferences preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
            preferences.reset(slideId);
            preferences.store();
        } else
        {
            throw new Exception("invalid-slide");
        }
    }

    private void savePreferences(ActionRequest request, ActionResponse response)
        throws Exception
    {
        String portletResource = ParamUtil.getString(request, "portletResource");
        PortletPreferences portletPreferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
        String slideId = ParamUtil.getString(request, "slideId", null);
        String title = ParamUtil.getString(request, "title", "");
        String link = ParamUtil.getString(request, "link", "");
        String desc = ParamUtil.getString(request, "desc", "");
        String image = ParamUtil.getString(request, "image", "");
        if(_log.isDebugEnabled())
        {
            _log.debug((new StringBuilder()).append("savePreferences - slideId=").append(slideId).append(", title=").append(title).append(", link=").append(link).append(", desc=").append(desc).append(", image=").append(image).toString());
        }
        verifyParameter(title, link, image);
        int order = SliderUtil.getLastSlide(request, response);
        if(_log.isDebugEnabled())
        {
            _log.debug((new StringBuilder()).append("savePreferences - order=").append(order).toString());
        }
        String values[] = {
            title, link, desc, image, String.valueOf((new Date()).getTime()), String.valueOf(order)
        };
        if(slideId == null || "".equals(slideId.trim()))
        {
            slideId = (new StringBuilder()).append("slides_").append(String.valueOf((new Date()).getTime())).toString();
        }
        if(_log.isDebugEnabled())
        {
            _log.debug((new StringBuilder()).append("slideId=").append(slideId).toString());
        }
        portletPreferences.setValues(slideId, values);
        portletPreferences.store();
        response.setRenderParameter("slideParamId", slideId);
        response.setRenderParameter("slideImage", image);
    }

    private void saveSlideOrder(PortletRequest request, PortletResponse response, Slide slide, int order)
        throws Exception
    {
        String portletResource = ParamUtil.getString(request, "portletResource");
        PortletPreferences portletPreferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
        String slideId = (new StringBuilder()).append("slides_").append(slide.getId()).toString();
        String title = slide.getTitle();
        String link = slide.getLink();
        String desc = slide.getDesc();
        String image = slide.getImageUrl();
        _log.info((new StringBuilder()).append("savePreferences - slideId=").append(slideId).append(", title=").append(title).append(", link=").append(link).append(", desc=").append(desc).append(", image=").append(image).append(", order=").append(order).toString());
        String values[] = {
            title, link, desc, image, String.valueOf(slide.getTimeMillis()), String.valueOf(order)
        };
        portletPreferences.setValues(slideId, values);
        portletPreferences.store();
    }

    private void verifyParameter(String title, String link, String image)
    {
        if(Validator.isNull(title))
        {
            throw new IllegalArgumentException("title-invalid");
        }
        if(Validator.isNull(image))
        {
            throw new IllegalArgumentException("image-invalid");
        } else
        {
            return;
        }
    }
}
