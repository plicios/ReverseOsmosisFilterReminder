package pl.piotrgorny.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import pl.piotrgorny.database.dao.FilterSetupDao
import pl.piotrgorny.database.entity.FilterSetup
import java.io.IOException
import java.util.*
import java.util.concurrent.CountDownLatch

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class FilterSetupTest {
    private lateinit var filterSetupDao: FilterSetupDao
    private lateinit var db: ReverseOsmosisDatabase


    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ReverseOsmosisDatabase::class.java).build()
        filterSetupDao = db.filterSetupDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeFilterSetupAndReadInList() = runBlocking {
        val filterSetup = FilterSetup(
            "test"
        )
        filterSetupDao.insert(filterSetup)
        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            filterSetupDao.getAll().collect {
                assertThat(
                    it.map { filterSetupWithFilters -> filterSetupWithFilters.filterSetup }
                ).contains(filterSetup)
                latch.countDown()
            }
        }
        latch.await()
        job.cancelAndJoin()
    }

    @Test
    fun updateFilterSetup() = runBlocking {
        val filterSetup = FilterSetup(
            "test"
        )
        val modifiedFilterSetup = FilterSetup(
            "test2"
        )

        modifiedFilterSetup.uid = filterSetupDao.insert(filterSetup).first()
        filterSetupDao.update(modifiedFilterSetup)


        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            filterSetupDao.getAll().collect {
                assertThat(
                    it.map { filterSetupWithFilters -> filterSetupWithFilters.filterSetup }
                ).doesNotContain(filterSetup)
                assertThat(
                    it.map { filterSetupWithFilters -> filterSetupWithFilters.filterSetup }
                ).contains(modifiedFilterSetup)
                latch.countDown()
            }
        }
        latch.await()
        job.cancelAndJoin()
    }

    @Test
    fun deleteFilterSetup() = runBlocking {
        val filterSetup = FilterSetup(
            "test"
        )

        filterSetup.uid = filterSetupDao.insert(filterSetup).first()

        filterSetupDao.delete(filterSetup)

        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            filterSetupDao.getAll().collect {
                assertThat(
                    it.map { filterSetupWithFilters -> filterSetupWithFilters.filterSetup }
                ).doesNotContain(filterSetup)
                latch.countDown()
            }
        }
        latch.await()
        job.cancelAndJoin()
    }
}