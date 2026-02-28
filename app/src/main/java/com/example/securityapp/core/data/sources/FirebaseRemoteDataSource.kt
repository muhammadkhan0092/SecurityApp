package com.example.securityapp.core.data.sources

import com.example.securityapp.utils.Result
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class FirebaseRemoteDataSource @Inject constructor(
    val firestore: FirebaseFirestore
) {
    suspend fun <T : Any> addData(
        documentId : String,
        collectionId : String,
        data : T
    ) : Result<Unit>{
        firestore.collection(collectionId).document(documentId).set(data).await()
        return Result.Success(Unit)
    }
    suspend inline fun <reified T : Any> queryCollection(
        collectionPath: String,
        documentId: String
    ): T? {
        val snapshot = firestore.collection(collectionPath).document(documentId).get().await()
        return snapshot.toObject(T::class.java)
    }
    suspend inline fun <reified T : Any> get(
        collectionPath: String,
        documentId: String
    ): T?{
        val result = firestore
            .collection(collectionPath)
            .document(documentId)
            .get()
            .await()
        return result.toObject(T::class.java)
    }

    suspend inline fun <reified T : Any> getDocumentByEqualFilter(
        collectionPath: String,
        key : String,
        value : String
    ): T{
        val result = firestore
            .collection(collectionPath)
            .whereEqualTo(key,value)
            .get()
            .await()
        return result.toObjects(T::class.java).first()?:throw Exception("User Null")
    }

    suspend inline fun <reified T : Any> getAllDocuments(
        collectionPath: String,
    ): List<T>{
        val result = firestore
            .collection(collectionPath)
            .get()
            .await()
        return result.toObjects(T::class.java)
    }

    suspend fun getString(collectionId: String,documentId: String): List<String> {
        val result = firestore.collection(collectionId)
            .get()
            .await()
        return result.documents.mapNotNull {document->
            document.getString(documentId)
        }
    }

    inline fun <reified T : Any> listenDocument(
        collectionPath: String,
        documentId: String
    ): Flow<T?> = callbackFlow {
        val docRef = firestore.collection(collectionPath).document(documentId)
        val listener = docRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            val data = snapshot?.toObject(T::class.java)
            trySend(data).isSuccess
        }
        awaitClose { listener.remove() }
    }
}