const {tables} = require("..");

module.exports = {
    seed: async(knex) => {
        await knex(tables.contract).insert(
            [
                {
                    id: 1,
                    startDate: new Date(2022, 10,10),
                    endDate: new Date(2023, 12,20),
                    active: 1
                },
                {
                    id: 2,
                    startDate: new Date(2022, 10,10),
                    endDate: new Date(2023, 6,20),
                    active: 1
                },
                {
                    id: 3,
                    startDate: new Date(2022, 1,12),
                    endDate: new Date(2023, 5,20),
                    active: 1
                }
            ]
        )
    }
}