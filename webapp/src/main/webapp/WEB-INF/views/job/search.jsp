<%@ include file="../template/headerWithNav.jsp" %>
<%
    RoleValidator roleValidator = new RoleValidator();
    List<String> roles = new ArrayList<String>();
    roles.add("manager");
    roles.add("owner");
    roles.add("clerk");
    roleValidator.validate(session, request, response, roles);
%>
<style scoped>
</style>
<div class="row">
    <form class="navbar-form pull-right" role="search">
        <div class="input-group">
            <input type="text" class="form-control" placeholder="Search"
                   id="searchText" name="searchText">

            <div class="input-group-btn">
                <button class="btn btn-default" type="button" id="searchButton"
                        name="searchButton">
                    <i class="glyphicon glyphicon-search"></i>
                </button>
            </div>
        </div>
    </form>
</div>

<div class="row">
    <div id="tableContainer">${table}</div>
</div>

<script>

    $("#searchText").keyup(function () {
        searchJob();
    });

    $("#searchButton").click(function () {
        searchJob();
    });

    function searchJob() {
        $.ajax({
            type: 'POST',
            url: '${pageContext.request.contextPath}/job/search/' + document.getElementById('searchText').value,
            success: function (result) {
                document.getElementById("tableContainer").innerHTML = result;
            }
        });
    }

</script>

<%@ include file="../template/footer.jsp" %>
