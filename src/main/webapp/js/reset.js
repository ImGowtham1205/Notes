// Show / Hide password
function togglePass(id, icon) {
    let field = document.getElementById(id);
    if (!field) return;

    if (field.type === "password") {
        field.type = "text";
        icon.textContent = "🙈";
    } else {
        field.type = "password";
        icon.textContent = "👁️";
    }
}

// Helper to show validation error
function showError(message) {
    const errorEl = document.getElementById("error");
    if (!errorEl) return;
    errorEl.textContent = message;
}

// Password pattern:
// Min 8 characters, at least 1 uppercase, 1 lowercase, 1 number, 1 special character
const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^\w\s]).{8,}$/;

// Attach validation + menu behaviour after DOM is ready
document.addEventListener("DOMContentLoaded", function () {

    /* ========= FORM VALIDATION ========= */
    const form = document.getElementById("resetForm");
    const currentPassField = document.getElementById("currentPass");
    const newPassField = document.getElementById("newPass");
    const confirmPassField = document.getElementById("confirmPass");

    if (form && currentPassField && newPassField && confirmPassField) {
        form.addEventListener("submit", function (e) {
            const currentPass = currentPassField.value.trim();
            const newPass = newPassField.value.trim();
            const confirmPass = confirmPassField.value.trim();

            // Clear previous error
            showError("");

            // Clear previous input error styles
            currentPassField.classList.remove("input-error");
            newPassField.classList.remove("input-error");
            confirmPassField.classList.remove("input-error");

            // Required fields check
            if (!currentPass) {
                showError("Please enter your current password.");
                currentPassField.classList.add("input-error");
                currentPassField.focus();
                e.preventDefault();
                return;
            }

            if (!newPass) {
                showError("Please enter a new password.");
                newPassField.classList.add("input-error");
                newPassField.focus();
                e.preventDefault();
                return;
            }

            if (!confirmPass) {
                showError("Please confirm your password.");
                confirmPassField.classList.add("input-error");
                confirmPassField.focus();
                e.preventDefault();
                return;
            }

            // Password strength check
            if (!passwordPattern.test(newPass)) {
                showError(
                    "Password must contain minimum 8 characters with 1 Uppercase, 1 Lowercase, 1 Number and 1 Special Character."
                );
                newPassField.classList.add("input-error");
                newPassField.focus();
                e.preventDefault();
                return;
            }

            // Confirm password match
            if (newPass !== confirmPass) {
                showError("Passwords do not match!");
                confirmPassField.classList.add("input-error");
                confirmPassField.focus();
                e.preventDefault();
                return;
            }
        });
    }

    // Handle server-side message auto-hide (both error and success)
    const errorMsgEl = document.getElementById("serverErrorMsg");
    const successMsgEl = document.getElementById("serverSuccessMsg");

    function autoHideBanner(el) {
        if (!el) return;
        const text = el.textContent.trim();
        if (text.length === 0) return;

        // After 4 seconds, fade it out
        setTimeout(function () {
            el.classList.add("fade-out");

            // After the transition ends, hide it completely
            setTimeout(function () {
                el.style.display = "none";
            }, 300); // matches CSS transition duration
        }, 4000);
    }

    autoHideBanner(errorMsgEl);
    autoHideBanner(successMsgEl);

    /* ========= HAMBURGER MENU ========= */
    const menuToggle = document.getElementById("menuToggle");
    const sideMenu = document.getElementById("sideMenu");
    const backdrop = document.getElementById("backdrop");

    function openMenu() {
        if (!sideMenu || !backdrop) return;
        sideMenu.classList.add("open");
        backdrop.classList.add("show");
        document.body.classList.add("menu-open");
    }

    function closeMenu() {
        if (!sideMenu || !backdrop) return;
        sideMenu.classList.remove("open");
        backdrop.classList.remove("show");
        document.body.classList.remove("menu-open");
    }

    if (menuToggle) {
        menuToggle.addEventListener("click", function () {
            if (sideMenu.classList.contains("open")) {
                closeMenu();
            } else {
                openMenu();
            }
        });
    }

    if (backdrop) {
        backdrop.addEventListener("click", closeMenu);
    }

    // Close with ESC key
    document.addEventListener("keydown", function (e) {
        if (e.key === "Escape") {
            closeMenu();
        }
    });
});
