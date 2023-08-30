import com.google.common.truth.Truth.assertThat
import com.saulmm.codewars.repository.CachingRepository
import com.saulmm.codewars.repository.ReadAndWriteDataSource
import com.saulmm.codewars.repository.ReadableDataSource
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.util.Date

class CachingRepositoryTest {

    @Test
    fun `when asking for data, and there is nothing in local, ask remote and save`() = runTest {
        val local = FakeLocalDataSource()

        val remote = FakeLocalDataSource.FakeRemoteDataSource(
            mapOf(
                "key1" to 1,
                "key2" to 2
            ),
        )

        val repository = CachingRepository(remote = remote, local = local)

        repository.get(RepositoryParams("key1"))
        assertThat(local.getData(RepositoryParams("key1"))).isEqualTo(1)
    }


    class FakeLocalDataSource(
        private val map: MutableMap<String, Pair<Int, Long>> = mutableMapOf()
    ) : ReadAndWriteDataSource<RepositoryParams, Int> {

        override suspend fun getData(query: RepositoryParams): Int? {
            return map[query.id]?.first
        }

        override suspend fun saveData(query: RepositoryParams, data: Int) {
            map[query.id] = data to Date().time
        }

        override suspend fun lastSavedDataDate(query: RepositoryParams): Date? {
            return Date(map.map { it.value.second }.max())
        }

        class FakeRemoteDataSource(
            val map: Map<String, Int>
        ) : ReadableDataSource<RepositoryParams, Int> {

            override suspend fun getData(query: RepositoryParams): Int? {
                return map[query.id]
            }
        }
    }
}

data class RepositoryParams(val id: String)