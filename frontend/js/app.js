// Mock data for income and expenses
const mockIncome = [];
const mockExpenses = [];

async function filterData() {
  const year = document.getElementById("year").value;
  const month = document.getElementById("month").value;

  try {
    const incomeResponse = await fetch(
      `http://localhost:8080/income/filter?year=${year}&month=${month}`
    );
    if (!incomeResponse.ok) {
      throw new Error("HTTP error! Status:" + incomeResponse.status);
    }
    let incomeData = await incomeResponse.json();
    incomeData = Array.isArray(incomeData) ? incomeData : incomeData.data;

    const expenseResponse = await fetch(
      `http://localhost:8080/expense/filter?year=${year}&month=${month}`
    );
    if (!expenseResponse.ok) {
      throw new Error("HTTP error! Status:" + expenseResponse.status);
    }
    let expenseData = await expenseResponse.json();
    expenseData = Array.isArray(expenseData) ? expenseData : expenseData.data;

    console.log("Filtered Income:", incomeData);
    console.log("Filtered Expenses:", expenseData);

    renderIncomeTable(incomeData);
    renderExpenseTable(expenseData);
    renderSummary(year, month);
  } catch (error) {
    console.error("Error fetching filtered data:", error);
  }
}

// Function to render income table
async function renderIncomeTable(incomeData = null) {
  const tableBody = document.querySelector("#income-table tbody");
  tableBody.innerHTML = ""; // Clear the table

  try {
    // If incomeData is not provided, fetch all income records (for first page load)
    if (!incomeData) {
      const response = await fetch("http://localhost:8080/income");
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      incomeData = await response.json();
    }

    console.log("Income Data:", incomeData);

    incomeData = Array.isArray(incomeData) ? incomeData : incomeData.data;

    if (!Array.isArray(incomeData)) {
      throw new Error("Invalid data format: Expected an array");
    }

    // Populate the table with data
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
async function renderExpenseTable(expenseData = null) {
  const tableBody = document.querySelector("#expense-table tbody");
  tableBody.innerHTML = "";

  try {
    if (!expenseData) {
      const response = await fetch("http://localhost:8080/expense");
      if (!response.ok) {
        throw new Error("HTTP error! Status:" + response.status);
      }
      expenseData = await response.json();
    }

    console.log("Expense data:", expenseData);

    expenseData = Array.isArray(expenseData) ? expenseData : expenseData.data;

    if (!Array.isArray(expenseData)) {
      throw new Error("Invalid data format: Expected an array");
    }

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
async function renderSummary(year = null, month = null) {
  try {
    let incomeUrl = "http://localhost:8080/income";
    let expenseUrl = "http://localhost:8080/expense";

    if (year && month) {
      incomeUrl += "/filter?year=" + year + "&month=" + month;
      expenseUrl += "/filter?year=" + year + "&month=" + month;
    }

    const incomeResponse = await fetch(incomeUrl);
    if (!incomeResponse.ok) {
      throw new Error("HTTP error! Status:" + incomeResponse.status);
    }

    let incomeData = await incomeResponse.json();
    incomeData = Array.isArray(incomeData) ? incomeData : incomeData.data;

    const expenseResponse = await fetch(expenseUrl);
    if (!expenseResponse.ok) {
      throw new Error("HTTP error! Status:" + expenseResponse.status);
    }

    let expenseData = await expenseResponse.json();
    expenseData = Array.isArray(expenseData) ? expenseData : expenseData.data;

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
async function deleteExpense(id) {
  if (!confirm("Are you sure you want to delete income iD " + id + "?")) {
    return;
  }

  try {
    const response = await fetch(`http://localhost:8080/expense/${id}`, {
      method: "DELETE",
    });

    if (!response.ok) {
      throw new Error("HTTP error! Status:" + response.status);
    }

    console.log("Expense ID " + id + " deleted successfully");

    await renderExpenseTable();
    await renderSummary();
  } catch (error) {
    console.error("Error deleting expense:", error);
    alert("Failed to delete expense");
  }
}

// Load all income & expenses when the page loads
window.onload = function () {
  renderIncomeTable(); // Loads all income
  renderExpenseTable(); // Loads all expenses
  renderSummary();
};
