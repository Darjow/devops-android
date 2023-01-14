const contractRepo = require('../repository/contract')

const getAll = async () => {
    console.log('Getting all contracts')
  const contracts = contractRepo.getAll();
  console.log('returning contracts:\n ' + contracts)
  return contracts
}

const addContract = async ({start, end}) => {
    console.log("adding contract with these parameters: " + start + ' ' + end)
    const id = await contractRepo.addContract(start,end);
    const contract = await getContractById(id)
    console.log("Contract is toegevoegd: ")
    return contract
}

 /*word eiglk ni gebruikt \_(^_^)_/ lmao */
const contractActiveren = async (id) => {
    try {
        await contractRepo.contractActiveren(id)
        return contractRepo.getContractById(id)
    } catch (error) {
        console.log(error)
    }
}

const getContractById = async(id) => {
    console.log("Grabbing contract with id:" + id);
    const contract = await contractRepo.getContractById(id)
    if(!contract[0]){
        return null;
    }
    console.log(JSON.stringify(contract[0]));
    return contract[0]
}

const updateContract = async(id, {contract}) => {
    await contractRepo.updateContract(id, contract);
    const cont = await contractRepo.getContractById(id)
    return cont
}

module.exports = {
  getAll,
  addContract,
  contractActiveren,
  getContractById,
  updateContract
}