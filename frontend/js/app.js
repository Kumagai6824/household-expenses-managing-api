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
async function renderExpenseTable() {
  const tableBody = document.querySelector("#expense-table tbody");
  tableBody.innerHTML = "";

  try {
    const response = await fetch("http://localhost:8080/expense");
    if (!response.ok) {
      throw new Error("HTTP error! Status:" + response.status);
    }
    const responseData = await response.json();
    console.log("Response data:", responseData);

    const expenseData = Array.isArray(responseData)
      ? responseData
      : responseData.data;

    expenseData.forEach((expense) => {
      const row = document.createElement("tr");
      row.innerHTML = `
      <td>${expense.type}</td>
      <td>${expense.category}</td>
      <td>${expense.amount}</td>
      <td>${expense.usedDate}</td>
      <td>
        <button onclick="deleteExpense(${expense.id})">Delete</button>
      </td>
    `;
      tableBody.appendChild(row);
    });
  } catch (error) {
    console.error("Error fetching expense data:" + error);
    tableBody.innerHTML = '<tr><td colspan="5">Failed to load data.</td></tr>';
  }
}

// Function to calculate and render budget summary
async function renderSummary() {
  try {
    const incomeResponse = await fetch("http://localhost:8080/income");
    if (!incomeResponse.ok) {
      throw new Error("HTTP error! Status:" + incomeResponse.status);
    }

    const incomeResponseData = await incomeResponse.json();
    console.log("Response data from backend:", incomeResponseData);

    const incomeData = Array.isArray(incomeResponseData)
      ? incomeResponseData
      : incomeResponseData.data;

    if (!Array.isArray(incomeData)) {
      throw new Error("Invalid data format: Expected an array");
    }

    const expenseResponse = await fetch("http://localhost:8080/expense");
    if (!expenseResponse.ok) {
      throw new Error("HTTP error! Status:" + expenseResponse.status);
    }

    const expenseResponseData = await expenseResponse.json();
    console.log("Response data from backend:", expenseResponseData);

    const expenseData = Array.isArray(expenseResponseData)
      ? expenseResponseData
      : expenseResponseData.data;

    if (!Array.isArray(expenseData)) {
      throw new Error("Invalid data format: Expected an array");
    }

    const totalProjectedIncome = incomeData
      .filter((item) => item.type.toLowerCase() === "projected")
      .reduce((sum, item) => sum + item.amount, 0);
    const totalActualIncome = incomeData
      .filter((item) => item.type.toLowerCase() === "actual")
      .reduce((sum, item) => sum + item.amount, 0);
    const totalProjectedExpenses = expenseData
      .filter((item) => item.type.toLowerCase() === "projected")
      .reduce((sum, item) => sum + item.amount, 0);
    const totalActualExpenses = expenseData
      .filter((item) => item.type.toLowerCase() === "actual")
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
  } catch (error) {
    console.error("Error fetching summary data:", error);
    const tableBody = document.querySelector("#summary-table tbody");
    tableBody.innerHTML =
      '<tr><td colspan="2">Failed to load summary.</td></tr>';
  }
}

// Add Income
document
  .querySelector("#income-form")
  .addEventListener("submit", async function (e) {
    e.preventDefault();
    const type = document.querySelector("#income-type").value;
    const category = document.querySelector("#income-category").value;
    const amount = parseFloat(document.querySelector("#income-amount").value);
    const usedDate = document.querySelector("#income-date").value;

    if (type && category && amount && usedDate) {
      const newIncome = { type, category, amount, usedDate };
      try {
        const response = await fetch("http://localhost:8080/income", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(newIncome),
        });

        if (!response.ok) {
          throw new Error("HTTP error! Status: ${response.status}");
        }

        console.log("Income added successfully");
        await renderIncomeTable();
        await renderSummary();
        this.reset();
      } catch (error) {
        console.error("Error adding income: ", error);
        alert("Failed to add income!");
      }
    } else {
      alert("Please fill out all fields!");
    }
  });

// Add Expense
document
  .querySelector("#expense-form")
  .addEventListener("submit", async function (e) {
    e.preventDefault();
    const type = document.querySelector("#expense-type").value;
    const category = document.querySelector("#expense-category").value;
    const amount = parseFloat(document.querySelector("#expense-amount").value);
    const usedDate = document.querySelector("#expense-date").value;

    if (type && category && amount && usedDate) {
      const newExpense = { type, category, amount, usedDate };
      try {
        const response = await fetch("http://localhost:8080/expense", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(newExpense),
        });

        if (!response.ok) {
          throw new Error("HTTP error! Status: " + response.status);
        }

        console.log("Expense added successfully");
        await renderExpenseTable();
        await renderSummary();
        this.reset();
      } catch (error) {
        console.error("Error adding expense: ", error);
        alert("Failed to add income!");
      }
    } else {
      alert("Please fill out all fields!");
    }
  });

// Delete Income
async function deleteIncome(id) {
  if (!confirm("Are you sure you want to delete income ID " + id + "?")) {
    return;
  }

  try {
    const response = await fetch(`http://localhost:8080/income/${id}`, {
      method: "DELETE",
    });

    if (!response.ok) {
      throw new Error("HTTP error! Status:" + response.status);
    }

    console.log("Income ID " + id + " deleted successfully");

    await renderIncomeTable();
    await renderSummary();
  } catch (error) {
    console.error("Error deleting income:", error);
    alert("Failed to delete income!");
  }
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
