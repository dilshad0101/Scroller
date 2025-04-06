package com.app.scoller.data

class AppDataRepositoryImpl {

}

interface AppDataRepository {
    suspend fun getVideo(): Any
}