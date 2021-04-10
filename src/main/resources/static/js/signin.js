$(document).ready(function () {
    $('#signIn').submit(sendLogin);

    function sendLogin(ev) {
ev.preventDefault();

        let user = {
            email: $("#email").val(),
            password: $("#password").val()
        }

        console.log(user);

        $.ajax({
            type: 'POST',
            url: '/login',
            dataType: 'json',
            data: JSON.stringify(user),
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Content-Type', 'application/json')
            },
            success: function (response, status, xhr) {
                console.log(xhr.responseJSON);
               // localStorage.setItem("token", xhr.getResponseHeader("Authorization"));
                localStorage.setItem("token", xhr.responseJSON.token);
                window.location='/main';
               // getMain();

            },
            error:  function (response, status, xhr) {
                console.log("nnn");

            }

        })

    }
    //
    // function getMain(){
    //         $.ajax({
    //             type: 'GET',
    //             url: '/main',
    //             dataType: 'json',
    //             beforeSend: function (xhr) {
    //                 xhr.setRequestHeader('Content-Type', 'application/json')
    //             },
    //             success: function (res) {
    //                 console.log(res);
    //
    //                 $('#hospitalss').html(searchResults(res)).then(
    //                     window.location = '/main-render'
    //                 );
    //
    //
    //
    //             },
    //             error: function (response) {
    //                 console.log(response);
    //             }
    //         })
    // }

});

// <div th:each="hospital : ${hospitals}">
//
//     <form action="#" method="post" id="add-fav">
//         <div className="item col-md-4">
//             <a style="text-decoration: none"
//                th:href="@{/hospital/(id=${hospital.id})}">
//                 <div className="thumbnail">
//                     <img src="">
//                         <div className="caption">
//                             <h4 th:text="${hospital.name}"></h4>
//                             <div className="row">
//                                 <div className="col-xs-12 col-md-6">
//                                     <p><span th:text="${hospital.town}"></span>, <span
//                                         th:text="${hospital.region}"></span></p>
//                                     <p><span th:text="${hospital.users.size()}"></span> specialists</p>
//                                 </div>
//                                 <div className="col-xs-12 col-md-6">
//                                     <button className="btn add-fav">
//                                         View details
//                                     </button>
//                                 </div>
//                             </div>
//                         </div>
//                 </div>
//             </a>
//         </div>
//     </form>
//
// </div>