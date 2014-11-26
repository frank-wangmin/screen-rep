<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<% 
String cxp =  request.getContextPath();
request.setAttribute("cxp",cxp);
%>

<script>
var vPath = "<c:out value="${cxp}"/>";
var contextPath = "<c:out value="${cxp}"/>";
var opPath = "<c:out value="${cxp}/js/operamasks-ui-2.0/themes/default/images/"/>";
</script>