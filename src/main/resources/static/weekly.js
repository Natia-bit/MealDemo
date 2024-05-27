function createWeeklyTable(data) {
  let table = '<table style="border-collapse: collapse;">';

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
    const response = await axios.get("/api/meal-random");
    return response.data;
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

    // check for empty input
    if (chickenInput.value == "") {
      chickenInput.value = 0;
    }
    if (meatInput.value == "") {
      meatInput.value = 0;
    }
    if (fishInput.value == "") {
      fishInput.value = 0;
    }
    if (vegetarianInput.value == "") {
      vegetarianInput.value = 0;
    }

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

    return response.data;
  } catch (e) {
    console.log(e);
  }
}

function findTotal() {
  const total = document.getElementById("totalDays");
  const chickenValue = document.querySelector("#chicken").value;
  const meatValue = document.querySelector("#meat").value;
  const fishValue = document.querySelector("#fish").value;
  const vegetarianValue = document.querySelector("#vegetarian").value;
  let sum = 0;

  const inputChicken = parseInt(chickenValue) || 0;
  const inputMeat = parseInt(meatValue) || 0;
  const inputFish = parseInt(fishValue) || 0;
  const inputVeg = parseInt(vegetarianValue) || 0;

  sum = inputChicken + inputMeat + inputFish + inputVeg;

  total.innerHTML = sum;

  if (sum < 7) {
    total.style.color = "orange";
  } else if (sum > 7) {
    total.style.color = "red";
  } else {
    total.style.color = "green";
  }
}
const daysForm = document.getElementById("weeklyfreq");
daysForm.addEventListener("input", findTotal);

const form = document.querySelector("#typePerWeek");
const btn = document.querySelector("#submitBtn");
form.addEventListener("submit", function (e) {
  e.preventDefault();
  addTypesPerWeek().then((data) => {
    return createWeeklyTable(data);
  });
});
