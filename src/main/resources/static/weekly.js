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
    console.log(key);
    table += `
      <tr>
      <td class="name">${capitalizeFirstLetter(key)}</td>
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

async function getWeeklyPlan() {
  try {
    const response = await axios.post("/api/weekly-plan", {});
    console.log(response);
    return response.data;
  } catch (error) {
    console.error(error);
  }
}

getWeeklyPlan().then((data) => {
  return createWeeklyTable(data);
});

function capitalizeFirstLetter(string) {
  return string.charAt(0).toUpperCase() + string.slice(1).toLowerCase();
}
