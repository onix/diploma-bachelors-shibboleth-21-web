$(document).ready(function () {
        $("#LoadingImage").show();
        $.getJSON('/logs/get-authentication-log', function (data) {
            $("#LoadingImage").hide();
            if (data.length == 0) { //TODO handle 500 status
                $('#container').append('<h3>Empty Log. Wait while someone will try to authenticate.</h3>');
            } else {
                $.each(data, function (index, value) {
                    var idEntry = parseInt(value['idEntry']);
                    var officeId = value['officeId'];
                    var typeOfEntryId;
                    if(value['typeOfEntryId'] == 1) {
                        typeOfEntryId = "OK";
                    } else
                        typeOfEntryId = "Fail";
                    var timeOfAction = value['timeOfAction'];
                    var employeeId = value['idEmployee'] + " " + value['name'] + " " + value['surname'];

                    var row =
                        "<tr>"+
                            "<td>" + idEntry + "</td>"+
                            "<td>" + officeId + "</td>"+
                            "<td>" + typeOfEntryId + "</td>"+
                            "<td>" + timeConverter(timeOfAction) + "</td>"+
                            "<td>" + employeeId + "</td>"+
                        "</tr>";
                    $('#logEntriesList').find('tbody').append(row);
                });
            }
        })
    }
);

function timeConverter(UNIX_timestamp){
    var a = new Date(UNIX_timestamp);
    var months = ['Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec'];
    var year = a.getFullYear();
    var month = months[a.getMonth()];
    var date = a.getDate();
    var hour = a.getHours();
    var min = a.getMinutes();
    var sec = a.getSeconds();
    return date + ', ' + month + ' ' + year + ' @ ' + hour + ':' + min + ':' + sec;
}