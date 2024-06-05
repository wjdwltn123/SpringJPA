// Made by Mohit Aneja https://codepen.io/cssjockey/


$(document).ready(function () {
    $("#btnLogin").on("click", function () {
        location.href = "/user/login";
    })


    $("#btnNotice").on("click", function () {
        location.href = "/notice/noticeList";
    })

    $("#btnEdit").on("click", function () {

        location.href = "/user/profileEdit";

    })

    $("#btnNewPassword").on("click", function () {

        location.href = "/user/myNewPassword";

    })

    $("#btnWithdrawal").on("click", function () {

        location.href = "/user/withdrawal";

    })

})

$(document).ready(function() {

    $('ul.profileTabs li').click(function() {
        var tab_id = $(this).attr('data-ptab');

        $('ul.profileTabs li').removeClass('activePT');
        $('.profileTabContent').removeClass('activePT');

        $(this).addClass('activePT');
        $("#" + tab_id).addClass('activePT');
    });

});