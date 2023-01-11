const Router = require("@koa/router");
const vmService = require("../service/virtualmachine");


const createVirtualmachine = async (ctx) => {
  const newVM= await vmService.createVm(ctx.request.body);
  ctx.body = newVM;
  ctx.status = 201;
}

const getVirtualMachineById = async (ctx) => {
  ctx.body = await vmService.getById(ctx.params.id);
}


const getVirtualmachines = async (ctx) => {
  ctx.body = await vmService.getAll();
}

const getVirtualmachineByContractId= async (ctx) => {
    ctx.body = await vmService.getVirtualmachineByContractId(ctx.request.body)
}

const getVirtualmachineByProjectId= async (ctx) => {
    ctx.body = await vmService.getVirtualmachinesByProjectId(ctx.params.id)
}

module.exports = (app) => {
  const router = new Router({prefix:"/vm"});

    router.get("/", getVirtualmachines);
    router.post("/", createVirtualmachine);
    router.get("/:id", getVirtualMachineById)
    router.get("/contract/:id", getVirtualmachineByContractId)
    router.get("/project/:id", getVirtualmachineByProjectId)

  app.use(router.routes()).use(router.allowedMethods());
}