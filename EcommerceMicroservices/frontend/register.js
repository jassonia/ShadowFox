async function register() {

    const username =
    document.getElementById(
        "username"
    ).value;

    const password =
    document.getElementById(
        "password"
    ).value;

    if(!username || !password){

        alert("Please fill all fields");

        return;
    }

    const user = {

        username: username,

        password: password
    };

    try{

        const response =
        await fetch(
            "http://localhost:8081/customers/register",
            {

                method:"POST",

                headers:{
                    "Content-Type":"application/json"
                },

                body:JSON.stringify(user)
            }
        );

        if(response.ok){

            alert(
                "Registration Successful"
            );

            window.location.href =
            "login.html";
        }

        else if(response.status == 409){

    alert(
        "User already exists"
    );
}

else{

    alert(
        "Registration Failed"
    );
}

    }

    catch(error){

        console.log(error);

        alert("Server Error");
    }
}