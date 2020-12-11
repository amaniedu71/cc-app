const { parseAsync } = require('json2csv');
const {v4} = require('uuid'); 
const fs = require('fs'); 
const path = require('path'); 
const os = require('os'); 
const functions = require('firebase-functions');
const firebase = require('firebase-admin');



firebase.initializeApp({projectId: "communitycookerapp", storageBucket: 'communitycookerapp.appspot.com'});


exports.addMessage = functions.https.onRequest(async (req, res) => {


    let query = firebase.firestore().collection('users/WT1bFP46yEZXHYEGL3GKmk7LqNA2/user_reports')
    return query.get().then(snap => {
		  if(true) {
        const applications =  snap.docs.map(doc => doc.data());

     

        // get csv output
        return parseAsync(applications)
        .then(csv => {

          console.log(csv)
          // const output = await parseAsync(applications, { fields });

          // generate filename
          const dateTime = new Date().toISOString().replace(/\W/g, "");
          const filename = `applications_${dateTime}.csv`;

          const tempLocalFile = path.join(os.tmpdir(), filename);

          return new Promise((resolve, reject) => {
            //write contents of csv into the temp file
            fs.writeFile(tempLocalFile, csv, error => {
              if (error) {
                reject(error);
                return;
              }
              const bucket = firebase.storage().bucket();

              // upload the file into the current firebase project default bucket
              bucket
                .upload(tempLocalFile, {
                  // Workaround: firebase console not generating token for files
                  // uploaded via Firebase Admin SDK
                  // https://github.com/firebase/firebase-admin-node/issues/694
                  metadata: {
                    metadata: {
                      firebaseStorageDownloadTokens: v4(),
                    }
                  },
                })
                .then(() => resolve())
                .catch(errorr => reject(errorr));
            });
          }); 
        }) 
        .catch(err => console.error(err));
      }
      else {
			console.log('doesn\'t exist'); 
		  }
    });
}); 
