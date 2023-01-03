const repoVm = require('../repository/virtualmachine')

const getAll = async () => {
    const vms = await getKnex()(tables.virtualmachine).select()
    console.log('returing all vms\n')
    console.log(vms)
    return vms
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
    console.log("getting vm with projectid: " + id)
    const vm = await repoVm.getVirtualmachinesByProjectId(id);
    if(!vm){
        console.log("geen vm gevonden")
        return null
        
    }
    console.log("returning vm: \n"+vm)
    return vm
}

const getVirtualmachineByContractId = async ({id})=> {
    console.log("getting vm with contractID: " + id)
    const vm = await repoVm.getVirtualmachineByContractId(id);
    if(!vm){
        console.log("geen vm gevonden")
        return null
    }
    console.log("returning vm: \n"+vm)
    return vm
}

const getVirtualmachinesById= async ({id}) => {
    console.log("getting vm with ID: " + id)
    const vm = await repoVm.getVirtualmachinesById(id);
    if(!vm){
        console.log("geen vm gevonden")
        return null
    }
    console.log("returning vm: "+vm)
    return vm
}
  

module.exports = {
  getAll,
  createVm,
  getVirtualmachinesByProjectId,
  getVirtualmachineByContractId,
  getVirtualmachinesById
}