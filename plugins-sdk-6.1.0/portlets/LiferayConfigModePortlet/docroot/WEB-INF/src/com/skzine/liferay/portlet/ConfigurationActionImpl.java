package com.skzine.liferay.portlet;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.portal.kernel.portlet.BaseConfigurationAction;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

public class ConfigurationActionImpl extends BaseConfigurationAction {

	@Override
	public void processAction(PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
			throws Exception {
		System.out.println("***Inside processaction of Config****");
		String name = actionRequest.getParameter("Name");
		PortletPreferences preferences = actionRequest.getPreferences();

		String portletResource = ParamUtil.getString(actionRequest, "portletResource");

		if (Validator.isNotNull(portletResource)) {
			preferences = PortletPreferencesFactoryUtil.getPortletSetup(actionRequest, portletResource);
		}
		
		preferences.setValue("pref-name", name);
		preferences.store();
		
		// TODO Auto-generated method stub
		super.processAction(portletConfig, actionRequest, actionResponse);
	}

	@Override
	public String render(PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
			throws Exception {
		System.out.println("***Inside render of Config****");
		// TODO Auto-generated method stub
		return "/WEB-INF/jsp/Configuration.jsp";

	}

}
