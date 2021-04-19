$(document).ready(function () {
    $('input[name="daterange"]').daterangepicker({
        opens: 'right'
    });

    $('#nwDates').click(addNwDate);

    function addNwDate() {

       let s = $('input[name="daterange"]').val();
       let bgin = s.substring(0,10);
       let finish = s.substring(13,23);

       let bd = new Date(bgin).toISOString().substring(0,19);
       let fd = new Date(finish).toISOString().substring(0,19);

       let u = {
           doctorId:1245,
           from: bd,
           to:fd
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

});