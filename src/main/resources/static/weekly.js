function createWeeklyTable(data) {
  let table = '<table style="border-collapse: collapse;">';

  table += `
    <thead>
      <tr>
        <th>Day</th>
        <th>Name</th>
        <th>Category</th>
      </tr>
    </thead>
    <tbody>
  `;

  const sorter = {
    monday: 1,
    tuesday: 2,
    wednesday: 3,
    thursday: 4,
    friday: 5,
    saturday: 6,
    sunday: 7,
  };

  let tmp = [];
  Object.keys(data).forEach(function (key) {
    let value = data[key];
    let index = sorter[key.toLowerCase()];
    tmp[index] = {
      key: key,
      value: value,
    };
  });

  let orderedData = {};
  tmp.forEach(function (obj) {
    orderedData[obj.key] = obj.value;
  });

  Object.keys(orderedData).forEach((key) => {
    const value = data[key];
    table += `
      <tr>
      <td class="name">${capitalizeFirstLetter(key).bold()}</td>
      <td class="name">${value.mealName}</td>
      <td class="category">${value.category}</td>
    </tr>
    `;
  });

  table += `
    </tbody>
    </table>
  `;

  const weeklyTable = document.querySelector("#weekyPlan");
  weeklyTable.innerHTML = table;
}

// MODAL

async function getRandomMeal() {
  try {
    const responce = await axios.get("/api/meal-random");
    return responce.data;
  } catch (e) {
    console.log(e);
  }
}
const modal = document.getElementById("myModal");
const randomMeal = document.getElementById("randomMeal");

async function openModal() {
  modal.classList.add("show");
  getRandomMeal().then((data) => {
    randomMeal.innerHTML = `<span style="color:red">[</span> ${data.category.bold()} <span style="color:red">]</span> ${
      data.mealName
    }`;
  });
}
function closeModal() {
  modal.classList.remove("show");
}
window.onclick = function (event) {
  if (event.target == modal) {
    modal.classList.remove("show");
  }
};

document.addEventListener("keyup", function (e) {
  if (e.key === "Escape") {
    closeModal();
  }
});

async function getWeeklyPlan() {
  try {
    const response = await axios.post("/api/weekly-plan", {});
    return response.data;
  } catch (error) {
    console.error(error);
  }
}

async function reloadWeeklyTable() {
  getWeeklyPlan().then((data) => {
    return createWeeklyTable(data);
  });
}

getWeeklyPlan().then((data) => {
  return createWeeklyTable(data);
});

function capitalizeFirstLetter(string) {
  return string.charAt(0).toUpperCase() + string.slice(1).toLowerCase();
}

async function addTypesPerWeek() {
  try {
    const chickenInput = document.querySelector("#chicken");
    const meatInput = document.querySelector("#meat");
    const fishInput = document.querySelector("#fish");
    const vegetarianInput = document.querySelector("#vegetarian");

    const response = await axios.post(
      "/api/weekly-plan",
      {
        Chicken: chickenInput.value,
        Fish: fishInput.value,
        Meat: meatInput.value,
        Vegetarian: vegetarianInput.value,
      },
      {
        headers: { "Content-Type": "application/json" },
      }
    );

    // chickenInput.value = "";
    // meatInput.value = "";
    // fishInput.value = "";
    // vegetarianInput.value = "";
    console.log(`Input value for chicken: ${chickenInput.value}`);
    console.log(`Input value for fish: ${fishInput.value}`);
    console.log(`Input value for meat: ${meatInput.value}`);
    console.log(`Input value for veg: ${vegetarianInput.value}`);
    console.log("done");
    console.log(response.data);
    return response.data;
  } catch (e) {
    console.log(e);
  }
}

const form = document.querySelector("#typePerWeek");
const btn = document.querySelector("#submitBtn");
form.addEventListener("submit", function (e) {
  e.preventDefault();
  addTypesPerWeek().then((data) => {
    return createWeeklyTable(data);
  });
});
