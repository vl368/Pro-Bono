package com.example.probonoapp

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.userDataStore: DataStore<UserList> by dataStore(
    fileName = "app_schema.pb",
    serializer = UserListSerializer
)

val Context.postDataStore: DataStore<PostList> by dataStore(
    fileName = "app_schema.pb",
    serializer = PostListSerializer
)

val Context.conversationDataStore: DataStore<ConversationList> by dataStore(
    fileName = "app_schema.pb",
    serializer = ConversationListSerializer
)

class DataManager(private val context: Context) {
    fun getUsers(): Flow<List<User>> {
        return context.userDataStore.data.map { storedList ->
            storedList.usersList
        }
    }

    fun getPosts(): Flow<List<Post>> {
        return context.postDataStore.data.map { storedList ->
            storedList.postsList
        }
    }

    fun getConversations(): Flow<List<Conversation>> {
        return context.conversationDataStore.data.map { storedList ->
            storedList.conversationsList
        }
    }

    fun getUser(userName: String): Flow<User?> {
        return context.userDataStore.data.map { storedList ->
            storedList.usersList.firstOrNull { user ->
                user.userName == userName
            }
        }
    }

    fun getPost(postId: String): Flow<Post?> {
        return context.postDataStore.data.map { storedList ->
            storedList.postsList.firstOrNull { post ->
                post.postId == postId
            }
        }
    }

    fun getConversation(conversationId: String): Flow<Conversation?> {
        return context.conversationDataStore.data.map { storedList ->
            storedList.conversationsList.firstOrNull { conversation ->
                conversation.conversationId == conversationId
            }
        }
    }

    suspend fun addUser(
        userName: String,
        password: String,
        emailAddress: String,
        firstName: String,
        lastName: String,
        zipCode: String,
        volunteer: Boolean,
        client: Boolean,
        firmName: String? = null,
        education: String? = null,
        barNumber: String? = null,
        specialities: List<String> = listOf(),
        occupation: String? = null
    ) {
        val user = User.newBuilder()
            .setUserName(userName)
            .setPassword(password)
            .setEmailAddress(emailAddress)
            .setFirstName(firstName)
            .setLastName(lastName)
            .setZipCode(zipCode)
            .setVolunteer(volunteer)
            .setClient(client)
        if (firmName != null) {
            val volunteerInfo = VolunteerProfile.newBuilder()
                .setFirmName(firmName)
                .setEducation(education)
                .setBarNumber(barNumber)
            for ((index, speciality) in specialities.withIndex()) {
                volunteerInfo.setSpecialities(index, speciality)
            }
            user.setVolunteerInfo(volunteerInfo)
        }
        else {
            val clientInfo = ClientProfile.newBuilder()
                .setOccupation(occupation)
            user.setClientInfo(clientInfo)
        }

        context.userDataStore.updateData { storedList ->
            storedList.toBuilder().addUsers(user).build()
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

    suspend fun addMessage(
        senderUserName: String,
        content: String,
        sentAt: String,
        conversationId: String
    ) {
        val newMessage = Message.newBuilder()
            .setSenderUserName(senderUserName)
            .setContent(content)
            .setSentAt(sentAt)
        context.conversationDataStore.updateData { storedList ->
            val builder = storedList.toBuilder()
            val conversation = storedList.conversationsList.firstOrNull() { conversation ->
                conversation.conversationId == conversationId
            }
            deleteConversation(conversationId)
            builder.addConversations(
                conversation?.toBuilder()
                    ?.addMessages(newMessage)
                    ?.build()
            )
            builder.build()
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

    suspend fun deleteUser(userName: String) {
        context.userDataStore.updateData { storedList ->
            val builder = storedList.toBuilder()
            val updatedList = builder.usersList.filterNot{ user ->
                user.userName == userName
            }

            builder.clearUsers()
            builder.addAllUsers(updatedList)
            builder.build()
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

    suspend fun addSampleData() {
        addUser(
            userName = "jdoe_law",
            password = "password123",
            emailAddress = "jdoe@example.com",
            firstName = "John",
            lastName = "Doe",
            zipCode = "19104",
            volunteer = true,
            client = false,
            firmName = "Doe & Associates",
            education = "University of Pennsylvania Law School",
            barNumber = "PA123456",
            specialities = listOf("Immigration Law", "Family Law")
        )
        addUser(
            userName = "asmith_esq",
            password = "securePass!",
            emailAddress = "asmith@example.com",
            firstName = "Alice",
            lastName = "Smith",
            zipCode = "10001",
            volunteer = true,
            client = false,
            firmName = "NY Public Defender Office",
            education = "Columbia Law School",
            barNumber = "NY987654",
            specialities = listOf("Criminal Defense")
        )
        addUser(
            userName = "mgarcia",
            password = "clientPass1",
            emailAddress = "mgarcia@example.com",
            firstName = "Maria",
            lastName = "Garcia",
            zipCode = "90210",
            volunteer = false,
            client = true,
            occupation = "Restaurant Worker"
        )
        addUser(
            userName = "tjohnson",
            password = "helpNeeded!",
            emailAddress = "tjohnson@example.com",
            firstName = "Thomas",
            lastName = "Johnson",
            zipCode = "60616",
            volunteer = false,
            client = true,
            occupation = "Delivery Driver"
        )
        addUser(
            userName = "lchen_probono",
            password = "volunteerPW",
            emailAddress = "lchen@example.com",
            firstName = "Linda",
            lastName = "Chen",
            zipCode = "94105",
            volunteer = true,
            client = false,
            firmName = "Chen Legal Group",
            education = "UC Berkeley School of Law",
            barNumber = "CA246810",
            specialities = listOf("Employment Law", "Housing Law")
        )
    }

    suspend fun findUser(userName: String, password: String, client: Boolean): Boolean {
        val storedList = context.userDataStore.data.first()

        val user = storedList.usersList.firstOrNull {
            it.userName == userName &&
                    it.password == password &&
                    it.client == client
        }

        return user != null
    }
}