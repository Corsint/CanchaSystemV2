document.addEventListener("DOMContentLoaded", async () => {
  try {
    // Obtener nombre del cliente
    const res = await fetch("/client/name");
    const data = await res.json();
    document.getElementById("salute").textContent = `¡Bienvenido ${data.name}!`;
  } catch (e) {
    console.error("Error al obtener nombre del cliente:", e);
  }

  try {
    // Obtener solo el ID del cliente
    const idRes = await fetch("/client/me");
    const idData = await idRes.json();

    if (idData && idData.id) {
      const clientId = idData.id;
      window.clientId = clientId;

      // Hacer una nueva petición para obtener el cliente completo con saldo
      const fullClientRes = await fetch(`/client/${clientId}`);
      const fullClientData = await fullClientRes.json();

      // Mostrar saldo actual
      const balanceElement = document.getElementById("currentBalance");
      if (fullClientData && typeof fullClientData.bankClient === "number") {
        balanceElement.textContent = `Saldo actual: $${fullClientData.bankClient.toFixed(2)}`;
      } else {
        balanceElement.textContent = "Saldo actual: No disponible";
        console.warn("El campo 'bankClient' no está presente.");
      }
    } else {
      console.error("No se pudo obtener el ID del cliente.");
    }
  } catch (e) {
    console.error("Error al obtener datos del cliente:", e);
  }

const requestBtn = document.getElementById("requestOwnerBtn");

if (requestBtn) {
  requestBtn.addEventListener("click", async () => {
    try {
      const res = await fetch("/client/me");
      const data = await res.json();

      if (!res.ok || !data.id) {
        alert("No se pudo obtener tu información para enviar la solicitud.");
        return;
      }

      const requestPayload = {
        client: { id: data.id }
      };

      const response = await fetch("/client/request", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(requestPayload)
      });

      if (response.ok) {
        alert("✅ Solicitud enviada con éxito.");
        requestBtn.disabled = true;
        requestBtn.textContent = "Solicitud enviada";
      } else {
        const error = await response.json();
        alert("❌ Error al enviar solicitud: " + (error.message || response.status));
      }

    } catch (err) {
      console.error("Error de red:", err);
      alert("Error de red al enviar la solicitud.");
    }
  });
}
});
