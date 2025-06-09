let ownerId;
    let roleId;

    async function cargarDatosDueño() {
        try {
            const res = await fetch("http://localhost:8080/owner/me");
            ownerId = await res.json();


            const ownerres = await fetch(`http://localhost:8080/owner/${ownerId}`);
            const owner = await ownerres.json();
            roleId = owner.role.id;


            document.getElementById("name").value = owner.name;
            document.getElementById("lastName").value = owner.lastName;
            document.getElementById("username").value = owner.username;
            document.getElementById("mail").value = owner.mail;
            document.getElementById("cellNumber").value = owner.cellNumber || '';
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
        const ownerActualizado = {
            id: ownerId,
            name: document.getElementById("name").value,
            lastName: document.getElementById("lastName").value,
            username: document.getElementById("username").value,
            mail: document.getElementById("mail").value,
            cellNumber: document.getElementById("cellNumber").value
        };

        if (!validarEmail(ownerActualizado.mail)) {
            alert("El email no tiene un formato válido.");
            return;
        }

        try {
            const res = await fetch("http://localhost:8080/owner/update", {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(ownerActualizado)
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

    window.addEventListener("DOMContentLoaded", cargarDatosDueño);