import "../css/styles.css";
const backendUrl = "http://192.168.10.8:8080";

async function loadSummary(mode = "yearly") {
  try {
    const response = await fetch(`${backendUrl}/summary?mode=${mode}`);
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    let summaryData = await response.json();

    //サマリーテーブル更新
    const tableBody = document.querySelector("#summary-table tbody");
    tableBody.innerHTML = "";

    summaryData.forEach((row) => {
      const tr = document.createElement("tr");
      tr.innerHTML = `
      <td>${row.year || row.month}</td>
      <td>${row.expense}</td>
      <td>${row.income - row.expense}</td>
      `;
      tableBody.appendChild(tr);
    });
  } catch (error) {
    console.error("Error loading summary:", error);
  }
}

//初期ロード
window.onload = () => {
  loadSummary("yearly");
};

//ダミー：年追加
document.getElementById("add-year").addEventListener("click", () => {
  const table = document.querySelector("#summary-table tbody");
  const newRow = document.createElement("tr");
  newRow.innerHTML = `
  <td>2022</td>
  <td>4000</td>
  <td>4500</td>
  <td>500</td>
  `;
  table.appendChild(newRow);
});
