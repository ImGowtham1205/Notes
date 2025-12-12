// Elements
const noteInput   = document.getElementById("noteInput");
const metaInfo    = document.getElementById("metaInfo");
const backBtn     = document.getElementById("backBtn");
const saveBtn     = document.getElementById("saveBtn");

// Format current date & time
function formatDateTime(date) {
    const options = {
        day: "numeric",
        month: "long",
        hour: "numeric",
        minute: "2-digit"
    };
    return date.toLocaleString(undefined, options);
}

// Update meta text
function updateMeta() {
    const now = new Date();
    const chars = noteInput.value.length;
    metaInfo.textContent = `${formatDateTime(now)} | ${chars} character${chars !== 1 ? "s" : ""}`;
}

// Initial meta info
updateMeta();

// Update characters on typing
noteInput.addEventListener("input", updateMeta);

// Back button — you will handle redirection in backend/controller
backBtn.addEventListener("click", () => {
    // Example: history.back();
    window.location.href = "/welcome";
});