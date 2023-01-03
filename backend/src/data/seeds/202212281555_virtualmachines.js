const {tables} = require("..");

module.exports = {
    seed: async(knex) => {
        await knex(tables.virtualmachine).insert(
            [
                {
                    id: 1,
                    name: "Willie's VM",
                    fqdn: "MOC2-FQDN", 
                    ipAdres: "25.236.117.11", 
                    username:"MOC-USER1", 
                    password:"DW2]]YmiPrvz34-dh5]g",                    
                    status: "Running",
                    operatingsystem: "Linux Kali",
                    cpu : 2,
                    memory: 4000,
                    storage: 50000,
                    mode: "SAAS",
                    backup_type:"MAANDELIJKS",
                    latest_backup: new Date(),
                    project_id: 1,
                    contract_id: 1
                },
            ]
        )
    }
}