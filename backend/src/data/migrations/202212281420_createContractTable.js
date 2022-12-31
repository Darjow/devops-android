const { tables } = require('..');

module.exports = {
  up: async (knex) => {
    await knex.schema.createTable(tables.contract, (table) => {

      table.increments('id').primary();
      table.date('start_date').notNullable();
      table.date('end_date').notNullable();
      table.boolean("geactiveerd").notNullable().defaultTo(false);
    });
  },
  down: (knex) => {
    return knex.schema.dropTableIfExists(tables.contract);
  },
};
