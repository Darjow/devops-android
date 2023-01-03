const {tables, getKnex} = require("../data/index");

const getAll = async () => {
  return await getKnex()(tables.project).select();
}

const updateCustomerById = async (id, customer) => {
    await getKnex()(tables.customer)
          .update({
            "contactpersoon1" : customer.contactpersoon1,
            "contactpersoon2" : customer.contactpersoon2
          })
          .where("id", id)
    return id
}

const getCustomerById = async (id) => {
  return await getKnex()(tables.customer).select().where("id",id)
}

const registerCustomer = async (firstname_c, lastname_c, email_c, password_c, phonenumber_c, 
bedrijf_c, opleiding_c, contactpersoon1_c,contactpersoon2_c) => {
  try{
    const [id] = await getKnex()(tables.customer).insert({
      email: email_c,
      phonenumber : phonenumber_c , 
      firstname : firstname_c,
      lastname : lastname_c,
      password: password_c,
      bedrijf : bedrijf_c,
      opleiding : opleiding_c,
      contactpersoon1 : JSON.stringify(contactpersoon1_c),
      contactpersoon2 : JSON.stringify(contactpersoon2_c),
    });
    return id
  }catch (error){
    console.log(error)
  }
}

const loginCustomer = async (email,password) => {
  const customer =  await getKnex()(tables.customer)
  .where(getKnex().raw(`email = "${email}"`))
  .where(getKnex().raw(`password = "${password}"`))
    return customer
}

module.exports = {
  getAll,
  getCustomerById,
  updateCustomerById,
  registerCustomer,
  loginCustomer
}