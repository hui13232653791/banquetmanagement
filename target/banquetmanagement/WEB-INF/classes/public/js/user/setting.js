layui.use(['form','jquery','jquery_cookie'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        $ = layui.jquery_cookie($);

    /**
     * 表单的submit监听
     *      form.on('submit(按钮元素的lay-filter属性值)', function (data) {

            });
     */
    form.on('submit(saveBtn)', function (data) {
        // 所有表单元素的值
        console.log(data.field);

        // 发送ajax请求
        $.ajax({
            type:"post",
            url:ctx + "/user/updateSetting",
            data:{
                id:data.field.id,
                phone:data.field.phone,
                email:data.field.email,
                trueName:data.field.trueName
            },
            success:function (result) {
                // 判断是否修改成功
                if (result.code == 200) {
                    // 成功
                    // 提示成功
                    top.layer.msg("操作成功！",{icon:6});
                    // 刷新父窗口，重新加载数据
                    parent.location.reload();

                } else {
                    layer.msg(result.msg, {icon:5});
                }
            }

        });
    });


});