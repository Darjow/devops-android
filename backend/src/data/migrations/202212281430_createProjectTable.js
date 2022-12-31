const { tables } = require('..');

module.exports = {
  up: async (knex) => {
    await knex.schema.createTable(tables.project, (table) => {
 
      table.increments('id').primary();
      table.string('name', 50).notNullable();
      table.integer('customer_id').notNullable()
    });
  },
  down: (knex) => {
    return knex.schema.dropTableIfExists(tables.project);
  },
};
