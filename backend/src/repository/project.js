const {tables, getKnex} = require("../data/index");

const getAll = async () => {
  return getKnex()(tables.project).select();
}


const getProjectById = async (id) => {
   const project= getKnex()(tables.project).select().where("id", id)

   return project
}

const getByCustomerId = async (cust_id) => {
  const project= getKnex()(tables.project).select().where("customer_id", cust_id)
    if(!project){
      return "Customer heeft geen projecten"
    }
   return project
}

const addProject = async(project) => {
  const [id] = await getKnex()(tables.project).insert({
    name : project.nameProject,
    customer_id : project.cust_Id
  })
  return id
}

module.exports = {
  getAll,
  getProjectById,
  getByCustomerId,
  addProject
}