// Made by Mohit Aneja https://codepen.io/cssjockey/
$(document).ready(function() {

    $('ul.profileTabs li').click(function() {
        var tab_id = $(this).attr('data-ptab');

        $('ul.profileTabs li').removeClass('activePT');
        $('.profileTabContent').removeClass('activePT');

        $(this).addClass('activePT');
        $("#" + tab_id).addClass('activePT');
    });

});