requireAuth();
document.getElementById('navUser').textContent = getUsername();

async function loadBudgets() {
  const budgets = await apiCall('GET', '/api/budgets');
  const tbody = document.getElementById('budgetTable');
  tbody.innerHTML = '';
  budgets.forEach(b => {
    const variance = (b.plannedAmount - b.actualAmount).toFixed(2);
    const varColor = variance >= 0 ? 'text-success' : 'text-danger';
    tbody.innerHTML += `<tr>
      <td>${b.analyticAccount ? b.analyticAccount.name : ''}</td>
      <td>₹${b.plannedAmount ? b.plannedAmount.toFixed(2) : '0.00'}</td>
      <td>₹${b.actualAmount ? b.actualAmount.toFixed(2) : '0.00'}</td>
      <td class="${varColor}">₹${variance}</td>
      <td>${b.periodStart || ''} → ${b.periodEnd || ''}</td>
    </tr>`;
  });
}

document.getElementById('openModalBtn').addEventListener('click', async () => {
  const accounts = await apiCall('GET', '/api/budgets/analytic-accounts');
  const sel = document.getElementById('bAccount');
  sel.innerHTML = accounts.map(a => `<option value="${a.id}">${a.name}</option>`).join('');
  if (accounts.length === 0) {
    sel.innerHTML = '<option disabled>No analytic accounts found — create one first</option>';
  }
  new bootstrap.Modal(document.getElementById('budgetModal')).show();
});

document.getElementById('saveBudget').addEventListener('click', async () => {
  const alertEl = document.getElementById('modalAlert');
  alertEl.classList.add('d-none');
  const body = {
    analyticAccount: { id: parseInt(document.getElementById('bAccount').value) },
    plannedAmount: parseFloat(document.getElementById('bPlanned').value),
    periodStart: document.getElementById('bStart').value,
    periodEnd: document.getElementById('bEnd').value
  };
  try {
    await apiCall('POST', '/api/budgets', body);
    bootstrap.Modal.getInstance(document.getElementById('budgetModal')).hide();
    document.getElementById('budgetForm').reset();
    loadBudgets();
  } catch (err) {
    alertEl.textContent = err.message;
    alertEl.classList.remove('d-none');
  }
});

loadBudgets();
