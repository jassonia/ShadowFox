if(
    sessionStorage.getItem(
        "loggedIn"
    ) != "true"
){

    window.location.href =
    "login.html";
}

const API_URL = "http://localhost:8082/products";

const ORDER_API = "http://localhost:8083/orders";

/* =========================
   WINDOW LOAD
========================= */

window.onload = function () {

    loadProducts();

    loadOrders();
};

/* =========================
   ADD PRODUCT
========================= */

async function addProduct() {

    const name =
        document.getElementById("name").value;

    const price =
        document.getElementById("price").value;

    const stock =
        document.getElementById("stock").value;

    const imageUrl =
        document.getElementById("imageUrl").value;

    /* VALIDATION */

    if (!name || !price || !stock || !imageUrl) {

        alert("Please fill all fields");

        return;
    }

    const product = {

        name: name,

        price: parseFloat(price),

        stock: parseInt(stock),

        imageUrl: imageUrl
    };

    /* SAVE PRODUCT */

    await fetch(API_URL, {

        method: "POST",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify(product)
    });

    clearFields();

    loadProducts();

    alert("Product added successfully");
}

/* =========================
   LOAD PRODUCTS
========================= */

async function loadProducts() {

    const response =
        await fetch(API_URL);

    const products =
        await response.json();

    document.getElementById("totalProducts")
        .innerText = products.length;

    let output = "";

    let notificationMessage = "";

    let lowStock = 0;

    let outStock = 0;

    products.forEach(product => {

        if (product.stock === 0) {

            notificationMessage +=
                "❌ " +
                product.name +
                " is OUT OF STOCK\n";
        }

        else if (product.stock < 5) {

            notificationMessage +=
                "⚠️ Low Stock : " +
                product.name +
                " (" +
                product.stock +
                " left)\n";
        }

        if (product.stock < 5 && product.stock > 0) {

            lowStock++;
        }

        if (product.stock === 0) {

            outStock++;
        }

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
                    onclick="editProduct(
                        ${product.id},
                        '${product.name}',
                        ${product.price},
                        ${product.stock},
                        '${product.imageUrl}'
                    )"
                    class="edit-btn"
                >
                    Edit
                </button>

                <button
                    onclick="deleteProduct(${product.id})"
                    class="delete-btn"
                >
                    Delete
                </button>

            </div>

            <img
            src="${product.imageUrl}"
            alt="Product Image"
            onerror="this.src='https://via.placeholder.com/250'"
            >

        </div>

        `;
    });

    document.getElementById("notificationBox")
    .innerText = notificationMessage ||
    "✅ All products are in stock";

    document.getElementById("productList")
        .innerHTML = output;
    
    document.getElementById("lowStockCount")
    .innerText = lowStock;

    document.getElementById("outStockCount")
    .innerText = outStock;

}

/* =========================
   EDIT PRODUCT
========================= */

async function editProduct(
    id,
    oldName,
    oldPrice,
    oldStock,
    oldImage
) {

    const newName =
        prompt(
            "Enter New Product Name",
            oldName
        );

    const newPrice =
        prompt(
            "Enter New Product Price",
            oldPrice
        );

    const newStock =
        prompt(
            "Enter New Stock Quantity",
            oldStock
        );

    const newImage =
        prompt(
            "Enter New Image URL",
            oldImage
        );

    if (
        !newName ||
        !newPrice ||
        !newStock ||
        !newImage
    ) {

        return;
    }

    const updatedProduct = {

        name: newName,

        price: parseFloat(newPrice),

        stock: parseInt(newStock),

        imageUrl: newImage
    };

    await fetch(`${API_URL}/${id}`, {

        method: "PUT",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify(updatedProduct)
    });

    loadProducts();

    alert("Product updated successfully");
}

/* =========================
   DELETE PRODUCT
========================= */

async function deleteProduct(id) {

    const confirmDelete =
        confirm(
            "Are you sure you want to delete?"
        );

    if (!confirmDelete) {

        return;
    }

    await fetch(`${API_URL}/${id}`, {

        method: "DELETE"
    });

    loadProducts();

    alert("Product deleted successfully");
}

/* =========================
   CLEAR FIELDS
========================= */

function clearFields() {

    document.getElementById("name")
        .value = "";

    document.getElementById("price")
        .value = "";

    document.getElementById("stock")
        .value = "";

    document.getElementById("imageUrl")
        .value = "";
}

/* =========================
   SEARCH PRODUCT
========================= */

function searchProduct() {

    const searchValue =
        document.getElementById("search")
        .value
        .toLowerCase();

    const products =
        document.querySelectorAll(".product");

    products.forEach(product => {

        const productText =
            product.innerText.toLowerCase();

        if (
            productText.includes(searchValue)
        ) {

            product.style.display = "flex";

        } else {

            product.style.display = "none";
        }
    });
}
/* =========================
   LOAD ORDERS
========================= */

async function loadOrders() {

    const response =
        await fetch(ORDER_API);

    const orders =
        await response.json();

    document.getElementById("totalOrders")
        .innerText = orders.length;

    let output = "";

    let totalRevenue = 0;
    if(orders.length === 0){

        document.getElementById("orderList")
        .innerHTML =
        "<h3>No Orders Available</h3>";

        return;
    }

    const activeOrders =
        orders.filter(
            order => order.status === "ACTIVE"
        ).length;

    const cancelledOrders =
        orders.filter(
            order => order.status === "CANCELLED"
        ).length;

    document.getElementById(
            "activeOrders"
        ).innerText = activeOrders;

    document.getElementById(
            "cancelledOrders"
        ).innerText = cancelledOrders;

    orders.forEach(order => {

        if(order.status === "ACTIVE"){

        totalRevenue += order.price;
    }

        output += `

        <div class="product">

            <div class="product-info">

                <h3>${order.productName}</h3>

                <p>Quantity: ${order.quantity}</p>

                <p>Total: ₹${order.price}</p>

                <p>Date: ${order.orderDate}</p>

                <p>Customer: ${order.username}</p>

                <p>Payment : ${order.paymentMethod} </p>

                <p>Payment Status : ${order.paymentStatus} </p>
        
                <p>
                    Status :
                    <span style="
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
                        class="delete-btn"
                    >
                        Cancel Order
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
                    `
                }

            </div>

            <img
                src="${order.imageUrl}"
                alt="Order Image"
                onerror="this.src='https://via.placeholder.com/250'"
            >

        </div>
        `;
    });

    document.getElementById("totalRevenue")
    .innerText = "₹" + totalRevenue;
    
    document.getElementById("orderList")
        .innerHTML = output;
}
function logout(){

    sessionStorage.clear();

    alert("Logged Out Successfully");

    window.location.href =
    "login.html";
}

async function cancelOrder(
    orderId,
    productName,
    quantity
) {

    /* RETURN STOCK */

    const response =
    await fetch(API_URL);

    const products =
    await response.json();

    const product =
    products.find(
        p => p.name === productName
    );

    if(product){

        product.stock =
        parseInt(product.stock) +
        parseInt(quantity);

        await fetch(
            `${API_URL}/${product.id}`,
            {
                method:"PUT",

                headers:{
                    "Content-Type":
                    "application/json"
                },

                body:JSON.stringify(product)
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

        /* UPDATE STATUS */

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
            "Order cancelled successfully.";
        }

        /* SAVE UPDATED ORDER */

        await fetch(
            `${ORDER_API}/${orderId}`,
            {

                method:"PUT",

                headers:{
                    "Content-Type":
                    "application/json"
                },

                body:JSON.stringify(order)
            }
        );
        await loadProducts();

        await loadOrders();

        alert("Order Cancelled Successfully");
}
function showSection(sectionId){

    document.getElementById(
        "addSection"
    ).style.display = "none";

    document.getElementById(
        "productsSection"
    ).style.display = "none";

    document.getElementById(
        "ordersSection"
    ).style.display = "none";

    document.getElementById(
        sectionId
    ).style.display = "block";
}