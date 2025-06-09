document.getElementById("registerForm").addEventListener("submit", function(event) {
        event.preventDefault();

        const client = {
            name: document.getElementById("name").value,
            lastName: document.getElementById("lastname").value,
            username: document.getElementById("username").value,
            password: document.getElementById("password").value,
            mail: document.getElementById("mail").value,
            cellNumber: document.getElementById("cellNumber").value,
        };

        fetch("/client/insert", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(client)
        })
        .then(async response => {
            if (response.ok) {
                document.getElementById("message").textContent = "¡Registro exitoso! Redirigiendo...";
                setTimeout(() => {
                    window.location.href = "/login.html"; // Asegúrate de tener /login configurado
                }, 2000);
            } else {
                const data = await response.json();
                document.getElementById("message").textContent = "Error: " + (data.message || JSON.stringify(data));
            }
        })
        .catch(error => {
            console.error("Error:", error);
            document.getElementById("message").textContent = "Error de red o del servidor.";
        });
    });