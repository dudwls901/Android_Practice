package fastcampus.part5.fastcampus_todo_mvvm.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fastcampus.part5.fastcampus_todo_mvvm.data.entity.ToDoEntity
import fastcampus.part5.fastcampus_todo_mvvm.domain.todo.GetTodoListUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/*
* 필요한 UseCase
* 1. [GetTodoListUseCase]
* 2. [UpdateTodoUseCase]
* 3. [DeleteAllToDoItemUsecase]
* */

internal class ListViewModel(
    private val getTodoListUseCase: GetTodoListUseCase
): ViewModel() {
    private var _toDoListLiveData = MutableLiveData<List<ToDoEntity>>()
    val todoListLiveData: LiveData<List<ToDoEntity>> = _toDoListLiveData

    fun fetchData(): Job = viewModelScope.launch{
        _toDoListLiveData.postValue(getTodoListUseCase())
    }
}