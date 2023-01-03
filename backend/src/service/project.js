const repoProject = require('../repository/project')

const getAll = async () => {
  const projects = await repoProject.getAll();
  return projects
}

const getProjectById = async (id) => {
   const project = await repoProject.getProjectById(id);
   if(!project){
    return null;
  }
  return project
}

const getByCustomerId = async (cust_id) => {
  const project = await repoProject.getByCustomerId(cust_id);
   if(!project){
    return null;
  }
  return project
}

module.exports = {
  getAll,
  getProjectById,
  getByCustomerId
}