const config = require('config');
const knex = require('knex');
const { join } = require('path');


const DATABASE_CLIENT = config.get('database.client');
const DATABASE_NAME = config.get('database.name');
const DATABASE_HOST = config.get('database.host');
const DATABASE_PORT = config.get('database.port');
const DATABASE_USERNAME = config.get('database.username');
const DATABASE_PASSWORD = config.get('database.password');

let knexInstance;


const test = true;



async function initializeData() {
  console.log('Initializing connection to the database');

  const knexOptions = {
    client: DATABASE_CLIENT,
    connection: {
      host: DATABASE_HOST,
      port: DATABASE_PORT,
      user: DATABASE_USERNAME,
      password: DATABASE_PASSWORD,
      insecureAuth: true,
    },
    debug: false,

    migrations: {
      tableName: 'knex_meta',
      directory: join('src', 'data', 'migrations'),
    },
    seeds: {
      directory: join('src', 'data', 'seeds'),
    }
  };

  knexInstance = knex(knexOptions);

  try {
    await knexInstance.raw('SELECT 1+1 AS result');
    await knexInstance.raw(`CREATE DATABASE IF NOT EXISTS ${DATABASE_NAME}`);

    await knexInstance.destroy();

    knexOptions.connection.database = DATABASE_NAME;
    knexInstance = knex(knexOptions);
    await knexInstance.raw('SELECT 1+1 AS result');
  } catch (error) {
    console.log(error);
    throw new Error('Could not initialize the data layer');
  }

  // Run migrations
  let migrationsFailed = true;
  try {
    await knexInstance.migrate.latest();
    migrationsFailed = false;
  } catch (error) {
    console.log('Error while migrating the database');
    console.log(error);
  }

  // Undo last migration if something failed
  if (migrationsFailed) {
    try {
      await knexInstance.migrate.down();
    } catch (error) {
      console.log('Error while undoing last migration');
      console.log(error);
    }

    // No point in starting the server
    throw new Error('Migrations failed');
  }

  if(test){
    try {
      await knexInstance.seed.run();
    } catch (error) {
      console.log('Error while seeding database');
      console.log(error)
    }
  
  }
  console.info('Succesfully connected to the database');

  return knexInstance;
}

async function shutdownData() {

  console.info('Shutting down database connection');

  await knexInstance.destroy();
  knexInstance = null;

  console.info('Database connection closed');
}

function getKnex() {
  if (!knexInstance) throw new Error('Please initialize the data layer before getting the Knex instance');
  return knexInstance;
}

const tables = {
  customer : 'customers',
  virtualmachine : 'virtualmachines',
  project : 'projects',
  contract : 'contracts'
};

module.exports = {
  tables,
  getKnex,
  initializeData,
  shutdownData,
};
