var totalRecord, currentPage;
//获取当前项目的名称
function getProjectPath() {
    //获取主机地址之后的目录
    var pathName = window.document.location.pathname;
    //获取带"/"的项目名，如：/cloudlibrary
    var projectName = pathName.substring(0, pathName.substr(1).indexOf('/') + 1);
    return projectName;
}
//清空表单样式及内容
function reset_form(ele){
    $(ele)[0].reset();
    //清空表单样式
    $(ele).find("*").removeClass("has-error has-success");
    $(ele).find(".help-block").text("");
}
//页面加载完成取加载表单数据
$(function () {
    //页面加载完成以后，直接去发送ajax请求,要到分页数据
    show_cargo(1);
});
function show_cargo(pageNum){
    $.ajax({
        url: getProjectPath() + "/selectbycargo?pageNum=" + pageNum,
        type: "POST",
        data: $("#search_form").serialize(),
        success:function (result) {
            //解析cargo数据
            build_table(result);
            //解析分页文字信息
            build_page_info(result);
            //解析分页条信息
            build_page_nav(result);
        }
    });
}
//解析cargo数据到表格
function build_table(result){
    //翻页后全选按钮设为false
    $("#check_all").prop("checked",false);
    //清空table表格
    $("#cargo_table tbody").empty();
    var cargos = result.extend.cargos.list;
    $.each(cargos, function (index, item) {
        var checkBoxTd = $("<td><input type='checkbox' class='check_item'/></td>");
        var cargoId = $("<td></td>").append(item.cargoId);
        var cargoNumber = $("<td></td>").append(item.cargoNumber);
        var cargoName = $("<td></td>").append(item.cargoName);
        var cargoColor = $("<td></td>").append(item.cargoColor);
        var cargoSize = $("<td></td>").append(item.cargoSize);
        var editBtn = $("<a></a>").addClass("btn btn-info btn-sm edit-btn")
            .attr("type", "button")
            .attr("href", "javascript:void(0);")
            .attr("edit-id", item.cargoId) //为编辑按钮添加一个自定义的属性，来表示当前cargo的id
            .append("编辑");
        var delBtn = $("<a></a>").addClass("btn btn-danger btn-sm del-btn")
            .attr("type", "button")
            .attr("href", "javascript:void(0);")
            .attr("del-id", item.cargoId) //为删除按钮添加一个自定义的属性，来表示当前cargo的id
            .append("删除");
        var findAllBtn = $("<a></a>").addClass("btn btn-warning btn-sm find-btn")
            .attr("type", "button")
            .attr("href", "javascript:void(0);")
            .attr("find-id", item.cargoId) //为删除按钮添加一个自定义的属性，来表示当前cargo的id
            .append("查看库存");
        var btnTd = $("<td></td>")
            .append(editBtn).append("  ")
            .append(delBtn).append("  ")
            .append(findAllBtn);
        $("<tr></tr>").append(checkBoxTd)
            .append(cargoId)
            .append(cargoNumber)
            .append(cargoName)
            .append(cargoColor)
            .append(cargoSize)
            .append(btnTd)
            .appendTo("#cargo_table tbody");
    });
}
//解析分页文字信息
function build_page_info(result) {
    $("#page_info_area").empty();
    $("#page_info_area").append("当前第" + result.extend.cargos.pageNum + "页,总" +
        result.extend.cargos.pages + "页,总" +
        result.extend.cargos.total + "条记录");
    totalRecord = result.extend.cargos.total;
    currentPage = result.extend.cargos.pageNum;
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
    if (result.extend.cargos.hasPreviousPage == false) {
        firstPageLi.addClass("disabled");
        prePageLi.addClass("disabled");
    } else {
        //为元素添加点击翻页的事件
        firstPageLi.click(function () {
            show_cargo(1);
        });
        prePageLi.click(function () {
            show_cargo(result.extend.cargos.pageNum - 1);
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
    if (result.extend.cargos.hasNextPage == false) {
        nextPageLi.addClass("disabled");
        lastPageLi.addClass("disabled");
    } else {
        nextPageLi.click(function () {
            show_cargo(result.extend.cargos.pageNum + 1);
        });
        lastPageLi.click(function () {
            show_cargo(result.extend.cargos.pages);
        });
    }

    var ul = $("<ul></ul>");
    //添加首页和前一页 的提示
    ul.append(firstPageLi).append(prePageLi);
    //遍历给ul中添加页码提示
    $.each(result.extend.cargos.navigatepageNums, function (index, item) {

        var numLi = $("<li></li>").append($("<a></a>").append(item));
        if (result.extend.cargos.pageNum == item) {
            numLi.addClass("active");
        }
        numLi.click(function () {
            show_cargo(item);
        });
        ul.append(numLi);
    });
    //添加下一页和末页 的提示
    ul.append(nextPageLi).append(lastPageLi);

    //把ul加入到对应位置
    ul.appendTo("#page_nav_area");
}
//删除单个
$(document).on("click", ".del-btn", function () {
    //1、弹出是否确认删除对话框
    var cargoNumber = $(this).parents("tr").find("td:eq(2)").text();
    var cargoId = $(this).attr("del-id");
    if (confirm("确认删除编号为" + cargoNumber + "的货品吗？")) {
        //确认，发送ajax请求删除
        $.ajax({
            url: getProjectPath() + "/deletecargo",
            type: "POST",
            data: "cargoId=" + cargoId,
            success: function () {
                show_cargo(currentPage);
            }
        });
    }
});
//条件查询
$("#search_btn").click(function () {
    show_cargo(currentPage);
});
$(document).on("click", ".edit-btn", function () {
    //清空修改表单样式和内容
    reset_form("#updateCargoForm");
    //查出货品信息，显示货品信息
    getCargo($(this).attr("edit-id"));
    $("#cargoUpdateModal").modal({
        backdrop:"static"
    });
});
//查询出货品信息添加到表单中
function getCargo(cargoId){
    $.ajax({
        url: getProjectPath() + "/selectbycargo",
        type: "POST",
        data: "cargoId=" + cargoId,
        success:function (result) {
            var cargo = result.extend.cargos.list[0];
            $("#cargo_update_static").val(cargo.cargoNumber);
            $("#cargoId_update").val(cargo.cargoId);
            $("#cargoName_update").val(cargo.cargoName);
            $("#cargoColor_update").val(cargo.cargoColor);
            $("#cargoSize_update").val(cargo.cargoSize);
        }
    });
}
//点击更新按钮
$(document).on("click", "#cargo_update_btn", function () {
    //判断表单校验是否通过
    if (validate_form("update")){
        //请求后端判断当前用户名是否存在
        if(!validate_cargo_is_exist("update")){
            // 不存在发送ajax请求修改数据
            $.ajax({
                url: getProjectPath() + "/updatecargo",
                type: "POST",
                data: $("#updateCargoForm").serialize(),
                success:function () {
                    //关闭对话框
                    $("#cargoUpdateModal").modal("hide");
                    //回到当前页面
                    show_cargo(currentPage);
                }
            });
        }else {
            alert("请确保货品名、货品尺寸、货品颜色三者合起来唯一！")
        }
    }
});
//校验表单数据是否通过
function validate_form(model){
    var cargoNameLen = $("#cargoName_" + model).val().length;
    var cargoColorLen = $("#cargoColor_" + model).val().length;
    var cargoSizeLen = $("#cargoSize_" + model).val().length;
    if(cargoNameLen < 2){
        show_validate_msg($("#cargoName_" + model), "error", "请至少输入两个字符");
        return false;
    }else {
        show_validate_msg($("#cargoName_" + model), "success", "");
    }
    if (cargoColorLen < 2){
        show_validate_msg($("#cargoColor_" + model), "error", "请至少输入两个字符");
        return false;
    }else {
        show_validate_msg($("#cargoColor_" + model), "success", "");
    }
    if(cargoSizeLen < 1){
        show_validate_msg($("#cargoSize_" + model), "error", "请至少输入一个字符");
        return false;
    }else {
        show_validate_msg($("#cargoSize_" + model), "success", "");
    }
    return true;
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
//请求后端判断修改后货品名，货品颜色，货品尺寸(三个属性确定唯一的索引)是否存在
function validate_cargo_is_exist(model){
    var cargoName = $("#cargoName_" + model).val();
    var cargoColor = $("#cargoColor_" + model).val();
    var cargoSize = $("#cargoSize_" + model).val();
    var flag;
    $.ajax({
        url: getProjectPath() + "/selectbycargo",
        type:"POST",
        data: "cargoName=" + cargoName + "&cargoColor=" + cargoColor + "&cargoSize=" + cargoSize,
        async: false,
        success:function (result) {
            if(result.extend.cargos.total > 0){
                flag = true;
            }else {
                flag = false;
            }
        }
    });
    return flag;
}
//点击添加按钮打开模拟框
$("#add_cargo_btn").click(function () {
    //清楚表单样式
    reset_form("#cargoAddModal form");
    $("#cargoAddModal").modal({
        backdrop:"static"
    });
});
//点击保存按钮进行添加
$("#cargo_save_btn").click(function (){
    //判断表单校验是否通过
    if (validate_form("add")){
        //请求后端判断当前货品是否存在
        if(!validate_cargo_is_exist("add")){
            // 不存在发送ajax请求修改数据
            $.ajax({
                url: getProjectPath() + "/addcargo",
                type: "POST",
                data: $("#addCargoForm").serialize(),
                success:function () {
                    //关闭对话框
                    $("#cargoAddModal").modal("hide");
                    //重置查询表单
                    $('#search_form')[0].reset()
                    //无条件查询当前有多少条数据
                    $.ajax({
                        url: getProjectPath() + "/selectbycargo",
                        type: "POST",
                        success:function (result) {
                            //显示当前添加行
                            show_cargo(result.extend.cargos.total);
                        }
                    });
                }
            });
        }else {
            alert("请确保货品名、货品尺寸、货品颜色三者合起来唯一！")
        }
    }
});
//完成全选/全不选功能
$("#check_all").click(function(){
    //attr获取checked是undefined;
    //我们这些dom原生的属性；attr获取自定义属性的值；
    //prop修改和读取dom原生属性的值
    //所有的check_item的cheacked属性等于check_all
    $(".check_item").prop("checked",$(this).prop("checked"));
});
//check_item全部选中则check选中
$(document).on("click",".check_item",function(){
    //判断当前选择中的元素是否check_item的个数
    var flag = $(".check_item:checked").length == $(".check_item").length;
    $("#check_all").prop("checked",flag);
});
//点击全部删除，就批量删除
$("#del_all_btn").click(function(){
    var cargoNumbers = "";
    var ids = "";
    $.each($(".check_item:checked"),function(){
        cargoNumbers += $(this).parents("tr").find("td:eq(2)").text()+",";
        //组装id字符串
        ids += "ids=" + $(this).parents("tr").find("td:eq(1)").text()+"&";
    });
    //去除empNames多余的,
    cargoNumbers = cargoNumbers.substring(0, cargoNumbers.length-1);
    //去除删除的id多余的-
    ids = ids.substring(0, ids.length-1);
    if(confirm("确认删除【"+cargoNumbers+"】的货品吗？")){
        //发送ajax请求删除
        $.ajax({
            url:getProjectPath() + "/delCargoList",
            type:"POST",
            data: ids,
            success:function(result){
                //回到当前页面
                show_cargo(currentPage);
                $("#check_all").prop("checked",false);
            }
        });
    }
});
//点击查看库存显示当前该商品所在仓库以及数量
$(document).on("click", ".find-btn", function () {
    window.location.href = getProjectPath() + "/showStock?cargoId=" + $(this).attr("find-id");
});