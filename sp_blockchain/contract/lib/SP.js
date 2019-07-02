/*
 * SPDX-License-Identifier: Apache-2.0
 */

'use strict';

const { Contract } = require('fabric-contract-api');


class SP extends Contract {

    async instantiate(ctx) {
        console.info('Contract is instantiating');
    }

    async CreateAssetsandMembers(ctx) {

        console.info('Create 2 Customers and 2 Pools');

        var customerKey = '320926195511175276';
        var customer = {
              docType: 'customer',
              name: 'zhang san',
              creditAmount: '10000.00',
              creditBonus: '0.00'
        };

        await ctx.stub.putState(customerKey, JSON.stringify(customer));

        customerKey = '320926195511175277';
        customer = {
              docType: 'customer',
              name: 'li si',
              creditAmount: '15000.00',
              creditBonus: '10.00'
        };

        await ctx.stub.putState(customerKey, JSON.stringify(customer));

        var creditPoolKey = 'CREDITPOOL';
        var creditPool = { 
              docType: 'creditPool', 
              description: 'credit pool',
              serialNumber: '0001',
              amount: '200000.00',
              owner: 'BOC'
        };

        await ctx.stub.putState(creditPoolKey, JSON.stringify(creditPool));

        var cashPoolKey = 'CASHPOOL';
        var cashPool = { 
              docType: 'cashPool', 
              description: 'cash pool',
              serialNumber: '0001',
              amount: '200000.00',
              owner: 'BOC'
        };

        await ctx.stub.putState(cashPoolKey, JSON.stringify(cashPool));

        return;

    }


    // read function to query the attribute of a customer
    async checkAttribute(ctx, commKey) {
        console.info('Get attribute', commKey);

        let customerBytes = await ctx.stub.getState(commKey);

        var customer = JSON.parse(customerBytes);
        console.info('Customer attribute: ', customer.name);

        return 'Customer attribute:' + customer.name;
    }

    // trade function to trade credit/cash to another
    async trade(ctx, debitKey, creditKey, amount, creditOrCash) {
        console.info('Trade to new owner', commKey, ' ', newOwner);

        let debitBytes = await ctx.stub.getState(debitKey);
        let creditBytes = await ctx.stub.getState(creditKey);
        if (debitBytes.length > 0 && creditBytes.length > 0) {
            var debit = JSON.parse(debitBytes);
            var credit = JSON.parse(creditBytes);
            console.info('debit is : ', debit.name);
            console.info('credit is: ', credit.name);

            //use bignumber.js to calculate 
            //debit.creditAmount = debit.creditAmount - amount
            //credit.creditAmount = credit.creditAmount + amount

            await ctx.stub.putState(debitKey, JSON.stringify(debit));
            await ctx.stub.putState(creditKey, JSON.stringify(credit));

            return 'Success';
        }
        else {
            console.info('No customer with that key: ', debitKey);
            return 'No customer with that key:' + debitKey;
        }
    }

}

module.exports = SP;