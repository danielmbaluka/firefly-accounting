var mirFactory = angular.module('mirFactory', []);

mirFactory.factory('mirFactory', ['$http', function($http) {
    this.mirLst = function(){
        return $http.get('/mir/list');
    };

    this.getMIR = function (id) {
        return $http.get('/mir/'+id);
    };

    this.mrctLst = function(){
        return [{
            referenceNo: "MRCT-01-15-0001",
            date: "January 1, 2015",
            purpose: "A sample purpose",
            preparedBy: "John Rsample",
            issuedBy: "Joseph Xsample",
            receivedBy: "Mark Lsample",
            approvedBy: "Vince Isample",
            details: [
                {
                    itemDescription: "Short bond paper 8.5 x 11",
                    quantity: 2,
                    unitCost: 150,
                    itemCost: 300
                },
                {
                    itemDescription: "Epson Printer",
                    quantity: 1,
                    unitCost: 1500,
                    itemCost: 1500
                },
                {
                    itemDescription: "Pilot Ballpen",
                    quantity: 100,
                    unitCost: 7,
                    itemCost: 700
                }
            ]

        },
            {

                referenceNo: "MRCT-01-15-0001",
                date: "January 1, 2015",
                purpose: "What is the purpose?",
                preparedBy: "John Rsample",
                issuedBy: "Joseph Xsample",
                receivedBy: "Mark Lsample",
                approvedBy: "Vince Isample",
                details: [
                    {
                        itemDescription: "Short bond paper 8.5 x 11",
                        quantity: 2,
                        unitCost: 150,
                        itemCost: 300
                    },
                    {
                        itemDescription: "Epson Printer",
                        quantity: 1,
                        unitCost: 1500,
                        itemCost: 1500
                    },
                    {
                        itemDescription: "Pilot Ballpen",
                        quantity: 100,
                        unitCost: 7,
                        itemCost: 700
                    }
                ]

            },
            {
                referenceNo: "MRCT-01-15-0001",
                date: "January 1, 2015",
                purpose: "The purpose should be required",
                preparedBy: "John Rsample",
                issuedBy: "Joseph Xsample",
                receivedBy: "Mark Lsample",
                approvedBy: "Vince Isample",
                details: [
                    {
                        itemDescription: "Short bond paper 8.5 x 11",
                        quantity: 2,
                        unitCost: 150,
                        itemCost: 300
                    },
                    {
                        itemDescription: "Epson Printer",
                        quantity: 1,
                        unitCost: 1500,
                        itemCost: 1500
                    },
                    {
                        itemDescription: "Pilot Ballpen",
                        quantity: 100,
                        unitCost: 7,
                        itemCost: 700
                    }
                ]

            }];

    };

    this.mockSelectedMrct = function(){

        return [{
            "description": "Carbidopa, Levodopa and Entacapone",
            "unit": "in",
            "quantity": 5,
            "unitPrice": 7603,
            "amount": 38015
        }, {
            "description": "Valproic",
            "unit": "in",
            "quantity": 35,
            "unitPrice": 5588,
            "amount": 195580
        }, {
            "description": "Azathioprine",
            "unit": "yrd",
            "quantity": 75,
            "unitPrice": 2717,
            "amount": 203775
        }, {
            "description": "symmetry Foaming Hand Sanitizer with Aloe and Vitamin E",
            "unit": "ml",
            "quantity": 60,
            "unitPrice": 2390,
            "amount": 143400
        }, {
            "description": "Megestrol Acetate",
            "unit": "yrd",
            "quantity": 25,
            "unitPrice": 9184,
            "amount": 229600
        }];
    };
    return this;
}]);