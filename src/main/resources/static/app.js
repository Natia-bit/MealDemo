function createMealsTable(mealData) {
  let table = '<table style="border-collapse: collapse;">';

  table += `
  <thead>
    <tr>
      <th>Name</th>
      <th>Category</th>
      <th colspan="2">Action</th>
    </tr>
  </thead>
  <tbody>
`;

  mealData.forEach((meal) => {
    table += `
    <tr>
    <td>${meal.mealName}</td>
    <td>${meal.category}</td>
    <td align="center">
      <button id="editBtn">
        <span class="material-symbols-outlined"> edit </span>
      </button>
    </td>
    <td>
    <button id="deleteBtn"
      type="button"
      class="close"
      aria-hidden="true"
      onclick="deleteMeal(this);"
    >
      <span class="material-symbols-outlined"> delete </span>
    </button>
  </td>
  </tr>
  `;
  });

  table += `
  </tbody>
  </table>
`;

  const dataTable = document.getElementById("dataTable");
  dataTable.innerHTML = table;
}

async function getMealsData() {
  try {
    const response = await axios.get("/api/meals");
    return response.data;
  } catch (error) {
    console.error(error);
  }
}

async function loadTable() {
  getMealsData().then((data) => {
    console.log("In loading table");
    return createMealsTable(data);
  });
}

async function addNewMeal() {
  try {
    const mealName = document.querySelector("#mealName");
    const mealCategory = document.querySelector("#mealCategory");

    const response = await axios.post(
      "/api/meals",
      {
        mealName: `${capitalizeFirstLetter(mealName.value)}`,
        category: mealCategory.value,
      },
      {
        headers: { "Content-Type": "application/json" },
      }
    );

    mealName.value = "";
    mealCategory.value = "";

    return response;
  } catch (e) {
    console.log(e);
  }
}

async function deleteMeal(el) {
  if (!confirm("Are you sure you want to delete?")) return;
  try {
    const row = el.parentNode.parentNode.rowIndex;
    getMealsData()
      .then((data) => {
        const meal = data.at(row - 1);
        const response = axios.delete(`/api/meals/${meal.id}`);
        return response;
      })
      .then(() => {
        loadTable();
      });
  } catch (e) {
    console.log(e);
  }
}

function capitalizeFirstLetter(string) {
  return string.charAt(0).toUpperCase() + string.slice(1).toLowerCase();
}

getMealsData().then((data) => {
  createMealsTable(data);

  const deleteBtn = document.querySelector("#deleteBtn");
  deleteBtn.addEventListener("click", () => {
    deleteMeal(deleteBtn);
  });
});

// const editBtn = document.querySelector("#editBtn");
// editBtn.addEventListener("ckick", function () {});

// ADD MEW MEALS
const form = document.querySelector("#newMealForm");
const btn = document.querySelector("#submitBtn");

form.addEventListener("submit", function (e) {
  e.preventDefault();
  addNewMeal().then(() => loadTable());
});
