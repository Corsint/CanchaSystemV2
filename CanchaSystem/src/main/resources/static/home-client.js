document.addEventListener("DOMContentLoaded", async () => {
  try {
    const nameResponse = await fetch("http://localhost:8080/client/name", { credentials: "include" });
    const nameData = await nameResponse.json();
    document.getElementById("salute").textContent = `¡Bienvenido ${nameData.name || "Cliente"}!`;

    const idResponse = await fetch("http://localhost:8080/client/me", { credentials: "include" });
    const idData = await idResponse.json();
    if (!idData.id) throw new Error("No se pudo obtener el ID del cliente.");
    window.clientId = idData.id;

    const balanceResponse = await fetch(`http://localhost:8080/client/${window.clientId}`);
    const balanceData = await balanceResponse.json();
    const balanceElement = document.getElementById("currentBalance");
    if (typeof balanceData.bankClient === "number") {
      balanceElement.textContent = `Saldo actual: $${balanceData.bankClient.toFixed(2)}`;
    } else {
      balanceElement.textContent = "Saldo actual: No disponible";
      console.warn("El campo 'bankClient' no está presente en la respuesta.");
    }
  } catch (error) {
    console.error("Error al cargar datos iniciales:", error);
  }

  const requestBtn = document.getElementById("requestOwnerBtn");
  if (requestBtn) {
    requestBtn.addEventListener("click", async () => {
      try {
        const requestPayload = { client: { id: window.clientId } };
        const requestResponse = await fetch("http://localhost:8080/client/request", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(requestPayload),
        });

        if (!requestResponse.ok) {
          const error = await requestResponse.json();
          console.error("Error en la solicitud:", error);
          alert(`❌ Error al enviar solicitud: ${error.message || "Error desconocido."}`);
          return;
        }

        alert("✅ Solicitud enviada con éxito.");
        requestBtn.disabled = true;
        requestBtn.textContent = "Solicitud enviada";
      } catch (error) {
        console.error("Error de red al enviar la solicitud:", error);
        alert("❌ Error de red al enviar la solicitud. Inténtalo de nuevo.");
      }
    });
  }

  const addMoneyForm = document.getElementById("addMoneyForm");
  addMoneyForm.addEventListener("submit", async (e) => {
    e.preventDefault();
    const amount = document.getElementById("amount").value;

    try {
      const response = await fetch(
        `http://localhost:8080/client/addMoneyToClient/${window.clientId}/${amount}`,
        {
          method: "PUT",
          credentials: "include",
        }
      );

      const message = await response.text();
      const responseMessage = document.getElementById("responseMessage");
      responseMessage.textContent = message;
      responseMessage.style.color = "green";
    } catch (error) {
      console.error("Error al agregar saldo:", error);
      const responseMessage = document.getElementById("responseMessage");
      responseMessage.textContent = "Error al agregar saldo.";
      responseMessage.style.color = "red";
    }
  });
});
