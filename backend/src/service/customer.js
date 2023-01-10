const repoCustomer = require('../repository/customer')

const getAll = async () => {
  console.log("Getting all customers...")
  const customers = await repoCustomer.getAll()
  console.log(customers)
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

const registerCustomer = async ({firstname_c, lastname_c, email_c, password_c, phonenumber_c, bedrijf_opleiding_c,
  contactpersoon1_phone_c ,contactpersoon1_email_c ,contactpersoon1_firstname_c ,contactpersoon1_lastname_c ,contactpersoon2_phone_c ,
contactpersoon2_email_c ,contactpersoon2_firstname_c ,contactpersoon2_lastname_c}) => {
    console.log('register customer with: ')
    console.log(firstname_c, lastname_c, email_c, password_c, phonenumber_c, bedrijf_opleiding_c, 
      contactpersoon1_phone_c ,contactpersoon1_email_c ,contactpersoon1_firstname_c ,contactpersoon1_lastname_c ,contactpersoon2_phone_c ,
    contactpersoon2_email_c ,contactpersoon2_firstname_c ,contactpersoon2_lastname_c)

  const id = await repoCustomer.registerCustomer(firstname_c, lastname_c, email_c, password_c, phonenumber_c, bedrijf_opleiding_c,
  contactpersoon1_phone_c ,contactpersoon1_email_c ,contactpersoon1_firstname_c ,contactpersoon1_lastname_c ,contactpersoon2_phone_c ,
  contactpersoon2_email_c ,contactpersoon2_firstname_c ,contactpersoon2_lastname_c)

  customer = await repoCustomer.getCustomerById(id)
  return customer[0]
}

const loginCustomer = async ({email,password}) => {
  const customer = await repoCustomer.loginCustomer(email, password)
  if(customer){
    return customer[0];
  }
  return ""
}

module.exports = {
  getAll,
  getCustomerById,
  updateCustomerById,
  registerCustomer,
  loginCustomer
}