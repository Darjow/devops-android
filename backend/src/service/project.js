const repoProject = require('../repository/project')

const getAll = async () => {
  console.log('Getting all projects')
  const projects = await repoProject.getAll();
  console.log('returning projects:\n ' + projects)
  return projects
}

const getProjectById = async (id) => {
  console.log('Getting project with ID: '+ id)
   const project = await repoProject.getProjectById(id);
   if(!project){
    console.log("no project found")
    return null;
  }
  console.log("returning project:\n" + project)
  return project
}

const getByCustomerId = async (id) => {
  console.log("Projects request received with customer id: " + id);

  const project = await repoProject.getByCustomerId(id);
   if(!project){
    console.log("no project found")
    return null;
  }
  console.log("returning project:\n")
  console.log(JSON.stringify(project) , new Date())
  return project
}

const addProject = async (project) => {
  console.log("making project with",project)

  const id = await repoProject.addProject(project);
  const proj= await repoProject.getProjectById(id);
  console.log("Project toegevoegd")
  console.log("returing" , proj)
  return proj[0]
}

module.exports = {
  getAll,
  getProjectById,
  getByCustomerId,
  addProject
}