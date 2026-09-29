requireAuth();
document.getElementById('navUser').textContent = getUsername();

async function load() {
  const [invoices, orders] = await Promise.all([
    apiCall('GET', '/api/customer-invoices'),
    apiCall('GET', '/api/sales-orders')
  ]);

  const invoicedOrderIds = new Set(invoices.map(i => i.salesOrder ? i.salesOrder.id : null));

  const invoiceTbody = document.getElementById('invoiceTable');
  invoiceTbody.innerHTML = '';
  invoices.forEach(i => {
    const badge = i.status === 'PAID'
      ? '<span class="badge bg-success">PAID</span>'
      : '<span class="badge bg-warning text-dark">UNPAID</span>';
    invoiceTbody.innerHTML += `<tr>
      <td>${i.id}</td>
      <td>${i.salesOrder ? i.salesOrder.id : ''}</td>
      <td>₹${i.amount ? i.amount.toFixed(2) : ''}</td>
      <td>${i.invoiceDate || ''}</td>
      <td>${badge}</td>
    </tr>`;
  });

  const pendingTbody = document.getElementById('pendingOrderTable');
  pendingTbody.innerHTML = '';
  orders.filter(o => o.status === 'PENDING' && !invoicedOrderIds.has(o.id)).forEach(o => {
    pendingTbody.innerHTML += `<tr>
      <td>${o.id}</td>
      <td>${o.contact ? o.contact.name : ''}</td>
      <td>${o.grainLot ? o.grainLot.grainType + ' #' + o.grainLot.id : ''}</td>
      <td>${o.quantityTons}</td>
      <td><button class="btn btn-sm btn-success" onclick="createInvoice(${o.id})">Generate Invoice</button></td>
    </tr>`;
  });
}

async function createInvoice(salesOrderId) {
  try {
    await apiCall('POST', `/api/customer-invoices/from-sales-order/${salesOrderId}`);
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
