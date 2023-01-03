const Koa = require('koa');
const config = require('config');
const koaCors = require('@koa/cors');
const bodyParser = require('koa-bodyparser');
const { initializeData, shutdownData } = require('./data');
const installRest = require('./REST');

const CORS_ORIGINS = config.get('cors.origins');
const CORS_MAX_AGE = config.get('cors.maxAge');


module.exports = async function createServer () {

	await initializeData();

	const app = new Koa();
	app.use(koaCors())	
	app.use(bodyParser());
	
	installRest(app);

    return {
        getApp(){
            return app;
        },

        start(){
            return new Promise((resolve) => {
            app.listen(9000);
            console.info(`Server listening on http://localhost:9000`);
            resolve()
            })
        },

        async stop(){{
            app.removeAllListeners();
            await shutdownData();
            console().info('Goodbye');
        }}
        }
    }
	
  