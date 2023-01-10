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

const getVirtualmachinesByProjectId = async (id) => {
    console.log("getting vm with projectid: " + id)
    const vm = await repoVm.getVirtualmachinesByProjectId(id);
    if(!vm){
        console.log("geen vm gevonden")
        return null
        
    }
    console.log("returning vm:");
    console.log(outputVms(vm));
    return outputVms(vm)
}

const getVirtualmachineByContractId = async ({id})=> {
    console.log("getting vm with contractID: " + id)
    const vm = await repoVm.getVirtualmachineByContractId(id);
    if(!vm){
        console.log("geen vm gevonden")
        return null
    }
    console.log("returning vm: \n"+ outputVms(vm))
    return outputVms(vm)
}

const getVirtualmachinesById= async ({id}) => {
    console.log("getting vm with ID: " + id)
    const vm = await repoVm.getVirtualmachinesById(id);
    if(!vm){
        console.log("geen vm gevonden")
        return null
    }
    console.log("returning vm: "+outputVms(vm))
    return outputVms(vm)
}
  

function outputVms(vms){
    let output = []

    for(let vm of vms){
        let hw = {
            memory : vm.memory,
            storage: vm.storage,
            cpu: vm.cpu
        }
        let _connection = {
            fqdn: vm.fqdn,
            ipAdres: vm.ipAdres,
            username: vm.username,
            password: vm.password
        }
        let _backup = {
            latest_backup: vm.latest_backup,
            backup_type: vm.backup_type
        }

        output.push(
           {
            name: vm.name,
            connection: _connection,
            status: vm.status,
            operatingsystem: vm.operatingsystem,
            hardware: hw,
            projectId: vm.project_id,
            mode: vm.mode,
            contractId: vm.contract_id,
            backup: _backup,
            id: vm.id,
           } 
        )
    }

    return output;




}

module.exports = {
    getAll,
    createVm,
    getVirtualmachinesByProjectId,
    getVirtualmachineByContractId,
    getVirtualmachinesById
  }