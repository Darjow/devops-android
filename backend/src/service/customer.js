const repoCustomer = require('../repository/customer')

const getAll = async () => {
  const customers = await repoCustomer.getAll()
  return customers
}

const updateCustomerById = async (id, {customer}) => {
  console.log(id, customer)
  await repoCustomer.updateCustomerById(id, customer);
  return repoCustomer.getCustomerById(id)
}

const getCustomerById = async (id) => {
  const customer = await repoCustomer.getCustomerById(id)
  return customer
}

const registerCustomer = async ({firstname_c, lastname_c, email_c, password_c, phonenumber_c, 
  bedrijf_c, opleiding_c, contactpersoon1_c,contactpersoon2_c}) => {

  const id = await repoCustomer.registerCustomer(firstname_c, lastname_c, email_c, password_c, phonenumber_c, 
  bedrijf_c, opleiding_c, contactpersoon1_c,contactpersoon2_c)

  return await repoCustomer.getCustomerById(id)
}

const loginCustomer = async ({email,password}) => {
  const customer = await repoCustomer.loginCustomer(email, password)
  if(customer){
    return customer[0];
  }
  return null
}

module.exports = {
  getAll,
  getCustomerById,
  updateCustomerById,
  registerCustomer,
  loginCustomer
}