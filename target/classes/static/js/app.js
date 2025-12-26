const API_URL = "http://localhost:8080/api/auth";

// ===== LOGIN =====
function login() {
    const email = document.getElementById("loginEmail").value;
    const password = document.getElementById("loginPassword").value;

    fetch(API_URL + "/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            email: email,
            password: password
        })
    })
        .then(res => {
            if (!res.ok) throw new Error("Login failed");
            return res.json();
        })
        .then(data => {
            // сохраняем JWT
            localStorage.setItem("token", data.token);
            localStorage.setItem("user", JSON.stringify(data.user));

            // переход в dashboard
            window.location.href = "dashboard.html";
        })
        .catch(err => {
            alert("Invalid email or password");
            console.error(err);
        });
}

// ===== REGISTER =====
function register() {
    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;
    const studentId = document.getElementById("studentId").value;
    const password = document.getElementById("password").value;

    fetch(API_URL + "/register", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            name: name,
            email: email,
            studentId: studentId,
            password: password
        })
    })
        .then(res => {
            if (!res.ok) throw new Error("Register failed");
            return res.json();
        })
        .then(() => {
            alert("Registration successful!");
            window.location.href = "login.html";
        })
        .catch(err => {
            alert("Registration error");
            console.error(err);
        });
}

// ===== LOGOUT =====
function logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    window.location.href = "login.html";
}
