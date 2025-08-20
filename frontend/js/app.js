//ダミーデータ
const data = [
  {
    year: 2022,
    expense: 4000,
    income: 4500,
    savings: 500,
    months: [
      { name: "January", expense: 1000, income: 1000, savings: 100 },
      { name: "February", expense: 3000, income: 3500, savings: 400 },
    ],
  },
];

import "../css/styles.css";
const backendUrl = "http://192.168.10.8:8080";

function renderTable() {
  const tbody = document.querySelector("#summary-table tbody");
  tbody.innerHTML = "";

  data.forEach((yearData, yearIndex) => {
    //年次行
    const yearRow = document.createElement("tr");
    yearRow.classList.add("year-row");
    yearRow.innerHTML = `
    <td>${yearData.year}</td>
      <td>${yearData.expense}</td>
      <td>${yearData.income}</td>
      <td>${yearData.savings}</td>
    `;
    yearRow.addEventListener("click", () => toggleYear(yearIndex));
    tbody.appendChild(yearRow);

    //月次行
    yearData.months.forEach((month, monthIndex) => {
      const monthRow = document.createElement("tr");
      monthRow.classList.add("month-row", `year-${yearIndex}`);
      monthRow.innerHTML = `
              <td>${month.name}</td>
        <td>${month.expense}</td>
        <td>${month.income}</td>
        <td>${month.savings} <button onclick="openMonth(${yearIndex}, ${monthIndex})">Open</button></td>
      `;
      tbody.appendChild(monthRow);
    });

    //Add Month行
    const addRow = document.createElement("tr");
    addRow.classList.add("month-row", `year-${yearIndex}`, "add-month-row");
    addRow.innerHTML = `
    <td colspan="4"><button onclick="addMonth(${yearIndex})">Add Month</button></td>
    `;
    tbody.appendChild(addRow);
  });
}

//アコーディオン切り替え
function toggleYear(yearIndex) {
  const rows = document.querySelectorAll(`.year-${yearIndex}`);
  rows.forEach((row) => {
    row.style.display =
      row.style.display === "table-row" ? "none" : "table-row";
  });
}

//月オープン→繊維処理
function openMonth(yearIndex, monthIndex) {
  const year = data[yearIndex].year;
  const month = data[yearIndex].months[monthIndex].name;
  alert(
    `${year}年 ${month} のExpenseページに遷移！（ここでページ移動処理を書く）`,
  );
  // 例: location.href = `/expense.html?year=${year}&month=${month}`;
}

//月追加処理
function addMonth(yearIndex) {
  const newMonth = { name: "NewMonth", expense: 0, income: 0, savings: 0 };
  data[yearIndex].months.push(newMonth);
  renderTable();
}

//初期描画
window.onload = renderTable;
