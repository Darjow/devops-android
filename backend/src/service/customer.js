const {tables, getKnex} = require("../data/index");

const getAll = async () => {
  return getKnex()(tables.project).select();
}

module.exports = {
  getAll,
}