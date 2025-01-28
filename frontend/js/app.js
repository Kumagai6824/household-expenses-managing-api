// Mock data for income and expenses
const mockIncome = [];
const mockExpenses = [];

// Function to render income table
async function renderIncomeTable() {
  const tableBody = document.querySelector("#income-table tbody");
  tableBody.innerHTML = ""; // Clear the table first

  try {
    // Fetch income data from the backend
    const response = await fetch("http://localhost:8080/income");
    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }

    const responseData = await response.json();
    console.log("Response data:", responseData); // Debug the raw response

    // Check if the response is an array or wrapped in an object
    const incomeData = Array.isArray(responseData)
      ? responseData
      : responseData.data;

    // Populate the table with backend data
    incomeData.forEach((income) => {
      const row = document.createElement("tr");
      row.innerHTML = `
        <td>${income.type}</td>
        <td>${income.category}</td>
        <td>${income.amount}</td>
        <td>${income.usedDate}</td>
        <td>
          <button onclick="deleteIncome(${income.id})">Delete</button>
        </td>
      `;
      tableBody.appendChild(row);
    });
  } catch (error) {
    console.error("Error fetching income data:", error);
    tableBody.innerHTML = '<tr><td colspan="5">Failed to load data.</td></tr>';
  }
}

// Function to render expense table
function renderExpenseTable() {
  const tableBody = document.querySelector("#expense-table tbody");
  tableBody.innerHTML = "";

  mockExpenses.forEach((expense, index) => {
    const row = document.createElement("tr");
    row.innerHTML = `
      <td>${expense.type}</td>
      <td>${expense.category}</td>
      <td>${expense.amount}</td>
      <td>${expense.date}</td>
      <td>
        <button onclick="deleteExpense(${index})">Delete</button>
      </td>
    `;
    tableBody.appendChild(row);
  });
}

// Function to calculate and render budget summary
function renderSummary() {
  const totalProjectedIncome = mockIncome
    .filter((item) => item.type === "projected")
    .reduce((sum, item) => sum + item.amount, 0);
  const totalActualIncome = mockIncome
    .filter((item) => item.type === "actual")
    .reduce((sum, item) => sum + item.amount, 0);
  const totalProjectedExpenses = mockExpenses
    .filter((item) => item.type === "projected")
    .reduce((sum, item) => sum + item.amount, 0);
  const totalActualExpenses = mockExpenses
    .filter((item) => item.type === "actual")
    .reduce((sum, item) => sum + item.amount, 0);

  const tableBody = document.querySelector("#summary-table tbody");
  tableBody.innerHTML = `
    <tr>
      <td>Projected</td>
      <td>${totalProjectedIncome}</td>
      <td>${totalProjectedExpenses}</td>
      <td>${totalProjectedIncome - totalProjectedExpenses}</td>
    </tr>
    <tr>
      <td>Actual</td>
      <td>${totalActualIncome}</td>
      <td>${totalActualExpenses}</td>
      <td>${totalActualIncome - totalActualExpenses}</td>
    </tr>
  `;
}

// Add Income
document
  .querySelector("#income-form")
  .addEventListener("submit", async function (e) {
    e.preventDefault();
    const type = document.querySelector("#income-type").value;
    const category = document.querySelector("#income-category").value;
    const amount = parseFloat(document.querySelector("#income-amount").value);
    const date = document.querySelector("#income-date").value;

    if (type && category && amount && date) {
      mockIncome.push({ type, category, amount, date });
      renderIncomeTable();
      renderSummary();
      this.reset();
    } else {
      alert("Please fill out all fields!");
    }
  });

// Add Expense
document
  .querySelector("#expense-form")
  .addEventListener("submit", function (e) {
    e.preventDefault();
    const type = document.querySelector("#expense-type").value;
    const category = document.querySelector("#expense-category").value;
    const amount = parseFloat(document.querySelector("#expense-amount").value);
    const date = document.querySelector("#expense-date").value;

    if (type && category && amount && date) {
      mockExpenses.push({ type, category, amount, date });
      renderExpenseTable();
      renderSummary();
      this.reset();
    } else {
      alert("Please fill out all fields!");
    }
  });

// Delete Income
function deleteIncome(index) {
  mockIncome.splice(index, 1);
  renderIncomeTable();
  renderSummary();
}

// Delete Expense
function deleteExpense(index) {
  mockExpenses.splice(index, 1);
  renderExpenseTable();
  renderSummary();
}

// Initial Render
renderIncomeTable();
renderExpenseTable();
renderSummary();
