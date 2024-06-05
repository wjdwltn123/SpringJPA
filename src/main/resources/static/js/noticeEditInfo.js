// HTML로딩이 완료되고, 실행됨
$(document).ready(function () {
    // 버튼 클릭했을때, 발생되는 이벤트 생성함(onclick 이벤트와 동일함)
    $("#btnEdit").on("click", function () {
        doSubmit(); // 공지사항 수정하기 실행
    })

    $("#btnInfo").on("click", function () {
        history.back(); // 돌아가기
    })
})

//글자 길이 바이트 단위로 체크하기(바이트값 전달)
function calBytes(str) {
    let tcount = 0;
    let tmpStr = String(str);            let strCnt = tmpStr.length;
    let onechar;
    for (let i = 0; i < strCnt; i++) {
        onechar = tmpStr.charAt(i);
        if (escape(onechar).length > 4) {
            tcount += 2;
        } else {
            tcount += 1;
        }
    }
    return tcount;
}

// 공지사항 저장하기
function doSubmit() {

    let f = document.getElementById("f"); // form 태그

    let seq = f.nSeq.value;

    if (f.title.value === "") {
        alert("제목을 입력하시기 바랍니다.");
        f.title.focus();
        return;
    }
    if (calBytes(f.title.value) > 200) {
        alert("최대 200Bytes까지 입력 가능합니다.");
        f.title.focus();
        return;
    }
    /*            let noticeCheck = false;
                for (let i = 0; i < f.noticeYn.length; i++) {
                    if (f.noticeYn[i].checked) {
                        noticeCheck = true;
                        break;
                    }
                }
                if (noticeCheck === false) {
                    alert("공지글 여부를 선택하시기 바랍니다.");
                    f.noticeYn[0].focus();
                    return;
                }*/

    if (f.contents.value === "") {
        alert("내용을 입력하시기 바랍니다.");
        f.contents.focus();
        return;
    }
    if (calBytes(f.contents.value) > 4000) {
        alert("최대 4000Bytes까지 입력 가능합니다.");
        f.contents.focus();
        return;
    }

    // Ajax 호출해서 글 등록하기
    $.ajax({
            url: "/notice/noticeUpdate",
            type: "post", // 전송방식은 Post
            dataType: "JSON", // 전송 결과는 JSON으로 받기
            data: $("#f").serialize(), // form 태그 내 input 등 객체를 자동으로 전송할 형태로 변경하기
            success: function (json) { // /notice/noticeUpdate 호출이 성공했다면..
                alert(json.msg); // 메시지 띄우기

                location.href = "/notice/noticeInfo?nSeq=" + seq; // 상세 보기로 이동
            }
        }
    )
}