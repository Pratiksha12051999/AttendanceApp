"use strict" ;

const  functions  =  require ( "firebase-functions" ) ;

// Firebase Admin initialization
var  admin  =  require ( "firebase-admin" ) ;
var  serviceAccount  =  require ( "./serviceAccount.json" ) ;
admin . initializeApp ( {
  credential : admin . credential . cert ( serviceAccount ) ,
  databaseURL : "https://attendance-app-3e6d7.firebaseio.com"
} ) ;

// Get Google Sheets instance
const  { google }  =  require ( "googleapis" ) ;
const  sheets  =  google . sheets ( "v4" ) ;
// Create JWT
const  jwtClient  =  new  google . auth . JWT ( {
  email : serviceAccount . client_email ,
  key : serviceAccount . private_key ,
  scopes : [ "https://www.googleapis.com/auth/spreadsheets" ]  // read and write sheets
} ) ;

// Get data from RTDB
exports . Sheet1ToSheet  =  functions . database . ref ( "/Sheet1" ) . onUpdate ( async  change  =>  {
  let  data  =  change . after . val ( ) ;

  // Convert JSON to Array following structure below
  / * 
  [
    ['COL-A', 'COL-B'],
    ['COL-A', 'COL-B']
  ]
  * /
  var  itemArray  =  [ ] ;
  var  valueArray  =  [ ] ;
  Object . keys ( data ) . forEach ( ( key ,  index )  =>  {
    itemArray . push ( key ) ;
    itemArray . push ( data [ key ] ) ;
    valueArray [ index ]  =  itemArray ;
    itemArray  =  [ ] ;
  } ) ;

  let  maxRange  =  valueArray . length  +  1 ;

  // Do authorization
  await  jwtClient . authorize ( ) ;
  
  // Create Google Sheets request
  let  request  =  {
    auth : jwtClient ,
    spreadsheetId : "1xdYDgDXnj1ZVODHEdcSAv51ex58GuGVPBrBTQVFOrUs" ,
    range : "Sheet1! A2: CL440"  +  maxRange ,
    valueInputOption : "RAW" ,
    requestBody : {
      values : valueArray
    }
  } ;
  
  // Update data to Google Sheets
  await  sheets . spreadsheets . values . update ( request ,  { } ) ;
} ) ;