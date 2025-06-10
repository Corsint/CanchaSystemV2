
    let clientId = null;
    document.addEventListener("DOMContentLoaded", async () => {
      const lista = document.getElementById("listaCanchas");
      lista.innerHTML = "<li>Cargando canchas...</li>";

      try {
        // Obtener cliente
        const clientRes = await fetch("http://localhost:8080/client/me");

        if (!clientRes.ok) {
          throw new Error(`Error obteniendo cliente: ${clientRes.status}`);
        }

        const clientData = await clientRes.json();
        clientId = clientData.id;
        console.log("Client ID:", clientId);

        // Obtener canchas
        const res = await fetch("http://localhost:8080/cancha/findallactive");
        const canchas = await res.json();
        console.log("Canchas recibidas:", canchas);

        if (canchas.length === 0) {
          lista.innerHTML = "<li>No hay canchas disponibles.</li>";
          return;
        }

        lista.innerHTML = ""; // limpiar el mensaje anterior

        canchas.forEach(c => {
          const li = document.createElement("li");

          // Generar estrellas manualmente
          let estrellasHTML = "";
          for (let i = 1; i <= 5; i++) {
            estrellasHTML += `<span class="star" data-value="${i}">☆</span>`;
          }

          li.innerHTML = `
            <strong>${c.name}</strong>
            - ${c.address}
            (${c.canchaType})<br>
            Precio: $${c.totalAmount}
            - Abre: ${c.openingHour} / Cierra: ${c.closingHour}<br>
            <button class="btn-detalles">Detalles</button>
            <button class="btn-reservar">Reservar</button>
            <button class="btn-reseñar" id="btn-reseñar-${c.id}">Reseñar</button>
            <div id="resena-form-${c.id}" style="display:none; margin-top:10px;">
                <label>Valoración (1-5):</label><br>
                <div class="stars" id="stars-${c.id}">
                    ${estrellasHTML}
                </div>
                <input type="hidden" id="rating-${c.id}" /><br>
                <label>Comentario:</label><br>
                <textarea id="comentario-${c.id}" rows="4" cols="40" placeholder="Escribe tu comentario..."></textarea><br>
                <button class="btn-confirmar-resena">Confirmar Reseña</button>
            </div>
          `;

          lista.appendChild(li);

          // Agregar eventos
          li.querySelector(".btn-detalles").addEventListener("click", () => {
            window.location.href = `details-cancha.html?canchaId=${c.id}`;
          });

          li.querySelector(".btn-reservar").addEventListener("click", () => {
            window.location.href = `make-reservation.html?canchaId=${c.id}`;
          });

          li.querySelector(".btn-reseñar").addEventListener("click", () => {
            mostrarFormularioResena(c.id);
          });

          li.querySelector(".btn-confirmar-resena").addEventListener("click", () => {
            enviarResena(c.id);
          });

          const estrellas = li.querySelectorAll(`#stars-${c.id} .star`);
          estrellas.forEach((star, index) => {
            star.addEventListener("click", () => {
              seleccionarEstrellas(c.id, index + 1);
            });
          });

          verificarSiYaReseno(c.id, clientId);
        });

      } catch (err) {
        lista.innerHTML = "<li>Error al cargar las canchas.</li>";
        console.error("Error general:", err);
      }
    });


function reservarCancha(id) {
    alert("Redirigiendo para reservar cancha con ID " + id);

}

function mostrarFormularioResena(canchaId) {
    const form = document.getElementById(`resena-form-${canchaId}`);
    form.style.display = form.style.display === "none" ? "block" : "none";
}

function seleccionarEstrellas(canchaId, valor) {
    const stars = document.querySelectorAll(`#stars-${canchaId} .star`);
    stars.forEach((star, index) => {
    star.classList.toggle("selected", index < valor);
    });

    // Guardamos el valor seleccionado como double en el input oculto
    document.getElementById(`rating-${canchaId}`).value = valor.toFixed(1);
}

async function enviarResena(canchaId) {
    const rating = parseFloat(document.getElementById(`rating-${canchaId}`).value);
    const message = document.getElementById(`comentario-${canchaId}`).value;

if (isNaN(rating) || rating < 1 || rating > 5) {
    alert("Debes seleccionar una valoración entre 1 y 5 estrellas.");
    return;
}

const review = {
    rating,
    message: message || null,
    client: { id: clientId }, // 🔧 Reemplazar por el ID real del cliente autenticado
    cancha: { id: canchaId }
};

try {
    const response = await fetch("http://localhost:8080/review/insert", {
  method: "POST",
  headers: {
    "Content-Type": "application/json"
  },
  body: JSON.stringify(review)
});

if (response.ok) {
  alert("¡Reseña enviada exitosamente! 🎉");
  document.getElementById(`resena-form-${canchaId}`).style.display = "none";
} else {
  const err = await response.json();
  console.error(err);
  alert("Error al enviar la reseña.");
}
} catch (error) {
console.error(error);
alert("Error de red al enviar la reseña.");
}
}
async function verificarSiYaReseno(canchaId, clientId) {
    try {
        const res = await fetch(`http://localhost:8080/review/clientReviewExists?canchaId=${canchaId}&clientId=${clientId}`);
        const yaReseno = await res.json();

    if (yaReseno === true) {
        const btn = document.getElementById(`btn-reseñar-${canchaId}`);
        btn.disabled = true;
        btn.textContent = "Ya reseñada";
    }
    } catch (e) {
        console.error("Error al verificar reseña previa:", e);
    }
}