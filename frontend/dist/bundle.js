/******/ (() => { // webpackBootstrap
/******/ 	"use strict";
/******/ 	var __webpack_modules__ = ({

/***/ "./src/css/styles.css":
/*!****************************!*\
  !*** ./src/css/styles.css ***!
  \****************************/
/***/ ((__unused_webpack_module, __webpack_exports__, __webpack_require__) => {

__webpack_require__.r(__webpack_exports__);
// extracted by mini-css-extract-plugin


/***/ })

/******/ 	});
/************************************************************************/
/******/ 	// The module cache
/******/ 	var __webpack_module_cache__ = {};
/******/ 	
/******/ 	// The require function
/******/ 	function __webpack_require__(moduleId) {
/******/ 		// Check if module is in cache
/******/ 		var cachedModule = __webpack_module_cache__[moduleId];
/******/ 		if (cachedModule !== undefined) {
/******/ 			return cachedModule.exports;
/******/ 		}
/******/ 		// Create a new module (and put it into the cache)
/******/ 		var module = __webpack_module_cache__[moduleId] = {
/******/ 			// no module.id needed
/******/ 			// no module.loaded needed
/******/ 			exports: {}
/******/ 		};
/******/ 	
/******/ 		// Execute the module function
/******/ 		__webpack_modules__[moduleId](module, module.exports, __webpack_require__);
/******/ 	
/******/ 		// Return the exports of the module
/******/ 		return module.exports;
/******/ 	}
/******/ 	
/************************************************************************/
/******/ 	/* webpack/runtime/make namespace object */
/******/ 	(() => {
/******/ 		// define __esModule on exports
/******/ 		__webpack_require__.r = (exports) => {
/******/ 			if(typeof Symbol !== 'undefined' && Symbol.toStringTag) {
/******/ 				Object.defineProperty(exports, Symbol.toStringTag, { value: 'Module' });
/******/ 			}
/******/ 			Object.defineProperty(exports, '__esModule', { value: true });
/******/ 		};
/******/ 	})();
/******/ 	
/************************************************************************/
var __webpack_exports__ = {};
// This entry needs to be wrapped in an IIFE because it needs to be isolated against other modules in the chunk.
(() => {
/*!***********************!*\
  !*** ./src/js/app.ts ***!
  \***********************/
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _css_styles_css__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../css/styles.css */ "./src/css/styles.css");
// ダミーデータ
var data = [
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

var backendUrl = "http://192.168.10.8:8080";
// テーブル描画
function renderTable() {
    var tbody = document.querySelector("#summary-table tbody");
    if (!tbody)
        return;
    tbody.innerHTML = "";
    data.forEach(function (yearData, yearIndex) {
        // 年次行
        var yearRow = document.createElement("tr");
        yearRow.classList.add("year-row");
        yearRow.innerHTML = "\n      <td>".concat(yearData.year, "</td>\n      <td>").concat(yearData.expense, "</td>\n      <td>").concat(yearData.income, "</td>\n      <td>").concat(yearData.savings, "</td>\n    ");
        yearRow.addEventListener("click", function () { return toggleYear(yearIndex); });
        tbody.appendChild(yearRow);
        // 月次行
        yearData.months.forEach(function (month, monthIndex) {
            var monthRow = document.createElement("tr");
            monthRow.classList.add("month-row", "year-".concat(yearIndex));
            monthRow.innerHTML = "\n        <td>".concat(month.name, "</td>\n        <td>").concat(month.expense, "</td>\n        <td>").concat(month.income, "</td>\n        <td>").concat(month.savings, " \n          <button onclick=\"openMonth(").concat(yearIndex, ", ").concat(monthIndex, ")\">Open</button>\n        </td>\n      ");
            tbody.appendChild(monthRow);
        });
        // Add Month行
        var addRow = document.createElement("tr");
        addRow.classList.add("month-row", "year-".concat(yearIndex), "add-month-row");
        addRow.innerHTML = "\n      <td colspan=\"4\">\n        <button onclick=\"addMonth(".concat(yearIndex, ")\">Add Month</button>\n      </td>\n    ");
        tbody.appendChild(addRow);
    });
}
// アコーディオン切り替え
function toggleYear(yearIndex) {
    var rows = document.querySelectorAll(".year-".concat(yearIndex));
    rows.forEach(function (row) {
        row.style.display =
            row.style.display === "table-row" ? "none" : "table-row";
    });
}
// 月オープン→遷移処理
function openMonth(yearIndex, monthIndex) {
    var year = data[yearIndex].year;
    var month = data[yearIndex].months[monthIndex].name;
    alert("".concat(year, "\u5E74 ").concat(month, " \u306EExpense\u30DA\u30FC\u30B8\u306B\u9077\u79FB\uFF01\uFF08\u3053\u3053\u3067\u30DA\u30FC\u30B8\u79FB\u52D5\u51E6\u7406\u3092\u66F8\u304F\uFF09"));
    // 例: location.href = `/expense.html?year=${year}&month=${month}`;
}
// 月追加処理
function addMonth(yearIndex) {
    var newMonth = {
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
window.openMonth = openMonth;
window.addMonth = addMonth;
//test
var testText = "This is test!";
console.log(testText);

})();

/******/ })()
;
//# sourceMappingURL=bundle.js.map