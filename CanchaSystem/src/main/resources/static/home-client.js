document.addEventListener("DOMContentLoaded", async () => {
  try {
    const res = await fetch("/client/name");
    const data = await res.json();
    document.getElementById("salute").textContent = `¡Bienvenido ${data.name}!`;
  } catch (e) {
    console.error("Error al obtener nombre del cliente:", e);
  }

  // Acción al hacer clic en "Solicitar ser dueño"
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
