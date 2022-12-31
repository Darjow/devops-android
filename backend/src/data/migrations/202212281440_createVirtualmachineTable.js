const { tables } = require('..');

module.exports = {
  up: async (knex) => {
    await knex.schema.createTable(tables.virtualmachine, (table) => {

      table.increments('id')
        .primary();

      table.string('name', 255).notNullable(); 
      table.string('connection').notNullable();
      table.string('status',40).notNullable();
      table.json('hardware').notNullable();
      table.string('operatingsystem').notNullable();
      table.string('mode', 10).notNullable();

      table.date('latest_backup').notNullable();
      table.string('backup_type').notNullable();

      table.integer('project_id').notNullable();
      table.integer('contract_id').notNullable();

      /*foreign keys*/
      /*
        .references('id').inTable('projects')
      
        .references('id').inTable('contracts');*/
    });
  },
  down: (knex) => {
    return knex.schema.dropTableIfExists(tables.virtualmachine);
  },
};
