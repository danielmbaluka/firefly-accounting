// get application's base URL
var baseURL = document.location.origin + document.location.pathname;

// toastr notifier https://github.com/CodeSeven/toastr
toastr.options.positionClass = 'toast-top-right';
toastr.options.closeButton = true;
toastr.options.extendedTimeOut = 1000;
toastr.options.timeOut = 5000;

// document status
var DOC_STAT_CREATED = 1;
var DOC_STAT_FOR_CHECKING = 2;
var DOC_STAT_APPROVED = 7;
var DOC_STAT_FOR_CANVASSING = 9;

var DOC_STAT_CREATED_DESC = 'Document Created';
var DOC_RETURNED_TO_CREATOR_DESC = 'Returned to creator';
var DOC_FOR_CHECK_WRITING_DESC = 'For Check Writing';

// entity types
var ENTITY_EMPLOYEE = 1;
var ENTITY_SUPPLIER = 2;


//try {
//
//    // web sockets: for notifications
//    var socket = io.connect('//localhost:3000');
//
//    socket.on('welcome' , function(data){
//        $('#messages').append('<li>' + data.message + '</li>');
//        socket.emit('i am client', {data: 'foo!'});
//    });
//
//    socket.on('time', function(data){
//        console.log(data);
//        $('#messages').append('<li>' + data.time + '</li>');
//    });
//
//    socket.on('error', function(){ connect.error(arguments)});
//    socket.on('message', function(){ connect.log(arguments)});
//}catch (e) {
//    console.log(e);
//}