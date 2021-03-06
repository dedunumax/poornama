<%@ include file="../template/headerWithNav.jsp" %>
<%
    RoleValidator roleValidator = new RoleValidator();
    List<String> roles = new ArrayList<String>();
    roles.add("manager");
    roleValidator.validate(session, request, response, roles);
%>

<style scoped>
</style>

<form id="expenseEditForm" class="form-horizontal" role="form"
      method="post"
      action="${pageContext.request.contextPath}/expense/edit/${expenseId}">
    <div class="form-group">
        <label for="expenseDate" class="col-sm-3 control-label">Date</label>

        <div class="col-sm-3">
            <input type="date" class="form-control" id="expenseDate"
                   name="expenseDate" placeholder="" value="${expenseDate}"/>
        </div>
    </div>
    <div class="form-group">
        <label for="tagList" class="col-sm-3 control-label">Tags</label>

        <div class="col-sm-6">
            <input type="text" class="form-control" id="tagList" name="tagList"
                   value="${tagValue}"/> <input type="hidden" id="tagString"
                                                name="tagString"/>
        </div>
    </div>
    <div class="form-group">
        <label for="description" class="col-sm-3 control-label">Description</label>

        <div class="col-sm-9">
			<textarea class="form-control" id="description" name="description"
                      rows="3" placeholder="" required>${description}</textarea>
        </div>
    </div>
    <div class="form-group">
        <label for="amount" class="col-sm-3 control-label">Amount</label>

        <div class="col-sm-3">
            <div class="input-group">
				<span class="input-group-addon"><spring:message
                        code="web.currency.symbol"/></span> <input type="number"
                                                                   class="form-control" id="amount" name="amount"
                                                                   value="${amount}" required/>
				<span class="input-group-addon"><spring:message
                        code="web.currency.suffix"/></span>
            </div>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-3 col-sm-10">
            <button type="submit" class="btn btn-primary">
                <spring:message code="web.button.save"/>
            </button>
            <a href="${pageContext.request.contextPath}/expense/search">
                <button type="button" class="btn btn-default">
                    <spring:message code="web.button.cancel"/>
                </button>
            </a>
        </div>
    </div>
</form>

<script>

    $('#tagList').tokenfield({
        autocomplete: {
            source: [${tagValueJS}],
            delay: 100
        },
        showAutocompleteOnFocus: true
    });

    $("#expenseEditForm").submit(function (event) {
        document.getElementById('tagString').value = $('#tagList').tokenfield('getTokensList', ',');
    });

</script>
<%@ include file="../template/footer.jsp" %>