$(document).ready(function () {

    $('#aHospital').click(anwHospital);

    function anwHospital(){
        let hospital = {
            name: $("#nam").val(),
            town: $("#town").val(),
            region: $("#rgion").val()
        }

        console.log(hospital);

        $.ajax({
            type: 'POST',
            url: '/hospital/add',
            dataType: "json",
            data: JSON.stringify(hospital),
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Content-Type', 'application/json')
            },
            success: function (response, status, xhr) {
                console.log(response.codeHospital);

                $('#ohospital').html("<label class='form-label'>Secret code of the hospital: "+response.codeHospital+"</label>");
                // window.location = '/login';
            },
            error: function (response, status, xhr) {
                console.log(response);
            }
        })
    }

});