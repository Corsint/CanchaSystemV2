document.addEventListener("DOMContentLoaded", async () => {
      try {
        const res = await fetch("http://localhost:8080/client/name");
        const data = await res.json();
        document.getElementById("salute").textContent = `¡Bienvenido ${data.name}!`;
      } catch (e) {
        console.error("Error al obtener nombre del cliente:", e);
      }
    });