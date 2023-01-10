const {tables, getKnex} = require("../data/index");

const getAll = async () => {
  return await getKnex()(tables.customer).select();
}

const updateCustomerById = async (id, customer) => {
    await getKnex()(tables.customer)
          .update({
            "contact1_phone" : customer.contactPs1.contact1_phone,
            "contact1_email" : customer.contactPs1.contact1_email,
            "contact1_firstname": customer.contactPs1.contact1_firstname,
            "contact1_lastname": customer.contactPs1.contact1_lastname,
            "contact2_phone" : customer.contactPs2.contact2_phone,
            "contact2_email" : customer.contactPs2.contact2_email,
            "contact2_firstname": customer.contactPs2.contact2_firstname,
            "contact2_lastname": customer.contactPs2.contact2_lastname,
          })
          .where("id", id)
    return id
}

const getCustomerById = async (id) => {
  return await getKnex()(tables.customer).select().where("id",id)
}

//const registerCustomer = async (customer)
const registerCustomer = async (firstname_c, lastname_c, email_c, password_c, phonenumber_c, bedrijf_opleiding_c,
  contactpersoon1_phone_c ,contactpersoon1_email_c ,contactpersoon1_firstname_c ,contactpersoon1_lastname_c ,contactpersoon2_phone_c ,
contactpersoon2_email_c ,contactpersoon2_firstname_c ,contactpersoon2_lastname_c) => {
  try{
    const [id] = await getKnex()(tables.customer).insert({
      email: email_c,
      phonenumber : phonenumber_c , //customer.phonenumber
      firstname : firstname_c,  //....
      lastname : lastname_c,
      password: password_c,
      bedrijf_opleiding : bedrijf_opleiding_c,

      contact1_phone : contactpersoon1_phone_c,
      contact1_email : contactpersoon1_email_c,
      contact1_firstname : contactpersoon1_firstname_c,
      contact1_lastname : contactpersoon1_lastname_c,

      contact2_phone : contactpersoon2_phone_c,
      contact2_email : contactpersoon2_email_c,
      contact2_firstname : contactpersoon2_firstname_c,
      contact2_lastname : contactpersoon2_lastname_c,
    });
    return id
  }catch (error){
    console.log(error)
  }
}

const loginCustomer = async (email,password) => {
  const customer =  await getKnex()(tables.customer)
    .where("email", email)
    .where("password", password)
  return customer
}

module.exports = {
  getAll,
  getCustomerById,
  updateCustomerById,
  registerCustomer,
  loginCustomer
}