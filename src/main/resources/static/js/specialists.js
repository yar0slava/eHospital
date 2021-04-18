$(document).ready(function () {
    alert("YAS");
    document.getElementById("filtrHospitalsRsult").style.display = "none";

    $('#filtrHospitals').click(filtr);

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
                // document.getElementById("hospitals").style.display = "none";
                // $('#filtrHospitalsRsult').html(searchResults(res));
                // document.getElementById("filtrHospitalsRsult").style.display = "block";

                // $("#searchInput").val("");
                //
            },
            error: function (response) {
                console.log(response.responseText);
            }
        })
    }

});