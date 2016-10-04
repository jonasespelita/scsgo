$(function () {


    var d1 = [];
    for (var i = 0; i <= 100; i += 1) {
        d1.push([i, parseInt(Math.random() * 30)]);
    }

    var d2 = [];
    for (var i = 0; i <= 100; i += 1) {
        d2.push([i, parseInt(Math.random() * 30)]);
    }
    //Display graph
    $.plot($("#vertical_bars"), [d1, d2], {
        colors: ["#ee7951", "#6db6ee", "#95c832", "#993eb7", "#3ba3aa"],
        series: {
            labels: ["A", "B"],
            bars: {
                show: true,
                barWidth: 0.6
            }
        }, grid: {
            hoverable: true
        }
    });

    $.plot($("#vertical_bars_pct"), [d1, d2], {
        colors: ["#ee7951", "#6db6ee", "#95c832", "#993eb7", "#3ba3aa"],
        bars: {
            show: true,
            barWidth: 0.6
        },
        grid: {
            hoverable: true
        }
    });
    $.plot($("#man_dist"), [d1], {
        colors: ["#ee7951", "#6db6ee", "#95c832", "#993eb7", "#3ba3aa"],
        bars: {
            show: true,
            barWidth: 0.6
        },
        grid: {
            hoverable: true
        }
    });
    
    

    $.plot($("#line_trend"), [d1, d2], {
        colors: ["#ee7951", "#6db6ee", "#95c832", "#993eb7", "#3ba3aa"],
        grid: {
            hoverable: true
        }
    });

//add tooltip event
    $("#vertical_bars_pct").bind("plothover", function (event, pos, item) {
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

                showTooltip(item.pageX + 5, item.pageY + 5, x + " = " + y);

            }
        } else {
            $('.chart-tooltip').remove();
            previousPoint = null;
        }

    });

    function showTooltip(x, y, contents, areAbsoluteXY) {
        var rootElt = 'body';

        $('<div id="tooltip" class="chart-tooltip">' + contents + '</div>').css({
            top: y - 50,
            left: x - 6,
            opacity: 0.9
        }).prependTo(rootElt).show();
    };
});