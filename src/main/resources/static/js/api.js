async function apiCall(method, url, body) {
  const auth = sessionStorage.getItem('authHeader');
  const options = {
    method,
    headers: { 'Content-Type': 'application/json', 'Authorization': auth }
  };
  if (body) options.body = JSON.stringify(body);
  const response = await fetch(url, options);
  if (response.status === 401) { window.location.href = 'login.html'; return; }
  if (response.status === 403) { throw Object.assign(new Error('Forbidden'), { status: 403 }); }
  if (!response.ok) { const err = await response.json(); throw new Error(err.error || 'Request failed'); }
  return response.status !== 204 ? response.json() : null;
}

function getUsername() {
  const auth = sessionStorage.getItem('authHeader');
  if (!auth) return null;
  try { return atob(auth.replace('Basic ', '')).split(':')[0]; } catch { return null; }
}

function requireAuth() {
  if (!sessionStorage.getItem('authHeader')) window.location.href = 'login.html';
}

function logout() {
  sessionStorage.clear();
  window.location.href = 'login.html';
}
