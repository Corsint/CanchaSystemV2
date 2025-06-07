const urlParams = new URLSearchParams(window.location.search);
  const canchaId = urlParams.get('canchaId');

  const detalleDiv = document.getElementById('detalleCancha');
  const fechaInput = document.getElementById('fecha');
  const horarioSelect = document.getElementById('horario');

  // Mostrar detalles de la cancha
  async function cargarDetallesCancha() {
    try {
      const res = await fetch(`http://localhost:8080/cancha/findCanchaById/${canchaId}`);
      const cancha = await res.json();

      detalleDiv.innerHTML = `
        <strong>Nombre:</strong> ${cancha.name}<br>
        <strong>Dirección:</strong> ${cancha.address}<br>
        <strong>Tipo:</strong> ${cancha.canchaType}<br>
        <strong>Precio:</strong> $${cancha.totalAmount}<br>
        <strong>Abre:</strong> ${cancha.openingHour} - <strong>Cierra:</strong> ${cancha.closingHour}<br>
        <strong>Techo:</strong> ${cancha.hasRoof ? "Sí" : "No"} |
        <strong>Ducha:</strong> ${cancha.canShower ? "Sí" : "No"}<br><br>
      `;
    } catch (err) {
      detalleDiv.innerHTML = "<p>Error al cargar la cancha.</p>";
      console.error(err);
    }
  }

  cargarDetallesCancha();

  // Cargar horarios disponibles al elegir fecha
  fechaInput.addEventListener('change', async () => {
    const fecha = fechaInput.value;
    if (!fecha) return;

    horarioSelect.innerHTML = "<option>Cargando horarios...</option>";

    try {
      const res = await fetch(`http://localhost:8080/reservation/getAvailableHours/${canchaId}/${fecha}`);
      if (res.status === 204) {
        horarioSelect.innerHTML = "<option>No hay horarios disponibles</option>";
        return;
      }
      const horas = await res.json();
      horarioSelect.innerHTML = "";
      horas.forEach(hora => {
        const option = document.createElement("option");
        option.value = hora;
        option.textContent = hora;
        horarioSelect.appendChild(option);
      });
    } catch (err) {
      horarioSelect.innerHTML = "<option>Error al cargar horarios</option>";
      console.error(err);
    }
  });

  // Enviar reserva
  document.getElementById('formReserva').addEventListener('submit', async (e) => {
    e.preventDefault();
    const fecha = fechaInput.value;
    const hora = horarioSelect.value;
    const fechaHora = `${fecha}T${hora}`; // formato ISO

    const body = {
      cancha: { id: canchaId },
      matchDate: fechaHora
    };

    try {
      const res = await fetch("http://localhost:8080/reservation/insert", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(body)
      });

      if (res.ok) {
        alert("¡Reserva confirmada!");
        location.href = "home-client.html"; // o redireccioná donde quieras
      } else {
        alert("Error al reservar");
      }
    } catch (err) {
      console.error("Error:", err);
      alert("Error inesperado");
    }
  });