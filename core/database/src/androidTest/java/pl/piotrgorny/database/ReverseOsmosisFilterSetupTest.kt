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
import pl.piotrgorny.database.dao.ReverseOsmosisFilterSetupDao
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
class ReverseOsmosisFilterSetupTest {
    private lateinit var reverseOsmosisFilterSetupDao: ReverseOsmosisFilterSetupDao
    private lateinit var db: ReverseOsmosisDatabase


    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ReverseOsmosisDatabase::class.java).build()
        reverseOsmosisFilterSetupDao = db.reverseOsmosisFilterSetupDao()
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
    fun updateFilterSetup() = runBlocking {
        val filterSetup = ReverseOsmosisFilterSetup(
            "test"
        )
        val modifiedFilterSetup = ReverseOsmosisFilterSetup(
            "test2"
        )

        modifiedFilterSetup.uid = reverseOsmosisFilterSetupDao.insert(filterSetup).first()
        reverseOsmosisFilterSetupDao.update(modifiedFilterSetup)


        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            reverseOsmosisFilterSetupDao.getAll().collect {
                assertThat(it).doesNotContain(filterSetup)
                assertThat(it).contains(modifiedFilterSetup)
                latch.countDown()
            }
        }
        latch.await()
        job.cancelAndJoin()
    }

    @Test
    fun deleteFilterSetup() = runBlocking {
        val filterSetup = ReverseOsmosisFilterSetup(
            "test"
        )

        filterSetup.uid = reverseOsmosisFilterSetupDao.insert(filterSetup).first()

        reverseOsmosisFilterSetupDao.delete(filterSetup)

        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            reverseOsmosisFilterSetupDao.getAll().collect {
                assertThat(it).doesNotContain(filterSetup)
                latch.countDown()
            }
        }
        latch.await()
        job.cancelAndJoin()
    }
}