$(document).ready(function () {
        $("#LoadingImage").show();
        $.getJSON('/employees/get-employees-list', function (data) {
            $("#LoadingImage").hide();
            if (data['employees'].length == 0) { //TODO handle 500 status
                $('#container').append('<h3>Empty employees list. Maybe, you\'ll try to <a href="/employees/create-new-employee" title="Add new employee">add</a> new one?</h3>');
            } else {
                $.each(data['employees'], function (index, value) {
                    var idEmployee = parseInt(value['idEmployee']);
                    var employeeName = value['name'];
                    var employeeSurname = value['surname'];
                    var employeePhone = value['phone'];
                    var employeeEmail = value['email'];

                    var row =
                        "<tr>"+
                            "<td>" + idEmployee + "</td>"+
                            "<td>" + employeeName + "</td>"+
                            "<td>" + employeeSurname + "</td>"+
                            "<td>" + employeePhone + "</td>"+
                            "<td>" + employeeEmail + "</td>"+
                            "<td>" +
                            "<a href=\"/passes/get/" + idEmployee + "\"><button class=\"btn\">Get Pass Image</button></a> " +
                            "<a href=\"/employees/edit/" + idEmployee + "\"><button class=\"btn btn-info\">Edit Employee</button></a> " +
                            "<button class=\"btn btn-danger\">Remove</button>" +
                            "</td>"+
                            "</tr>";
                    $('#employeesList').find('tbody').prepend(row);
                });
                $(".btn-danger").bind("click", removeEmployee);
            }
        })
    }
);

function removeEmployee() {
    var row = $(this).parent().parent(); //tr
    var employeeId = parseInt(row.find("td:first").text());
    $.ajax({
        url: '/employees/delete/' + employeeId,
        type: 'DELETE',
        success: function(result) {
            row.remove();
        }
    });
}
