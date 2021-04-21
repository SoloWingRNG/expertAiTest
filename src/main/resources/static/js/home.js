var dataTable = null;


$(function () {

    dataTable = $('#tableFile').DataTable({
        processing: true,
        serverSide: true,
        ajax: {
            contentType: 'application/json',
            url: '/file/table',
            type: 'POST',
            dataType: "json",
            data: function (data, jqXHR) {
                return JSON.stringify(data);
            },
            beforeSend: function (jqXHR, settings) {
            },
            complete: function (jqXHR, textStatus) {
                $("button[name='btnDelete']").on('click', function (ev) {
                  //  ev.preventDefault();
                    let id = $(this).attr('data');
                    deleteFile(id)
                });

                $("button[name='btnProcess']").on('click', function (ev) {
                   // ev.preventDefault();
                    let id = $(this).attr('data');
                    processFile(id);
                });
            },
            error: function (jqXHR, textStatus, errorThrown) {

            }
        },
        columns: [
            {data: "id"},
            {data: "title"},
            {data: "size"},
            {data: "id", className: "delete"},
            {data: "id", className: "process"}
            ],
        order: [
            [0, "asc"]
        ],

        columnDefs: [
            {
                render: function (data, type, row, meta) {
                    let res = "";
                    if (row['size'] != null) {
                        res += data + " kb";
                    }
                    return res;
                },
                targets: 2
            },{

            render: function (data, type, row, meta) {
                let res = "";
                if (row['id'] != null) {
                        res+= '<button type="button" name="btnDelete" id="btnDelete' + row.id + '" style="margin: 0 auto; display: flex;" class="btn btn-sm glyphicon glyphicon-remove deleteFile" data="' + row['id'] + '"data-toggle="tooltip" title="Delete File" />';
                    }
                return res;
            },
            targets: 3
            },{
            render: function (data,type,row,meta) {
                let res ="";
                if (row['id'] != null) {
                    res+= '<button type="button" name ="btnProcess" id="btnProcess' + row.id + '" style="margin: 0 auto; display: flex;" class="btn btn-sm glyphicon glyphicon-cog" data="' + row['id'] + '"data-toggle="tooltip" title="Process File" />';
                }
                return res;
            },
                targets: 4
            }]
    });
});


function refreshTable() {
    $('#tableFile').DataTable().ajax.reload();
}


function deleteFile(idFile) {

    let options = {
        url: "/deleteFile/" + idFile,
        type: "DELETE",
        data: null,

        success: function (data, textStatus, jqXHR) {
            commonLib.showNotification(jqXHR.responseJSON.response, "success", 10);
            dataTable.ajax.reload();
        },
        beforeSend: function (jqXHR, settings) {
        },
        complete: function (jqXHR, textStatus) {
        },
        error: function (jqXHR, textStatus, errorThrown) {
            commonLib.showNotification(jqXHR.responseJSON.response, "danger", 10);
        }
    };
    $.ajax(options);
}


function processFile(idFile) {

    let options = {
        url: "/processFile/" + idFile,
        type: "POST",
        data: null,

        success: function (data, textStatus, jqXHR) {
            commonLib.showNotification(jqXHR.responseJSON.response, "success", 10);
            dataTable.ajax.reload();
        },
        beforeSend: function (jqXHR, settings) {

        },
        complete: function (jqXHR, textStatus) {

        },
        error: function (jqXHR, textStatus, errorThrown) {
            commonLib.showNotification(jqXHR.responseJSON.response, "danger", 10);
        }
    };
    $.ajax(options);
}
