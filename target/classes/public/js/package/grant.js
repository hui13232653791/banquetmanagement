$(function () {
    // 加载树形结构
    loadModuleData();
});

// 定义树形结构对象
var zTreeObj;


/**
 * 加载资源树形数据
 */
function loadModuleData() {
    // 配置信息对象  zTree的参数配置
    var setting = {
        // 使用复选框
        check:{
            enable:true,
            chkStyle: "checkbox",
            chkboxType: { "Y": "ps", "N": "ps" }
        },
        // 使用简单的JSON数据
        data:{
            simpleData:{
                enable: true
            }
        },
        // 绑定函数
        callback: {
            // onCheck函数：当 checkbox/radio 被选中或取消选中时触发的函数
            onCheck: zTreeOnCheck
        }
    };

    // 数据
    // 通过ajax查询菜品列表
    $.ajax({
        type:"get",
        url:ctx + "/dishes/queryAllDishes",
        // 查询所有的菜品列表时，传递套餐ID，查询当前套餐对应的已经授权的资源
        data:{
            packageId:$("[name='packageId']").val()
        },
        dataType:"json",
        success:function (data) {
            // data:查询到的资源列表
            // 加载zTree树插件
            zTreeObj = $.fn.zTree.init($("#test1"), setting, data);
        }
    });
}

/**
 * 当 checkbox/radio 被选中或取消选中时触发的函数
 * @param event
 * @param treeId
 * @param treeNode
 */
function zTreeOnCheck(event, treeId, treeNode) {
    // alert(treeNode.tId + ", " + treeNode.name + "," + treeNode.checked);

    // getCheckedNodes(checked):获取所有被勾选的节点集合。
    // 如果checked=true，表示获取勾选的节点；如果checked=false，表示获取未勾选的节点。
    var nodes = zTreeObj.getCheckedNodes(true);
    // console.log(nodes);

    // 获取所有的资源的id值  dIds=1&dIds=2&dIds=3
    // 判断并遍历选中的节点集合
    if (nodes.length > 0) {
        // 定义资源ID
        var dIds = "dIds=";
        // 遍历节点集合，获取资源的ID
        for (var i = 0; i < nodes.length; i++) {
            if (i < nodes.length-1) {
                dIds += nodes[i].id + "&dIds=";
            } else {
                dIds += nodes[i].id;
            }
        }
        console.log(dIds);
    }

    // 获取需要授权的套餐ID的值（隐藏域）
    var packageId = $("[name='packageId']").val();

    // 发送ajax请求，执行菜品的授权操作
    $.ajax({
        type:"post",
        url:ctx + "/package/addGrant",
        data:dIds+"&packageId="+packageId,
        dataType:"json",
        success:function (data) {
            console.log(data);
        }
    });

};

