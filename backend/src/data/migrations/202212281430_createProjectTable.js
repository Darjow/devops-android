const { tables } = require('..');

module.exports = {
  up: async (knex) => {
    await knex.schema.createTable(tables.project, (table) => {
 
      table.increments('id');
      table.string('name', 50).notNullable();
      table.integer("customer_id").notNullable();

      table.foreign("customer_id", "fk_project_customerid")
        .references(`${tables.customer}.id`)
        .onUpdate('CASCADE');
    });
  },
  down: (knex) => {
    return knex.schema.dropTableIfExists(tables.project);
  },
};
