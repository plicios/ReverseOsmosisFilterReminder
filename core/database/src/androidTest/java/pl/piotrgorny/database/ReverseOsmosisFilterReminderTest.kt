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
import pl.piotrgorny.database.dao.ReverseOsmosisFilterReminderDao
import pl.piotrgorny.database.entity.ReverseOsmosisFilterReminder
import java.io.IOException
import java.util.*
import java.util.concurrent.CountDownLatch

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ReverseOsmosisFilterReminderTest {
    private lateinit var reverseOsmosisFilterReminderDao: ReverseOsmosisFilterReminderDao
    private lateinit var db: ReverseOsmosisDatabase


    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ReverseOsmosisDatabase::class.java).build()
        reverseOsmosisFilterReminderDao = db.reverseOsmosisFilterReminderDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeFilterSetupAndReadInList() = runBlocking {
        val filterReminder = ReverseOsmosisFilterReminder(
            0,
            Date(),
            "buynew"
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

    @Test
    fun updateFilterSetup() = runBlocking {
        val filterReminder = ReverseOsmosisFilterReminder(
            0,
            Date(),
            "buynew"
        )
        val modifiedFilterReminder = ReverseOsmosisFilterReminder(
            0,
            Date(Date().time + 4000),
            "buynew"
        )

        modifiedFilterReminder.uid = reverseOsmosisFilterReminderDao.insert(filterReminder).first()
        reverseOsmosisFilterReminderDao.update(modifiedFilterReminder)


        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            reverseOsmosisFilterReminderDao.getByFilter(0).collect {
                assertThat(it).doesNotContain(filterReminder)
                assertThat(it).contains(modifiedFilterReminder)
                latch.countDown()
            }
        }
        latch.await()
        job.cancelAndJoin()
    }

    @Test
    fun deleteFilterSetup() = runBlocking {
        val filterReminder = ReverseOsmosisFilterReminder(
            0,
            Date(),
            "buynew"
        )

        filterReminder.uid = reverseOsmosisFilterReminderDao.insert(filterReminder).first()

        reverseOsmosisFilterReminderDao.delete(filterReminder)

        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            reverseOsmosisFilterReminderDao.getByFilter(0).collect {
                assertThat(it).doesNotContain(filterReminder)
                latch.countDown()
            }
        }
        latch.await()
        job.cancelAndJoin()
    }
}