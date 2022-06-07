package fastcampus.part5.fastcampus_todo_mvvm.di

import fastcampus.part5.fastcampus_todo_mvvm.data.repository.TestToDoRepository
import fastcampus.part5.fastcampus_todo_mvvm.data.repository.ToDoRepository
import fastcampus.part5.fastcampus_todo_mvvm.domain.todo.GetTodoListUseCase
import fastcampus.part5.fastcampus_todo_mvvm.domain.todo.InsertToDoListUseCase
import fastcampus.part5.fastcampus_todo_mvvm.presentation.list.ListViewModel
import org.koin.android.experimental.dsl.viewModel
import org.koin.android.viewmodel.dsl.viewModel

import org.koin.dsl.module

internal val appTestModule = module {

    //ViewModel
    viewModel { ListViewModel(get()) }

    //UseCase
    factory {GetTodoListUseCase(get())}
    factory { InsertToDoListUseCase(get()) }

    //Repository
    single<ToDoRepository>{ TestToDoRepository()}

}