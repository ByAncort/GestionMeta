
const ModalForm = document.getElementById("modal-task");

ModalForm.addEventListener('submit', (event) => {
    event.preventDefault();

    // Obtener valores de los campos del formulario
    const nameField = document.getElementById('name').value;
    const statusField = document.getElementById('status').value;
    const proyectField = document.getElementById('proyect').value;
    const responsableField = document.getElementById('user').value;
    const horasField = document.getElementById('horas').value;
    const descriptionField = document.getElementById('description').value;

    // Construir el objeto de datos
    const data = {
        name: nameField,
        projectName: proyectField,
        description: descriptionField,
        creation_date: Date.now(),
        last_update_date:Date.now(),
        status: statusField,
        responsable:responsableField,
        horas: horasField

    };

    // Enviar la solicitud POST a la API
    fetch('/api/task', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Error al guardar la tarea.');
            }
        })
        .then(data => {
            console.log('Tarea guardada:', data);
            // Cerrar el modal después de guardar
            document.getElementById("taskModal").classList.add("hidden");
            // Opcional: Limpiar el formulario
            ModalForm.reset();
        })
        .catch(error => {
            console.error('Error:', error);
        });
});

document.addEventListener("DOMContentLoaded", function () {
    // Abrir el modal
    document.getElementById("openModal").addEventListener("click", function () {
        document.getElementById("taskModal").classList.remove("hidden");
    });

    // Cerrar el modal
    document.getElementById("closeModal").addEventListener("click", function () {
        document.getElementById("taskModal").classList.add("hidden");
    });
});