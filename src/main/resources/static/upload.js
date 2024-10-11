document.getElementById('imageUploadForm').addEventListener('submit', function (event) {
    event.preventDefault(); // Verhindert das Standardverhalten des Formulars

    var formData = new FormData(this); // Holt die Formulardaten

    fetch('/upload', {
        method: 'POST',
        body: formData
    })
        .then(response => response.json())
        .then(data => {
            // Hier kannst du die Logik für die Erfolgsmeldung und das Aktualisieren des Inhalts hinzufügen
            console.log('Success:', data);
            // Beispiel: Zeige das hochgeladene Bild an
            const img = document.createElement('img');
            img.src = '/images/' + data.fileName; // Setze den Bildpfad
            document.body.appendChild(img);
        })
        .catch((error) => {
            console.error('Error:', error);
        });
});
