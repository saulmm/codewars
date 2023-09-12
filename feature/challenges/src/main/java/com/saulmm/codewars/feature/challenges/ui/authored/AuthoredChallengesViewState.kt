import com.saulmm.codewars.entity.Challenge

sealed class AuthoredChallengesViewState(
    open val username: String = "",
    open val textQuery: String? = null
) {
    object Idle : AuthoredChallengesViewState()

    data class Loading(
        override val username: String,
        override val textQuery: String? = null
    ) : AuthoredChallengesViewState(username, textQuery)

    data class Failure(
        override val username: String,
        override val textQuery: String? = null
    ) : AuthoredChallengesViewState(username, textQuery)

    data class Loaded(
        override val username: String,
        override val textQuery: String? = null,
        val katas: List<Challenge> = emptyList()
    ) : AuthoredChallengesViewState(username, textQuery)
}
