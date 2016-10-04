$(function () {
    //===== File uploader =====//

    $("#uploader").pluploadQueue({
        runtimes: 'html5,html4',
        url: 'upload'
    });

    $('#genBtn').hide()
    var uploader = $("#uploader").pluploadQueue()
    uploader.bind('UploadComplete', function () {
        if ((uploader.total.uploaded) == uploader.files.length) {
            console.log('show')
            $('#genBtn').fadeIn();
        }
    });
});