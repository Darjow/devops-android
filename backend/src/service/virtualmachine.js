const repoVm = require('../repository/virtualmachine')

const getAll = async () => {
    const vms = await getKnex()(tables.virtualmachine).select()
    console.log('returing all vms\n')
    console.log(JSON.stringify(outputVms(vms)));
    return outputVms(vms)
}

const getById = async (id) =>{
    console.log("Getting VM with id: " + id);
    const vm = await repoVm.getById(id);
    if(!vm[0]){
        console.log("Geen vm gevonden met id: " + id);
        return null;
    }
    console.log('received vm service: \n')
    console.log(JSON.stringify(vm));

    return outputVms(vm[0]);


}
//deze eeft geen parameter
const createVm = async ({...vm}) => {
  console.log("Creating new vm : ",vm);
    const id = await repoVm.createVm(vm)
    const vmm =  await repoVm.getVirtualmachinesById(id)
    return outputVms(vm)

}

const getVirtualmachinesByProjectId = async (id) => {
    console.log("getting vm with projectid: " + id)
    const vm = await repoVm.getVirtualmachinesByProjectId(id);
    if(!vm[0]){
        console.log("geen vm gevonden")
        return null
        
    }
    console.log("returning vm:");
    console.log(JSON.stringify(outputVms(vm)));
    return outputVms(vm)
}

const getVirtualmachineByContractId = async ({id})=> {
    console.log("getting vm with contractID: " + id)
    const vm = await repoVm.getVirtualmachineByContractId(id);
    if(!vm[0]){
        console.log("geen vm gevonden")
        return null
    }
    console.log("returning vm:");
    console.log(JSON.stringify(outputVms(vm)));
    return outputVms(vm)
}

const getVirtualmachinesById= async ({id}) => {
    console.log("getting vm with ID: " + id)
    const vm = await repoVm.getVirtualmachinesById(id);
    if(!vm[0]){
        console.log("geen vm gevonden")
        return null
    }
    console.log("returning vm:");
    console.log(JSON.stringify(outputVms(vm)));
    return outputVms(vm)
}
  


function outputVms(vms){
    let output = []

    if(Array.isArray(vms)){
        vms.forEach(e => output.push(OUTVM(e)))
    }else{
        output = OUTVM(vms)
    }

    return output;




}
  //doordat we snel een db hebben gemaakt en deze niet beoordeeld werdhebben we niet echt rekening gehouden met foreign keys
  //uiteindelijk hebben we beter een mongoDB gebruikt of firebase. 
function OUTVM(vm){
    return  {
        name: vm.name,
        connection: {
            fqdn: vm.fqdn,
            ipAdres: vm.ipAdres,
            username: vm.username,
            password: vm.password
        },
        status: vm.status,
        operatingsystem: vm.operatingsystem,
        hardware: {
            memory : vm.memory,
            storage: vm.storage,
            cpu: vm.cpu

        },
        projectId: vm.project_id,
        mode: vm.mode,
        contractId: vm.contract_id,
        backup: {
            latest_backup: vm.latest_backup,
            backup_type: vm.backup_type
        },
        id: vm.id,
    }
}

module.exports = {
    getAll,
    createVm,
    getVirtualmachinesByProjectId,
    getVirtualmachineByContractId,
    getVirtualmachinesById,
    getById
  }