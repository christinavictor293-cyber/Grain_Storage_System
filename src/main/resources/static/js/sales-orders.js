requireAuth();
document.getElementById('navUser').textContent = getUsername();

async function loadOrders() {
  const orders = await apiCall('GET', '/api/sales-orders');
  const tbody = document.getElementById('orderTable');
  tbody.innerHTML = '';
  orders.forEach(o => {
    const badge = o.status === 'DISPATCHED'
      ? '<span class="badge bg-success">DISPATCHED</span>'
      : '<span class="badge bg-warning text-dark">PENDING</span>';
    tbody.innerHTML += `<tr>
      <td>${o.id}</td>
      <td>${o.contact ? o.contact.name : ''}</td>
      <td>${o.grainLot ? o.grainLot.grainType + ' #' + o.grainLot.id : ''}</td>
      <td>${o.quantityTons}</td>
      <td>${o.orderDate || ''}</td>
      <td>${badge}</td>
    </tr>`;
  });
}

document.getElementById('openModalBtn').addEventListener('click', async () => {
  const [contacts, grainLots] = await Promise.all([
    apiCall('GET', '/api/contacts'),
    apiCall('GET', '/api/grain-lots')
  ]);
  const customers = contacts.filter(c => c.contactType === 'CUSTOMER');
  document.getElementById('soCustomer').innerHTML = customers.map(c => `<option value="${c.id}">${c.name}</option>`).join('');
  document.getElementById('soGrainLot').innerHTML = grainLots.map(l => `<option value="${l.id}">${l.grainType} #${l.id}</option>`).join('');
  document.getElementById('soDate').value = new Date().toISOString().split('T')[0];
  new bootstrap.Modal(document.getElementById('orderModal')).show();
});

document.getElementById('saveOrder').addEventListener('click', async () => {
  const alertEl = document.getElementById('modalAlert');
  alertEl.classList.add('d-none');
  const body = {
    contact: { id: parseInt(document.getElementById('soCustomer').value) },
    grainLot: { id: parseInt(document.getElementById('soGrainLot').value) },
    quantityTons: parseFloat(document.getElementById('soQty').value),
    orderDate: document.getElementById('soDate').value
  };
  try {
    await apiCall('POST', '/api/sales-orders', body);
    bootstrap.Modal.getInstance(document.getElementById('orderModal')).hide();
    document.getElementById('orderForm').reset();
    loadOrders();
  } catch (err) {
    alertEl.textContent = err.message;
    alertEl.classList.remove('d-none');
  }
});

loadOrders();
