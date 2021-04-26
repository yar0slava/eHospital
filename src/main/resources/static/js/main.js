document.getElementById("ahosp").style.display = "none";
document.getElementById("signup").style.display = "block";
document.getElementById("signin").style.display = "block";
document.getElementById("logout").style.display = "none";
document.getElementById("prof").style.display = "none";

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
        if(json.authority[0].name === "patient" ||   json.authority[0].name === "doctor"){
            document.getElementById("logout").style.display = "inline";
            document.getElementById("prof").style.display = "inline";
            document.getElementById("signup").style.display = "none";
            document.getElementById("signin").style.display = "none";
        }else if(json.authority[0].name === "admin"){
            document.getElementById("logout").style.display = "inline";
            document.getElementById("prof").style.display = "inline";
            document.getElementById("ahosp").style.display = "inline";
            document.getElementById("signup").style.display = "none";
            document.getElementById("signin").style.display = "none";
        }

    },
    error: function (){
    }
});

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
            url: '/search-hospitals-input',
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

        let town = $("#town").val();
        let region = $("#rgion").val();
        console.log(town);
        console.log(region);

        $.ajax({
            type: 'GET',
            url: '/search-hospitals',
            dataType: 'json',
            data: {
                town: $("#town").val(),
                region: $("#rgion").val()
            },
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Content-Type', 'application/json')
            },
            success: function (res) {
                console.log(res);
                document.getElementById("hospitals").style.display = "none";
                 $('#filtrHospitalsRsult').html(searchResults(res));
                 document.getElementById("filtrHospitalsRsult").style.display = "block";

                // $("#searchInput").val("");
                //
            },
            error: function (response) {
                console.log(response);
            }
        })
    }
function searchResults(res){
    let r = "";
    for(let  i=0; i< res.length;i++){
        r +=
            "<div style='display: inline-block; margin-right: 5px'>"+
                "<form action='#' method='post' id='add-fav'>"+
                    "<div style='width: 350px'>"+
                        "<a style='text-decoration: none' th:href='/hospital/?id="+res[i].id+"'>"+
                            "<div style='height:100%; background-color: white; border-radius: 8px; margin-bottom: 15px; padding-left: 5%; padding-right: 5%'>"+
                                "<img src = ''>"+
                                    "<div>"+
                                        "<h4>"+res[i].name+"</h4>"+
                                        "<div style='padding-bottom: 5px'>"+
                                            "<div style='width:160px;display: inline-block;'>"+
                                                "<p><span>"+res[i].town+"</span>, <span>"+res[i].region+"</span></p>"+
                                                 "<p><span></span></p>"+
                                            "</div>"+
                                            "<div style='width:20px; display: inline-block;'></div>"+
                                            "<div style='width: 120px; display: inline-block;'>"+
                                                "<button style='border-radius:8px; background: #4aa1f3; width: 100%;text-shadow: none; color:white !important;padding: 5px 2px 5px 2px;font-weight: bold;'>View details</button>"+
                                            "</div>"+
                                        "</div>"+
                                    "</div>"+
                            "</div>"+
                        "</a>"+
                    "</div>"+
                "</form>"+
            "</div>";

    }
    return r;
}

});