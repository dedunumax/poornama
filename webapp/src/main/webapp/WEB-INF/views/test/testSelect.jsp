<%@ page import="com.poornama.logic.object.EmployeeAttendanceLogic" %>
<%@ include file="../template/headerWithNav.jsp" %>
<style scoped>

</style>
<%
    EmployeeAttendanceLogic employeeAttendanceLogic = new EmployeeAttendanceLogic();
    out.println(employeeAttendanceLogic.getEmployeeAttendanceTable("04/27/2014"));
%>


<script>

</script>
<%@ include file="../template/footer.jsp" %>