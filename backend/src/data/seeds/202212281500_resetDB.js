const { tables } = require('..');

module.exports = {
  seed: async (knex) => {
    await knex(tables.contract).delete();
    await knex(tables.customer).delete();
    await knex(tables.project).delete();
    await knex(tables.virtualmachine).delete();
  },
};