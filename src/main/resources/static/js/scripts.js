// Funktion zum Laden der Optionen basierend auf der ausgewählten Vorlage
function loadTemplateOptions() {
    const templateId = document.getElementById('templateSelect').value;

    if (!templateId) {
        // Wenn keine Vorlage ausgewählt ist, leere die Dropdown-Optionen
        updateOptionsDropdown({});
        return;
    }

    // Fetch-Request, um die Optionen für die ausgewählte Vorlage zu laden
    fetch(`/templates/${templateId}/options`)
        .then(response => response.json())
        .then(data => {
            // Aktualisiere die Dropdown-Optionen mit den erhaltenen Daten
            updateOptionsDropdown(data);
        })
        .catch(error => {
            console.error('Fehler beim Laden der Optionen:', error);
        });
}

// Funktion zur Aktualisierung des Dropdowns für die Skripttypen
function updateOptionsDropdown(data) {
    const saleTypeDropdown = document.getElementById('saleType');
    saleTypeDropdown.innerHTML = ''; // Leere das Dropdown

    // Überprüfe, ob es Optionen gibt, und füge sie hinzu
    if (data.sale) {
        saleTypeDropdown.innerHTML += '<option value="sale">Sale</option>';
    }
    if (data.lead) {
        saleTypeDropdown.innerHTML += '<option value="lead">Lead</option>';
    }
    if (data.install) {
        saleTypeDropdown.innerHTML += '<option value="install">Install</option>';
    }

    // Wenn keine Optionen vorhanden sind, zeige eine entsprechende Nachricht an
    if (!saleTypeDropdown.innerHTML) {
        saleTypeDropdown.innerHTML = '<option value="">Keine Optionen verfügbar</option>';
    }
}

// Neu laden der Dropdowns, wenn die Seite zurückgeholt wird
window.addEventListener('pageshow', (event) => {
    if (event.persisted || performance.getEntriesByType('navigation')[0].type === 'back_forward') {
        window.location.reload();
    }
});

function copyToClipboard(id) {
    var copyText = document.getElementById(id);
    copyText.select(); // Wählt den Text im Textfeld aus
    document.execCommand("copy"); // Kopiert den ausgewählten Text in die Zwischenablage
}

