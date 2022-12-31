const {tables, getKnex} = require("../data/index");

const getAll = async () => {
  return getKnex()(tables.virtualmachine).select()
}

const createVm = async (name_vm, connection_vm, status_vm, hardware_vm, 
    operatingsystem_vm, mode_vm, backup_type_vm, latest_backup_vm, project_id_vm, contract_id_vm) => {
  console.log(
            "New vm: " + 
                name_vm, connection_vm, status_vm, hardware_vm, 
                operatingsystem_vm, mode_vm, backup_type_vm, 
                latest_backup_vm, project_id_vm, contract_id_vm
            );
    const [id]= await getKnex()(tables.virtualmachine).insert({
        name: name_vm,
        connection: connection_vm,
        status: status_vm,
        hardware: hardware_vm,
        operatingsystem : operatingsystem_vm,
        mode : mode_vm,
        backup_type : backup_type_vm,
        latest_backup : latest_backup_vm,
        project_id : project_id_vm,
        contract_id : contract_id_vm
    });
    return id
}

const getVirtualmachinesByProjectId = async (id) => {
    try {
        return getKnex()(tables.virtualmachine).select().where("project_id", id)
    } catch (error) {
        console.log(error)
    }
}

const getVirtualmachineByContractId = async (id)=> {
    return getKnex()(tables.virtualmachine).select().where("contract_id", id)
}

const getVirtualmachinesById= async (id) => {
    return getKnex()(tables.virtualmachine).select().where("id", id)
}
  

module.exports = {
  getAll,
  createVm,
  getVirtualmachinesByProjectId,
  getVirtualmachineByContractId,
  getVirtualmachinesById
}