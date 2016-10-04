$(function () {

    $.getJSON('getTrends', function (data) {
        console.log(data)

        var ds = new Array();

        $(data).each(function () {
            ds.push({
                data: [[this.week, this.totalDemand]]
            });
            ds.push({
                data: [[this.week, this.totalOutput]]

            });
        });


        $.plot($("#line_trend"), ds, {
            colors: ["#ee7951", "#6db6ee"],
            series: {
                bars: {
                    show: true,
                    barWidth: 0.6
                }
            }, grid: {
                hoverable: true
            }, xaxis: {
                mode: "categories"
            }
        });
    });
    loadData(1);
    var loaded = {1: true}
    $('.nav-tabs a').on('click', function () {
        var weekId = $(this).attr('ref');
        if (!loaded[weekId]) {
            loadData(weekId);
            loaded[weekId] = true;
        }
    });
    function loadData(i) {

        $('#datatable' + i).DataTable({
            'ajax': {url: 'getTable/' + (i - 1), cache: false},
            "bJQueryUI": false,
            "bAutoWidth": false,
            "sPaginationType": "full_numbers",
            "sDom": '<"datatable-header"fl><"datatable-scroll"t><"datatable-footer"ip>',
            "oLanguage": {
                "sSearch": "<span>Filter:</span> _INPUT_",
                "sLengthMenu": "<span>Show entries:</span> _MENU_",
                "oPaginate": {"sFirst": "First", "sLast": "Last", "sNext": ">", "sPrevious": "<"}
            }
        });

        $.getJSON('getData/' + (i - 1), function (data) {
            console.log(data)
            $('#optManCnt' + i).text(Math.round(data.optManCnt));
            $('#optManCntDmd' + i).text(Math.round(data.optManCntDmd));
            $('#eqCnt' + i).text(Math.round(data.equipCnt));
            $('#manCnt' + i).text(data.totalManpower);
            $('#grpCnt' + i).text(data.groupSetups.length);
            $('#file-lbl' + i).text(data.fileName + ' - ' + data.dateStr);


            // demp time!





            var ds = new Array();
            ds.push({
                data: [["Total Demand", data.totalDemand], ["Potential equipments Output", data.potEqOut], ["Potential Man Output", data.potManOut]]
            });

            ds.push({
                data: [["Total Demand", data.totalOut], ["Potential equipments Output", data.totalOut], ["Potential Man Output", data.totalOut]]

            });

            $.plot($('#demp' + i), ds, {
                colors: ["#ee7951", "#6db6ee", "#95c832", "#993eb7", "#3ba3aa"],
                series: {
                    bars: {
                        show: true,
                        barWidth: 0.6,
                        align: "center"
                    }
                },
                xaxis: {
                    mode: "categories",
                    categories: ["Total Demand", "Potential equipments Output", "Potential Man Output"]
                }
            });





            var groupSetup = data.groupSetups;

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
                }, yaxis: {min: 0, max: 100}
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

            var previousPoint;
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

                        var y = Math.round(item.datapoint[1] * 100) / 100;
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

                        var y = Math.round(item.datapoint[1] * 100) / 100;
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
                        var y = Math.round(outputPct[item.dataIndex][1] * 100) / 100;
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
}
);
