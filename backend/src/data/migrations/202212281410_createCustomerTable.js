const {tables} = require('..')

module.exports = {
    up: async (knex) => {
        await knex.schema.createTable(tables.customer, (table) => {
            table.integer('id').primary();
            table.string('firstname',40).notNullable();
            table.string('lastname',40).notNullable();
            table.string('email', 40).notNullable();
            table.string('password', 40).notNullable();
            table.string('phonenumber',40).notNullable();
            table.string('bedrijf_opleiding',40).nullable();

            table.string("contact1_phone", 20).nullable()
            table.string("contact1_email", 40).nullable()
            table.string("contact1_firstname", 20).nullable()
            table.string("contact1_lastname", 20).nullable()
           
            table.string("contact2_phone", 20).nullable()
            table.string("contact2_email", 40).nullable()
            table.string("contact2_firstname", 20).nullable()
            table.string("contact2_lastname", 20).nullable()

        })
    },
    down: (knex) => {
        return knex.schema.dropTableIfExists(tables.customer);
    }
}