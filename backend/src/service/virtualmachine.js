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
const createVm = async ({...vm}) => {
    const id = await repoVm.createVm(serializeVM(vm))
    const vmm =  await repoVm.getVirtualmachinesById(id)
    return outputVms(vmm[0])

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

function serializeVM(vm){
    let _connection = vm.connection;
    let _hardware = vm.hardware 
    let _backup = vm.backup
    
    console.log("VM REceived to serialize: !!!!! +" + JSON.stringify(vm));
    if(!vm.connection || !vm.fqdn){
        _connection = {
            fqdn : null,
            ipAdres : null,
            username : null,
            password : null
        }
    }
    if(!vm.hardware || !vm.hardware.memory){
        _hardware = {
            memory : null,
            storage : null,
            cpu : null,
        }
    }
    if(!vm.backup || !vm.backup.backup_type){
        _backup = {
            backup_type : null,
            date : null,
        }   
    }

    console.log();
    console.log();
    console.log();

    console.log(JSON.stringify(_backup));

    console.log();
    console.log();
    console.log();
    return {
        id: vm.id,
        name: vm.name,
        status: vm.status,
        operatingsystem: vm.operatingsystem,
        memory: _hardware.memory,
        storage: _hardware.storage,
        cpu: _hardware.cpu,
        mode: vm.mode,
        latest_backup: _backup.date,
        backup_type: _backup.backup_type,
        fqdn: _connection.fqdn,
        ipAdres: _connection.ipAdres,
        username: _connection.username,
        password: _connection.password,
        project_id: vm.projectId,
        contract_id: vm.contractId
        
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