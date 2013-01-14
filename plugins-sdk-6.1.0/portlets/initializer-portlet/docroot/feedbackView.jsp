

<script  type="text/javascript" src="<%=renderRequest.getContextPath()%>/js/jquery.lightbox_me.js"></script>
<link href="<%=renderRequest.getContextPath()%>/css/feedback.css"  rel="stylesheet" media="screen" type="text/css" />

 <!--  <a href="#" id="try-1" class="try">Try it!</a>--> 


  <div id="feedback" style="display: none; left: 50%; margin-left: -223px; z-index: 1002; position: fixed; top: 50%; margin-top: -159px;">
                <h3 class="" id="see_id">Feedback</h3>
                <span>Please provide your valuable feeback using the form below</span>
                <div id="feedback_form">
                 <!--   <label><strong>Email : </strong> <input class="sprited"></label>-->
                    <label><strong>Feedback Type : </strong> <br/> <select class="fbackType" id="fbackType"  >
                    												<option value="-1">Select Type </option>
                    												<option value="1">Suggestion </option>
                    												<option value="2">Request </option>
                    												<option value="3">Bug/Issue </option>
                    												<option value="4">Ideas</option>
                    												 </select></label>
                    <label><strong>Title : </strong> <br/> <input  id="feedbackTitle" style="width:362px;margin:5px;height:24px;font-size:12px;" /></label>
                    <label><strong>Description : </strong> <br/> <textarea  id="desc" style="height:80px;width:362px;margin:5px;font-size:12px;" > </textarea></label>
                    <div id="actions">
                        <a href="#" id="cancel" style="display:none;" class="close form_button orange">Cancel</a>
                        <a href="#" id="log_in" class="form_button orange" >Submit</a>
                    </div>
                </div>
                <h3 class="" id="left_out">Thank you</h3>
                <span>EPIC support team</span>
                <a href="#" class="close sprited" id="close_x">close</a>
  </div>

<%
	String userName = user.getFirstName() +" "+user.getLastName(); 
%>
<script type="text/javascript">
jQuery(document).ready( function()
{
	function launch() 
	{
		jQuery('#feedback').lightbox_me({centered: true, onLoad: function() { jQuery('#feedback').find('input:first').focus()}});
	}
	jQuery('#try-1').click(function(e)
	{
		jQuery("#feedback").lightbox_me({centered: true, onLoad: function()
		{
			jQuery("#feedback").find("input:first").focus();
			}					
		}); 
		e.preventDefault();
	});			
});
function <portlet:namespace />resetForm()
{
					jQuery("#feedback").css("display","none"); 
					jQuery("#fbackType option[value='-1']").attr("selected","selected");
					jQuery("#desc").val("");
					jQuery("#feedbackTitle").val("");

}
jQuery("#close_x").click(function(){ <portlet:namespace />resetForm(); });

jQuery("#log_in").click(function()
{
			
				var fbackType = jQuery("#fbackType option:selected").val();
				if( fbackType!= '-1' )
				{
					var feedbackTitle = jQuery("#feedbackTitle").val();
					if( feedbackTitle!=null&& feedbackTitle!="" )
					{
						var desc = jQuery("#desc").val();
						if( desc!=null&& desc!="" ) <portlet:namespace />sendEmail();
						else alert(" Please enter description");
					}
					else alert(" Please enter title");	
						
				}
				else alert(" Please select feedback type");
				

	});
	

function <portlet:namespace />sendEmail()
{
				var feedbackType = jQuery("#fbackType option:selected").text();
				var feedbackTitle  = jQuery("#feedbackTitle").val();
				var description  = jQuery("#desc").val();
				var myurl = "<br> User's Page : "+window.location;
				var browserr = "<br>Browser Name : "+navigator.appName;
				browserr += "<br>Browser Version : " +navigator.appVersion;
				browserr += "<br>Cookies Enabled : " +navigator.cookieEnabled;
				browserr += "<br>Operating System : "+navigator.platform ;
												
				var <portlet:namespace />ajax = new AjaxUtils();
	  			var parameters = {} ;	  			
	  			parameters["cmd"]= "sendFeedback";
	  			parameters["feedbackType"]= feedbackType;
	  			parameters["feedbackTitle"]= feedbackTitle;
	  			parameters["description"]= description;
	  			parameters["browserr"]= browserr;
	  			parameters["myurl"]= myurl;
	  			parameters["userName"]= "<%=userName%>";
	  			<portlet:namespace />ajax.parameters = parameters;
	  			<portlet:namespace />ajax.actionRequest("get" );
	  			
	  			<portlet:namespace />resetForm();
	  			
	  			
}
    
</script>

