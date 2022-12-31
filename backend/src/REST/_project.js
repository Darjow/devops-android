const Router = require("@koa/router");
const projectService = require("../service/project");

const getProjects = async (ctx) => {
  ctx.body = await projectService.getAll();
}

module.exports = (app) => {
  const router = new Router({prefix:"/project"});

  router.get("/", getProjects);

  app.use(router.routes()).use(router.allowedMethods());
}