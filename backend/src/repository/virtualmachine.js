const {tables, getKnex} = require("../data/index");

const getAll = async () => {
  return getKnex()(tables.virtualmachine).select()
}
const getById = async (id) => {
    return getKnex()(tables.virtualmachine).select().where("id", id)
}
const createVm = async (vm) => {
    console.log("Creating VM with parameters: " + JSON.stringify(vm));
    const [id]= await getKnex()(tables.virtualmachine).insert({
        name: vm.name,
        status: vm.status,
        memory: vm.memory,
        storage: vm.storage,
        cpu: vm.cpu,
        operatingsystem : vm.operatingsystem,
        mode : vm.mode,
        backup_type : vm.backup_type,
        project_id : vm.project_id,
        contract_id : vm.contract_id
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