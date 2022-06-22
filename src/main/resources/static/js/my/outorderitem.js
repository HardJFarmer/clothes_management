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
function reset_form(ele) {
    $(ele)[0].reset();
    //清空表单样式
    $(ele).find("*").removeClass("has-error has-success");
    $(ele).find(".help-block").text("");
}

//页面加载完成执行操作
$(function () {
    //页面加载完成以后，直接去发送ajax请求,要到分页数据
    show_item(1);
});

var orderId = $("#orderId").val();
var warehouseId = $("#warehouseId").val();

function show_item(pageNum) {
    $.ajax({
        url: getProjectPath() + "/selectorderitem?pageNum=" + pageNum,
        type: "POST",
        data: "orderId=" + orderId,
        success: function (result) {
            // 解析cargo数据
            build_table(result.extend.orderitems.list);
            //解析分页文字信息
            build_page_info(result);
            //解析分页条信息
            build_page_nav(result);
        }
    });
}

function build_table(orderitems) {
    $("#item_table tbody").empty();
    $.each(orderitems, function (index, item) {
        const id = $("<td></td>").append(index + 1);
        const cargoNumber = $("<td></td>").append(item.cargo.cargoNumber);
        const cargoName = $("<td></td>").append(item.cargo.cargoName);
        const cargoColor = $("<td></td>").append(item.cargo.cargoColor);
        const cargoSize = $("<td></td>").append(item.cargo.cargoSize);
        const changeAccount = $("<td></td>").append(item.changeAccount);
        const editBtn = $("<a></a>").addClass("btn btn-info btn-sm edit-btn")
            .attr("type", "button")
            .attr("href", "javascript:void(0);")
            .attr("edit-id", item.orderitemId)
            .attr("update-cargo-id", item.cargo.cargoId)
            .append("修改数量");
        const delBtn = $("<a></a>").addClass("btn btn-danger btn-sm del-btn")
            .attr("type", "button")
            .attr("href", "javascript:void(0);")
            .attr("del-id", item.orderitemId)
            .attr("del-cargo-id", item.cargo.cargoId)
            .append("删除");
        const btnTd = $("<td></td>")
            .append(editBtn).append("  ")
            .append(delBtn).append("  ");
        $("<tr></tr>")
            .append(id)
            .append(cargoNumber)
            .append(cargoName)
            .append(cargoColor)
            .append(cargoSize)
            .append(changeAccount)
            .append(btnTd)
            .appendTo("#item_table tbody");
    });
}

//解析分页文字信息
function build_page_info(result) {
    $("#page_info_area").empty();
    $("#page_info_area").append("当前第" + result.extend.orderitems.pageNum + "页,总" +
        result.extend.orderitems.pages + "页,总" +
        result.extend.orderitems.total + "条记录");
    totalRecord = result.extend.orderitems.total;
    currentPage = result.extend.orderitems.pageNum;
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
    if (result.extend.orderitems.hasPreviousPage == false) {
        firstPageLi.addClass("disabled");
        prePageLi.addClass("disabled");
    } else {
        //为元素添加点击翻页的事件
        firstPageLi.click(function () {
            show_item(1);
        });
        prePageLi.click(function () {
            show_item(result.extend.orderitems.pageNum - 1);
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
    if (result.extend.orderitems.hasNextPage == false) {
        nextPageLi.addClass("disabled");
        lastPageLi.addClass("disabled");
    } else {
        nextPageLi.click(function () {
            show_item(result.extend.orderitems.pageNum + 1);
        });
        lastPageLi.click(function () {
            show_item(result.extend.orderitems.pages);
        });
    }

    var ul = $("<ul></ul>");
    //添加首页和前一页 的提示
    ul.append(firstPageLi).append(prePageLi);
    //遍历给ul中添加页码提示
    $.each(result.extend.orderitems.navigatepageNums, function (index, item) {

        var numLi = $("<li></li>").append($("<a></a>").append(item));
        if (result.extend.orderitems.pageNum == item) {
            numLi.addClass("active");
        }
        numLi.click(function () {
            show_item(item);
        });
        ul.append(numLi);
    });
    //添加下一页和末页 的提示
    ul.append(nextPageLi).append(lastPageLi);

    //把ul加入到对应位置
    ul.appendTo("#page_nav_area");
}

// 删除单个
$(document).on("click", ".del-btn", function () {
    //1、弹出是否确认删除对话框
    var orderItemNumber = $(this).parents("tr").find("td:eq(0)").text();
    var orderItemId = $(this).attr("del-id");
    var cargoId = $(this).attr("del-cargo-id");
    var changeAccount = $(this).parents("tr").find("td:eq(5)").text();
    if (confirm("确认删除编号为" + orderItemNumber + "的订单明细吗？")) {
        //确认，发送ajax请求删除
        $.ajax({
            url: getProjectPath() + "/deleteoutorderitem",
            type: "POST",
            data: "orderitemId=" + orderItemId + "&warehouseId="
                + warehouseId + "&cargoId=" + cargoId + "&changeAccount=" + changeAccount,
            success: function () {
                show_item(currentPage);
            }
        });
    }
});
//点击添加按钮打开模拟框
$("#add_item_btn").click(function () {
    //清楚表单样式
    reset_form("#itemAddModal form");
    //构建仓库下拉栏
    selectCargo("#add_cargoId");
    $("#itemAddModal").modal({
        backdrop: "static"
    });
});

//构建货品下拉栏
function selectCargo(ele) {
    //获取当前仓库的所有货品
    var warehouseId = $("#warehouseId").val();
    $.ajax({
        url: getProjectPath() + "/selectcargoandwarehouse",
        type: "GET",
        data: "warehouseId=" + warehouseId,
        success: function (result) {
            build_select(result.extend.warehouses[0].cargoList, ele);
        }
    });
}

//开始构建
function build_select(cargos, ele) {
    $(ele).empty();
    $.each(cargos, function (index, item) {
        $("<option></option>")
            .prop("value", item.cargoId)
            .append("--货品编号：" + item.cargoNumber
                + "  --货品名：" + item.cargoName
                + "--尺寸：" + item.cargoSize
                + "--颜色：" + item.cargoColor)
            .appendTo(ele);
    });
    $("#account_static").val(cargos[0].account);
}

//当下拉栏变化后
$("#add_cargoId").on("change", function () {
    //获得其所选值
    var cargoId = $(this).val();
    getAccount(cargoId, "#account_static")
});
//点击保存按钮进行添加
$("#item_save_btn").click(function () {
    //判断表单校验是否通过
    if (validate_form()) {
        $.ajax({
            url: getProjectPath() + "/addoutorderitem",
            type: "POST",
            data: $("#addItemForm").serialize(),
            success: function (result) {
                if(result.code == 100){
                    //关闭对话框
                    $("#itemAddModal").modal("hide");
                    //无条件查询当前有多少条数据
                    $.ajax({
                        url: getProjectPath() + "/selectorderitem",
                        type: "POST",
                        data: "orderId=" + orderId,
                        success: function (result) {
                            //显示当前添加行
                            show_item(result.extend.orderitems.total);
                        }
                    });
                }else{
                    alert("库存不足！");
                }
            }
        });
    }
});

//校验表单数据是否通过
function validate_form() {
    const account_static = $("#account_static").val();
    const add_account = $("#add_account").val();

    if(!validate_account("#add_account")){
        return false;
    }

    if (Number(add_account) > Number(account_static)) {
        show_validate_msg($("#add_account"), "error", "输入的数量不能大于库存");
        return false;
    } else {
        show_validate_msg($("#add_account"), "success", "");
    }
    return true;
}

//表单校验提示
function show_validate_msg(ele, status, msg) {
    //清除当前元素的校验状态
    ele.parent().removeClass("has-success has-error");
    ele.next("span").text("");
    if ("success" == status) {
        ele.parent().addClass("has-success");
        ele.next("span").text(msg);
    } else if ("error" == status) {
        ele.parent().addClass("has-error");
        ele.next("span").text(msg);
    }
}

$(document).on("click", ".edit-btn", function () {
    //清空修改表单样式和内容
    reset_form("#updateItemForm");
    //查出数量
    getAccount($(this).attr("update-cargo-id"), "#account_update_static");
    $("#orderitem-update-id").val($(this).attr("edit-id"));
    $("#cargo-update-id").val($(this).attr("update-cargo-id"));
    $("#itemUpdateModal").modal({
        backdrop: "static"
    });
});
//查出数量
function getAccount(cargoId,ele) {
    //查询后端获判断当前仓库该商品的数量
    $.ajax({
        url: getProjectPath() + "/selectAccount",
        type: "POST",
        data: "cargoId=" + cargoId + "&warehouseId=" + warehouseId,
        success: function (result) {
            $(ele).val(result.extend.cargoAndWareHouse.account);
        }
    });
}
//点击更新按钮
$(document).on("click", "#account_update_btn", function () {
    //判断表单校验是否通过
    if (validate_account("#update_account")) {
        $.ajax({
            url: getProjectPath() + "/updateoutorderitem",
            type: "POST",
            data: $("#updateItemForm").serialize(),
            success: function (result) {
                if (result.code == 100){
                    //关闭对话框
                    $("#itemUpdateModal").modal("hide");
                    //回到当前页面
                    show_item(currentPage);
                }else {
                    alert("库存不够！");
                }
            }
        });
    }
});
//判断数字数量输入是否合法
function validate_account(ele) {
    var regx = /^[1-9]\d*$/;
    var value = $(ele).val();
    if (!regx.test(value)) {
        show_validate_msg($(ele), "error", "请输入合理数量");
        return false;
    } else {
        show_validate_msg($(ele), "success", "");
        return true;
    }
}
