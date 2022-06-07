package fastcampus.part5.fastcampus_todo_mvvm.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import fastcampus.part5.fastcampus_todo_mvvm.di.appTestModule
import fastcampus.part5.fastcampus_todo_mvvm.livedata.LiveDataTestObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

@ExperimentalCoroutinesApi
internal abstract class ViewModelTest : KoinTest{

    //유닛테스트를 위한 mocking라이브러리 모키토
    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    //mock 데이터
    @Mock
    private lateinit var context: Application

    //쓰레드를 쉽게 바꾸기 위함
    //코루틴 테스트할 때 해당 디스페처로 메인 쓰레드로 넘어갔다가 io쓰레드로 넘어갔다가
    private val dispatcher = TestCoroutineDispatcher()


    @Before
    fun setup(){
        startKoin {
            androidContext(context)
            module{ appTestModule}
        }
        Dispatchers.setMain(dispatcher)
    }

    //테스트 끝난 후 코루틴과 코인 주입했었던 것을 마무리
    @After
    fun tearDown(){
        stopKoin()
        Dispatchers.resetMain() // MainDispatcher를 초기화 해주어야 메모리 누수가 발생하지 않음
    }

    //라이브 데이터를 바로 사용할 수 없어서 비슷하게 동작하는 코드를 작성
    //
    protected fun <T> LiveData<T>.test(): LiveDataTestObserver<T> {
        val testObserver = LiveDataTestObserver<T>()
        observeForever(testObserver)
        return testObserver
    }

}