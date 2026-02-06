package com.example.probonoapp

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.postDataStore: DataStore<PostList> by dataStore(
    fileName = "app_schema.pb",
    serializer = PostListSerializer
)


class PostDataManager(private val context: Context) {
    fun getPosts(): Flow<List<Post>> {
        return context.postDataStore.data.map { storedList ->
            storedList.postsList
        }
    }

    fun getPost(postId: String): Flow<Post?> {
        return context.postDataStore.data.map { storedList ->
            storedList.postsList.firstOrNull { post ->
                post.postId == postId
            }
        }
    }

    suspend fun addPost(
        title: String,
        specialities: List<String> = listOf(),
        description: String,
        creatorUserName: String,
        postedAt: String
    ) {
        val postId = "${(100000..999999).random()}"
        val post = Post.newBuilder()
            .setTitle(title)
            .setDescription(description)
            .setCreatorUserName(creatorUserName)
            .setPostId(postId)
            .setPostedAt(postedAt)
        for ((index, speciality) in specialities.withIndex()) {
            post.setSpecialities(index, speciality)
        }
        context.postDataStore.updateData { storedList ->
            storedList.toBuilder().addPosts(post).build()
        }
    }

    suspend fun deletePost(postId: String) {
        context.postDataStore.updateData { storedList ->
            val builder = storedList.toBuilder()
            val updatedList = builder.postsList.filterNot{ post ->
                post.postId == postId
            }

            builder.clearPosts()
            builder.addAllPosts(updatedList)
            builder.build()
        }
    }
}