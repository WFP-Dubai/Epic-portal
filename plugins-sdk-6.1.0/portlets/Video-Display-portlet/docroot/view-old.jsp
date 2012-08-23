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
	System.out.println( " b4 param_URL : " +param_URL );

	System.out.println( " param_URL : " +param_URL );
	
	
	
	String videoW = ""+ ( Integer.parseInt( param_videoWidth ) - 275 );
	
	int totalWidth= Integer.parseInt( param_videoWidth )+275;
%>
	<link rel="stylesheet" type="text/css" media="all" href="/Video-Display-portlet/css/clearfix.css" />
	<link rel="stylesheet" type="text/css" media="all" href="/Video-Display-portlet/css/style.css" />
	<link rel="stylesheet" type="text/css" media="all" href="/Video-Display-portlet/css/fonts.css"  />
	<link rel="stylesheet" type="text/css" media="all" href="/Video-Display-portlet/css/jquery.dualSlider.0.2.css" />

	
	<script src="/rcs-slider-portlet/js/jquery-1.3.2.min.js" type="text/javascript"></script>
	
	<script src="/Video-Display-portlet/js/jquery.easing.1.3.js" type="text/javascript"></script>
	<script src="/Video-Display-portlet/js/jquery.timers-1.2.js" type="text/javascript"></script>
	<script src="/Video-Display-portlet/js/jquery.dualSlider.0.3.js" type="text/javascript"></script>

   
	<script type="text/javascript">
		
		var cWidth =0;
		
		jQuery(document).ready(function() {
			
			var outW = jQuery(".wrappers").width();
		
			jQuery(".carousel").css("width",outW+"px" );
			
			var totalWidth= jQuery(".carousel").width();
			
			cWidth = outW;
			totalWidth = outW-275;	
			
			jQuery("iframe").attr("width",totalWidth );
			cWidth = totalWidth;
		
			/*var ua = jQuery.browser;
			
			if ( ua.mozilla )
			{
				jQuery("iframe").attr("width",totalWidth );
				cWidth = totalWidth;
			}
			else
			{
				jQuery("iframe").attr("width",outW );

			}*/

			
			//alert( cWidth );
			jQuery("iframe").attr("height","340" );
			//jQuery(".carousel").attr("width",cWidth );
			//jQuery(".wrappers").attr("width",""+ ( parseInt(cWidth)+275 ) );
			
			jQuery(".carousel").dualSlider({
				auto:true,
				autoDelay: 6000,
				easingCarousel: "swing",
				easingDetails: "easeOutBack",
				durationCarousel: 1000,
				durationDetails: 600
			});

			
			
		});

		 function pausse()
			{
				 jQuery(".pause", myobj).hide();
                jQuery(".play", myobj).show();
                jQuery("body").stopTime("autoScroll");
				alert(myobj);

			}
		
		
	</script>
	
	

	
	<div class="wrappers clearfix">
		
		

		
		<div class="carousel clearfix">

			<div class="panel">
				
				<div class="details_wrapper">
					
					<div class="details">
					
						<div class="detail">
							<h2 class="Lexia-Bold"><a href="#"> World Food Programme</a> <br>Fighting hunger worldwide </h2>
							<a href="#" title="Read more" class="more">Read more</a>
						</div><!-- /detail -->

						<div class="detail">
							<h2 class="Lexia-Bold"><a href="#">Niger:</a><br>  Food For Mothers & Children In Niger  </h2>
							<a href="#" title="Read more" class="more">Read more</a>
						</div><!-- /detail -->

						<div class="detail">
							<h2 class="Lexia-Bold"><a href="#">Niger:</a><br>  Getting By On Leaves And Berries </h2>
							<a href="#" title="Read more" class="more">Read more</a>
						</div><!-- /detail -->

						<div class="detail">
							<h2 class="Lexia-Bold"><a href="#">Mali</a> <br> A Grain Of Hope For Refugees From Mali  </h2>
							<a href="#" title="Read more" class="more">Read more</a>
						</div><!-- /detail -->

						<div class="detail">
							<h2 class="Lexia-Bold"><a href="#">Chad:</a> <br>  hunger crisis deepens in one of Africa's remotest regions   </h2>
							<a href="#" title="Read more" class="more">Read more</a>
						</div><!-- /detail -->
							<div class="detail">
							<h2 class="Lexia-Bold"><a href="#">Sahel</a> <br> Food Crisis In Africa's Sahel Region    </h2>
							<a href="#" title="Read more" class="more">Read more</a>
						</div><!-- /detail -->
						
						
						
						
						
					
				
						
					
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
				
				<div class="item item_1" onclick="pausse()" >
					<iframe   height="340" src="<%=param_URL%>" frameborder="0" allowfullscreen></iframe>
				</div><!-- /item -->
				
				<div class="item item_1" onclick="pausse()" >
				
					<iframe  onclick="pausse()" height="340" src="http://www.youtube.com/embed/aaTZ05mSfBo?feature=player_detailpage" frameborder="0" allowfullscreen></iframe>
				</div><!-- /item -->
				
				<div class="item item_1">
					<iframe onclick="pausse()"  height="340" src="http://www.youtube.com/embed/w2xEnDDFuPU?feature=player_detailpage" frameborder="0" allowfullscreen></iframe>
				</div><!-- /item -->

				
				<div class="item item_1">
					<iframe onclick="pausse()"  height="340" src="http://www.youtube.com/embed/e8ADO-4mPro?feature=player_detailpage" frameborder="0" allowfullscreen></iframe>
				</div><!-- /item -->


				<div class="item item_1">
					<iframe onclick="pausse()"  height="340" src="http://www.youtube.com/embed/cXAleVyEFis?feature=player_detailpage" frameborder="0" allowfullscreen></iframe>
				</div><!-- /item -->

				
				<div class="item item_1">
				
					<iframe  onclick="pausse()" height="340" src="http://www.youtube.com/embed/zizbho8QIfg?feature=player_detailpage" frameborder="0" allowfullscreen></iframe>
				</div><!-- /item -->
				
			</div><!-- /backgrounds -->
			
			
		</div><!-- /carousel --> 
		
	
		
		
		
	</div><!-- /wrapper -->