<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c-rt" %>
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

<script type="text/javascript" src="/EPIC-theme/js/jquery-1.7.1.min.js"></script>
<%@ include file="/AjaxUtils.jsp" %>
<%
		PortletPreferences preferences = renderRequest.getPreferences();
		String portletResource = ParamUtil.getString(request, "portletResource");

		if (Validator.isNotNull(portletResource)) {
		preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
		}
%>	
<div id="news_statusMessage" style="display: none"
					class="portlet-msg-success">Your request completed
					successfully.</div>

<form id="<portlet:namespace />newsForm" name="<portlet:namespace />newsForm" method="POST">
<span class="aui-field aui-field-choice"> <span
	class="aui-field-content"> <span
		class="aui-field-element aui-field-label-right"> <input
			type="checkbox" value="true" onclick=" " name="newsAuthor"
			id="newsAuthor" class="aui-field-input aui-field-input-choice"
			>
	</span> <label for="ratedNews" class="aui-choice-label">Author</label>
</span>
</span>
<span class="aui-field aui-field-choice"> <span
	class="aui-field-content"> <span
		class="aui-field-element aui-field-label-right"> <input
			type="checkbox" value="true" onclick=" " name="displayDate"
			id="displayDate" class="aui-field-input aui-field-input-choice"
			>
	</span> <label for="displayDate" class="aui-choice-label"> Date display</label>
</span>
</span>
<span class="aui-field aui-field-select aui-field-menu"> <span
	class="aui-field-content"> <label for="pageDelta"
		class="aui-field-label"> Maximum Items to Display </label> <span
		class="aui-field-element "> <select
			name="_86_preferences--pageDelta--" id="news_display_size"
			class="aui-field-input aui-field-input-select aui-field-input-menu">
				<option value="1">1</option>
				<option value="2">2</option>
				<option value="3">3</option>
				<option value="4">4</option>
				<option value="5">5</option>
				<option value="10" selected="">10</option>
				<option value="25">25</option>
				<option value="50">50</option>
				<option value="100">100</option>
		</select>
	</span>
</span>
</span>
<span class="aui-field aui-field-choice"> <span
	class="aui-field-content"> <span
		class="aui-field-element aui-field-label-right"> <input
			type="checkbox" value="true" onclick=" " name="recentNews"
			id="recentNews" class="aui-field-input aui-field-input-choice"
			>
	</span> <label for="recentNews" class="aui-choice-label">Recent News </label>
</span>
</span>
<span class="aui-field aui-field-choice"> <span
	class="aui-field-content"> <span
		class="aui-field-element aui-field-label-right"> <input
			type="checkbox" value="true" onclick=" " name="popularNews"
			id="popularNews" class="aui-field-input aui-field-input-choice"
			>
	</span> <label for="popularNews" class="aui-choice-label">Most Popular</label>
</span>
</span>
<span class="aui-field aui-field-choice"> <span
	class="aui-field-content"> <span
		class="aui-field-element aui-field-label-right"> <input
			type="checkbox" value="true" onclick=" " name="commentedNews"
			id="commentedNews" class="aui-field-input aui-field-input-choice"
			>
	</span> <label for="commentedNews" class="aui-choice-label">Most
			Commented </label>
</span>
</span>
<span class="aui-field aui-field-choice"> <span
	class="aui-field-content"> <span
		class="aui-field-element aui-field-label-right"> <input
			type="checkbox" value="true" onclick=" " name="ratedNews"
			id="ratedNews" class="aui-field-input aui-field-input-choice"
			>
	</span> <label for="ratedNews" class="aui-choice-label">Most Rated</label>
</span>
</span>
<input type="hidden" id="cmd" name="cmd" value="saveNewsPref" />
<div class="aui-button-holder ">
	<span class="aui-button aui-button-submit"> <span
		class="aui-button-content"> <input type="button" value="Save"
			class="aui-button-input aui-button-input-submit" onclick="<portlet:namespace />saveNewsPreferences()" />
	</span>
	</span>
</div>

</form>

<%@ include file="/scripts.jsp" %>