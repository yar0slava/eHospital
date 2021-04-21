$(document).ready(function () {
    var dateToday = new Date();

    $('#nwDates').click(addNwDate);
    function addNwDate() {
        let mt= $("#meetingTime").val();
        $.ajax({
            type: 'PUT',
            url: "/appointments/signup?meeting="+mt,
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Content-Type', 'application/json');
                xhr.setRequestHeader(localStorage.getItem("token"))
                console.log(xhr);
            },
            success: function (response) {
                alert("Your meeting is created successfully!")
                window.location = '/profile';
            },

            error: function (response) {
                window.location = "/login";
                console.log(response);
            }
        })
        console.log(mt);
    }

    $('input[name="birthday"]').daterangepicker({
        singleDatePicker: true,
        showDropdowns: true,
        minDate: dateToday,
        minYear: new Date().getFullYear(),
        maxYear: new Date().getFullYear()+1
    }, function(start, end, label) {
        let n = start._d.toLocaleString();
        console.log(n);
        let f = n.substring(6,10)+"-"+n.substring(3,5)+"-"+n.substring(0,2)+"T00:00:00";
        console.log(f);

        let id =$("#doctorId").val()
        let myUrl = "/appointments/free?doctorId="+id+"&date="+f;

        $.ajax({
            type: 'GET',
            url: myUrl,
            success: function (response) {
                console.log(response);
                $('#timess').html(searchResults(response));
            },
            error: function (response) {

            }
        })
    });

    function searchResults(r){
        if(r.length===0){
            return "<label class='form-label'>There is no available time for this day.</label>";
        }

        let rs="<label class='form-label'>Choose available time: </label>";

            rs+="<select name='meetingTime' id='meetingTime'>";
        for(let i=0; i<r.length;i++) {
            rs += "<option value='" + r[i].id + "'>" + r[i].dateTime.substring(11, 16) +
                "</option>";
        }
              rs+=  "</select>";

        return rs;
    }
});