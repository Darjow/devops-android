const { tables } = require('..');

module.exports = {
  up: async (knex) => {
    await knex.schema.createTable(tables.contract, (table) => {

      table.increments('id').primary();
      table.date('startDate').notNullable();
      table.date('endDate').notNullable();
      table.integer("active").notNullable().defaultTo(0);
    });
  },
  down: (knex) => {
    return knex.schema.dropTableIfExists(tables.contract);
  },
};
