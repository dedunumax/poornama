<%@ include file="../template/headerWithNav.jsp" %>
<%
  RoleValidator roleValidator = new RoleValidator();
  List<String> roles = new ArrayList<String>();
  roles.add("admin");
  roles.add("manager");
  //roles.add("accountant");
  //roles.add("clerk");
  roleValidator.validate(session, request, response, roles);
%>
<style scoped>

</style>

${salaryTable}

<br />

<a href="${pageContext.request.contextPath}">
  <button type="button" class="btn btn-default"><span class="glyphicon glyphicon-home"></span> Home</button>
</a>

<script>

</script>
<%@ include file="../template/footer.jsp" %>