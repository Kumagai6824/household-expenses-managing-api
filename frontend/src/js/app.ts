// types.ts (型定義ファイルに分けてもOK)
interface MonthData {
  name: string;
  expense: number;
  income: number;
  savings: number;
}

interface YearData {
  year: number;
  expense: number;
  income: number;
  savings: number;
  months: MonthData[];
}

// ダミーデータ
const data: YearData[] = [
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

const backendUrl: string = "http://192.168.10.8:8080";

// テーブル描画
function renderTable(): void {
  const tbody = document.querySelector<HTMLTableSectionElement>(
    "#summary-table tbody",
  );

  if (!tbody) return;
  tbody.innerHTML = "";

  data.forEach((yearData, yearIndex) => {
    // 年次行
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

    // 月次行
    yearData.months.forEach((month, monthIndex) => {
      const monthRow = document.createElement("tr");
      monthRow.classList.add("month-row", `year-${yearIndex}`);
      monthRow.innerHTML = `
        <td>${month.name}</td>
        <td>${month.expense}</td>
        <td>${month.income}</td>
        <td>${month.savings} 
          <button onclick="openMonth(${yearIndex}, ${monthIndex})">Open</button>
        </td>
      `;
      tbody.appendChild(monthRow);
    });

    // Add Month行
    const addRow = document.createElement("tr");
    addRow.classList.add("month-row", `year-${yearIndex}`, "add-month-row");
    addRow.innerHTML = `
      <td colspan="4">
        <button onclick="addMonth(${yearIndex})">Add Month</button>
      </td>
    `;
    tbody.appendChild(addRow);
  });
}

// アコーディオン切り替え
function toggleYear(yearIndex: number): void {
  const rows = document.querySelectorAll<HTMLTableRowElement>(
    `.year-${yearIndex}`,
  );
  rows.forEach((row) => {
    row.style.display =
      row.style.display === "table-row" ? "none" : "table-row";
  });
}

// 月オープン→遷移処理
function openMonth(yearIndex: number, monthIndex: number): void {
  const year = data[yearIndex].year;
  const month = data[yearIndex].months[monthIndex].name;
  alert(
    `${year}年 ${month} のExpenseページに遷移！（ここでページ移動処理を書く）`,
  );
  // 例: location.href = `/expense.html?year=${year}&month=${month}`;
}

// 月追加処理
function addMonth(yearIndex: number): void {
  const newMonth: MonthData = {
    name: "NewMonth",
    expense: 0,
    income: 0,
    savings: 0,
  };
  data[yearIndex].months.push(newMonth);
  renderTable();
}

// 初期描画
window.onload = renderTable;

// グローバルに関数をバインド（onclick用）
(window as any).openMonth = openMonth;
(window as any).addMonth = addMonth;

//test
let testText: string = "This is test!";
console.log(testText);
