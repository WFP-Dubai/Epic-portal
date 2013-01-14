
<script type="text/javascript">

	function <portlet:namespace />saveNewsPreferences()
	{	
		var <portlet:namespace />ajax = new AjaxUtils();
	  	var parameters = {} ;	  			
	  	parameters["cmd"]= "saveNewsPreferences";
	  	parameters["display_size"]= jQuery("select#news_display_size option:selected").val();
	  	parameters["newsAuthor"]= jQuery("input#newsAuthor").is(":checked");
	  	parameters["recentNews"]= jQuery("input#recentNews").is(":checked");
	  	parameters["popularNews"]= jQuery("input#popularNews").is(":checked");
	  	parameters["commentedNews"]= jQuery("input#commentedNews").is(":checked");
	  	parameters["ratedNews"]= jQuery("input#ratedNews").is(":checked");	 
	  	parameters["displayDate"]= jQuery("input#displayDate").is(":checked"); 		
	  	<portlet:namespace />ajax.parameters = parameters;
	  	<portlet:namespace />ajax.actionRequest("post" );
	  	
		jQuery("#news_statusMessage").css("display","block");
		
	}
	if( "<%=Boolean.parseBoolean( preferences.getValue("recentNews","false") )%>" == "true" ) jQuery("#recentNews").attr("checked","checked");
	if( "<%=Boolean.parseBoolean(preferences.getValue("popularNews","false") )%>" == "true" ) jQuery("#popularNews").attr("checked","checked");
	if( "<%=Boolean.parseBoolean(preferences.getValue("commentedNews","false") )%>" == "true" ) jQuery("#commentedNews").attr("checked","checked");
	if( "<%=Boolean.parseBoolean(preferences.getValue("ratedNews","false") )%>" == "true" ) jQuery("#ratedNews").attr("checked","checked");
	if( "<%=Boolean.parseBoolean(preferences.getValue("newsAuthor","false") )%>" == "true" ) jQuery("#newsAuthor").attr("checked","checked");
	if( "<%=Boolean.parseBoolean(preferences.getValue("displayDate","false") )%>" == "true" ) jQuery("#displayDate").attr("checked","checked");
	jQuery("#news_display_size option[value='<%=preferences.getValue("display_size","3")%>']").attr("selected","selected");
	
</script>

