// Client-side required field validation + hide server message
document.addEventListener("DOMContentLoaded", function () {

    const form = document.getElementById("loginForm");
    const emailInput = document.getElementById("email");
    const passwordInput = document.getElementById("password");

    const emailError = document.getElementById("emailError");
    const passwordError = document.getElementById("passwordError");

    function clearErrors() {
        emailError.textContent = "";
        passwordError.textContent = "";

        emailInput.classList.remove("input-error");
        passwordInput.classList.remove("input-error");
    }

    form.addEventListener("submit", function (e) {
        clearErrors();
        let valid = true;

        const emailValue = emailInput.value.trim();
        const passwordValue = passwordInput.value.trim();

        // Email required
        if (emailValue === "") {
            emailError.textContent = "Email is required.";
            emailInput.classList.add("input-error");
            valid = false;
        } else {
            // Basic email pattern validation
            const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailPattern.test(emailValue)) {
                emailError.textContent = "Enter a valid email.";
                emailInput.classList.add("input-error");
                valid = false;
            }
        }

        // Password required
        if (passwordValue === "") {
            passwordError.textContent = "Password is required.";
            passwordInput.classList.add("input-error");
            valid = false;
        }

        if (!valid) {
            e.preventDefault(); // Block form submit
        }
    });

    // Hide server-side message automatically
    const serverError = document.getElementById("serverErrorMsg");
    if (serverError) {
        setTimeout(function () {
            serverError.classList.add("fade-out");
        }, 3000); // Visible for 3 seconds

        setTimeout(function () {
            if (serverError && serverError.parentNode) {
                serverError.parentNode.removeChild(serverError);
            }
        }, 3500);
    }
});
