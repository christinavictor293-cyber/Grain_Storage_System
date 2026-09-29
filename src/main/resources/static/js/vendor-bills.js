requireAuth();
document.getElementById('navUser').textContent = getUsername();

async function load() {
  const [bills, grainLots] = await Promise.all([
    apiCall('GET', '/api/vendor-bills'),
    apiCall('GET', '/api/grain-lots')
  ]);

  const billedLotIds = new Set(bills.map(b => b.grainLot ? b.grainLot.id : null));

  const billTbody = document.getElementById('billTable');
  billTbody.innerHTML = '';
  bills.forEach(b => {
    const badge = b.status === 'PAID'
      ? '<span class="badge bg-success">PAID</span>'
      : '<span class="badge bg-warning text-dark">PENDING</span>';
    const payBtn = b.status === 'PENDING'
      ? `<button class="btn btn-sm btn-primary" onclick="payBill(${b.id})">Pay via Bank</button>`
      : '';
    billTbody.innerHTML += `<tr>
      <td>${b.grainLot ? b.grainLot.id : ''}</td>
      <td>${b.farmer ? b.farmer.name : ''}</td>
      <td>₹${b.amount ? b.amount.toFixed(2) : ''}</td>
      <td>${b.billDate || ''}</td>
      <td>${badge}</td>
      <td>${payBtn}</td>
    </tr>`;
  });

  const unbilledTbody = document.getElementById('unbilledTable');
  unbilledTbody.innerHTML = '';
  grainLots.filter(l => !billedLotIds.has(l.id)).forEach(l => {
    unbilledTbody.innerHTML += `<tr>
      <td>${l.id}</td>
      <td>${l.grainType}</td>
      <td>${l.quantityTons}</td>
      <td>${l.farmer ? l.farmer.name : ''}</td>
      <td><button class="btn btn-sm btn-success" onclick="createBill(${l.id})">Create Bill</button></td>
    </tr>`;
  });
}

async function createBill(grainLotId) {
  try {
    await apiCall('POST', `/api/vendor-bills/from-grain-lot/${grainLotId}`);
    load();
  } catch (err) {
    showAlert(err.message, 'danger');
  }
}

async function payBill(billId) {
  try {
    await apiCall('POST', `/api/payments/vendor-bill/${billId}?mode=BANK`);
    load();
  } catch (err) {
    showAlert(err.message, 'danger');
  }
}

function showAlert(msg, type) {
  const el = document.getElementById('alertBox');
  el.className = `alert alert-${type}`;
  el.textContent = msg;
}

load();
