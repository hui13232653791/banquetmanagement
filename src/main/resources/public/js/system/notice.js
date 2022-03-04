layui.use(['table','layer'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;

    /**
     * 加载数据表格
     */
    var tableIns = table.render({
        id:'noticeTable'
        // 容器元素的ID属性值
        ,elem: '#noticeList'
        // 容器的高度 full-差值
        ,height: 'full-125'
        // 单元格最小的宽度
        ,cellMinWidth:95
        // 访问数据的URL（后台的数据接口）
        ,url: ctx + '/notice/list'
        // 开启分页
        ,page: true
        // 默认每页显示的数量
        ,limit:10
        // 每页页数的可选项
        ,limits:[10,20,30,40,50]
        // 开启头部工具栏
        ,toolbar:'#toolbarDemo'
        // 表头
        ,cols: [[
            // field：要求field属性值与返回的数据中对应的属性字段名一致
            // title：设置列的标题
            // sort：是否允许排序（默认：false）
            // fixed：固定列
            {type:'checkbox', fixed:'center'},
            {field: 'id', title: 'ID', align: 'center'},
            {field: 'title', title: '待办标题', align: 'center'},
            {field: 'openName', title: '发布人', align: 'center'},
            {field: 'state', title: '待办状态', align: 'center',templet: function (d) {
                    if (d.state == 0){return '未完成'} else{return '已完成'}
                }},
            {field: 'createDate', title: '创建时间', align: 'center'},
            {field: 'updateDate', title: '更新时间', align: 'center'},
            {title:'操作',templet:'#noticeListBar', fixed: 'right', align:'center', minWidth:150}
        ]]
    });


    /**
     * 搜索按钮的点击事件
     */
    $(".search_btn").click(function () {
        /**
         * 表格重载
         *    多条件查询
         */
        tableIns.reload({
            // 设置需要传递给后端的参数
            where: { //设定异步数据接口的额外参数，任意设
                // 通过文本框，设置传递的参数
                title: $("[name='title']").val() // 待办标题
                ,openName: $("[name='openName']").val() // 发布人
                ,state:$("[name='state']").val()  //待办状态
                ,startTime:$("[name='startTime']").val() // 开始时间
                ,endTime:$("[name='endTime']").val() // 结束时间
            }
            ,page: {
                curr: 1 // 重新从第 1 页开始
            }
        });
    });

    /**
     * 监听头部工具栏
     */
    table.on('toolbar(notices)', function (data) {

        if (data.event == "add") { // 添加待办
            // 打开添加/修改待办的对话框
            openAddOrUpdateNoticeDialog();
        } else if (data.event == "del") { // 删除待办
            // 获取被选中的数据的信息
            var checkStatus = table.checkStatus(data.config.id);
            // console.log(checkStatus);
            // 删除多个待办记录
            deleteNotices(checkStatus.data);
        }
    });
    
    /**
     * 删除多条待办记录
     * @param userData
     */
    function deleteNotices(noticeData) {
        // 判断用户是否选择了要删除的待办
        if (noticeData.length == 0) {
            layer.msg("请选择要删除的待办！", {icon:5});
            return;
        }
        // 询问用户是否确认删除
        layer.confirm('您确定要删除选中的待办吗？',{icon:3, title:'待办管理'}, function (index) {
            // 关闭确认框
            layer.close(index);
            // 传递的参数是数组   ids=1&ids=2&ids=3
            var ids = "ids=";
            // 循环选中的行记录的数据
            for(var i = 0; i < noticeData.length; i++) {
                if(i < noticeData.length -1) {
                    ids = ids + noticeData[i].id + "&ids="
                } else {
                    ids = ids + noticeData[i].id;
                }
            }
            // console.log(ids);

            // 发送ajax请求，执行删除待办
            $.ajax({
                type:"post",
                url:ctx + "/notice/delete",
                data:ids, // 传递的参数是数组 ids=1&ids=2&ids=3
                success:function (result) {
                    // 判断删除结果
                    if (result.code == 200) {
                        // 提示成功
                        layer.msg("删除成功！",{icon:6});
                        // 刷新表格
                        tableIns.reload();
                    } else {
                        // 提示失败
                        layer.msg(result.msg, {icon:5});
                    }
                }
            });
        });
    }


    /**
     * 监听行工具栏
     */
    table.on('tool(notices)', function (data) {

        if (data.event == "edit") { // 更新待办
            // 打开添加/修改待办的对话框
            openAddOrUpdateNoticeDialog(data.data.id);
        } else if (data.event == "del") { // 删除待办
            // 删除单条待办记录
            deleteNotice(data.data.id);
        } else if (data.event == "info") { // 查看已完成待办
            // 打开对话框
            openNoticeDialog(data.data.id);
        }
    });

    /**
     * 删除单条待办记录
     * @param id
     */
    function deleteNotice(id) {
        // 弹出确认框，询问用户是否确认删除
        layer.confirm('确定要删除该待办吗？',{icon:3, title:"待办管理"}, function (index) {
            // 关闭确认框
            layer.close(index);

            // 发送ajax请求，删除记录
            $.ajax({
                type:"post",
                url:ctx + "/notice/delete",
                data:{
                    ids:id
                },
                success:function (result) {
                    // 判断删除结果
                    if (result.code == 200) {
                        // 提示成功
                        layer.msg("删除成功！",{icon:6});
                        // 刷新表格
                        tableIns.reload();
                    } else {
                        // 提示失败
                        layer.msg(result.msg, {icon:5});
                    }
                }
            });
        });
    }


    /**
     * 打开添加/修改待办的对话框
     */
    function openAddOrUpdateNoticeDialog(id) {
        var title = "<h3>待办管理 - 添加待办</h3>";
        var url = ctx + "/notice/toAddOrUpdateNoticePage";

        // 判断id是否为空；如果为空，则为添加操作，否则是修改操作
        if (id != null && id != '') {
            title = "<h3>待办管理 - 更新待办</h3>";
            url+= "?id="+id; // 传递主键，查询数据
        }

        // iframe层
        layui.layer.open({
            // 类型
            type: 2,
            // 标题
            title: title,
            // 宽高
            area: ['650px', '500px'],
            // url地址
            content: url,
            // 可以最大化与最小化
            maxmin:true
        });
    }

    /**
     * 查看待已完成待办的对话框
     */
    function openNoticeDialog(id) {
        var title = "<h3>待办管理 - 待办详情</h3>";
        var url = ctx + "/notice/selectNoticeById?id="+id;

        // iframe层
        layui.layer.open({
            // 类型
            type: 2,
            // 标题
            title: title,
            // 宽高
            area: ['650px', '380px'],
            // url地址
            content: url,
            // 可以最大化与最小化
            maxmin:true
        });
    }


});

layui.use('laydate', function(){
    var laydate = layui.laydate;
    //初始化时间选择器
    laydate.render({
        elem: "#startTime",
        type: "datetime"
    });
    laydate.render({
        elem: "#endTime",
        type: "datetime"
    });
});