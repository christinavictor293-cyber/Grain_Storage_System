document.getElementById('loginForm').addEventListener('submit', async (e) => {
  e.preventDefault();
  const username = document.getElementById('username').value.trim();
  const password = document.getElementById('password').value;
  const encoded = btoa(username + ':' + password);
  sessionStorage.setItem('authHeader', 'Basic ' + encoded);

  try {
    await apiCall('GET', '/api/warehouses');
    window.location.href = 'dashboard.html';
  } catch (err) {
    sessionStorage.clear();
    const box = document.getElementById('alertBox');
    box.textContent = 'Invalid username or password.';
    box.classList.remove('d-none');
  }
});
