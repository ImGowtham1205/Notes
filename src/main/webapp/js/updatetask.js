const backBtn  = document.getElementById("backBtn");
const saveTick = document.getElementById("saveTick");
const editForm = document.getElementById("editForm");

// Back navigation
if (backBtn) {
    backBtn.addEventListener("click", () => {
        if (history.length > 1) {
            history.back();
        } else {
            // fallback: go to welcome page
            const ctx = document.body.getAttribute("data-context-path") || "";
            window.location.href = ctx + "/welcome";
        }
    });
}

// Save/update on tick
if (saveTick && editForm) {
    saveTick.addEventListener("click", () => {
        editForm.submit();
    });
}
