const {tables, getKnex} = require("../data/index");

const getAll = async () => {
  return getKnex()(tables.virtualmachine).select()
}
const getById = async (id) => {
    return getKnex()(tables.virtualmachine).select().where("id", id)
}
const createVm = async ({name_vm, status_vm, operatingsystem_vm, hardware, 
    projectId, VirtualMachineModus, contractId, backup}) => {

        console.table(vm_mode)
        
    const [id]= await getKnex()(tables.virtualmachine).insert({
        name : name_vm, 
        status : status_vm, 
        operatingsystem : operatingsystem_vm, 
        memory : hardware.memory,
        storage : hardware.storage,
        cpu : hardware.cpu,
        mode : VirtualMachineModus,
        backup_type : backup.backup_type,
        project_id : projectId, 
        contract_id: contractId
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
  getById,
  createVm,
  getVirtualmachinesByProjectId,
  getVirtualmachineByContractId,
  getVirtualmachinesById
}