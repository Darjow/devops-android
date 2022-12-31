const Router = require("@koa/router");
const contractService = require("../service/contract");

const getContracts = async (ctx) => {
  ctx.body = await contractService.getAll();
}

const addContract = async (ctx) => {
    await contractService.addContract(ctx.request.body)
    
}


module.exports = (app) => {
  const router = new Router({prefix:"/contract"});

  router.get("/", getContracts);
  router.post("/", addContract)

  app.use(router.routes()).use(router.allowedMethods());
}