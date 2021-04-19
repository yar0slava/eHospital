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

       }
        $.ajax({
            type: 'POST',
            url: '/add',
            data: JSON.stringify(user),
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Content-Type', 'application/json')
            },
            success: function (response) {
                console.log(response);
                // window.location = '/login';
            },
            error: function (response) {
                alert("Account with this email already exists");
            }
        })

    }


    // $(function() {
    //
    //
    //     $('input[name="daterange"]').daterangepicker();
    // });
});