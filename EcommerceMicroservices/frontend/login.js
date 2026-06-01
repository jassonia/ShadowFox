async function login() {

    const username =
    document.getElementById(
        "username"
    ).value;

    const password =
    document.getElementById(
        "password"
    ).value;

    const role =
    document.querySelector(
        'input[name="role"]:checked'
    ).value;

    /* ADMIN LOGIN */

    if(
        role === "admin"
    ){

        if(
            username === "admin" &&
            password === "admin5674"
        ){
            sessionStorage.setItem(
            "loggedIn",
            "true"
        );

            window.location.href =
            "admin.html";
        }

        else{

            alert(
                "Invalid Admin Credentials"
            );
        }

        return;
    }

    /* CUSTOMER LOGIN */

    try{

    const response =
    await fetch(
        "http://localhost:8081/customers/login",
        {

            method:"POST",

            headers:{
                "Content-Type":"application/json"
            },

            body:JSON.stringify({

                username: username,

                password: password
            })
        }
    );

    /* LOGIN FAILED */

    if(!response.ok){

        alert(
            "Invalid Username or Password"
        );

        return;
    }

    /* LOGIN SUCCESS */

    const data =
    await response.json();

    alert(
        "Login Successful"
    );
    sessionStorage.setItem(
    "loggedIn",
    "true"
    );
    sessionStorage.setItem(
    "username",
    username
);

    window.location.href =
    "customer.html";
}

catch(error){

    console.log(error);

    alert("Server Error");
}
}
function toggleRegister() {

    const role =
    document.querySelector(
        'input[name="role"]:checked'
    ).value;

    const registerLink =
    document.getElementById(
        "registerLink"
    );

    /* HIDE FOR ADMIN */

    if(role === "admin"){

        registerLink.style.display =
        "none";
    }

    /* SHOW FOR CUSTOMER */

    else{

        registerLink.style.display =
        "block";
    }
}