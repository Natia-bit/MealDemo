function createMealsTable(mealData) {
  let table = '<table style="border-collapse: collapse;">';

  mealData.forEach((meal) => {
    table += `
    <tr>
    <td class="name">${meal.mealName}</td>
    <td class="category">${meal.category}</td>
    <td align="center">
      <button id="editBtn"
      data-state="edit"
      aria-label="edit button"
      aria-live="polite"
      onclick="updateThis(this);">
      <span id="edit" class="material-symbols-outlined"> edit </span>
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

// UPDATE MEAL
async function updateThis(el) {
  try {
    const row = el.parentNode.parentNode.rowIndex;
    getMealsData().then((data) => {
      const meal = data.at(row);
      const tableRow = el.parentNode.parentNode;
      const mealNameSelector = tableRow.querySelector(".name");

      let requestObj = {
        name: undefined,
        newItem: undefined,
      };

      let isEditing = false;

      if (el.nodeName !== "BUTTON") return;

      if (!isEditing) {
        requestObj.id = tableRow;
        requestObj.name = mealNameSelector.innerText.trim();
        requestObj.newItem = undefined;
      }

      if (el.dataset.state === "edit") {
        if (isEditing) {
          return;
        } else {
          isEditing = true;
        }

        // Move caret to end of string
        const range = document.createRange();
        const selection = document.getSelection();
        range.selectNodeContents(mealNameSelector);
        range.collapse(false);
        selection.removeAllRanges();
        selection.addRange(range);

        // make field editable
        mealNameSelector.classList.add("editable");
        mealNameSelector.contentEditable = true;
        mealNameSelector.focus();

        el.innerHTML = `<span class="material-symbols-outlined"> done </span>`;
        el.dataset.state = "save";
        el.ariaLabel = "save button";
      } else if (el.dataset.state === "save") {
        mealNameSelector.classList.remove("editable");
        mealNameSelector.contentEditable = false;

        const response = axios.put(
          `api/meals/${meal.id}`,
          {
            mealName: `${capitalizeFirstLetter(
              mealNameSelector.innerText.trim()
            )}`,
            category: meal.category,
          },
          {
            headers: { "Content-Type": "application/json" },
          }
        );

        el.innerHTML = `<span class="material-symbols-outlined"> edit </span>`;
        el.dataset.state = "edit";
        el.ariaLabel = "edit button";

        // Allow editing again
        isEditing = false;

        return response;
      } else {
        console.error("editSaveBtn has invalid data-state attribute");
      }
    });
  } catch (e) {
    console.error();
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
