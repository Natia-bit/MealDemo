const homePage = async () => {
  try {
    const res = await axios.get("localhost:8080/home/meals");
    return res.data;
  } catch (e) {
    return "Oh oh.... somethings is not right...";
  }
};
