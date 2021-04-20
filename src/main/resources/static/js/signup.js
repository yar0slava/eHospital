document.getElementById("hospital").style.display = "none";
document.getElementById("specializations").style.display = "none";

function sendRegistration() {
    let hospital="";
    if($("#hospitalCode").val() !=="Hospital code"){
        hospital = $("#hospitalCode").val();
    }
    let user = {
        passport: $("#passport").val(),
        email: $("#email").val(),
        password: $("#password").val(),
        firstName: $("#firstName").val(),
        lastName: $("#lastName").val(),
        gender: $("#gender").val(),
        birthday: $("#birthday").val(),
        phone: $("#phone").val(),
        hospitalCode: hospital,
        authority:[$("#role").val()],
        specializations: $("#specializations").val()
    }

    console.log(user);

    $.ajax({
        type: 'POST',
        url: '/signup',
        dataType: "json",
        data: JSON.stringify(user),
        beforeSend: function (xhr) {
            xhr.setRequestHeader('Content-Type', 'application/json')
        },
        success: function (response, status, xhr) {
            console.log(response);
            // window.location = '/login';

        },
        error: function (response, status, xhr) {

            if (response.responseJSON.message === "Wrong hospital code."){
                alert(response.responseJSON.message);
            }else {
                alert("Account with this email already exists");
            }
        }
    })
}

var currentTab = 0;
showTab(currentTab);

function showTab(n) {
    var x = document.getElementsByClassName("tab");
    x[n].style.display = "block";
    if (n == 0) {
        document.getElementById("prevBtn").style.display = "none";
    } else {
        document.getElementById("prevBtn").style.display = "inline";
    }
    if (n == (x.length - 1)) {
        document.getElementById("nextBtn").innerHTML = "Submit";
    } else {
        document.getElementById("nextBtn").innerHTML = "Next";
    }
    fixStepIndicator(n)
}

function nextPrev(n) {
    let rol = $("#role").val();
    if(rol === "doctor"){
        document.getElementById("hospital").style.display = "block";
        document.getElementById("specializations").style.display = "block";

    }

    var x = document.getElementsByClassName("tab");
    if (n == 1 && !validateForm()) return false;
    x[currentTab].style.display = "none";
    currentTab = currentTab + n;
    if (currentTab >= x.length) {
        sendRegistration();
        return false;
    }
    showTab(currentTab);
}

function validateForm() {
    var x, y, i, valid = true;
    x = document.getElementsByClassName("tab");
    y = x[currentTab].getElementsByTagName("input");
    for (i = 0; i < y.length; i++) {
        if (y[i].value == "") {
            y[i].className += " invalid";
            valid = false;
        }
    }
    if (valid) {
        document.getElementsByClassName("step")[currentTab].className += " finish";
    }
    return valid;
}

function fixStepIndicator(n) {
    var i, x = document.getElementsByClassName("step");
    for (i = 0; i < x.length; i++) {
        x[i].className = x[i].className.replace(" active", "");
    }
    x[n].className += " active";
}

$(document).ready(function(){

    $.ajax({
        type: "GET",
        url: "/api/users/specializations",
        dataType: "json",
        success: function (array) {
            array.forEach(function(object){
                $("#specializations").append('<option value="' + object.name + '">' + object.name + '</option>');
            })
        }
    })

})