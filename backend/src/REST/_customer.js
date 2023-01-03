const Router = require("@koa/router");
const customerService = require("../service/customer");

const getCustomers = async (ctx) => {
  ctx.body = await customerService.getAll();
}

const login = async (ctx) => {
    ctx.body = await customerService.loginCustomer(
      {
        email : ctx.request.body.loginCredentials.email,
        password : ctx.request.body.loginCredentials.password
      })
}
const register = async(ctx) => {
  ctx.body = await customerService.registerCustomer({
    firstname_c : ctx.request.body.firstname, 
    lastname_c : ctx.request.body.lastname, 
    email_c : ctx.request.body.email, 
    password_c : ctx.request.body.password, 
    phonenumber_c : ctx.request.body.phonenumber, 
    bedrijf_c : ctx.request.body.bedrijf, 
    opleiding_c : ctx.request.body.opleiding, 
    contactpersoon1_c : ctx.request.body.constactpersoon1,
    contactpersoon2_c : ctx.request.body.constactpersoon2
  })
}

const getCustomerById = async(ctx) => {
  ctx.body = await customerService.getCustomerById(ctx.params.id)
}

const updateCustomerById = async(ctx) => {
  ctx.body = await customerService.updateCustomerById(ctx.params.id, {
    customer: ctx.request.body.customer,
  })
}

module.exports = (app) => {
  const router = new Router({prefix:"/customer"});

  router.get("/", getCustomers);
  router.post("/", register);
  router.get("/login", login);
  router.get("/:id", getCustomerById);
  router.put("/:id", updateCustomerById)

  app.use(router.routes()).use(router.allowedMethods());
}