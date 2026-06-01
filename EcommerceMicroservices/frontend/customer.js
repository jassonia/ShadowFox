const username =
sessionStorage.getItem(
    "username"
);

if(!username){

    window.location.href =
    "login.html";
}

let allUserOrders = [];

const API_URL = "http://localhost:8082/products";

const ORDER_API = "http://localhost:8083/orders";

/* WINDOW LOAD */

window.onload = function () {

    const username =
    sessionStorage.getItem("username");

    document.getElementById(
        "welcomeUser"
    ).innerText =
    "Welcome, " + username;

    document.getElementById("orderQuantity")
        .addEventListener("input", calculateTotal);

    loadProducts();

    loadOrders();
};

/* AUTO TOTAL */

function calculateTotal() {

    const quantity =
        document.getElementById("orderQuantity").value;

    const price =
        document.getElementById("orderQuantity")
        .dataset.price;

    const total = quantity * price;

    document.getElementById("orderPrice")
        .value = total || "";
}

/* LOAD PRODUCTS */

async function loadProducts() {

    const response =
        await fetch(API_URL);

    const products =
        await response.json();

    let output = "";

    products.forEach(product => {

        output += `

        <div class="product
            ${product.stock < 5 ? 'low-stock' : ''}
        ">

            <div class="product-info">

                <h3>${product.name}</h3>

                <p>Price: ₹${product.price}</p>

                <p>Stock: ${product.stock}</p>

                ${
                    product.stock === 0
                    ?
                    '<p style="color:red;font-weight:bold;">Out Of Stock</p>'
                    :
                    ''
                }

                <button
                    onclick="quickOrder(
                        ${product.id},
                        '${product.name}',
                        ${product.price},
                        ${product.stock},
                        '${product.imageUrl}'
                    )"
                    class="order-btn"
                    ${product.stock === 0 ? 'disabled' : ''}
                >
                    Order Now
                </button>

            </div>

            <img
                src="${product.imageUrl}"
                alt="Product Image"
            >

        </div>

        `;
    });

    document.getElementById("productList")
        .innerHTML = output;
}

/* QUICK ORDER */

function quickOrder(
    id,
    name,
    price,
    stock,
    imageUrl
) {

    document.getElementById("orderProduct")
        .value = name;

    document.getElementById("orderQuantity")
        .value = "";

    document.getElementById("orderPrice")
        .value = "";

    const quantityField =
        document.getElementById("orderQuantity");

    quantityField.dataset.price = price;

    quantityField.dataset.id = id;

    quantityField.dataset.stock = stock;

    quantityField.dataset.image = imageUrl;

    quantityField.focus();
}

/* PLACE ORDER */

async function placeOrder() {

    try {

        const productName =
            document.getElementById("orderProduct").value;

        const quantity =
            parseInt(
                document.getElementById("orderQuantity").value
            );

        const price =
            parseFloat(
                document.getElementById("orderPrice").value
            );

        const quantityField =
            document.getElementById("orderQuantity");

        const imageUrl =
            quantityField.dataset.image;

        const productId =
            quantityField.dataset.id;

        const currentStock =
            parseInt(
                quantityField.dataset.stock
            );

        const username =
            sessionStorage.getItem("username");

        if (!productName || !quantity || !price) {

            alert("Please fill all order fields");

            return;
        }

        if (quantity > currentStock) {

            alert("Not enough stock available");

            return;
        }

        const order = {

            username: username,

            productName: productName,

            quantity: quantity,

            price: price,

            imageUrl: imageUrl,

            orderDate:
                new Date().toLocaleString(),

            status: "ACTIVE",

            productId: productId,

            currentStock: currentStock
        };

        sessionStorage.setItem(
            "pendingOrder",
            JSON.stringify(order)
        );

        window.location.href =
            "payment.html";

    } catch (error) {

        console.log(error);

        alert("Error placing order");
    }

}



/* LOAD ORDERS */

async function loadOrders() {

    const response =
        await fetch(ORDER_API);

    const orders =
        await response.json();

    const username =
        sessionStorage.getItem(
            "username"
        );

    /* ONLY CURRENT USER ORDERS */

    const userOrders =
        orders.filter(order =>
            order.username === username
        );

        allUserOrders = userOrders;

    document.getElementById(
        "totalOrders"
    ).innerText =
        userOrders.length;

    let output = "";

    /* NO ORDERS */

    if (userOrders.length === 0) {

        output = `

        <div class="product">

            <div class="product-info">

                <h3>No Orders Yet</h3>

            </div>

        </div>

        `;

        document.getElementById(
            "orderList"
        ).innerHTML = output;

        return;
    }

    /* DISPLAY ORDERS */

    userOrders.forEach(order => {

        output += `

        <div class="product">

            <div class="product-info">

                <h3>${order.productName}</h3>

                <p>
                    Quantity:
                    ${order.quantity}
                </p>

                <p>
                    Total:
                    ₹${order.price}
                </p>

                <p>
                    Date:
                    ${order.orderDate}
                </p>

                <p>
                    Customer:
                    ${order.username}
                </p>

                <p>

                    Status :

                    <span
                    style="
                    font-weight:bold;
                    color:${
                        order.status === "ACTIVE"
                        ? "green"
                        : "red"
                    };
                    ">

                    ${order.status}

                    </span>

                </p>

                <p>Payment : ${order.paymentMethod} </p>

                <p>Payment Status : ${order.paymentStatus}</p>

                ${order.notification ?
                    `
                    <p style="color:blue;font-weight:bold;">
                    🔔 ${order.notification}
                    </p>
                    `
                    : ""
                    }

                ${
                    order.status === "ACTIVE"
                    ?
                    `
                    <button
                        onclick="cancelOrder(
                            ${order.id},
                            '${order.productName}',
                            ${order.quantity}
                        )"
                        class="cancel-btn"
                    >
                        Cancel Order
                    </button>

                    <button
                        onclick="viewInvoice(${order.id})"
                        class="invoice-btn"
                    >
                        Invoice
                    </button>
                    `
                    :
                    `
                    <button
                        class="cancelled-btn"
                        disabled
                    >
                        Cancelled
                    </button>

                    <button
                        onclick="viewInvoice(${order.id})"
                        class="invoice-btn"
                    >
                        Invoice
                    </button>
                    `
                }

            </div>

            <img
                src="${order.imageUrl}"
                alt="Order Image"
            >

        </div>

        `;
    });

    document.getElementById(
        "orderList"
    ).innerHTML = output;
}

/* CANCEL ORDER */

async function cancelOrder(
    orderId,
    productName,
    quantity
) {

    const response =
        await fetch(API_URL);

    const products =
        await response.json();

    const product =
        products.find(
            p => p.name === productName
        );

    /* RETURN STOCK */

    if (product) {

        product.stock =
            parseInt(product.stock) +
            parseInt(quantity);

        await fetch(
            `${API_URL}/${product.id}`,
            {
                method: "PUT",

                headers: {
                    "Content-Type":
                    "application/json"
                },

                body: JSON.stringify(product)
            }
        );
    }

    /* GET ORDER */

    const orderResponse =
        await fetch(
            `${ORDER_API}/${orderId}`
        );

    const order =
        await orderResponse.json();

    /* CHANGE STATUS */

    order.status = "CANCELLED";

    if(
        order.paymentMethod !==
        "Cash On Delivery"
    ){

        order.paymentStatus =
        "REFUNDED";

        order.notification =
        "Your payment has been refunded successfully.";
    }
    else{

        order.paymentStatus =
        "NOT PAID";

        order.notification =
        "Order cancelled.";
    }

    /* UPDATE ORDER */

    await fetch(
        `${ORDER_API}/${orderId}`,
        {
            method: "PUT",

            headers: {
                "Content-Type":
                "application/json"
            },

            body: JSON.stringify(order)
        }
    );

    await loadProducts();

    await loadOrders()

    alert(
        "Order Cancelled Successfully"
    );
}
/* SEARCH */

function searchProduct() {

    const searchValue =
        document.getElementById("search")
        .value
        .toLowerCase();

    const products =
        document.querySelectorAll(".product");

    products.forEach(product => {

        const text =
            product.innerText.toLowerCase();

        if (text.includes(searchValue)) {

            product.style.display = "flex";

        } else {

            product.style.display = "none";
        }
    });
}

/* CLEAR ORDER */

function clearOrderFields() {

    document.getElementById("orderProduct")
        .value = "";

    document.getElementById("orderQuantity")
        .value = "";

    document.getElementById("orderPrice")
        .value = "";
}
function logout(){

    sessionStorage.clear();

    alert("Logged Out");

    window.location.href =
    "login.html";
}
function displayOrders(orderList){

    let output = "";

    orderList.forEach(order => {

        output += `
        <div class="product">

            <div class="product-info">

                <h3>${order.productName}</h3>

                <p>Quantity: ${order.quantity}</p>

                <p>Total: ₹${order.price}</p>

                 <p>
                    Date:
                    ${order.orderDate}
                </p>

                <p>
                    Customer:
                    ${order.username}
                </p>

                <p>Status:
                <span style="
                color:${order.status === 'ACTIVE'
                    ? 'green'
                    : 'red'};
                font-weight:bold;">
                ${order.status}
                </span>
                </p>
                 <p>
                    Payment :
                    ${order.paymentMethod}
                </p>

                <p>
                    Payment Status :
                    ${order.paymentStatus}
                </p>

                ${
                    order.notification
                    ?
                    `
                    <p style="
                    color:blue;
                    font-weight:bold;
                    ">
                    🔔 ${order.notification}
                    </p>
                    `
                    :
                    ""
                }

                ${
                    order.status === "ACTIVE"
                    ?
                    `
                    <button
                        onclick="cancelOrder(
                            ${order.id},
                            '${order.productName}',
                            ${order.quantity}
                        )"
                        class="cancel-btn"
                    >
                        Cancel Order
                    </button>

                    <button
                        onclick="viewInvoice(${order.id})"
                        class="invoice-btn"
                    >
                        Invoice
                    </button>
                    `
                    :
                    `
                    <button
                        class="cancelled-btn"
                        disabled
                    >
                        Cancelled
                    </button>

                    <button
                        onclick="viewInvoice(${order.id})"
                        class="invoice-btn"
                    >
                        Invoice
                    </button>
                    `
                }

            </div>

            <img src="${order.imageUrl}">
        </div>
        `;
    });

    document.getElementById(
        "orderList"
    ).innerHTML = output;
}

function showActiveOrders(){

    const activeOrders =
    allUserOrders.filter(
        order =>
        order.status === "ACTIVE"
    );

    displayOrders(activeOrders);
}

function showCancelledOrders(){

    const cancelledOrders =
    allUserOrders.filter(
        order =>
        order.status === "CANCELLED"
    );

    displayOrders(cancelledOrders);
}
function viewInvoice(orderId){

    const order =
    allUserOrders.find(
        o => o.id === orderId
    );

    alert(
        "Invoice No : INV-" + order.id +
        "\nCustomer : " + order.username +
        "\nProduct : " + order.productName +
        "\nQuantity : " + order.quantity +
        "\nPrice : ₹" + order.price +
        "\nPayment : " + order.paymentMethod +
        "\nDate : " + order.orderDate
    );
}