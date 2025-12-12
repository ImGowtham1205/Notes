document.addEventListener("DOMContentLoaded", function () {
  const form = document.getElementById("registerForm");
  const emailInput = document.getElementById("email");
  const emailError = document.getElementById("emailError");

  // ---- Helper to clear errors ----
  function clearEmailError() {
    if (!emailError || !emailInput) return;
    emailError.textContent = "";
    emailInput.classList.remove("input-error");
  }

  // ---- Email validation on submit ----
  if (form && emailInput && emailError) {
    form.addEventListener("submit", function (e) {
      clearEmailError();

      const emailVal = emailInput.value.trim();
      let isValid = true;

      // Required check
      if (emailVal === "") {
        emailError.textContent = "Email is required";
        emailInput.classList.add("input-error");
        isValid = false;
      } else {
        // Basic email pattern check
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (!emailRegex.test(emailVal)) {
          emailError.textContent = "Please enter a valid email address";
          emailInput.classList.add("input-error");
          isValid = false;
        }
      }

      if (!isValid) {
        e.preventDefault(); // Stop form submit on validation error
      }
    });

    // Clear error while typing
    emailInput.addEventListener("input", function () {
      clearEmailError();
    });
  }

  // ---- Fade out server-side messages after a few seconds ----
  const successMsg = document.getElementById("serverSuccessMsg");
  const errorMsg = document.getElementById("servererrorMsg");

  [successMsg, errorMsg].forEach((msgEl) => {
    if (!msgEl) return;

    // Show for 3 seconds, then fade out (CSS already has .fade-out)
    setTimeout(() => {
      msgEl.classList.add("fade-out");

      // After fade animation (300ms), remove from layout
      setTimeout(() => {
        msgEl.style.display = "none";
      }, 300);
    }, 3000); // visible duration = 3s
  });
});
