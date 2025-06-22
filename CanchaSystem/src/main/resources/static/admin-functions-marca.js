document.addEventListener("DOMContentLoaded", () => {
  cargarMarcas();

  document.getElementById("filtroNombre").addEventListener("input", cargarMarcas);
  document.getElementById("filtroEstado").addEventListener("change", cargarMarcas);
});

async function cargarMarcas() {
  const lista = document.getElementById("listaMarcas");
  lista.innerHTML = "<li>Cargando...</li>";

  try {
    const res = await fetch("http://localhost:8080/canchaBrand/findall");
    const marcas = await res.json();

    const nombre = document.getElementById("filtroNombre").value.toLowerCase();
    const estado = document.getElementById("filtroEstado").value;

    const filtradas = marcas.filter(m =>
      (!nombre || m.brandName.toLowerCase().includes(nombre)) &&
      (estado === "" ||
        (estado === "activa" && m.active) ||
        (estado === "inactiva" && !m.active))
    );

    if (filtradas.length === 0) {
      lista.innerHTML = "<li>No se encontraron marcas.</li>";
      return;
    }

    lista.innerHTML = "";
    filtradas.forEach(m => {
      const li = document.createElement("li");
      li.innerHTML = `
        <input type="text" value="${m.brandName}" id="brandName-${m.id}">
        <label><input type="checkbox" id="active-${m.id}" ${m.active ? "checked" : ""}> Activa</label>
        <br>
        <button onclick="guardarMarca(${m.id})">Guardar</button>
        <button onclick="eliminarMarca(${m.id})">Eliminar</button>
        <hr>
      `;
      lista.appendChild(li);
    });
  } catch (err) {
    console.error(err);
    lista.innerHTML = "<li>Error al cargar marcas.</li>";
  }
}

async function guardarMarca(id) {
  const marcaEditada = {
    id,
    brandName: document.getElementById(`brandName-${id}`).value,
    active: document.getElementById(`active-${id}`).checked
  };

  try {
    const res = await fetch("http://localhost:8080/canchaBrand/update", {
      method: "PUT",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(marcaEditada)
    });

    if (res.ok) {
      alert("Marca actualizada correctamente");
      cargarMarcas();
    } else {
      const errorText = await res.text();
      alert("Error al actualizar la marca:\n" + errorText);
    }
  } catch (e) {
    console.error(e);
    alert("Error de red");
  }
}

async function eliminarMarca(id) {
  if (!confirm("¿Seguro que querés eliminar esta marca?")) return;

  try {
    const res = await fetch(`http://localhost:8080/canchaBrand/deleteCanchaBrand/${id}`, {
      method: "DELETE"
    });

    const data = await res.json();

    if (res.ok) {
      alert(data.message); // ← muestra "Marca eliminada"
      cargarMarcas();
    } else {
      alert("Error al eliminar la marca: " + data.message);
    }
  } catch (e) {
    console.error(e);
    alert("Error de red");
  }

}
