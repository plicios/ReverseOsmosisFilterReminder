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
import pl.piotrgorny.database.dao.ReverseOsmosisFilterDao
import pl.piotrgorny.database.dao.ReverseOsmosisFilterSetupDao
import pl.piotrgorny.database.entity.FilterType
import pl.piotrgorny.database.entity.ReverseOsmosisFilter
import pl.piotrgorny.database.entity.ReverseOsmosisFilterSetup
import java.io.IOException
import java.util.*
import java.util.concurrent.CountDownLatch

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ReverseOsmosisFilterTest {
    private lateinit var reverseOsmosisFilterDao: ReverseOsmosisFilterDao
    private lateinit var db: ReverseOsmosisDatabase


    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ReverseOsmosisDatabase::class.java).build()
        reverseOsmosisFilterDao = db.reverseOsmosisFilterDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeFilterSetupAndReadInList() = runBlocking {
        val filter = ReverseOsmosisFilter(
            0,
            FilterType.Carbon,
            "test",
            1,
            Date()
        )
        reverseOsmosisFilterDao.insert(filter)
        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            reverseOsmosisFilterDao.getByFilterSetup(0).collect {
                assertThat(it).contains(filter)
                latch.countDown()
            }
        }
        latch.await()
        job.cancelAndJoin()
    }

    @Test
    fun updateFilterSetup() = runBlocking {
        val filter = ReverseOsmosisFilter(
            0,
            FilterType.Carbon,
            "test",
            1,
            Date()
        )
        val modifiedFilter = ReverseOsmosisFilter(
            0,
            FilterType.SemiPermeableMembrane,
            "test2",
            10,
            Date()
        )

        modifiedFilter.uid = reverseOsmosisFilterDao.insert(filter).first()
        reverseOsmosisFilterDao.update(modifiedFilter)


        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            reverseOsmosisFilterDao.getByFilterSetup(0).collect {
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
        val filter = ReverseOsmosisFilter(
            0,
            FilterType.Carbon,
            "test",
            1,
            Date()
        )

        filter.uid = reverseOsmosisFilterDao.insert(filter).first()

        reverseOsmosisFilterDao.delete(filter)

        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            reverseOsmosisFilterDao.getByFilterSetup(0).collect {
                assertThat(it).doesNotContain(filter)
                latch.countDown()
            }
        }
        latch.await()
        job.cancelAndJoin()
    }
}