const mockIncome = [
  { type: 'projected', category: 'Salary', amount: 5000, date: '2025-01-01' },
  { type: 'actual', category: 'Salary', amount: 4900, date: '2025-01-01' }
];

function renderIncomeTable(){
const tableBody=document.querySelector('#income-table tbody');
tableBody.innerHTML='';

mockIncome.forEach((income, index)=>{
const row =document.createElement('tr');
    row.innerHTML = `
      <td>${income.type}</td>
      <td>${income.category}</td>
      <td>${income.amount}</td>
      <td>${income.date}</td>
      <td>
        <button onclick="deleteIncome(${index})">Delete</button>
      </td>
    `;
    tableBody.appendChild(row);
})
}

document.querySelector('#income-form').addEventListener('submit', function(e){
e.preventDefault();

const type=document.querySelector('#type').value;
const category=document.querySelector('#category').value;
const amount=parseFloat(document.querySelector('#amount').value);
const date=document.querySelector('#date').value;

if (type && category && amount && date){
mockIncome.push({type, category,amount,date});
renderIncomeTable();
this.reset();
}else{
alert('Please fill out all fields!');
}
});

function deleteIncome(index){
mockIncome.splice(index,1);
renderIncomeTable();
}
renderIncomeTable();

