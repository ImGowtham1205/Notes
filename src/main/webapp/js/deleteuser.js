/* deleteuser.js
   Full script for deleteuser.jsp
   - defensive guard to remove stray server messages
   - hamburger / side-menu behavior
   - delete-user password reveal + validation
   - auto-show + auto-hide server-side message when present
   - lightweight accessibility + focus handling
*/

(function () {
  // ---------- CONTEXT PATH ----------
  const contextPath = document.body.getAttribute("data-context-path") || "";

  // ---------- DEFENSIVE GUARD ----------
  // Remove any #serverErrorMsg that's not inside #password-area (prevents stray markup from showing)
  (function removeStrayServerMsg() {
    if (document.readyState === 'loading') {
      document.addEventListener('DOMContentLoaded', () => {
        const serverMsg = document.getElementById('serverErrorMsg');
        const passwordArea = document.getElementById('password-area');
        if (serverMsg && (!passwordArea || !passwordArea.contains(serverMsg))) {
          serverMsg.remove();
        }
      });
    } else {
      const serverMsg = document.getElementById('serverErrorMsg');
      const passwordArea = document.getElementById('password-area');
      if (serverMsg && (!passwordArea || !passwordArea.contains(serverMsg))) {
        serverMsg.remove();
      }
    }
  })();

  // ---------- ELEMENT REFERENCES ----------
  const menuToggle = document.getElementById("menuToggle");
  const sideMenu   = document.getElementById("sideMenu");
  const backdrop   = document.getElementById("backdrop");
  const changePasswordBtn = document.getElementById("changePasswordBtn");
  const logoutBtn = document.getElementById("logoutBtn");
  const homeBtn = document.getElementById("homeBtn");

  const confirmBtn = document.getElementById('confirm-btn');
  const passwordArea = document.getElementById('password-area');
  const deleteForm = document.getElementById('delete-form');
  const cancelBtn = document.getElementById('cancel-btn');
  const passwordInput = document.getElementById('password');

  // ---------- SAFE SELECTORS / UTIL ----------
  function qs(id) { return document.getElementById(id); }
  const focusableSelector = 'a[href], button:not([disabled]), input:not([disabled]), [tabindex]:not([tabindex="-1"])';

  // ---------- MENU: open/close ----------
  function openSideMenu() {
    if (!sideMenu || !backdrop) return;
    sideMenu.classList.add("open");
    backdrop.classList.add("show");
    sideMenu.setAttribute("aria-hidden", "false");
    sideMenu.setAttribute("tabindex", "-1");
    const first = sideMenu.querySelector(focusableSelector);
    if (first) first.focus();
    // Reduce verbosity to screen readers for main content
    document.querySelectorAll('main, .page, header').forEach(el => el.setAttribute('aria-hidden', 'true'));
  }

  function closeSideMenu() {
    if (!sideMenu || !backdrop) return;
    sideMenu.classList.remove("open");
    backdrop.classList.remove("show");
    sideMenu.setAttribute("aria-hidden", "true");
    document.querySelectorAll('main, .page, header').forEach(el => el.removeAttribute('aria-hidden'));
    if (menuToggle) menuToggle.focus();
  }

  // ---------- MENU EVENT HANDLERS ----------
  menuToggle?.addEventListener("click", () => {
    if (sideMenu && sideMenu.classList.contains("open")) closeSideMenu();
    else openSideMenu();
  });

  backdrop?.addEventListener("click", () => closeSideMenu());

  sideMenu?.addEventListener("click", (ev) => {
    const target = ev.target;
    if (!target) return;
    // if a menu button or menu-item clicked, close menu shortly after to allow navigation action
    if (target.matches("button") || target.matches(".menu-item") || target.closest("button")) {
      setTimeout(closeSideMenu, 140);
    }
  });

  changePasswordBtn?.addEventListener("click", () => {
    window.location.href = contextPath + "/showchangepassword";
  });

  homeBtn?.addEventListener("click", () => {
    window.location.href = contextPath + "/welcome";
  });

  logoutBtn?.addEventListener("click", () => {
    window.location.href = contextPath + "/logout";
  });

  // ---------- KEY HANDLING: Escape / Tab trap ----------
  document.addEventListener("keydown", (e) => {
    if (e.key === "Escape") {
      if (sideMenu && sideMenu.classList.contains("open")) closeSideMenu();
      if (passwordArea && passwordArea.classList.contains("open")) closePasswordArea();
    }

    // Tab trapping inside sideMenu
    if (e.key === "Tab" && sideMenu && sideMenu.classList.contains("open")) {
      const focusables = Array.from(sideMenu.querySelectorAll(focusableSelector)).filter(Boolean);
      if (focusables.length === 0) return;
      const first = focusables[0];
      const last = focusables[focusables.length - 1];
      if (e.shiftKey && document.activeElement === first) {
        e.preventDefault();
        last.focus();
      } else if (!e.shiftKey && document.activeElement === last) {
        e.preventDefault();
        first.focus();
      }
    }
  });

  // Lightweight focus-in guard to prevent focus leak
  document.addEventListener('focusin', function (ev) {
    if (!sideMenu) return;
    if (sideMenu.classList.contains('open') && !sideMenu.contains(ev.target)) {
      const first = sideMenu.querySelector(focusableSelector);
      if (first) first.focus();
    }
  });

  // ---------- PASSWORD AREA (delete logic) ----------
  function openPasswordArea() {
    if (!passwordArea || !confirmBtn) return;
    passwordArea.classList.add('open');
    passwordArea.setAttribute('aria-hidden', 'false');
    confirmBtn.setAttribute('aria-expanded', 'true');
    setTimeout(()=> { if (passwordInput) passwordInput.focus(); }, 220);
  }

  function closePasswordArea() {
    if (!passwordArea || !confirmBtn) return;
    passwordArea.classList.remove('open');
    passwordArea.setAttribute('aria-hidden', 'true');
    confirmBtn.setAttribute('aria-expanded', 'false');
    if (confirmBtn) confirmBtn.focus();
  }

  confirmBtn?.addEventListener('click', function () {
    openPasswordArea();
  });

  cancelBtn?.addEventListener('click', function () {
    closePasswordArea();
  });

  // ---------- FORM VALIDATION + UX ----------
  if (deleteForm) {
    deleteForm.addEventListener('submit', function (e) {
      const pw = (passwordInput && passwordInput.value) ? passwordInput.value.trim() : '';
      const note = document.getElementById('pw-note');

      if (!pw || pw.length < 8) {
        e.preventDefault();
        if (passwordInput) {
          passwordInput.setAttribute('aria-invalid', 'true');
          passwordInput.focus();
        }
        if (note) note.textContent = 'Please enter your password (at least 8 characters).';
        if (!document.querySelector('.field-error')) {
          const err = document.createElement('div');
          err.className = 'field-error';
          err.textContent = 'Password must be at least 8 characters.';
          note?.parentNode?.appendChild(err);
        }
        return false;
      }

      // disable submit to avoid double submits
      const submitBtn = document.getElementById('submit-btn');
      if (submitBtn) submitBtn.disabled = true;
      if (cancelBtn) cancelBtn.disabled = true;
      // allow form to submit naturally
    });
  }

  passwordInput?.addEventListener('input', function () {
    const note = document.getElementById('pw-note');
    if (passwordInput.value.trim().length >= 8) {
      passwordInput.removeAttribute('aria-invalid');
      if (note) note.textContent = 'We require your password to confirm this action.';
      const inlineErr = document.querySelector('.field-error');
      if (inlineErr) inlineErr.remove();
    }
  });

  // ---------- SERVER-SIDE MESSAGE AUTO SHOW + AUTO HIDE ----------
  function handleServerMessage() {
    const serverMsg = document.getElementById('serverErrorMsg');
    if (!serverMsg) return;

    // make sure the message is inside the password area; if not, remove it (defensive)
    if (passwordArea && !passwordArea.contains(serverMsg)) {
      serverMsg.remove();
      return;
    }

    // Ensure password area is visible so message is seen
    if (passwordArea && !passwordArea.classList.contains('open')) openPasswordArea();

    // Add ARIA attributes
    serverMsg.setAttribute('role', 'alert');
    serverMsg.setAttribute('aria-live', 'polite');

    // Allow user to dismiss early by clicking the message
    serverMsg.style.cursor = 'pointer';
    serverMsg.addEventListener('click', function () {
      serverMsg.classList.add('hide');
      setTimeout(() => { try { serverMsg.remove(); } catch (err) {} }, 420);
    }, { once: true });

    // Auto hide after a visible duration
    const visibleDuration = 3500; // ms
    const hideTransition = 420; // should match CSS

    setTimeout(() => {
      serverMsg.classList.add('hide');
      setTimeout(() => { try { serverMsg.remove(); } catch (err) {} }, hideTransition);
    }, visibleDuration);
  }

  if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', handleServerMessage);
  } else {
    handleServerMessage();
  }

  // ---------- SAFE INIT ----------
  function safeInit() {
    const serverMsg = document.getElementById('serverErrorMsg');
    if (serverMsg && passwordArea && !passwordArea.classList.contains('open')) {
      openPasswordArea();
    }
  }

  if (document.readyState !== 'loading') safeInit();
  else document.addEventListener('DOMContentLoaded', safeInit);

  // ---------- END ----------
})();
