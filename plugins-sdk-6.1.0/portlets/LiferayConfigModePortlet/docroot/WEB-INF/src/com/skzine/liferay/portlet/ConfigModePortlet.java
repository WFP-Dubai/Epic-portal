
package com.skzine.liferay.portlet;

import javax.portlet.GenericPortlet;
import javax.portlet.ActionRequest;
import javax.portlet.RenderRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderResponse;
import javax.portlet.PortletException;
import java.io.IOException;
import javax.portlet.PortletRequestDispatcher;

/**
 * ConfigModePortlet Portlet Class
 * @author Sanjay Kannan
 */
public class ConfigModePortlet extends GenericPortlet {

	public void doView(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {

		response.setContentType("text/html");
		
	    PortletRequestDispatcher dispatcher =
	        getPortletContext().getRequestDispatcher("/WEB-INF/jsp/ConfigModePortlet_view.jsp");
	    dispatcher.include(request, response);
		
	}


	public void processAction(ActionRequest request, ActionResponse response)
			throws PortletException, IOException {

	}

}
