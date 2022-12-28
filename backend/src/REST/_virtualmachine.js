const Router = require("@koa/router");
const wmService = require("../service/virtualmachine");


const createVirtualmachine = async (ctx) => {
  const newVM= await wmService.createVm(ctx.request.body);
  ctx.body = newVM;
  ctx.status = 201;
}


const getVirtualmachines = async (ctx) => {
  ctx.body = await wmService.getAll();
}

const getVirtualmachineByContractId= async (ctx) => {
    ctx.body = await wmService.getVirtualmachineByContractId(ctx.request.body)
}

const getVirtualmachineByProjectId= async (ctx) => {
    ctx.body = await wmService.getVirtualmachinesByProjectId(ctx.request.body)
}

module.exports = (app) => {
  const router = new Router({prefix:"/vm"});

    router.get("/", getVirtualmachines);
    router.post("/", createVirtualmachine);
    router.get("/contract/:id", getVirtualmachineByContractId)
    router.get("/project/:id", getVirtualmachineByProjectId)

  app.use(router.routes()).use(router.allowedMethods());
}