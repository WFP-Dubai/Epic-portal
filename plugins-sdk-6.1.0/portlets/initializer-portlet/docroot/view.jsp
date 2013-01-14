


<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>


<%@ include file="/init.jsp"%>
<%@ include file="/AjaxUtils.jsp"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.io.IOException"%>

<%@ page import="lu.globalepic.model.GeoIPUsersMapDAO"%>

<%@ page import="lu.maxmind.geoip.CountryCodes"%>
<%@ page import="lu.globalepic.model.MarkVO"%>
<%@ page import="lu.maxmind.geoip.Location"%>
<%@ page import="com.liferay.portal.theme.ThemeDisplay"%>
<%@ page import="com.liferay.portal.model.User"%>

<%@ page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@ page import="com.liferay.portal.util.PortalUtil"%>
<%@ page import="javax.servlet.http.HttpServletRequest"%>

<%@ page import="com.liferay.portal.model.User"%>
<%@ page import="com.liferay.portal.theme.ThemeDisplay"%>
<%@ page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@ page import="javax.portlet.PortletSession"%>


<%@ page import="lu.maxmind.geoip.CountryCodes"%>
<%@ page import="java.util.Map"%>

<liferay-theme:defineObjects />
<portlet:defineObjects />

<script src="/EPIC-theme/js/jquery-1.7.1.min.js" type="text/javascript"></script>
<!-- Require EasyJQuery After JQuery -->
<script type="text/javascript"  src="<%=renderRequest.getContextPath()%>/js/easyjquery.js"></script>
<script type="text/javascript">


    // 1. Your Data Here
    function my_callback(json) {
        alert("IP :" + json.IP + " nCOUNTRY: " + json.COUNTRY);
    }

    function my_callback2(json) {
        // more information at http://api.easyjquery.com/test/demo-ip.php
       // alert("IP :" + json.IP + " nCOUNTRY: " + json.COUNTRY + " City: " + json.cityName + " regionName: " + json.regionName);
      		  var <portlet:namespace />ajax = new AjaxUtils();
	  			var parameters = {} ;
	  			parameters["ip"]= json.IP;
	  			parameters["cmd"]= "updateIP";
	  			<portlet:namespace />ajax.parameters = parameters;
	  			<portlet:namespace />ajax.actionRequest("get" );
    }

    // 2. Setup Callback Function
   // EasyjQuery_Get_IP("my_callback"); // fastest version
    EasyjQuery_Get_IP("my_callback2","full"); // full version
</script>



<script type="text/javascript">


function <portlet:namespace />getip(json)
{
      			//alert(json.ip); // alerts the ip address
      
      			var <portlet:namespace />ajax = new AjaxUtils();
	  			var parameters = {} ;
	  			parameters["ip"]= json.ip;
	  			parameters["cmd"]= "updateIP";
	  			<portlet:namespace />ajax.parameters = parameters;
	  			<portlet:namespace />ajax.actionRequest("get" );
	  			alert( json.ip );
	  			
}
    
    


</script>

<%
/*
 // System.out.println( " @@@ : Client IP :" + com.liferay.portal.util.PortalUtil.getHttpServletRequest(renderRequest).getRemoteAddr() );

	User user = ((ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY)).getUser();
	//System.out.println( " £££   userId : "+user.getUserId() + "email : "+user.getEmailAddress( ) );
	//PortletSession portletSession = renderRequest.getPortletSession();	
	//renderRequest.getPortletSession().setAttribute("City-"+user.getUserId(),"Dubai" , PortletSession.APPLICATION_SCOPE);
	String emailAddress = user.getEmailAddress();
	String countryName="";
	String countryCode="";
	
	 Object obj = renderRequest.getPortletSession().getAttribute("code-"+user.getUserId(),  PortletSession.APPLICATION_SCOPE);
	    
	 if( obj != null )
	 {
	    	countryCode = obj.toString();
	 }
		
	if( emailAddress!=null && !emailAddress.equals("default@liferay.com"))
	{	
	
		List<MarkVO> markList = new ArrayList<MarkVO>();
		//System.out.println("  34334343434343");
		markList = GeoIPUsersMapDAO.getUsersData( user.getUserId() );
		//System.out.println("  ERRR");
		Integer num_users = GeoIPUsersMapDAO.getNumUsers();
		
		int num_marks= markList.size();
		
		MarkVO mark = (MarkVO) markList.get(0);
		if( mark!=null)
		{
		String htmlCode = mark.getHtmlCode();
		Float longitude = mark.getLongitude();
		Float latitude = mark.getLatitude();	
    
   		//System.out.println( " countryCode  countryCode   countryCode : "+countryCode );
    
   		if( countryCode== null || countryCode=="")
    	{
    			countryCode = 	mark.getLocation().countryCode;
    			countryName = 	mark.getLocation().countryName;
   		}
	  

		
	
		}

	
	
	}
	
	*/

%>

<%@ include file="/feedbackView.jsp"%>

<!-- <script type="application/javascript" src="http://jsonip.appspot.com/?callback=<portlet:namespace />getip"></script> -->
