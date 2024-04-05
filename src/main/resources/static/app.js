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
    <td class="name">${meal.mealName}</td>
    <td class="category">${meal.category}</td>
    <td align="center">
      <button id="editBtn"
      type="button"

      aria-hidden="true"
      onclick="editMeal(this);">
      <span id="edit" class="material-symbols-outlined"> edit </span>
      </button>
    </td>
    <td>
    <td align="center">
    <button id="doneBtn"
    type="button"

    aria-hidden="true"
    onclick="updateMeal(this);">
    <span id="done" class="material-symbols-outlined"> done </span>
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

async function editMeal(el) {
  getMealsData().then((data) => {
    console.log("ping");
    const row = el.parentNode.parentNode.rowIndex;
    console.log(`Index: ${row - 1} | Table row: ${row}`);
    const meal = data.at(row - 1);
    // console.log(`ID: ${meal.id}`);
    // console.log(`Name: ${meal.mealName} Category: ${meal.category}`);

    document.querySelector("#editBtn").style.display = "none";
    document.querySelector("#doneBtn").style.display = "block";

    let mealName = document.querySelector(".name");
    console.log(`meal name ${mealName.innerHTML}`);

    mealName =
      "<input type='text' id='nameIn" + "' value='" + mealName.innerHTML + "'>";

    // let mealName = document.querySelector("#name");
    // console.log(mealName.innerHTML);
    // let mealNameData = mealName.innerHTML;

    // mealName.innerHTML =
    //   "<input type='text' id='nameIn" + "' value='" + mealNameData + "'>";
  });
}

async function updateMeal(el) {
  console.log("Upldate clicked");
  const row = el.parentNode.parentNode.rowIndex;
  alert("Done");
  document.querySelector("#doneBtn").style.display = "none";
  document.querySelector("#editBtn").style.display = "block";
}

function capitalizeFirstLetter(string) {
  return string.charAt(0).toUpperCase() + string.slice(1).toLowerCase();
}

getMealsData().then((data) => {
  createMealsTable(data);

  // EDIT
  const editBtn = document.querySelector("#editBtn");
  editBtn.addEventListener("click", function (e) {
    editMeal(editBtn);
  });

  // DELETE
  const deleteBtn = document.querySelector("#deleteBtn");
  deleteBtn.addEventListener("click", () => {
    deleteMeal(deleteBtn);
  });
});

// ADD MEW MEALS
const form = document.querySelector("#newMealForm");
const btn = document.querySelector("#submitBtn");

form.addEventListener("submit", function (e) {
  e.preventDefault();
  addNewMeal().then(() => loadTable());
});
