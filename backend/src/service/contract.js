const {tables, getKnex} = require("../data/index");

const getAll = async () => {
  return getKnex()(tables.contract).select();
}

const addContract = async ({start, end}) => {
    try {    
        const [id] = await getKnex()(tables.contract).insert({
        start_date : start,
        end_date: end})  
        return id
    } catch (error) {
        console.log(error)
    }

}

const contractActiveren = async ({id}) => {
    try {
        await getKnex()(tables.contract).insert({
            geactiveerd : 1
        }).where("id", id)
    } catch (error) {
        console.log(error)
    }
}

const getContractById = async(id) => {
    return getKnex()(tables.contract).select().where("id", id)
}

module.exports = {
  getAll,
  addContract,
  contractActiveren,
  getContractById
}