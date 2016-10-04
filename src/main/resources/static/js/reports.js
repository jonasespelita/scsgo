$(function () {
    loadData(1);
    var loaded = {1: true}
    $('.nav-tabs a').on('click', function () {
        var weekId = $(this).attr('ref');
        if (!loaded[weekId]) {
            loadData(weekId);
        }
    });
    function loadData(i) {

        $.getJSON('getData/' + (i - 1), function (data) {
            console.log(data);
            var fileName = data.fileName;
            $('#file-lbl' + i).text(fileName);
            var groupSetup = data.groupSetup;

            var labels = [];
            var demandQty = [];
            var outputQty = [];
            var demandPct = [];
            var outputPct = [];
            var manpowerDst = [];

            $(groupSetup).each(function (index) {
                labels.push(this.name);
                manpowerDst.push([index, this.manpower]);
                demandQty.push([index, this.demand]);
                outputQty.push([index, this.totalOutput]);
                demandPct.push([index, 100]);
                outputPct.push([index, (this.totalOutput / this.demand) * 100]);
            });
//        console.log(demandQty)
//        console.log(outputQty)
            $.plot($("#vertical_bars" + i), [demandQty, outputQty], {
                colors: ["#ee7951", "#6db6ee", "#95c832", "#993eb7", "#3ba3aa"],
                series: {
                    labels: labels,
                    stack: false,
                    bars: {
                        show: true,
                        barWidth: 0.6
                    }
                }, grid: {
                    hoverable: true
                }
            });

            $.plot($("#vertical_bars_pct" + i), [demandPct, outputPct], {
                colors: ["#ee7951", "#6db6ee", "#95c832", "#993eb7", "#3ba3aa"],
                series: {
                    labels: labels,
                    stack: false,
                    bars: {
                        show: true,
                        barWidth: 0.6
                    }
                },
                grid: {
                    hoverable: true
                }
            });

            $.plot($("#man_dist" + i), [manpowerDst], {
                series: {
                    labels: labels,
                    stack: false,
                    bars: {
                        show: true,
                        barWidth: 0.6
                    }
                },
                grid: {
                    hoverable: true
                }
            });


            $("#vertical_bars" + i).bind("plothover", function (event, pos, item) {
                if (item) {
                    if (previousPoint != item.datapoint) {
                        previousPoint = item.datapoint;
                        //delete de prГ©cГ©dente tooltip
                        $('.chart-tooltip').remove();

                        var x = item.datapoint[0];

                        //All the bars concerning a same x value must display a tooltip with this value and not the shifted value
                        if (item.series.bars.order) {
                            for (var i = 0; i < item.series.data.length; i++) {
                                if (item.series.data[i][3] == item.datapoint[0])
                                    x = item.series.data[i][0];
                            }
                        }

                        var y = item.datapoint[1];
                        var indicator;
                        switch (item.seriesIndex) {
                            case 0:// demand
                                indicator = "Dmd";
                                break;
                            case 1://qty
                                indicator = "Out"

                                break;
                        }
                        showTooltip(item.pageX + 5, item.pageY + 5,
                                item.series.labels[item.dataIndex] + " " + indicator + " = " + y);

                    }
                } else {
                    $('.chart-tooltip').remove();
                    previousPoint = null;
                }

            });

            $("#man_dist" + i).bind("plothover", function (event, pos, item) {
                if (item) {
                    if (previousPoint != item.datapoint) {
                        previousPoint = item.datapoint;
                        //delete de prГ©cГ©dente tooltip
                        $('.chart-tooltip').remove();

                        var x = item.datapoint[0];

                        //All the bars concerning a same x value must display a tooltip with this value and not the shifted value
                        if (item.series.bars.order) {
                            for (var i = 0; i < item.series.data.length; i++) {
                                if (item.series.data[i][3] == item.datapoint[0])
                                    x = item.series.data[i][0];
                            }
                        }

                        var y = item.datapoint[1];
                        var indicator;
                        switch (item.seriesIndex) {
                            case 0:// demand
                                indicator = "Dmd";
                                break;
                            case 1://qty
                                indicator = "Out"

                                break;
                        }
                        showTooltip(item.pageX + 5, item.pageY + 5,
                                item.series.labels[item.dataIndex] + " " + indicator + " = " + y);

                    }
                } else {
                    $('.chart-tooltip').remove();
                    previousPoint = null;
                }

            });

            $("#vertical_bars_pct" + i).bind("plothover", function (event, pos, item) {
                if (item) {
                    if (previousPoint != item.datapoint) {
                        previousPoint = item.datapoint;
                        //delete de prГ©cГ©dente tooltip
                        $('.chart-tooltip').remove();

                        var x = item.datapoint[0];

                        //All the bars concerning a same x value must display a tooltip with this value and not the shifted value
                        if (item.series.bars.order) {
                            for (var i = 0; i < item.series.data.length; i++) {
                                if (item.series.data[i][3] == item.datapoint[0])
                                    x = item.series.data[i][0];
                            }
                        }
                        console.log(outputPct[item.dataIndex][1])
                        var y = outputPct[item.dataIndex][1];
                        var indicator;
                        switch (item.seriesIndex) {
                            case 0:// demand
                                indicator = "Dmd";
                                break;
                            case 1://qty
                                indicator = "Out"

                                break;
                        }
                        showTooltip(item.pageX + 5, item.pageY + 5,
                                item.series.labels[item.dataIndex] + " = " + y);

                    }
                } else {
                    $('.chart-tooltip').remove();
                    previousPoint = null;
                }

            });




        });
    }
    function showTooltip(x, y, contents, areAbsoluteXY) {
        var rootElt = 'body';

        $('<div id="tooltip" class="chart-tooltip">' + contents + '</div>').css({
            top: y - 50,
            left: x - 6,
            opacity: 0.9
        }).prependTo(rootElt).show();
    }
    ;
});
