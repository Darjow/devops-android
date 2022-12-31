const {tables} = require("..");

module.exports = {
    seed: async(knex) => {
        await knex(tables.project).insert(
            [
                {
                   id: 1, 
                   name: "Project A", 
                   customer_id: 1
                },
                 {
                   id: 2, 
                   name: "Project B", 
                   customer_id: 1
                },
                 {
                   id: 3, 
                   name: "Project A", 
                   customer_id: 2
                },
                 {
                   id: 4, 
                   name: "Project D", 
                   customer_id: 3
                },
            ]
        )
    }
}