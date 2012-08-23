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
<%@ include file="/AjaxUtils.jsp"%>
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
	String param_videoWidth = preferences.getValue("pref-videoWidth","500");
	String param_videoHeight = preferences.getValue("pref-videoHeight","340");
	System.out.println( " b4 param_URL : " +param_URL );

	System.out.println( " param_URL : " +param_URL );
	
	
	
	String videoW = ""+ ( Integer.parseInt( param_videoWidth ) - 275 );
	
	int totalWidth= Integer.parseInt( param_videoWidth )+275;
%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<link rel="stylesheet" type="text/css" media="all" href="/Video-Display-portlet/css/clearfix.css" />
	<link rel="stylesheet" type="text/css" media="all" href="/Video-Display-portlet/css/style.css" />
	<link rel="stylesheet" type="text/css" media="all" href="/Video-Display-portlet/css/fonts.css"  />
	<link rel="stylesheet" type="text/css" media="all" href="/Video-Display-portlet/css/jquery.dualSlider.0.2.css" />

	
	<script src="/rcs-slider-portlet/js/jquery-1.7.1.min.js" type="text/javascript" charset="utf-8"></script>
	<!-- Require EasyJQuery After JQuery -->
<script type="text/javascript" language="Javascript" src="http://api.easyjquery.com/easyjquery.js"></script>
<script type="text/javascript" language="Javascript">
    // 1. Your Data Here
    function my_callback(json) {
        alert("IP :" + json.IP + " nCOUNTRY: " + json.COUNTRY);
    }

    function my_callback2(json) {
        // more information at http://api.easyjquery.com/test/demo-ip.php
        //alert("IP :" + json.IP + " nCOUNTRY: " + json.COUNTRY + " City: " + json.cityName + " regionName: " + json.regionName);
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
	  			
	  			
}
    
    


</script>
	<script src="/Video-Display-portlet/js/jquery.easing.1.3.js" type="text/javascript"></script>
	<script src="/Video-Display-portlet/js/jquery.timers-1.2.js" type="text/javascript"></script>
	<script src="/Video-Display-portlet/js/jquery.dualSlider.0.3.js" type="text/javascript"></script>

   
	<script type="text/javascript">
		var cWidth =0;
		
		jQuery(document).ready(function() 
		{
			
			
			var sourceURL ="<%=param_URL%>";
			var urlArray = sourceURL.split(",");		
			var stringDIV ="";
			var sText="";
			
			for( var i=0;i<urlArray.length;i++ )
			{
				var myURL = urlArray[i];				
				stringDIV +="<div class=\"item item_1\">";
				stringDIV +="<iframe  id=\"videoFrame_"+i+"\" onClick=\"clic(this.id)\" width=\"604\" onclick=\"pausse()\" height=\"340\" src=\""+myURL+"\" frameborder=\"0\" allowfullscreen></iframe>";
				stringDIV +="</div>";				
				sText+="<div class=\"detail\"><h2 class=\"Lexia-Bold\"><a href=\"#\"> WFP</a> <br>Fighting hunger worldwide</h2>";
				sText+="<a href=\"#\" title=\"Read more\" class=\"more\">Read more</a></div>";
			}
			
			
			jQuery(".wrappers .backgrounds").html( stringDIV );
			jQuery(".details_wrapper .details").html( sText );
			
			jQuery(".wrappers .backgrounds").click(function()
			{
				
					alert('clicked');
			});
			
				
			var outW = jQuery(".wrappers").width();		
			jQuery(".carousel").css("width",outW+"px" );
			jQuery(".carousel .backgrounds .item").css("width",outW+"px" );			
			var totalWidth= jQuery(".carousel").width();	
			
			cWidth = jQuery(".carousel").width();
			totalWidth = cWidth-250;			
			jQuery("iframe").attr("width",totalWidth );
			
			jQuery("iframe").bind('mouseover',function(){
				 jQuery(".pause", myobj).hide();
                jQuery(".play", myobj).show();
                jQuery("body").stopTime("autoScroll");
			});
			
		 
		var mySlider=	jQuery(".carousel").dualSlider({
				auto:true,
				autoDelay: 6000,
				easingCarousel: "swing",
				easingDetails: "easeOutBack",
				durationCarousel: 1000,
				durationDetails: 600
			});
			jQuery("iframe").bind('mouseout',function()
				{
			
				
				});
			
		});
		function clic(id)
		{
   			 alert(id);
		}
		
		
	</script>
	
	

	
	<div class="wrappers clearfix">
		

		
		
		<div class="carousel clearfix">

			<div class="panel">
				
				<div class="details_wrapper">
					
					<div class="details">
					
						
						
					
						
					
					</div><!-- /details -->
					
				</div><!-- /details_wrapper -->
				
				<div class="paging">
					<div id="numbers"></div>
					<a href="javascript:void(0);" class="previous" title="Previous" >Previous</a>
					<a href="javascript:void(0);" class="next" title="Next">Next</a>
				</div><!-- /paging -->
				
				<a href="javascript:void(0);" class="play" title="Turn on autoplay">Play</a>
				<a href="javascript:void(0);" class="pause" title="Turn off autoplay">Pause</a>
				
			</div><!-- /panel -->
	
			<div class="backgrounds">
				
				
				
			</div><!-- /backgrounds -->
			
			
		</div><!-- /carousel --> 
		
		
		
		
	</div><!-- /wrapper -->
	
<!-- <script type="application/javascript" src="http://jsonip.appspot.com/?callback=<portlet:namespace />getip"></script> -->
	