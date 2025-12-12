// ---------- welcome.js (final) ----------

// ---------- CONTEXT PATH ----------
const contextPath = document.body.getAttribute("data-context-path") || "";

// ---------- ELEMENTS ----------
const menuToggle = document.getElementById("menuToggle");
const sideMenu   = document.getElementById("sideMenu");
const backdrop   = document.getElementById("backdrop");
const changePasswordBtn = document.getElementById("changePasswordBtn");
const logoutBtn = document.getElementById("logoutBtn");

const mainBar      = document.getElementById("mainBar");
const selectBar    = document.getElementById("selectBar");
const bottomDelete = document.getElementById("bottomDelete");
const selectedText = document.getElementById("selectedCount");
const cancelSelect = document.getElementById("cancelSelect");
const deleteBtn    = document.getElementById("deleteBtn");
const noteCards    = document.querySelectorAll(".note-card");
const deleteForm   = document.getElementById("deleteForm");
const addNoteBtn   = document.getElementById("addNoteBtn");

// ---------- HAMBURGER MENU (toggle classes used by your CSS) ----------
menuToggle?.addEventListener("click", () => {
    sideMenu.classList.add("open");
    backdrop.classList.add("show");
});

backdrop?.addEventListener("click", () => {
    sideMenu.classList.remove("open");
    backdrop.classList.remove("show");
});

// close menu when a menu item is clicked (nice UX)
sideMenu?.addEventListener("click", (ev) => {
    if (ev.target && ev.target.matches("button, .menu-item")) {
        sideMenu.classList.remove("open");
        backdrop.classList.remove("show");
    }
});

// ---------- SIDE MENU NAV ----------
changePasswordBtn?.addEventListener("click", () => {
    window.location.href = contextPath + "/showchangepassword";
});

deleteAccountBtn?.addEventListener("click", () => {
    window.location.href = contextPath + "/deleteuser";
});

logoutBtn?.addEventListener("click", () => {
    window.location.href = contextPath + "/logout";
});

// ---------- SELECTION MODE ----------
let selectionMode = false;
let selectedNotes = new Set();

function enterSelectionMode(card) {
    if (!selectionMode) {
        selectionMode = true;
        if (mainBar) mainBar.style.display = "none";
        if (selectBar) selectBar.style.display = "flex";
        if (bottomDelete) bottomDelete.style.display = "block";
    }
    toggleCard(card);
}

function toggleCard(card) {
    const id = String(card.dataset.id);

    if (selectedNotes.has(id)) {
        selectedNotes.delete(id);
        card.classList.remove("selected");
    } else {
        selectedNotes.add(id);
        card.classList.add("selected");
    }

    updateSelectedText();

    if (selectedNotes.size === 0) {
        exitSelectionMode();
    }
}

function updateSelectedText() {
    const count = selectedNotes.size;
    if (selectedText) {
        selectedText.textContent =
            count + (count === 1 ? " item selected" : " items selected");
    }
}

function exitSelectionMode() {
    selectionMode = false;
    selectedNotes.clear();
    if (mainBar) mainBar.style.display = "flex";
    if (selectBar) selectBar.style.display = "none";
    if (bottomDelete) bottomDelete.style.display = "none";

    document.querySelectorAll(".note-card.selected").forEach(c => c.classList.remove("selected"));
}

// ---------- ATTACH EVENTS TO NOTE CARDS ----------
noteCards.forEach(card => {
    // ignore placeholder/empty card
    if (!card.dataset.id) return;

    let timer;
    let longPressed = false;

    // touch events for mobile
    card.addEventListener("touchstart", (e) => {
        longPressed = false;
        timer = setTimeout(() => {
            longPressed = true;
            enterSelectionMode(card);
        }, 400);
    });

    card.addEventListener("touchend", () => clearTimeout(timer));
    card.addEventListener("touchmove", () => clearTimeout(timer));

    // mouse long-press for desktop
    card.addEventListener("mousedown", () => {
        longPressed = false;
        timer = setTimeout(() => {
            longPressed = true;
            enterSelectionMode(card);
        }, 400);
    });

    card.addEventListener("mouseup", () => clearTimeout(timer));
    card.addEventListener("mouseleave", () => clearTimeout(timer));

    // click behavior (selection toggle or open edit)
    card.addEventListener("click", (ev) => {
        const id = card.dataset.id;
        // If we are in selection mode, toggle selection on click
        if (selectionMode && !longPressed) {
            toggleCard(card);
            return;
        }

        // If not in selection mode and not a long press, open edit page
        if (!selectionMode && !longPressed) {
            window.location.href = contextPath + "/edittask/" + id;
        }
    });
});

// cancel selection button
cancelSelect?.addEventListener("click", exitSelectionMode);

// ---------- DELETE: only "No tasks selected to delete." alert. No confirmation ----------
if (deleteBtn && deleteForm) {
    deleteBtn.addEventListener("click", (e) => {
        e.preventDefault();

        if (selectedNotes.size === 0) {
            alert("No tasks selected to delete.");
            return;
        }

        // clear any previous inputs
        Array.from(deleteForm.querySelectorAll('input[name="taskids"]')).forEach(i => i.remove());

        // append hidden inputs for each selected id
        selectedNotes.forEach(id => {
            const input = document.createElement("input");
            input.type = "hidden";
            input.name = "taskids";
            input.value = id;
            deleteForm.appendChild(input);
        });

        // submit the form (POST)
        deleteForm.submit();
    });
}

// ---------- ADD BUTTON ----------
addNoteBtn?.addEventListener("click", () => {
    window.location.href = contextPath + "/addtask";
});

// ---------- Optional: close menu with ESC key ----------
document.addEventListener("keydown", (e) => {
    if (e.key === "Escape") {
        sideMenu?.classList.remove("open");
        backdrop?.classList.remove("show");
    }
});
