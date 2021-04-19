$(document).ready(function () {
    var dateToday = new Date();

    $('input[name="birthday"]').daterangepicker({
        singleDatePicker: true,
        showDropdowns: true,
        minDate: dateToday,
        minYear: new Date().getFullYear(),
        maxYear: new Date().getFullYear()+1
    });

    $('#nwDates').click(addNwDate);
    function addNwDate() {

        let s = $('input[name="birthday"]').val();
        let k = new Date(s).toISOString().substring(0,19);
        let h = k.substring(0,11)+"00"+k.substring(13,19);

        let id =$("#doctorId").val()
        let myUrl = "/appointments/free?doctorId="+id+"&date="+h;

        $.ajax({
            type: 'GET',
            url: myUrl,
            success: function (response) {
                console.log(response);
            },
            error: function (response) {

            }
        })
    }

});