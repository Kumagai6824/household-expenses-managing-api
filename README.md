# household-expenses-managing-api

---

### 1. **Planning Phase**

**Refined Requirements for Simplicity**

1. **Income Management:**
    - Add projected income.
    - Add actual income.
    - Automatically calculate the difference (projected - actual).

2. **Expense Management:**
    - Add projected expense.
    - Add actual expense.
    - Automatically calculate the difference (projected - actual).

3. **Summaries:**
    - View total projected income, total actual income, and the difference.
    - View total projected expenses, total actual expenses, and the difference.
    - View a budget summary (e.g., total income - total expenses).

4. **CRUD Operations:**
    - Add, update, and delete individual entries for income and expenses.

---

### **Detailed Use Cases**

#### **1. Adding Data**

- **As a user, I want to add a new projected or actual income/expense** so that I can track my financial plans and
  spending.
    - Inputs: `type (income/expense)`, `category`, `amount`, `date`.
    - System Response: Store the data and recalculate summaries.

#### **2. Viewing Summaries**

- **As a user, I want to view summaries** so that I can understand my overall financial situation.
    - Display: Total projected, actual, and difference for income and expenses.

#### **3. Updating Data**

- **As a user, I want to update an entry** in case I make a mistake or my plans change.
    - Inputs: Record ID, updated values.
    - System Response: Update the database and recalculate summaries.

#### **4. Deleting Data**

- **As a user, I want to delete an entry** to keep my data accurate.
    - Inputs: Record ID.
    - System Response: Remove the record and recalculate summaries.

---

### **Database Schema**

#### **1. Income Table**

Stores information about all projected and actual incomes.

| Column Name       | Data Type    | Description                                   |
|-------------------|--------------|-----------------------------------------------|
| `id`              | INT (PK)     | Unique identifier for the income entry.       |
| `type`            | ENUM         | Type of income: `projected`, `actual`.        |
| `category`        | VARCHAR(50)  | Income category (e.g., Salary, Bonus).        |
| `amount`          | DECIMAL(10,2)| Amount of income.                             |
| `date`            | DATE         | Date of the income.                           |
| `created_at`      | TIMESTAMP    | Record creation timestamp.                    |
| `updated_at`      | TIMESTAMP    | Record last update timestamp.                 |

---

#### **2. Expense Table**

Stores information about all projected and actual expenses.

| Column Name       | Data Type    | Description                                   |
|-------------------|--------------|-----------------------------------------------|
| `id`              | INT (PK)     | Unique identifier for the expense entry.      |
| `type`            | ENUM         | Type of expense: `projected`, `actual`.       |
| `category`        | VARCHAR(50)  | Expense category (e.g., Rent, Groceries).     |
| `amount`          | DECIMAL(10,2)| Amount of expense.                            |
| `date`            | DATE         | Date of the expense.                          |
| `created_at`      | TIMESTAMP    | Record creation timestamp.                    |
| `updated_at`      | TIMESTAMP    | Record last update timestamp.                 |

---

#### **3. Summary View (Virtual Table)**

This will be a database view for summaries. It calculates totals and differences dynamically from the `Income`
and `Expense` tables.

**Projected Summary View:**
Calculation in Java

**Actual Summary View:**
Similar to above, but uses `type = 'actual'`.

---
vGot it! If you're not using JPA, you can opt for **JDBC Template** or similar approaches in Spring Boot for database
interactions. JDBC Template is lightweight, easy to use, and allows more control over SQL queries.

Now, let’s move on to **API design** for your app. Here’s how we can structure it:

---

### **API Design**

#### **Base URL**

`/api/v1`

#### **Endpoints**

1. **Income Management**
    - **Add Income (POST)**
        - URL: `/api/v1/income`
        - Description: Add a new income entry (projected or actual).
        - Request Body:
          ```json
          {
            "type": "projected",
            "category": "Salary",
            "amount": 5000,
            "date": "2025-01-01"
          }
          ```
        - Response:
          ```json
          {
            "message": "Income added successfully"
          }
          ```

    - **Get All Income (GET)**
        - URL: `/api/v1/income`
        - Description: Fetch all income entries.
        - Response:
          ```json
          [
            {
              "id": 1,
              "type": "projected",
              "category": "Salary",
              "amount": 5000,
              "date": "2025-01-01"
            },
            {
              "id": 2,
              "type": "actual",
              "category": "Salary",
              "amount": 4900,
              "date": "2025-01-01"
            }
          ]
          ```

    - **Update Income (PUT)**
        - URL: `/api/v1/income/{id}`
        - Description: Update an existing income entry.
        - Request Body (Example):
          ```json
          {
            "type": "actual",
            "category": "Salary",
            "amount": 4950,
            "date": "2025-01-01"
          }
          ```
        - Response:
          ```json
          {
            "message": "Income updated successfully"
          }
          ```

    - **Delete Income (DELETE)**
        - URL: `/api/v1/income/{id}`
        - Description: Delete an income entry.
        - Response:
          ```json
          {
            "message": "Income deleted successfully"
          }
          ```

---

2. **Expense Management**
    - Similar endpoints as Income:
        - Add Expense: `/api/v1/expense` (POST)
        - Get All Expenses: `/api/v1/expense` (GET)
        - Update Expense: `/api/v1/expense/{id}` (PUT)
        - Delete Expense: `/api/v1/expense/{id}` (DELETE)

---

3. **Summary Management**
    - **Get Summary (GET)**
        - URL: `/api/v1/summary`
        - Description: Fetch calculated summaries for income, expenses, and budget.
        - Response:
          ```json
          {
            "totalProjectedIncome": 5000,
            "totalActualIncome": 4900,
            "incomeDifference": 100,
            "totalProjectedExpenses": 3000,
            "totalActualExpenses": 3200,
            "expenseDifference": -200,
            "budgetDifference": 700
          }
          ```

---

### **Basic UI Layout**

#### **1. Header**

- Displays the app name (e.g., "Family Budget Manager").
- Could include navigation links in the future (e.g., "Dashboard," "Settings").

#### **2. Main Sections**

The main screen can be divided into three key sections:

1. **Income Management**
    - A form to add new income (type, category, amount, date).
    - A table or list to display existing income entries with options to edit/delete.

2. **Expense Management**
    - A form to add new expenses (type, category, amount, date).
    - A table or list to display existing expense entries with options to edit/delete.

3. **Summary**
    - Display key summaries such as:
        - Total projected income, actual income, and difference.
        - Total projected expenses, actual expenses, and difference.
        - Overall budget surplus/deficit (income - expenses).

---

### 3. **Back-End Development**

- **Set Up Spring Boot Project:**
    - Use Spring Initializr to create your project with dependencies (Spring Web, Spring Data JPA, MySQL).
- **Implement CRUD APIs:**
    - Controllers for handling HTTP requests.
    - Services for business logic (e.g., summary/difference calculations).
    - Repositories for interacting with the database.
- **Use Docker:**
    - Set up MySQL in a Docker container.
    - Connect your Spring Boot app to the Dockerized database.
- Test APIs using tools like **Postman**.

---

### 4. **Database Design**

- Example schema:
    - **Users:** `id`, `username`, `email`, `password`.
    - **Budgets:** `id`, `user_id`, `type` (income/expense), `category`, `projected`, `actual`.
- Use **RDS on AWS** for production.

---

### 5. **Infrastructure**

- **AWS Setup:**
    - Use **VPC** for network isolation.
    - Launch an **EC2 instance** for hosting the Spring Boot app.
    - Use **RDS** for your MySQL database.
    - Set up security groups to allow communication between EC2 and RDS.
- **Dockerize the App:**
    - Use a Dockerfile to containerize the Spring Boot app.
    - Push the image to **Amazon Elastic Container Registry (ECR)**.

---

### 6. **Agile Development with Scrum**

- **Sprint Planning:**
    - Break down tasks into small user stories (e.g., "As a user, I want to add an expense").
- **Daily Standups:**
    - Briefly discuss progress, blockers, and next steps.
- **Sprint Review:**
    - Demonstrate completed features to stakeholders (e.g., your family!).
- Use tools like **Jira** or **Trello** for task tracking.

---

### 7. **Testing**

- Write **unit tests** for back-end logic using JUnit.
- Test the front-end using tools like Selenium or Cypress.
- Perform integration testing to ensure front-end and back-end work seamlessly.

---

### 8. **Deployment**

- Deploy the front-end on **AWS S3/CloudFront** for static hosting.
- Deploy the back-end on the **EC2 instance** or using AWS Fargate for scalability.
- Use **AWS CodePipeline** for CI/CD automation.

---

### 9. **Post-Deployment**

- Set up monitoring (e.g., **AWS CloudWatch**, logging).
- Gather feedback for continuous improvement.

---
