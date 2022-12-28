const {tables} = require("..");

module.exports = {
    seed: async(knex) => {
        await knex(tables.customer).insert(
            [
                {
                   id: 1,
                    firstname: "John",
                    lastname: "Doe",
                    phonenumber: "0497815223",
                    email : "john.doe@hotmail.com",
                    password: "Password#69",
                    bedrijf: "De la where",
                    contactpersoon1: {
                        phone: '0474619443',
                        email: 'contactpersoon@hotmail.com',
                        firstname: 'Contact',
                        lastname: 'Persoon'
                      }
                },
            ]
        )
    }
}