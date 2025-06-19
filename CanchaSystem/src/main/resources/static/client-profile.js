let clientId;
    let roleId;

    async function cargarDatosCliente() {
        try {
            const res = await fetch("http://localhost:8080/client/me");
            const me = await res.json();
            clientId = me.id;


            const clienteres = await fetch(`http://localhost:8080/client/${clientId}`);
            const cliente = await clienteres.json();
            roleId = cliente.role.id;


            document.getElementById("name").value = cliente.name;
            document.getElementById("lastName").value = cliente.lastName;
            document.getElementById("username").value = cliente.username;
            document.getElementById("mail").value = cliente.mail;
            document.getElementById("cellNumber").value = cliente.cellNumber || '';
        } catch (error) {
            alert("Error al cargar los datos del cliente");
            console.error(error);
        }
    }

    function habilitarEdicion() {
        const campos = ["name", "lastName", "username", "mail", "cellNumber"];
        campos.forEach(id => {
            const input = document.getElementById(id);
            input.readOnly = false;
            input.classList.add("editable");
        });
        document.querySelector("button[onclick='guardarCambios()']").classList.remove("hidden");
    }

    async function guardarCambios() {
        const clienteActualizado = {
            id: clientId,
            name: document.getElementById("name").value,
            lastName: document.getElementById("lastName").value,
            username: document.getElementById("username").value,
            mail: document.getElementById("mail").value,
            cellNumber: document.getElementById("cellNumber").value
        };

        if (!validarEmail(clienteActualizado.mail)) {
            alert("El email no tiene un formato válido.");
            return;
        }

        try {
            const res = await fetch("http://localhost:8080/client/update", {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(clienteActualizado)
            });

            if (res.ok) {
                alert("Datos actualizados correctamente 🎉");
                 window.location.href = "/login.html";
            } else {
                alert("Error al guardar los cambios");
            }
        } catch (e) {
            console.error("Error en la actualización:", e);
            alert("No se pudo guardar");
        }
    }

    function validarEmail(email) {
        const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return regex.test(email);
    }

    window.addEventListener("DOMContentLoaded", cargarDatosCliente);