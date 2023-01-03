const contractRepo = require('../repository/contract')

const getAll = async () => {
  const contracts = contractRepo.getAll();
  return {contracts}
}

const addContract = async ({start, end}) => {
    try {    
        const id = await contractRepo.addContract(start,end);
        return contractRepo.getContractById(id)
    } catch (error) {
        console.log(error)
    }
}

 /*word eiglk ni gebruikt \_(^_^)_/*/
const contractActiveren = async (id) => {
    try {
        await contractRepo.contractActiveren(id)
        return contractRepo.getContractById(id)
    } catch (error) {
        console.log(error)
    }
}

const getContractById = async(id) => {
    const contract = await contractRepo.getContractById(id)
    return {contract}
}

const updateContract = async(id, {contract}) => {
    await contractRepo.updateContract(id, contract);
    const cont = await contractRepo.getContractById(id)
    return {cont}
}

module.exports = {
  getAll,
  addContract,
  contractActiveren,
  getContractById,
  updateContract
}