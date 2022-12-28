const Router = require("@koa/router");
const customerService = require("../service/customer");

const getCustomers = async (ctx) => {
  ctx.body = await customerService.getAll();
}

const login = async (ctx) => {
    
}
const register = async(ctx) => {

}

module.exports = (app) => {
  const router = new Router({prefix:"/customer"});

  router.get("/", getCustomers);
  router.post("/", register);
  router.get("/login")

  app.use(router.routes()).use(router.allowedMethods());
}