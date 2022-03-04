layui.use(['layer','echarts'], function () {
    var $ = layui.jquery,
        echarts = layui.echarts;

    /**
     * 发送ajax请求，查询饼状图所需的数据
     */
    $.ajax({
        type:"get",
        url:ctx + "/dishestype/countDishesNumberByDishesType",
        dataType:"json",
        success:function (data) {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('make'));

            // 指定图表的配置项和数据
            var option = {
                title: {
                    text: '菜品类别统计',
                    left: 'center',
                    // 主标题文本样式设置
                    textStyle: {
                        fontSize: 28,
                        fontWeight: 'bolder',
                    }
                },
                tooltip: {
                    trigger: 'item',
                    formatter: '{a} <br/>{b} : {c}个 ({d}%)'
                },
                legend: {
                    left: 'center',
                    top: 'bottom',
                    itemWidth: 24,   // 设置图例图形的宽
                    itemHeight: 18,  // 设置图例图形的高
                    data: data.data1
                },
                toolbox: {
                    show: true,
                    feature: {
                        mark: {show: true},
                        dataView: {show: true, readOnly: false},
                        magicType: {
                            show: true,
                            type: ['pie', 'funnel']
                        },
                        restore: {show: true},
                        saveAsImage: {show: true}
                    }
                },
                series: [
                    {
                        name: '半径模式',
                        type: 'pie',
                        radius: '50%',
                        center: ['25%', '50%'],
                        roseType: 'radius',
                        label: {
                            show: true
                        },
                        emphasis: {
                            label: {
                                show: true
                            }
                        },
                        data: data.data2
                    },
                    {
                        name: '面积模式',
                        type: 'pie',
                        radius: '50%',
                        center: ['75%', '50%'],
                        roseType: 'area',
                        data: data.data2
                    }
                ]
            };

            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        }
    });

});