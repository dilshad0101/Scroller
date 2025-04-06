package com.app.scoller.data

class AppDataSourceImpl {
}

interface AppDataSource{
    suspend fun getVideo():Any
}