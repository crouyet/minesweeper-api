const fetch = require("isomorphic-unfetch");
const querystring = require("querystring");

class MinesweeperClient {

    constructor(basePath) {
        this.basePath = basePath;
    }

    request(endpoint = "", options = {}, user = null) {

        let url = this.basePath + endpoint;

        let headers = {
            'Content-type': 'application/json',
            'cookie': 'user='+user
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

    saveUser(id) {
        let url = "/users/" + id;
        let config = {
            method: 'POST'
        };

        return this.request(url, config)
    }

    getUser(id) {
        let url = "/users/" + id;
        let config = {
            method: 'GET'
        };

        return this.request(url, config)
    }

    deleteUser(id) {
        let url = "/users/" + id;
        let config = {
            method: 'DELETE'
        };

        return this.request(url, config)
    }

    /**
     *
     * @param options-> optional: cols, rows, mines
     * @param userId -> optional
     * @returns GameInfo
     */
    newGame(options, userId) {
        let qs = options ? "?" + querystring.stringify(options) : "";

        let url = "/games" + qs;
        let config = {
            method: 'GET'
        };

        return this.request(url, config, userId);
    }

    /**
     *
     * @param id -> required: game id
     * @param move -> required: CLOSE, DISCOVER, FLAG, QUESTION
     * @param options -> required: posX, posY
     * @param userId -> optional
     * @returns GameInfo
     */
    makeMove(id, move, options, userId) {
        let qs = options ? "?" + querystring.stringify(options) : "";

        let url = "/games/" + id + "/" + move + qs;
        let config = {
            method: 'PUT'
        };

        return this.request(url, config, userId);
    }

    /**
     *
     * @param id -> required: game id
     * @param userId -> optional
     * @returns GameInfo
     */
    pauseGame(id, userId) {
        let url = "/games/" + id + "/pause";
        let config = {
            method: 'PUT'
        };

        return this.request(url, config, userId);
    }

    /**
     *
     * @param id -> required: game id
     * @param userId -> optional
     * @returns GameInfo
     */
    deleteGame(id, userId) {
        let url = "/games/" + id + "/delete";
        let config = {
            method: 'DELETE'
        };

        return this.request(url, config, userId);
    }

}