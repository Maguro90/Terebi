package com.maguro.terebi.domain

import com.maguro.terebi.data.RequestResponse
import com.maguro.terebi.data.model.Show
import com.maguro.terebi.data.repository.ShowRepository

interface SearchShowsUseCase {

    suspend operator fun invoke(query: String): RequestResponse<List<Show>>

}

class SearchShowUseCaseImpl(
    private val showRepository: ShowRepository
) : SearchShowsUseCase {

    override suspend fun invoke(query: String): RequestResponse<List<Show>> {
        return showRepository.searchShows(query)
    }
}