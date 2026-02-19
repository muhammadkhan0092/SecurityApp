package com.example.securityapp.core.data.sources

import com.example.securityapp.utils.Result
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await


class FirebaseRemoteDataSource(
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
        crossinline queryBuilder: (CollectionReference) -> Query
    ): List<T> {
        val query = queryBuilder(firestore.collection(collectionPath))
        val snapshot = query.get().await()
        return snapshot.documents.mapNotNull {
            it.toObject(T::class.java)
        }
    }
    suspend inline fun <reified T : Any> get(
        collectionPath: String,
        documentId: String
    ): T{
        val result = firestore
            .collection(collectionPath)
            .document(documentId)
            .get()
            .await()
        return result.toObject(T::class.java)?:throw Exception("User Null")
    }

    suspend fun getString(collectionId: String,documentId: String): List<String> {
        val result = firestore.collection(collectionId)
            .get()
            .await()
        return result.documents.mapNotNull {document->
            document.getString(documentId)
        }
    }
}