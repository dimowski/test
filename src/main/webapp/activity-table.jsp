<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!----- Contact table ----->
<div class="table-responsive">
    <table class="table table-hover table-bordered">
        <thead>
        <tr>
            <th class="text-center"><input type="checkbox" id="checkAll" onclick="checkAll(this)"/></th>
            <th>Полное имя</th>
            <th>Дата рождения</th>
            <th>Адрес</th>
            <th>Место работы</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="tempContact" items="${CONTACT_LIST.values()}">
            <tr>
                <td class="text-center"><input type="checkbox" name="${tempContact.id}"/></td>
                <td>
                    <c:url var="editLink" value="main">
                        <c:param name="command" value="editContact"/>
                        <c:param name="contactId" value="${tempContact.id}"/>
                    </c:url>
                    <a href="${editLink}">${tempContact.firstName} ${tempContact.middleName} ${tempContact.lastName}</a>
                </td>
                <td class="text-center">
                    <c:if test="${tempContact.birthday != null}">
                            <span class="glyphicon glyphicon-calendar" style="color:lightgray; font-size: 80%"
                                  aria-hidden="true"></span> ${tempContact.birthday}
                    </c:if>
                </td>
                <td>${tempContact.address}</td>
                <td>${tempContact.jobCurrent}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>