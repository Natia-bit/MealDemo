// MEALS TABLE
async function getMealsData() {
  try {
    const response = await axios.get("/api/meals");
    return response.data;
  } catch (error) {
    console.error(error);
  }
}

function createMealsTable(mealData) {
  let table = '<table style="border-collapse: collapse;">';

  table += `
  <thead>
    <tr>
      <th>Name</th>
      <th>Category</th>
      <th>Action</th>
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
        <button id="editBtn"><span class="material-symbols-outlined">
        edit
        </span></button>
        <button id="deleteBtn"><span class="material-symbols-outlined">
        delete
        </span></button>
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

getMealsData().then((data) => {
  createMealsTable(data);

  const deleteBtn = document.querySelector("#deleteBtn");
  deleteBtn.addEventListener("click", function () {
    if (window.confirm("Do you really want to delete?")) {
      console.log(`Deletion simulation succeeded of meal with`);
      // find id
      // call delete api
      // gererateTable
      getMealsData().then((data) => {
        createMealsTable(data);
      });
    } else {
      console.log("Aborted");
    }
  });

  const editBtn = document.querySelector("#editBtn");
  editBtn.addEventListener("ckick", function () {});
});

// ADD MEW MEALS
async function addNewMeal() {
  try {
    const mealNameInput = document.querySelector("#mealName").value;
    const categoryInput = document.querySelector("#category").value;

    axios.post(
      "/api/meals",
      {
        mealName: mealNameInput,
        category: categoryInput,
      },
      {
        headers: { "Content-Type": "application/json" },
      }
    );

    mealNameInput = "";
    categoryInput = "";
  } catch (e) {
    console.log(e);
  }
}

const form = document.querySelector("#newMealForm");
// const mealNameInput = document.querySelector("#mealName");
// const categoryInput = document.querySelector("#category");
// const submitBtn = document.getElementById("submitNewMeal");

form.addEventListener("submit", function (e) {
  e.preventDefault();
  addNewMeal()
    .then(
      getMealsData().then((data) => {
        createMealsTable(data);
      })
    )
    .catch((err) => console.log(err));
});
