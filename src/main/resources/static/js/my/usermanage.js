var totalRecord, currentPage;
//获取当前项目的名称
function getProjectPath() {
    //获取主机地址之后的目录
    var pathName = window.document.location.pathname;
    //获取带"/"的项目名，如：/cloudlibrary
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    return projectName;
}
//页面加载完成取加载表单数据
$(function () {
    //页面加载完成以后，直接去发送ajax请求,要到分页数据
    show_user(1);
});
//展示用户
function show_user(pageNum) {
    $.ajax({
        url: getProjectPath() + "/userbyidnamestatus?pageNum=" + pageNum,
        type: "POST",
        data:$("#search_form").serialize(),
        success: function (result) {
            //解析user数据
            build_user_table(result);
            //解析分页文字信息
            build_page_info(result);
            //解析分页条信息
            build_page_nav(result);
        }
    });
}
//解析user数据
function build_user_table(result) {
    //清空table表格
    $("#user_table tbody").empty();
    var users = result.extend.users.list;
    $.each(users, function (index, item) {
        var tr = $("<tr></tr>");
        var userId = $("<td></td>").append(item.userId);
        var username = $("<td></td>").append(item.username);
        var userStatus = $("<td></td>");
        var editBtn = $("<a></a>").addClass("btn btn-info btn-sm edit-btn")
            .attr("type", "button")
            .attr("href", "javascript:void(0);")
            .attr("edit-id", item.userId) //为编辑按钮添加一个自定义的属性，来表示当前user的id
            .append("编辑");
        var delBtn = $("<a></a>").addClass("btn btn-danger btn-sm del-btn")
            .attr("type", "button")
            .attr("href", "javascript:void(0);")
            .attr("del-id", item.userId) //为删除按钮添加一个自定义的属性，来表示当前user的id
            .append("删除");
        var btnTd = $("<td></td>").append(editBtn).append(" ").append(delBtn);
        ;
        if (item.userStatus == '1') {
            userStatus.append("在职");
        } else {
            userStatus.append("离职");
            delBtn.removeClass("del-btn").addClass("disabled");
            editBtn.removeClass("edit-btn").addClass("disabled");
            userStatus.addClass("warning");
        }
        tr.append(userId)
            .append(username)
            .append(userStatus)
            .append(btnTd)
            .appendTo("#user_table tbody");
    });
}
//解析分页文字信息
function build_page_info(result) {
    $("#page_info_area").empty();
    $("#page_info_area").append("当前第" + result.extend.users.pageNum + "页,总" +
        result.extend.users.pages + "页,总" +
        result.extend.users.total + "条记录");
    totalRecord = result.extend.users.total;
    currentPage = result.extend.users.pageNum;
}
//解析分页条信息
function build_page_nav(result) {
    $("#page_nav_area").empty();
    //构建首页和前一页li标签
    var firstPageLi = $("<li></li>")
        .append($("<a></a>")
            .append("首页"));
    var prePageLi = $("<li></li>")
        .append($("<a></a>")
            .append("← 前一页"))
        .addClass("prev");
    if (result.extend.users.hasPreviousPage == false) {
        firstPageLi.addClass("disabled");
        prePageLi.addClass("disabled");
    } else {
        //为元素添加点击翻页的事件
        firstPageLi.click(function () {
            show_user(1);
        });
        prePageLi.click(function () {
            show_user(result.extend.users.pageNum - 1);
        });
    }

    //构建末页后下一页
    var nextPageLi = $("<li></li>")
        .append($("<a></a>")
            .append("下一页 →"))
        .addClass("next");
    var lastPageLi = $("<li></li>")
        .append($("<a></a>")
            .append("末页"));
    if (result.extend.users.hasNextPage == false) {
        nextPageLi.addClass("disabled");
        lastPageLi.addClass("disabled");
    } else {
        nextPageLi.click(function () {
            show_user(result.extend.users.pageNum + 1);
        });
        lastPageLi.click(function () {
            show_user(result.extend.users.pages);
        });
    }

    var ul = $("<ul></ul>");
    //添加首页和前一页 的提示
    ul.append(firstPageLi).append(prePageLi);
    //遍历给ul中添加页码提示
    $.each(result.extend.users.navigatepageNums, function (index, item) {

        var numLi = $("<li></li>").append($("<a></a>").append(item));
        if (result.extend.users.pageNum == item) {
            numLi.addClass("active");
        }
        numLi.click(function () {
            show_user(item);
        });
        ul.append(numLi);
    });
    //添加下一页和末页 的提示
    ul.append(nextPageLi).append(lastPageLi);

    //把ul加入到对应位置
    ul.appendTo("#page_nav_area");
}
//删除单个user
$(document).on("click", ".del-btn", function () {
    //1、弹出是否确认删除对话框
    var userName = $(this).parents("tr").find("td:eq(1)").text();
    var userId = $(this).attr("del-id");
    if (confirm("确认删除【" + userName + "】吗？")) {
        //确认，发送ajax请求删除
        $.ajax({
            url: getProjectPath() + "/updateuser/" + userId,
            type: "DELETE",
            success: function () {
                show_user(currentPage);
            }
        });
    }
});
//条件查询
$("#search_btn").click(function () {
    show_user(currentPage);
});
//点击修改密码打开修改员工信息窗口
$(document).on("click", ".edit-btn", function () {
    //清楚表单样式
    show_validate_msg($("#userName_update_input"), "", "")
    //1、查出员工信息，显示员工信息
    getUser($(this).attr("edit-id"));
    $("#userUpdateModal").modal({
        backdrop:"static"
    });
});
//查询员工信息
function getUser(userId) {
    $.ajax({
        url: getProjectPath() + "/userbyidnamestatus",
        type:"POST",
        data: "userId="+userId,
        success:function (result) {
            var user = result.extend.users.list[0];
            //将用户数据添加到表单中
            $("#userId_update_static").val(user.userId);
            $("#userId_update").val(user.userId);
            $("#userName_update_input").val(user.username);
        }
    });
}
//用户名正则表达式， 4-12位的英文或数字
var  username_reg = /^[0-9a-zA-Z]{4,12}$/
//修改员工信息
$(document).on("click", "#user_update_btn", function () {
    //判断表单校验是否通过
    if (validate_input($("#userName_update_input"), username_reg)){
        //请求后端判断当前用户名是否存在
        if(!validate_username_is_exist($("#userName_update_input").val())){
            //不存在发送ajax请求修改数据
            $.ajax({
                url: getProjectPath() + "/updateuser",
                type: "POST",
                data: $("#updateUserForm").serialize(),
                success:function () {
                    //关闭对话框
                    $("#userUpdateModal").modal("hide");
                    //回到当前页面
                    show_user(currentPage);
                }
            });
        }else {
            show_validate_msg($(this),"success", "");
        }
    }
});
//用户名修改输入框失去焦点
$("#userName_update_input").blur(function() {
    //判断用户名输入是否合法
    if (!validate_input($(this), username_reg)) {
        show_validate_msg($(this),"error", "请输入4-12位英文或数字");
    }else {
        show_validate_msg($(this),"", "");
        if(validate_username_is_exist($(this).val())){
            show_validate_msg($(this),"error", "用户名已存在");
        }else {
            show_validate_msg($(this),"success", "");
        }
    }
});
//判断输入框内容是否合法
function validate_input(ele, reg){
    var value = ele.val();
    if (!reg.test(value)){
        return false;
    }else {
        return true;
    }
}
//表单校验提示
function show_validate_msg(ele,status,msg){
    //清除当前元素的校验状态
    ele.parent().removeClass("has-success has-error");
    ele.next("span").text("");
    if("success"==status){
        ele.parent().addClass("has-success");
        ele.next("span").text(msg);
    }else if("error" == status){
        ele.parent().addClass("has-error");
        ele.next("span").text(msg);
    }
}
//请求后端判断当前用户名是否存在
function validate_username_is_exist(value){
    var flag;
    $.ajax({
        url: getProjectPath() + "/userbyidnamestatus",
        type:"POST",
        data:"username=" + value,
        async:false,
        success:function (result) {
            if(result.extend.users.total > 0){
                flag = true;
            }else {
                flag = false;
            }
        }
    });
    return flag;
}
//点击添加员工按钮打开模拟框
$("#add_user_btn").click(function () {
    //清除添加表单的输入
    $("#userAddModal form input").val("");
    //清楚表单样式
    show_validate_msg($("#userAddModal form input"), "", "")
    $("#userAddModal").modal({
        backdrop:"static"
    });
});
//用户名添加输入框失去焦点
$("#username_add_input").blur(function() {
    //判断用户名输入是否合法
    if (!validate_input($(this), username_reg)) {
        show_validate_msg($(this),"error", "请输入4-12位英文或数字");
    }else {
        show_validate_msg($(this),"", "");
        if(validate_username_is_exist($(this).val())){
            show_validate_msg($(this),"error", "用户名已存在");
        }else {
            show_validate_msg($(this),"success", "");
        }
    }
});
//添加员工信息
$(document).on("click", "#user_save_btn", function () {
    //判断表单校验是否通过
    if (validate_input($("#username_add_input"), username_reg)){
        //请求后端判断当前用户名是否存在
        if(!validate_username_is_exist($("#username_add_input").val())){
            //不存在发送ajax请求修改数据
            $.ajax({
                url: getProjectPath() + "/adduser",
                type: "POST",
                data: $("#userAddModal form").serialize(),
                success:function () {
                    //关闭对话框
                    $("#userAddModal").modal("hide");
                    //重置查询表单
                    $('#search_form')[0].reset()
                    //显示当前添加行
                    show_user(999);
                }
            });
        }else {
            show_validate_msg($(this),"success", "");
        }
    }
});