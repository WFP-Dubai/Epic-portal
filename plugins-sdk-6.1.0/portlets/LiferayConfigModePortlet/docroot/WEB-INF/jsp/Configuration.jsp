<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>

<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portal.kernel.util.Validator" %>
<%@ page import="com.liferay.portlet.PortletPreferencesFactoryUtil" %>
<%@ page import="javax.portlet.PortletPreferences" %>
<%@ page import="javax.portlet.RenderRequest" %>


<portlet:defineObjects />
<%
	PortletPreferences preferences = renderRequest.getPreferences();
	
	String portletResource = ParamUtil.getString(request, "portletResource");
	
	if (Validator.isNotNull(portletResource)) {
		preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
	}

	String param_name = preferences.getValue("pref-name","");
%>
<form action='<liferay-portlet:actionURL portletConfiguration="true" />' method="post" name="<portlet:namespace />fm" >
<table>
	<tr>
		<td>Name:</td>
		<td>
			<%
				if (preferences != null) {
			%>
				<input class="lfr-input-text" name="Name" type="text" value="<%=param_name %>" />
			<%
				} else {
			%>
				<input class="lfr-input-text" name="Name" type="text" value="" />
			<%
				}
			%>
		</td>
	</tr>
	<tr>
		<td><input type="submit" value='<liferay-ui:message key="save" />' /></td>
		<td><input type="button" value='<liferay-ui:message key="cancel" />' /></td>
	</tr>
</table>

</form>
