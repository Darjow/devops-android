const Router = require("@koa/router");
const customerService = require("../service/customer");

const getCustomers = async (ctx) => {
  ctx.body = await customerService.getAll();
}

const login = async (ctx) => {
  console.log("Login request has been called.");
    let temp = await customerService.loginCustomer(
      {
        email : ctx.request.body.email,
        password : ctx.request.body.password
      })
      ctx.body = temp
  console.log("Returning: ");
  console.log(temp);
}

const register = async(ctx) => {
  console.log('Registering a customer....')
  ctx.body = await customerService.registerCustomer(ctx.request.body)
}

const getCustomerById = async(ctx) => {
  ctx.body = await customerService.getCustomerById(ctx.params.id)
}

const updateCustomerById = async(ctx) => {
  console.log("Updating user with id: " + ctx.params.id)
  ctx.body = await customerService.updateCustomerById(ctx.params.id, ctx.request.body)
}

module.exports = (app) => {
  const router = new Router({prefix:"/customer"});

  router.get("/", getCustomers);
  router.post("/", register);
  router.post("/login", login);
  router.get("/:id", getCustomerById);
  router.put("/:id", updateCustomerById)

  app.use(router.routes()).use(router.allowedMethods());
}