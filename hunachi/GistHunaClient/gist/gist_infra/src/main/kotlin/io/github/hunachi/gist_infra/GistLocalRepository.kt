package io.github.hunachi.gist_infra

import androidx.paging.DataSource
import io.github.hunachi.database.MyDatabase
import io.github.hunachi.model.File
import io.github.hunachi.model.Gist
import kotlinx.coroutines.experimental.*

class GistLocalRepository(private val database: MyDatabase) {

    fun gists(): DataSource.Factory<Int, Gist> = database.getGistDao().findGists()

    fun userGists(name: String): DataSource.Factory<Int, Gist> = database.getGistDao().findUserGists(name)

    suspend fun insertGists(gists: List<Gist>) = coroutineScope {
        launch {
            database.getGistDao().insertGists(gists)
        }.join()
    }

    suspend fun insertFiles(files: List<File>) = coroutineScope {
        launch {
            database.getFileDao().insertFiles(files)
        }.join()
    }

    suspend fun deleteGists() = coroutineScope {
        launch {
            database.getGistDao().deleteAllGists()
        }.join()
    }
}