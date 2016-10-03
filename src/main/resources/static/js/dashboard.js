$(function () {
    //===== File uploader =====//

    $("#uploader").pluploadQueue({
        runtimes: 'html5,html4',
        url: 'upload'
//        max_file_size: '2mb'
//        unique_names: true,
//        filters: [
//            {title: "CSV files", extensions: "csv"}
//        ]
    });
});