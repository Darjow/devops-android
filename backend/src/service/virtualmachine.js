const repoVm = require('../repository/virtualmachine')

const getAll = async () => {
    const vms = await getKnex()(tables.virtualmachine).select()
    return {vms}
}

const createVm = async ({name_vm, connection_vm, status_vm, hardware_vm, 
    operatingsystem_vm, mode_vm, backup_type_vm, latest_backup_vm, project_id_vm, contract_id_vm}) => {
  console.log(
            "New vm: " + 
                name_vm, connection_vm, status_vm, hardware_vm, 
                operatingsystem_vm, mode_vm, backup_type_vm, 
                latest_backup_vm, project_id_vm, contract_id_vm
            );
    const id = await repoVm.createVm(name_vm, connection_vm, status_vm, hardware_vm, 
    operatingsystem_vm, mode_vm, backup_type_vm, latest_backup_vm, project_id_vm, contract_id_vm)
    return repoVm.getVirtualmachinesById(id)

}

const getVirtualmachinesByProjectId = async ({id}) => {
    const vm = await repoVm.getVirtualmachinesByProjectId(id);
    if(!vm){
        return "geen vm gevonden met dat projectId"
    }
    return {vm}
}

const getVirtualmachineByContractId = async ({id})=> {
    const vm = await repoVm.getVirtualmachineByContractId(id);
    if(!vm){
        return "geen vm gevonden met dat contractid"
    }
    return {vm}
}

const getVirtualmachinesById= async ({id}) => {
    const vm = await repoVm.getVirtualmachinesById(id);
    if(!vm){
        return "geen vm gevonden met dat id"
    }
    return {vm}
}
  

module.exports = {
  getAll,
  createVm,
  getVirtualmachinesByProjectId,
  getVirtualmachineByContractId,
  getVirtualmachinesById
}