const {tables} = require('..')

module.exports = {
    up: async (knex) => {
        await knex.schema.createTable(tables.customer, (table) => {
            table.increments('id').primary();
            table.string('firstname',40).notNullable();
            table.string('lastname',40).notNullable();
            table.string('email', 40).notNullable();
            table.string('password', 40).notNullable();
            table.string('phonenumber',40).notNullable();
            table.string('bedrijf',40);
            table.string('opleiding',40);

            /*conactpersoon 1*/
            table.json('contactpersoon1');

             /*conactpersoon 2*/
            table.json('contactpersoon2');
        })
    },
    down: (knex) => {
        return knex.schema.dropTableIfExists(tables.customer);
    }
}