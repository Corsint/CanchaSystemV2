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

  // --- El resto del código queda igual (solicitud y agregar saldo) ---
});
