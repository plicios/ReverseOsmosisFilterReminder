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
import pl.piotrgorny.database.dao.ReverseOsmosisFilterReminderDao
import pl.piotrgorny.database.dao.ReverseOsmosisFilterSetupDao
import pl.piotrgorny.database.entity.FilterType
import pl.piotrgorny.database.entity.ReverseOsmosisFilter
import pl.piotrgorny.database.entity.ReverseOsmosisFilterReminder
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
class SimpleEntityReadWriteTest {
    private lateinit var reverseOsmosisFilterSetupDao: ReverseOsmosisFilterSetupDao
    private lateinit var reverseOsmosisFilterDao: ReverseOsmosisFilterDao
    private lateinit var reverseOsmosisFilterReminderDao: ReverseOsmosisFilterReminderDao
    private lateinit var db: ReverseOsmosisDatabase


    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ReverseOsmosisDatabase::class.java).build()
        reverseOsmosisFilterSetupDao = db.reverseOsmosisFilterSetupDao()
        reverseOsmosisFilterDao = db.reverseOsmosisFilterDao()
        reverseOsmosisFilterReminderDao = db.reverseOsmosisFilterReminderDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeFilterSetupAndReadInList() = runBlocking {
        val filterSetup = ReverseOsmosisFilterSetup(
            "test"
        )
        reverseOsmosisFilterSetupDao.insert(filterSetup)
        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            reverseOsmosisFilterSetupDao.getAll().collect {
                assertThat(it).contains(filterSetup)
                latch.countDown()
            }
        }
        latch.await()
        job.cancelAndJoin()
    }

    @Test
    fun writeFilterAndReadInList() = runBlocking {
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
    fun writeFilterReminderAndReadInList() = runBlocking {
        val filterReminder = ReverseOsmosisFilterReminder(
            0,
            Date()
        )
        reverseOsmosisFilterReminderDao.insert(filterReminder)
        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            reverseOsmosisFilterReminderDao.getByFilter(0).collect {
                assertThat(it).contains(filterReminder)
                latch.countDown()
            }
        }
        latch.await()
        job.cancelAndJoin()
    }
}