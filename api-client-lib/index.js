const fetch = require("isomorphic-unfetch");
const querystring = require("querystring");

class Client {
    constructor(basePath) {
        this.basePath = basePath;
    }

    request(endpoint = "", options = {}) {

        let url = this.basePath + endpoint;

        let headers = {
            'Content-type': 'application/json'
        };

        let config = {
            ...options,
            ...headers
        };


        return fetch(url, config).then(r => {
            if (r.ok) {
                return r.json()
            }
            throw new Error(r)
        })
    }

}