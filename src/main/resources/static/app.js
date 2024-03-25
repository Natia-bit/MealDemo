async function getMealsData() {
  try {
    const response = await axios.get("/api/meals");
    // console.log("The responce is: ", response.data);
    return response.data;
  } catch (error) {
    console.error(error);
  }
}

getMealsData().then((data) => {
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

  data.forEach((meal) => {
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

  const deleteBtn = document.querySelector("#deleteBtn");
  deleteBtn.addEventListener("click", function () {
    if (window.confirm("Do you really want to delete?")) {
      console.log("Deletion simulation succeeded");
      // call delete api
      // gererateTable
    } else {
      console.log("Aborted");
    }
  });
});
