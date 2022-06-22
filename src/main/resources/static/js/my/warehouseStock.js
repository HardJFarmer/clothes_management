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
    show_stock(1);
});
function show_stock(pageNum){
    var warehouseId = $("#warehouseId").val();
    $.ajax({
        url: getProjectPath() + "/selectcargoandwarehouse?pageNum=" + pageNum,
        type: "POST",
        data: "warehouseId=" + warehouseId,
        success:function (result) {
            //显示页面提示信息
            show_msg(result.extend.warehouses.list[0]);
            // 解析cargo数据
            build_table(result.extend.warehouses.list[0].cargoList);
            //解析分页文字信息
            build_page_info(result);
            //解析分页条信息
            build_page_nav(result);
        }
    });
}
function show_msg(warerhouse){
    $(".panel-heading").text(" id:" + warerhouse.warehouseId + " 仓库名称：" +
        warerhouse.warehouseName);
}
//解析数据到表格
function build_table(cargos){
    //清空table表格
    $.each(cargos, function (index, item) {
        const cargoId = $("<td></td>").append(item.cargoId);
        const cargoNumber = $("<td></td>").append(item.cargoNumber);
        const cargoName = $("<td></td>").append(item.cargoName);
        const cargoColor = $("<td></td>").append(item.cargoColor);
        const cargoSize = $("<td></td>").append(item.cargoSize);
        const account = $("<td></td>").append(item.account);
        $("<tr></tr>").append(cargoId)
            .append(cargoNumber)
            .append(cargoName)
            .append(cargoColor)
            .append(cargoSize)
            .append(account)
            .appendTo("#stock_table tbody");
    });
}
//解析分页文字信息
function build_page_info(result) {
    $("#page_info_area").empty();
    $("#page_info_area").append("当前第" + result.extend.warehouses.pageNum + "页,总" +
        result.extend.warehouses.pages + "页,总" +
        result.extend.warehouses.total + "条记录");
    totalRecord = result.extend.warehouses.total;
    currentPage = result.extend.warehouses.pageNum;
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
    if (result.extend.warehouses.hasPreviousPage == false) {
        firstPageLi.addClass("disabled");
        prePageLi.addClass("disabled");
    } else {
        //为元素添加点击翻页的事件
        firstPageLi.click(function () {
            show_stock(1);
        });
        prePageLi.click(function () {
            show_stock(result.extend.warehouses.pageNum - 1);
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
    if (result.extend.warehouses.hasNextPage == false) {
        nextPageLi.addClass("disabled");
        lastPageLi.addClass("disabled");
    } else {
        nextPageLi.click(function () {
            show_stock(result.extend.warehouses.pageNum + 1);
        });
        lastPageLi.click(function () {
            show_stock(result.extend.warehouses.pages);
        });
    }

    var ul = $("<ul></ul>");
    //添加首页和前一页 的提示
    ul.append(firstPageLi).append(prePageLi);
    //遍历给ul中添加页码提示
    $.each(result.extend.warehouses.navigatepageNums, function (index, item) {

        var numLi = $("<li></li>").append($("<a></a>").append(item));
        if (result.extend.warehouses.pageNum == item) {
            numLi.addClass("active");
        }
        numLi.click(function () {
            show_stock(item);
        });
        ul.append(numLi);
    });
    //添加下一页和末页 的提示
    ul.append(nextPageLi).append(lastPageLi);

    //把ul加入到对应位置
    ul.appendTo("#page_nav_area");
}