const Router = require("@koa/router");
const contractService = require("../service/contract");

const getContracts = async (ctx) => {
  ctx.body = await contractService.getAll();
}

const addContract = async (ctx) => {
  contract = await contractService.addContract({
    start : ctx.request.body.start,
    end : ctx.request.body.end
  })
  console.log("--- return contract : " + JSON.stringify(contract) );
  ctx.body = contract
    
}

const updateContract = async (ctx) => {
  const contract = await contractService.updateContract(ctx.params.id,
  {
    contract : ctx.request.body
  })
  return contract
}

const getContractById = async (ctx) => {
 ctx.body = await contractService.getContractById(ctx.params.id)
}


module.exports = (app) => {
  const router = new Router({prefix:"/contract"});

  router.get("/", getContracts);
  router.post("/", addContract);
  router.put("/:id", updateContract);
  router.get("/:id", getContractById);

  app.use(router.routes()).use(router.allowedMethods());
}