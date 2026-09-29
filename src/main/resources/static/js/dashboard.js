requireAuth();
document.getElementById('navUser').textContent = getUsername();

async function loadDashboard() {
  const [warehouses, grainLots, vendorBills, customerInvoices] = await Promise.all([
    apiCall('GET', '/api/warehouses'),
    apiCall('GET', '/api/grain-lots'),
    apiCall('GET', '/api/vendor-bills'),
    apiCall('GET', '/api/customer-invoices')
  ]);

  document.getElementById('countWarehouses').textContent = warehouses.length;
  document.getElementById('countGrainLots').textContent = grainLots.length;
  document.getElementById('countVendorBills').textContent = vendorBills.filter(b => b.status === 'PENDING').length;
  document.getElementById('countCustomerInvoices').textContent = customerInvoices.filter(i => i.status === 'UNPAID').length;

  const tbody = document.getElementById('warehouseTable');
  tbody.innerHTML = '';
  warehouses.forEach(w => {
    const remaining = (w.capacityTons - w.currentStockTons).toFixed(2);
    tbody.innerHTML += `<tr>
      <td>${w.name}</td>
      <td>${w.location}</td>
      <td>${w.capacityTons}</td>
      <td>${w.currentStockTons}</td>
      <td>${remaining}</td>
    </tr>`;
  });
}

document.getElementById('saveWarehouse').addEventListener('click', async () => {
  const alertEl = document.getElementById('modalAlert');
  alertEl.classList.add('d-none');
  const body = {
    name: document.getElementById('wName').value.trim(),
    location: document.getElementById('wLocation').value.trim(),
    capacityTons: parseFloat(document.getElementById('wCapacity').value),
    currentStockTons: 0.0
  };
  try {
    await apiCall('POST', '/api/warehouses', body);
    bootstrap.Modal.getInstance(document.getElementById('warehouseModal')).hide();
    document.getElementById('warehouseForm').reset();
    loadDashboard();
  } catch (err) {
    alertEl.textContent = err.message;
    alertEl.classList.remove('d-none');
  }
});

loadDashboard();
