
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>


<portlet:defineObjects />


<liferay-ui:input-editor height="200"  name="headerFckEditor" width="200">
</liferay-ui:input-editor>

<script type="text/javascript">
function <portlet:namespace/>initEditor()
{
    return "Good Morning!!!" ;
}


</script>
