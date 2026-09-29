requireAuth();
document.getElementById('navUser').textContent = getUsername();

async function loadGrainLots() {
  const lots = await apiCall('GET', '/api/grain-lots');
  const tbody = document.getElementById('grainLotTable');
  tbody.innerHTML = '';
  lots.forEach(l => {
    const badge = l.grade === 'GRADE_A'
      ? '<span class="badge bg-success">GRADE_A</span>'
      : '<span class="badge bg-warning text-dark">SUB_STANDARD</span>';
    tbody.innerHTML += `<tr>
      <td>${l.grainType}</td>
      <td>${badge}</td>
      <td>${l.quantityTons}</td>
      <td>${l.pricePerTon}</td>
      <td>${l.warehouse ? l.warehouse.name : ''}</td>
      <td>${l.farmer ? l.farmer.name : ''}</td>
    </tr>`;
  });
}

document.getElementById('openModalBtn').addEventListener('click', async () => {
  const [warehouses, contacts] = await Promise.all([
    apiCall('GET', '/api/warehouses'),
    apiCall('GET', '/api/contacts')
  ]);
  const wSel = document.getElementById('glWarehouse');
  wSel.innerHTML = warehouses.map(w => `<option value="${w.id}">${w.name}</option>`).join('');
  const fSel = document.getElementById('glFarmer');
  const vendors = contacts.filter(c => c.contactType === 'VENDOR');
  fSel.innerHTML = vendors.map(v => `<option value="${v.id}">${v.name}</option>`).join('');
  new bootstrap.Modal(document.getElementById('grainLotModal')).show();
});

document.getElementById('saveGrainLot').addEventListener('click', async () => {
  const alertEl = document.getElementById('modalAlert');
  alertEl.classList.add('d-none');
  const body = {
    grainType: document.getElementById('glType').value.trim(),
    grade: document.getElementById('glGrade').value,
    quantityTons: parseFloat(document.getElementById('glQty').value),
    pricePerTon: parseFloat(document.getElementById('glPrice').value),
    warehouse: { id: parseInt(document.getElementById('glWarehouse').value) },
    farmer: { id: parseInt(document.getElementById('glFarmer').value) }
  };
  try {
    await apiCall('POST', '/api/grain-lots', body);
    bootstrap.Modal.getInstance(document.getElementById('grainLotModal')).hide();
    document.getElementById('grainLotForm').reset();
    loadGrainLots();
  } catch (err) {
    alertEl.textContent = err.message;
    alertEl.classList.remove('d-none');
  }
});

loadGrainLots();
