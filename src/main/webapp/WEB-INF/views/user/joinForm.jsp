<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="../layout/header.jsp" %>

<div class="container my-3">
    <div class="container">
        <form action="/join" method="post" onsubmit="return valid()">
            <div class="form-group mb-2">
                <div class="d-flex">
                    <%-- onchage 입력 필드에서 값이 변경되어 포커스가 해제되었을 때 발생--%>
                    <%-- oninput 입력 필드에서 값이 변경될 때 마다 발생--%>
                    <input type="text" name="username" class="form-control" placeholder="Enter username" id="username"
                           maxlength="20" onchange="changeUsernameState()" required>
                    <button type="button" class="badge bg-secondary ms-2" onclick="sameUsername()">중복확인</button>
                </div>
                <div id="usernameErrorMsg" class="error-msg"></div>
            </div>

            <div class="form-group mb-2">
                <input type="password" name="password" class="form-control" placeholder="Enter password" id="password"
                       maxlength="20" required>
                <div id="passwordErrorMsg" class="error-msg"></div>
            </div>

            <div class="form-group mb-2">
                <input type="password" class="form-control" placeholder="Enter samePassword" id="samePassword"
                       maxlength="20" required>
                <div id="samePasswordErrorMsg" class="error-msg"></div>
            </div>

            <div class="form-group mb-2">
                <input type="email" name="email" class="form-control" placeholder="Enter email" id="email"
                       maxlength="50" required>
            </div>

            <button class="btn btn-primary">회원가입</button>
        </form>

    </div>
</div>

<script>
    function valid() {
        alert("회원가입 유효성 검사")
        return true;
    }
</script>

<%@ include file="../layout/footer.jsp" %>