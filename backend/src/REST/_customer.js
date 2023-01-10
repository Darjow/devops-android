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
  ctx.body = await customerService.registerCustomer({
    firstname_c : ctx.request.body.firstname, 
    lastname_c : ctx.request.body.lastname, 
    email_c : ctx.request.body.email, 
    password_c : ctx.request.body.password, 
    phonenumber_c : ctx.request.body.phonenumber, 
    bedrijf_opleiding_c : ctx.request.body.bedrijf_opleiding,
    /*contactps 1*/
    contactpersoon1_phone_c : ctx.request.body.contactPs1.contact1_phone,
    contactpersoon1_email_c : ctx.request.body.contactPs1.contact1_email,
    contactpersoon1_firstname_c : ctx.request.body.contactPs1.contact1_firstname,
    contactpersoon1_lastname_c : ctx.request.body.contactPs1.contact1_lastname,
  /*contactps 2*/
    contactpersoon2_phone_c : ctx.request.body.contactPs2.contact1_phone,
    contactpersoon2_email_c : ctx.request.body.contactPs2.contact1_email,
    contactpersoon2_firstname_c : ctx.request.body.contactPs2.contact1_firstname,
    contactpersoon2_lastname_c : ctx.request.body.contactPs2.contact1_lastname,
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
  router.post("/login", login);
  router.get("/:id", getCustomerById);
  router.put("/:id", updateCustomerById)

  app.use(router.routes()).use(router.allowedMethods());
}