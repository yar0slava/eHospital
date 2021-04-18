$(document).ready(function () {
    document.getElementById("filtrHospitalsRsult").style.display = "none";

    $('#filtrHospitals').click(filtr);
    $('#search-form').click(search);

    function search(ev){
        ev.preventDefault();
        let searchInput =  $("#search").val();
        console.log(searchInput);

        $.ajax({
            type: 'GET',
            url: '/search-doctors-input',
            dataType: 'json',
            data: {
                search: $("#search").val()
            },
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Content-Type', 'application/json')
            },
            success: function (res) {
                console.log(res);
                document.getElementById("hospitals").style.display = "none";
                $('#filtrHospitalsRsult').html(searchResults(res));
                document.getElementById("filtrHospitalsRsult").style.display = "block";
            },
            error: function (response) {
                console.log(response);
            }
        })

    }

    function filtr(ev){
        ev.preventDefault();

        let specialization = $("#spialization").val();
        let town = $("#town").val();
        let region = $("#rgion").val();

        let myUrl="/search-doctors";
        if(town !== ""){
            myUrl+="?town="+town;
        }
        if(region !== "" && town !== ""){
            myUrl += "&region="+region;
        }
        if(region !== "" && town === ""){
            myUrl += "?region="+region;
        }

        if(specialization.length !== 0){
            for (let i=0; i< specialization.length;i++){
                if(i === 0){
                    if(town !== "" || region !== ""){
                        myUrl+="&";
                    }else {
                        myUrl+="?";
                    }
                    myUrl+= "specialization="+specialization[i];
                }else{
                    myUrl+= "&specialization="+specialization[i];
                }
            }
        }

        console.log("URL");
        console.log(myUrl);
        $.ajax({
            type: 'GET',
            url: myUrl,
            success: function (res) {
                console.log(res);
                document.getElementById("hospitals").style.display = "none";
                 $('#filtrHospitalsRsult').html(searchResults(res));
                 document.getElementById("filtrHospitalsRsult").style.display = "block";

                // $("#searchInput").val("");
                //
            },
            error: function (response) {
                console.log(response.responseText);
            }
        })
    }

    function searchResults(res){
        let r = "";
        for(let  i=0; i< res.length;i++) {
            r +=
                "<div th:each='doctor : ${doctors}' style='display: inline-block;'>" +
                "<form action='#' method='post' id='add-fav'>" +
                "<div style='width: 350px; text-align: center'>" +
                "<a style='text-decoration: none'>" +
                "<div style='background-color: white; border-radius: 8px; margin-bottom: 15px; padding-left: 5%; padding-right: 5%'>" +
                "<img src = ''>" +
                "<div>" +
                "<p> <span>" + res[i].lastName + " "+ "</span><span>" + res[i].firstName + "</span></p>" +
                "<div style='padding-bottom: 5px'>" +
                "<div style='width:330px;display: inline-block; text-align: center'>";

            let m = "";
            for (let j = 0; j < res[i].specialization.length; j++) {
                m += "<span>" + res[i].specialization[j].name + " " + "</span>"
            }

            r += "<div style='display: inline; padding-right: 10px;'>" +
                m +
                "</div>" +
                "<p> <span>" + res[i].hospital.town + "</span>, <span>" + res[i].hospital.region + "</span></p>" +
                "<p> <span>" + res[i].email + "</span></p>" +
                "</div>" +
                "<div style='width: 320px;'>" +
                "<button style='border-radius:8px; background: #4aa1f3; width: 100%;text-shadow: none; color:white !important;padding: 5px 2px 5px 2px;font-weight: bold;'>View details </button>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>" +
                "</a>" +
                "</div>" +
                "</form>" +
                "</div>";
        }
        return r;
    }


});