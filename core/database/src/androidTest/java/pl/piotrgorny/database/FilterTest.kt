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
import pl.piotrgorny.database.dao.FilterDao
import pl.piotrgorny.database.entity.Filter
import java.io.IOException
import java.util.*
import java.util.concurrent.CountDownLatch

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class FilterTest {
    private lateinit var filterDao: FilterDao
    private lateinit var db: ReverseOsmosisDatabase


    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ReverseOsmosisDatabase::class.java).build()
        filterDao = db.filterDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeFilterSetupAndReadInList() = runBlocking {
        val filter = Filter(
            0,
            "Carbon",
            "one day",
            Date()
        )
        filterDao.insert(filter)
        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            filterDao.getByFilterSetup(0).collect {
                assertThat(it).contains(filter)
                latch.countDown()
            }
        }
        latch.await()
        job.cancelAndJoin()
    }

    @Test
    fun updateFilterSetup() = runBlocking {
        val filter = Filter(
            0,
            "Carbon",
            "one day",
            Date()
        )
        val modifiedFilter = Filter(
            0,
            "SemiPermeableMembrane",
            "two days",
            Date()
        )

        modifiedFilter.uid = filterDao.insert(filter).first()
        filterDao.update(modifiedFilter)


        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            filterDao.getByFilterSetup(0).collect {
                assertThat(it).doesNotContain(filter)
                assertThat(it).contains(modifiedFilter)
                latch.countDown()
            }
        }
        latch.await()
        job.cancelAndJoin()
    }

    @Test
    fun deleteFilterSetup() = runBlocking {
        val filter = Filter(
            0,
            "Carbon",
            "one day",
            Date()
        )

        filter.uid = filterDao.insert(filter).first()

        filterDao.delete(filter)

        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            filterDao.getByFilterSetup(0).collect {
                assertThat(it).doesNotContain(filter)
                latch.countDown()
            }
        }
        latch.await()
        job.cancelAndJoin()
    }
}