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
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@page import="com.liferay.portal.kernel.util.HttpUtil"%>
<%@ page import="lu.globalepic.dao.BlogDAO"%>
<%@ page import="lu.globalepic.dao.LiferayDAO"%>
<%@ page import="lu.globalepic.util.BlogsWrapper"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Map"%>


<portlet:defineObjects /> 


<script src="/EPIC-theme/js/tabcontent.js" type="text/javascript"></script>
<link href="/EPIC-theme/css/tabcontent.css" rel="stylesheet" type="text/css" />

<%
	PortletPreferences preferences = renderRequest.getPreferences();	
	String portletResource = ParamUtil.getString(request, "portletResource");	
	if (Validator.isNotNull(portletResource)) {
		preferences = PortletPreferencesFactoryUtil.getPortletSetup(request, portletResource);
	}
	Map blogsMap =  LiferayDAO.getAllBlogs( Integer.parseInt( preferences.getValue("display_size","0") ));	
	pageContext.setAttribute("recentMap", blogsMap.get("recentMap") );
	pageContext.setAttribute("popularMap", blogsMap.get("popularMap") );
	pageContext.setAttribute("commentMap", blogsMap.get("commentMap") );
	pageContext.setAttribute("ratedMap", blogsMap.get("ratedMap") );

	
%>	
<c:set var="author" scope="session" value="<%=Boolean.parseBoolean(preferences.getValue(\"newsAuthor\",\"false\") )%>"/>
<c:set var="displayDate" scope="session" value="<%=Boolean.parseBoolean(preferences.getValue(\"displayDate\",\"false\") )%>"/>
<c:set var="recentNews" scope="session" value="<%=Boolean.parseBoolean( preferences.getValue(\"recentNews\",\"false\") )%>"/>
<c:set var="popularNews" scope="session" value="<%=Boolean.parseBoolean( preferences.getValue(\"popularNews\",\"false\") )%>"/>
<c:set var="commentedNews" scope="session" value="<%=Boolean.parseBoolean( preferences.getValue(\"commentedNews\",\"false\") )%>"/>
<c:set var="ratedNews" scope="session" value="<%=Boolean.parseBoolean( preferences.getValue(\"ratedNews\",\"false\") )%>"/>
<c:set var="MAX_SIZE" scope="session" value="<%=Integer.parseInt( preferences.getValue(\"display_size\",\"0\") )%>"/>



<div class="htabs">
	<ul class="tabs" title="true">
		<c:if test="${popularNews}"><li id="liPopular" ><a href="#" title="view1" >Most&nbsp;Popular</a></li></c:if>		
		<c:if test="${recentNews}"><li id="liRecent" ><a href="#" title="view2">Most&nbsp;Recent</a></li> </c:if>
		<c:if test="${commentedNews}"><li id="liCommented" ><a href="#" title="view3">Most&nbsp;Commented</a></li></c:if>		
		<c:if test="${ratedNews}"><li id="liRated" ><a href="#" title="view4">Most&nbsp;Rated</a></li></c:if>		
	</ul>


<div class="tabcontents portlet-blogs-aggregator portlet-blogs">
  <c:if test="${popularNews}">
<div class="tabcontent" id="view1">  

	 <c:forEach var="blogEntry" items="${popularMap}" begin="0" end="${MAX_SIZE-1}" step="1">	 
			  <div class="entry-info">
			   		<a href="<c:out value="/web/apps/news/-/blogs/${blogEntry.urlTitle}" />"  >
			   			<span class="entry-title"><c:out value="${blogEntry.title}" /> </span>
			   		 </a> 			
					<c:if test="${displayDate==true}"> 
					 			<div class="entry-date"> <c:out value="${blogEntry.createdDate}" />  &nbsp;|&nbsp; views : ${blogEntry.viewsCount} </div>
					</c:if>
					<c:if test="${author== true}"> 
			 			 	<div class="authorSpan">
			   						By <span class="entry-author" style="float:none;"><c:out value="${blogEntry.userName}" /> </span>
			   				</div>
			   		</c:if>
			   	</div>	
	   </c:forEach>	  

</div>
 </c:if>
<c:if test="${recentNews}">
<div class="tabcontent" id="view2">
 <c:forEach var="blogEntry" items="${recentMap}" begin="0" end="${MAX_SIZE-1}" step="1">		 
			  <div class="entry-info">
			   		<a href="<c:out value="/web/apps/news/-/blogs/${blogEntry.urlTitle}" />"  >
			   			<span class="entry-title"><c:out value="${blogEntry.title}" /> </span>
			   		 </a> 			
					<c:if test="${displayDate==true}"> 
					 			<div class="entry-date"> <c:out value="${blogEntry.createdDate}" /> </div>
					</c:if>
					<c:if test="${author== true}"> 
			 			 	<div class="authorSpan">
			   						By <span class="entry-author" style="float:none;"><c:out value="${blogEntry.userName}" /> </span>
			   				</div>
			   		</c:if>
			   	</div>	
	   </c:forEach>	
	
</div>
</c:if>
<c:if test="${commentedNews}">
<div class="tabcontent" id="view3" >	

	   <c:forEach var="blogEntry" items="${commentMap}" begin="0" end="${MAX_SIZE-1}" step="1">	 
			  <div class="entry-info">
			   		<a href="<c:out value="/web/apps/news/-/blogs/${blogEntry.urlTitle}" />"  >
			   			<span class="entry-title"><c:out value="${blogEntry.title}" /> </span>
			   		 </a> 	
			   		 <div>comments : <c:out value="${blogEntry.comments}" /> </div>		
					<c:if test="${displayDate==true}"> 
					 			<div class="entry-date"> <c:out value="${blogEntry.createdDate}" /> </div>
					 	
					</c:if>
					
					<c:if test="${author== true}"> 
			 			 	<div class="authorSpan">
			   						By <span class="entry-author" style="float:none;"><c:out value="${blogEntry.userName}" /> </span>
			   				</div>
			   				</c:if>
			   	</div>	
	   </c:forEach>	

</div>
</c:if>

<div class="tabcontent" id="view4">
<c:if test="${ratedNews}">
  <c:forEach var="blogEntry" items="${ratedMap}" begin="0" end="${MAX_SIZE-1}" step="1">		 
			  <div class="entry-info">
			   		<a href="<c:out value="/web/apps/news/-/blogs/${blogEntry.urlTitle}" />"  >
			   			<span class="entry-title"><c:out value="${blogEntry.title}" /> </span>
			   		 </a> 	
			   		 	
			   		  <div> Rating : <c:out value="${blogEntry.rating}" /> </div>			
					<c:if test="${displayDate==true}"> 
					 			<div class="entry-date"> <c:out value="${blogEntry.createdDate}" /> </div>
					</c:if>
					
					<c:if test="${author== true}"> 
			 			 	<div class="authorSpan">
			   						By <span class="entry-author" style="float:none;"><c:out value="${blogEntry.userName}" /> </span>
			   				</div>
			   		</c:if>
			   	</div>	
	   </c:forEach>	
	   </c:if>
</div>

</div>
</div>
