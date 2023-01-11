const { tables } = require('..');

module.exports = {
  up: async (knex) => {
    await knex.schema.createTable(tables.virtualmachine, (table) => {

      table.increments('id').primary();

      table.string('name', 255).notNullable(); 
      table.string('status',40).notNullable();
      table.string('operatingsystem').notNullable();
      table.integer("memory", 10).notNullable()
      table.integer("storage", 10).notNullable()
      table.integer("cpu", 10).notNullable()
      table.string('mode', 10).notNullable();

      table.date('latest_backup');
      table.string('backup_type').notNullable();
      table.string("fqdn", 30).nullable()
      table.string("ipAdres", 20).nullable()
      table.string("username", 30).nullable()
      table.string("password", 50).nullable()

      table.integer('project_id').notNullable();
      table.integer('contract_id').notNullable();

      table.foreign('project_id').references('project.id')
      .onDelete("SET NULL")
      .onUpdate("CASCADE")

      

    });
  },
  down: (knex) => {
    return knex.schema.dropTableIfExists(tables.virtualmachine);
  },
};
