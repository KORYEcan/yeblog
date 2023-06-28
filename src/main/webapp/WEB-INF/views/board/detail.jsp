<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="../layout/header.jsp" %>


<div class="container my-3">
    <c:if test="${sessionUser.id == board.user.id}">
        <div class="mb-3 d-flex">
            <a href="/s/board/${board.id}/updateForm" class="btn btn-warning">수정</a>
            <form action="/s/board/${board.id}/delete" method="post">
                <button class="btn btn-danger">삭제</button>
            </form>
        </div>
    </c:if>


    <div class="mb-2 d-flex justify-content-end">


        <div class="sns-go">

            글 번호 :
            <span id="id" class="me-3">
            <i>${board.id}</i>
        </span>
            작성자 :
            <span class="me-3">
            <i>${board.user.username}</i>
        </span>


            <ul>
                <li>
                    SNS 공유하기 :
                    <a href="#" onclick="javascript:window.open('http://share.naver.com/web/shareView.nhn?url=' +encodeURIComponent(document.URL)+'&title='+encodeURIComponent(document.title), 'naversharedialog', 'menubar=no,toolbar=no,resizable=yes,scrollbars=yes,height=300,width=600');return false;" target="_blank" alt="Share on Naver" rel="nofollow"><img src="/upload/네이버블로그.png" width="35px" height="35px" alt="네이버 블로그 공유하기"></a>
                </li>
                <li>
                    <a href="#" onclick="javascript:window.open('http://band.us/plugin/share?body='+encodeURIComponent(document.title)+encodeURIComponent('\r\n')+encodeURIComponent(document.URL)+'&route='+encodeURIComponent(document.URL), 'menubar=no,toolbar=no,resizable=yes,scrollbars=yes,height=600,width=600');return false;" target="_blank" alt="네이버 밴드에 공유하기" rel="nofollow"><img src="/upload/BAND.png" width="35px" height="35px" alt='네이버 밴드에 공유하기'></a>
                </li>
                <li>
                    <a href="#" onclick="javascript:window.open('https://www.facebook.com/sharer/sharer.php?u=' +encodeURIComponent(document.URL)+'&t='+encodeURIComponent(document.title), 'facebooksharedialog', 'menubar=no,toolbar=no,resizable=yes,scrollbars=yes,height=300,width=600');return false;" target="_blank" alt="Share on Facebook" rel="nofollow"><img src="/upload/facebook.png" width="35px" height="35px"  alt="페이스북 공유하기"></a>
                </li>
                <li>
                    <a href="#" onclick="javascript:window.open('https://twitter.com/intent/tweet?text=[%EA%B3%B5%EC%9C%A0]%20' +encodeURIComponent(document.URL)+'%20-%20'+encodeURIComponent(document.title), 'twittersharedialog', 'menubar=no,toolbar=no,resizable=yes,scrollbars=yes,height=300,width=600');return false;" target="_blank" alt="Share on Twitter" rel="nofollow"><img src="/upload/트위터.png" width="35px" height="35px"  alt="트위터 공유하기"></a>
                </li>
                <li>
                    <a href="#" onclick="javascript:window.open('https://story.kakao.com/s/share?url=' +encodeURIComponent(document.URL), 'kakaostorysharedialog', 'menubar=no,toolbar=no,resizable=yes,scrollbars=yes, height=400,width=600');return false;" target="_blank" alt="Share on kakaostory" rel="nofollow"><img src="/upload/kakaostory.png" width="35px" height="35px" alt="카카오스토리 공유하기"></a>
                </li>
            </ul>
        </div>




    </div>

    <div>
        <h1><b>${board.title}</b></h1>
    </div>
    <hr/>
    <div>
        <div>${board.content}</div>
    </div>
    <hr/>
    <i id="heart" class="fa-regular fa-heart fa-lg"></i>

    <div class="card mt-3">

       <form action="/s/board/${board.id}/reply" method="post">
            <input type="hidden" id="board_Id" value="${board.id}"/>
            <div class="card-body">
                <textarea id="reply-content" class="form-control" rows="1"></textarea>
            </div>
            <div class="card-footer" >
                <button type="submit" id="btn-reply-save" class="btn btn-primary">등록</button>
            </div>
        </form>
    </div>
    <br/>
    <div class="card">
        <form>
            <input type="hidden" id="boardId" value="${board.id}"/>
            <div class="card-header" >댓글 리스트</div>
            <ul id="reply-box" class="list-group">
            <c:forEach items="${board.replyList}" var="reply">
            <li id="reply-1" class="list-group-item d-flex justify-content-between">
            <div>${board.replyList.content}</div>
            <div class="d-flex">
                <div class="font-italic">작성자 : ${board.replyList.username} &nbsp;</div>
                <button onClick="deleteReply()" class="badge bg-secondary">삭제</button>
            </div>
            </li>
            </c:forEach>
            </ul>
        </form>
    </div>
</div>



<%@ include file="../layout/footer.jsp" %>