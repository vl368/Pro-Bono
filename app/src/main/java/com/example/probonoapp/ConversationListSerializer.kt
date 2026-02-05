package com.example.probonoapp

import androidx.datastore.core.Serializer
import androidx.datastore.core.CorruptionException
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object ConversationListSerializer : Serializer<ConversationList> {
    override val defaultValue: ConversationList = ConversationList.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): ConversationList {
        try {
            return ConversationList.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: ConversationList, output: OutputStream) {
        return t.writeTo(output)
    }
}

