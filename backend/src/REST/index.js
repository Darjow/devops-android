const Router = require('@koa/router')

const installVmRouter = require('./_virtualmachine')
const installCustomerRouter = require('./_customer')
const installProjectRouter = require('./_project')
const installContractRouter = require('./_contract')

/**
 * @param {Koa} app
 */
module.exports = (app) => {
    const router = new Router({
      prefix: '/api',
    });
  
    installVmRouter(router)
    installContractRouter(router)
    installCustomerRouter(router)
    installProjectRouter(router)
  
    app.use(router.routes()).use(router.allowedMethods());
};