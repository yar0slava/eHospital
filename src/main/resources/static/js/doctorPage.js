$(document).ready(function () {

    $.ajax({
        type: "GET",
        url: "http://localhost:8080/appointments?page=0&size=10",
        dataType: "json",
        success: function (json) {
            console.log(json);
            createListAppointments(json);
        }
    });

    $('input[name="daterange"]').daterangepicker({
        opens: 'right'
    });

    $('#nwDates').click(addNwDate);

    function addNwDate() {

        let s = $('input[name="daterange"]').val();
        let bgin = s.substring(0, 10);
        let finish = s.substring(13, 23);

        let bd = new Date(bgin).toISOString().substring(0, 19);
        let fd = new Date(finish).toISOString().substring(0, 19);

        let u = {
            doctorId: 1245,
            from: bd,
            to: fd
        }
        console.log(u);
        $.ajax({
            type: 'POST',
            url: '/appointments/add',
            data: JSON.stringify(u),
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Content-Type', 'application/json')
            },
            success: function (response) {
                console.log(response);
            },
            error: function (response) {

            }
        })
    }

    function createListAppointments(json) {

        $("#appointmentsList").empty();

        json.forEach(function (object) {

            $("#appointmentsList").append('\
                                <tr><td id="id">'+object.id+'</td>\
                                <td id="dateTime">'+object.dateTime.substring(0, 10)+'</td>\
                                <td id="dateTime">'+object.dateTime.substring(11, 16)+'</td>\
                                <td id="patient">'+object.patientId+'</td></tr>');

            // $("#appointmentsList").find("#id").last().text(object.id);
            // $("#appointmentsList").find("#dateTime").last().text(object.dateTime);
            // $("#appointmentsList").find("#patient").last().text(object.patientId);

        });
    };

});