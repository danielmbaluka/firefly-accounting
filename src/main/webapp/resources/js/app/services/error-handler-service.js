var errorHandlerService = angular.module('errorHandlerService', []);

errorHandlerService.service('errorToElementBinder', function() {
    this.bindToElements = function(data, scopeErrors) {
        var fields = [];
        try {
            var messages = data.messages;
            for (var idx = 0; idx < messages.length; idx++) {
                var field = 'err_' + data.fields[idx];

                var index = fields.indexOf(field);

                if (index >= 0) { // found
                    var msgs = [];
                    msgs = scopeErrors[field];
                    msgs.push(data.messages[idx]);
                    scopeErrors[field] = msgs;

                } else {    // fresh message
                    scopeErrors[field] = [data.messages[idx]];
                    fields.push(field);
                }
            }
        }catch (err) {}
        return scopeErrors;
    }
});