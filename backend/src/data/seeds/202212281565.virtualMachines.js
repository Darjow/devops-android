const {tables} = require("..");

module.exports = {
    seed: async(knex) => {
        await knex(tables.virtualmachine).insert(
          [
            {
                id: 1,
                name: "Fire Frontend",
                fqdn: "MOC2-FQDN", 
                ipAdres: "25.236.117.11", 
                username:"admin", 
                password:"DW2]]YmiPrvz34-dh5]g",                    
                status: "RUNNING",
                operatingsystem: "WINDOWS_2016",
                cpu : 4,
                memory: 4000,
                storage: 50000,
                mode: "IAAS",
                backup_type:"DAGELIJKS",
                latest_backup: new Date(),
                project_id: 1,
                contract_id: 2
            },
            {
                id: 2,
                name: "Backend 1",
                fqdn: "MOC2-FQDN", 
                ipAdres: "25.236.117.12", 
                username:"admin", 
                password:"DW2]]YmiPrvz34-dh5]g",                    
                status: "RUNNING",
                operatingsystem: "LINUX_KALI",
                cpu : 2,
                memory: 4000,
                storage: 50000,
                mode: "PAAS",
                backup_type:"MAANDELIJKS",
                latest_backup: new Date(),
                project_id: 1,
                contract_id: 2
            }, {
                id: 3,
                name: "Webscraper",
                fqdn: "MOC1-FQDN", 
                ipAdres: "85.226.17.113", 
                username:"admin", 
                password:"DW2]]YmiPrvz34-dh5]g",                    
                status: "TERMINATED",
                operatingsystem: "LINUX_UBUNTU",
                cpu : 1,
                memory: 2000,
                storage: 5000,
                mode: "PAAS",
                backup_type:"MAANDELIJKS",
                latest_backup: new Date(),
                project_id: 2,
                contract_id: 3
            }
        ]
    )
}
}