package com.maguro.terebi.domain

import com.maguro.terebi.data.RequestResponse
import com.maguro.terebi.data.model.Episode
import com.maguro.terebi.data.model.Id
import com.maguro.terebi.data.model.Show
import com.maguro.terebi.data.repository.EpisodeRepository
import com.maguro.terebi.data.repository.ShowRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

interface GetShowWithEpisodeUseCase {
    suspend operator fun invoke(showId: Id, episodeId: Id): RequestResponse<ShowWithEpisode>

}

class GetShowWithEpisodeUseCaseImpl(
    private val showRepository: ShowRepository,
    private val episodeRepository: EpisodeRepository
) : GetShowWithEpisodeUseCase {
    override suspend fun invoke(showId: Id, episodeId: Id): RequestResponse<ShowWithEpisode> {
        return coroutineScope {

            val showDeferred = async { showRepository.getShowDetails(showId) }
            val episodeDeferred = async { episodeRepository.getEpisodeDetails(episodeId) }

            val showResponse = showDeferred.await()
            val episodeResponse = episodeDeferred.await()

            val show = when (showResponse) {
                is RequestResponse.Success -> {
                    showResponse.data
                }
                is RequestResponse.Error -> {
                    return@coroutineScope showResponse
                }
            }

            val episode = when (episodeResponse) {
                is RequestResponse.Success -> {
                    episodeResponse.data
                }
                is RequestResponse.Error -> {
                    return@coroutineScope episodeResponse
                }
            }

            RequestResponse.Success(
                data = ShowWithEpisode(
                    show = show,
                    episode = episode
                )
            )
        }
    }
}

data class ShowWithEpisode(
    val show: Show,
    val episode: Episode
)