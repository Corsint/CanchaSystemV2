let ownerId = null;
    document.addEventListener("DOMContentLoaded", async () => {
    const lista = document.getElementById("listaCanchas");

    try {



    const res = await fetch("http://localhost:8080/cancha/findallactive");
    const canchas = await res.json();

    if (canchas.length === 0) {
      lista.innerHTML = "<li>No hay canchas disponibles.</li>";
      return;
    }

    canchas.forEach(c => {
    const li = document.createElement("li");

    li.innerHTML = `
        <strong>${c.name}</strong>
        - ${c.address}
        (${c.canchaType})<br>
        Precio: $${c.totalAmount}
        - Abre: ${c.openingHour} / Cierra: ${c.closingHour}<br>
        <button onclick="location.href='details-cancha-owner.html?canchaId=${c.id}'">Detalles</button>
        <hr>`;
    lista.appendChild(li);
    });
    } catch (err) {
        lista.innerHTML = "<li>Error al cargar las canchas.</li>";
        console.error(err);
    }
    });

