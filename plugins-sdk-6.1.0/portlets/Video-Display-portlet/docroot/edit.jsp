<%
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
%>


<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portal.kernel.util.Validator" %>
<%@ page import="com.liferay.portlet.PortletPreferencesFactoryUtil" %>
<%@ page import="javax.portlet.PortletPreferences" %>
<%@ page import="javax.portlet.RenderRequest" %>
<%@ page import="com.liferay.portal.theme.ThemeDisplay"%>
<%@ page import="com.liferay.portal.theme.PortletDisplay"%>
<%@ page import="com.liferay.portal.kernel.util.WebKeys"%> 
<%@ page import="javax.portlet.WindowState"%>
<portlet:defineObjects />
<%
	ThemeDisplay themeDisplay= (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
	PortletDisplay portletDisplay= themeDisplay.getPortletDisplay();
	String portletId= renderRequest.getAttribute(WebKeys.PORTLET_ID).toString();

	PortletPreferences preferences = renderRequest.getPreferences();
	
	String portletResource = ParamUtil.getString(request, "portletResource");
	
	if (Validator.isNotNull(portletResource)) {
		preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
	}
	

	String param_URL = preferences.getValue("pref-videoURL","");
	String param_videoWidth = preferences.getValue("pref-videoWidth","100");
	String param_videoHeight = preferences.getValue("pref-videoHeight","340");
	
	System.out.println( " param_URL : " +param_URL );
%>

<form action='<liferay-portlet:actionURL  />' method="post" name="<portlet:namespace />fm" id="<portlet:namespace />fm" >
<table>
  <tr>
    <td class="right">Enter Video URL :</td>
    <td class="left">
    
    						
    		<%
				if (preferences != null) {
			%>
				<textarea class="lfr-input-text" id="videoURL"  name="videoURL"  rows="5" /> <%=param_URL %> </textarea>
			<%
				} else {
			%>
				<textarea class="lfr-input-text" id="videoURL"  name="videoURL" rows="1"></textarea>
			<%
				}
			%>
    						
    						
    </td>
  </tr>
  <tr>
    <td  class="right">Width :</td>
    <td class="left"><input type="text" name="videoWidth"  value="<%=param_videoWidth%>"></input></td>
  </tr>
    <tr>
    <td  class="right">Height :</td>
    <td  class="left"><input type="text" name="videoHeight"  value="<%=param_videoHeight%>"></input></td>
  </tr>
  <tr>
  <td>&nbsp;<input type="hidden" id="cmd" name="cmd" value="savePreference" ></input></td>
   <td>&nbsp;<input type="hidden" id="uniqueWidthId" name="uniqueWidth" value="" ></input>
   <input type="hidden" id="uniqueHeightId" name="uniqueHeightId" value="" >
   <input type="hidden" id="uniqueURLId" name="uniqueURLId" value="" >
   </td>
  </tr>
   <tr >
    <td  class="right"><input type="submit" value='<liferay-ui:message key="Save" />' /> </td>
    <td  class="left"><input type="submit" value='<liferay-ui:message key="Cancel" />' /> </td>
  </tr>
</table>
</form>


