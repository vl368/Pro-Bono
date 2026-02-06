package com.example.probonoapp

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.conversationDataStore: DataStore<ConversationList> by dataStore(
    fileName = "app_schema.pb",
    serializer = ConversationListSerializer
)

class ConversationDataManager(private val context: Context) {
    fun getConversations(): Flow<List<Conversation>> {
        return context.conversationDataStore.data.map { storedList ->
            storedList.conversationsList
        }
    }

    fun getConversation(conversationId: String): Flow<Conversation?> {
        return context.conversationDataStore.data.map { storedList ->
            storedList.conversationsList.firstOrNull { conversation ->
                conversation.conversationId == conversationId
            }
        }
    }

    suspend fun addConversation(
        participantUserNames: List<String> = listOf()
    ) {
        val conversationId = "${(100000..999999).random()}"
        val conversation = Conversation.newBuilder()
            .setConversationId(conversationId)
        for ((index, userName) in participantUserNames.withIndex()) {
            conversation.setParticipantUserNames(index, userName)
        }
        context.conversationDataStore.updateData { storedList ->
            storedList.toBuilder().addConversations(conversation).build()
        }
    }

    suspend fun deleteConversation(conversationId: String) {
        context.conversationDataStore.updateData { storedList ->
            val builder = storedList.toBuilder()
            val updatedList = builder.conversationsList.filterNot{ conversation ->
                conversation.conversationId == conversationId
            }

            builder.clearConversations()
            builder.addAllConversations(updatedList)
            builder.build()
        }
    }
}