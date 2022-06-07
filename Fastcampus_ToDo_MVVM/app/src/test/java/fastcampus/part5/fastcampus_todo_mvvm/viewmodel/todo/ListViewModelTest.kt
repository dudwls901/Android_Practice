package fastcampus.part5.fastcampus_todo_mvvm.viewmodel.todo


import fastcampus.part5.fastcampus_todo_mvvm.viewmodel.ViewModelTest
import fastcampus.part5.fastcampus_todo_mvvm.data.entity.ToDoEntity
import fastcampus.part5.fastcampus_todo_mvvm.domain.todo.InsertToDoListUseCase
import fastcampus.part5.fastcampus_todo_mvvm.presentation.list.ListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.koin.test.inject


/*
* [ListViewModel]을 테스트하기 위한 Unit Test Class
* 1. initData()
* 2. test viewModel fetch
* 3. test Item Update
* 4. test Item Delete All
* */
@ExperimentalCoroutinesApi
internal class ListViewModelTest: ViewModelTest() {

    private val viewModel: ListViewModel by inject()

    private val insertToDoListUseCase: InsertToDoListUseCase by inject()

    private val mockList = (0 until 10).map{
        ToDoEntity(
            id = it.toLong(),
            title = "title $it",
            description = "description $it",
            hasCompleted = false
        )
    }

    /* Use case
    * 1. InsertTodoListUseCase
    * 2. GetTodoItemUseCase
    * 3.
    * */

    //초기화
    @Before
    fun init(){
        initData()
    }
    private fun initData() = runBlockingTest {
        insertToDoListUseCase(mockList)
    }

    // Test : 입력된 데이터를 불러와서 검증한다.
    @Test
    fun `test viewModel fetch`(): Unit = runBlockingTest {
        val testObservable = viewModel.todoListLiveData.test()
        viewModel.fetchData()
        testObservable.assertValueSequence(
            listOf(
                mockList
            )
        )
    }

}