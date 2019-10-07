const functions = require('firebase-functions');
const admin = require('firebase-admin');

const increment = admin.firestore.FieldValue.increment(1);
const decrement = admin.firestore.FieldValue.increment(-1);

admin.initializeApp();

exports.setDefaultSources = functions.region('europe-west1')
	.auth
	.user()
	.onCreate((user) => {

  		var defaultSources = {
     		isMediumOn : true,
    		isStackoverflowOn : true,
     		isDevToOn : true
  		};

  	    return admin.firestore()
		    .collection('sources')
		    .doc(user.uid)
		    .set(defaultSources);
});

exports.addLikedArticle = functions.region('europe-west1')
    .firestore
    .document('profiles/{profileId}/likedArticles/{articleId}')
    .onCreate((snap, context) => {

        return admin.firestore()
                .collection('articles')
                .doc(context.params.articleId)
                .update({likesCount: increment});
});

exports.removeLikedArticle = functions.region('europe-west1')
    .firestore
    .document('profiles/{profileId}/likedArticles/{articleId}')
    .onDelete((snap, context) => {

        return admin.firestore()
                .collection('articles')
                .doc(context.params.articleId)
                .update({likesCount: decrement});
});