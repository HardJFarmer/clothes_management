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
    // 查询当前所有仓库
    selectWarehouse();
    //页面加载完成以后，直接去发送ajax请求,要到分页数据
    show_inStock(1);
});
//查询现所有仓库
function selectWarehouse() {
    $.ajax({
        url: getProjectPath() + "/warehouse",
        type: "GET",
        success: function (result) {
            build_select(result.extend.warehouses);
        }
    });
}
//构建仓库下拉选择框
function build_select(warehouses) {
    $.each(warehouses, function (index, item) {
        var option1 = $("<option></option>")
            .prop("value", item.warehouseId)
            .append(item.warehouseName);
        var option2 = $("<option></option>")
            .prop("value", item.warehouseId)
            .append(item.warehouseName);
        var option3 = $("<option></option>")
            .prop("value", item.warehouseId)
            .append(item.warehouseName);
        option1.appendTo("#search_warehouseName");
        option2.appendTo("#update_warehouseName");
        option3.appendTo("#add_warehouseName");
    });
}
//显示入库单
function show_inStock(pageNum) {
    $.ajax({
        url: getProjectPath() + "/inorder?pageNum=" + pageNum,
        type: "POST",
        data: $("#search_form").serialize(),
        success: function (result) {
            // 解析数据
            build_table(result);
            //解析分页文字信息
            build_page_info(result);
            //解析分页条信息
            build_page_nav(result);
        }
    });
}
//解析数据到表格
function build_table(result) {
    //翻页后全选按钮设为false
    $("#check_all").prop("checked", false);
    //清空table表格
    $("#order_table tbody").empty();
    var orders = result.extend.orders.list;
    $.each(orders, function (index, item) {
        const checkBoxTd = $("<td><input type='checkbox' class='check_item'/></td>");
        const orderId = $("<td></td>").append(item.orderId);
        const orderNumber = $("<td></td>").append(item.orderNumber);
        const orderDate = $("<td></td>").append(item.orderDate);
        const responsiblePerson = $("<td></td>").append(item.responsiblePerson);
        const sourceCompany = $("<td></td>").append(item.sourceCompany);
        const wareHouseName = $("<td></td>").append(item.wareHouse.warehouseName);
        const editBtn = $("<a></a>").addClass("btn btn-info btn-sm edit-btn")
            .attr("type", "button")
            .attr("href", "javascript:void(0);")
            .attr("edit-id", item.orderId) //为编辑按钮添加一个自定义的属性，来表示当前cargo的id
            .append("编辑");
        const delBtn = $("<a></a>").addClass("btn btn-danger btn-sm del-btn")
            .attr("type", "button")
            .attr("href", "javascript:void(0);")
            .attr("del-id", item.orderId) //为删除按钮添加一个自定义的属性，来表示当前cargo的id
            .append("删除");
        const itemBtn = $("<a></a>").addClass("btn btn-warning btn-sm find-btn")
            .attr("type", "button")
            .attr("href", "javascript:void(0);")
            .attr("find-id", item.orderId)
            .attr("warehouse-id", item.wareHouse.warehouseId)
            .append("明细修改");
        const btnTd = $("<td></td>")
            .append(editBtn).append("  ")
            .append(delBtn).append("  ")
            .append(itemBtn);
        $("<tr></tr>").append(checkBoxTd)
            .append(orderId)
            .append(orderNumber)
            .append(orderDate)
            .append(responsiblePerson)
            .append(sourceCompany)
            .append(wareHouseName)
            .append(btnTd)
            .appendTo("#order_table tbody");
    });
}
//解析分页文字信息
function build_page_info(result) {
    $("#page_info_area").empty();
    $("#page_info_area").append("当前第" + result.extend.orders.pageNum + "页,总" +
        result.extend.orders.pages + "页,总" +
        result.extend.orders.total + "条记录");
    totalRecord = result.extend.orders.total;
    currentPage = result.extend.orders.pageNum;
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
    if (result.extend.orders.hasPreviousPage == false) {
        firstPageLi.addClass("disabled");
        prePageLi.addClass("disabled");
    } else {
        //为元素添加点击翻页的事件
        firstPageLi.click(function () {
            show_inStock(1);
        });
        prePageLi.click(function () {
            show_inStock(result.extend.orders.pageNum - 1);
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
    if (result.extend.orders.hasNextPage == false) {
        nextPageLi.addClass("disabled");
        lastPageLi.addClass("disabled");
    } else {
        nextPageLi.click(function () {
            show_inStock(result.extend.orders.pageNum + 1);
        });
        lastPageLi.click(function () {
            show_inStock(result.extend.orders.pages);
        });
    }

    var ul = $("<ul></ul>");
    //添加首页和前一页 的提示
    ul.append(firstPageLi).append(prePageLi);
    //遍历给ul中添加页码提示
    $.each(result.extend.orders.navigatepageNums, function (index, item) {

        var numLi = $("<li></li>").append($("<a></a>").append(item));
        if (result.extend.orders.pageNum == item) {
            numLi.addClass("active");
        }
        numLi.click(function () {
            show_inStock(item);
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
    var orderNumber = $(this).parents("tr").find("td:eq(2)").text();
    var orderId = $(this).attr("del-id");
    if (confirm("确认删除编号为" + orderNumber + "的入库单吗？")) {
        //确认，发送ajax请求删除
        $.ajax({
            url: getProjectPath() + "/deleteinorder",
            type: "POST",
            data: "orderId=" + orderId,
            success: function () {
                show_inStock(currentPage);
            }
        });
    }
});
//条件查询
$("#search_btn").click(function () {
    show_inStock(currentPage);
});
//点击编辑按钮
$(document).on("click", ".edit-btn", function () {
    if (!isEmpty($(this).attr("edit-id"))){
        alert("当前入库单已有明细，不允许修改！")
    }else {
        //清空修改表单样式和内容
        reset_form("#updateOrderForm");

        //查出订单信息，显示订单信息
        getOrder($(this).attr("edit-id"));
        $("#orderUpdateModal").modal({
            backdrop: "static"
        });
    }
});
//查询出当前订单信息添加到表单中
function getOrder(orderId) {
    $.ajax({
        url: getProjectPath() + "/inorder",
        type: "POST",
        data: "orderId=" + orderId,
        success: function (result) {
            const order = result.extend.orders.list[0];
            $("#order_update_static").val(order.orderNumber);
            $("#orderId_update").val(order.orderId);
            $("#responsiblePerson_update").val(order.responsiblePerson);
            $("#sourceCompany_update").val(order.sourceCompany);
            $("#orderDate_update").val(order.orderDate);
            $("#update_warehouseName").val(order.wareHouse.warehouseId);
        }
    });
}
//点击更新按钮
$(document).on("click", "#order_update_btn", function () {
    //判断表单校验是否通过
    if (validate_form("update")) {
        $.ajax({
            url: getProjectPath() + "/updateinorder",
            type: "POST",
            data: $("#updateOrderForm").serialize(),
            success: function () {
                //关闭对话框
                $("#orderUpdateModal").modal("hide");
                //回到当前页面
                show_inStock(currentPage);
            }
        });
    }
});
//校验表单数据是否通过
function validate_form(model) {
    const responsiblePersonLen = $("#responsiblePerson_" + model).val().length;
    const sourceCompanyLen = $("#sourceCompany_" + model).val().length;

    if (responsiblePersonLen < 2) {
        show_validate_msg($("#responsiblePerson_" + model), "error", "请输入真实姓名");
        return false;
    } else {
        show_validate_msg($("#responsiblePerson_" + model), "success", "");
    }
    if (sourceCompanyLen < 3) {
        show_validate_msg($("#sourceCompany_" + model), "error", "请输入正确来源");
        return false;
    } else {
        show_validate_msg($("#sourceCompany_" + model), "success", "");
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
//点击添加按钮打开模拟框
$("#add_order_btn").click(function () {
    //清楚表单样式
    reset_form("#orderAddModal form");
    //日期选择框选择当前日期
    chooseDate("#orderDate_add")
    $("#orderAddModal").modal({
        backdrop: "static"
    });
});
//添加入库单日期选择现在
function chooseDate(ele) {
    const time = new Date();
    const day = ("0" + time.getDate()).slice(-2);
    const month = ("0" + (time.getMonth() + 1)).slice(-2);
    const today = time.getFullYear() + "-" + (month) + "-" + (day);
    $(ele).val(today);
}
//点击保存按钮进行添加
$("#order_save_btn").click(function () {
    //判断表单校验是否通过
    if (validate_form("add")) {
        // 不存在发送ajax请求修改数据
        $.ajax({
            url: getProjectPath() + "/addinorder",
            type: "POST",
            data: $("#addOrderForm").serialize(),
            success: function () {
                //关闭对话框
                $("#orderAddModal").modal("hide");
                //重置查询表单
                $('#search_form')[0].reset()
                //无条件查询当前有多少条数据
                $.ajax({
                    url: getProjectPath() + "/inorder",
                    type: "POST",
                    success: function (result) {
                        //显示当前添加行
                        show_inStock(result.extend.orders.total);
                    }
                });
            }
        });
    }
});
//完成全选/全不选功能
$("#check_all").click(function () {
    //attr获取checked是undefined;
    //我们这些dom原生的属性；attr获取自定义属性的值；
    //prop修改和读取dom原生属性的值
    //所有的check_item的cheacked属性等于check_all
    $(".check_item").prop("checked", $(this).prop("checked"));
});
//check_item全部选中则check选中
$(document).on("click", ".check_item", function () {
    //判断当前选择中的元素是否check_item的个数
    var flag = $(".check_item:checked").length == $(".check_item").length;
    $("#check_all").prop("checked", flag);
});
//点击全部删除，就批量删除
$("#del_all_btn").click(function(){
    var orderNumbers = "";
    var ids = "";
    $.each($(".check_item:checked"),function(){
        orderNumbers += $(this).parents("tr").find("td:eq(2)").text()+",";
        //组装id字符串
        ids += "ids=" + $(this).parents("tr").find("td:eq(1)").text()+"&";
    });
    //去除empNames多余的,
    orderNumbers = orderNumbers.substring(0, orderNumbers.length-1);
    //去除删除的id多余的-
    ids = ids.substring(0, ids.length-1);
    if(confirm("确认删除【"+orderNumbers+"】的入库单吗？")){
        //发送ajax请求删除
        $.ajax({
            url:getProjectPath() + "/delOrderList",
            type:"POST",
            data: ids,
            success:function(result){
                //回到当前页面
                show_inStock(currentPage);
                $("#check_all").prop("checked",false);
            }
        });
    }
});
//点击查看库存显示当前该商品所在仓库以及数量
$(document).on("click", ".find-btn", function () {
    var data = "orderId=" + $(this).attr("find-id") + "&warehouseId="
        + $(this).attr("warehouse-id");
    window.location.href = getProjectPath() + "/showinitem?" + data;
});
//判断当前有单据中没有明细
function isEmpty(orderId) {
    var flag;
    $.ajax({
        url: getProjectPath() + "/selectorderitem",
        type: "POST",
        data: "orderId=" + orderId,
        async: false,
        success: function (result) {
            if(result.extend.orderitems.total > 0){
                flag = false;
            }else {
                flag = true;
            }
        }
    });
    return flag;
}

