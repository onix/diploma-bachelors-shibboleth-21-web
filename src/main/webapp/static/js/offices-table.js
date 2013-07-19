$(document).ready(function () {
        $("#LoadingImage").show();
        $.getJSON('/offices/get-offices-list', function (data) {
            $("#LoadingImage").hide();
            if (data['offices'].length == 0) { //TODO handle 500 status
                $('#container').append('<h3>Empty offices list. Maybe, you\'ll try to <a href="/offices/create-new-office" title="Add new office entry">add</a> new one?</h3>');
            } else {
                $.each(data['offices'], function (index, value) {
                    var idOffice = parseInt(value['idOffice']);
                    var officeName = value['name'];
                    var officeAddress = value['address'];

                    var row =
                        "<tr>"+
                        "<td>" + idOffice + "</td>"+
                        "<td>" + officeName + "</td>"+
                        "<td>" + officeAddress + "</td>"+
                        "<td><a href=\"/offices/edit/" + idOffice + "\"><button class=\"btn\">Edit</button></a> " +
                            "<button class=\"btn btn-danger\">Remove</button></td>"+
                        "</tr>";
                    $('#officesList').find('tbody').append(row);
                });
                $(".btn-danger").bind("click", removeOffice);
            }
        })
    }
);

function removeOffice() {
    var row = $(this).parent().parent(); //tr
    var officeId = parseInt(row.find("td:first").text());
    $.ajax({
        url: '/offices/delete/' + officeId,
        type: 'DELETE',
        success: function(result) {
            row.remove();
        }
    });
}
