$(document).ready(function () {

    var authority = "";

    $.ajax({
        type: "GET",
        url: "http://localhost:8080/api/users",
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Content-Type', 'application/json'),
                xhr.setRequestHeader('Authorization', localStorage.getItem("token"))
        },
        dataType: "json",
        success: function (json) {
            console.log(json);
            addInfo(json);
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
            // doctorId: 1245,
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
                xhr.setRequestHeader('Authorization', localStorage.getItem("token"))
            },
            success: function (response) {
                console.log(response);
            },
            error: function (response) {

            }
        })
    };

    function createListAppointments(json) {

        $("#appointmentsList").empty();

        json.forEach(function (object) {

            $("#appointmentsList").append('\
                                <tr><td id="id">'+object.id+'</td>\
                                <td id="dateTime">'+object.dateTime.substring(0, 10)+'</td>\
                                <td id="dateTime">'+object.dateTime.substring(11, 16)+'</td>\
                                <td id="patient">'+ ((object.name==null) ? "" : object.name) +'</td></tr>');

            // $("#appointmentsList").find("#id").last().text(object.id);
            // $("#appointmentsList").find("#dateTime").last().text(object.dateTime);
            // $("#appointmentsList").find("#patient").last().text(object.patientId);

        });
    };

    function addInfo(object) {

        authority = object.authority[0].name;


        if(object.authority[0].name=="doctor") {
            authority = "doctor";

            $("#doctorName").text('Doctor ' + object.firstName + " " + object.lastName);

            let spec = "";
            object.specializations.forEach(function (sp) {
                spec += sp.name + " ";
            });

            $("#hospital").text(object.hospital.name);
            $("#specialization").text(spec);
            $("#email").text(object.email);


            $("#patientCol").text("Patient");

            $.ajax({
                type: "GET",
                url: "http://localhost:8080/appointments?page=0&size=10",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Content-Type', 'application/json'),
                        xhr.setRequestHeader('Authorization', localStorage.getItem("token"))
                },
                dataType: "json",
                success: function (json) {
                    console.log(json);
                    createListAppointments(json);
                }
            });

        }else{
            authority = "patient";

            $("#doctorName").text('Patient ' + object.firstName + " " + object.lastName);
            $("#userInfo").empty();

            $("#userInfo").html("<div class=\"row\">\n" +
                "\n" +
                "                <table class=\"table table-sm table-bordered\">\n" +
                "\n" +
                "                    <thead class=\"text-center\">\n" +
                "                    <tr>\n" +
                "                        <th scope=\"col\" class=\"text-center\" colspan=\"2\">Information about patient</th>\n" +
                "                    </tr>\n" +
                "                    </thead>\n" +
                "                    <tbody>\n" +
                "                    <tr>\n" +
                "                        <th scope=\"col\">Birth date</th>\n" +
                "                        <td id=\"specialization\" >"+ object.birthday+ "</td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <th  scope=\"col\">Passport</th>\n" +
                "                        <td id=\"hospital\">"+object.passport+"</td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <th  scope=\"col\">Email</th>\n" +
                "                        <td id=\"email\">"+object.email+"</td>\n" +
                "                    </tr>\n" +
                "\n" +
                "                    </tbody>\n" +
                "                </table>\n" +
                "            </div>");

            $("#patientCol").text("Doctor");

            document.getElementById('pikr').style.display = "none";
            document.getElementById('nwDates').style.display = "none";


            $.ajax({
                type: "GET",
                url: "http://localhost:8080/appointments/patient",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader('Content-Type', 'application/json'),
                        xhr.setRequestHeader('Authorization', localStorage.getItem("token"))
                },
                dataType: "json",
                success: function (json) {
                    console.log(json);
                    createListAppointments(json);
                }
            });
        }
    };
});