layui.use(['table','layer'],function(){
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;


    /**
     * 加载数据表格
     */
    var tableIns = table.render({
        id:'loginTable'
        // 容器元素的ID属性值
        ,elem: '#loginList'
        // 容器的高度 full-差值
        ,height: 'full-125'
        // 单元格最小的宽度
        ,cellMinWidth:95
        // 访问数据的URL（后台的数据接口）
        ,url: ctx + '/loginfo/list'
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
            {type:'checkbox', fixed:'center'}
            ,{field: 'id', title: '编号',  sort: true, fixed: 'left'}
            ,{field: 'loginName', title: '登录用户名', align:'center'}
            ,{field: 'loginIp', title: '登录地址', align:'center'}
            ,{field: 'loginDate', title: '登录时间', align:'center'}
            ,{title:'操作',templet:'#loginListBar', fixed: 'right', align:'center', minWidth:150}
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
                loginName: $("[name='loginName']").val() // 登录名称
                ,loginIp: $("[name='loginIp']").val() // 登录IP
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
    table.on('toolbar(logins)', function (data) {
        if (data.event == "del") { // 删除登录日志
            // 获取被选中的数据的信息
            var checkStatus = table.checkStatus(data.config.id);
            // console.log(checkStatus);
            // 删除多个登录日志
            deleteLoginfos(checkStatus.data);
        }
    });
    
    /**
     * 删除多条登录日志
     * @param loginData
     */
    function deleteLoginfos(loginData) {
        // 判断用户是否选择了要删除的记录
        if (loginData.length == 0) {
            layer.msg("请选择要删除的日志！", {icon:5});
            return;
        }
        // 询问用户是否确认删除
        layer.confirm('您确定要删除选中的日志吗？',{icon:3, title:'登录日志管理'}, function (index) {
            // 关闭确认框
            layer.close(index);
            // 传递的参数是数组   ids=1&ids=2&ids=3
            var ids = "ids=";
            // 循环选中的行记录的数据
            for(var i = 0; i < loginData.length; i++) {
                if(i < loginData.length -1) {
                    ids = ids + loginData[i].id + "&ids="
                } else {
                    ids = ids + loginData[i].id;
                }
            }
            // console.log(ids);

            // 发送ajax请求，执行删除登录日志
            $.ajax({
                type:"post",
                url:ctx + "/loginfo/delete",
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
    table.on('tool(logins)', function (data) {

        if (data.event == "del") { // 删除登录日志
            // 删除单条登录日志
            deleteLoginfo(data.data.id);
        }
    });

    /**
     * 删除单条登录日志
     * @param id
     */
    function deleteLoginfo(id) {
        // 弹出确认框，询问用户是否确认删除
        layer.confirm('确定要删除该日志吗？',{icon:3, title:"登录日志管理"}, function (index) {
            // 关闭确认框
            layer.close(index);

            // 发送ajax请求，删除记录
            $.ajax({
                type:"post",
                url:ctx + "/loginfo/delete",
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