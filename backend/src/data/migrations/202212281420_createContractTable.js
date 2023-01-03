const { tables } = require('..');

module.exports = {
  up: async (knex) => {
    await knex.schema.createTable(tables.contract, (table) => {

      table.integer('id').primary();
      table.date('startDate').notNullable();
      table.date('endDate').notNullable();
      table.boolean("active").notNullable().defaultTo(false);
    });
  },
  down: (knex) => {
    return knex.schema.dropTableIfExists(tables.contract);
  },
};
