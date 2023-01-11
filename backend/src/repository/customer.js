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

const registerCustomer = async (customer) => {
  const [id] = await getKnex()(tables.customer).insert({
    firstname: customer.firstname,
    lastname : customer.lastname ,
    email : customer.email,  
    password : customer.password,
    phonenumber: customer.phonenumber,
    bedrijf_opleiding : customer.bedrijf_opleiding,

    contact1_phone : customer.contactPs1.contact1_phone,
    contact1_email : customer.contactPs1.contact1_email,
    contact1_firstname : customer.contactPs1.contact1_firstname,
    contact1_lastname : customer.contactPs1.contact1_lastname,

    contact2_phone : customer.contactPs2.contact2_phone,
    contact2_email : customer.contactPs2.contact2_email,
    contact2_firstname : customer.contactPs2.contact2_firstname,
    contact2_lastname : customer.contactPs2.contact2_lastname,
  });
    return id
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