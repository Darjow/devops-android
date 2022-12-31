const Router = require("@koa/router");
const projectService = require("../service/project");

const getProjects = async (ctx) => {
  ctx.body = await projectService.getAll();
}

const getProjectById = async(ctx) => {
  ctx.body = await projectService.getProjectById(ctx.params.id)
}

const getProjectByCustomerId = async(ctx) => {
  ctx.body = await projectService.getProjectByCustomerId(ctx.params.cust_id)
}

module.exports = (app) => {
  const router = new Router({prefix:"/project"});

  router.get("/", getProjects);
  router.get("/:id", getProjectById);
  router.get("/:cust_id", getProjectByCustomerId)

  app.use(router.routes()).use(router.allowedMethods());
}