// 页面长时间未响应退出登录
$(function(){
    var maxTime = 20 * 60; // seconds
    var time = maxTime;
    // 监控界面鼠标键盘操作
    $('body').on('keydown mousemove mousedown', function(e) {
        time = maxTime; // 重置页面返回时间
    });
    var intervalId = setInterval(function() {
        time--;
        if (time <= 0) {
            ShowInvalidLoginMessage();
            clearInterval(intervalId);
        }
    }, 1000)
    function ShowInvalidLoginMessage() {
        var url = getProjectPath();
        //发送请求清楚session
        $.get(url + "/logout" );
        // 到登录界面
        window.location.href = url + "/";
    }
});
//获取当前项目的名称
function getProjectPath() {
    //获取主机地址之后的目录
    var pathName = window.document.location.pathname;
    //获取带"/"的项目名，如：/cloudlibrary
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    return projectName;
}