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
      </button>

      <button id="doneBtn"
      type="text"
      value="done"
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
    console.log("clicked");
    el.querySelector("#edit").style.display = "none";
    // el.querySelector("#done").style.display = "block";
    const row = el.parentNode.parentNode.rowIndex;
    const meal = data.at(row - 1);

    console.log(el.parentNode.parentNode);
    const tableRow = el.parentNode.parentNode;
    const mealNameSelector = tableRow.querySelector(".name");
    console.log(mealNameSelector);
    console.log(mealNameSelector.innerHTML);

    mealNameSelector.innerHTML =
      "<input type='text' id='nameIn" +
      "' value='" +
      mealNameSelector.innerHTML +
      "'>";
  });
}

async function updateWithApi(el) {
  const row = el.parentNode.parentNode.rowIndex;
  const meal = data.at(row - 1);

  const inputValue = tableRow.querySelector("#nameIn").value;
  console.log(`name Val: ${inputValue}`);
  document.querySelector(".name").innerHTML = inputValue;
  console.log(inputValue).value;

  const response = await axios.put(
    `api/meals/${meal.id}`,
    {
      mealName: `${capitalizeFirstLetter(inputValue)}`,
      category: meal.category,
    },
    {
      headers: { "Content-Type": "application/json" },
    }
  );

  // document.querySelector("#done").style.display = "none";
  // document.querySelector(".edit").style.display = "block";

  return response;
}

async function updateMeal(el) {
  try {
    getMealsData().then((data) => updateWithApi(data));
    alert("Done");
  } catch (e) {
    console.log(e);
  }
}

function capitalizeFirstLetter(string) {
  return string.charAt(0).toUpperCase() + string.slice(1).toLowerCase();
}

getMealsData().then((data) => {
  createMealsTable(data);

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

// SEARCH
const trs = document.getElementById("dataTable").getElementsByTagName("tr");
document.getElementById("search").onkeyup = (e) => {
  for (const tr of trs) {
    tr.style.display = tr.innerText
      .toLowerCase()
      .includes(e.target.value.toLowerCase())
      ? ""
      : "none";
  }
};
