const {tables, getKnex} = require("../data/index");

const getAll = async () => {
  return getKnex()(tables.contract).select();
}

const addContract = async (start, end) => {
    try {    
        [id] = await getKnex()(tables.contract).insert({
        startDate : start,
        endDate: end})
        return id
    } catch (error) {
        console.log(error)
    }
}

const contractActiveren = async (id) => {
    const contract = await getKnex()(tables.contract).insert({geactiveerd : 1})
                        .where("id", id)
    return contract
}

const getContractById = async(id) => {
    return getKnex()(tables.contract).select().where("id", id)
}

//Moet miss nog aangepast worden hang af van api
const updateContract = async(id, contract) => {
     await getKnex()(tables.contract).update({
        start_date : contract.startdate,
        end_date : contract.enddate
     }).where("id", id)
     return getContractById(id)
}

module.exports = {
  getAll,
  addContract,
  contractActiveren,
  getContractById,
  updateContract
}