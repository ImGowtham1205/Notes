// Client-side validation for Register form + hide server-side message
document.addEventListener("DOMContentLoaded", function () {

    const form = document.getElementById("registerForm");

    const usernameInput = document.getElementById("username");
    const emailInput    = document.getElementById("email");
    const passwordInput = document.getElementById("password");

    const usernameError = document.getElementById("usernameError");
    const emailError    = document.getElementById("emailError");
    const passwordError = document.getElementById("passwordError");

    function clearErrors() {
        usernameError.textContent = "";
        emailError.textContent = "";
        passwordError.textContent = "";

        usernameInput.classList.remove("input-error");
        emailInput.classList.remove("input-error");
        passwordInput.classList.remove("input-error");
    }

    form.addEventListener("submit", function (e) {
        clearErrors();
        let valid = true;

        const usernameValue = usernameInput.value.trim();
        const emailValue    = emailInput.value.trim();
        const passwordValue = passwordInput.value.trim();

        // USERNAME REQUIRED + MIN LENGTH
        if (usernameValue === "") {
            usernameError.textContent = "Username is required.";
            usernameInput.classList.add("input-error");
            valid = false;
        } else if (usernameValue.length < 3) {
            usernameError.textContent = "Username must be at least 3 characters.";
            usernameInput.classList.add("input-error");
            valid = false;
        }

        // EMAIL REQUIRED + PATTERN
        if (emailValue === "") {
            emailError.textContent = "Email is required.";
            emailInput.classList.add("input-error");
            valid = false;
        } else {
            const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailPattern.test(emailValue)) {
                emailError.textContent = "Please enter a valid email address.";
                emailInput.classList.add("input-error");
                valid = false;
            }
        }

        // PASSWORD REQUIRED + STRONG PATTERN
        if (passwordValue === "") {
            passwordError.textContent = "Password is required.";
            passwordInput.classList.add("input-error");
            valid = false;
        } else {
            // 1 uppercase, 1 lowercase, 1 digit, 1 special char, min 8 chars
            const strongPasswordPattern =
                /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

            if (!strongPasswordPattern.test(passwordValue)) {
                passwordError.textContent =
                    "Password must be 8+ chars, include uppercase, lowercase, number and special character.";
                passwordInput.classList.add("input-error");
                valid = false;
            }
        }

        if (!valid) {
            e.preventDefault(); // Stop submit if validation fails
        }
    });

    // ---- Hide server-side messages after a few seconds ----
    const serverSuccess = document.getElementById("serverSuccessMsg");
    const serverError   = document.getElementById("serverErrorMsg");

    function autoHideBanner(bannerElement) {
        if (!bannerElement) return;

        // Show for 3 seconds, then fade out
        setTimeout(function () {
            bannerElement.classList.add("fade-out");
        }, 3000);

        // Remove from DOM after fade animation ends
        setTimeout(function () {
            if (bannerElement && bannerElement.parentNode) {
                bannerElement.parentNode.removeChild(bannerElement);
            }
        }, 3500);
    }

    autoHideBanner(serverSuccess);
    autoHideBanner(serverError);
});
