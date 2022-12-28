const {tables} = require("..");

module.exports = {
    seed: async(knex) => {
        await knex(tables.contract).insert(
            [
                {
                    id: 1,
                    start_date: new Date(2022, 10,10),
                    end_date: new Date(2023, 12,20),
                },
                {
                    id: 2,
                    start_date: new Date(2022, 10,10),
                    end_date: new Date(2023, 6,20),
                }
            ]
        )
    }
}