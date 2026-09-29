requireAuth();
document.getElementById('navUser').textContent = getUsername();

async function loadContacts() {
  const contacts = await apiCall('GET', '/api/contacts');
  const tbody = document.getElementById('contactTable');
  tbody.innerHTML = '';
  contacts.forEach(c => {
    const badge = c.contactType === 'VENDOR'
      ? '<span class="badge bg-secondary">VENDOR</span>'
      : '<span class="badge bg-info text-dark">CUSTOMER</span>';
    tbody.innerHTML += `<tr>
      <td>${c.name}</td>
      <td>${badge}</td>
      <td>${c.phone || ''}</td>
      <td>${c.address || ''}</td>
    </tr>`;
  });
}

document.getElementById('saveContact').addEventListener('click', async () => {
  const alertEl = document.getElementById('modalAlert');
  alertEl.classList.add('d-none');
  const body = {
    name: document.getElementById('cName').value.trim(),
    contactType: document.getElementById('cType').value,
    phone: document.getElementById('cPhone').value.trim(),
    address: document.getElementById('cAddress').value.trim()
  };
  try {
    await apiCall('POST', '/api/contacts', body);
    bootstrap.Modal.getInstance(document.getElementById('contactModal')).hide();
    document.getElementById('contactForm').reset();
    loadContacts();
  } catch (err) {
    alertEl.textContent = err.message;
    alertEl.classList.remove('d-none');
  }
});

loadContacts();
