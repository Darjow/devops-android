const Router = require("@koa/router");
const projectService = require("../service/project");

const getProjects = async (ctx) => {
  ctx.body = await projectService.getAll();
}

const getProjectById = async(ctx) => {
  ctx.body = await projectService.getProjectById(ctx.params.id)
}

const getProjectByCustomerId = async(ctx) => {
  ctx.body = await projectService.getByCustomerId(ctx.params.id)
}

const addProject = async(ctx) => {
  ctx.body = await projectService.addProject(ctx.request.body)
}

module.exports = (app) => {
  const router = new Router({prefix:"/project"});

  router.get("/", getProjects);
  router.get("/:id", getProjectById);
  router.get("/customer/:id", getProjectByCustomerId)
  router.post("/", addProject)

  app.use(router.routes()).use(router.allowedMethods());
}