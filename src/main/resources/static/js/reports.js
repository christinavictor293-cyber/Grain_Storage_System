requireAuth();
document.getElementById('navUser').textContent = getUsername();

function showPermissionError() {
  document.getElementById('permissionAlert').classList.remove('d-none');
  document.getElementById('balanceSheet').classList.add('d-none');
}

async function loadBalanceSheet() {
  try {
    const data = await apiCall('GET', '/api/reports/balance-sheet');
    const assets = data.asset || {};
    const liabilities = data.liability || {};

    const assetsList = document.getElementById('assetsList');
    assetsList.innerHTML = '';
    let assetsTotal = 0;
    Object.entries(assets).forEach(([name, bal]) => {
      assetsTotal += bal;
      assetsList.innerHTML += `<li class="list-group-item d-flex justify-content-between"><span>${name}</span><span>₹${bal.toFixed(2)}</span></li>`;
    });
    document.getElementById('assetsTotal').textContent = '₹' + assetsTotal.toFixed(2);

    const liabilitiesList = document.getElementById('liabilitiesList');
    liabilitiesList.innerHTML = '';
    let liabilitiesTotal = 0;
    Object.entries(liabilities).forEach(([name, bal]) => {
      liabilitiesTotal += bal;
      liabilitiesList.innerHTML += `<li class="list-group-item d-flex justify-content-between"><span>${name}</span><span>₹${bal.toFixed(2)}</span></li>`;
    });
    document.getElementById('liabilitiesTotal').textContent = '₹' + liabilitiesTotal.toFixed(2);
  } catch (err) {
    if (err.status === 403) { showPermissionError(); return; }
    console.error(err);
  }
}

async function loadProfitLoss() {
  try {
    const data = await apiCall('GET', '/api/reports/profit-and-loss');
    const net = data.netProfit;
    document.getElementById('plIncome').textContent = '₹' + (data.totalIncome || 0).toFixed(2);
    document.getElementById('plExpense').textContent = '₹' + (data.totalExpense || 0).toFixed(2);
    const netEl = document.getElementById('plNet');
    netEl.textContent = '₹' + (net || 0).toFixed(2);
    netEl.className = net >= 0 ? 'text-success' : 'text-danger';
  } catch (err) {
    if (err.status === 403) { showPermissionError(); return; }
    console.error(err);
  }
}

async function loadBudgetVariance() {
  try {
    const rows = await apiCall('GET', '/api/reports/budget-variance');
    const tbody = document.getElementById('varianceTable');
    tbody.innerHTML = '';
    rows.forEach(r => {
      const varColor = r.variance >= 0 ? 'text-success' : 'text-danger';
      tbody.innerHTML += `<tr>
        <td>${r.analyticAccount}</td>
        <td>₹${Number(r.plannedAmount).toFixed(2)}</td>
        <td>₹${Number(r.actualAmount).toFixed(2)}</td>
        <td class="${varColor}">₹${Number(r.variance).toFixed(2)}</td>
      </tr>`;
    });
  } catch (err) {
    if (err.status === 403) { showPermissionError(); return; }
    console.error(err);
  }
}

loadBalanceSheet();
loadProfitLoss();
loadBudgetVariance();
