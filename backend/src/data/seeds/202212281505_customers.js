const {tables} = require("..");

module.exports = {
    seed: async(knex) => {
        await knex(tables.customer).insert(
            [
                {
                    id: 1,
                    firstname: "John",
                    lastname: "Doe",
                    email : "john.doe@hotmail.com",
                    password: "Password#69",
                    phonenumber: "0497815223",
                    bedrijf_opleiding: "De la where",
                    contact1_phone: "0497815773",
                    contact1_email: "contact1@hotmail.com",
                    contact1_firstname: "Some",
                    contact1_lastname: "One",
                    contact2_phone: null,
                    contact2_email: null,
                    contact2_firstname: null,
                    contact2_lastname: null
                },
            ]
        )
    }
}