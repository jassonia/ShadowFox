const ORDER_API =
"http://localhost:8083/orders";

const PRODUCT_API =
"http://localhost:8082/products";

async function payNow(){

    try{

        const paymentMethod =
        document.querySelector(
            'input[name="payment"]:checked'
        );

        if(!paymentMethod){

            alert(
                "Select Payment Method"
            );

            return;
        }

        const order =
        JSON.parse(
            sessionStorage.getItem(
                "pendingOrder"
            )
        );

        if(!order){

            alert(
                "No Order Found"
            );

            return;
        }

        /* SAVE PAYMENT METHOD */

        order.paymentMethod =
        paymentMethod.value;

        /* PAYMENT STATUS */

        if(
            paymentMethod.value ===
            "Cash On Delivery"
        ){

            order.paymentStatus =
            "Pending";
        }
        else{

            order.paymentStatus =
            "Paid";
        }

        /* UPI VALIDATION */

        if(
            paymentMethod.value === "UPI"
        ){

            const upiId =
            document.getElementById(
                "upiId"
            ).value;

            if(!upiId){

                alert(
                    "Enter UPI ID"
                );

                return;
            }
        }

        /* CARD VALIDATION */

        if(
            paymentMethod.value ===
            "Credit Card" ||
            paymentMethod.value ===
            "Debit Card"
        ){

            const cardNumber =
            document.getElementById(
                "cardNumber"
            ).value;

            const cardHolder =
            document.getElementById(
                "cardHolder"
            ).value;

            const expiry =
            document.getElementById(
                "expiry"
            ).value;

            const cvv =
            document.getElementById(
                "cvv"
            ).value;

            if(
                !cardNumber ||
                !cardHolder ||
                !expiry ||
                !cvv
            ){

                alert(
                    "Fill all card details"
                );

                return;
            }
        }

        /* SAVE ORDER */

        const response =
        await fetch(
            ORDER_API,
            {

                method:"POST",

                headers:{
                    "Content-Type":
                    "application/json"
                },

                body:JSON.stringify(order)
            }
        );

        if(!response.ok){

            alert(
                "Order Save Failed"
            );

            return;
        }
        
        /* UPDATE STOCK */

        const productResponse =
        await fetch(
            `${PRODUCT_API}/${order.productId}`
        );

        const product =
        await productResponse.json();

        product.stock =
            order.currentStock -
            order.quantity;

        await fetch(
            `${PRODUCT_API}/${order.productId}`,
            {

                method:"PUT",

                headers:{
                    "Content-Type":
                    "application/json"
                },

                body:JSON.stringify(product)
            }
        );

        if(
            paymentMethod.value ===
            "Cash On Delivery"
        ){

            alert(
                "Order Confirmed. Payment will be collected during delivery."
            );
        }
        else{

            alert(
                "Payment Successful. Order Confirmed."
            );
        }

        sessionStorage.removeItem("pendingOrder");
        
        window.location.href =
        "customer.html";
    }

    catch(error){

        console.log(error);

        alert(
            "Payment Failed"
        );
    }

    }

function showFields(){

   const selected =
    document.querySelector(
        'input[name="payment"]:checked'
    );

    if(!selected){
        return;
    }

    const method =
    selected.value;

    document.getElementById(
        "upiSection"
    ).style.display =
    method === "UPI"
    ? "block"
    : "none";

    document.getElementById(
        "cardSection"
    ).style.display =
    method === "Credit Card" ||
    method === "Debit Card"
    ? "block"
    : "none";

    document.getElementById(
        "codSection"
    ).style.display =
    method === "Cash On Delivery"
    ? "block"
    : "none";
}