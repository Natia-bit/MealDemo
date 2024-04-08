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
      type="text"
      value="Edit"
      aria-hidden="true"
      onclick="editMeal(this);">
      <span id="edit" class="material-symbols-outlined"> edit </span>
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
    console.log("clicked");
    el.querySelector("#edit").style.display = "none";
    el.querySelector("#done").style.display = "block";
    const row = el.parentNode.parentNode.rowIndex;
    const meal = data.at(row - 1);
    console.log(meal.mealName);

    // const name = document.querySelector(".name");
    // console.log(meal);
    // const nameData = name.value;

    // name.innerHTML =
    //   "<input type='text' id='nameIn" + "' value='" + name + "'>";

    // const inputValue = document.querySelector("#nameIn").value;
    // console.log(`name Val: ${inputValue}`);
    // document.querySelector(".name").innerHTML = inputValue;

    // console.log(inputValue);

    // document.querySelector("#done").style.display = "none";
    // document.querySelector("#edit").style.display = "block";
  });
}

// async function updateMeal(el) {
//   getMealsData().then((data) => {
//     console.log("Upldate clicked");
//     const row = el.parentNode.parentNode.rowIndex;
//     const meal = data.at(row - 1);

//     const nameVal = document.querySelector("#nameIn").value;
//     console.log(`name Val: ${nameVal}`);
//     document.querySelector(".name").innerHTML = nameVal;

//     document.querySelector(".done").style.display = "none";
//     document.querySelector(".edit").style.display = "block";

//     alert("Done");
//   });
// }

function capitalizeFirstLetter(string) {
  return string.charAt(0).toUpperCase() + string.slice(1).toLowerCase();
}

getMealsData().then((data) => {
  createMealsTable(data);

  // EDIT
  // const editBtn = document.querySelector("#editBtn");
  // const editBtn = document.querySelector("#editBtn");
  // editBtn.addEventListener("click", function (e) {
  //   editMeal(editBtn);
  // });

  // const updateBtn = document.querySelector(".done");
  // updateBtn.addEventListener("click", function (e) {
  //   updateMeal(updateBtn);
  // });

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
